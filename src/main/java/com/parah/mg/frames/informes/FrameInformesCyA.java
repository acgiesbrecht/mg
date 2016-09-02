/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames.informes;

import com.parah.mg.domain.miembros.TblEntidades;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class FrameInformesCyA extends javax.swing.JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameInformesCyA.class);
    Map<String, String> persistenceMap = new HashMap<>();
    List<TblEntidades> listMiembrosFiltered;
    TblEntidades selectedMiembro;
    CurrentUser currentUser = CurrentUser.getInstance();
    
    /**
     * Creates new form FramePagos
     */
    public FrameInformesCyA() {

        super("Informes Aportes y Colectas",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {

            persistenceMap = Utils.getInstance().getPersistenceMap();
            initComponents();

            jspAno.setValue(Calendar.getInstance().get(Calendar.YEAR));
            cboMes.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));

            jspAnoA.setValue(Calendar.getInstance().get(Calendar.YEAR));
            cboMesA.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));

            jspAnoDebitoManual.setValue(Calendar.getInstance().get(Calendar.YEAR));
            jspAnoAportesResumen.setValue(Calendar.getInstance().get(Calendar.YEAR));

            jspAnoCP.setValue(Calendar.getInstance().get(Calendar.YEAR));
            cboMesCP.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));

            jspAnoDebitoManual.setValue(Calendar.getInstance().get(Calendar.YEAR));
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
        dateToStringConverter1 = new com.parah.mg.utils.DateToStringConverter();
        queryMiembros = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        listMiembros = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryMiembros.getResultList());
        dateTimeTableCellRenderer1 = new com.parah.mg.utils.DateTimeTableCellRenderer();
        numberCellRenderer1 = new com.parah.mg.utils.NumberCellRenderer();
        queryEventos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventos t WHERE t.idEventoTipo.id = 1 AND t.idGrupo IN :grupos ORDER BY t.fecha");
        queryEventos.setParameter("grupos", currentUser.getUser().getTblGruposList());
        listEventos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventos.getResultList());
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboMes = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jspAno = new javax.swing.JSpinner();
        cmdResumenPorMes = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cmdPendientesPorMes = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cboMesA = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jspAnoA = new javax.swing.JSpinner();
        cmdResumenPorMesA = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cmdPendientesPorMesA = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jspAnoDebitoManual = new javax.swing.JSpinner();
        cmdAportesDebitoManual = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jspAnoAportesResumen = new javax.swing.JSpinner();
        cmdAportesResumen = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cboMesCP = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jspAnoCP = new javax.swing.JSpinner();
        cmdResumenCP = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cboMesCPAD = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jspAnoCPAD = new javax.swing.JSpinner();
        cmdResumenCP1 = new javax.swing.JButton();

        dateTimeTableCellRenderer1.setText("dateTimeTableCellRenderer1");

        numberCellRenderer1.setText("numberCellRenderer1");

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

        jLabel2.setText("Resumen de Colectas por Mes:");

        jLabel3.setText("Mes:");

        cboMes.setEditable(true);
        cboMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        jLabel4.setText("Año:");

        cmdResumenPorMes.setText("Ver");
        cmdResumenPorMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdResumenPorMesActionPerformed(evt);
            }
        });

        jLabel5.setText("Colectas pendientes de cobro:");

        cmdPendientesPorMes.setText("Ver");
        cmdPendientesPorMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPendientesPorMesActionPerformed(evt);
            }
        });

        jLabel6.setText("Mes:");

        cboMesA.setEditable(true);
        cboMesA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        jLabel7.setText("Año:");

        cmdResumenPorMesA.setText("Ver");
        cmdResumenPorMesA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdResumenPorMesAActionPerformed(evt);
            }
        });

        jLabel8.setText("Aportes pendientes de cobro:");

        cmdPendientesPorMesA.setText("Ver");
        cmdPendientesPorMesA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPendientesPorMesAActionPerformed(evt);
            }
        });

        jLabel9.setText("Resumen de Aportes por Mes:");

        jLabel10.setText("Resumen Anual de Aportes sin Debito Automatico:");

        jLabel12.setText("Año:");

        cmdAportesDebitoManual.setText("Ver");
        cmdAportesDebitoManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAportesDebitoManualActionPerformed(evt);
            }
        });

        jLabel11.setText("Resumen Anual de Aportes:");

        jLabel13.setText("Año:");

        cmdAportesResumen.setText("Ver");
        cmdAportesResumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAportesResumenActionPerformed(evt);
            }
        });

        jLabel14.setText("Resumen de Compramisos/Pagos por Mes:");

        jLabel15.setText("Mes:");

        cboMesCP.setEditable(true);
        cboMesCP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        jLabel16.setText("Año:");

        cmdResumenCP.setText("Ver");
        cmdResumenCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdResumenCPActionPerformed(evt);
            }
        });

        jLabel17.setText("Detalle de Compramisos/Pagos por Mes:");

        jLabel18.setText("Mes:");

        cboMesCPAD.setEditable(true);
        cboMesCPAD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        jLabel19.setText("Año:");

        cmdResumenCP1.setText("Ver");
        cmdResumenCP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdResumenCP1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdPendientesPorMes))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(12, 12, 12)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jspAno, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmdResumenPorMes)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboMesCP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jspAnoCP, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdResumenCP))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jspAnoAportesResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmdAportesResumen))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jspAnoDebitoManual, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmdAportesDebitoManual))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdPendientesPorMesA))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(12, 12, 12)
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cboMesA, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jspAnoA, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmdResumenPorMesA)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboMesCPAD, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jspAnoCPAD, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmdResumenCP1)))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdPendientesPorMes)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(cboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jspAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdResumenPorMes))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdPendientesPorMesA)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(cboMesA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jspAnoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdResumenPorMesA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jspAnoDebitoManual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdAportesDebitoManual))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jspAnoAportesResumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdAportesResumen))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(cboMesCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jspAnoCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdResumenCP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(cboMesCPAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jspAnoCPAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdResumenCP1))
                .addContainerGap(314, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
    }//GEN-LAST:event_formInternalFrameActivated

    private Connection getConnection() {
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("mg_PU", persistenceMap).createEntityManager();
            String url = persistenceMap.get("javax.persistence.jdbc.url");
            String user = persistenceMap.get("javax.persistence.jdbc.user");
            String pass = persistenceMap.get("javax.persistence.jdbc.password");
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            return null;
        }
    }

    private void cmdResumenPorMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdResumenPorMesActionPerformed
        try {
            if (Integer.parseInt(cboMes.getSelectedItem().toString()) > 0 && Integer.parseInt(jspAno.getValue().toString()) > 0) {
                Map parameters = new HashMap();
                parameters.put("mes", Integer.parseInt(cboMes.getSelectedItem().toString()));
                parameters.put("ano", Integer.parseInt(jspAno.getValue().toString()));
                Utils.getInstance().showReport("colectas_por_mes", parameters, true);
            }
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdResumenPorMesActionPerformed

    private void cmdPendientesPorMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPendientesPorMesActionPerformed

        try {
            Map parameters = new HashMap();
            Utils.getInstance().showReport("colectas_pendientes_por_mes", parameters, false);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdPendientesPorMesActionPerformed

    private void cmdResumenPorMesAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdResumenPorMesAActionPerformed
        try {
            if (Integer.parseInt(cboMesA.getSelectedItem().toString()) > 0 && Integer.parseInt(jspAnoA.getValue().toString()) > 0) {
                Map parameters = new HashMap();
                parameters.put("pMes", Integer.parseInt(cboMesA.getSelectedItem().toString()));
                parameters.put("pAno", Integer.parseInt(jspAnoA.getValue().toString()));
                Utils.getInstance().showReport("aportes_por_mes", parameters, false);
            }
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdResumenPorMesAActionPerformed

    private void cmdPendientesPorMesAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPendientesPorMesAActionPerformed
        try {
            Map parameters = new HashMap();
            Utils.getInstance().showReport("aportes_pendientes_por_mes", parameters, false);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdPendientesPorMesAActionPerformed

    private void cmdAportesDebitoManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAportesDebitoManualActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("ano", Integer.parseInt(jspAnoDebitoManual.getValue().toString()));
            Utils.getInstance().showReport("aportes_resumen_sin_debito_automatico", "aportes_resumen_sin_debito_automatico_subreport", parameters, true);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdAportesDebitoManualActionPerformed

    private void cmdAportesResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAportesResumenActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("ano", Integer.parseInt(jspAnoAportesResumen.getValue().toString()));
            Utils.getInstance().showReport("aportes_resumen_anual", "aportes_resumen_anual_subreport", parameters, true);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdAportesResumenActionPerformed

    private void cmdResumenCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdResumenCPActionPerformed
        try {
            if (Integer.parseInt(cboMesCP.getSelectedItem().toString()) > 0 && Integer.parseInt(jspAnoCP.getValue().toString()) > 0) {
                Map parameters = new HashMap();

                parameters.put("pMes", Integer.parseInt(cboMesCP.getSelectedItem().toString()));
                parameters.put("pAno", Integer.parseInt(jspAnoCP.getValue().toString()));
                Utils.getInstance().showReport("aportes_colectas_resumen_compromisos_pagos_por_mes", parameters, true);
            }
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdResumenCPActionPerformed

    private void cmdResumenCP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdResumenCP1ActionPerformed
        try {
            if (Integer.parseInt(cboMesCPAD.getSelectedItem().toString()) > 0 && Integer.parseInt(jspAnoCPAD.getValue().toString()) > 0) {
                Map parameters = new HashMap();

                parameters.put("pMes", Integer.parseInt(cboMesCPAD.getSelectedItem().toString()));
                parameters.put("pAno", Integer.parseInt(jspAnoCPAD.getValue().toString()));
                Utils.getInstance().showReport("aportes_colectas_resumen_compromisos_ad_pagos_por_mes", parameters, true);
            }
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdResumenCP1ActionPerformed

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
            java.util.logging.Logger.getLogger(FrameInformesCyA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameInformesCyA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameInformesCyA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameInformesCyA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameInformesCyA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboMes;
    private javax.swing.JComboBox<String> cboMesA;
    private javax.swing.JComboBox<String> cboMesCP;
    private javax.swing.JComboBox<String> cboMesCPAD;
    private javax.swing.JButton cmdAportesDebitoManual;
    private javax.swing.JButton cmdAportesResumen;
    private javax.swing.JButton cmdPendientesPorMes;
    private javax.swing.JButton cmdPendientesPorMesA;
    private javax.swing.JButton cmdResumenCP;
    private javax.swing.JButton cmdResumenCP1;
    private javax.swing.JButton cmdResumenPorMes;
    private javax.swing.JButton cmdResumenPorMesA;
    private com.parah.mg.utils.DateTimeTableCellRenderer dateTimeTableCellRenderer1;
    private com.parah.mg.utils.DateToStringConverter dateToStringConverter1;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner jspAno;
    private javax.swing.JSpinner jspAnoA;
    private javax.swing.JSpinner jspAnoAportesResumen;
    private javax.swing.JSpinner jspAnoCP;
    private javax.swing.JSpinner jspAnoCPAD;
    private javax.swing.JSpinner jspAnoDebitoManual;
    private java.util.List<com.parah.mg.domain.TblEventos> listEventos;
    private java.util.List<com.parah.mg.domain.miembros.TblEntidades> listMiembros;
    private com.parah.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query queryEventos;
    private javax.persistence.Query queryMiembros;
    // End of variables declaration//GEN-END:variables
}
