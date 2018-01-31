/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.frames.informes;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.gnadenheimer.mg.domain.TblCentrosDeCosto;
import com.gnadenheimer.mg.domain.TblCuentasContables;
import com.gnadenheimer.mg.domain.miembros.TblEntidades;
import com.gnadenheimer.mg.domain.models.BalanceGeneral;
import com.gnadenheimer.mg.utils.CurrentUser;
import com.gnadenheimer.mg.utils.Utils;
import java.beans.Beans;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class FrameInformesContabilidad extends javax.swing.JInternalFrame {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    Map<String, String> persistenceMap = new HashMap<>();
    List<TblEntidades> listMiembrosFiltered;
    TblEntidades selectedMiembro;
    CurrentUser currentUser = CurrentUser.getInstance();
    DatePickerSettings datePickerSettings = new DatePickerSettings();
    DatePickerSettings datePickerSettings1 = new DatePickerSettings();
    DatePickerSettings datePickerSettings2 = new DatePickerSettings();
    DatePickerSettings datePickerSettings3 = new DatePickerSettings();
    DatePickerSettings datePickerSettings4 = new DatePickerSettings();
    DatePickerSettings datePickerSettings5 = new DatePickerSettings();
    DatePickerSettings datePickerSettings6 = new DatePickerSettings();
    DatePickerSettings datePickerSettings7 = new DatePickerSettings();
    DatePickerSettings datePickerSettings8 = new DatePickerSettings();
    DatePickerSettings datePickerSettings9 = new DatePickerSettings();
    DatePickerSettings datePickerSettings10 = new DatePickerSettings();

    TimePickerSettings timePickerSettings = new TimePickerSettings();
    TimePickerSettings timePickerSettings1 = new TimePickerSettings();
    TimePickerSettings timePickerSettings2 = new TimePickerSettings();
    TimePickerSettings timePickerSettings3 = new TimePickerSettings();
    TimePickerSettings timePickerSettings4 = new TimePickerSettings();
    TimePickerSettings timePickerSettings5 = new TimePickerSettings();
    TimePickerSettings timePickerSettings6 = new TimePickerSettings();
    TimePickerSettings timePickerSettings7 = new TimePickerSettings();
    TimePickerSettings timePickerSettings8 = new TimePickerSettings();
    TimePickerSettings timePickerSettings9 = new TimePickerSettings();
    TimePickerSettings timePickerSettings10 = new TimePickerSettings();

    /**
     * Creates new form FramePagos
     */
    public FrameInformesContabilidad() {

        super("Informes Contabilidad",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {

            persistenceMap = Utils.getInstance().getPersistenceMap();

            datePickerSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
            datePickerSettings1.setFormatForDatesCommonEra("dd/MM/yyyy");

            timePickerSettings.setFormatForDisplayTime("HH:mm:ss");
            timePickerSettings1.setFormatForDisplayTime("HH:mm:ss");

            initComponents();

            if (!Beans.isDesignTime()) {
                entityManager.getTransaction().begin();
            }

            AutoCompleteSupport support = AutoCompleteSupport.install(cboCentroDeCosto, GlazedLists.eventListOf(listCentrosDeCosto.toArray()));
            support.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support1 = AutoCompleteSupport.install(cboCentroDeCostoExtractoCtaCte, GlazedLists.eventListOf(listCentrosDeCosto.toArray()));
            support1.setFilterMode(TextMatcherEditor.CONTAINS);

            cboPeriodo.setSelectedIndex(0);

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

        entityManager = java.beans.Beans.isDesignTime() ? null : Persistence.createEntityManagerFactory("mg_PU", persistenceMap).createEntityManager();
        queryMiembros = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        listMiembros = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryMiembros.getResultList());
        queryEventos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventos t WHERE t.idEventoTipo.id = 1 AND t.idGrupo IN :grupos ORDER BY t.fecha");
        queryEventos.setParameter("grupos", currentUser.getUser().getTblGruposList());
        listEventos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventos.getResultList());
        queryCentrosDeCosto = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCentrosDeCosto t");
        listCentrosDeCosto = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryCentrosDeCosto.getResultList());
        jLabel5 = new javax.swing.JLabel();
        cmdLibroDiario = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cmdLibroMayor = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        cmdLibroCompras = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        cmdLibroVentas = new javax.swing.JButton();
        cmdLibroMayorSoloTotales = new javax.swing.JButton();
        cboCentroDeCosto = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cmdLibroMayorSoloTotalesCC = new javax.swing.JButton();
        cmdLibroMayorCC = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cboPeriodo = new javax.swing.JComboBox<>();
        txtFechaHasta = new com.github.lgooddatepicker.components.DateTimePicker(datePickerSettings1, timePickerSettings1);
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtFechaDesde = new com.github.lgooddatepicker.components.DateTimePicker(datePickerSettings, timePickerSettings);
        cboCentroDeCostoExtractoCtaCte = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmdExtractoCtaCteCC = new javax.swing.JButton();
        cmdExtractoCtaCte1 = new javax.swing.JButton();
        cmdExtractoCtaCte2 = new javax.swing.JButton();
        cmdExtractoCtaCte3 = new javax.swing.JButton();
        cmdExtractoCtaCteSaldos = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        cmdBalanceGeneral = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        cmdDDJJ121 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
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
        });

        jLabel5.setText("Libro Diario");

        cmdLibroDiario.setText("Ver");
        cmdLibroDiario.setEnabled(false);
        cmdLibroDiario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroDiarioActionPerformed(evt);
            }
        });

        jLabel10.setText("Libro Mayor");

        cmdLibroMayor.setText("Ver");
        cmdLibroMayor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroMayorActionPerformed(evt);
            }
        });

        jLabel15.setText("Libro Egresos");

        cmdLibroCompras.setText("Ver");
        cmdLibroCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroComprasActionPerformed(evt);
            }
        });

        jLabel18.setText("Libro Ingresos");

        cmdLibroVentas.setText("Ver");
        cmdLibroVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroVentasActionPerformed(evt);
            }
        });

        cmdLibroMayorSoloTotales.setText("Ver solo totales");
        cmdLibroMayorSoloTotales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroMayorSoloTotalesActionPerformed(evt);
            }
        });

        cboCentroDeCosto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Centro de Costo");

        cmdLibroMayorSoloTotalesCC.setText("Ver solo totales");
        cmdLibroMayorSoloTotalesCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroMayorSoloTotalesCCActionPerformed(evt);
            }
        });

        cmdLibroMayorCC.setText("Ver");
        cmdLibroMayorCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroMayorCCActionPerformed(evt);
            }
        });

        jLabel6.setText("Periodo:");

        cboPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Este año", "Este mes", "Hoy", "Año pasado" }));
        cboPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPeriodoActionPerformed(evt);
            }
        });

        jLabel21.setText("Extracto Cuenta Corriente");

        jLabel22.setText("Desde");

        jLabel23.setText("Hasta");

        cboCentroDeCostoExtractoCtaCte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Centro de Costo");

        cmdExtractoCtaCteCC.setText("Ver Detalle");
        cmdExtractoCtaCteCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExtractoCtaCteCCActionPerformed(evt);
            }
        });

        cmdExtractoCtaCte1.setText("Ver Detalle");
        cmdExtractoCtaCte1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExtractoCtaCte1ActionPerformed(evt);
            }
        });

        cmdExtractoCtaCte2.setText("Ver Resumen");
        cmdExtractoCtaCte2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExtractoCtaCte2ActionPerformed(evt);
            }
        });

        cmdExtractoCtaCte3.setText("Ver Resumen");
        cmdExtractoCtaCte3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExtractoCtaCte3ActionPerformed(evt);
            }
        });

        cmdExtractoCtaCteSaldos.setText("Ver Saldos");
        cmdExtractoCtaCteSaldos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExtractoCtaCteSaldosActionPerformed(evt);
            }
        });

        jLabel19.setText("Balance General");

        cmdBalanceGeneral.setText("Ver");
        cmdBalanceGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBalanceGeneralActionPerformed(evt);
            }
        });

        jLabel20.setText("DDJJ - IVA Semestral");

        cmdDDJJ121.setText("Ver");
        cmdDDJJ121.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDDJJ121ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(18, 18, 18)
                        .addComponent(txtFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmdDDJJ121)
                            .addComponent(cmdBalanceGeneral)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmdLibroMayor)
                                    .addComponent(cmdLibroDiario, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdLibroMayorSoloTotales)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(cboCentroDeCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmdLibroMayorCC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdLibroMayorSoloTotalesCC))
                            .addComponent(cmdLibroVentas)
                            .addComponent(cmdLibroCompras)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmdExtractoCtaCte1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdExtractoCtaCte2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdExtractoCtaCteSaldos)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboCentroDeCostoExtractoCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmdExtractoCtaCteCC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdExtractoCtaCte3)))))
                .addGap(99, 99, 99))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmdExtractoCtaCte1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmdLibroDiario))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cmdLibroMayorSoloTotales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmdLibroMayor))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cmdLibroMayorCC)
                                            .addComponent(cmdLibroMayorSoloTotalesCC))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cboCentroDeCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdLibroCompras)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdLibroVentas)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmdExtractoCtaCteCC)
                                        .addComponent(cmdExtractoCtaCte3))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cboCentroDeCostoExtractoCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmdExtractoCtaCte2)
                        .addComponent(cmdExtractoCtaCteSaldos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdBalanceGeneral))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdDDJJ121))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
    }//GEN-LAST:event_formInternalFrameActivated

    private void cmdLibroDiarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLibroDiarioActionPerformed

        try {
            Map parameters = new HashMap();
            Utils.getInstance().showReport("colectas_pendientes_por_mes", parameters, true);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdLibroDiarioActionPerformed

    private void cmdLibroMayorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLibroMayorActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            //parameters.put("ctaContable", 101020100);
            Utils.getInstance().showReport("libro_mayor", "libro_mayor_subreport", "libro_mayor_subreport_saldo_anterior", parameters, false);
            //Utils.getInstance().showReport("libro_mayor_subreport", "libro_mayor_subreport_saldo_anterior", parameters);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }

    }//GEN-LAST:event_cmdLibroMayorActionPerformed

    private void cmdLibroComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLibroComprasActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));

            Utils.getInstance().showReport("libro_egresos", parameters, true);

        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdLibroComprasActionPerformed

    private void cmdLibroVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLibroVentasActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));

            Utils.getInstance().showReport("libro_ingresos", parameters, true);

        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdLibroVentasActionPerformed

    private void cmdLibroMayorSoloTotalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLibroMayorSoloTotalesActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            //parameters.put("ctaContable", 101020100);
            Utils.getInstance().showReport("libro_mayor_solo_totales", "libro_mayor_solo_totales_subreport", "libro_mayor_solo_totales_subreport_saldo_anterior", parameters, false);
            //Utils.getInstance().showReport("libro_mayor_subreport", "libro_mayor_subreport_saldo_anterior", parameters);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdLibroMayorSoloTotalesActionPerformed

    private void cmdLibroMayorSoloTotalesCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLibroMayorSoloTotalesCCActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            parameters.put("centroDeCosto", ((TblCentrosDeCosto) cboCentroDeCosto.getSelectedItem()).getId());
            parameters.put("centroDeCostoNombre", ((TblCentrosDeCosto) cboCentroDeCosto.getSelectedItem()).getDescripcion());
            //parameters.put("ctaContable", 101020100);
            Utils.getInstance().showReport("libro_mayor_solo_totales_cc", "libro_mayor_solo_totales_subreport_cc", "libro_mayor_solo_totales_subreport_saldo_anterior_cc", parameters, false);
            //Utils.getInstance().showReport("libro_mayor_subreport", "libro_mayor_subreport_saldo_anterior", parameters);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdLibroMayorSoloTotalesCCActionPerformed

    private void cmdLibroMayorCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLibroMayorCCActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            parameters.put("centroDeCosto", ((TblCentrosDeCosto) cboCentroDeCosto.getSelectedItem()).getId());
            parameters.put("centroDeCostoNombre", ((TblCentrosDeCosto) cboCentroDeCosto.getSelectedItem()).getDescripcion());
            //parameters.put("ctaContable", 101020100);
            Utils.getInstance().showReport("libro_mayor_cc", "libro_mayor_subreport_cc", "libro_mayor_subreport_saldo_anterior_cc", parameters, false);
            //Utils.getInstance().showReport("libro_mayor_subreport", "libro_mayor_subreport_saldo_anterior", parameters);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdLibroMayorCCActionPerformed

    private void cboPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPeriodoActionPerformed
        try {

            LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endDate = LocalDateTime.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

            switch (cboPeriodo.getSelectedItem().toString()) {
                case "Este año":
                    startDate = LocalDateTime.now().withDayOfYear(1).toLocalDate().atStartOfDay();
                    endDate = startDate.plusYears(1).minusNanos(1);
                    break;
                case "Este mes":
                    startDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
                    endDate = startDate.plusMonths(1).minusNanos(1);
                    break;
                case "Hoy":
                    startDate = LocalDateTime.now().toLocalDate().atStartOfDay();
                    endDate = startDate.plusDays(1).minusNanos(1);
                    break;
                case "Año pasado":
                    startDate = LocalDateTime.now().withDayOfYear(1).minusYears(1).toLocalDate().atStartOfDay();
                    endDate = startDate.plusYears(1).minusNanos(1);
                    break;
            }

            txtFechaDesde.setDateTimeStrict(startDate);
            txtFechaHasta.setDateTimeStrict(endDate);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cboPeriodoActionPerformed

    private void cmdExtractoCtaCteCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExtractoCtaCteCCActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            parameters.put("centroDeCosto", ((TblCentrosDeCosto) cboCentroDeCostoExtractoCtaCte.getSelectedItem()).getId());
            parameters.put("centroDeCostoNombre", ((TblCentrosDeCosto) cboCentroDeCostoExtractoCtaCte.getSelectedItem()).getDescripcion());
            Utils.getInstance().showReport("extracto_ctacte_cc", "extracto_ctacte_subreport_saldo_anterior_cc", parameters, false);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdExtractoCtaCteCCActionPerformed

    private void cmdExtractoCtaCte1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExtractoCtaCte1ActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            Utils.getInstance().showReport("extracto_ctacte", "extracto_ctacte_subreport_saldo_anterior", parameters, false);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdExtractoCtaCte1ActionPerformed

    private void cmdExtractoCtaCte2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExtractoCtaCte2ActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            Utils.getInstance().showReport("extracto_ctacte_resumen", "extracto_ctacte_subreport_saldo_anterior", parameters, false);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdExtractoCtaCte2ActionPerformed

    private void cmdExtractoCtaCte3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExtractoCtaCte3ActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            parameters.put("centroDeCosto", ((TblCentrosDeCosto) cboCentroDeCostoExtractoCtaCte.getSelectedItem()).getId());
            parameters.put("centroDeCostoNombre", ((TblCentrosDeCosto) cboCentroDeCostoExtractoCtaCte.getSelectedItem()).getDescripcion());
            Utils.getInstance().showReport("extracto_ctacte_resumen_cc", "extracto_ctacte_subreport_saldo_anterior_cc", parameters, false);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdExtractoCtaCte3ActionPerformed

    private void cmdExtractoCtaCteSaldosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExtractoCtaCteSaldosActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            Utils.getInstance().showReport("extracto_ctacte_saldos", "extracto_ctacte_saldos_subreport", "extracto_ctacte_saldos_subreport_saldo_anterior", parameters, false);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdExtractoCtaCteSaldosActionPerformed

    private void cmdBalanceGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBalanceGeneralActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            Utils.getInstance().showReport("balance_general", parameters, false, getDataSourceBalanceGeneral(txtFechaDesde.getDateTimeStrict(), txtFechaHasta.getDateTimeStrict()));

            /*Utils.getInstance().showReport("balance_general", "balance_general_subreport", parameters, false);

            SELECT ID, DESCRIPCION, SUM(IMPORTE) AS IMPORTE FROM (
SELECT cc.ID, cc.DESCRIPCION, SUM(CAST(A.MONTO AS BIGINT)) AS IMPORTE
FROM TBL_ASIENTOS A, TBL_CUENTAS_CONTABLES cc
WHERE cc.ID = A.ID_CUENTA_CONTABLE_DEBE
AND cc.ID_CUENTA_MADRE = $P{ctaContable}
AND A.FECHAHORA BETWEEN $P{fechaDesde} AND $P{fechaHasta}
AND A.MONTO > 0
GROUP BY cc.ID, cc.DESCRIPCION
UNION ALL
SELECT cc.ID, cc.DESCRIPCION, -SUM(CAST(A.MONTO AS BIGINT)) AS IMPORTE
FROM TBL_ASIENTOS A, TBL_CUENTAS_CONTABLES cc
WHERE cc.ID = A.ID_CUENTA_CONTABLE_HABER
AND cc.ID_CUENTA_MADRE = $P{ctaContable}
AND A.FECHAHORA BETWEEN $P{fechaDesde} AND $P{fechaHasta}
AND A.MONTO > 0
GROUP BY cc.ID, cc.DESCRIPCION
UNION ALL
SELECT cc.ID, cc.DESCRIPCION, 0 AS IMPORTE
FROM TBL_CUENTAS_CONTABLES cc
WHERE cc.ID_CUENTA_MADRE = $P{ctaContable}
AND IMPUTABLE = FALSE) cuentas
GROUP BY ID, DESCRIPCION

             */
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdBalanceGeneralActionPerformed

    private static JRDataSource getDataSourceBalanceGeneral(LocalDateTime desdeFecha, LocalDateTime hastaFecha) {
        try {
            List<BalanceGeneral> balanceGeneralList = new ArrayList<>();
            Map<String, String> persistenceMap = Utils.getInstance().getPersistenceMap();
            EntityManager entityManager = Persistence.createEntityManagerFactory("mg_PU", persistenceMap).createEntityManager();
            entityManager.getTransaction().begin();

            List<TblCuentasContables> listCuentasMadre = (List<TblCuentasContables>) entityManager.createQuery("select t from TblCuentasContables t where t.idCuentaMadre = null and t.id <> 400000000").getResultList();

            listCuentasMadre.stream().forEach(cta -> {
                //List<BalanceGeneral> bgItemList = getSumCuentaContable(cta, entityManager, desdeFecha, hastaFecha, 1);
                //balanceGeneralList.addAll(bgItemList);
                Long sum1 = 0L;
                BalanceGeneral bg1 = new BalanceGeneral();
                bg1.setCuentaContable(cta);
                bg1.setNombreCuenta(cta.getDescripcion());
                bg1.setNroCuentaIndentada(cta.getId().toString());
                List<BalanceGeneral> bgItemListNivel2 = new ArrayList<>();
                for (TblCuentasContables cta2 : cta.getTblCuentasContablesList()) {
                    if (cta2.getImputable()) {
                        BalanceGeneral bg2 = getSumaCuentaImputable(cta2, entityManager, desdeFecha, hastaFecha, 2);
                        bgItemListNivel2.add(bg2);
                        sum1 += bg2.getImporte();
                    } else {
                        Long sum2 = 0L;
                        BalanceGeneral bg2 = new BalanceGeneral();
                        bg2.setCuentaContable(cta2);
                        bg2.setNombreCuenta(cta2.getDescripcion());
                        bg2.setNroCuentaIndentada(String.format("%2s", "") + cta2.getId().toString());
                        List<BalanceGeneral> bgItemListNivel3 = new ArrayList<>();
                        for (TblCuentasContables cta3 : cta2.getTblCuentasContablesList()) {
                            if (cta3.getImputable()) {
                                BalanceGeneral bg3 = getSumaCuentaImputable(cta3, entityManager, desdeFecha, hastaFecha, 3);
                                bgItemListNivel3.add(bg3);
                                sum2 += bg3.getImporte();
                            } else {
                                Long sum3 = 0L;
                                BalanceGeneral bg3 = new BalanceGeneral();
                                bg3.setCuentaContable(cta3);
                                bg3.setNombreCuenta(cta3.getDescripcion());
                                bg3.setNroCuentaIndentada(String.format("%4s", "") + cta3.getId().toString());
                                List<BalanceGeneral> bgItemListNivel4 = new ArrayList<>();
                                for (TblCuentasContables cta4 : cta3.getTblCuentasContablesList()) {
                                    if (cta4.getImputable()) {
                                        BalanceGeneral bg4 = getSumaCuentaImputable(cta4, entityManager, desdeFecha, hastaFecha, 4);
                                        bgItemListNivel4.add(bg4);
                                        sum3 += bg4.getImporte();
                                    } else {
                                        Long sum4 = 0L;
                                        BalanceGeneral bg4 = new BalanceGeneral();
                                        bg4.setCuentaContable(cta4);
                                        bg4.setNombreCuenta(cta4.getDescripcion());
                                        bg4.setNroCuentaIndentada(String.format("%6s", "") + cta4.getId().toString());
                                        List<BalanceGeneral> bgItemListNivel5 = new ArrayList<>();
                                        for (TblCuentasContables cta5 : cta4.getTblCuentasContablesList()) {
                                            BalanceGeneral bg5 = getSumaCuentaImputable(cta5, entityManager, desdeFecha, hastaFecha, 5);
                                            bgItemListNivel5.add(bg5);
                                            sum4 += bg5.getImporte();
                                        }
                                        bg4.setImporte(sum4);
                                        bgItemListNivel4.add(bg4);
                                        bgItemListNivel4.addAll(bgItemListNivel5);
                                        sum3 += sum4;
                                    }
                                }
                                bg3.setImporte(sum3);
                                bgItemListNivel3.add(bg3);
                                bgItemListNivel3.addAll(bgItemListNivel4);
                                sum2 += sum3;
                            }
                        }
                        bg2.setImporte(sum2);
                        bgItemListNivel2.add(bg2);
                        bgItemListNivel2.addAll(bgItemListNivel3);
                        sum1 += sum2;
                    }
                }
                bg1.setImporte(sum1);
                balanceGeneralList.add(bg1);
                balanceGeneralList.addAll(bgItemListNivel2);
            });

            entityManager.close();
            return new JRBeanCollectionDataSource(balanceGeneralList);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            return null;
        }
    }

    private static BalanceGeneral getSumaCuentaImputable(TblCuentasContables cta, EntityManager entityManager, LocalDateTime desdeFecha, LocalDateTime hastaFecha, Integer nivel) {
        BalanceGeneral bgItemImputable = new BalanceGeneral();
        Long importeImputable = (Long) entityManager.createNativeQuery("SELECT SUM(IMPORTE) FROM (SELECT SUM(CAST(A.MONTO AS BIGINT)) AS IMPORTE "
                + " FROM TBL_ASIENTOS A "
                + " WHERE A.ID_CUENTA_CONTABLE_DEBE = " + cta.getId().toString() + " AND A.FECHAHORA BETWEEN '" + desdeFecha.format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ") + "' AND '" + hastaFecha.format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ") + "'"
                + " UNION ALL"
                + " SELECT SUM(CAST(-A.MONTO AS BIGINT)) AS IMPORTE "
                + " FROM TBL_ASIENTOS A"
                + " WHERE A.ID_CUENTA_CONTABLE_HABER = " + cta.getId().toString() + " AND A.FECHAHORA BETWEEN '" + desdeFecha.format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ") + "' AND '" + hastaFecha.format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ") + "') S").getSingleResult();
        bgItemImputable.setCuentaContable(cta);
        bgItemImputable.setNroCuentaIndentada(String.format("%" + String.valueOf(nivel * 2) + "s", "") + cta.getId().toString());
        bgItemImputable.setNombreCuenta(cta.getDescripcion());
        if (importeImputable == null) {
            importeImputable = 0L;
        }
        bgItemImputable.setImporte(importeImputable);
        return bgItemImputable;
    }

    private static List<BalanceGeneral> getSumCuentaContable(TblCuentasContables cta, EntityManager entityManager, LocalDateTime desdeFecha, LocalDateTime hastaFecha, Integer nivel) {
        //try {
        List<BalanceGeneral> listItems = new ArrayList<>();
        if (cta.getImputable()) {
            BalanceGeneral bgItemImputable = new BalanceGeneral();
            Long importeImputable = (Long) entityManager.createNativeQuery("SELECT SUM(IMPORTE) FROM (SELECT SUM(CAST(A.MONTO AS BIGINT)) AS IMPORTE "
                    + " FROM TBL_ASIENTOS A "
                    + " WHERE A.ID_CUENTA_CONTABLE_DEBE = " + cta.getId().toString() + " AND A.FECHAHORA BETWEEN '" + desdeFecha.format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ") + "' AND '" + hastaFecha.format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ") + "'"
                    + " UNION ALL"
                    + " SELECT SUM(CAST(-A.MONTO AS BIGINT)) AS IMPORTE "
                    + " FROM TBL_ASIENTOS A"
                    + " WHERE A.ID_CUENTA_CONTABLE_HABER = " + cta.getId().toString() + " AND A.FECHAHORA BETWEEN '" + desdeFecha.format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ") + "' AND '" + hastaFecha.format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ") + "') S").getSingleResult();
            bgItemImputable.setCuentaContable(cta);
            bgItemImputable.setNroCuentaIndentada(String.format("%" + String.valueOf(nivel * 3) + "s", "") + cta.getId().toString());
            bgItemImputable.setNombreCuenta(cta.getDescripcion());
            if (importeImputable == null) {
                importeImputable = 0L;
            }
            bgItemImputable.setImporte(importeImputable);

            listItems.add(bgItemImputable);
        } else {
            Long sumImporte = 0L;
            for (TblCuentasContables cc : cta.getTblCuentasContablesList()) {
                List<BalanceGeneral> bgList = getSumCuentaContable(cc, entityManager, desdeFecha, hastaFecha, nivel + 1);
                listItems.addAll(bgList);
                sumImporte += bgList.stream().mapToLong(BalanceGeneral::getImporte).sum();
            }
            BalanceGeneral bgItem = new BalanceGeneral();
            bgItem.setCuentaContable(cta);
            bgItem.setNombreCuenta(cta.getDescripcion());
            bgItem.setNroCuentaIndentada(String.format("%" + String.valueOf(nivel * 3) + "s", "") + cta.getId().toString());
            bgItem.setImporte(sumImporte);

            listItems.add(bgItem);
        }
        return listItems;
        /* } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            return null;
        }*/
    }

    private void cmdDDJJ121ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDDJJ121ActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaDesde.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaHasta.getDateTimeStrict()));
            Utils.getInstance().showReport("ddjj-121", parameters, false);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdDDJJ121ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(FrameInformesContabilidad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameInformesContabilidad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameInformesContabilidad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameInformesContabilidad.class
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameInformesContabilidad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboCentroDeCosto;
    private javax.swing.JComboBox<String> cboCentroDeCostoExtractoCtaCte;
    private javax.swing.JComboBox<String> cboPeriodo;
    private javax.swing.JButton cmdBalanceGeneral;
    private javax.swing.JButton cmdDDJJ121;
    private javax.swing.JButton cmdExtractoCtaCte1;
    private javax.swing.JButton cmdExtractoCtaCte2;
    private javax.swing.JButton cmdExtractoCtaCte3;
    private javax.swing.JButton cmdExtractoCtaCteCC;
    private javax.swing.JButton cmdExtractoCtaCteSaldos;
    private javax.swing.JButton cmdLibroCompras;
    private javax.swing.JButton cmdLibroDiario;
    private javax.swing.JButton cmdLibroMayor;
    private javax.swing.JButton cmdLibroMayorCC;
    private javax.swing.JButton cmdLibroMayorSoloTotales;
    private javax.swing.JButton cmdLibroMayorSoloTotalesCC;
    private javax.swing.JButton cmdLibroVentas;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private java.util.List<com.gnadenheimer.mg.domain.TblCentrosDeCosto> listCentrosDeCosto;
    private java.util.List<com.gnadenheimer.mg.domain.TblEventos> listEventos;
    private java.util.List<com.gnadenheimer.mg.domain.miembros.TblEntidades> listMiembros;
    private javax.persistence.Query queryCentrosDeCosto;
    private javax.persistence.Query queryEventos;
    private javax.persistence.Query queryMiembros;
    private com.github.lgooddatepicker.components.DateTimePicker txtFechaDesde;
    private com.github.lgooddatepicker.components.DateTimePicker txtFechaHasta;
    // End of variables declaration//GEN-END:variables
}
