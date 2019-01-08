/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.strategies.basic.dispatching.phase.parking;

import static java.util.Objects.requireNonNull;
import java.util.Set;
import javax.inject.Inject;
import org.opentcs.components.kernel.Router;
import org.opentcs.components.kernel.services.InternalTransportOrderService;
import org.opentcs.data.model.Point;
import org.opentcs.data.model.Vehicle;
import org.opentcs.strategies.basic.dispatching.DefaultDispatcherConfiguration;
import org.opentcs.strategies.basic.dispatching.ProcessabilityChecker;
import org.opentcs.strategies.basic.dispatching.TransportOrderUtil;
import org.opentcs.strategies.basic.dispatching.selection.CompositeReparkVehicleSelectionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates parking orders for idle vehicles already at a parking position to send them to higher
 * prioritized parking positions.
 *
 * @author Martin Grzenia (Fraunhofer IML)
 */
public class PrioritizedReparkPhase
    extends AbstractParkingPhase {

  /**
   * This class's logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PrioritizedReparkPhase.class);

  private final CompositeReparkVehicleSelectionFilter vehicleSelectionFilter;
  private final ParkingPositionPriorityComparator priorityComparator;

  @Inject
  public PrioritizedReparkPhase(InternalTransportOrderService orderService,
                                PrioritizedParkingPositionSupplier parkingPosSupplier,
                                Router router,
                                ProcessabilityChecker processabilityChecker,
                                TransportOrderUtil transportOrderUtil,
                                DefaultDispatcherConfiguration configuration,
                                CompositeReparkVehicleSelectionFilter vehicleSelectionFilter,
                                ParkingPositionPriorityComparator priorityComparator) {
    super(orderService,
          parkingPosSupplier,
          router,
          processabilityChecker,
          transportOrderUtil,
          configuration);
    this.vehicleSelectionFilter = requireNonNull(vehicleSelectionFilter, "vehicleSelectionFilter");
    this.priorityComparator = requireNonNull(priorityComparator, "priorityComparator");
  }

  @Override
  public void run() {
    if (!getConfiguration().parkIdleVehicles()
        || !getConfiguration().considerParkingPositionPriorities()
        || !getConfiguration().reparkVehiclesToHigherPriorityPositions()) {
      return;
    }

    LOG.debug("Looking for parking vehicles to send to higher prioritized parking positions...");

    Set<Vehicle> vehicles = getOrderService().fetchObjects(Vehicle.class, vehicleSelectionFilter);
    vehicles.stream()
        .sorted((vehicle1, vehicle2) -> {
          // Sort the vehicles based on the priority of the parking position they occupy
          Point point1 = getOrderService().fetchObject(Point.class, vehicle1.getCurrentPosition());
          Point point2 = getOrderService().fetchObject(Point.class, vehicle2.getCurrentPosition());
          return priorityComparator.compare(point1, point2);
        })
        .forEach(vehicle -> createParkingOrder(vehicle));
  }
}
