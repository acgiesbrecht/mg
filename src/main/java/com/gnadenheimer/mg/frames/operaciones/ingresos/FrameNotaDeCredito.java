/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.frames.operaciones.ingresos;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.gnadenheimer.mg.domain.TblAsientos;
import com.gnadenheimer.mg.domain.TblAsientosTemporales;
import com.gnadenheimer.mg.domain.TblFacturas;
import com.gnadenheimer.mg.domain.TblNotasDeCredito;
import com.gnadenheimer.mg.domain.TblRecibos;
import com.gnadenheimer.mg.domain.TblTransferencias;
import com.gnadenheimer.mg.domain.miembros.TblEntidades;
import com.gnadenheimer.mg.domain.models.PagosRealizados;
import com.gnadenheimer.mg.utils.CurrentUser;
import com.gnadenheimer.mg.utils.Utils;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public class FrameNotaDeCredito extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameNotaDeCredito.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    Map<String, String> persistenceMap = new HashMap<>();
    EventList<String> eventListRuc = new BasicEventList<>();
    DatePickerSettings datePickerSettings = new DatePickerSettings(Locale.getDefault());
    TimePickerSettings timePickerSettings = new TimePickerSettings(Locale.getDefault());

    public FrameNotaDeCredito() {
        super("Nota de Credito",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {

            persistenceMap = Utils.getInstance().getPersistenceMap();
            datePickerSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
            timePickerSettings.setFormatForDisplayTime("HH:mm:ss");
            timePickerSettings.setFormatForMenuTimes("HH:mm:ss");

            initComponents();
            if (!Beans.isDesignTime()) {
                entityManager.getTransaction().begin();
            }

            eventListRuc.clear();
            eventListRuc.addAll((List<String>) entityManager.createQuery("select distinct f.ruc from TblFacturas f where f.anulado = false order by f.ruc").getResultList());
            AutoCompleteSupport support1 = AutoCompleteSupport.install(cboRuc, eventListRuc);
            support1.setFilterMode(TextMatcherEditor.CONTAINS);

            cboRuc.setSelectedIndex(-1);
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addPropertyChangeListener("permanentFocusOwner", new PropertyChangeListener() {
                        @Override
                        public void propertyChange(final PropertyChangeEvent e) {
                            if (e.getNewValue() instanceof JFormattedTextField) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        JFormattedTextField textField = (JFormattedTextField) e.getNewValue();
                                        textField.selectAll();
                                    }
                                });
                            }
                            if (e.getNewValue() instanceof JTextField) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        JTextField textField = (JTextField) e.getNewValue();
                                        textField.selectAll();
                                    }
                                });
                            }
                        }
                    });
            refresh();
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        entityManager = java.beans.Beans.isDesignTime() ? null : Persistence.createEntityManagerFactory("mg_PU", persistenceMap).createEntityManager();
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblNotasDeCredito t ORDER BY t.nro");
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        queryFacturas = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblFacturas t WHERE t.nro = null");
        listFacturas = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryFacturas.getResultList());
        queryTimbrados = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblTimbradosNotasDeCredito t WHERE t.activo = true");
        listTimbrados = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryTimbrados.getResultList());
        dateTimeTableCellRenderer1 = new com.gnadenheimer.mg.utils.DateTimeTableCellRenderer();
        cancelarButton = new javax.swing.JButton();
        imprimirButton = new javax.swing.JButton();
        montoLabel = new javax.swing.JLabel();
        txtTimbrado = new javax.swing.JFormattedTextField();
        montoLabel1 = new javax.swing.JLabel();
        fecha1Label = new javax.swing.JLabel();
        ctacteLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFacturas = new javax.swing.JTable();
        dtpFecha = new DateTimePicker(datePickerSettings, timePickerSettings);
        cboRuc = new javax.swing.JComboBox<>();
        txtNro = new javax.swing.JTextField();

        FormListener formListener = new FormListener();

        dateTimeTableCellRenderer1.setText("dateTimeTableCellRenderer1");

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(formListener);

        imprimirButton.setText("Guardar & Imprimir");
        imprimirButton.addActionListener(formListener);

        montoLabel.setText("Timbrado:");
        montoLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtTimbrado.setColumns(9);
        txtTimbrado.setEditable(false);
        txtTimbrado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtTimbrado.addFocusListener(formListener);
        txtTimbrado.addMouseListener(formListener);
        txtTimbrado.addActionListener(formListener);

        montoLabel1.setText("Nota de Credito Nro:");
        montoLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        fecha1Label.setText("Fecha:");

        ctacteLabel3.setText("RUC:");

        tblFacturas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listFacturas, tblFacturas);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nro}"));
        columnBinding.setColumnName("Nro");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idTimbrado.nro}"));
        columnBinding.setColumnName("Timbrado");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha/Hora");
        columnBinding.setColumnClass(java.time.LocalDateTime.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${razonSocial}"));
        columnBinding.setColumnName("Razon Social");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ruc}"));
        columnBinding.setColumnName("RUC");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${importeAporte}"));
        columnBinding.setColumnName("Importe Aporte");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${importeDonacion}"));
        columnBinding.setColumnName("Importe Donacion");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(tblFacturas);
        if (tblFacturas.getColumnModel().getColumnCount() > 0) {
            tblFacturas.getColumnModel().getColumn(2).setCellRenderer(dateTimeTableCellRenderer1);
        }

        cboRuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboRuc.addActionListener(formListener);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(458, 458, 458)
                                .addComponent(imprimirButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelarButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(montoLabel)
                                    .addComponent(montoLabel1)
                                    .addComponent(fecha1Label)
                                    .addComponent(ctacteLabel3))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtNro, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtTimbrado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)))))
                        .addGap(0, 27, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelarButton, imprimirButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(montoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoLabel1)
                    .addComponent(txtNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(fecha1Label))
                    .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ctacteLabel3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelarButton)
                    .addComponent(imprimirButton))
                .addGap(23, 23, 23))
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.MouseListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == cancelarButton) {
                FrameNotaDeCredito.this.cancelarButtonActionPerformed(evt);
            }
            else if (evt.getSource() == imprimirButton) {
                FrameNotaDeCredito.this.imprimirButtonActionPerformed(evt);
            }
            else if (evt.getSource() == txtTimbrado) {
                FrameNotaDeCredito.this.txtTimbradoActionPerformed(evt);
            }
            else if (evt.getSource() == cboRuc) {
                FrameNotaDeCredito.this.cboRucActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == txtTimbrado) {
                FrameNotaDeCredito.this.txtTimbradoFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == txtTimbrado) {
                FrameNotaDeCredito.this.txtTimbradoMouseClicked(evt);
            }
        }

        public void mouseEntered(java.awt.event.MouseEvent evt) {
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
        }

        public void mousePressed(java.awt.event.MouseEvent evt) {
        }

        public void mouseReleased(java.awt.event.MouseEvent evt) {
        }
    }// </editor-fold>//GEN-END:initComponents

    void refresh() {
        listTimbrados.clear();
        listTimbrados.addAll(queryTimbrados.getResultList());
        if (listTimbrados.size() > 0) {
            txtTimbrado.setText(listTimbrados.get(0).getNro());
            imprimirButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Debe tener un timbrado activo para poder facturar.");
            imprimirButton.setEnabled(false);
        }

        if (listTimbrados.size() > 0) {
            txtTimbrado.setText(listTimbrados.get(0).getNro());
            imprimirButton.setEnabled(true);
            if (list.size() > 0) {
                txtNro.setText(Utils.generateNextFacturaNroConEstPtoExp(listTimbrados.get(0).getEstablecimiento(),
                        listTimbrados.get(0).getPuntoDeExpedicion(),
                        list.get(list.size() - 1).getNro()));
            } else {
                txtNro.setText(Utils.generateFacturaNroConEstPtoExp(listTimbrados.get(0).getEstablecimiento(),
                        listTimbrados.get(0).getPuntoDeExpedicion(),
                        listTimbrados.get(0).getNroNotaDeCreditoIncio()));
            }
            cboRuc.setSelectedIndex(-1);
            dtpFecha.setDateTimeStrict(LocalDateTime.now());
            
        } else {
            JOptionPane.showMessageDialog(null, "Debe tener un timbrado activo para poder facturar.");
            imprimirButton.setEnabled(false);
        }
    }

    Boolean validar() {
        if (txtNro.getText().length() < 15) {
            txtNro.setBackground(Color.red);
            JOptionPane.showMessageDialog(null, "Numero de factura no valido!");
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private void imprimirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirButtonActionPerformed
        try {

            if (!validar()) {
                JOptionPane.showMessageDialog(null, "Datos no válidos!");
                return;
            }

            if (tblFacturas.getSelectedRowCount() != 1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una factura de la lista!");
                return;
            }

            TblFacturas factura = listFacturas.get(tblFacturas.convertRowIndexToModel(tblFacturas.getSelectedRow()));

            TblNotasDeCredito notaDeCredito = new TblNotasDeCredito();
            entityManager.persist(notaDeCredito);

            notaDeCredito.setNro(txtNro.getText());
            notaDeCredito.setIdTimbrado(listTimbrados.get(0));
            notaDeCredito.setFechahora(dtpFecha.getDateTimeStrict());
            notaDeCredito.setNroFactura(factura);
            List<TblAsientos> ts = notaDeCredito.getTblAsientosList();
            if (ts == null) {
                ts = new LinkedList<>();
                notaDeCredito.setTblAsientosList((List) ts);
            }

            for (TblAsientos asientoFactura : factura.getTblAsientosList()) {
                TblAsientos asientoNota = new TblAsientos();
                asientoNota.setFechahora(notaDeCredito.getFechahora());
                asientoNota.setIdCentroDeCostoDebe(asientoFactura.getIdCentroDeCostoHaber());
                asientoNota.setIdCentroDeCostoHaber(asientoFactura.getIdCentroDeCostoDebe());
                asientoNota.setIdCuentaContableDebe(asientoFactura.getIdCuentaContableHaber());
                asientoNota.setIdCuentaContableHaber(asientoFactura.getIdCuentaContableDebe());
                asientoNota.setMonto(asientoFactura.getMonto());
                asientoNota.setIdUser(currentUser.getUser());
                ts.add(asientoNota);
                for (TblAsientosTemporales at : asientoFactura.getTblAsientosTemporalesList()) {
                    at.setFacturado(false);
                    entityManager.merge(at);
                }
            }

            notaDeCredito.setAnulado(false);
            notaDeCredito.setIdUser(currentUser.getUser());

            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();

            Utils.getInstance().printNotaDeCredito(notaDeCredito);

            refresh();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_imprimirButtonActionPerformed

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarButtonActionPerformed
        try {
            refresh();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cancelarButtonActionPerformed

    private void txtTimbradoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimbradoFocusGained

    }//GEN-LAST:event_txtTimbradoFocusGained

    private void txtTimbradoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimbradoMouseClicked

    }//GEN-LAST:event_txtTimbradoMouseClicked

    private void txtTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimbradoActionPerformed

    }//GEN-LAST:event_txtTimbradoActionPerformed

    private void cboRucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRucActionPerformed
        try {
            if (cboRuc.getSelectedItem() != null) {
                listFacturas.clear();
                listFacturas.addAll((List<TblFacturas>) entityManager.createQuery("select f from TblFacturas f where f.anulado = false and f.ruc = '" + cboRuc.getSelectedItem().toString() + "'").getResultList());

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cboRucActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelarButton;
    private javax.swing.JComboBox<String> cboRuc;
    private javax.swing.JLabel ctacteLabel3;
    private com.gnadenheimer.mg.utils.DateTimeTableCellRenderer dateTimeTableCellRenderer1;
    private com.github.lgooddatepicker.components.DateTimePicker dtpFecha;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel fecha1Label;
    private javax.swing.JButton imprimirButton;
    private javax.swing.JScrollPane jScrollPane1;
    private java.util.List<com.gnadenheimer.mg.domain.TblNotasDeCredito> list;
    private java.util.List<com.gnadenheimer.mg.domain.TblFacturas> listFacturas;
    private java.util.List<com.gnadenheimer.mg.domain.TblTimbradosNotasDeCredito> listTimbrados;
    private javax.swing.JLabel montoLabel;
    private javax.swing.JLabel montoLabel1;
    private javax.persistence.Query query;
    private javax.persistence.Query queryFacturas;
    private javax.persistence.Query queryTimbrados;
    private javax.swing.JTable tblFacturas;
    private javax.swing.JTextField txtNro;
    private javax.swing.JFormattedTextField txtTimbrado;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameNotaDeCredito.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameNotaDeCredito.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameNotaDeCredito.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameNotaDeCredito.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new FrameNotaDeCredito());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
