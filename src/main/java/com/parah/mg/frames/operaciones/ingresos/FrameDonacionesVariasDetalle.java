/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames.operaciones.ingresos;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.parah.mg.domain.TblAsientos;
import com.parah.mg.domain.TblCategoriasArticulos;
import com.parah.mg.domain.TblCentrosDeCosto;
import com.parah.mg.domain.TblCuentasContables;
import com.parah.mg.domain.TblCuentasContablesPorDefecto;
import com.parah.mg.domain.TblDonacionesVariasDetalle;
import com.parah.mg.domain.TblEventoDetalle;
import com.parah.mg.domain.TblEventoTipos;
import com.parah.mg.domain.miembros.TblEntidades;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class FrameDonacionesVariasDetalle extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameDonacionesVariasDetalle.class);
    Map<String, String> persistenceMap = new HashMap<>();
    EventList<TblEntidades> eventListMiembros = new BasicEventList<>();
    CurrentUser currentUser = CurrentUser.getInstance();
    TblEventoTipos idEventoTipo;
    TblCentrosDeCosto centroDeCostoPreferido;
    JComboBox<TblCentrosDeCosto> cboCentroDeCostoDebe = new JComboBox();
    JComboBox<TblCentrosDeCosto> cboCentroDeCostoHaber = new JComboBox();
    JComboBox<TblCuentasContables> cboCuentaHaber = new JComboBox();
    JComboBox<TblCuentasContables> cboCuentaDebe = new JComboBox();

    Set forwardKeys = getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
    Set newForwardKeys = new HashSet(forwardKeys);

    public FrameDonacionesVariasDetalle() {
        super("Colectas",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {
            persistenceMap = Utils.getInstance().getPersistenceMap();

            //newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
            //setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newForwardKeys);
            //InputMap im = newButton.getInputMap(WIDTH).getInputMap();
            //im.put(KeyStroke.getKeyStroke("ENTER"), "pressed");
            //im.put(KeyStroke.getKeyStroke("released ENTER"), "released");
            initComponents();

            cboMiembro.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        montoField.requestFocusInWindow();
                    }
                }
            });

            this.dateTimeTableCellRenderer1.setEnProceso(
                    true);

            this.numberCellRenderer1.setEnProceso(
                    true);

            this.normalTableCellRenderer1.setEnProceso(
                    true);

            if (!Beans.isDesignTime()) {
                entityManager.getTransaction().begin();
                entityManager1.getTransaction().begin();
            }

            centroDeCostoPreferido = (TblCentrosDeCosto) entityManager.createQuery("SELECT t FROM TblCentrosDeCosto t WHERE t.preferido = true").getSingleResult();

            //AutoCompleteDecorator.decorate(cboFechaRemate);
            //AutoCompleteDecorator.decorate(cboCategoria);
            //AutoCompleteSupport support = AutoCompleteSupport.install(cboCentroDeCosto, GlazedLists.eventListOf(listCentrosDeCosto.toArray()));
            //support.setFilterMode(TextMatcherEditor.CONTAINS);
            eventListMiembros.clear();

            eventListMiembros.addAll(listMiembros);
            AutoCompleteSupport support2 = AutoCompleteSupport.install(cboMiembro, eventListMiembros);

            support2.setFilterMode(TextMatcherEditor.CONTAINS);

            idEventoTipo = entityManager.find(TblEventoTipos.class, 4);

            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addPropertyChangeListener("permanentFocusOwner", new PropertyChangeListener() {
                        @Override
                        public void propertyChange(final PropertyChangeEvent e
                        ) {
                            if (e.getNewValue() instanceof JFormattedTextField) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        JFormattedTextField textField = (JFormattedTextField) e.getNewValue();
                                        textField.selectAll();
                                    }
                                });
                            }
                        }
                    }
                    );
            masterTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    try {
                        if (!lse.getValueIsAdjusting()) {
                            if (asientosTable.getColumnModel().getColumnCount() == 5) {
                                asientosTable.getColumn("Centro de Costo Debe").setCellEditor(new DefaultCellEditor(cboCentroDeCostoHaber));
                                asientosTable.getColumn("Centro de Costo Haber").setCellEditor(new DefaultCellEditor(cboCentroDeCostoHaber));
                                asientosTable.getColumn("Cuenta Contable Debe").setCellEditor(new DefaultCellEditor(cboCuentaDebe));
                                asientosTable.getColumn("Cuenta Contable Haber").setCellEditor(new DefaultCellEditor(cboCuentaHaber));
                                asientosTable.getColumnModel().getColumn(4).setCellRenderer(numberCellRenderer1);
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
                        LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
                    }
                }
            });
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
        entityManager1 = java.beans.Beans.isDesignTime() ? null : Persistence.createEntityManagerFactory("mg_PU", persistenceMap).createEntityManager();
        queryMiembros = java.beans.Beans.isDesignTime() ? null : entityManager1.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        listMiembros = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryMiembros.getResultList());
        normalTableCellRenderer1 = new com.parah.mg.utils.NormalTableCellRenderer();
        categoriasConverter1 = new com.parah.mg.utils.CategoriasConverter();
        queryDonacionesVariasDetalle = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblDonacionesVariasDetalle t ORDER BY t.id");
        listDonacionesVariasDetalle = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryDonacionesVariasDetalle.getResultList());
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        dateTimeTableCellRenderer1 = new com.parah.mg.utils.DateTimeTableCellRenderer();
        integerLongConverter1 = new com.parah.mg.utils.IntegerLongConverter();
        numberCellRenderer1 = new com.parah.mg.utils.NumberCellRenderer();
        dateTimeToStringConverter1 = new com.parah.mg.utils.DateTimeToStringConverter();
        tblCategoriasArticulosQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCategoriasArticulos t ORDER BY t.descripcion");
        tblCategoriasArticulosList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(tblCategoriasArticulosQuery.getResultList());
        tblFormasDePagoQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblFormasDePago t ORDER BY t.id");
        tblFormasDePagoList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(tblFormasDePagoQuery.getResultList());
        ctaCteTableCellRenderer1 = new com.parah.mg.utils.CtaCteTableCellRenderer();
        queryCentrosDeCosto = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCentrosDeCosto t");
        listCentrosDeCosto = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(((javax.persistence.Query)null).getResultList());
        queryCuentasContables = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCuentasContables t where t.imputable = true");
        listCuentasContables = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryCuentasContables.getResultList());
        queryCuentasContablesPorDefecto = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCuentasContablesPorDefecto t");
        listCuentasContablesPorDefecto = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryCuentasContablesPorDefecto.getResultList());
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        montoLabel = new javax.swing.JLabel();
        idMiembroLabel = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        idMiembroLabel1 = new javax.swing.JLabel();
        txtCtaCte = new javax.swing.JTextField();
        idMiembroLabel2 = new javax.swing.JLabel();
        dateTableCellRenderer1 = new com.parah.mg.utils.DateTimeTableCellRenderer();
        cboMiembro = new javax.swing.JComboBox();
        montoField = new javax.swing.JFormattedTextField();
        jButton2 = new javax.swing.JButton();
        montoLabel1 = new javax.swing.JLabel();
        cboForma = new javax.swing.JComboBox();
        fechahoraLabel = new javax.swing.JLabel();
        dtpFecha = new org.jdesktop.swingx.JXDatePicker();
        txtObservacion = new javax.swing.JTextField();
        descripcionLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        asientosTable = new javax.swing.JTable();
        cmdBorrarAsiento = new javax.swing.JButton();
        cmdAddAsiento = new javax.swing.JButton();
        montoLabel6 = new javax.swing.JLabel();

        FormListener formListener = new FormListener();

        normalTableCellRenderer1.setText("normalTableCellRenderer1");

        dateTimeTableCellRenderer1.setText("dateTimeTableCellRenderer1");

        numberCellRenderer1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numberCellRenderer1.setText("numberCellRenderer1");

        ctaCteTableCellRenderer1.setText("ctaCteTableCellRenderer1");

        addInternalFrameListener(formListener);

        masterTable.setAutoCreateRowSorter(true);
        masterTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        masterTable.setRowHeight(20);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listDonacionesVariasDetalle, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha/Hora");
        columnBinding.setColumnClass(java.time.LocalDateTime.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idEntidad.ctacte}"));
        columnBinding.setColumnName("Cta. Cte.");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idEntidad}"));
        columnBinding.setColumnName("Donador");
        columnBinding.setColumnClass(com.parah.mg.domain.miembros.TblEntidades.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${monto}"));
        columnBinding.setColumnName("Monto");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            masterTable.getColumnModel().getColumn(1).setPreferredWidth(20);
            masterTable.getColumnModel().getColumn(1).setCellRenderer(ctaCteTableCellRenderer1);
            masterTable.getColumnModel().getColumn(2).setPreferredWidth(500);
            masterTable.getColumnModel().getColumn(3).setPreferredWidth(20);
            masterTable.getColumnModel().getColumn(3).setCellRenderer(numberCellRenderer1);
        }

        montoLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        montoLabel.setText("Monto:");

        idMiembroLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel.setText("Donador:");

        saveButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        saveButton.setText("Guardar");
        saveButton.addActionListener(formListener);

        refreshButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        refreshButton.setText("Cancelar");
        refreshButton.addActionListener(formListener);

        newButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        newButton.setText("Nuevo");
        newButton.addActionListener(formListener);

        deleteButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        deleteButton.setText("Eliminar");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), deleteButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        deleteButton.addActionListener(formListener);

        idMiembroLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel1.setText("Cta. Cte.:");

        txtCtaCte.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtCtaCte, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtCtaCte.addFocusListener(formListener);
        txtCtaCte.addKeyListener(formListener);

        idMiembroLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel2.setText("Nombre:");

        cboMiembro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listMiembros, cboMiembro);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idEntidad}"), cboMiembro, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cboMiembro, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cboMiembro.addPopupMenuListener(formListener);
        cboMiembro.addActionListener(formListener);
        cboMiembro.addKeyListener(formListener);

        montoField.setColumns(9);
        montoField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        montoField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        montoField.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.monto}"), montoField, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(integerLongConverter1);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), montoField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        montoField.addFocusListener(formListener);
        montoField.addMouseListener(formListener);
        montoField.addActionListener(formListener);
        montoField.addKeyListener(formListener);

        jButton2.setText("Actualizar");
        jButton2.addActionListener(formListener);

        montoLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        montoLabel1.setText("Forma de Pago:");

        cboForma.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFormasDePagoList, cboForma);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idFormaDePagoPreferida}"), cboForma, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cboForma, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cboForma.addActionListener(formListener);
        cboForma.addKeyListener(formListener);

        fechahoraLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fechahoraLabel.setText("Fecha:");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.fechahora}"), dtpFecha, org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), dtpFecha, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.observacion}"), txtObservacion, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtObservacion, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        descripcionLabel.setText("Observación:");

        asientosTable.setAutoCreateRowSorter(true);
        asientosTable.setRowHeight(20);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${selectedElement.tblAsientosList}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, eLProperty, asientosTable);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCentroDeCostoDebe}"));
        columnBinding.setColumnName("Centro De Costo Debe");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCentrosDeCosto.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCuentaContableDebe}"));
        columnBinding.setColumnName("Cuenta Contable Debe");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCuentasContables.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCentroDeCostoHaber}"));
        columnBinding.setColumnName("Centro De Costo Haber");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCentrosDeCosto.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCuentaContableHaber}"));
        columnBinding.setColumnName("Cuenta Contable Haber");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCuentasContables.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${monto}"));
        columnBinding.setColumnName("Importe");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(asientosTable);

        cmdBorrarAsiento.setText("-");
        cmdBorrarAsiento.addActionListener(formListener);

        cmdAddAsiento.setText("+");
        cmdAddAsiento.addActionListener(formListener);

        montoLabel6.setText("Asientos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(masterScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(montoLabel6)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmdAddAsiento)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmdBorrarAsiento))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(299, 299, 299))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(refreshButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(deleteButton, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(idMiembroLabel)
                            .addComponent(fechahoraLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(342, 342, 342)
                                .addComponent(dateTableCellRenderer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(montoLabel1)
                                    .addComponent(montoLabel)
                                    .addComponent(descripcionLabel))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(montoField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboForma, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(idMiembroLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)
                                                .addComponent(idMiembroLabel2))
                                            .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cboMiembro, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, refreshButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(masterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fechahoraLabel)
                    .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idMiembroLabel)
                    .addComponent(idMiembroLabel1)
                    .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idMiembroLabel2)
                    .addComponent(cboMiembro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboForma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(montoLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(montoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descripcionLabel))
                .addGap(18, 18, 18)
                .addComponent(montoLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdAddAsiento)
                    .addComponent(cmdBorrarAsiento))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton)))
                .addGap(50, 50, 50)
                .addComponent(dateTableCellRenderer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.KeyListener, java.awt.event.MouseListener, javax.swing.event.InternalFrameListener, javax.swing.event.PopupMenuListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                FrameDonacionesVariasDetalle.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FrameDonacionesVariasDetalle.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == newButton) {
                FrameDonacionesVariasDetalle.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                FrameDonacionesVariasDetalle.this.deleteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cboMiembro) {
                FrameDonacionesVariasDetalle.this.cboMiembroActionPerformed(evt);
            }
            else if (evt.getSource() == montoField) {
                FrameDonacionesVariasDetalle.this.montoFieldActionPerformed(evt);
            }
            else if (evt.getSource() == jButton2) {
                FrameDonacionesVariasDetalle.this.jButton2ActionPerformed(evt);
            }
            else if (evt.getSource() == cboForma) {
                FrameDonacionesVariasDetalle.this.cboFormaActionPerformed(evt);
            }
            else if (evt.getSource() == cmdBorrarAsiento) {
                FrameDonacionesVariasDetalle.this.cmdBorrarAsientoActionPerformed(evt);
            }
            else if (evt.getSource() == cmdAddAsiento) {
                FrameDonacionesVariasDetalle.this.cmdAddAsientoActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == txtCtaCte) {
                FrameDonacionesVariasDetalle.this.txtCtaCteFocusGained(evt);
            }
            else if (evt.getSource() == montoField) {
                FrameDonacionesVariasDetalle.this.montoFieldFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
        }

        public void keyPressed(java.awt.event.KeyEvent evt) {
        }

        public void keyReleased(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == txtCtaCte) {
                FrameDonacionesVariasDetalle.this.txtCtaCteKeyReleased(evt);
            }
            else if (evt.getSource() == cboMiembro) {
                FrameDonacionesVariasDetalle.this.cboMiembroKeyReleased(evt);
            }
            else if (evt.getSource() == montoField) {
                FrameDonacionesVariasDetalle.this.montoFieldKeyReleased(evt);
            }
            else if (evt.getSource() == cboForma) {
                FrameDonacionesVariasDetalle.this.cboFormaKeyReleased(evt);
            }
        }

        public void keyTyped(java.awt.event.KeyEvent evt) {
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == montoField) {
                FrameDonacionesVariasDetalle.this.montoFieldMouseClicked(evt);
            }
        }

        public void mouseEntered(java.awt.event.MouseEvent evt) {
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
        }

        public void mousePressed(java.awt.event.MouseEvent evt) {
        }

        public void mouseReleased(java.awt.event.MouseEvent evt) {
        }

        public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            if (evt.getSource() == FrameDonacionesVariasDetalle.this) {
                FrameDonacionesVariasDetalle.this.formInternalFrameActivated(evt);
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

        public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
        }

        public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            if (evt.getSource() == cboMiembro) {
                FrameDonacionesVariasDetalle.this.cboMiembroPopupMenuWillBecomeInvisible(evt);
            }
        }

        public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
        }
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("unchecked")
    void refresh() {
        try {

            entityManager.getTransaction().rollback();
            entityManager.getTransaction().begin();

            java.util.List data = queryDonacionesVariasDetalle.getResultList();
            data.stream().forEach((entity) -> {
                entityManager.refresh(entity);
            });
            listDonacionesVariasDetalle.clear();
            listDonacionesVariasDetalle.addAll(data);

            entityManager1.getTransaction().rollback();
            entityManager1.getTransaction().begin();
            data = queryMiembros.getResultList();
            data.stream().forEach((entity) -> {
                entityManager1.refresh(entity);
            });
            listMiembros.clear();
            listMiembros.addAll(data);
            eventListMiembros.clear();
            eventListMiembros.addAll(data);

            txtCtaCte.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try {
            int[] selected = masterTable.getSelectedRows();
            List<TblDonacionesVariasDetalle> toRemove = new ArrayList<>(selected.length);
            for (Integer idx = 0; idx < selected.length; idx++) {
                TblDonacionesVariasDetalle t = listDonacionesVariasDetalle.get(masterTable.convertRowIndexToModel(selected[idx]));
                toRemove.add(t);
                entityManager.remove(t);
            }
            listDonacionesVariasDetalle.removeAll(toRemove);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void newDetalle() {
        try {
            TblDonacionesVariasDetalle t = new TblDonacionesVariasDetalle();

            t.setIdUser(currentUser.getUser());

            entityManager.persist(t);
            listDonacionesVariasDetalle.add(t);
            Integer row = listDonacionesVariasDetalle.size() - 1;
            masterTable.setRowSelectionInterval(row, row);
            masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));
            cboForma.setSelectedIndex(0);
            txtCtaCte.requestFocusInWindow();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        newDetalle();
    }//GEN-LAST:event_newButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        save();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void save() {
        try {
            if (((Number) montoField.getValue()).intValue() == 0) {
                JOptionPane.showMessageDialog(null, "El monto no puede ser 0.");
                montoField.requestFocusInWindow();
                return;
            }
            /*for (TblDonacionesVariasDetalle evd : listDonacionesVariasDetalle) {
                if (entityManager.contains(evd)) {
                    if (evd.getTblAsientosList().size() == 2) {
                        ((List<TblAsientos>) evd.getTblAsientosList()).get(0).setMonto(((Long) (evd.getMonto().longValue() * evd.getIdEvento().getPorcentajeAporte().longValue() / 100)).intValue());
                        ((List<TblAsientos>) evd.getTblAsientosList()).get(1).setMonto(evd.getMonto() - ((List<TblAsientos>) evd.getTblAsientosList()).get(0).getMonto());
                        entityManager.merge(evd);
                    } else if (evd.getTblAsientosList().isEmpty()) {

                        List<TblAsientos> ts = evd.getTblAsientosList();
                        if (ts == null) {
                            ts = new LinkedList<>();
                            evd.setTblAsientosList((List) ts);
                        }
                        TblAsientos asientoAporte = new TblAsientos();
                        asientoAporte.setFechahora(evd.getIdEvento().getFecha().atStartOfDay());
                        asientoAporte.setIdCentroDeCostoDebe((TblCentrosDeCosto) entityManager.createQuery("SELECT t FROM TblCentrosDeCosto t WHERE t.preferido = true").getSingleResult());
                        asientoAporte.setIdCentroDeCostoHaber(asientoAporte.getIdCentroDeCostoDebe());
                        asientoAporte.setIdCuentaContableDebe(cuentasContablesPorDefecto.getIdCuentaACobrar());
                        asientoAporte.setIdCuentaContableHaber(cuentasContablesPorDefecto.getIdCuentaAportes());
                        asientoAporte.setMonto(((Long) (evd.getMonto().longValue() * evd.getIdEvento().getPorcentajeAporte().longValue() / 100)).intValue());
                        asientoAporte.setIdUser(currentUser.getUser());

                        ts.add(asientoAporte);

                        TblAsientos asientoDonacion = new TblAsientos();
                        asientoDonacion.setFechahora(evd.getIdEvento().getFecha().atStartOfDay());
                        asientoDonacion.setIdCentroDeCostoDebe((TblCentrosDeCosto) entityManager.createQuery("SELECT t FROM TblCentrosDeCosto t WHERE t.preferido = true").getSingleResult());
                        asientoDonacion.setIdCentroDeCostoHaber(asientoAporte.getIdCentroDeCostoDebe());
                        asientoDonacion.setIdCuentaContableDebe(cuentasContablesPorDefecto.getIdCuentaACobrar());
                        asientoDonacion.setIdCuentaContableHaber(cuentasContablesPorDefecto.getIdCuentaDonaciones());
                        asientoDonacion.setMonto(evd.getMonto() - asientoAporte.getMonto());
                        asientoDonacion.setIdUser(currentUser.getUser());

                        ts.add(asientoDonacion);

                        entityManager.merge(evd);
                    }
                }
            }*/

            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            refresh();
            //newButton.requestFocus();
        } catch (RollbackException ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());

            entityManager.getTransaction().begin();
            List<com.parah.mg.domain.TblDonacionesVariasDetalle> merged = new ArrayList<>(listDonacionesVariasDetalle.size());
            listDonacionesVariasDetalle.stream().forEach((t) -> {
                merged.add(entityManager.merge(t));
            });
            listDonacionesVariasDetalle.clear();
            listDonacionesVariasDetalle.addAll(merged);
        } catch (Exception exx) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + exx.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), exx);
        }

    }
    private void txtCtaCteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCtaCteKeyReleased
        try {
            /*if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
             cboMiembro.requestFocusInWindow();
             }*/
            txtCtaCte.setBackground(Color.white);
            if (txtCtaCte.getText().length() > 4) {
                List<TblEntidades> list = listMiembros;
                Optional<TblEntidades> value = list.stream().filter(a -> a.getCtacte().equals(Integer.valueOf(txtCtaCte.getText())))
                        .findFirst();
                if (value.isPresent()) {
                    cboMiembro.setSelectedItem(value.get());
                    txtCtaCte.setBackground(Color.green);
                    montoField.requestFocus();
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteKeyReleased

    private void txtCtaCteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtaCteFocusGained
        try {
            txtCtaCte.selectAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteFocusGained

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        refresh();
    }//GEN-LAST:event_formInternalFrameActivated

    @SuppressWarnings("unchecked")
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed

        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void montoFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_montoFieldFocusGained
        montoField.selectAll();
    }//GEN-LAST:event_montoFieldFocusGained

    private void montoFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_montoFieldMouseClicked

    }//GEN-LAST:event_montoFieldMouseClicked

    private void montoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_montoFieldActionPerformed

    }//GEN-LAST:event_montoFieldActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            List data = queryMiembros.getResultList();
            data.stream().forEach((entity) -> {
                entityManager1.refresh(entity);
            });
            listMiembros.clear();
            listMiembros.addAll(data);
            eventListMiembros.clear();
            eventListMiembros.addAll(data);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cboMiembroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMiembroActionPerformed
        try {
            if (cboMiembro.getSelectedItem() != null) {
                txtCtaCte.setText(((TblEntidades) cboMiembro.getSelectedItem()).getCtacte().toString());

            } else {
                txtCtaCte.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cboMiembroActionPerformed

    private void cboFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFormaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboFormaActionPerformed

    private void montoFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_montoFieldKeyReleased
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                if (((Number) montoField.getValue()).intValue() == 0) {
                    JOptionPane.showMessageDialog(null, "El monto no puede ser 0.");
                    montoField.requestFocusInWindow();
                    return;
                }
                if (cboMiembro.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "No ha eligido un donador.");
                    txtCtaCte.requestFocusInWindow();
                    return;
                }
                save();
                Integer reply = JOptionPane.showConfirmDialog(null, "Desea crear un nuevo registro?", title, JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    newDetalle();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_montoFieldKeyReleased

    private void cboFormaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboFormaKeyReleased
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                montoField.requestFocusInWindow();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cboFormaKeyReleased

    private void cboMiembroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboMiembroKeyReleased

    }//GEN-LAST:event_cboMiembroKeyReleased

    private void cboMiembroPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cboMiembroPopupMenuWillBecomeInvisible

    }//GEN-LAST:event_cboMiembroPopupMenuWillBecomeInvisible

    private void cmdBorrarAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBorrarAsientoActionPerformed
        try {
            Integer index = masterTable.getSelectedRow();
            TblDonacionesVariasDetalle T = listDonacionesVariasDetalle.get(masterTable.convertRowIndexToModel(index));
            List<TblAsientos> ts = T.getTblAsientosList();
            int[] selected = asientosTable.getSelectedRows();
            List<TblAsientos> toRemove = new ArrayList<>(selected.length);
            for (Integer idx = 0; idx < selected.length; idx++) {
                selected[idx] = asientosTable.convertRowIndexToModel(selected[idx]);
                Integer count = 0;
                Iterator<TblAsientos> iter = ts.iterator();
                while (count++ < selected[idx]) {
                    iter.next();
                }
                TblAsientos t = iter.next();
                toRemove.add(t);
                entityManager.remove(t);
            }
            ts.removeAll(toRemove);
            masterTable.clearSelection();
            masterTable.setRowSelectionInterval(index, index);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cmdBorrarAsientoActionPerformed

    private void cmdAddAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddAsientoActionPerformed
        try {
            addAsiento();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cmdAddAsientoActionPerformed

    private void addAsiento() {
        try {
            Integer index = masterTable.getSelectedRow();
            TblDonacionesVariasDetalle T = listDonacionesVariasDetalle.get(masterTable.convertRowIndexToModel(index));
            List<TblAsientos> ts = T.getTblAsientosList();
            if (ts == null) {
                ts = new LinkedList<>();
                T.setTblAsientosList((List) ts);
            }
            TblAsientos t = new TblAsientos();

            t.setFechahora(T.getFechahora() != null ? T.getFechahora() : null);
            t.setIdUser(currentUser.getUser());

            t.setIdCentroDeCostoDebe(centroDeCostoPreferido);
            t.setIdCentroDeCostoHaber(centroDeCostoPreferido);
            t.setIdCuentaContableDebe(listCuentasContablesPorDefecto.get(0).getIdCuentaACobrar());
            t.setIdCuentaContableHaber(listCuentasContablesPorDefecto.get(0).getIdCuentaDonaciones());

            if (ts.isEmpty()) {
                t.setMonto(T.getMonto());
            }

            ts.add(t);
            entityManager.merge(T);
            masterTable.clearSelection();
            masterTable.setRowSelectionInterval(index, index);
            Integer row = T.getTblAsientosList().size() - 1;
            asientosTable.setRowSelectionInterval(row, row);
            asientosTable.scrollRectToVisible(asientosTable.getCellRect(row, 0, true));
            if (asientosTable.getColumnModel().getColumnCount() > 0 && asientosTable.getRowCount() == 1) {
                asientosTable.getColumn("Centro de Costo Debe").setCellEditor(new DefaultCellEditor(cboCentroDeCostoDebe));
                asientosTable.getColumn("Centro de Costo Haber").setCellEditor(new DefaultCellEditor(cboCentroDeCostoHaber));
                asientosTable.getColumn("Cuenta Contable Debe").setCellEditor(new DefaultCellEditor(cboCuentaDebe));
                asientosTable.getColumn("Cuenta Contable Haber").setCellEditor(new DefaultCellEditor(cboCuentaHaber));
                asientosTable.getColumnModel().getColumn(4).setCellRenderer(numberCellRenderer1);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }

    private void createAsientoInicial() {
        try {
            addAsiento();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable asientosTable;
    private com.parah.mg.utils.CategoriasConverter categoriasConverter1;
    private javax.swing.JComboBox cboForma;
    private javax.swing.JComboBox cboMiembro;
    private javax.swing.JButton cmdAddAsiento;
    private javax.swing.JButton cmdBorrarAsiento;
    private com.parah.mg.utils.CtaCteTableCellRenderer ctaCteTableCellRenderer1;
    private com.parah.mg.utils.DateTimeTableCellRenderer dateTableCellRenderer1;
    private com.parah.mg.utils.DateTimeTableCellRenderer dateTimeTableCellRenderer1;
    private com.parah.mg.utils.DateTimeToStringConverter dateTimeToStringConverter1;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel descripcionLabel;
    private org.jdesktop.swingx.JXDatePicker dtpFecha;
    private javax.persistence.EntityManager entityManager;
    private javax.persistence.EntityManager entityManager1;
    private javax.swing.JLabel fechahoraLabel;
    private javax.swing.JLabel idMiembroLabel;
    private javax.swing.JLabel idMiembroLabel1;
    private javax.swing.JLabel idMiembroLabel2;
    private com.parah.mg.utils.IntegerLongConverter integerLongConverter1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private java.util.List<com.parah.mg.domain.TblCentrosDeCosto> listCentrosDeCosto;
    private java.util.List<com.parah.mg.domain.TblCuentasContables> listCuentasContables;
    private java.util.List<com.parah.mg.domain.TblCuentasContablesPorDefecto> listCuentasContablesPorDefecto;
    private java.util.List<com.parah.mg.domain.TblDonacionesVariasDetalle> listDonacionesVariasDetalle;
    private java.util.List listMiembros;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private javax.swing.JFormattedTextField montoField;
    private javax.swing.JLabel montoLabel;
    private javax.swing.JLabel montoLabel1;
    private javax.swing.JLabel montoLabel6;
    private javax.swing.JButton newButton;
    private com.parah.mg.utils.NormalTableCellRenderer normalTableCellRenderer1;
    private com.parah.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query queryCentrosDeCosto;
    private javax.persistence.Query queryCuentasContables;
    private javax.persistence.Query queryCuentasContablesPorDefecto;
    private javax.persistence.Query queryDonacionesVariasDetalle;
    private javax.persistence.Query queryMiembros;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private java.util.List<com.parah.mg.domain.TblCategoriasArticulos> tblCategoriasArticulosList;
    private javax.persistence.Query tblCategoriasArticulosQuery;
    private java.util.List<com.parah.mg.domain.TblCategoriasArticulos> tblFormasDePagoList;
    private javax.persistence.Query tblFormasDePagoQuery;
    private javax.swing.JTextField txtCtaCte;
    private javax.swing.JTextField txtObservacion;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());

                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameDonacionesVariasDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameDonacionesVariasDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameDonacionesVariasDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameDonacionesVariasDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new FrameDonacionesVariasDetalle());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
