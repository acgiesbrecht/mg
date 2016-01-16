/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.frames;

import com.lacreacion.mg.frames.informes.FrameInformesRemates;
import com.lacreacion.mg.frames.operaciones.FrameFacturacionColectiva;
import com.lacreacion.mg.frames.operaciones.FrameColectasDetalle;
import com.lacreacion.mg.frames.operaciones.FrameAportesDetalle;
import com.lacreacion.mg.frames.operaciones.FrameRematesDetalle;
import com.lacreacion.mg.frames.operaciones.FrameRematesPagos;
import com.lacreacion.mg.frames.operaciones.FrameFacturacionUnica;
import com.lacreacion.mg.frames.admin.FrameConfigAdmin;
import com.lacreacion.mg.frames.admin.FrameEventoCuotasAdmin;
import com.lacreacion.mg.frames.admin.FrameCategoriasArticulosAdmin;
import com.lacreacion.mg.frames.admin.FrameRecibos;
import com.lacreacion.mg.frames.admin.FrameIglesiaAdmin;
import com.lacreacion.mg.frames.admin.FrameEventosAdmin;
import com.lacreacion.mg.frames.admin.FrameRolesAdmin;
import com.lacreacion.mg.frames.admin.FrameEntidadesAdmin;
import com.lacreacion.mg.frames.admin.FrameTimbradosAdmin;
import com.lacreacion.mg.frames.admin.FrameUsuariosAdmin;
import com.lacreacion.mg.frames.admin.FrameFacturasAdmin;
import com.lacreacion.mg.frames.admin.FrameGruposAdmin;
import com.lacreacion.mg.frames.admin.FrameTransferencias;
import com.lacreacion.mg.domain.TblUsers;
import com.lacreacion.mg.utils.CurrentUser;
import com.lacreacion.mg.utils.Utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.swing.DesktopManager;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.apache.commons.io.IOUtils;
import org.apache.derby.drda.NetworkServerControl;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author user
 */
public class MdiFrame extends javax.swing.JFrame {

    CurrentUser currentUser = CurrentUser.getInstance();

    JDesktopPane desktop;
    DesktopManager manager;

    Map<String, String> persistenceMap = new HashMap<>();
    private BufferedImage img;

