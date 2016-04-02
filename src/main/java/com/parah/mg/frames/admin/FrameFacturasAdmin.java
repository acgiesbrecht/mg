/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames.admin;

import com.parah.mg.domain.TblAsientos;
import com.parah.mg.domain.TblFacturas;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import java.awt.EventQueue;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public class FrameFacturasAdmin extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameFacturasAdmin.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    String databaseIP;
    Map<String, String> persistenceMap = new HashMap<>();

    public FrameFacturasAdmin() {
        super("Administrar Facturas",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        persistenceMap = Utils.getInstance().getPersistenceMap();
        initComponents();
        if (!Beans.isDesignTime()) {
            entityManager.getTransaction().begin();
        }
        chkAnulado.setVisible(false);
        txtNro.setVisible(false);

        TableFilterHeader filterHeader = new TableFilterHeader(masterTable, AutoChoices.ENABLED);
        filterHeader.setAdaptiveChoices(false);
        filterHeader.getParserModel().setIgnoreCase(true);
        filterHeader.setPosition(TableFilterHeader.Position.TOP);
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
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblFacturas t ORDER BY t.fechahora");
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        dateToStringConverter1 = new com.parah.mg.utils.DateToStringConverter();
        dateTableCellRenderer1 = new com.parah.mg.utils.DateTimeTableCellRenderer();
        queryGrupos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblGrupos t");
        listGrupos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryGrupos.getResultList());
        queryEventoTipos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventoTipos t");
        listEventoTipos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventoTipos.getResultList());
        donacionTableCellRenderer1 = new com.parah.mg.utils.DonacionTableCellRenderer();
        rucTableCellRenderer1 = new com.parah.mg.utils.RucTableCellRenderer();
        facturaNroTableCellRenderer1 = new com.parah.mg.utils.FacturaNroTableCellRenderer();
        numberCellRenderer1 = new com.parah.mg.utils.NumberCellRenderer();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        anularButton = new javax.swing.JButton();
        imprimirButton = new javax.swing.JButton();
        chkAnulado = new javax.swing.JCheckBox();
        txtNro = new javax.swing.JFormattedTextField();

        FormListener formListener = new FormListener();

        dateTableCellRenderer1.setText("dateTableCellRenderer1");

        donacionTableCellRenderer1.setText("donacionTableCellRenderer1");

        rucTableCellRenderer1.setText("rucTableCellRenderer1");

        facturaNroTableCellRenderer1.setText("facturaNroTableCellRenderer1");

        numberCellRenderer1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numberCellRenderer1.setText("numberCellRenderer1");

        masterTable.setAutoCreateRowSorter(true);
        masterTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nro}"));
        columnBinding.setColumnName("Nro");
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idTimbrado}"));
        columnBinding.setColumnName("Timbrado");
        columnBinding.setColumnClass(com.parah.mg.domain.TblTimbrados.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${razonSocial}"));
        columnBinding.setColumnName("Razon Social");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ruc}"));
        columnBinding.setColumnName("RUC");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${importeAporte}"));
        columnBinding.setColumnName("Importe Aporte");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${importeDonacion}"));
        columnBinding.setColumnName("Importe Donacion");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${importeTotal}"));
        columnBinding.setColumnName("Importe Total");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idEntidad}"));
        columnBinding.setColumnName("Miembro");
        columnBinding.setColumnClass(com.parah.mg.domain.miembros.TblEntidades.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${anulado}"));
        columnBinding.setColumnName("Anulado");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(0).setCellRenderer(facturaNroTableCellRenderer1);
            masterTable.getColumnModel().getColumn(2).setResizable(false);
            masterTable.getColumnModel().getColumn(2).setCellRenderer(dateTableCellRenderer1);
            masterTable.getColumnModel().getColumn(4).setResizable(false);
            masterTable.getColumnModel().getColumn(4).setCellRenderer(rucTableCellRenderer1);
            masterTable.getColumnModel().getColumn(5).setCellRenderer(numberCellRenderer1);
            masterTable.getColumnModel().getColumn(6).setCellRenderer(numberCellRenderer1);
            masterTable.getColumnModel().getColumn(7).setCellRenderer(numberCellRenderer1);
        }

        anularButton.setText("Anular");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), anularButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        anularButton.addActionListener(formListener);

        imprimirButton.setText("Imprimir");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), imprimirButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        imprimirButton.addActionListener(formListener);

        chkAnulado.setText("Anulado");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.anulado}"), chkAnulado, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.nro}"), txtNro, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 837, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chkAnulado)
                        .addGap(51, 51, 51)
                        .addComponent(txtNro, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(imprimirButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(anularButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {anularButton, imprimirButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(anularButton)
                    .addComponent(imprimirButton)
                    .addComponent(chkAnulado)
                    .addComponent(txtNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == anularButton) {
                FrameFacturasAdmin.this.anularButtonActionPerformed(evt);
            }
            else if (evt.getSource() == imprimirButton) {
                FrameFacturasAdmin.this.imprimirButtonActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("unchecked")
    private void imprimirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirButtonActionPerformed
        try {
            if (masterTable.getSelectedRow() > -1) {
                TblFacturas factura = list.get(masterTable.convertRowIndexToModel(masterTable.getSelectedRow()));
                Utils.getInstance().printFactura(factura);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_imprimirButtonActionPerformed

    private void anularButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anularButtonActionPerformed
        try {
            if (masterTable.getSelectedRow() > -1) {
                int[] selected = masterTable.getSelectedRows();

                TblFacturas t = list.get(masterTable.convertRowIndexToModel(selected[0]));

                t.setAnulado(true);
                Calendar calendar = Calendar.getInstance();
                for (TblAsientos asiento : t.getTblAsientosCollection()) {
                    Calendar calendarAsiento = Calendar.getInstance();
                    calendarAsiento.setTime(asiento.getFechahora());
                    if (calendarAsiento.get(Calendar.MONTH) < calendar.get(Calendar.MONTH)) {
                        TblAsientos asientoInverso = new TblAsientos();
                        entityManager.persist(asientoInverso);
                        asientoInverso.setFechahora(new Date());
                        asientoInverso.setIdCentroDeCosto(asiento.getIdCentroDeCosto());
                        asientoInverso.setIdCuentaContableDebe(asiento.getIdCuentaContableHaber());
                        asientoInverso.setIdCuentaContableHaber(asiento.getIdCuentaContableDebe());
                        asientoInverso.setMonto(asiento.getMonto());
                        asientoInverso.setIdUser(currentUser.getUser());
                        asientoInverso.setObservacion("Anulacion");
                    } else {
                        entityManager.remove(asiento);
                    }
                }
                entityManager.merge(t);
                //chkAnulado.setSelected(true);
                entityManager.getTransaction().commit();
                entityManager.getTransaction().begin();
                java.util.Collection data = query.getResultList();
                for (Object entity : data) {
                    entityManager.refresh(entity);
                }
                list.clear();
                list.addAll(data);
            }
        } catch (RollbackException ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            entityManager.getTransaction().begin();
            List<com.parah.mg.domain.TblFacturas> merged = new ArrayList<>(list.size());
            for (com.parah.mg.domain.TblFacturas t : list) {
                merged.add(entityManager.merge(t));
            }
            list.clear();
            list.addAll(merged);
        }
    }//GEN-LAST:event_anularButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton anularButton;
    private javax.swing.JCheckBox chkAnulado;
    private com.parah.mg.utils.DateTimeTableCellRenderer dateTableCellRenderer1;
    private com.parah.mg.utils.DateToStringConverter dateToStringConverter1;
    private com.parah.mg.utils.DonacionTableCellRenderer donacionTableCellRenderer1;
    private javax.persistence.EntityManager entityManager;
    private com.parah.mg.utils.FacturaNroTableCellRenderer facturaNroTableCellRenderer1;
    private javax.swing.JButton imprimirButton;
    private java.util.List<com.parah.mg.domain.TblFacturas> list;
    private java.util.List<com.parah.mg.domain.TblEventoTipos> listEventoTipos;
    private java.util.List<com.parah.mg.domain.TblGrupos> listGrupos;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private com.parah.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query query;
    private javax.persistence.Query queryEventoTipos;
    private javax.persistence.Query queryGrupos;
    private com.parah.mg.utils.RucTableCellRenderer rucTableCellRenderer1;
    private javax.swing.JFormattedTextField txtNro;
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
            java.util.logging.Logger.getLogger(FrameFacturasAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameFacturasAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameFacturasAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameFacturasAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                frame.setContentPane(new FrameFacturasAdmin());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
