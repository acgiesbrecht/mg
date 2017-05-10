/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.frames.operaciones.ingresos;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.gnadenheimer.mg.domain.TblAsientos;
import com.gnadenheimer.mg.domain.TblAsientosTemporales;
import com.gnadenheimer.mg.domain.TblCuentasContablesPorDefecto;
import com.gnadenheimer.mg.domain.TblEventoDetalle;
import com.gnadenheimer.mg.domain.TblEventoTipos;
import com.gnadenheimer.mg.domain.TblEventos;
import com.gnadenheimer.mg.domain.TblTransferencias;
import com.gnadenheimer.mg.domain.models.PagosEventoPendientes;
import com.gnadenheimer.mg.domain.models.PagosMensualesPendientes;
import com.gnadenheimer.mg.utils.CurrentUser;
import com.gnadenheimer.mg.utils.Utils;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.Beans;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public class FrameCobrarTransferenciasAyCporEvento extends JInternalFrame implements TableModelListener {

    private static final Logger LOGGER = LogManager.getLogger(FrameCobrarTransferenciasAyCporEvento.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    Map<String, String> persistenceMap = new HashMap<>();
    TblCuentasContablesPorDefecto cuentasContablesPorDefecto;
    DatePickerSettings datePickerSettings = new DatePickerSettings(Locale.getDefault());

    public FrameCobrarTransferenciasAyCporEvento() {
        super("Cobrar Aportes y Colectas",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        persistenceMap = Utils.getInstance().getPersistenceMap();
        initComponents();
        if (!Beans.isDesignTime()) {
            entityManager.getTransaction().begin();
        }

        datePickerSettings.setFormatForDatesCommonEra("dd/MM/yyyy");

        cuentasContablesPorDefecto = entityManager.find(TblCuentasContablesPorDefecto.class, 1);

        TableFilterHeader filterHeader = new TableFilterHeader(masterTable, AutoChoices.DISABLED);
        filterHeader.setAdaptiveChoices(false);
        filterHeader.getParserModel().setIgnoreCase(true);
        filterHeader.setPosition(TableFilterHeader.Position.TOP);

        Action marcarCobrado = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (masterTable.getSelectedRowCount() > 0) {

                }
            }
        };
        masterTable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_MASK),
                "marcarCobrado");
        masterTable.getActionMap().put("marcarCobrado",
                marcarCobrado);

        masterTable.getModel().addTableModelListener(this);

        refresh();
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        try {
            Integer suma = 0;
            for (PagosEventoPendientes pago : list) {
                if (pago.getCobrado()) {
                    suma += pago.getTblEventoDetalle().getMonto();
                }
            }
            lblTotal.setText(String.format("%(,d", suma));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
        queryMiembros = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEntidades t ORDER BY t.apellidos, t.nombres");
        listMiembros = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryMiembros.getResultList());
        dateToStringConverter1 = new com.gnadenheimer.mg.utils.DateToStringConverter();
        dateTableCellRenderer1 = new com.gnadenheimer.mg.utils.DateTimeTableCellRenderer();
        numberCellRenderer1 = new com.gnadenheimer.mg.utils.NumberCellRenderer();
        integerLongConverter1 = new com.gnadenheimer.mg.utils.IntegerLongConverter();
        queryEventos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventos t WHERE t.idEventoTipo.id > 1 AND t.idGrupo IN :grupos AND  EXTRACT(YEAR FROM t.fecha) = :anoActivo ORDER BY t.fecha");
        queryEventos.setParameter("grupos", currentUser.getUser().getTblGruposList());
        queryEventos.setParameter("anoActivo", persistenceMap.get("anoActivo"));
        listEventos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventos.getResultList());
        ctaCteTableCellRenderer1 = new com.gnadenheimer.mg.utils.CtaCteTableCellRenderer();
        mesTableCellRenderer1 = new com.gnadenheimer.mg.utils.MesTableCellRenderer();
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventoDetalle t WHERE t.id = null");
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        saveButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        cboMarcarSeleccionados = new javax.swing.JButton();
        descripcionLabel4 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        descripcionLabel5 = new javax.swing.JLabel();
        dtpFechaCobro = new DatePicker(datePickerSettings);

        FormListener formListener = new FormListener();

        dateTableCellRenderer1.setText("dateTableCellRenderer1");

        numberCellRenderer1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numberCellRenderer1.setText("numberCellRenderer1");

        ctaCteTableCellRenderer1.setText("ctaCteTableCellRenderer1");

        mesTableCellRenderer1.setText("mesTableCellRenderer1");

        addInternalFrameListener(formListener);

        masterTable.setAutoCreateRowSorter(true);
        masterTable.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tblEventoDetalle.idEntidad.ctacte}"));
        columnBinding.setColumnName("Cta Cte");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tblEventoDetalle.idEntidad.nombreCompleto}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tblEventoDetalle.fechahora}"));
        columnBinding.setColumnName("Fecha de Evento");
        columnBinding.setColumnClass(java.time.LocalDateTime.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tblEventoDetalle.idEvento.descripcion}"));
        columnBinding.setColumnName("Evento");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tblEventoDetalle.idEvento.idEventoTipo.descripcion}"));
        columnBinding.setColumnName("Tipo de Evento");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tblEventoDetalle.monto}"));
        columnBinding.setColumnName("Importe");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${cobrado}"));
        columnBinding.setColumnName("Cobrado");
        columnBinding.setColumnClass(Boolean.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            masterTable.getColumnModel().getColumn(0).setCellRenderer(ctaCteTableCellRenderer1);
            masterTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            masterTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            masterTable.getColumnModel().getColumn(2).setCellRenderer(dateTableCellRenderer1);
            masterTable.getColumnModel().getColumn(5).setCellRenderer(numberCellRenderer1);
            masterTable.getColumnModel().getColumn(6).setPreferredWidth(50);
        }

        saveButton.setText("Guardar");
        saveButton.addActionListener(formListener);

        refreshButton.setText("Cancelar");
        refreshButton.addActionListener(formListener);

        cboMarcarSeleccionados.setText("Marcar como cobrado a las filas seleccionadas");
        cboMarcarSeleccionados.addActionListener(formListener);

        descripcionLabel4.setText("Fecha de Cobro:");

        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(102, 102, 102));

        descripcionLabel5.setText("Importe total de Registros marcados:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(masterScrollPane, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cboMarcarSeleccionados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(refreshButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descripcionLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(descripcionLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dtpFechaCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {refreshButton, saveButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descripcionLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dtpFechaCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descripcionLabel4))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(refreshButton)
                    .addComponent(cboMarcarSeleccionados))
                .addContainerGap())
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, javax.swing.event.InternalFrameListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                FrameCobrarTransferenciasAyCporEvento.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FrameCobrarTransferenciasAyCporEvento.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cboMarcarSeleccionados) {
                FrameCobrarTransferenciasAyCporEvento.this.cboMarcarSeleccionadosActionPerformed(evt);
            }
        }

        public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            if (evt.getSource() == FrameCobrarTransferenciasAyCporEvento.this) {
                FrameCobrarTransferenciasAyCporEvento.this.formInternalFrameActivated(evt);
            }
        }

        public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
        }

        public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
        }

        public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
        }

        public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
        }

        public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
        }

        public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
        }
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("unchecked")
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {
            int secuencia = (new Random()).nextInt(10000);
            for (PagosEventoPendientes pago : list) {
                if (pago.getCobrado()) {

                    TblTransferencias t = new TblTransferencias();
                    entityManager.persist(t);
                    t.setIdEntidad(pago.getTblEventoDetalle().getIdEntidad());
                    t.setConcepto(pago.getTblEventoDetalle().getIdEvento().getIdEventoTipo().getDescripcion() + " " + String.valueOf(pago.getTblEventoDetalle().getFechahora().get(ChronoField.MONTH_OF_YEAR)) + "/" + String.valueOf(pago.getTblEventoDetalle().getFechahora().get(ChronoField.YEAR)));
                    t.setMontoAporte(((Long) (pago.getTblEventoDetalle().getMonto().longValue() * pago.getTblEventoDetalle().getIdEvento().getPorcentajeAporte().longValue() / 100)).intValue());
                    t.setMontoDonacion(pago.getTblEventoDetalle().getMonto() - t.getMontoAporte());
                    t.setCobrado(true);

                    t.setFechahoraCompromiso(pago.getTblEventoDetalle().getFechahora().toLocalDate());
                    t.setFechahora(dtpFechaCobro.getDate());
                    t.setIdEventoDetalle(pago.getTblEventoDetalle());
                    t.setIdEventoTipo(pago.getTblEventoDetalle().getIdEvento().getIdEventoTipo());
                    t.setIdUser(currentUser.getUser());
                    t.setSeqPago(secuencia);

                    List<TblAsientosTemporales> listAsientosTemporales = t.getTblAsientosTemporalesList();
                    if (listAsientosTemporales == null) {
                        listAsientosTemporales = new LinkedList<>();
                        t.setTblAsientosTemporalesList(listAsientosTemporales);
                    }

                    for (TblAsientos asiento : pago.getTblEventoDetalle().getTblAsientosList()) {
                        TblAsientosTemporales aT = new TblAsientosTemporales();
                        entityManager.persist(aT);
                        aT.setFacturado(false);
                        aT.setFechahora(t.getFechahora().atStartOfDay());
                        aT.setIdCentroDeCostoDebe(asiento.getIdCentroDeCostoHaber());
                        aT.setIdCentroDeCostoHaber(asiento.getIdCentroDeCostoDebe());
                        aT.setIdCuentaContableDebe(asiento.getIdCentroDeCostoHaber().getIdCuentaContableCtaCtePorDefecto());
                        aT.setIdCuentaContableHaber(asiento.getIdCuentaContableDebe());
                        if (asiento.getIdCuentaContableHaber().equals(cuentasContablesPorDefecto.getIdCuentaAportes())) {
                            aT.setEsAporte(true);
                        } else {
                            aT.setEsAporte(false);
                        }
                        aT.setMonto(asiento.getMonto());

                        listAsientosTemporales.add(aT);

                    }

                    if (t.getTblAsientosTemporalesList().stream().mapToInt(x -> x.getMonto()).sum() != t.getMontoTotal()) {
                        JOptionPane.showMessageDialog(null, "Error de consistencia de importes. Transferencia no guardada.");
                        entityManager.remove(t);
                        return;
                    }
                }
            }
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            refresh();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private Integer findAsientoTemporal(List<TblAsientosTemporales> listAsientosTemporales, TblAsientosTemporales at) {

        return -1;
    }

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated

    }//GEN-LAST:event_formInternalFrameActivated

    private void cboMarcarSeleccionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMarcarSeleccionadosActionPerformed
        try {
            int[] selectedRows = masterTable.getSelectedRows();
            for (Integer i = 0; i < selectedRows.length; i++) {
                //list.get(i).setCobrado(true);
                masterTable.setValueAt(true, selectedRows[i], 5);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cboMarcarSeleccionadosActionPerformed

    void refresh() {
        try {
            entityManager.getTransaction().rollback();
            entityManager.getTransaction().begin();
            query = entityManager.createQuery("SELECT new com.gnadenheimer.mg.domain.models.PagosEventoPendientes(evd, false) FROM TblEventoDetalle evd WHERE (evd.idEvento.idEventoTipo.id = 2 OR evd.idEvento.idEventoTipo.id = 3) AND evd.id NOT IN (SELECT t.idEventoDetalle.id FROM TblTransferencias t) AND EXTRACT(YEAR FROM evd.fechahora) = " + persistenceMap.get("anoActivo") + " ORDER BY evd.idEntidad.ctacte, evd.fechahora");
            list.clear();
            list.addAll(query.getResultList());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cboMarcarSeleccionados;
    private com.gnadenheimer.mg.utils.CtaCteTableCellRenderer ctaCteTableCellRenderer1;
    private com.gnadenheimer.mg.utils.DateTimeTableCellRenderer dateTableCellRenderer1;
    private com.gnadenheimer.mg.utils.DateToStringConverter dateToStringConverter1;
    private javax.swing.JLabel descripcionLabel4;
    private javax.swing.JLabel descripcionLabel5;
    private com.github.lgooddatepicker.components.DatePicker dtpFechaCobro;
    private javax.persistence.EntityManager entityManager;
    private com.gnadenheimer.mg.utils.IntegerLongConverter integerLongConverter1;
    private javax.swing.JLabel lblTotal;
    private java.util.List<com.gnadenheimer.mg.domain.models.PagosEventoPendientes> list;
    private java.util.List<com.gnadenheimer.mg.domain.TblEventos> listEventos;
    private java.util.List listMiembros;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private com.gnadenheimer.mg.utils.MesTableCellRenderer mesTableCellRenderer1;
    private com.gnadenheimer.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query query;
    private javax.persistence.Query queryEventos;
    private javax.persistence.Query queryMiembros;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
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
            java.util.logging.Logger.getLogger(FrameCobrarTransferenciasAyCporEvento.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameCobrarTransferenciasAyCporEvento.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameCobrarTransferenciasAyCporEvento.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameCobrarTransferenciasAyCporEvento.class
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

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new FrameCobrarTransferenciasAyCporEvento());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
