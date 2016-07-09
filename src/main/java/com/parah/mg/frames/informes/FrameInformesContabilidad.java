/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames.informes;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.parah.mg.domain.miembros.TblEntidades;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
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

    TimePickerSettings timePickerSettings = new TimePickerSettings();
    TimePickerSettings timePickerSettings1 = new TimePickerSettings();
    TimePickerSettings timePickerSettings2 = new TimePickerSettings();
    TimePickerSettings timePickerSettings3 = new TimePickerSettings();
    TimePickerSettings timePickerSettings4 = new TimePickerSettings();
    TimePickerSettings timePickerSettings5 = new TimePickerSettings();
    TimePickerSettings timePickerSettings6 = new TimePickerSettings();
    TimePickerSettings timePickerSettings7 = new TimePickerSettings();
    TimePickerSettings timePickerSettings8 = new TimePickerSettings();

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
            datePickerSettings2.setFormatForDatesCommonEra("dd/MM/yyyy");
            datePickerSettings3.setFormatForDatesCommonEra("dd/MM/yyyy");
            datePickerSettings4.setFormatForDatesCommonEra("dd/MM/yyyy");
            datePickerSettings5.setFormatForDatesCommonEra("dd/MM/yyyy");
            datePickerSettings6.setFormatForDatesCommonEra("dd/MM/yyyy");
            datePickerSettings7.setFormatForDatesCommonEra("dd/MM/yyyy");

            timePickerSettings.setFormatForDisplayTime("HH:mm:ss");
            timePickerSettings1.setFormatForDisplayTime("HH:mm:ss");
            timePickerSettings2.setFormatForDisplayTime("HH:mm:ss");
            timePickerSettings3.setFormatForDisplayTime("HH:mm:ss");
            timePickerSettings4.setFormatForDisplayTime("HH:mm:ss");
            timePickerSettings5.setFormatForDisplayTime("HH:mm:ss");
            timePickerSettings6.setFormatForDisplayTime("HH:mm:ss");
            timePickerSettings7.setFormatForDisplayTime("HH:mm:ss");

            initComponents();

            LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endDate = LocalDateTime.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);
            txtFechaInicioDiario.setDateTimeStrict(startDate);
            txtFechaInicioMayor.setDateTimeStrict(startDate);
            txtFechaInicioCompras.setDateTimeStrict(startDate);
            txtFechaInicioVentas.setDateTimeStrict(startDate);

            txtFechaFinMayor.setDateTimeStrict(endDate);
            txtFechaFinDiario.setDateTimeStrict(endDate);
            txtFechaFinCompras.setDateTimeStrict(endDate);
            txtFechaFinVentas.setDateTimeStrict(endDate);
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
        jLabel5 = new javax.swing.JLabel();
        cmdLibroDiario = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmdLibroMayor = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cmdLibroMayor1 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cmdLibroVentas = new javax.swing.JButton();
        txtFechaInicioDiario = new com.github.lgooddatepicker.components.DateTimePicker(datePickerSettings, timePickerSettings);
        txtFechaFinDiario = new com.github.lgooddatepicker.components.DateTimePicker(datePickerSettings1, timePickerSettings1);
        txtFechaInicioMayor = new com.github.lgooddatepicker.components.DateTimePicker(datePickerSettings2, timePickerSettings2);
        txtFechaInicioCompras = new com.github.lgooddatepicker.components.DateTimePicker(datePickerSettings4, timePickerSettings4);
        txtFechaInicioVentas = new com.github.lgooddatepicker.components.DateTimePicker(datePickerSettings6, timePickerSettings6);
        txtFechaFinMayor = new com.github.lgooddatepicker.components.DateTimePicker(datePickerSettings3, timePickerSettings3);
        txtFechaFinCompras = new com.github.lgooddatepicker.components.DateTimePicker(datePickerSettings5, timePickerSettings5);
        txtFechaFinVentas = new com.github.lgooddatepicker.components.DateTimePicker(datePickerSettings7, timePickerSettings7);

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

        jLabel5.setText("Libro Diario");

        cmdLibroDiario.setText("Ver");
        cmdLibroDiario.setEnabled(false);
        cmdLibroDiario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroDiarioActionPerformed(evt);
            }
        });

        jLabel10.setText("Libro Mayor");

        jLabel11.setText("Desde");

        jLabel12.setText("Hasta");

        cmdLibroMayor.setText("Ver");
        cmdLibroMayor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroMayorActionPerformed(evt);
            }
        });

        jLabel13.setText("Desde");

        jLabel14.setText("Hasta");

        jLabel15.setText("Libro Compras");

        jLabel16.setText("Desde");

        jLabel17.setText("Hasta");

        cmdLibroMayor1.setText("Ver");
        cmdLibroMayor1.setEnabled(false);
        cmdLibroMayor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroMayor1ActionPerformed(evt);
            }
        });

        jLabel18.setText("Libro Ventas");

        jLabel19.setText("Desde");

        jLabel20.setText("Hasta");

        cmdLibroVentas.setText("Ver");
        cmdLibroVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLibroVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(jLabel15))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtFechaInicioDiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtFechaFinDiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmdLibroDiario))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtFechaInicioMayor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtFechaFinMayor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmdLibroMayor)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtFechaInicioVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtFechaFinVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtFechaInicioCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtFechaFinCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmdLibroMayor1))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdLibroVentas)))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmdLibroDiario)
                                .addComponent(jLabel5)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14))
                            .addComponent(txtFechaInicioDiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaFinDiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12)
                                .addComponent(cmdLibroMayor))
                            .addComponent(txtFechaInicioMayor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaFinMayor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(jLabel16)
                                .addComponent(jLabel17)
                                .addComponent(cmdLibroMayor1))
                            .addComponent(txtFechaInicioCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtFechaFinCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jLabel19)
                        .addComponent(jLabel20)
                        .addComponent(cmdLibroVentas))
                    .addComponent(txtFechaInicioVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaFinVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(517, Short.MAX_VALUE))
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
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaInicioMayor.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaFinMayor.getDateTimeStrict()));
            //parameters.put("ctaContable", 101020100);
            Utils.getInstance().showReport("libro_mayor", "libro_mayor_subreport", "libro_mayor_subreport_saldo_anterior", parameters, false);
            //Utils.getInstance().showReport("libro_mayor_subreport", "libro_mayor_subreport_saldo_anterior", parameters);
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }

    }//GEN-LAST:event_cmdLibroMayorActionPerformed

    private void cmdLibroMayor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLibroMayor1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdLibroMayor1ActionPerformed

    private void cmdLibroVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLibroVentasActionPerformed
        try {
            Map parameters = new HashMap();
            parameters.put("fechaDesde", Timestamp.valueOf(txtFechaInicioVentas.getDateTimeStrict()));
            parameters.put("fechaHasta", Timestamp.valueOf(txtFechaFinVentas.getDateTimeStrict()));

            Utils.getInstance().showReport("libro_ventas", parameters, true);

        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_cmdLibroVentasActionPerformed

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
    private javax.swing.JButton cmdLibroDiario;
    private javax.swing.JButton cmdLibroMayor;
    private javax.swing.JButton cmdLibroMayor1;
    private javax.swing.JButton cmdLibroVentas;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel5;
    private java.util.List<com.parah.mg.domain.TblEventos> listEventos;
    private java.util.List<com.parah.mg.domain.miembros.TblEntidades> listMiembros;
    private com.parah.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query queryEventos;
    private javax.persistence.Query queryMiembros;
    private com.github.lgooddatepicker.components.DateTimePicker txtFechaFinCompras;
    private com.github.lgooddatepicker.components.DateTimePicker txtFechaFinDiario;
    private com.github.lgooddatepicker.components.DateTimePicker txtFechaFinMayor;
    private com.github.lgooddatepicker.components.DateTimePicker txtFechaFinVentas;
    private com.github.lgooddatepicker.components.DateTimePicker txtFechaInicioCompras;
    private com.github.lgooddatepicker.components.DateTimePicker txtFechaInicioDiario;
    private com.github.lgooddatepicker.components.DateTimePicker txtFechaInicioMayor;
    private com.github.lgooddatepicker.components.DateTimePicker txtFechaInicioVentas;
    // End of variables declaration//GEN-END:variables
}
