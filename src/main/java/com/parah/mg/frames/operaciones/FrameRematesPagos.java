/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames.operaciones;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.parah.mg.domain.TblAsientos;
import com.parah.mg.domain.TblAsientosTemporales;
import com.parah.mg.domain.TblCuentasContablesPorDefecto;
import com.parah.mg.domain.TblEventoCuotas;
import com.parah.mg.domain.TblEventoDetalle;
import com.parah.mg.domain.TblEventos;
import com.parah.mg.domain.TblRecibos;
import com.parah.mg.domain.TblTransferencias;
import com.parah.mg.domain.miembros.TblEntidades;
import com.parah.mg.domain.models.CuotaModel;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.prefs.Preferences;
import javax.persistence.Persistence;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class FrameRematesPagos extends javax.swing.JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameRematesPagos.class);
    Map<String, String> persistenceMap = new HashMap<>();
    List<TblEntidades> listEntidadesFiltered;
    TblEntidades selectedEntidad;
    List<Date> listFechasCuotas;
    TblEventoCuotas remateCuotas;
    Integer saldoActual;
    Timer timer;
    EventList<TblEntidades> eventListEntidades = new BasicEventList<>();
    EventList<TblEventos> eventListEventos = new BasicEventList<>();
    CurrentUser currentUser = CurrentUser.getInstance();
    TblCuentasContablesPorDefecto cuentasContablesPorDefecto;

    public FrameRematesPagos() {

        super("Remates Pagos",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {

            persistenceMap = Utils.getInstance().getPersistenceMap();
            initComponents();

            txtCtaCte.setEnabled(false);
            cboEntidad.setEnabled(false);
            masterTable.setVisible(false);
            lblDeuda.setVisible(false);
            lblPagos.setVisible(false);
            lblSaldo.setVisible(false);
            txtTransferencia.setVisible(false);
            txtRecibo.setVisible(false);
            cmdProcesar.setVisible(false);

            cuentasContablesPorDefecto = entityManager.find(TblCuentasContablesPorDefecto.class, 1);

            eventListEntidades.clear();
            eventListEntidades.addAll(listEntidades);
            AutoCompleteSupport support1 = AutoCompleteSupport.install(cboEntidad, eventListEntidades);
            support1.setFilterMode(TextMatcherEditor.CONTAINS);

            eventListEventos.clear();
            eventListEventos.addAll(listEventos);
            AutoCompleteSupport support2 = AutoCompleteSupport.install(cboFechaRemate, eventListEventos);
            support2.setFilterMode(TextMatcherEditor.CONTAINS);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            Date today = sdf.parse(s);
            Optional<TblEventos> value = listEventos.stream().filter(a -> a.getFecha().equals(today))
                    .findFirst();
            if (value.isPresent()) {
                cboFechaRemate.setSelectedItem(value.get());
            } else {
                cboFechaRemate.setSelectedIndex(cboFechaRemate.getItemCount() - 1);
            }

            loadPendientes();

            cboEntidad.setSelectedIndex(-1);
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addPropertyChangeListener("permanentFocusOwner", new PropertyChangeListener() {
                        @Override
                        public void propertyChange(final PropertyChangeEvent e) {
                            if (e.getNewValue() instanceof JFormattedTextField) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        JFormattedTextField textField = (JFormattedTextField) e.getNewValue();
                                        textField.selectAll();
                                    }
                                });
                            }
                        }
                    });
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
        dateToStringConverter1 = new com.parah.mg.utils.DateToStringConverter();
        queryEntidades = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        listEntidades = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEntidades.getResultList());
        dateTimeTableCellRenderer1 = new com.parah.mg.utils.DateTimeTableCellRenderer();
        numberCellRenderer1 = new com.parah.mg.utils.NumberCellRenderer();
        queryEventos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventos t WHERE t.idEventoTipo.id = 1 AND t.idGrupo IN :grupos ORDER BY t.fecha");
        queryEventos.setParameter("grupos", currentUser.getUser().getTblGruposList());
        listEventos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventos.getResultList());
        queryEventoDetalle = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventoDetalle t WHERE t.idEvento = :eventoId AND t.idEntidad = :miembroId ORDER BY t.fechahora");
        queryEventoDetalle.setParameter("miembroId", null) ;
        queryEventoDetalle.setParameter("eventoId", null) ;
        listEventoDetalle = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventoDetalle.getResultList());
        queryFormasDePago = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblFormasDePago t");
        listFormasDePago = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryFormasDePago.getResultList());
        idMiembroLabel = new javax.swing.JLabel();
        idMiembroLabel1 = new javax.swing.JLabel();
        txtCtaCte = new javax.swing.JTextField();
        idMiembroLabel2 = new javax.swing.JLabel();
        cboEntidad = new javax.swing.JComboBox();
        idMiembroLabel3 = new javax.swing.JLabel();
        idMiembroLabel4 = new javax.swing.JLabel();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        lblDeuda = new javax.swing.JLabel();
        idMiembroLabel7 = new javax.swing.JLabel();
        lblPagos = new javax.swing.JLabel();
        idMiembroLabel8 = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        idMiembroLabel5 = new javax.swing.JLabel();
        idMiembroLabel6 = new javax.swing.JLabel();
        cmdCancel = new javax.swing.JButton();
        cmdProcesar = new javax.swing.JButton();
        lblCuotasFechas = new javax.swing.JLabel();
        txtTransferencia = new javax.swing.JFormattedTextField();
        txtRecibo = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cboFechaRemate = new javax.swing.JComboBox();

        dateTimeTableCellRenderer1.setText("dateTimeTableCellRenderer1");

        numberCellRenderer1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
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

        idMiembroLabel.setText("Miembro:");

        idMiembroLabel1.setText("Cta. Cte.:");

        txtCtaCte.setEnabled(false);
        txtCtaCte.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCtaCteFocusGained(evt);
            }
        });
        txtCtaCte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCtaCteActionPerformed(evt);
            }
        });
        txtCtaCte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCtaCteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCtaCteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCtaCteKeyTyped(evt);
            }
        });

        idMiembroLabel2.setText("Nombre:");

        cboEntidad.setEnabled(false);

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEntidades, cboEntidad);
        bindingGroup.addBinding(jComboBoxBinding);

        cboEntidad.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cboEntidadPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cboEntidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEntidadActionPerformed(evt);
            }
        });

        idMiembroLabel3.setText("Deuda Total:");

        idMiembroLabel4.setText("Realizar Pago:");

        masterTable.setAutoCreateRowSorter(true);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEventoDetalle, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha/Hora");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCategoriaArticulo}"));
        columnBinding.setColumnName("Categoria Articulo");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCategoriasArticulos.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idEvento}"));
        columnBinding.setColumnName("Evento");
        columnBinding.setColumnClass(com.parah.mg.domain.TblEventos.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${observacion}"));
        columnBinding.setColumnName("Observacion");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${monto}"));
        columnBinding.setColumnName("Monto");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, cboEntidad, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), masterTable, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(0).setCellRenderer(dateTimeTableCellRenderer1);
            masterTable.getColumnModel().getColumn(4).setCellRenderer(numberCellRenderer1);
        }

        lblDeuda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDeuda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDeuda.setText("1000000");

        idMiembroLabel7.setText("Pagos anteriores:");

        lblPagos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPagos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPagos.setText("1000000");

        idMiembroLabel8.setText("Saldo a pagar:");

        lblSaldo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSaldo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSaldo.setText("1000000");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        idMiembroLabel5.setText("Transferencia:");

        idMiembroLabel6.setText("Efectivo:");

        cmdCancel.setText("Cancelar");
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        cmdProcesar.setText("Procesar & Imprimir");
        cmdProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdProcesarActionPerformed(evt);
            }
        });

        txtTransferencia.setColumns(9);
        txtTransferencia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtTransferencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTransferenciaMouseClicked(evt);
            }
        });
        txtTransferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransferenciaActionPerformed(evt);
            }
        });

        txtRecibo.setColumns(9);
        txtRecibo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtRecibo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtReciboMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmdProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(idMiembroLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(idMiembroLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCuotasFechas, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(lblCuotasFechas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idMiembroLabel5)
                            .addComponent(txtTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idMiembroLabel6)
                            .addComponent(txtRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jButton1.setText("Actualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Fecha de Remate:");

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEventos, cboFechaRemate);
        bindingGroup.addBinding(jComboBoxBinding);

        cboFechaRemate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFechaRemateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboFechaRemate, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(405, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(idMiembroLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(lblDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(idMiembroLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(lblPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(idMiembroLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(lblSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(idMiembroLabel)
                        .addGap(18, 18, 18)
                        .addComponent(idMiembroLabel1)
                        .addGap(12, 12, 12)
                        .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(idMiembroLabel2)
                        .addGap(12, 12, 12)
                        .addComponent(cboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jButton1))
                    .addComponent(idMiembroLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 96, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(masterScrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboFechaRemate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(idMiembroLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(idMiembroLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(idMiembroLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(cboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(masterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idMiembroLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblDeuda)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idMiembroLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblPagos)))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idMiembroLabel8)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblSaldo)))
                .addGap(7, 7, 7)
                .addComponent(idMiembroLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCtaCteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtaCteFocusGained
        txtCtaCte.setSelectionStart(0);
        txtCtaCte.setSelectionEnd(txtCtaCte.getText().length());
    }//GEN-LAST:event_txtCtaCteFocusGained

    private void txtCtaCteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCtaCteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteKeyPressed

    private void txtCtaCteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCtaCteKeyReleased
        try {
            txtCtaCte.setBackground(Color.white);
            if (txtCtaCte.getText().length() > 4) {
                listEntidadesFiltered = listEntidades;
                Optional<TblEntidades> value = listEntidadesFiltered.stream().filter(a -> a.getCtacte().equals(Integer.valueOf(txtCtaCte.getText())))
                        .findFirst();
                if (value.isPresent()) {
                    cboEntidad.setSelectedItem(value.get());
                    txtCtaCte.setBackground(Color.green);
                }


                /*for (TblEntidades value : listEntidadesFiltered) {
                 if (value.getCtacte().equals(Integer.valueOf(txtCtaCte.getText()))) {
                 cboEntidad.setSelectedItem(value);
                 txtCtaCte.setBackground(Color.green);
                 }
                 }*/
            }
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteKeyReleased

    private void txtCtaCteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCtaCteKeyTyped

        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteKeyTyped

    void loadPendientes() {
        try {
//            if (cboFechaRemate.getSelectedIndex() > -1) {
            //              Integer remateId = ((TblEventos) cboFechaRemate.getSelectedItem()).getId();
            //queryEntidades = entityManager.createNativeQuery("SELECT m.* FROM tbl_miembros m, tbl_remates_detalle r WHERE r.id_remate = " + remateId.toString() + " AND r.id_miembro = m.id AND m.ctacte <> 11111 GROUP BY m.id ORDER BY m.nombre", TblEntidades.class);
            /*queryEntidades = entityManager.createNativeQuery("WITH remates AS "
                 + "	(SELECT m.*, SUM(rd.monto) AS monto FROM tbl_miembros m "
                 + "	LEFT JOIN tbl_remates_detalle rd ON m.id = rd.id_miembro "
                 + "	group by m.id, m.nombre, m.ctacte, m.domicilio, m.box),"
                 + "     pagos AS"
                 + "       (SELECT m.*, COALESCE(SUM(p.monto),0) AS monto FROM tbl_miembros m "
                 + "	LEFT JOIN tbl_pagos p ON m.id = p.id_miembro "
                 + "	group by m.id, m.nombre, m.ctacte, m.domicilio, m.box)     "
                 + "SELECT remates.id, remates.nombre, remates.ctacte, remates.domicilio, remates.box FROM remates, pagos "
                 + "where remates.id = pagos.id AND (remates.monto - pagos.monto) > 0 "
                 + "order by remates.nombre", TblEntidades.class);

                queryEntidades = entityManager.createNativeQuery("SELECT remates.id, remates.nombre, remates.ctacte, remates.domicilio, remates.box FROM "
                        + "	(SELECT m.*, SUM(rd.monto) AS monto FROM TBL_ENTIDADES m "
                        + "	LEFT JOIN TBL_EVENTO_DETALLE rd ON m.id = rd.id_miembro "
                        + "	group by m.id, m.nombre, m.ruc, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida) remates, "
                        + "     (SELECT m.*, COALESCE(SUM(p.monto),0) AS monto FROM TBL_ENTIDADES m "
                        + "	LEFT JOIN TBL_TRANSFERENCIAS p ON m.id = p.id_miembro "
                        + "	group by m.id, m.nombre, m.ruc, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida) transferencias, "
                        + "     (SELECT m.*, COALESCE(SUM(p.monto),0) AS monto FROM TBL_ENTIDADES m "
                        + "	LEFT JOIN TBL_RECIBOS p ON m.id = p.id_miembro "
                        + "	group by m.id, m.nombre, m.ruc, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida) recibos "
                        + "WHERE remates.id = transferencias.id AND remates.id = recibos.id AND (remates.monto - transferencias.monto - recibos.monto) > 0 "
                        + "ORDER BY remates.nombre", TblEntidades.class);
             */
 /*queryEntidades = entityManager.createNativeQuery("SELECT eventodetalle.id, "
                    + "                     eventodetalle.nombres, "
                    + "                     eventodetalle.apellidos, "
                    + "                     eventodetalle.RAZON_SOCIAL, "
                    + "                     eventodetalle.RUC_SIN_DV, "
                    + "                     eventodetalle.ctacte, "
                    + "                     eventodetalle.domicilio, "
                    + "                     eventodetalle.box, "
                    + "                     eventodetalle.id_forma_de_pago_preferida, "
                    + "                    eventodetalle.IS_MIEMBRO_ACTIVO, "
                    + "                    eventodetalle.ID_FORMA_DE_PAGO_PREFERIDA, "
                    + "                    eventodetalle.APORTE_MENSUAL, "
                    + "                    eventodetalle.ID_ENTIDAD_PAGANTE_APORTES, "
                    + "                    eventodetalle.FECHA_NACIMIENTO, "
                    + "                    eventodetalle.FECHA_BAUTISMO, "
                    + "                    eventodetalle.FECHA_ENTRADA_CONGREGACION, "
                    + "                    eventodetalle.FECHA_SALIDA_CONGREGACION, "
                    + "                    eventodetalle.FECHA_DEFUNCION, "
                    + "                    eventodetalle.ID_AREA_SERVICIO_EN_IGLESIA, "
                    + "                    eventodetalle.ID_MIEMBROS_CATEGORIA_DE_PAGO, "
                    + "                    eventodetalle.ID_MIEMBROS_ALERGIA, "
                    + "                     eventodetalle.id_user"
                    + " FROM "
                    + "        (SELECT m.*, SUM(rd.monto) AS monto FROM TBL_ENTIDADES m "
                    + "            LEFT JOIN (SELECT ed.* FROM MG.TBL_EVENTO_DETALLE ed, MG.TBL_EVENTOS ev WHERE ed.ID_EVENTO = ev.ID AND ev.ID_EVENTO_TIPO = 1) rd"
                    + "            ON m.id = rd.ID_ENTIDAD "
                    + "            group by m.id, m.nombres, m.apellidos, m.RAZON_SOCIAL, m.RUC_SIN_DV, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida, "
                    + "                    m.ID_ENTIDAD_PAGANTE_APORTES, "
                    + "                    m.IS_MIEMBRO_ACTIVO, "
                    + "                    m.ID_FORMA_DE_PAGO_PREFERIDA, "
                    + "                    m.APORTE_MENSUAL, "
                    + "                    m.FECHA_NACIMIENTO, "
                    + "                    m.FECHA_BAUTISMO, "
                    + "                    m.FECHA_ENTRADA_CONGREGACION, "
                    + "                    m.FECHA_SALIDA_CONGREGACION, "
                    + "                    m.FECHA_DEFUNCION, "
                    + "                    m.ID_AREA_SERVICIO_EN_IGLESIA, "
                    + "                    m.ID_MIEMBROS_CATEGORIA_DE_PAGO, "
                    + "                    m.ID_MIEMBROS_ALERGIA) eventodetalle, "
                    + "        (SELECT m.*, COALESCE(SUM(p.monto),0) AS monto FROM TBL_ENTIDADES m "
                    + "            LEFT JOIN (SELECT * FROM MG.TBL_TRANSFERENCIAS WHERE ID_EVENTO_TIPO = 1) p ON m.id = p.ID_ENTIDAD "
                    + "            group by m.id, m.nombres, m.apellidos, m.RAZON_SOCIAL, m.RUC_SIN_DV, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida, "
                    + "                    m.IS_MIEMBRO_ACTIVO, "
                    + "                    m.ID_ENTIDAD_PAGANTE_APORTES, "
                    + "                    m.ID_FORMA_DE_PAGO_PREFERIDA, "
                    + "                    m.APORTE_MENSUAL, "
                    + "                    m.FECHA_NACIMIENTO, "
                    + "                    m.FECHA_BAUTISMO, "
                    + "                    m.FECHA_ENTRADA_CONGREGACION, "
                    + "                    m.FECHA_SALIDA_CONGREGACION, "
                    + "                    m.FECHA_DEFUNCION, "
                    + "                    m.ID_AREA_SERVICIO_EN_IGLESIA, "
                    + "                    m.ID_MIEMBROS_CATEGORIA_DE_PAGO, "
                    + "                    m.ID_MIEMBROS_ALERGIA) transferencias, "
                    + "        (SELECT m.*, COALESCE(SUM(p.monto),0) AS monto FROM TBL_ENTIDADES m "
                    + "            LEFT JOIN (SELECT * FROM MG.TBL_RECIBOS WHERE ID_EVENTO_TIPO = 1) p ON m.id = p.ID_ENTIDAD "
                    + "            group by m.id, m.nombres, m.apellidos, m.RAZON_SOCIAL, m.RUC_SIN_DV, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida, "
                    + "                    m.IS_MIEMBRO_ACTIVO, "
                    + "                    m.ID_FORMA_DE_PAGO_PREFERIDA, "
                    + "                    m.APORTE_MENSUAL, "
                    + "                    m.ID_ENTIDAD_PAGANTE_APORTES, "
                    + "                    m.FECHA_NACIMIENTO, "
                    + "                    m.FECHA_BAUTISMO, "
                    + "                    m.FECHA_ENTRADA_CONGREGACION, "
                    + "                    m.FECHA_SALIDA_CONGREGACION, "
                    + "                    m.FECHA_DEFUNCION, "
                    + "                    m.ID_AREA_SERVICIO_EN_IGLESIA, "
                    + "                    m.ID_MIEMBROS_CATEGORIA_DE_PAGO, "
                    + "                    m.ID_MIEMBROS_ALERGIA) recibos "
                    + "    WHERE eventodetalle.id = transferencias.id AND eventodetalle.id = recibos.id AND (eventodetalle.monto - transferencias.monto - recibos.monto) > 0 "
                    + "    ORDER BY eventodetalle.ctacte", TblEntidades.class);
             */
            queryEntidades = entityManager.createNativeQuery("SELECT entidades.* FROM TBL_ENTIDADES entidades, (SELECT ID FROM (SELECT e.ID, e.CTACTE, SUM(DETALLE.MONTOAPORTE) AS MONTOAPORTE, SUM(DETALLE.MONTODONACION) AS MONTODONACION FROM"
                    + " (SELECT ed.id_entidad,"
                    + "                SUM(ed.monto*ev.PORCENTAJE_APORTE/100) AS montoAporte,"
                    + "                SUM(ed.monto*(100-ev.PORCENTAJE_APORTE)/100) AS montoDonacion"
                    + "              FROM MG.TBL_EVENTO_DETALLE ed LEFT JOIN MG.TBL_EVENTOS ev ON ed.ID_EVENTO = ev.ID WHERE ev.ID = " + ((TblEventos) cboFechaRemate.getSelectedItem()).getId().toString()
                    + "                group by ed.id_entidad"
                    + " UNION ALL  "
                    + " SELECT p.id_entidad,"
                    + "                 -SUM(p.MONTO_APORTE) AS montoAporte,"
                    + "                 -SUM(p.MONTO_DONACION) AS montoDonacion"
                    + "                 FROM MG.TBL_TRANSFERENCIAS p WHERE p.ID_EVENTO = " + ((TblEventos) cboFechaRemate.getSelectedItem()).getId().toString()
                    + "                 group by p.id_entidad"
                    + " UNION ALL "
                    + " SELECT p.id_entidad,"
                    + "        -SUM(p.MONTO_APORTE) AS montoAporte,"
                    + "        -SUM(p.MONTO_DONACION) AS montoDonacion"
                    + "        FROM MG.TBL_RECIBOS p WHERE p.ID_EVENTO = " + ((TblEventos) cboFechaRemate.getSelectedItem()).getId().toString()
                    + "        group by p.id_entidad) DETALLE LEFT JOIN MG.TBL_ENTIDADES e ON DETALLE.ID_ENTIDAD = e.ID"
                    + " GROUP BY e.ID, e.CTACTE, e.APELLIDOS, e.NOMBRES) d"
                    + " WHERE MONTODONACION + MONTOAPORTE > 0) e"
                    + " WHERE entidades.ID = e.ID"
                    + " ORDER BY CTACTE", TblEntidades.class);
            listEntidades.clear();
            listEntidades.addAll(queryEntidades.getResultList());
            eventListEntidades.clear();
            eventListEntidades.addAll(listEntidades);

            if (listEntidades.size() > 0) {
                txtCtaCte.setEnabled(true);
                txtCtaCte.requestFocus();
                cboEntidad.setEnabled(true);
            } else {
                txtCtaCte.setEnabled(false);
                cboEntidad.setEnabled(false);
            }

            remateCuotas = entityManager.find(TblEventoCuotas.class, ((TblEventos) cboFechaRemate.getSelectedItem()).getId());
            String fechas = "Las transferencias seran imprimidas con fechas de";
            listFechasCuotas = Utils.getInstance().getCuotasFechas(remateCuotas);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Date fecha : listFechasCuotas) {
                fechas += ", " + sdf.format(fecha);
            }
            fechas = fechas + ".";
            fechas = fechas.replaceFirst(",", " ");
            lblCuotasFechas.setText(fechas);

        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }

    void loadEntidad() {
        try {
            selectedEntidad = (TblEntidades) cboEntidad.getSelectedItem();
            /*queryRematesDetalle
             = entityManager.createNativeQuery("SELECT * FROM tbl_remates_detalle WHERE id_remate = " + ((TblRemates) cboFechaRemate.getSelectedItem()).getId().toString() + " AND id_miembro = "
             + selectedEntidad.getId().toString() + " ORDER BY fechahora", TblRematesDetalle.class
             );*/

            queryEventoDetalle.setParameter("eventoId", (TblEventos) cboFechaRemate.getSelectedItem());
            queryEventoDetalle.setParameter("miembroId", selectedEntidad);
            listEventoDetalle.clear();

            listEventoDetalle.addAll(queryEventoDetalle.getResultList());
            if (listEventoDetalle.size() > 0) {
                masterTable.setVisible(true);
                lblDeuda.setVisible(true);
                lblPagos.setVisible(true);
                lblSaldo.setVisible(true);
                txtTransferencia.setVisible(true);
                txtRecibo.setVisible(true);
                cmdProcesar.setVisible(true);
            } else {
                masterTable.setVisible(false);
                lblDeuda.setVisible(false);
                lblPagos.setVisible(false);
                lblSaldo.setVisible(false);
                txtTransferencia.setVisible(false);
                txtRecibo.setVisible(false);
                cmdProcesar.setVisible(false);
            }

            Integer deuda = listEventoDetalle.stream()
                    .mapToInt(s -> s.getMonto())
                    .sum();

            lblDeuda.setText(String.format("%(,d", deuda));
            /*
             Integer pagosT;
             try {
             System.out.println("SELECT sum(monto) AS total   FROM tbl_transferencias  WHERE id_miembro = " + selectedEntidad.getId().toString() + " AND id_remate = " + ((TblRemates) cboFechaRemate.getSelectedItem()).getId().toString());

             pagosT = Integer.parseInt(entityManager.createNativeQuery("SELECT sum(monto) AS total"
             + "   FROM tbl_transferencias"
             + "  WHERE id_miembro = " + selectedEntidad.getId().toString()
             + " AND id_remate = " + ((TblRemates) cboFechaRemate.getSelectedItem()).getId().toString()).getSingleResult().toString());
             } catch (Exception ex) {
             pagosT = 0;
             }
             Integer pagosR;
             try {
             pagosR = Integer.parseInt(entityManager.createNativeQuery("SELECT sum(monto) AS total"
             + " FROM tbl_recibos"
             + " WHERE id_miembro = " + selectedEntidad.getId().toString()
             + " AND id_remate = " + ((TblRemates) cboFechaRemate.getSelectedItem()).getId().toString()).getSingleResult().toString());
             } catch (Exception ex) {
             pagosR = 0;
             }

             Integer pagos = pagosT + pagosR;
             */

            Integer transferencias;
            try {
                transferencias = Integer.parseInt(entityManager.createNativeQuery("SELECT sum(MONTO_APORTE + MONTO_DONACION) AS total"
                        + " FROM tbl_transferencias"
                        + " WHERE id_entidad = " + selectedEntidad.getId().toString()
                        + " AND id_evento = " + ((TblEventos) cboFechaRemate.getSelectedItem()).getId().toString()).getSingleResult().toString());
            } catch (Exception ex) {
                transferencias = 0;
            }
            Integer recibos;
            try {
                recibos = Integer.parseInt(entityManager.createNativeQuery("SELECT sum(MONTO_APORTE + MONTO_DONACION) AS total"
                        + " FROM tbl_recibos"
                        + " WHERE id_entidad = " + selectedEntidad.getId().toString()
                        + " AND id_evento = " + ((TblEventos) cboFechaRemate.getSelectedItem()).getId().toString()).getSingleResult().toString());
            } catch (Exception ex) {
                recibos = 0;
            }

            lblPagos.setText(String.format("%(,d", recibos + transferencias));

            saldoActual = deuda - (recibos + transferencias);

            lblSaldo.setText(String.format("%(,d", saldoActual));

            txtTransferencia.setValue(saldoActual);
            txtRecibo.setValue(0);

            txtTransferencia.selectAll();
            //txtTransferencia.requestFocus();
            txtCtaCte.setText(((TblEntidades) cboEntidad.getSelectedItem()).getCtacte().toString());

        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }
    private void cboEntidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEntidadActionPerformed

    }//GEN-LAST:event_cboEntidadActionPerformed

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed

    }//GEN-LAST:event_cmdCancelActionPerformed

    private void txtCtaCteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCtaCteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteActionPerformed

    private void cmdProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdProcesarActionPerformed
        try {
            cmdProcesar.setEnabled(false);
            //Connection conn = DriverManager.getConnection("jdbc:postgresql://" + databaseIP + ":5432/remate", "postgres", "123456");
            Connection conn = DriverManager.getConnection(persistenceMap.get("javax.persistence.jdbc.url"), persistenceMap.get("javax.persistence.jdbc.user"), persistenceMap.get("javax.persistence.jdbc.password"));
            TblEventos currentEvento = (TblEventos) cboFechaRemate.getSelectedItem();
            Date fecha = new Date();
            Integer t_id = 0;
            Integer r_id = 0;
            /*if (!StringUtils.isNumeric(txtTransferencia.getText())) {
             txtTransferencia1.setText("0");
             }
             if (!StringUtils.isNumeric(txtEfectivo.getText())) {
             txtEfectivo.setText("0");
             }*/
            if (((Number) txtRecibo.getValue()).intValue() + ((Number) txtTransferencia.getValue()).intValue() > saldoActual) {
                JOptionPane.showMessageDialog(null, "El Monto a abonar no puede superar la deuda existente.");
                return;
            }
            //if (Integer.valueOf(txtTransferencia.getValue()) > 0) {
            Integer transferenciaMonto = ((Number) txtTransferencia.getValue()).intValue();
            if (transferenciaMonto > 0) {
                //List<CuotaModel> cuotasList = Varios.getCuotas(remateCuotas, Integer.valueOf(txtTransferencia1.getText()));
                List<CuotaModel> cuotasList = Utils.getInstance().getCuotas(remateCuotas, transferenciaMonto);
                for (CuotaModel cuota : cuotasList) {
                    TblTransferencias transferencia = new TblTransferencias();
                    transferencia.setFechahora(cuota.getFecha());
                    transferencia.setIdEntidad(selectedEntidad);
                    transferencia.setConcepto(((TblEventos) cboFechaRemate.getSelectedItem()).getDescripcion());
                    transferencia.setMontoAporte(cuota.getMonto() * ((TblEventos) cboFechaRemate.getSelectedItem()).getPorcentajeAporte() / 100);
                    transferencia.setMontoDonacion(cuota.getMonto() - transferencia.getMontoAporte());
                    transferencia.setIdEventoTipo(((TblEventos) cboFechaRemate.getSelectedItem()).getIdEventoTipo());
                    transferencia.setIdEvento((TblEventos) cboFechaRemate.getSelectedItem());
                    transferencia.setCobrado(false);
                    transferencia.setIdUser(currentUser.getUser());

                    List<TblAsientos> listAsientos = new ArrayList<>();
                    for (TblEventoDetalle evd : listEventoDetalle) {
                        listAsientos.addAll(evd.getTblAsientosCollection());
                    }

                    Collection<TblAsientosTemporales> listAsientosTemporales = transferencia.getTblAsientosTemporalesCollection();
                    if (listAsientosTemporales == null) {
                        listAsientosTemporales = new LinkedList<>();
                        transferencia.setTblAsientosTemporalesCollection(listAsientosTemporales);
                    }

                    //Aporte
                    TblAsientosTemporales aT = new TblAsientosTemporales();
                    aT.setFacturado(false);
                    aT.setFechahora(transferencia.getFechahora());
                    aT.setIdCentroDeCosto(listAsientos.get(0).getIdCentroDeCosto());
                    aT.setIdCuentaContableDebe(listAsientos.get(0).getIdCentroDeCosto().getIdCuentaContableCtaCtePorDefecto());
                    aT.setIdCuentaContableHaber(cuentasContablesPorDefecto.getIdCuentaACobrar());
                    aT.setEsAporte(true);
                    aT.setMonto(transferencia.getMontoAporte());
                    listAsientosTemporales.add(aT);

                    //Donacion
                    aT = new TblAsientosTemporales();
                    aT.setFacturado(false);
                    aT.setFechahora(transferencia.getFechahora());
                    aT.setIdCentroDeCosto(listAsientos.get(0).getIdCentroDeCosto());
                    aT.setIdCuentaContableDebe(listAsientos.get(0).getIdCentroDeCosto().getIdCuentaContableCtaCtePorDefecto());
                    aT.setIdCuentaContableHaber(cuentasContablesPorDefecto.getIdCuentaACobrar());
                    aT.setEsAporte(false);
                    aT.setMonto(transferencia.getMontoDonacion());
                    listAsientosTemporales.add(aT);

                    entityManager.getTransaction().begin();
                    entityManager.persist(transferencia);
                    entityManager.flush();
                    entityManager.getTransaction().commit();
                    t_id = transferencia.getId();
                    if (t_id > 0) {
                        Map parameters = new HashMap();
                        parameters.put("transferencia_id", t_id);
                        parameters.put("logo", getClass().getResourceAsStream("/reports/cclogo200.png"));
                        parameters.put("logo2", getClass().getResourceAsStream("/reports/cclogo200.png"));
                        parameters.put("logo3", getClass().getResourceAsStream("/reports/cclogo200.png"));

                        if (Preferences.userRoot().node("MG").get("modoImpresion", "Normal").equals("Normal")) {
                            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/transferencia.jrxml"));
                            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                            JasperPrintManager.printReport(jasperPrint, false);
                        } else {
                            for (int x = 1; x == 3; x++) {
                                JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/transferencia_simple.jrxml"));
                                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                                JasperPrintManager.printReport(jasperPrint, false);
                            }
                        }
                    }
                }
            }

            Integer reciboMonto = ((Number) txtRecibo.getValue()).intValue();
            if (reciboMonto > 0) {
                TblRecibos recibo = new TblRecibos();
                recibo.setFechahora(fecha);
                recibo.setIdEntidad(selectedEntidad);
                recibo.setConcepto(((TblEventos) cboFechaRemate.getSelectedItem()).getDescripcion());
                recibo.setMontoAporte(reciboMonto * ((TblEventos) cboFechaRemate.getSelectedItem()).getPorcentajeAporte() / 100);
                recibo.setMontoDonacion(reciboMonto - recibo.getMontoAporte());
                recibo.setIdEventoTipo(((TblEventos) cboFechaRemate.getSelectedItem()).getIdEventoTipo());
                recibo.setIdUser(currentUser.getUser());

                List<TblAsientos> listAsientos = new ArrayList<>();
                for (TblEventoDetalle evd : listEventoDetalle) {
                    listAsientos.addAll(evd.getTblAsientosCollection());
                }

                Collection<TblAsientosTemporales> listAsientosTemporales = recibo.getTblAsientosTemporalesCollection();
                if (listAsientosTemporales == null) {
                    listAsientosTemporales = new LinkedList<>();
                    recibo.setTblAsientosTemporalesCollection(listAsientosTemporales);
                }

                //Aporte
                TblAsientosTemporales aT = new TblAsientosTemporales();
                aT.setFacturado(false);
                aT.setFechahora(recibo.getFechahora());
                aT.setIdCentroDeCosto(listAsientos.get(0).getIdCentroDeCosto());
                aT.setIdCuentaContableDebe(listAsientos.get(0).getIdCentroDeCosto().getIdCuentaContableCtaCtePorDefecto());
                aT.setIdCuentaContableHaber(cuentasContablesPorDefecto.getIdCuentaACobrar());
                aT.setEsAporte(true);
                aT.setMonto(recibo.getMontoAporte());
                listAsientosTemporales.add(aT);

                //Donacion
                aT = new TblAsientosTemporales();
                aT.setFacturado(false);
                aT.setFechahora(recibo.getFechahora());
                aT.setIdCentroDeCosto(listAsientos.get(0).getIdCentroDeCosto());
                aT.setIdCuentaContableDebe(listAsientos.get(0).getIdCentroDeCosto().getIdCuentaContableCtaCtePorDefecto());
                aT.setIdCuentaContableHaber(cuentasContablesPorDefecto.getIdCuentaACobrar());
                aT.setEsAporte(false);
                aT.setMonto(recibo.getMontoDonacion());
                listAsientosTemporales.add(aT);

                entityManager.getTransaction().begin();
                entityManager.persist(recibo);
                entityManager.flush();
                entityManager.getTransaction().commit();

                r_id = recibo.getId();
                if (r_id > 0) {
                    Map parameters = new HashMap();
                    parameters.put("recibo_id", r_id);
                    JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/recibo.jrxml"));
                    JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                    //JasperViewer jReportsViewer = new JasperViewer(jasperPrint, false);
                    //jReportsViewer.setVisible(true);
                    JasperPrintManager.printReport(jasperPrint, false);
                }
            }

            loadPendientes();
            cboEntidad.setSelectedItem(null);
            listEventoDetalle.clear();
            lblDeuda.setText("");
            lblSaldo.setText("");
            lblPagos.setText("");
            txtCtaCte.setText("");
            txtCtaCte.setBackground(Color.white);
            txtCtaCte.requestFocus();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        } finally {
            cmdProcesar.setEnabled(true);
        }
    }//GEN-LAST:event_cmdProcesarActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
//
    }//GEN-LAST:event_formInternalFrameActivated

    private void txtTransferenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTransferenciaMouseClicked

    }//GEN-LAST:event_txtTransferenciaMouseClicked

    private void txtReciboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtReciboMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReciboMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadPendientes();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cboEntidadPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cboEntidadPopupMenuWillBecomeInvisible
        if (cboEntidad.getSelectedItem() != null) {
            loadEntidad();
        }

    }//GEN-LAST:event_cboEntidadPopupMenuWillBecomeInvisible

    private void txtTransferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransferenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransferenciaActionPerformed

    private void cboFechaRemateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFechaRemateActionPerformed

    }//GEN-LAST:event_cboFechaRemateActionPerformed

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
            java.util.logging.Logger.getLogger(FrameRematesPagos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameRematesPagos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameRematesPagos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameRematesPagos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameRematesPagos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboEntidad;
    private javax.swing.JComboBox cboFechaRemate;
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdProcesar;
    private com.parah.mg.utils.DateTimeTableCellRenderer dateTimeTableCellRenderer1;
    private com.parah.mg.utils.DateToStringConverter dateToStringConverter1;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel idMiembroLabel;
    private javax.swing.JLabel idMiembroLabel1;
    private javax.swing.JLabel idMiembroLabel2;
    private javax.swing.JLabel idMiembroLabel3;
    private javax.swing.JLabel idMiembroLabel4;
    private javax.swing.JLabel idMiembroLabel5;
    private javax.swing.JLabel idMiembroLabel6;
    private javax.swing.JLabel idMiembroLabel7;
    private javax.swing.JLabel idMiembroLabel8;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCuotasFechas;
    private javax.swing.JLabel lblDeuda;
    private javax.swing.JLabel lblPagos;
    private javax.swing.JLabel lblSaldo;
    private java.util.List<com.parah.mg.domain.miembros.TblEntidades> listEntidades;
    private java.util.List<com.parah.mg.domain.TblEventoDetalle> listEventoDetalle;
    private java.util.List<com.parah.mg.domain.TblEventos> listEventos;
    private java.util.List<com.parah.mg.domain.TblFormasDePago> listFormasDePago;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private com.parah.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query queryEntidades;
    private javax.persistence.Query queryEventoDetalle;
    private javax.persistence.Query queryEventos;
    private javax.persistence.Query queryFormasDePago;
    private javax.swing.JTextField txtCtaCte;
    private javax.swing.JFormattedTextField txtRecibo;
    private javax.swing.JFormattedTextField txtTransferencia;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
