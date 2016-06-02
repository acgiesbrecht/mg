/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames;

import com.parah.mg.domain.TblDatabaseUpdates;
import com.parah.mg.domain.TblTransferencias;
import com.parah.mg.domain.TblUsers;
import com.parah.mg.frames.admin.FrameAutofacturasAdmin;
import com.parah.mg.frames.admin.FrameCategoriasArticulosAdmin;
import com.parah.mg.frames.admin.FrameCentrosDeCostoAdmin;
import com.parah.mg.frames.admin.FrameConfigAdmin;
import com.parah.mg.frames.admin.FrameCuentasContablesAdmin;
import com.parah.mg.frames.admin.FrameEntidadesAdmin;
import com.parah.mg.frames.admin.FrameEventoCuotasAdmin;
import com.parah.mg.frames.admin.FrameEventosAdmin;
import com.parah.mg.frames.admin.FrameFacturasAdmin;
import com.parah.mg.frames.admin.FrameGruposAdmin;
import com.parah.mg.frames.admin.FrameIglesiaAdmin;
import com.parah.mg.frames.admin.FrameRecibosAdmin;
import com.parah.mg.frames.admin.FrameTimbradosAdmin;
import com.parah.mg.frames.admin.FrameTimbradosAutofacturasAdmin;
import com.parah.mg.frames.admin.FrameTransferenciasAdmin;
import com.parah.mg.frames.admin.FrameUsuariosAdmin;
import com.parah.mg.frames.informes.FrameInformesContabilidad;
import com.parah.mg.frames.informes.FrameInformesCyA;
import com.parah.mg.frames.informes.FrameInformesRemates;
import com.parah.mg.frames.operaciones.FrameAportesDetalle;
import com.parah.mg.frames.operaciones.FrameAsientosManuales;
import com.parah.mg.frames.operaciones.FrameAutofacturacion;
import com.parah.mg.frames.operaciones.FrameCobrarTransferenciasAyC;
import com.parah.mg.frames.operaciones.FrameCobrarTransferenciasRemates;
import com.parah.mg.frames.operaciones.FrameColectasDetalle;
import com.parah.mg.frames.operaciones.FrameFacturacionColectiva;
import com.parah.mg.frames.operaciones.FrameFacturacionUnica;
import com.parah.mg.frames.operaciones.FrameFacturasCompra;
import com.parah.mg.frames.operaciones.FrameRematesDetalle;
import com.parah.mg.frames.operaciones.FrameRematesPagos;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.swing.DesktopManager;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.apache.derby.drda.NetworkServerControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author user
 */
public class MdiFrame extends javax.swing.JFrame {

    CurrentUser currentUser = CurrentUser.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(MdiFrame.class);

    JDesktopPane desktop;
    DesktopManager manager;

    Map<String, String> persistenceMap = new HashMap<>();
    private BufferedImage img;

