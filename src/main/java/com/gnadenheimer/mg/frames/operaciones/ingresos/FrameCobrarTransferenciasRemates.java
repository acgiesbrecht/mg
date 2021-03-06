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
import com.gnadenheimer.mg.domain.TblAsientosTemporales;
import com.gnadenheimer.mg.domain.TblCuentasContablesPorDefecto;
import com.gnadenheimer.mg.domain.TblEventos;
import com.gnadenheimer.mg.domain.TblTransferencias;
import com.gnadenheimer.mg.utils.CurrentUser;
import com.gnadenheimer.mg.utils.Utils;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.persistence.Persistence;
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
public class FrameCobrarTransferenciasRemates extends JInternalFrame implements TableModelListener {

    private static final Logger LOGGER = LogManager.getLogger(FrameCobrarTransferenciasRemates.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    Map<String, String> persistenceMap = new HashMap<>();
    TblCuentasContablesPorDefecto cuentasContablesPorDefecto;
    DatePickerSettings datePickerSettings = new DatePickerSettings(Locale.getDefault());

    public FrameCobrarTransferenciasRemates() {
        super("Cobrar Transferencias de Remates",
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

        AutoCompleteSupport support = AutoCompleteSupport.install(cboFechaRemate, GlazedLists.eventListOf(listEventos.toArray()));
        support.setFilterMode(TextMatcherEditor.CONTAINS);

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
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        try {
            Integer suma = 0;
            for (TblTransferencias t : list) {
                if (t.getCobrado()) {
                    suma += t.getMontoTotal();
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
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblTransferencias t WHERE t.idEvento = :eventoId AND t.cobrado = false");
        query.setParameter("eventoId", null) ;
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        queryMiembros = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEntidades t ORDER BY t.apellidos, t.nombres");
        listMiembros = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryMiembros.getResultList());
        dateToStringConverter1 = new com.gnadenheimer.mg.utils.DateToStringConverter();
        numberCellRenderer1 = new com.gnadenheimer.mg.utils.NumberCellRenderer();
        integerLongConverter1 = new com.gnadenheimer.mg.utils.IntegerLongConverter();
        ctaCteTableCellRenderer1 = new com.gnadenheimer.mg.utils.CtaCteTableCellRenderer();
        mesTableCellRenderer1 = new com.gnadenheimer.mg.utils.MesTableCellRenderer();
        queryEventos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventos t WHERE t.idEventoTipo.id = 1 AND t.idGrupo IN :grupos AND EXTRACT(YEAR FROM t.fecha) = :anoActivo ORDER BY t.fecha");
        queryEventos.setParameter("grupos", currentUser.getUser().getTblGruposList());
        queryEventos.setParameter("anoActivo", persistenceMap.get("anoActivo"));
        listEventos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventos.getResultList());
        dateTableCellRenderer2 = new com.gnadenheimer.mg.utils.DateTableCellRenderer();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        saveButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        cboMarcarSeleccionados = new javax.swing.JButton();
        descripcionLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboFechaRemate = new javax.swing.JComboBox();
        dtpFechaCobro = new DatePicker(datePickerSettings);
        lblTotal = new javax.swing.JLabel();
        descripcionLabel5 = new javax.swing.JLabel();

        FormListener formListener = new FormListener();

        numberCellRenderer1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numberCellRenderer1.setText("numberCellRenderer1");

        ctaCteTableCellRenderer1.setText("ctaCteTableCellRenderer1");

        mesTableCellRenderer1.setText("mesTableCellRenderer1");

        dateTableCellRenderer2.setText("dateTableCellRenderer2");

        addInternalFrameListener(formListener);

        masterTable.setAutoCreateRowSorter(true);
        masterTable.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahoraCompromiso}"));
        columnBinding.setColumnName("Fecha");
        columnBinding.setColumnClass(java.time.LocalDate.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idEntidad.ctacte}"));
        columnBinding.setColumnName("Cta Cte");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idEntidad.nombreCompleto}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${montoTotal}"));
        columnBinding.setColumnName("Monto");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${cobrado}"));
        columnBinding.setColumnName("Cobrado");
        columnBinding.setColumnClass(Boolean.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(0).setCellRenderer(dateTableCellRenderer2);
            masterTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            masterTable.getColumnModel().getColumn(1).setCellRenderer(ctaCteTableCellRenderer1);
            masterTable.getColumnModel().getColumn(2).setPreferredWidth(200);
            masterTable.getColumnModel().getColumn(3).setCellRenderer(numberCellRenderer1);
        }

        saveButton.setText("Guardar");
        saveButton.addActionListener(formListener);

        refreshButton.setText("Cancelar");
        refreshButton.addActionListener(formListener);

        cboMarcarSeleccionados.setText("Marcar como cobrado a las filas seleccionadas");
        cboMarcarSeleccionados.addActionListener(formListener);

        descripcionLabel4.setText("Fecha de Cobro:");

        jLabel2.setText("Fecha de Remate:");

        cboFechaRemate.addActionListener(formListener);

        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(102, 102, 102));

        descripcionLabel5.setText("Importe total de Registros marcados:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(masterScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cboMarcarSeleccionados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(refreshButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboFechaRemate, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(descripcionLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dtpFechaCobro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(descripcionLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {refreshButton, saveButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboFechaRemate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descripcionLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dtpFechaCobro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descripcionLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(refreshButton)
                    .addComponent(cboMarcarSeleccionados)))
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, javax.swing.event.InternalFrameListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                FrameCobrarTransferenciasRemates.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FrameCobrarTransferenciasRemates.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cboMarcarSeleccionados) {
                FrameCobrarTransferenciasRemates.this.cboMarcarSeleccionadosActionPerformed(evt);
            }
            else if (evt.getSource() == cboFechaRemate) {
                FrameCobrarTransferenciasRemates.this.cboFechaRemateActionPerformed(evt);
            }
        }

        public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            if (evt.getSource() == FrameCobrarTransferenciasRemates.this) {
                FrameCobrarTransferenciasRemates.this.formInternalFrameActivated(evt);
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
            if (dtpFechaCobro.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Fecha de cobro invalida o no especificada.");
                return;
            }
            int secuencia = (new Random()).nextInt(10000);
            for (TblTransferencias t : list) {
                if (t.getCobrado()) {
                    t.setCobrado(true);
                    t.setFechahora(dtpFechaCobro.getDate());
                    t.setSeqPago(secuencia);

                    /*Query queryEvd = entityManager.createQuery("SELECT t FROM TblEventoDetalle t "
                            + "WHERE t.idEntidad = :entidad"
                            + " AND t.idEvento = :eventoId");

                    queryEvd.setParameter("entidad", t.getIdEntidad());
                    queryEvd.setParameter("eventoId", t.getIdEvento());
                    List<TblEventoDetalle> listEvd = (List<TblEventoDetalle>) queryEvd.getResultList();
                    List<TblAsientos> listAsientos = new ArrayList<>();
                    for (TblEventoDetalle evd : listEvd) {
                        listAsientos.addAll(evd.getTblAsientosList());
                    }

                    Query queryTransferenciasAnteriores = entityManager.createQuery("SELECT t FROM TblTransferencias t "
                            + "WHERE t.idEntidad = :entidad"
                            + " AND t.idEvento = :eventoId");
                    queryTransferenciasAnteriores.setParameter("entidad", t.getIdEntidad());
                    queryTransferenciasAnteriores.setParameter("eventoId", t.getIdEvento());
                    List<TblTransferencias> listTransferenciasAnteriores = (List<TblTransferencias>) queryTransferenciasAnteriores.getResultList();

                    for (TblTransferencias tAnterior : listTransferenciasAnteriores) {
                        if (tAnterior != t) {
                            for (TblAsientosTemporales atAnterior : tAnterior.getTblAsientosTemporalesList()) {
                                for (TblAsientos asiento : listAsientos) {
                                    if (atAnterior.getIdCentroDeCosto().equals(asiento.getIdCentroDeCosto())
                                            && atAnterior.getIdCuentaContableDebe().equals(asiento.getIdCentroDeCosto().getIdCuentaContableCtaCtePorDefecto())
                                            && atAnterior.getIdCuentaContableHaber().equals(asiento.getIdCuentaContableDebe())
                                            && atAnterior.getMonto().equals(asiento.getMonto())) {
                                        listAsientos.remove(asiento);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    List<TblAsientosTemporales> listAsientosTemporales = t.getTblAsientosTemporalesList();
                    if (listAsientosTemporales == null) {
                        listAsientosTemporales = new LinkedList<>();
                        t.setTblAsientosTemporalesList(listAsientosTemporales);
                    }

                    for (TblAsientos asiento : listAsientos) {
                        TblAsientosTemporales aT = new TblAsientosTemporales();
                        entityManager.persist(aT);
                        aT.setFacturado(false);
                        aT.setFechahora(t.getFechahora().atStartOfDay());
                        aT.setIdCentroDeCosto(asiento.getIdCentroDeCosto());
                        aT.setIdCuentaContableDebe(asiento.getIdCentroDeCosto().getIdCuentaContableCtaCtePorDefecto());
                        aT.setIdCuentaContableHaber(asiento.getIdCuentaContableDebe());
                        if (asiento.getIdCuentaContableHaber().equals(cuentasContablesPorDefecto.getIdCuentaAportes())) {
                            aT.setEsAporte(true);
                        } else {
                            aT.setEsAporte(false);
                        }
                        aT.setMonto(asiento.getMonto());
                        listAsientosTemporales.add(aT);
                    }*/
                    List<TblAsientosTemporales> listAsientosTemporales = t.getTblAsientosTemporalesList();
                    if (listAsientosTemporales == null) {
                        listAsientosTemporales = new LinkedList<>();
                        t.setTblAsientosTemporalesList(listAsientosTemporales);
                    }
                    TblAsientosTemporales asientoTemporalAporte = new TblAsientosTemporales();
                    entityManager.persist(asientoTemporalAporte);
                    asientoTemporalAporte.setFacturado(false);
                    asientoTemporalAporte.setFechahora(t.getFechahora().atStartOfDay());
                    asientoTemporalAporte.setIdCentroDeCostoDebe(t.getIdEvento().getIdCentroDeCosto());
                    asientoTemporalAporte.setIdCentroDeCostoHaber(t.getIdEvento().getIdCentroDeCosto());
                    asientoTemporalAporte.setIdCuentaContableDebe(t.getIdEvento().getIdCentroDeCosto().getIdCuentaContableCtaCtePorDefecto());
                    asientoTemporalAporte.setIdCuentaContableHaber(cuentasContablesPorDefecto.getIdCuentaAportes());
                    asientoTemporalAporte.setEsAporte(true);
                    asientoTemporalAporte.setMonto(t.getMontoAporte());
                    listAsientosTemporales.add(asientoTemporalAporte);

                    TblAsientosTemporales asientoTemporalDonacion = new TblAsientosTemporales();
                    entityManager.persist(asientoTemporalDonacion);
                    asientoTemporalDonacion.setFacturado(false);
                    asientoTemporalDonacion.setFechahora(t.getFechahora().atStartOfDay());
                    asientoTemporalDonacion.setIdCentroDeCostoDebe(t.getIdEvento().getIdCentroDeCosto());
                    asientoTemporalDonacion.setIdCentroDeCostoHaber(t.getIdEvento().getIdCentroDeCosto());
                    asientoTemporalDonacion.setIdCuentaContableDebe(t.getIdEvento().getIdCentroDeCosto().getIdCuentaContableCtaCtePorDefecto());
                    asientoTemporalDonacion.setIdCuentaContableHaber(cuentasContablesPorDefecto.getIdCuentaDonaciones());
                    asientoTemporalDonacion.setEsAporte(false);
                    asientoTemporalDonacion.setMonto(t.getMontoDonacion());
                    listAsientosTemporales.add(asientoTemporalDonacion);

                    entityManager.merge(t);

                    /*Integer montoAsientos = //t.getTblAsientosTemporalesList().stream().mapToInt(x -> x.getMonto()).sum();
                    Integer montoT = t.getMontoTotal();
                    if (!montoAsientos.equals(montoT)) {
                        JOptionPane.showMessageDialog(null, "Error de consistencia de importes. Transferencia no guardada.");
                        t.getTblAsientosTemporalesList().clear();
                        entityManager.merge(t);
                        return;
                    }*/
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

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated

    }//GEN-LAST:event_formInternalFrameActivated

    private void cboMarcarSeleccionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMarcarSeleccionadosActionPerformed
        try {
            int[] selectedRows = masterTable.getSelectedRows();
            for (Integer i = 0; i < selectedRows.length; i++) {
                //list.get(i).setCobrado(true);
                masterTable.setValueAt(true, selectedRows[i], 4);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cboMarcarSeleccionadosActionPerformed

    private void cboFechaRemateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFechaRemateActionPerformed
        refresh();
    }//GEN-LAST:event_cboFechaRemateActionPerformed

    void refresh() {
        try {
            if (cboFechaRemate.getSelectedItem() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                entityManager.getTransaction().begin();
                query.setParameter("eventoId", (TblEventos) cboFechaRemate.getSelectedItem());
                java.util.List data = query.getResultList();
                for (Object entity : data) {
                    entityManager.refresh(entity);
                }
                list.clear();
                list.addAll(data);

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboFechaRemate;
    private javax.swing.JButton cboMarcarSeleccionados;
    private com.gnadenheimer.mg.utils.CtaCteTableCellRenderer ctaCteTableCellRenderer1;
    private com.gnadenheimer.mg.utils.DateTableCellRenderer dateTableCellRenderer2;
    private com.gnadenheimer.mg.utils.DateToStringConverter dateToStringConverter1;
    private javax.swing.JLabel descripcionLabel4;
    private javax.swing.JLabel descripcionLabel5;
    private com.github.lgooddatepicker.components.DatePicker dtpFechaCobro;
    private javax.persistence.EntityManager entityManager;
    private com.gnadenheimer.mg.utils.IntegerLongConverter integerLongConverter1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblTotal;
    private java.util.List<com.gnadenheimer.mg.domain.TblTransferencias> list;
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
            java.util.logging.Logger.getLogger(FrameCobrarTransferenciasRemates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameCobrarTransferenciasRemates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameCobrarTransferenciasRemates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameCobrarTransferenciasRemates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                frame.setContentPane(new FrameCobrarTransferenciasRemates());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
