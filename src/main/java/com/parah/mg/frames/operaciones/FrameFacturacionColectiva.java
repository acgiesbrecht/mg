/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames.operaciones;

import com.parah.mg.domain.TblFacturas;
import com.parah.mg.domain.miembros.TblEntidades;
import com.parah.mg.domain.models.PagosRealizados;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import java.awt.EventQueue;
import java.beans.Beans;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public class FrameFacturacionColectiva extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameFacturacionColectiva.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    String databaseIP;
    Map<String, String> persistenceMap = new HashMap<>();

    public FrameFacturacionColectiva() {
        super("Facturacion Unica",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {

            persistenceMap = Utils.getInstance().getPersistenceMap();
            initComponents();
            if (!Beans.isDesignTime()) {
                entityManager.getTransaction().begin();
            }
            generate();
            if (masterTable.getRowCount() > 0) {
                imprimirButton.setEnabled(true);
            }

            TableFilterHeader filterHeader = new TableFilterHeader(masterTable, AutoChoices.ENABLED);
            filterHeader.setAdaptiveChoices(false);
            filterHeader.getParserModel().setIgnoreCase(true);
            filterHeader.setPosition(TableFilterHeader.Position.TOP);

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
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblFacturas t ORDER BY t.nro");
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        queryEntidades = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        listEntidades = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEntidades.getResultList());
        queryTimbrados = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblTimbrados t WHERE t.activo = true");
        listTimbrados = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryTimbrados.getResultList());
        rucTableCellRenderer1 = new com.parah.mg.utils.RucTableCellRenderer();
        numberCellRenderer1 = new com.parah.mg.utils.NumberCellRenderer();
        facturaNroTableCellRenderer1 = new com.parah.mg.utils.FacturaNroTableCellRenderer();
        cancelarButton = new javax.swing.JButton();
        imprimirButton = new javax.swing.JButton();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();

        FormListener formListener = new FormListener();

        rucTableCellRenderer1.setText("rucTableCellRenderer1");

        numberCellRenderer1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numberCellRenderer1.setText("numberCellRenderer1");

        facturaNroTableCellRenderer1.setText("facturaNroTableCellRenderer1");

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(formListener);

        imprimirButton.setText("Guardar & Imprimir");
        imprimirButton.addActionListener(formListener);

        masterTable.setAutoCreateRowSorter(true);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nro}"));
        columnBinding.setColumnName("Nro");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha/Hora");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idMiembro}"));
        columnBinding.setColumnName("Miembro");
        columnBinding.setColumnClass(com.parah.mg.domain.miembros.TblEntidades.class);
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
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${importeDonacion}"));
        columnBinding.setColumnName("Importe Donacion");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idTimbrado}"));
        columnBinding.setColumnName("Timbrado");
        columnBinding.setColumnClass(com.parah.mg.domain.TblTimbrados.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(0).setCellRenderer(facturaNroTableCellRenderer1);
            masterTable.getColumnModel().getColumn(4).setCellRenderer(rucTableCellRenderer1);
            masterTable.getColumnModel().getColumn(5).setCellRenderer(numberCellRenderer1);
            masterTable.getColumnModel().getColumn(6).setCellRenderer(numberCellRenderer1);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 585, Short.MAX_VALUE)
                        .addComponent(imprimirButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelarButton))
                    .addComponent(masterScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 837, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelarButton, imprimirButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(masterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelarButton)
                    .addComponent(imprimirButton))
                .addContainerGap())
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == cancelarButton) {
                FrameFacturacionColectiva.this.cancelarButtonActionPerformed(evt);
            }
            else if (evt.getSource() == imprimirButton) {
                FrameFacturacionColectiva.this.imprimirButtonActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("unchecked")
    private void imprimirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirButtonActionPerformed
        try {

            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            list.stream().forEach((factura) -> {
                //print((int) factura.getNro());
                Utils.getInstance().printFactura(factura);
            });
            java.util.Collection data = query.getResultList();
            data.stream().forEach((entity) -> {
                entityManager.refresh(entity);
            });
            list.clear();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_imprimirButtonActionPerformed

    void print(Integer nro) {
        try {
            //Connection conn = DriverManager.getConnection("jdbc:postgresql://" + databaseIP + ":5432/remate", "postgres", "123456");
            Connection conn = DriverManager.getConnection(persistenceMap.get("javax.persistence.jdbc.url"), persistenceMap.get("javax.persistence.jdbc.user"), persistenceMap.get("javax.persistence.jdbc.password"));
            Map parameters = new HashMap();
            parameters.put("factura_id", nro);
            /*parameters.put("logo", getClass().getResource("/reports/cclogo200.png").getPath());
             parameters.put("logo", getClass().getResourceAsStream("/reports/cclogo200.png"));
             parameters.put("logo2", getClass().getResourceAsStream("/reports/cclogo200.png"));
             parameters.put("logo3", getClass().getResourceAsStream("/reports/cclogo200.png"));*/
            //JOptionPane.showMessageDialog(null, getClass().getResource("/reports/cclogo200.png").getPath());
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/factura.jrxml"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            //JasperViewer jReportsViewer = new JasperViewer(jasperPrint, false);
            //jReportsViewer.setVisible(true);
            JasperPrintManager.printReport(jasperPrint, false);

        } catch (SQLException | JRException ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarButtonActionPerformed
        try {
            this.setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cancelarButtonActionPerformed

    private void generate() {
        try {
            list.clear();
            list.addAll(query.getResultList());
            int siguienteFacturaNro;
            if (list.size() > 0) {
                siguienteFacturaNro = list.get(list.size() - 1).getNro() + 1;
            } else {
                siguienteFacturaNro = 1;
            }

            list.clear();
            Calendar cal = Calendar.getInstance();
            String ano = String.valueOf(cal.get(Calendar.YEAR));
            List<PagosRealizados> pagosRealizados = entityManager.createNativeQuery("SELECT m.id AS ID, "
                    + " COALESCE(transferencias.t_aporte,0) AS T_APORTE, "
                    + " COALESCE(transferencias.t_donacion,0) AS T_DONACION, "
                    + " COALESCE(recibos.r_aporte,0) AS R_APORTE,"
                    + " COALESCE(recibos.r_donacion,0) AS R_DONACION,"
                    + " COALESCE(facturas.f_aporte,0) AS F_APORTE,"
                    + " COALESCE(facturas.f_donacion,0) AS F_DONACION"
                    + " FROM TBL_ENTIDADES m"
                    + "     LEFT JOIN (SELECT m.id, COALESCE(SUM(t.MONTO_APORTE),0) AS t_aporte,"
                    + "     COALESCE(SUM(t.MONTO_DONACION),0) AS t_donacion"
                    + "     FROM TBL_ENTIDADES m"
                    + "     LEFT JOIN TBL_TRANSFERENCIAS t ON m.id = t.id_entidad "
                    + "     WHERE YEAR(t.fechahora) >= " + ano
                    + "     GROUP BY m.id"
                    + "	) transferencias ON m.id = transferencias.id"
                    + "     LEFT JOIN (SELECT m.id, COALESCE(SUM(r.MONTO_APORTE),0) AS r_aporte,"
                    + "     COALESCE(SUM(r.MONTO_DONACION),0) AS r_donacion "
                    + "     FROM TBL_ENTIDADES m"
                    + "     LEFT JOIN TBL_RECIBOS r ON m.id = r.id_entidad "
                    + "     WHERE YEAR(r.fechahora) >= " + ano
                    + "     GROUP BY m.id"
                    + "	) recibos ON m.id = recibos.id"
                    + "     LEFT JOIN (SELECT m.id, COALESCE(SUM(f.importe_aporte),0) AS f_aporte, 		"
                    + "     COALESCE(SUM(f.importe_donacion),0) AS f_donacion 		"
                    + "     FROM TBL_ENTIDADES m"
                    + "     LEFT JOIN TBL_FACTURAS f ON m.id = f.id_entidad "
                    + "     WHERE YEAR(f.fechahora) >= " + ano
                    + "     AND f.anulado = false"
                    + "     GROUP BY m.id"
                    + "	) facturas ON m.id = facturas.id", PagosRealizados.class).getResultList();
            TblEntidades m;
            for (PagosRealizados pr : pagosRealizados) {

                m = entityManager.find(TblEntidades.class, pr.getId());
                if ((pr.getRDonacion() + pr.getTDonacion() - pr.getFDonacion()) > 0 || (pr.getRAporte() + pr.getTAporte() - pr.getFAporte()) > 0) {
                    TblFacturas f = new TblFacturas();
                    entityManager.persist(f);
                    f.setNro(siguienteFacturaNro);
                    f.setIdTimbrado(listTimbrados.get(0));
                    f.setFechahora(new Date());
                    f.setIdEntidad(m);
                    f.setRazonSocial(m.getNombres() + " " + m.getApellidos());
                    if (m.getRucSinDv() != null) {
                        f.setRuc(m.getRucSinDv());
                    } else {
                        f.setRuc("44444401");
                    }
                    f.setAnulado(false);
                    f.setImporteAporte(pr.getRAporte() + pr.getTAporte() - pr.getFAporte());
                    f.setImporteDonacion(pr.getRDonacion() + pr.getTDonacion() - pr.getFDonacion());
                    f.setIdUser(currentUser.getUser());
                    list.add(f);
                    int row = list.size() - 1;
                    masterTable.setRowSelectionInterval(row, row);
                    masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));
                    siguienteFacturaNro++;
                }
            }
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelarButton;
    private javax.persistence.EntityManager entityManager;
    private com.parah.mg.utils.FacturaNroTableCellRenderer facturaNroTableCellRenderer1;
    private javax.swing.JButton imprimirButton;
    private java.util.List<com.parah.mg.domain.TblFacturas> list;
    private java.util.List<com.parah.mg.domain.miembros.TblEntidades> listEntidades;
    private java.util.List<com.parah.mg.domain.TblTimbrados> listTimbrados;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private com.parah.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query query;
    private javax.persistence.Query queryEntidades;
    private javax.persistence.Query queryTimbrados;
    private com.parah.mg.utils.RucTableCellRenderer rucTableCellRenderer1;
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
            java.util.logging.Logger.getLogger(FrameFacturacionColectiva.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameFacturacionColectiva.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameFacturacionColectiva.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameFacturacionColectiva.class
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
                frame.setContentPane(new FrameFacturacionColectiva());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
