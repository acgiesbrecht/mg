/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames.admin;

import com.parah.mg.domain.TblAsientos;
import com.parah.mg.domain.TblCuentasContablesPorDefecto;
import com.parah.mg.domain.TblEventoDetalle;
import com.parah.mg.domain.TblFacturas;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.prefs.Preferences;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Industria
 */
public class FrameConfigAdmin extends javax.swing.JInternalFrame {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(FrameConfigAdmin.class);

    public FrameConfigAdmin() {
        super("Configuracion",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {
            initComponents();

            final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

            if (jarFile.isFile()) {  // Run with JAR file
                final JarFile jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
                while (entries.hasMoreElements()) {
                    final String name = entries.nextElement().getName();
                    if (name.startsWith("sql/")) { //filter according to the path
                        cboSqlFiles.addItem("/" + name);
                    }
                }
                jar.close();
            } else { // Run with IDE
                final URL url = getClass().getResource("/sql");
                if (url != null) {
                    try {
                        final File apps = new File(url.toURI());
                        for (File app : apps.listFiles()) {
                            cboSqlFiles.addItem("/sql/" + app.getName());
                        }
                    } catch (URISyntaxException ex) {
                        // never happens
                    }
                }
            }
            /*
            File[] files = (new File(getClass().getResource("/sql").toURI())).listFiles();
            for (File f : files) {
                cboSqlFiles.addItem(f.getName());
            }*/
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtIP = new javax.swing.JTextField();
        rbServidor = new javax.swing.JRadioButton();
        rbRemoto = new javax.swing.JRadioButton();
        panelDatadir = new java.awt.Panel();
        txtDatadir = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmdDatadir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cboModoImpresion = new javax.swing.JComboBox();
        cmdReset = new javax.swing.JButton();
        txtFacturaX = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtFacturaY = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmdFacturaPrintTest = new javax.swing.JButton();
        cboSqlFiles = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cboFormatoFactura = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();

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

        jLabel2.setText("Direccion IP de Base de datos:");

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbServidor);
        rbServidor.setText("Servidor");
        rbServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbServidorActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbRemoto);
        rbRemoto.setSelected(true);
        rbRemoto.setText("Remoto");
        rbRemoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRemotoActionPerformed(evt);
            }
        });

        txtDatadir.setText("C:\\mgdb");

        jLabel3.setText("Carpeta de Base de datos:");

        cmdDatadir.setText("...");
        cmdDatadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDatadirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDatadirLayout = new javax.swing.GroupLayout(panelDatadir);
        panelDatadir.setLayout(panelDatadirLayout);
        panelDatadirLayout.setHorizontalGroup(
            panelDatadirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatadirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(38, 38, 38)
                .addComponent(txtDatadir, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdDatadir)
                .addGap(17, 17, 17))
        );
        panelDatadirLayout.setVerticalGroup(
            panelDatadirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatadirLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDatadirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDatadir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdDatadir))
                .addContainerGap())
        );

        jLabel4.setText("Impresion de Transferencias");

        cboModoImpresion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Triplicado" }));

        cmdReset.setBackground(new java.awt.Color(255, 102, 102));
        cmdReset.setText("Actualizar Base");
        cmdReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdResetActionPerformed(evt);
            }
        });

        txtFacturaX.setText("0");
        txtFacturaX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFacturaXKeyReleased(evt);
            }
        });

        jLabel5.setText("Ajuste Factura Izquierda:");

        txtFacturaY.setText("0");
        txtFacturaY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFacturaYKeyReleased(evt);
            }
        });

        jLabel6.setText("Ajuste Factura Superior:");

        cmdFacturaPrintTest.setText("Impresion de Prueba");
        cmdFacturaPrintTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFacturaPrintTestActionPerformed(evt);
            }
        });

        jLabel7.setText("Impresion de Facturas");

        cboFormatoFactura.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Preimpreso con rejilla", "Preimpreso sin rejilla" }));

        jButton3.setBackground(new java.awt.Color(255, 0, 153));
        jButton3.setText("Actualizar Asientos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtIP, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                        .addGap(275, 275, 275))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmdFacturaPrintTest, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtFacturaY, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton1))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtFacturaX, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addComponent(panelDatadir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbServidor)
                                .addGap(18, 18, 18)
                                .addComponent(rbRemoto))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboFormatoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboModoImpresion, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cboSqlFiles, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmdReset, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jButton3))
                            .addComponent(jLabel7))
                        .addGap(0, 197, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbServidor)
                    .addComponent(rbRemoto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatadir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboModoImpresion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboFormatoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFacturaX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFacturaY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdFacturaPrintTest)
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboSqlFiles, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
//            prefs.put("PrinterName", cboPrinter.getSelectedItem().toString());
            Preferences.userRoot().node("MG").put("DatabaseIP", txtIP.getText());
            Preferences.userRoot().node("MG").put("Datadir", txtDatadir.getText());
            Preferences.userRoot().node("MG").put("isServer", String.valueOf(rbServidor.isSelected()));
            Preferences.userRoot().node("MG").put("modoImpresion", cboModoImpresion.getSelectedItem().toString());
            Preferences.userRoot().node("MG").put("formatoFactura", cboFormatoFactura.getSelectedItem().toString());
            Preferences.userRoot().node("MG").put("facturaLeftMargin", txtFacturaX.getText());
            Preferences.userRoot().node("MG").put("facturaTopMargin", txtFacturaY.getText());

            this.setVisible(false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        txtIP.setText(Preferences.userRoot().node("MG").get("DatabaseIP", "127.0.0.1"));
        txtDatadir.setText(Preferences.userRoot().node("MG").get("Datadir", "C:\\javadb"));
        rbServidor.setSelected(Boolean.parseBoolean(Preferences.userRoot().node("MG").get("isServer", "true")));
        cboModoImpresion.setSelectedItem(Preferences.userRoot().node("MG").get("modoImpresion", "Normal"));
        cboFormatoFactura.setSelectedItem(Preferences.userRoot().node("MG").get("formateFactura", "Preimpreso sin rejilla"));
        txtFacturaX.setText(Preferences.userRoot().node("MG").get("facturaLeftMargin", "0"));
        txtFacturaY.setText(Preferences.userRoot().node("MG").get("facturaTopMargin", "0"));

        Preferences.userRoot().node("MG").put("facturaTopMargin", txtFacturaY.getText());
    }//GEN-LAST:event_formInternalFrameActivated

    private void cmdDatadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDatadirActionPerformed
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Eligir ubicación de la base de datos.");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                txtDatadir.setText(chooser.getSelectedFile().getPath());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cmdDatadirActionPerformed

    private void rbServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbServidorActionPerformed
        panelDatadir.setVisible(rbServidor.isSelected());
    }//GEN-LAST:event_rbServidorActionPerformed

    private void rbRemotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRemotoActionPerformed
        panelDatadir.setVisible(rbServidor.isSelected());
    }//GEN-LAST:event_rbRemotoActionPerformed

    private void cmdResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdResetActionPerformed
        try {

            int reply = JOptionPane.showConfirmDialog(null, "Realmente desea borrar todos los datos y limpiar la base de datos?", title, JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Utils.getInstance().executeSQL(cboSqlFiles.getSelectedItem().toString());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cmdResetActionPerformed

    private void cmdFacturaPrintTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdFacturaPrintTestActionPerformed
        try {
            TblFacturas testF = new TblFacturas();
            testF.setNro(1234567);
            testF.setFechahora(new java.sql.Date((new Date()).getTime()));
            testF.setRazonSocial("Empresa SA");
            testF.setRuc("8888888");
            testF.setDomicilio("Loma Plata");
            testF.setCasillaDeCorreo(1158);
            testF.setIdUser(CurrentUser.getInstance().getUser());
            testF.setImporteAporte(15000000);
            testF.setImporteDonacion(25000000);

            Utils.getInstance().printFactura(testF);

            /*
             Map parameters = new HashMap();
             parameters.put("factura_id", 1324567);
             parameters.put("fechahora", new java.sql.Date((new Date()).getTime()));
             parameters.put("razon_social", "Empresa SA");
             parameters.put("ruc", 88888888);
             parameters.put("importe_aporte", 1250000);
             parameters.put("importe_donacion", 10000000);

             parameters.put("logo", getClass().getResourceAsStream("/reports/cclogo200.png"));
             parameters.put("logo2", getClass().getResourceAsStream("/reports/cclogo200.png"));
             parameters.put("logo3", getClass().getResourceAsStream("/reports/cclogo200.png"));
             //JOptionPane.showMessageDialog(null, getClass().getResource("/reports/cclogo200.png").getPath());
             JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/factura.jrxml"));

             JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
             //JasperViewer jReportsViewer = new JasperViewer(jasperPrint, false);
             //jReportsViewer.setVisible(true);
             jasperPrint.setLeftMargin(Integer.getInteger(txtFacturaX.getText()));
             jasperPrint.setTopMargin(Integer.getInteger(txtFacturaY.getText()));
             JasperPrintManager.printReport(jasperPrint, false);
             */
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cmdFacturaPrintTestActionPerformed

    private void txtFacturaXKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFacturaXKeyReleased
        try {
            Preferences.userRoot().node("MG").put("facturaLeftMargin", txtFacturaX.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_txtFacturaXKeyReleased

    private void txtFacturaYKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFacturaYKeyReleased
        try {
            Preferences.userRoot().node("MG").put("facturaTopMargin", txtFacturaY.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_txtFacturaYKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            CurrentUser currentUser = CurrentUser.getInstance();
            Map<String, String> persistenceMap = Utils.getInstance().getPersistenceMap();
            EntityManager entityManager = Persistence.createEntityManagerFactory("mg_PU", persistenceMap).createEntityManager();

            Query queryEventoDetalle = entityManager.createQuery("SELECT t FROM TblEventoDetalle t ORDER BY t.id");
            List<TblEventoDetalle> listEventoDetalle = org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventoDetalle.getResultList());
            TblCuentasContablesPorDefecto cuentasContablesPorDefecto = entityManager.find(TblCuentasContablesPorDefecto.class, 1);
            for (TblEventoDetalle evd : listEventoDetalle) {
                if (entityManager.contains(evd)) {
                    if (evd.getTblAsientosCollection().size() == 2) {
                        ((List<TblAsientos>) evd.getTblAsientosCollection()).get(0).setMonto(evd.getMonto() * evd.getIdEvento().getPorcentajeAporte() / 100);
                        ((List<TblAsientos>) evd.getTblAsientosCollection()).get(1).setMonto(evd.getMonto() - ((List<TblAsientos>) evd.getTblAsientosCollection()).get(0).getMonto());
                        entityManager.merge(evd);
                    } else if (evd.getTblAsientosCollection().isEmpty()) {

                        Collection<TblAsientos> ts = evd.getTblAsientosCollection();
                        if (ts == null) {
                            ts = new LinkedList<>();
                            evd.setTblAsientosCollection((List) ts);
                        }
                        TblAsientos asientoAporte = new TblAsientos();
                        asientoAporte.setFechahora(evd.getIdEvento().getFecha());
                        asientoAporte.setIdCentroDeCosto(evd.getIdEvento().getIdCentroDeCosto());
                        asientoAporte.setIdCuentaContableDebe(cuentasContablesPorDefecto.getIdCuentaACobrar());
                        asientoAporte.setIdCuentaContableHaber(cuentasContablesPorDefecto.getIdCuentaAportes());
                        asientoAporte.setMonto(evd.getMonto() * evd.getIdEvento().getPorcentajeAporte() / 100);
                        asientoAporte.setIdUser(currentUser.getUser());

                        ts.add(asientoAporte);

                        TblAsientos asientoDonacion = new TblAsientos();
                        asientoDonacion.setFechahora(evd.getIdEvento().getFecha());
                        asientoDonacion.setIdCentroDeCosto(evd.getIdEvento().getIdCentroDeCosto());
                        asientoDonacion.setIdCuentaContableDebe(cuentasContablesPorDefecto.getIdCuentaACobrar());
                        asientoDonacion.setIdCuentaContableHaber(cuentasContablesPorDefecto.getIdCuentaDonaciones());
                        asientoDonacion.setMonto(evd.getMonto() - ((List<TblAsientos>) evd.getTblAsientosCollection()).get(0).getMonto());
                        asientoDonacion.setIdUser(currentUser.getUser());

                        ts.add(asientoDonacion);

                        entityManager.merge(evd);
                    }
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(FrameConfigAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameConfigAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameConfigAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameConfigAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameConfigAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboFormatoFactura;
    private javax.swing.JComboBox cboModoImpresion;
    private javax.swing.JComboBox cboSqlFiles;
    private javax.swing.JButton cmdDatadir;
    private javax.swing.JButton cmdFacturaPrintTest;
    private javax.swing.JButton cmdReset;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private java.awt.Panel panelDatadir;
    private javax.swing.JRadioButton rbRemoto;
    private javax.swing.JRadioButton rbServidor;
    private javax.swing.JTextField txtDatadir;
    private javax.swing.JTextField txtFacturaX;
    private javax.swing.JTextField txtFacturaY;
    private javax.swing.JTextField txtIP;
    // End of variables declaration//GEN-END:variables
}