    /**
     * Creates new form MDIFrame
     */
    public MdiFrame() {
        try {
            persistenceMap = Utils.getInstance().getPersistenceMap();

            if (Boolean.parseBoolean(Preferences.userRoot().node("MG").get("isServer", "true"))) {
                NetworkServerControl server = new NetworkServerControl();
                server.start(null);
            }

            String dataDir = persistenceMap.get("backUpDir");
            Files.createDirectories(Paths.get(dataDir));

            img = ImageIO.read(getClass().getResourceAsStream("/g4204.png"));

            initComponents();

            InputStream resourceAsStream = this.getClass().getResourceAsStream("/version.properties");
            Properties prop = new Properties();
            prop.load(resourceAsStream);
            setTitle("MG " + prop.getProperty("project.version") + "." + prop.getProperty("project.build"));
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/g4204.png")));
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

            this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

            currentUser.addPropertyChangeListener(
                    (PropertyChangeEvent event) -> {
                        if ("user".equals(event.getPropertyName())) {
                            mnuOpRemates.setEnabled(currentUser.hasRole(1));
                            mnuOpPagos.setEnabled(currentUser.hasRole(1));
                            mnuOpColectas.setEnabled(currentUser.hasRole(1));
                            mnuOpAportes.setEnabled(currentUser.hasRole(1));

                            mnuOpFacturaUnica.setEnabled(currentUser.hasRole(2));
                            mnuOpFacturaPendientes.setEnabled(currentUser.hasRole(2));
                            mnuOpCobrarTransferencias.setEnabled(currentUser.hasRole(2));
                            mnuOpCobrarTransferenciasAyC.setEnabled(currentUser.hasRole(2));

                            mnuEgFacturas.setEnabled(currentUser.hasRole(2));
                            mnuEgAutofacturas.setEnabled(currentUser.hasRole(2));
                            mnuEgNotasDeCredito.setEnabled(currentUser.hasRole(2));
                            mnuEgRecibos.setEnabled(currentUser.hasRole(2));
                            mnuEgAsientosManuales.setEnabled(currentUser.hasRole(3));

                            mnuAdMiembros.setEnabled(currentUser.hasRole(2));

                            mnuAdEventos.setEnabled(currentUser.hasRole(3));
                            mnuAdCuotas.setEnabled(currentUser.hasRole(3));
                            mnuAdCat.setEnabled(currentUser.hasRole(2));

                            mnuAdTransf.setEnabled(currentUser.hasRole(2));
                            mnuAdRecibos.setEnabled(currentUser.hasRole(2));

                            mnuAdTimbrados.setEnabled(currentUser.hasRole(3));
                            mnuAdFacturas.setEnabled(currentUser.hasRole(2));

                            mnuAdTimbradosAutofacturas.setEnabled(currentUser.hasRole(3));
                            mnuAdAutofacturas.setEnabled(currentUser.hasRole(2));

                            mnuAdCentrosDeCosto.setEnabled(currentUser.hasRole(3));
                            mnuAdCuentasContables.setEnabled(currentUser.hasRole(3));

                            mnuAdIglesia.setEnabled(currentUser.hasRole(3));
                            mnuAdConfig.setEnabled(currentUser.hasRole(3));
                            mnuAdUsuarios.setEnabled(currentUser.hasRole(3));
                            mnuAdGrupos.setEnabled(currentUser.hasRole(3));

                            mnuAdInformes.setEnabled(currentUser.hasRole(1));
                            mnuInformesCyA.setEnabled(currentUser.hasRole(2));
                            mnuInformesContabilidad.setEnabled(currentUser.hasRole(2));

                        }
                    }
            );

            //AUTO LOGIN-------------------------------
            currentUser.setUser(null);

            EntityManager entityManager = Persistence.createEntityManagerFactory("mg_PU", persistenceMap).createEntityManager();
            entityManager.getTransaction().begin();

            try {
                Object o = entityManager.createNativeQuery("select count(*) from tbl_users where 1=2").getSingleResult();
            } catch (Exception e) {
                Utils.getInstance().executeSQL("/sql/javadb.sql");
            }

            Boolean hasBackedUp = false;

            try {
                Object o = entityManager.createNativeQuery("select count(*) from tbl_database_updates where 1=2").getSingleResult();
            } catch (Exception e) {
                hasBackedUp = Utils.getInstance().executeUpdateSQL("/sql/javadb_20160224.sql", hasBackedUp);
            }

            if (entityManager.find(TblDatabaseUpdates.class, "/sql/javadb_20160219.sql") == null) {
                hasBackedUp = Utils.getInstance().executeUpdateSQL("/sql/javadb_20160219.sql", hasBackedUp);
            }

            if (entityManager.find(TblDatabaseUpdates.class, "/sql/javadb_20160219.sql") == null) {
                hasBackedUp = Utils.getInstance().executeUpdateSQL("/sql/javadb_20160219.sql", hasBackedUp);
            }

            if (entityManager.find(TblDatabaseUpdates.class, "/sql/javadb_20160323.sql") == null) {
                hasBackedUp = Utils.getInstance().executeUpdateSQL("/sql/javadb_20160323.sql", hasBackedUp);
            }
            if (entityManager.find(TblDatabaseUpdates.class, "/sql/javadb_contabilidad_y_compras.sql") == null) {
                hasBackedUp = Utils.getInstance().executeUpdateSQL("/sql/javadb_contabilidad_y_compras.sql", hasBackedUp);
            }
            if (entityManager.find(TblDatabaseUpdates.class, "/sql/javadb_20160330.sql") == null) {
                hasBackedUp = Utils.getInstance().executeUpdateSQL("/sql/javadb_20160330.sql", hasBackedUp);
            }
            if (entityManager.find(TblDatabaseUpdates.class, "/sql/javadb_20160409.sql") == null) {
                hasBackedUp = Utils.getInstance().executeUpdateSQL("/sql/javadb_20160409.sql", hasBackedUp);
            }
            if (entityManager.find(TblDatabaseUpdates.class, "/sql/javadb_20160429.sql") == null) {
                hasBackedUp = Utils.getInstance().executeUpdateSQL("/sql/javadb_20160429.sql", hasBackedUp);
            }
            if (entityManager.find(TblDatabaseUpdates.class, "/sql/javadb_20160507.sql") == null) {
                hasBackedUp = Utils.getInstance().executeUpdateSQL("/sql/javadb_20160507.sql", hasBackedUp);
                List<TblTransferencias> listT = entityManager.createQuery("SELECT t FROM TblTransferencias t").getResultList();
                if (listT != null) {
                    for (TblTransferencias t : listT) {
                        if (t.getFechahoraCompromiso() == null) {
                            t.setFechahoraCompromiso(t.getFechahora());
                            entityManager.merge(t);
                        }
                    }
                    entityManager.getTransaction().commit();
                    entityManager.getTransaction().begin();
                }
            }
            if (entityManager.find(TblDatabaseUpdates.class, "/sql/javadb_20160601.sql") == null) {
                hasBackedUp = Utils.getInstance().executeUpdateSQL("/sql/javadb_20160601.sql", hasBackedUp);
            }

            List<TblUsers> list = entityManager.createQuery("SELECT t FROM TblUsers t").getResultList();
            for (TblUsers user : list) {
                if (user.getNombre().equals("adrian") && BCrypt.checkpw(String.valueOf("adrian"), user.getPassword())) {
                    currentUser.setUser(user);
                }
            }
            //-------------------------------------

            if (currentUser.getUser()
                    == null) {
                FormLogin frame = new FormLogin(this, true);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                if (currentUser.getUser() != null) {
                    mnuLogin.setText("Cerrar Sesion: " + currentUser.getUser().getNombre());
                }
            }

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

        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuLogin = new javax.swing.JMenuItem();
        mnuOpFacturacion = new javax.swing.JMenu();
        mnuOpRemates = new javax.swing.JMenuItem();
        mnuOpPagos = new javax.swing.JMenuItem();
        mnuOpCobrarTransferencias = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mnuOpAportes = new javax.swing.JMenuItem();
        mnuOpColectas = new javax.swing.JMenuItem();
        mnuOpCobrarTransferenciasAyC = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        mnuOpFacturaPendientes = new javax.swing.JMenuItem();
        mnuOpFacturaUnica = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        mnuEgFacturas = new javax.swing.JMenuItem();
        mnuEgAutofacturas = new javax.swing.JMenuItem();
        mnuEgNotasDeCredito = new javax.swing.JMenuItem();
        mnuEgRecibos = new javax.swing.JMenuItem();
        mnuEgAsientosManuales = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        mnuAdInformes = new javax.swing.JMenuItem();
        mnuInformesCyA = new javax.swing.JMenuItem();
        mnuInformesContabilidad = new javax.swing.JMenuItem();
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
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        mnuAdTimbradosAutofacturas = new javax.swing.JMenuItem();
        mnuAdAutofacturas = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuAdCentrosDeCosto = new javax.swing.JMenuItem();
        mnuAdCuentasContables = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        mnuAdIglesia = new javax.swing.JMenuItem();
        mnuAdUsuarios = new javax.swing.JMenuItem();
        mnuAdGrupos = new javax.swing.JMenuItem();
        mnuAdConfig = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnuAdBackUp = new javax.swing.JMenuItem();

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

        mnuOpFacturacion.setText("Ingresos");

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

        mnuOpCobrarTransferencias.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpCobrarTransferencias.setText("Ingresar Transferencias Cobradas de Remates");
        mnuOpCobrarTransferencias.setEnabled(false);
        mnuOpCobrarTransferencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpCobrarTransferenciasActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpCobrarTransferencias);
        mnuOpFacturacion.add(jSeparator8);

        mnuOpAportes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpAportes.setText("Aportes");
        mnuOpAportes.setEnabled(false);
        mnuOpAportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpAportesActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpAportes);

