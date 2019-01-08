/*
 * openTCS copyright information:
 * Copyright (c) 2005-2011 ifak e.V.
 * Copyright (c) 2012 Fraunhofer IML
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.guing.components.properties.panel;

import com.google.inject.Inject;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.opentcs.components.plantoverview.PropertySuggestions;
import org.opentcs.guing.components.dialogs.DetailsDialogContent;
import org.opentcs.guing.components.properties.type.KeyValueProperty;
import org.opentcs.guing.components.properties.type.MergedPropertySuggestions;
import org.opentcs.guing.components.properties.type.Property;
import org.opentcs.guing.util.ResourceBundleUtil;
import org.opentcs.util.gui.BoundsPopupMenuListener;

/**
 * A panel for editing a property (key-value pair).
 *
 * @see KeyValueProperty
 * @author Sebastian Naumann (ifak e.V. Magdeburg)
 */
public class KeyValuePropertyEditorPanel
    extends JPanel
    implements DetailsDialogContent {

  /**
   * The property to be edited.
   */
  private KeyValueProperty fProperty;
  /**
   * The combo box's key textfield.
   */
  private final JTextField keyTextField;
  /**
   * The combo box's value textfield.
   */
  private final JTextField valueTextField;
  /**
   * Suggestions for property keys and values.
   */
  private final PropertySuggestions propertySuggestions;

  /**
   * Creates new instance.
   *
   * @param propertySuggestions The properties that are suggested.
   */
  @Inject
  public KeyValuePropertyEditorPanel(MergedPropertySuggestions propertySuggestions) {
    this.propertySuggestions = requireNonNull(propertySuggestions, "propertySuggestions");
    initComponents();
    valueComboBox.addPopupMenuListener(new BoundsPopupMenuListener());
    keyComboBox.addPopupMenuListener(new BoundsPopupMenuListener());
    fProperty = new KeyValueProperty(null, "", "");
    keyTextField = (JTextField) (keyComboBox.getEditor().getEditorComponent());
    valueTextField = (JTextField) (valueComboBox.getEditor().getEditorComponent());
  }

  @Override
  public void setProperty(Property property) {
    fProperty = (KeyValueProperty) property;
    keyTextField.setText(fProperty.getKey());
    valueTextField.setText(fProperty.getValue());
  }

  @Override
  public void updateValues() {
    fProperty.setKeyAndValue(keyTextField.getText(), valueTextField.getText());
  }

  @Override
  public String getTitle() {
    return ResourceBundleUtil.getBundle().getString("KeyValuePropertyEditorPanel.title");
  }

  @Override
  public Property getProperty() {
    return fProperty;
  }

  // CHECKSTYLE:OFF
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    keyLabel = new javax.swing.JLabel();
    valueLabel = new javax.swing.JLabel();
    keyComboBox = new JComboBox<>(propertySuggestions.getKeySuggestions().toArray(new String[propertySuggestions.getKeySuggestions().size()]));
    valueComboBox = new JComboBox<>(propertySuggestions.getValueSuggestions().toArray(new String[propertySuggestions.getValueSuggestions().size()]));

    setLayout(new java.awt.GridBagLayout());

    keyLabel.setFont(keyLabel.getFont());
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/opentcs/guing/res/labels"); // NOI18N
    keyLabel.setText(bundle.getString("KeyValuePropertyEditorPanel.key.text")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
    add(keyLabel, gridBagConstraints);

    valueLabel.setFont(valueLabel.getFont());
    valueLabel.setText(bundle.getString("KeyValuePropertyEditorPanel.value.text")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
    add(valueLabel, gridBagConstraints);

    keyComboBox.setEditable(true);
    keyComboBox.setPrototypeDisplayValue("tenletters");
    keyComboBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        keyValueChangedListener(evt);
      }
    });
    add(keyComboBox, new java.awt.GridBagConstraints());

    valueComboBox.setEditable(true);
    valueComboBox.setPrototypeDisplayValue("tenletters");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    add(valueComboBox, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

  private void keyValueChangedListener(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_keyValueChangedListener

    Set<String> specSuggestions = propertySuggestions.getValueSuggestionsFor(
        String.valueOf(keyComboBox.getSelectedItem()));
    if (specSuggestions.isEmpty()) {
      specSuggestions = propertySuggestions.getValueSuggestions();
    }
    String[] valueSuggestions = specSuggestions
        .toArray(new String[specSuggestions.size()]);
    Object currentSuggestion = valueComboBox.getEditor().getItem();
    valueComboBox.setModel(new DefaultComboBoxModel<>(valueSuggestions));
    valueComboBox.validate();
    valueComboBox.getEditor().setItem(currentSuggestion);
  }//GEN-LAST:event_keyValueChangedListener

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox<String> keyComboBox;
  private javax.swing.JLabel keyLabel;
  private javax.swing.JComboBox<String> valueComboBox;
  private javax.swing.JLabel valueLabel;
  // End of variables declaration//GEN-END:variables
  // CHECKSTYLE:ON
}