    /**
     * Creates new form MDIFrame
     */
    public MdiFrame() {
        try {

            persistenceMap = Utils.getInstance().getDatabaseIP();

            if (Boolean.parseBoolean(Preferences.userRoot().node("MG").get("isServer", "true"))) {
                NetworkServerControl server = new NetworkServerControl();
                server.start(null);
            }

            img = ImageIO.read(getClass().getResourceAsStream("/images/g4204.png"));

            initComponents();

            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/g4204.png")));
            desktop = new JDesktopPane() {
                @Override
                protected void paintComponent(Graphics grphcs) {
                    super.paintComponent(grphcs);
                    grphcs.drawImage(img, (this.getWidth() - img.getWidth()) / 2, (this.getHeight() - img.getHeight()) / 2, null);
                }

                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(img.getWidth(), img.getHeight());
                }
            };
            desktop.setBackground(Color.LIGHT_GRAY);
            setContentPane(desktop);

            this.setExtendedState(
                    this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

            currentUser.addPropertyChangeListener(
                    (PropertyChangeEvent event) -> {
                        if ("user".equals(event.getPropertyName())) {
                            mnuOpRemates.setEnabled(currentUser.hasRole(1));
                            mnuOpPagos.setEnabled(currentUser.hasRole(1));
                            mnuOpColectas.setEnabled(currentUser.hasRole(1));
                            mnuOpAportes.setEnabled(currentUser.hasRole(1));

                            mnuOpFacturaUnica.setEnabled(currentUser.hasRole(2));
                            mnuOpFacturaPendientes.setEnabled(currentUser.hasRole(2));

                            mnuAdMiembros.setEnabled(currentUser.hasRole(2));

                            mnuAdEventos.setEnabled(currentUser.hasRole(3));
                            mnuAdCuotas.setEnabled(currentUser.hasRole(3));
                            mnuAdCat.setEnabled(currentUser.hasRole(2));

                            mnuAdTransf.setEnabled(currentUser.hasRole(2));
                            mnuAdRecibos.setEnabled(currentUser.hasRole(2));

                            mnuAdTimbrados.setEnabled(currentUser.hasRole(2));
                            mnuAdFacturas.setEnabled(currentUser.hasRole(2));

                            mnuAdIglesia.setEnabled(currentUser.hasRole(3));
                            mnuAdConfig.setEnabled(currentUser.hasRole(3));
                            mnuAdUsuarios.setEnabled(currentUser.hasRole(3));
                            mnuAdGrupos.setEnabled(currentUser.hasRole(3));
                            mnuAdRoles.setEnabled(currentUser.hasRole(3));

                            mnuAdInformes.setEnabled(currentUser.hasRole(1));
                        }
                    }
            );

            //AUTO LOGIN-------------------------------
            currentUser.setUser(
                    null);

            EntityManager entityManager = javax.persistence.Persistence.createEntityManagerFactory("mg_PU").createEntityManager();

            try {
                Object o = entityManager.createNativeQuery("select count(*) from tbl_users where 1=2").getSingleResult();
            } catch (Exception e) {
                resetDB();
            }

            List<TblUsers> list = entityManager.createQuery("SELECT t FROM TblUsers t").getResultList();
            for (TblUsers user : list) {
                if (user.getNombre().equals("adrian")) {
                    if (BCrypt.checkpw(String.valueOf("adrian"), user.getPassword())) {
                        currentUser.setUser(user);
                    }
                }
            }
            //-------------------------------------

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void resetDB() {
        try {
            Map<String, String> persistenceMap = Utils.getInstance().getDatabaseIP();
            Boolean error = false;
            Connection conn = DriverManager.getConnection(persistenceMap.get("javax.persistence.jdbc.url"), persistenceMap.get("javax.persistence.jdbc.user"), persistenceMap.get("javax.persistence.jdbc.password"));
            List<String> sql = Arrays.asList(IOUtils.toString(getClass().getResourceAsStream("/sql/javadb.sql")).split(";"));
            Statement stmt = conn.createStatement();
            for (String s : sql) {
                try {
                    stmt.executeUpdate(s);
                } catch (SQLException exx) {
                    error = true;
                    if (exx.getErrorCode() != 30000) {
                        JOptionPane.showMessageDialog(null, exx.getMessage() + String.valueOf(exx.getErrorCode()));
                    }
                }
            }

            if (error) {
                JOptionPane.showMessageDialog(null, "Error. Por favor pruebe otra vez.");
            } else {
                JOptionPane.showMessageDialog(null, "Base de Datos restablecida!");

            }

            stmt.close();
            conn.close();
        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
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

        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        lblBG = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuLogin = new javax.swing.JMenuItem();
        mnuOpFacturacion = new javax.swing.JMenu();
        mnuOpRemates = new javax.swing.JMenuItem();
        mnuOpPagos = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mnuOpColectas = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        mnuOpAportes = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        mnuOpFacturaPendientes = new javax.swing.JMenuItem();
        mnuOpFacturaUnica = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        mnuAdInformes = new javax.swing.JMenuItem();
        mnuAdInformes1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuAdMiembros = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuAdEventos = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuAdCuotas = new javax.swing.JMenuItem();
        mnuAdCat = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnuAdTransf = new javax.swing.JMenuItem();
        mnuAdRecibos = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mnuAdTimbrados = new javax.swing.JMenuItem();
        mnuAdFacturas = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuAdIglesia = new javax.swing.JMenuItem();
        mnuAdUsuarios = new javax.swing.JMenuItem();
        mnuAdGrupos = new javax.swing.JMenuItem();
        mnuAdRoles = new javax.swing.JMenuItem();
        mnuAdConfig = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnuAdConfig1 = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem4.setText("jMenuItem4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Usuario");

        mnuLogin.setText("Iniciar Sesion");
        mnuLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLoginActionPerformed(evt);
            }
        });
        jMenu1.add(mnuLogin);

        jMenuBar1.add(jMenu1);

        mnuOpFacturacion.setText("Operaciones");

        mnuOpRemates.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpRemates.setText("Remates");
        mnuOpRemates.setEnabled(false);
        mnuOpRemates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpRematesActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpRemates);

