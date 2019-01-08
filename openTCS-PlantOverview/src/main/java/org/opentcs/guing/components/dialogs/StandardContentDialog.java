/*
 * openTCS copyright information:
 * Copyright (c) 2005-2011 ifak e.V.
 * Copyright (c) 2012 Fraunhofer IML
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.guing.components.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

/**
 * Ein Standarddialog mit einem Ok- und einem Abbrechen-Button. Ein
 * Standarddialog nimmt jeglichen Inhalt vom Typ JComponent auf und stellt ihn
 * als Inhalt dar.
 *
 * @author Sebastian Naumann (ifak e.V. Magdeburg)
 */
public class StandardContentDialog
    extends javax.swing.JDialog implements InputValidationListener {

  /**
   * A return status code - returned if Cancel button has been pressed
   */
  public static final int RET_CANCEL = 0;
  /**
   * A return status code - returned if OK button has been pressed
   */
  public static final int RET_OK = 1;
  /**
   * Button-Konfiguration Ok und Abbrechen.
   */
  public static final int OK_CANCEL = 10;
  /**
   * Button-Konfiguration Ok, Abbrechen und Übernehmen.
   */
  public static final int OK_CANCEL_APPLY = 11;
  /**
   * Button-Konfiguration Schließen.
   */
  public static final int CLOSE = 12;
  /**
   * Button-Konfiguration benutzerdefiniert.
   */
  public static final int USER_DEFINED = 13;
  /**
   * Der Dialoginhalt.
   */
  protected DialogContent fContent;
  /**
   *
   */
  private int returnStatus = RET_CANCEL;

  /**
   * Creates new instance.
   */
  public StandardContentDialog(Component parent, DialogContent content) {
    this(parent, content, true, OK_CANCEL);
  }

  /**
   * Creates new form StandardDialog.
   *
   * @param parent Die Komponente, zu der der Dialog zentriert wird
   * @param content der Inhalt
   * @param modal ob der Dialog modal sein soll
   * @param options welche Schaltflächen angezeigt werden sollen
   */
  public StandardContentDialog(Component parent,
                               DialogContent content,
                               boolean modal,
                               int options) {
    super(JOptionPane.getFrameForComponent(parent), modal);

    initComponents();
    initButtons(options);

    JComponent component = content.getComponent();

    if (component.getBorder() == null) {
      component.setBorder(new EmptyBorder(4, 4, 4, 4));
    }

    getContentPane().add(component, BorderLayout.CENTER);
    setTitle(content.getDialogTitle());
    content.initFields();
    pack();
    setLocationRelativeTo(parent);
    fContent = content;

    getRootPane().setDefaultButton(okButton);
  }

  @Override
  public void inputValidationSuccessful(boolean success) {
    this.okButton.setEnabled(success);
  }

  /**
   * Initialisiert die Schaltknöpfe.
   *
   * @param options welche Schaltknöpfe angezeigt werden sollen
   */
  

  /**
   * Liefert den Return-Status.
   *
   * @return the return status of this dialog - one of RET_OK or RET_CANCEL
   */
  public int getReturnStatus() {
    return returnStatus;
  }

  /**
   * Fügt eine benutzerdefinierte Schaltfläche hinzu.
   *
   * @param text die Beschriftung der Schaltfläche
   * @param returnStatus der Rückgabewert, wenn die Schaltfläche gedrückt wird
   */
  public void addUserDefinedButton(String text, final int returnStatus) {
    JButton button = new JButton(text);
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent evt) {
        doClose(returnStatus);
      }
    });

    buttonPanel.add(button);
  }

  /**
   * Botschaft des Dialoginhaltes, dass der Dialog geschlossen werden kann.
   */
  public void requestClose() {
    doClose(RET_CANCEL);
  }
  protected final void initButtons(int options) {
    switch (options) {
      case OK_CANCEL:
        applyButton.setVisible(false);
        closeButton.setVisible(false);
        break;

      case OK_CANCEL_APPLY:
        closeButton.setVisible(false);
        break;

      case CLOSE:
        okButton.setVisible(false);
        cancelButton.setVisible(false);
        applyButton.setVisible(false);
        break;

      case USER_DEFINED:
        okButton.setVisible(false);
        cancelButton.setVisible(false);
        applyButton.setVisible(false);
        closeButton.setVisible(false);
        break;

      default:
    }
  }


  // CHECKSTYLE:OFF
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    buttonPanel = new javax.swing.JPanel();
    okButton = new javax.swing.JButton();
    cancelButton = new CancelButton();
    applyButton = new javax.swing.JButton();
    closeButton = new javax.swing.JButton();

    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        closeDialog(evt);
      }
    });

    buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 5));
    buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

    okButton.setFont(okButton.getFont().deriveFont(okButton.getFont().getStyle() | java.awt.Font.BOLD));
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/opentcs/guing/res/labels"); // NOI18N
    okButton.setText(bundle.getString("dialog.buttonOk.text")); // NOI18N
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        okButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(okButton);

    cancelButton.setFont(cancelButton.getFont());
    cancelButton.setText(bundle.getString("dialog.buttonCancel.text")); // NOI18N
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cancelButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(cancelButton);

    applyButton.setFont(applyButton.getFont());
    applyButton.setText(bundle.getString("dialog.buttonApply.text")); // NOI18N
    applyButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        applyButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(applyButton);

    closeButton.setFont(closeButton.getFont());
    closeButton.setText(bundle.getString("dialog.buttonClose.text")); // NOI18N
    closeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        closeButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(closeButton);

    getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

    pack();
  }// </editor-fold>//GEN-END:initComponents
  // CHECKSTYLE:ON

  /**
   * Button "Schließen" gedrückt.
   *
   * @param evt das Ereignis
   */
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
      doClose(RET_CANCEL);
    }//GEN-LAST:event_closeButtonActionPerformed

  /**
   * Button "Übernehmen" gedrückt.
   *
   * @param evt das Ereignis
   */
    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
      fContent.update();
    }//GEN-LAST:event_applyButtonActionPerformed

  /**
   * Button "Ok" gedrückt.
   *
   * @param evt das Ereignis
   */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
      fContent.update();

      if (!fContent.updateFailed()) {
        doClose(RET_OK);
      }
    }//GEN-LAST:event_okButtonActionPerformed

  /**
   * Button "Abbrechen" gedrückt.
   *
   * @param evt das Ereignis
   */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
      doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

  /**
   * Closes the dialog
   */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
      doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

  /**
   * Schließt den Dialog.
   *
   * @param retStatus der Rückgabestatus
   */
  private void doClose(int retStatus) {
    returnStatus = retStatus;
    setVisible(false);
    dispose();
  }

  // CHECKSTYLE:OFF
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton applyButton;
  private javax.swing.JPanel buttonPanel;
  private javax.swing.JButton cancelButton;
  private javax.swing.JButton closeButton;
  private javax.swing.JButton okButton;
  // End of variables declaration//GEN-END:variables
  // CHECKSTYLE:ON
}
