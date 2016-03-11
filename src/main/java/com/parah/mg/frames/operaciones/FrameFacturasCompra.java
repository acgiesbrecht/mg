/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames.operaciones;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.parah.mg.domain.TblAsientos;
import com.parah.mg.domain.TblCentrosDeCosto;
import com.parah.mg.domain.TblContribuyentes;
import com.parah.mg.domain.TblCuentasContables;
import com.parah.mg.domain.TblFacturasCompra;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import com.parah.utils.CalcDV;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public class FrameFacturasCompra extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameFacturasCompra.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    String databaseIP;
    Map<String, String> persistenceMap = new HashMap<>();
    JComboBox<TblCentrosDeCosto> cboCentroDeCosto = new JComboBox();
    JComboBox<TblCuentasContables> cboCuentaHaber = new JComboBox();
    JComboBox<TblCuentasContables> cboCuentaDebe = new JComboBox();

    public FrameFacturasCompra() {
        super("Facturas Compras",
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

            TableFilterHeader filterHeader = new TableFilterHeader(masterTable, AutoChoices.ENABLED);
            filterHeader.setPosition(TableFilterHeader.Position.TOP);
            filterHeader.setAdaptiveChoices(false);

            AutoCompleteSupport support2 = AutoCompleteSupport.install(cboCentroDeCosto, GlazedLists.eventListOf(listCentrosDeCosto.toArray()));
            support2.setFilterMode(TextMatcherEditor.CONTAINS);

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
                            TblContribuyentes c = entityManager.find(TblContribuyentes.class, rucField.getText().substring(0, rucField.getText().length() - 2));
                            if (c != null) {
                                txtRazonSocial.setText(c.getRazonSocial());
                            }
                        } else {
                            txtRazonSocial.setText("");
                        }
                    }
                }
            });

            txtMontoIVA5.getDocument().addDocumentListener(new DocumentListener() {
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
                        if (txtMontoIVA5.getText().length() > 0 && txtMontoIVA5.getValue() != null) {
                            Integer i = ((Number) txtMontoIVA5.getValue()).intValue();
                            Integer x = ((Number) Math.round(i / 21.0)).intValue();
                            txtIVA5.setValue(x);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
                        LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
                    }
                }
            });

            txtMontoIVA10.getDocument().addDocumentListener(new DocumentListener() {
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
                        if (txtMontoIVA10.getText().length() > 0 && txtMontoIVA10.getValue() != null) {
                            Integer i = ((Number) txtMontoIVA10.getValue()).intValue();
                            Integer x = ((Number) Math.round(i / 11.0)).intValue();
                            txtIVA10.setValue(x);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
                        LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
                    }
                }
            });

            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addPropertyChangeListener("permanentFocusOwner", new PropertyChangeListener() {
                        @Override
                        public void propertyChange(final PropertyChangeEvent e) {
                            try {
                                if (e.getNewValue() instanceof JFormattedTextField) {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        public void run() {
                                            JFormattedTextField textField = (JFormattedTextField) e.getNewValue();
                                            textField.selectAll();
                                        }
                                    });
                                } else if (e.getNewValue() instanceof JTextField) {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        public void run() {
                                            JTextField textField = (JTextField) e.getNewValue();
                                            textField.selectAll();
                                        }
                                    });
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
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblFacturasCompra t");
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
        dtpFecha = new org.jdesktop.swingx.JXDatePicker();
        txtExentas = new javax.swing.JFormattedTextField();
        idMiembroLabel3 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        txtTimbrado = new javax.swing.JFormattedTextField();
        idLabel1 = new javax.swing.JLabel();
        dtpVencimientoTimbrado = new org.jdesktop.swingx.JXDatePicker();
        fechahoraLabel1 = new javax.swing.JLabel();
        txtMontoIVA5 = new javax.swing.JFormattedTextField();
        montoLabel1 = new javax.swing.JLabel();
        txtMontoIVA10 = new javax.swing.JFormattedTextField();
        montoLabel2 = new javax.swing.JLabel();
        txtIVA5 = new javax.swing.JFormattedTextField();
        montoLabel3 = new javax.swing.JLabel();
        txtIVA10 = new javax.swing.JFormattedTextField();
        montoLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        asientosTable = new javax.swing.JTable();
        montoLabel5 = new javax.swing.JLabel();
        cmdBorrarAsiento = new javax.swing.JButton();
        cmdAddAsiento = new javax.swing.JButton();
        txtRazonSocial = new javax.swing.JTextField();
        rbContado = new javax.swing.JRadioButton();
        rbCredito = new javax.swing.JRadioButton();

        FormListener formListener = new FormListener();

        numberCellRenderer1.setText("numberCellRenderer1");

        rucTableCellRenderer1.setText("rucTableCellRenderer1");

        condicionFacturaTableCellRenderer1.setText("condicionFacturaTableCellRenderer1");

        addInternalFrameListener(formListener);

        masterTable.setAutoCreateRowSorter(true);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nro}"));
        columnBinding.setColumnName("Nro");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nroTimbrado}"));
        columnBinding.setColumnName("Timbrado");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${razonSocial}"));
        columnBinding.setColumnName("Razon Social");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ruc}"));
        columnBinding.setColumnName("RUC");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${montoExentas}"));
        columnBinding.setColumnName("Importe Exentas");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${montoIva5}"));
        columnBinding.setColumnName("Importe IVA 5%");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${montoIva10}"));
        columnBinding.setColumnName("Importe IVA 10%");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${condicionContado}"));
        columnBinding.setColumnName("Condicion");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(5).setCellRenderer(numberCellRenderer1);
            masterTable.getColumnModel().getColumn(6).setCellRenderer(numberCellRenderer1);
            masterTable.getColumnModel().getColumn(7).setCellRenderer(numberCellRenderer1);
            masterTable.getColumnModel().getColumn(8).setCellRenderer(condicionFacturaTableCellRenderer1);
        }

        fechahoraLabel.setText("Fecha:");

        conceptoLabel.setText("Observacion:");

        montoLabel.setText("Monto Exentas:");

        idLabel.setDisplayedMnemonic('N');
        idLabel.setText("Nro Factura:");

        idMiembroLabel.setText("Proveedor:");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.concepto}"), conceptoField, org.jdesktop.beansbinding.BeanProperty.create("text"));
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

        idMiembroLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel2.setText("Razon Social: ");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.fechahora}"), dtpFecha, org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), dtpFecha, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtExentas.setColumns(9);
        txtExentas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtExentas.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtExentas.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.montoExentas}"), txtExentas, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(integerLongConverter1);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtExentas, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtExentas.addFocusListener(formListener);
        txtExentas.addMouseListener(formListener);
        txtExentas.addActionListener(formListener);

        idMiembroLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel3.setText("RUC:");

        try {
            jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-#######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), jFormattedTextField1, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtTimbrado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.nroTimbrado}"), txtTimbrado, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(integerLongConverter1);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtTimbrado, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        idLabel1.setDisplayedMnemonic('N');
        idLabel1.setText("Nro Timbrado:");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.vencimientoTimbrado}"), dtpVencimientoTimbrado, org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), dtpVencimientoTimbrado, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        fechahoraLabel1.setText("Vencimiento Timbrado:");

        txtMontoIVA5.setColumns(9);
        txtMontoIVA5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtMontoIVA5.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtMontoIVA5.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.montoIva5}"), txtMontoIVA5, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(integerLongConverter1);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtMontoIVA5, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtMontoIVA5.addFocusListener(formListener);
        txtMontoIVA5.addMouseListener(formListener);
        txtMontoIVA5.addActionListener(formListener);

        montoLabel1.setText("Monto incl. IVA 5%:");

        txtMontoIVA10.setColumns(9);
        txtMontoIVA10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtMontoIVA10.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtMontoIVA10.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.montoIva10}"), txtMontoIVA10, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(integerLongConverter1);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtMontoIVA10, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtMontoIVA10.addFocusListener(formListener);
        txtMontoIVA10.addMouseListener(formListener);
        txtMontoIVA10.addActionListener(formListener);

        montoLabel2.setText("Monto incl. IVA 10%:");

        txtIVA5.setColumns(9);
        txtIVA5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtIVA5.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtIVA5.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.iva5}"), txtIVA5, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(integerLongConverter1);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtIVA5, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtIVA5.addFocusListener(formListener);
        txtIVA5.addMouseListener(formListener);
        txtIVA5.addActionListener(formListener);

        montoLabel3.setText("Monto incl. IVA 5%:");

        txtIVA10.setColumns(9);
        txtIVA10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtIVA10.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtIVA10.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.iva10}"), txtIVA10, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(integerLongConverter1);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtIVA10, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtIVA10.addFocusListener(formListener);
        txtIVA10.addMouseListener(formListener);
        txtIVA10.addActionListener(formListener);

        montoLabel4.setText("Monto incl. IVA 10%:");

        asientosTable.setRowHeight(20);
        asientosTable.setRowSelectionAllowed(false);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${selectedElement.tblAsientosList}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, eLProperty, asientosTable);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCentroDeCosto}"));
        columnBinding.setColumnName("Centro de Costo");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCentrosDeCosto.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCuentaContableDebe}"));
        columnBinding.setColumnName("Cuenta Contable Debe");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCuentasContables.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCuentaContableHaber}"));
        columnBinding.setColumnName("Cuenta Contable Haber");
        columnBinding.setColumnClass(com.parah.mg.domain.TblCuentasContables.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${monto}"));
        columnBinding.setColumnName("Importe");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(asientosTable);
        if (asientosTable.getColumnModel().getColumnCount() > 0) {
            asientosTable.getColumnModel().getColumn(3).setCellRenderer(numberCellRenderer1);
        }

        montoLabel5.setText("Asientos");

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

        rbContado.setSelected(true);
        rbContado.setText("Contado");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.condicionContado}"), rbContado, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        rbCredito.setText("Credito");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtExentas, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(montoLabel2)
                                                .addGap(10, 10, 10)
                                                .addComponent(txtMontoIVA10, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(montoLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtMontoIVA5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(montoLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(montoLabel4)
                                        .addGap(12, 12, 12)
                                        .addComponent(txtIVA10, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(montoLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIVA5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(montoLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmdAddAsiento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdBorrarAsiento)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
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
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idMiembroLabel)
                                .addGap(42, 42, 42)
                                .addComponent(idMiembroLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rucField, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(idMiembroLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRazonSocial))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(fechahoraLabel)
                                            .addComponent(idLabel))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rbContado)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rbCredito))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(idLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(fechahoraLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(dtpVencimientoTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(conceptoLabel)
                                            .addGap(8, 8, 8)
                                            .addComponent(conceptoField))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 207, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, refreshButton, saveButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(masterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(idLabel)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fechahoraLabel1)
                        .addComponent(dtpVencimientoTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fechahoraLabel)
                    .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbContado)
                    .addComponent(rbCredito))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idMiembroLabel)
                    .addComponent(rucField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idMiembroLabel2)
                    .addComponent(idMiembroLabel3)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(montoLabel)
                            .addComponent(txtExentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(montoLabel1)
                            .addComponent(txtMontoIVA5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMontoIVA10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(montoLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(montoLabel3)
                            .addComponent(txtIVA5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIVA10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(montoLabel4))))
                .addGap(18, 18, 18)
                .addComponent(montoLabel5)
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
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(refreshButton)
                    .addComponent(deleteButton)
                    .addComponent(newButton))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.KeyListener, java.awt.event.MouseListener, javax.swing.event.InternalFrameListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                FrameFacturasCompra.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FrameFacturasCompra.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == newButton) {
                FrameFacturasCompra.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                FrameFacturasCompra.this.deleteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == rucField) {
                FrameFacturasCompra.this.rucFieldActionPerformed(evt);
            }
            else if (evt.getSource() == txtExentas) {
                FrameFacturasCompra.this.txtExentasActionPerformed(evt);
            }
            else if (evt.getSource() == txtMontoIVA5) {
                FrameFacturasCompra.this.txtMontoIVA5ActionPerformed(evt);
            }
            else if (evt.getSource() == txtMontoIVA10) {
                FrameFacturasCompra.this.txtMontoIVA10ActionPerformed(evt);
            }
            else if (evt.getSource() == txtIVA5) {
                FrameFacturasCompra.this.txtIVA5ActionPerformed(evt);
            }
            else if (evt.getSource() == txtIVA10) {
                FrameFacturasCompra.this.txtIVA10ActionPerformed(evt);
            }
            else if (evt.getSource() == cmdBorrarAsiento) {
                FrameFacturasCompra.this.cmdBorrarAsientoActionPerformed(evt);
            }
            else if (evt.getSource() == cmdAddAsiento) {
                FrameFacturasCompra.this.cmdAddAsientoActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == rucField) {
                FrameFacturasCompra.this.rucFieldFocusGained(evt);
            }
            else if (evt.getSource() == txtExentas) {
                FrameFacturasCompra.this.txtExentasFocusGained(evt);
            }
            else if (evt.getSource() == txtMontoIVA5) {
                FrameFacturasCompra.this.txtMontoIVA5FocusGained(evt);
            }
            else if (evt.getSource() == txtMontoIVA10) {
                FrameFacturasCompra.this.txtMontoIVA10FocusGained(evt);
            }
            else if (evt.getSource() == txtIVA5) {
                FrameFacturasCompra.this.txtIVA5FocusGained(evt);
            }
            else if (evt.getSource() == txtIVA10) {
                FrameFacturasCompra.this.txtIVA10FocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
        }

        public void keyPressed(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == rucField) {
                FrameFacturasCompra.this.rucFieldKeyPressed(evt);
            }
        }

        public void keyReleased(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == rucField) {
                FrameFacturasCompra.this.rucFieldKeyReleased(evt);
            }
        }

        public void keyTyped(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == rucField) {
                FrameFacturasCompra.this.rucFieldKeyTyped(evt);
            }
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == txtExentas) {
                FrameFacturasCompra.this.txtExentasMouseClicked(evt);
            }
            else if (evt.getSource() == txtMontoIVA5) {
                FrameFacturasCompra.this.txtMontoIVA5MouseClicked(evt);
            }
            else if (evt.getSource() == txtMontoIVA10) {
                FrameFacturasCompra.this.txtMontoIVA10MouseClicked(evt);
            }
            else if (evt.getSource() == txtIVA5) {
                FrameFacturasCompra.this.txtIVA5MouseClicked(evt);
            }
            else if (evt.getSource() == txtIVA10) {
                FrameFacturasCompra.this.txtIVA10MouseClicked(evt);
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
            if (evt.getSource() == FrameFacturasCompra.this) {
                FrameFacturasCompra.this.formInternalFrameActivated(evt);
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
    }// </editor-fold>//GEN-END:initComponents

    void refresh() {
        try {
            entityManager.getTransaction().rollback();
            entityManager.getTransaction().begin();
            java.util.Collection data = query.getResultList();
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
            List<com.parah.mg.domain.TblFacturasCompra> toRemove = new ArrayList<>(selected.length);
            for (int idx = 0; idx < selected.length; idx++) {
                com.parah.mg.domain.TblFacturasCompra t = list.get(masterTable.convertRowIndexToModel(selected[idx]));
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
            com.parah.mg.domain.TblFacturasCompra t = new com.parah.mg.domain.TblFacturasCompra();
            entityManager.persist(t);
            t.setIdUser(currentUser.getUser());
            list.add(t);
            int row = list.size() - 1;
            masterTable.setRowSelectionInterval(row, row);
            masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_newButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            java.util.Collection data = query.getResultList();
            data.stream().forEach((entity) -> {
                entityManager.refresh(entity);
            });
            list.clear();
            list.addAll(data);
        } catch (RollbackException ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            entityManager.getTransaction().begin();
            List<com.parah.mg.domain.TblFacturasCompra> merged = new ArrayList<>(list.size());
            for (com.parah.mg.domain.TblFacturasCompra t : list) {
                merged.add(entityManager.merge(t));
            }
            list.clear();
            list.addAll(merged);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated

    }//GEN-LAST:event_formInternalFrameActivated

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

    private void txtExentasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtExentasFocusGained
        txtExentas.selectAll();
    }//GEN-LAST:event_txtExentasFocusGained

    private void txtExentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExentasMouseClicked

    }//GEN-LAST:event_txtExentasMouseClicked

    private void txtExentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExentasActionPerformed

    }//GEN-LAST:event_txtExentasActionPerformed

    private void txtMontoIVA5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMontoIVA5FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoIVA5FocusGained

    private void txtMontoIVA5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMontoIVA5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoIVA5MouseClicked

    private void txtMontoIVA5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoIVA5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoIVA5ActionPerformed

    private void txtMontoIVA10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMontoIVA10FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoIVA10FocusGained

    private void txtMontoIVA10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMontoIVA10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoIVA10MouseClicked

    private void txtMontoIVA10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoIVA10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoIVA10ActionPerformed

    private void txtIVA5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIVA5FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIVA5FocusGained

    private void txtIVA5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtIVA5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIVA5MouseClicked

    private void txtIVA5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIVA5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIVA5ActionPerformed

    private void txtIVA10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIVA10FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIVA10FocusGained

    private void txtIVA10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtIVA10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIVA10MouseClicked

    private void txtIVA10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIVA10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIVA10ActionPerformed

    private void dtpVencimientoTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dtpVencimientoTimbradoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dtpVencimientoTimbradoActionPerformed

    private void cmdBorrarAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBorrarAsientoActionPerformed
        try {
            int index = masterTable.getSelectedRow();
            TblFacturasCompra T = list.get(masterTable.convertRowIndexToModel(index));
            Collection<TblAsientos> ts = T.getTblAsientosList();
            int[] selected = asientosTable.getSelectedRows();
            List<TblAsientos> toRemove = new ArrayList<>(selected.length);
            for (int idx = 0; idx < selected.length; idx++) {
                selected[idx] = asientosTable.convertRowIndexToModel(selected[idx]);
                int count = 0;
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

        int index = masterTable.getSelectedRow();
        TblFacturasCompra T = list.get(masterTable.convertRowIndexToModel(index));
        Collection<TblAsientos> ts = T.getTblAsientosList();
        if (ts == null) {
            ts = new LinkedList<>();
            T.setTblAsientosList((List) ts);
        }
        TblAsientos t = new TblAsientos();
        t.setFechahora(T.getFechahora());
        t.setIdUser(currentUser.getUser());
        entityManager.persist(t);
        ts.add(t);
        masterTable.clearSelection();
        masterTable.setRowSelectionInterval(index, index);
        int row = ts.size() - 1;
        asientosTable.setRowSelectionInterval(row, row);
        asientosTable.scrollRectToVisible(asientosTable.getCellRect(row, 0, true));

        if (asientosTable.getColumnModel().getColumnCount() > 0) {
            asientosTable.getColumn("Centro de Costo").setCellEditor(new DefaultCellEditor(cboCentroDeCosto));
            asientosTable.getColumn("Cuenta Contable Debe").setCellEditor(new DefaultCellEditor(cboCuentaDebe));
            asientosTable.getColumn("Cuenta Contable Haber").setCellEditor(new DefaultCellEditor(cboCuentaHaber));
        }
    }//GEN-LAST:event_cmdAddAsientoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable asientosTable;
    private javax.swing.JButton cmdAddAsiento;
    private javax.swing.JButton cmdBorrarAsiento;
    private javax.swing.JTextField conceptoField;
    private javax.swing.JLabel conceptoLabel;
    private com.parah.mg.utils.CondicionFacturaTableCellRenderer condicionFacturaTableCellRenderer1;
    private javax.swing.JButton deleteButton;
    private org.jdesktop.swingx.JXDatePicker dtpFecha;
    private org.jdesktop.swingx.JXDatePicker dtpVencimientoTimbrado;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel fechahoraLabel;
    private javax.swing.JLabel fechahoraLabel1;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel idLabel1;
    private javax.swing.JLabel idMiembroLabel;
    private javax.swing.JLabel idMiembroLabel2;
    private javax.swing.JLabel idMiembroLabel3;
    private com.parah.mg.utils.IntegerLongConverter integerLongConverter1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JScrollPane jScrollPane1;
    private java.util.List<com.parah.mg.domain.TblFacturasCompra> list;
    private java.util.List<com.parah.mg.domain.TblCentrosDeCosto> listCentrosDeCosto;
    private java.util.List<com.parah.mg.domain.TblContribuyentes> listContribuyentes;
    private java.util.List<com.parah.mg.domain.TblCuentasContables> listCuentasContables;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private javax.swing.JLabel montoLabel;
    private javax.swing.JLabel montoLabel1;
    private javax.swing.JLabel montoLabel2;
    private javax.swing.JLabel montoLabel3;
    private javax.swing.JLabel montoLabel4;
    private javax.swing.JLabel montoLabel5;
    private javax.swing.JButton newButton;
    private com.parah.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query query;
    private javax.persistence.Query queryCentrosDeCosto;
    private javax.persistence.Query queryContribuyentes;
    private javax.persistence.Query queryCuentasContables;
    private javax.swing.JRadioButton rbContado;
    private javax.swing.JRadioButton rbCredito;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField rucField;
    private com.parah.mg.utils.RucTableCellRenderer rucTableCellRenderer1;
    private javax.swing.JButton saveButton;
    private javax.swing.JFormattedTextField txtExentas;
    private javax.swing.JFormattedTextField txtIVA10;
    private javax.swing.JFormattedTextField txtIVA5;
    private javax.swing.JFormattedTextField txtMontoIVA10;
    private javax.swing.JFormattedTextField txtMontoIVA5;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JFormattedTextField txtTimbrado;
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
            java.util.logging.Logger.getLogger(FrameFacturasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameFacturasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameFacturasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameFacturasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                frame.setContentPane(new FrameFacturasCompra());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