        mnuOpPagos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpPagos.setText("Pagos de Remates");
        mnuOpPagos.setEnabled(false);
        mnuOpPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpPagosActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpPagos);
        mnuOpFacturacion.add(jSeparator8);

        mnuOpColectas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpColectas.setText("Colectas");
        mnuOpColectas.setEnabled(false);
        mnuOpColectas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpColectasActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpColectas);
        mnuOpFacturacion.add(jSeparator7);

        mnuOpAportes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpAportes.setText("Aportes");
        mnuOpAportes.setEnabled(false);
        mnuOpAportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpAportesActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpAportes);
        mnuOpFacturacion.add(jSeparator9);

        mnuOpFacturaPendientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpFacturaPendientes.setText("Facturacion Colectiva");
        mnuOpFacturaPendientes.setEnabled(false);
        mnuOpFacturaPendientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpFacturaPendientesActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpFacturaPendientes);

        mnuOpFacturaUnica.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.SHIFT_MASK));
        mnuOpFacturaUnica.setText("Facturacion Unica");
        mnuOpFacturaUnica.setEnabled(false);
        mnuOpFacturaUnica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpFacturaUnicaActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpFacturaUnica);

        jMenuBar1.add(mnuOpFacturacion);

        jMenu4.setText("Informes");

        mnuAdInformes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        mnuAdInformes.setText("Informes Remates");
        mnuAdInformes.setEnabled(false);
        mnuAdInformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdInformesActionPerformed(evt);
            }
        });
        jMenu4.add(mnuAdInformes);

        mnuAdInformes1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        mnuAdInformes1.setText("Informes Colectas y Aportes");
        mnuAdInformes1.setEnabled(false);
        mnuAdInformes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdInformes1ActionPerformed(evt);
            }
        });
        jMenu4.add(mnuAdInformes1);

        jMenuBar1.add(jMenu4);

        jMenu2.setText("Administracion");

        mnuAdMiembros.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mnuAdMiembros.setText("Administrar Miembros");
        mnuAdMiembros.setEnabled(false);
        mnuAdMiembros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdMiembrosActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdMiembros);
        jMenu2.add(jSeparator2);

        mnuAdEventos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        mnuAdEventos.setText("Administrar Eventos");
        mnuAdEventos.setEnabled(false);
        mnuAdEventos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdEventosActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdEventos);
        jMenu2.add(jSeparator3);

        mnuAdCuotas.setText("Administrar Cuotas");
        mnuAdCuotas.setEnabled(false);
        mnuAdCuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdCuotasActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdCuotas);

        mnuAdCat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        mnuAdCat.setText("Administrar Categorias de Articulos");
        mnuAdCat.setEnabled(false);
        mnuAdCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdCatActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdCat);
        jMenu2.add(jSeparator4);

        mnuAdTransf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        mnuAdTransf.setText("Administrar Transferencias");
        mnuAdTransf.setEnabled(false);
        mnuAdTransf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdTransfActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdTransf);

        mnuAdRecibos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        mnuAdRecibos.setText("Administrar Recibos");
        mnuAdRecibos.setEnabled(false);
        mnuAdRecibos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdRecibosActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdRecibos);
        jMenu2.add(jSeparator6);

        mnuAdTimbrados.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        mnuAdTimbrados.setText("Administrar Timbrados");
        mnuAdTimbrados.setEnabled(false);
        mnuAdTimbrados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdTimbradosActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdTimbrados);

        mnuAdFacturas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        mnuAdFacturas.setText("Administrar Facturas");
        mnuAdFacturas.setEnabled(false);
        mnuAdFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdFacturasActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdFacturas);
        jMenu2.add(jSeparator1);

        mnuAdIglesia.setText("Administrar Iglesia");
        mnuAdIglesia.setEnabled(false);
        mnuAdIglesia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdIglesiaActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdIglesia);

        mnuAdUsuarios.setText("Administrar Usuarios");
        mnuAdUsuarios.setEnabled(false);
        mnuAdUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdUsuariosActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdUsuarios);

        mnuAdGrupos.setText("Administrar Grupos");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jMenu1, org.jdesktop.beansbinding.ELProperty.create("${selected}"), mnuAdGrupos, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        mnuAdGrupos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdGruposActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdGrupos);

        mnuAdRoles.setText("Administrar Roles");
        mnuAdRoles.setEnabled(false);
        mnuAdRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdRolesActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdRoles);

        mnuAdConfig.setText("Configuracion del Sistema");
        mnuAdConfig.setEnabled(false);
        mnuAdConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdConfigActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdConfig);
        jMenu2.add(jSeparator5);

        mnuAdConfig1.setText("Realizar BackUp");
        mnuAdConfig1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdConfig1ActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdConfig1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBG, javax.swing.GroupLayout.DEFAULT_SIZE, 1067, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBG, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuAdMiembrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdMiembrosActionPerformed
        try {
            FrameEntidadesAdmin frame = new FrameEntidadesAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_mnuAdMiembrosActionPerformed

    private void mnuAdCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdCatActionPerformed
        try {
            FrameCategoriasArticulosAdmin frame = new FrameCategoriasArticulosAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }

    }//GEN-LAST:event_mnuAdCatActionPerformed

    private void mnuOpRematesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpRematesActionPerformed
        try {
            FrameRematesDetalle frameR = new FrameRematesDetalle();
            frameR.setVisible(true);

            desktop.add(frameR);

            frameR.setSelected(true);
            frameR.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_mnuOpRematesActionPerformed

    private void mnuOpPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpPagosActionPerformed
        try {
            FrameRematesPagos frame = new FrameRematesPagos();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuOpPagosActionPerformed

    private void mnuAdConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdConfigActionPerformed
        try {
            FrameConfigAdmin frame = new FrameConfigAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_mnuAdConfigActionPerformed

    private void mnuAdTransfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdTransfActionPerformed
        try {
            FrameTransferencias frame = new FrameTransferencias();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuAdTransfActionPerformed

    private void mnuAdRecibosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdRecibosActionPerformed
        try {
            FrameRecibos frame = new FrameRecibos();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_mnuAdRecibosActionPerformed

    private void mnuAdCuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdCuotasActionPerformed
        try {
            FrameEventoCuotasAdmin frame = new FrameEventoCuotasAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_mnuAdCuotasActionPerformed

    private void mnuAdIglesiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdIglesiaActionPerformed
        try {
            FrameIglesiaAdmin frame = new FrameIglesiaAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_mnuAdIglesiaActionPerformed

    private void mnuAdInformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdInformesActionPerformed
        try {
            FrameInformesRemates frame = new FrameInformesRemates();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuAdInformesActionPerformed

    private void mnuOpColectasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpColectasActionPerformed
        try {
            FrameColectasDetalle frame = new FrameColectasDetalle();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuOpColectasActionPerformed

    private void mnuAdUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdUsuariosActionPerformed
        try {
            FrameUsuariosAdmin frame = new FrameUsuariosAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuAdUsuariosActionPerformed

    private void mnuAdGruposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdGruposActionPerformed
        try {
            FrameGruposAdmin frame = new FrameGruposAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuAdGruposActionPerformed

    private void mnuLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLoginActionPerformed
        try {
            if (mnuLogin.getText().equals("Iniciar Sesion")) {
                FormLogin frame = new FormLogin(this, true);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                if (currentUser.getUser() != null) {
                    mnuLogin.setText("Cerrar Sesion: " + currentUser.getUser().getNombre());
                }
            } else {
                mnuLogin.setText("Iniciar Sesion");
                currentUser.setUser(null);
                desktop.removeAll();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuLoginActionPerformed

    private void mnuAdEventosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdEventosActionPerformed
        try {
            FrameEventosAdmin frame = new FrameEventosAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuAdEventosActionPerformed

    private void mnuAdRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdRolesActionPerformed
        try {
            FrameRolesAdmin frame = new FrameRolesAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuAdRolesActionPerformed

    private void mnuAdTimbradosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdTimbradosActionPerformed
        try {
            FrameTimbradosAdmin frame = new FrameTimbradosAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuAdTimbradosActionPerformed

    private void mnuAdFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdFacturasActionPerformed
        try {
            FrameFacturasAdmin frame = new FrameFacturasAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuAdFacturasActionPerformed

    private void mnuOpFacturaUnicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpFacturaUnicaActionPerformed
        try {
            FrameFacturacionUnica frame = new FrameFacturacionUnica();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuOpFacturaUnicaActionPerformed

    private void mnuOpAportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpAportesActionPerformed
        try {
            FrameAportesDetalle frame = new FrameAportesDetalle();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuOpAportesActionPerformed

    private void mnuOpFacturaPendientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpFacturaPendientesActionPerformed
        try {
            FrameFacturacionColectiva frame = new FrameFacturacionColectiva();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuOpFacturaPendientesActionPerformed

    private void mnuAdInformes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdInformes1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuAdInformes1ActionPerformed

    private void mnuAdConfig1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdConfig1ActionPerformed
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Eligir ubicación de la base de datos.");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String backupdirectory = chooser.getSelectedFile().getPath() + "\\BackUp_" + sdf.format(new Date());

                Map<String, String> persistenceMap = Utils.getInstance().getDatabaseIP();

                Connection conn = DriverManager.getConnection(persistenceMap.get("javax.persistence.jdbc.url"), persistenceMap.get("javax.persistence.jdbc.user"), persistenceMap.get("javax.persistence.jdbc.password"));

                try (CallableStatement cs = conn.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)")) {
                    cs.setString(1, backupdirectory);
                    cs.execute();
                    cs.close();
                }
                JOptionPane.showMessageDialog(null, "BackUp guardado con exito en: " + backupdirectory);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuAdConfig1ActionPerformed

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
            /*for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
             System.out.println(info.getName());
             if ("Nimbus".equals(info.getName())) {
             javax.swing.UIManager.setLookAndFeel(info.getClassName());
             break;
             }*/
            UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MdiFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MdiFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MdiFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MdiFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MdiFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JLabel lblBG;
    private javax.swing.JMenuItem mnuAdCat;
    private javax.swing.JMenuItem mnuAdConfig;
    private javax.swing.JMenuItem mnuAdConfig1;
    private javax.swing.JMenuItem mnuAdCuotas;
    private javax.swing.JMenuItem mnuAdEventos;
    private javax.swing.JMenuItem mnuAdFacturas;
    private javax.swing.JMenuItem mnuAdGrupos;
    private javax.swing.JMenuItem mnuAdIglesia;
    private javax.swing.JMenuItem mnuAdInformes;
    private javax.swing.JMenuItem mnuAdInformes1;
    private javax.swing.JMenuItem mnuAdMiembros;
    private javax.swing.JMenuItem mnuAdRecibos;
    private javax.swing.JMenuItem mnuAdRoles;
    private javax.swing.JMenuItem mnuAdTimbrados;
    private javax.swing.JMenuItem mnuAdTransf;
    private javax.swing.JMenuItem mnuAdUsuarios;
    private javax.swing.JMenuItem mnuLogin;
    private javax.swing.JMenuItem mnuOpAportes;
    private javax.swing.JMenuItem mnuOpColectas;
    private javax.swing.JMenuItem mnuOpFacturaPendientes;
    private javax.swing.JMenuItem mnuOpFacturaUnica;
    private javax.swing.JMenu mnuOpFacturacion;
    private javax.swing.JMenuItem mnuOpPagos;
    private javax.swing.JMenuItem mnuOpRemates;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the databaseIP
     */
}