        mnuOpColectas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpColectas.setText("Colectas");
        mnuOpColectas.setEnabled(false);
        mnuOpColectas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpColectasActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpColectas);

        mnuOpCobrarTransferenciasAyC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpCobrarTransferenciasAyC.setText("Ingresar Transferencias Cobradas de Aportes y Colectas");
        mnuOpCobrarTransferenciasAyC.setEnabled(false);
        mnuOpCobrarTransferenciasAyC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpCobrarTransferenciasAyCActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpCobrarTransferenciasAyC);
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

        mnuOpFacturaUnica.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpFacturaUnica.setText("Facturacion Unica");
        mnuOpFacturaUnica.setEnabled(false);
        mnuOpFacturaUnica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpFacturaUnicaActionPerformed(evt);
            }
        });
        mnuOpFacturacion.add(mnuOpFacturaUnica);

        jMenuBar1.add(mnuOpFacturacion);

        jMenu5.setText("Egresos");

        mnuEgFacturas.setText("Facturas");
        mnuEgFacturas.setEnabled(false);
        mnuEgFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEgFacturasActionPerformed(evt);
            }
        });
        jMenu5.add(mnuEgFacturas);

        mnuEgAutofacturas.setText("AutoFacturas");
        mnuEgAutofacturas.setEnabled(false);
        mnuEgAutofacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEgAutofacturasActionPerformed(evt);
            }
        });
        jMenu5.add(mnuEgAutofacturas);

        mnuEgNotasDeCredito.setText("Notas de Credito");
        mnuEgNotasDeCredito.setEnabled(false);
        jMenu5.add(mnuEgNotasDeCredito);

        mnuEgRecibos.setText("Recibos");
        mnuEgRecibos.setEnabled(false);
        jMenu5.add(mnuEgRecibos);

        mnuEgAsientosManuales.setText("Asientos Manuales");
        mnuEgAsientosManuales.setEnabled(false);
        mnuEgAsientosManuales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEgAsientosManualesActionPerformed(evt);
            }
        });
        jMenu5.add(mnuEgAsientosManuales);

        jMenuBar1.add(jMenu5);

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

        mnuInformesCyA.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        mnuInformesCyA.setText("Informes Colectas y Aportes");
        mnuInformesCyA.setEnabled(false);
        mnuInformesCyA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInformesCyAActionPerformed(evt);
            }
        });
        jMenu4.add(mnuInformesCyA);

        mnuInformesContabilidad.setText("Informes Contabilidad");
        mnuInformesContabilidad.setEnabled(false);
        mnuInformesContabilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInformesContabilidadActionPerformed(evt);
            }
        });
        jMenu4.add(mnuInformesContabilidad);

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
        jMenu2.add(jSeparator10);

        mnuAdTimbradosAutofacturas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        mnuAdTimbradosAutofacturas.setText("Administrar Timbrados Autofacturas");
        mnuAdTimbradosAutofacturas.setEnabled(false);
        mnuAdTimbradosAutofacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdTimbradosAutofacturasActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdTimbradosAutofacturas);

        mnuAdAutofacturas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        mnuAdAutofacturas.setText("Administrar Autofacturas");
        mnuAdAutofacturas.setEnabled(false);
        mnuAdAutofacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdAutofacturasActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdAutofacturas);
        jMenu2.add(jSeparator1);

        mnuAdCentrosDeCosto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        mnuAdCentrosDeCosto.setText("Administrar Centros de Costo");
        mnuAdCentrosDeCosto.setEnabled(false);
        mnuAdCentrosDeCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdCentrosDeCostoActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdCentrosDeCosto);

        mnuAdCuentasContables.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        mnuAdCuentasContables.setText("Administrar Plan de Cuentas");
        mnuAdCuentasContables.setEnabled(false);
        mnuAdCuentasContables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdCuentasContablesActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdCuentasContables);
        jMenu2.add(jSeparator7);

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

        mnuAdConfig.setText("Configuracion del Sistema");
        mnuAdConfig.setEnabled(false);
        mnuAdConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdConfigActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdConfig);
        jMenu2.add(jSeparator5);

        mnuAdBackUp.setText("Realizar BackUp");
        mnuAdBackUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdBackUpActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAdBackUp);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1091, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 754, Short.MAX_VALUE)
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuAdConfigActionPerformed

    private void mnuAdTransfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdTransfActionPerformed
        try {
            FrameTransferenciasAdmin frame = new FrameTransferenciasAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuAdTransfActionPerformed

    private void mnuAdRecibosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdRecibosActionPerformed
        try {
            FrameRecibosAdmin frame = new FrameRecibosAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuAdEventosActionPerformed

    private void mnuAdTimbradosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdTimbradosActionPerformed
        try {
            FrameTimbradosAdmin frame = new FrameTimbradosAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
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
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuOpFacturaPendientesActionPerformed

    private void mnuInformesCyAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInformesCyAActionPerformed
        try {
            FrameInformesCyA frame = new FrameInformesCyA();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuInformesCyAActionPerformed

    private void mnuAdBackUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdBackUpActionPerformed
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Eligir ubicación de la base de datos.");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                Utils.getInstance().exectueBackUp(chooser.getSelectedFile().getAbsolutePath());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuAdBackUpActionPerformed

    private void mnuOpCobrarTransferenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpCobrarTransferenciasActionPerformed
        try {
            FrameCobrarTransferenciasRemates frame = new FrameCobrarTransferenciasRemates();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuOpCobrarTransferenciasActionPerformed

    private void mnuOpCobrarTransferenciasAyCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpCobrarTransferenciasAyCActionPerformed
        try {
            FrameCobrarTransferenciasAyC frame = new FrameCobrarTransferenciasAyC();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuOpCobrarTransferenciasAyCActionPerformed

    private void mnuAdCentrosDeCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdCentrosDeCostoActionPerformed
        try {
            FrameCentrosDeCostoAdmin frame = new FrameCentrosDeCostoAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuAdCentrosDeCostoActionPerformed

    private void mnuAdCuentasContablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdCuentasContablesActionPerformed

        try {
            FrameCuentasContablesAdmin frame = new FrameCuentasContablesAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuAdCuentasContablesActionPerformed

    private void mnuEgFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEgFacturasActionPerformed
        try {
            FrameFacturasCompra frame = new FrameFacturasCompra();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuEgFacturasActionPerformed

    private void mnuInformesContabilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInformesContabilidadActionPerformed
        try {
            FrameInformesContabilidad frame = new FrameInformesContabilidad();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuInformesContabilidadActionPerformed

    private void mnuEgAsientosManualesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEgAsientosManualesActionPerformed
        try {
            FrameAsientosManuales frame = new FrameAsientosManuales();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

    }//GEN-LAST:event_mnuEgAsientosManualesActionPerformed

    private void mnuAdTimbradosAutofacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdTimbradosAutofacturasActionPerformed
        try {
            FrameTimbradosAutofacturasAdmin frame = new FrameTimbradosAutofacturasAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuAdTimbradosAutofacturasActionPerformed

    private void mnuAdAutofacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdAutofacturasActionPerformed
        try {
            FrameAutofacturasAdmin frame = new FrameAutofacturasAdmin();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuAdAutofacturasActionPerformed

    private void mnuEgAutofacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEgAutofacturasActionPerformed
        try {
            FrameAutofacturacion frame = new FrameAutofacturacion();
            frame.setVisible(true);

            desktop.add(frame);

            frame.setSelected(true);
            frame.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_mnuEgAutofacturasActionPerformed

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
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JMenuItem mnuAdAutofacturas;
    private javax.swing.JMenuItem mnuAdBackUp;
    private javax.swing.JMenuItem mnuAdCat;
    private javax.swing.JMenuItem mnuAdCentrosDeCosto;
    private javax.swing.JMenuItem mnuAdConfig;
    private javax.swing.JMenuItem mnuAdCuentasContables;
    private javax.swing.JMenuItem mnuAdCuotas;
    private javax.swing.JMenuItem mnuAdEventos;
    private javax.swing.JMenuItem mnuAdFacturas;
    private javax.swing.JMenuItem mnuAdGrupos;
    private javax.swing.JMenuItem mnuAdIglesia;
    private javax.swing.JMenuItem mnuAdInformes;
    private javax.swing.JMenuItem mnuAdMiembros;
    private javax.swing.JMenuItem mnuAdRecibos;
    private javax.swing.JMenuItem mnuAdTimbrados;
    private javax.swing.JMenuItem mnuAdTimbradosAutofacturas;
    private javax.swing.JMenuItem mnuAdTransf;
    private javax.swing.JMenuItem mnuAdUsuarios;
    private javax.swing.JMenuItem mnuEgAsientosManuales;
    private javax.swing.JMenuItem mnuEgAutofacturas;
    private javax.swing.JMenuItem mnuEgFacturas;
    private javax.swing.JMenuItem mnuEgNotasDeCredito;
    private javax.swing.JMenuItem mnuEgRecibos;
    private javax.swing.JMenuItem mnuInformesContabilidad;
    private javax.swing.JMenuItem mnuInformesCyA;
    private javax.swing.JMenuItem mnuLogin;
    private javax.swing.JMenuItem mnuOpAportes;
    private javax.swing.JMenuItem mnuOpCobrarTransferencias;
    private javax.swing.JMenuItem mnuOpCobrarTransferenciasAyC;
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
