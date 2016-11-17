/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames.operaciones.egresos;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.parah.mg.domain.TblAsientos;
import com.parah.mg.domain.TblCentrosDeCosto;
import com.parah.mg.domain.TblContribuyentes;
import com.parah.mg.domain.TblCuentasContables;
import com.parah.mg.domain.TblRecibosCompra;
import com.parah.mg.domain.TblTimbradosCompras;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import com.parah.utils.CalcDV;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public class FrameRecibosCompra extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameRecibosCompra.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    String databaseIP;
    Map<String, String> persistenceMap = new HashMap<>();
    JComboBox<TblCentrosDeCosto> cboCentroDeCostoDebe = new JComboBox();
    JComboBox<TblCentrosDeCosto> cboCentroDeCostoHaber = new JComboBox();
    JComboBox<TblCuentasContables> cboCuentaHaber = new JComboBox();
    JComboBox<TblCuentasContables> cboCuentaDebe = new JComboBox();
    TblCentrosDeCosto centroDeCostoPreferido;
    DatePickerSettings datePickerSettings = new DatePickerSettings(Locale.getDefault());
    DatePickerSettings datePickerSettings1 = new DatePickerSettings(Locale.getDefault());
    DatePickerSettings datePickerSettings2 = new DatePickerSettings(Locale.getDefault());
    TblTimbradosCompras timbradoCompras;

    public FrameRecibosCompra() {
        super("Recibos Compras",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {
            persistenceMap = Utils.getInstance().getPersistenceMap();
            datePickerSettings1.setFormatForDatesCommonEra("dd/MM/yyyy");
            initComponents();

            if (!Beans.isDesignTime()) {
                entityManager.getTransaction().begin();
            }

            centroDeCostoPreferido = (TblCentrosDeCosto) entityManager.createQuery("SELECT t FROM TblCentrosDeCosto t WHERE t.preferido = true").getSingleResult();

            TableFilterHeader filterHeader = new TableFilterHeader(masterTable, AutoChoices.DISABLED);
            filterHeader.setPosition(TableFilterHeader.Position.TOP);
            filterHeader.setAdaptiveChoices(false);
            filterHeader.getParserModel().setIgnoreCase(true);

            AutoCompleteSupport support2 = AutoCompleteSupport.install(cboCentroDeCostoDebe, GlazedLists.eventListOf(listCentrosDeCosto.toArray()));
            support2.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support5 = AutoCompleteSupport.install(cboCentroDeCostoHaber, GlazedLists.eventListOf(listCentrosDeCosto.toArray()));
            support5.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support3 = AutoCompleteSupport.install(cboCuentaDebe, GlazedLists.eventListOf(listCuentasContables.toArray()));
            support3.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support4 = AutoCompleteSupport.install(cboCuentaHaber, GlazedLists.eventListOf(listCuentasContables.toArray()));
            support4.setFilterMode(TextMatcherEditor.CONTAINS);

            rucField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    process();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    process();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    process();
                }

                public void process() {
                    if (rucField.getText().length() > 6) {
                        if (CalcDV.isValidRUC(rucField.getText())) {
                            TblContribuyentes c = entityManager.find(TblContribuyentes.class, CalcDV.getRUCsinDVfromRUC(rucField.getText()));
                            if (c != null) {
                                txtRazonSocial.setText(c.getRazonSocial());
                            }
                        } else {
                            txtRazonSocial.setText("");
                        }
                    }
                }
            });

            txtMonto.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    process();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    process();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    process();
                }

                public void process() {
                    try {
                        if (txtMonto.getText().length() > 0 && txtMonto.getValue() != null) {
                            updateAsientoInicial();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
                        LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
                    }
                }
            });

            masterTable.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
                try {
                    if (!lse.getValueIsAdjusting()) {
                        if (asientosTable.getColumnModel().getColumnCount() == 5) {
                            asientosTable.getColumn("Centro de Costo Debe").setCellEditor(new DefaultCellEditor(cboCentroDeCostoDebe));
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
            });
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addPropertyChangeListener("permanentFocusOwner", (final PropertyChangeEvent e) -> {
                        try {
                            if (e.getNewValue() instanceof JFormattedTextField) {
                                SwingUtilities.invokeLater(() -> {
                                    JFormattedTextField textField = (JFormattedTextField) e.getNewValue();
                                    textField.selectAll();
                                });
                            } else if (e.getNewValue() instanceof JTextField) {
                                SwingUtilities.invokeLater(() -> {
                                    JTextField textField = (JTextField) e.getNewValue();
                                    textField.selectAll();
                                });
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
                            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
                        }
                    });
            
                        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
            KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
            KeyStroke ctrlTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.CTRL_DOWN_MASK);
            Set<KeyStroke> keys = new HashSet<>();
            keys.add(enter);
            keys.add(tab);
            keys.add(ctrlTab);
            KeyboardFocusManager.getCurrentKeyboardFocusManager().setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keys);

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
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblRecibosCompra t order by t.fechahora");
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        queryContribuyentes = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        listContribuyentes = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryContribuyentes.getResultList());
        queryCuentasContables = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCuentasContables t where t.imputable = true");
        listCuentasContables = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryCuentasContables.getResultList());
        queryCentrosDeCosto = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCentrosDeCosto t");
        listCentrosDeCosto = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryCentrosDeCosto.getResultList());
        numberCellRenderer1 = new com.parah.mg.utils.NumberCellRenderer();
        rucTableCellRenderer1 = new com.parah.mg.utils.RucTableCellRenderer();
        condicionFacturaTableCellRenderer1 = new com.parah.mg.utils.CondicionFacturaTableCellRenderer();
        integerLongConverter1 = new com.parah.mg.utils.IntegerLongConverter();
        buttonGroup1 = new javax.swing.ButtonGroup();
        queryCuentasContablesPorDefecto = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCuentasContablesPorDefecto t");
        listCuentasContablesPorDefecto = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryCuentasContablesPorDefecto.getResultList());
        inverseBooleanConverter1 = new com.parah.mg.utils.InverseBooleanConverter();
        queryTimbrados = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblTimbradosCompras t");
        listTimbrados = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryTimbrados.getResultList());
        dateTableCellRenderer1 = new com.parah.mg.utils.DateTableCellRenderer();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        fechahoraLabel = new javax.swing.JLabel();
        conceptoLabel = new javax.swing.JLabel();
        montoLabel = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        idMiembroLabel = new javax.swing.JLabel();
        conceptoField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        rucField = new javax.swing.JTextField();
        idMiembroLabel2 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JFormattedTextField();
        idMiembroLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        asientosTable = new javax.swing.JTable();
        cmdBorrarAsiento = new javax.swing.JButton();
        cmdAddAsiento = new javax.swing.JButton();
        txtRazonSocial = new javax.swing.JTextField();
        montoLabel6 = new javax.swing.JLabel();
        dtpFecha = new DatePicker(datePickerSettings1);
        txtNro = new javax.swing.JTextField();

        FormListener formListener = new FormListener();

        numberCellRenderer1.setText("numberCellRenderer1");

        rucTableCellRenderer1.setText("rucTableCellRenderer1");

        condicionFacturaTableCellRenderer1.setText("condicionFacturaTableCellRenderer1");

        dateTableCellRenderer1.setText("dateTableCellRenderer1");

        masterTable.setAutoCreateRowSorter(true);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nro}"));
        columnBinding.setColumnName("Nro");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha");
        columnBinding.setColumnClass(java.time.LocalDate.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${razonSocial}"));
        columnBinding.setColumnName("Razon Social");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ruc}"));
        columnBinding.setColumnName("RUC");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${monto}"));
        columnBinding.setColumnName("Importe");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            masterTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            masterTable.getColumnModel().getColumn(1).setCellRenderer(dateTableCellRenderer1);
            masterTable.getColumnModel().getColumn(2).setPreferredWidth(250);
            masterTable.getColumnModel().getColumn(3).setPreferredWidth(50);
            masterTable.getColumnModel().getColumn(4).setPreferredWidth(100);
            masterTable.getColumnModel().getColumn(4).setCellRenderer(numberCellRenderer1);
        }

        fechahoraLabel.setText("Fecha:");

        conceptoLabel.setText("Observacion:");

        montoLabel.setText("Importe:");

        idLabel.setDisplayedMnemonic('N');
        idLabel.setText("Nro Recibo:");

        idMiembroLabel.setText("Proveedor:");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.observacion}"), conceptoField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), conceptoField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        saveButton.setText("Guardar");
        saveButton.addActionListener(formListener);

        refreshButton.setText("Cancelar");
        refreshButton.addActionListener(formListener);

        newButton.setMnemonic('u');
        newButton.setText("Nuevo");
        newButton.addActionListener(formListener);

        deleteButton.setText("Eliminar");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), deleteButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        deleteButton.addActionListener(formListener);

        rucField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.ruc}"), rucField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), rucField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        rucField.addFocusListener(formListener);
        rucField.addActionListener(formListener);
        rucField.addKeyListener(formListener);

        idMiembroLabel2.setText("Razon Social: ");
        idMiembroLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtMonto.setColumns(9);
        txtMonto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtMonto.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtMonto.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.monto}"), txtMonto, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(integerLongConverter1);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtMonto, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtMonto.addFocusListener(formListener);
        txtMonto.addMouseListener(formListener);
        txtMonto.addActionListener(formListener);

        idMiembroLabel3.setText("RUC:");
        idMiembroLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        asientosTable.setAutoCreateRowSorter(true);
        asientosTable.setRowHeight(20);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${selectedElement.tblAsientosList}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, eLProperty, asientosTable);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCentroDeCostoDebe}"));
        columnBinding.setColumnName("Centro de Costo Debe");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCentrosDeCosto.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCuentaContableDebe}"));
        columnBinding.setColumnName("Cuenta Contable Debe");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCuentasContables.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCentroDeCostoHaber}"));
        columnBinding.setColumnName("Centro de Costo Haber");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCentrosDeCosto.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCuentaContableHaber}"));
        columnBinding.setColumnName("Cuenta Contable Haber");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCuentasContables.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${monto}"));
        columnBinding.setColumnName("Importe");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), asientosTable, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(asientosTable);

        cmdBorrarAsiento.setText("-");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cmdBorrarAsiento, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cmdBorrarAsiento.addActionListener(formListener);

        cmdAddAsiento.setText("+");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cmdAddAsiento, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cmdAddAsiento.addActionListener(formListener);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.razonSocial}"), txtRazonSocial, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtRazonSocial, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        montoLabel6.setText("Asientos");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.fechahora}"), dtpFecha, org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), dtpFecha, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.nro}"), txtNro, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtNro, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

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
                        .addComponent(newButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton))
                    .addComponent(montoLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmdAddAsiento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdBorrarAsiento))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(conceptoLabel)
                                .addGap(8, 8, 8)
                                .addComponent(conceptoField, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(montoLabel)
                                .addGap(18, 18, 18)
                                .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(idLabel)
                                    .addComponent(fechahoraLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtNro)
                                        .addGap(18, 18, 18))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idMiembroLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idMiembroLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rucField, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idMiembroLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, refreshButton, saveButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(masterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idLabel)
                            .addComponent(txtNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idMiembroLabel)
                            .addComponent(rucField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idMiembroLabel2)
                            .addComponent(idMiembroLabel3)
                            .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fechahoraLabel)
                            .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(montoLabel)))
                .addComponent(montoLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdAddAsiento)
                    .addComponent(cmdBorrarAsiento))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conceptoLabel)
                    .addComponent(conceptoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(refreshButton)
                    .addComponent(deleteButton)
                    .addComponent(newButton))
                .addContainerGap())
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.KeyListener, java.awt.event.MouseListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                FrameRecibosCompra.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FrameRecibosCompra.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == newButton) {
                FrameRecibosCompra.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                FrameRecibosCompra.this.deleteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == rucField) {
                FrameRecibosCompra.this.rucFieldActionPerformed(evt);
            }
            else if (evt.getSource() == txtMonto) {
                FrameRecibosCompra.this.txtMontoActionPerformed(evt);
            }
            else if (evt.getSource() == cmdBorrarAsiento) {
                FrameRecibosCompra.this.cmdBorrarAsientoActionPerformed(evt);
            }
            else if (evt.getSource() == cmdAddAsiento) {
                FrameRecibosCompra.this.cmdAddAsientoActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == rucField) {
                FrameRecibosCompra.this.rucFieldFocusGained(evt);
            }
            else if (evt.getSource() == txtMonto) {
                FrameRecibosCompra.this.txtMontoFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
        }

        public void keyPressed(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == rucField) {
                FrameRecibosCompra.this.rucFieldKeyPressed(evt);
            }
        }

        public void keyReleased(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == rucField) {
                FrameRecibosCompra.this.rucFieldKeyReleased(evt);
            }
        }

        public void keyTyped(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == rucField) {
                FrameRecibosCompra.this.rucFieldKeyTyped(evt);
            }
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == txtMonto) {
                FrameRecibosCompra.this.txtMontoMouseClicked(evt);
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
    }// </editor-fold>//GEN-END:initComponents

    void refresh() {
        try {
            entityManager.getTransaction().rollback();
            entityManager.getTransaction().begin();
            java.util.List data = query.getResultList();
            data.stream().forEach((entity) -> {
                entityManager.refresh(entity);
            });
            list.clear();
            list.addAll(data);

            data = queryContribuyentes.getResultList();
            data.stream().forEach((entity) -> {
                entityManager.refresh(entity);
            });
            listContribuyentes.clear();
            listContribuyentes.addAll(data);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);

        }
    }

    @SuppressWarnings("unchecked")
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try {
            int[] selected = masterTable.getSelectedRows();
            List<TblRecibosCompra> toRemove = new ArrayList<>(selected.length);
            for (Integer idx = 0; idx < selected.length; idx++) {
                TblRecibosCompra t = list.get(masterTable.convertRowIndexToModel(selected[idx]));
                toRemove.add(t);
                entityManager.remove(t);
            }
            list.removeAll(toRemove);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        try {
            if (checkDatosRecibo()) {
                TblRecibosCompra t = new TblRecibosCompra();
                entityManager.persist(t);
                t.setIdUser(currentUser.getUser());
                t.setMonto(0);
                txtMonto.setValue(0);
                list.add(t);

                Integer row = list.size() - 1;
                masterTable.setRowSelectionInterval(row, row);
                masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));
                createAsientoInicial();
                txtNro.requestFocusInWindow();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_newButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {
            if (checkDatosRecibo()) {

                list.stream().forEach((rc) -> {
                    rc.getTblAsientosList().stream().forEach((a) -> {
                        a.setFechahora(rc.getFechahora().atStartOfDay());
                    });
                });
                entityManager.getTransaction().commit();
                entityManager.getTransaction().begin();
                java.util.List data = query.getResultList();
                data.stream().forEach((entity) -> {
                    entityManager.refresh(entity);
                });
                list.clear();
                list.addAll(data);
            }
        } catch (RollbackException ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            entityManager.getTransaction().begin();
            List<TblRecibosCompra> merged = new ArrayList<>(list.size());
            list.stream().forEach((t) -> {
                merged.add(entityManager.merge(t));
            });
            list.clear();
            list.addAll(merged);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void rucFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rucFieldFocusGained
        try {
            rucField.selectAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rucFieldFocusGained

    private void rucFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rucFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rucFieldActionPerformed

    private void rucFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucFieldKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_rucFieldKeyPressed

    private void rucFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucFieldKeyReleased

    }//GEN-LAST:event_rucFieldKeyReleased

    private void rucFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucFieldKeyTyped

        // TODO add your handling code here:
    }//GEN-LAST:event_rucFieldKeyTyped

    private void txtMontoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMontoFocusGained
        try {
            txtMonto.selectAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_txtMontoFocusGained

    private void txtMontoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMontoMouseClicked

    }//GEN-LAST:event_txtMontoMouseClicked

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed

    }//GEN-LAST:event_txtMontoActionPerformed

    private void dtpVencimientoTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dtpVencimientoTimbradoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dtpVencimientoTimbradoActionPerformed

    private void cmdBorrarAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBorrarAsientoActionPerformed
        try {
            Integer index = masterTable.getSelectedRow();
            TblRecibosCompra T = list.get(masterTable.convertRowIndexToModel(index));
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
            TblRecibosCompra T = list.get(masterTable.convertRowIndexToModel(index));
            List<TblAsientos> ts = T.getTblAsientosList();
            if (ts == null) {
                ts = new LinkedList<>();
                T.setTblAsientosList((List) ts);
            }
            TblAsientos t = new TblAsientos();

            t.setFechahora(T.getFechahora() != null ? T.getFechahora().atStartOfDay() : null);
            t.setIdUser(currentUser.getUser());

            t.setIdCentroDeCostoDebe(centroDeCostoPreferido);
            t.setIdCentroDeCostoHaber(centroDeCostoPreferido);
            t.setIdCuentaContableDebe(listCuentasContablesPorDefecto.get(0).getIdCuentaHaberComprasFacturaCredito());
            t.setIdCuentaContableHaber(t.getIdCentroDeCostoHaber().getIdCuentaContableCtaCtePorDefecto());

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

    private void updateAsientoInicial() {
        try {
            if (masterTable.getSelectedRow() > -1) {
                Integer index = masterTable.getSelectedRow();
                TblRecibosCompra T = list.get(masterTable.convertRowIndexToModel(index));
                List<TblAsientos> ts = (List) T.getTblAsientosList();
                if (ts != null) {
                    if (asientosTable.getModel().getRowCount() == 1) {
                        asientosTable.getModel().setValueAt(T.getMonto(), 0, 4);
                        TblAsientos asiento = ts.get(0);
                        asiento.setFechahora(T.getFechahora() != null ? T.getFechahora().atStartOfDay() : null);
                        //asiento.setMonto(T.getMontoExentas() + T.getMontoIva5() + T.getMontoIva10());
                        entityManager.merge(asiento);
                    }
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }

    private Boolean checkDatosRecibo() {
        try {
            Integer index = masterTable.getSelectedRow();
            if (index != -1) {
                /*if (!txtNro.getText().matches("^\\d{3}-\\d{3}-\\d{7}")) {
                    JOptionPane.showMessageDialog(null, "El nro de factura no es valido.");
                    return false;
                }*/

                //Importe TOTAL cuadrar
                TblRecibosCompra T = list.get(masterTable.convertRowIndexToModel(index));
                Integer sumaAsientos = 0;

                if (T.getNro() == null || T.getNro().equals("")) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un numero de recibo valido.");
                    return false;
                }
                if (T.getFechahora() == null) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar una facha valida.");
                    return false;
                }

                sumaAsientos = T.getTblAsientosList().stream().map((a) -> a.getMonto()).reduce(sumaAsientos, Integer::sum);
                if (T.getMonto() - sumaAsientos != 0) {
                    JOptionPane.showMessageDialog(null, "El importe total del recibo debe coincidir con los asientos.");
                    return false;
                }

            }
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            return false;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable asientosTable;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cmdAddAsiento;
    private javax.swing.JButton cmdBorrarAsiento;
    private javax.swing.JTextField conceptoField;
    private javax.swing.JLabel conceptoLabel;
    private com.parah.mg.utils.CondicionFacturaTableCellRenderer condicionFacturaTableCellRenderer1;
    private com.parah.mg.utils.DateTableCellRenderer dateTableCellRenderer1;
    private javax.swing.JButton deleteButton;
    private com.github.lgooddatepicker.components.DatePicker dtpFecha;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel fechahoraLabel;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel idMiembroLabel;
    private javax.swing.JLabel idMiembroLabel2;
    private javax.swing.JLabel idMiembroLabel3;
    private com.parah.mg.utils.IntegerLongConverter integerLongConverter1;
    private com.parah.mg.utils.InverseBooleanConverter inverseBooleanConverter1;
    private javax.swing.JScrollPane jScrollPane1;
    private java.util.List<com.parah.mg.domain.TblRecibosCompra> list;
    private java.util.List<com.parah.mg.domain.TblCentrosDeCosto> listCentrosDeCosto;
    private java.util.List<com.parah.mg.domain.TblContribuyentes> listContribuyentes;
    private java.util.List<com.parah.mg.domain.TblCuentasContables> listCuentasContables;
    private java.util.List<com.parah.mg.domain.TblCuentasContablesPorDefecto> listCuentasContablesPorDefecto;
    private java.util.List<com.parah.mg.domain.TblFacturasCompra> listTimbrados;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private javax.swing.JLabel montoLabel;
    private javax.swing.JLabel montoLabel6;
    private javax.swing.JButton newButton;
    private com.parah.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query query;
    private javax.persistence.Query queryCentrosDeCosto;
    private javax.persistence.Query queryContribuyentes;
    private javax.persistence.Query queryCuentasContables;
    private javax.persistence.Query queryCuentasContablesPorDefecto;
    private javax.persistence.Query queryTimbrados;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField rucField;
    private com.parah.mg.utils.RucTableCellRenderer rucTableCellRenderer1;
    private javax.swing.JButton saveButton;
    private javax.swing.JFormattedTextField txtMonto;
    private javax.swing.JTextField txtNro;
    private javax.swing.JTextField txtRazonSocial;
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
            java.util.logging.Logger.getLogger(FrameRecibosCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameRecibosCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameRecibosCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameRecibosCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setContentPane(new FrameRecibosCompra());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }

}
