/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.frames.operaciones.egresos;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.gnadenheimer.mg.domain.TblAsientos;
import com.gnadenheimer.mg.frames.admin.*;
import com.gnadenheimer.mg.utils.CurrentUser;
import com.gnadenheimer.mg.utils.Utils;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import net.coderazzi.filters.gui.TableFilterHeader.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public class FrameAsientosManuales extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameAsientosManuales.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    String databaseIP;
    Map<String, String> persistenceMap = new HashMap<>();
    DatePickerSettings dps = new DatePickerSettings();
    TimePickerSettings tps = new TimePickerSettings();

    public FrameAsientosManuales() {
        super("Asientos Manuales",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {
            persistenceMap = Utils.getInstance().getPersistenceMap();

            dps.setFormatForDatesCommonEra("dd/MM/yyyy");

            tps.setFormatForDisplayTime("HH:mm:ss");
            tps.setFormatForMenuTimes("HH:mm:ss");
            tps.setDisplayToggleTimeMenuButton(false);

            initComponents();
            if (!Beans.isDesignTime()) {
                entityManager.getTransaction().begin();
            }

            AutoCompleteSupport support1 = AutoCompleteSupport.install(cboCentroDeCostoDebe, GlazedLists.eventListOf(listCentrosDeCosto.toArray()));
            support1.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support4 = AutoCompleteSupport.install(cboCentroDeCostoHaber, GlazedLists.eventListOf(listCentrosDeCosto.toArray()));
            support4.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support2 = AutoCompleteSupport.install(cboCuentaDebe, GlazedLists.eventListOf(listCuentasContables.toArray()));
            support2.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support3 = AutoCompleteSupport.install(cboCuentaHaber, GlazedLists.eventListOf(listCuentasContables.toArray()));
            support3.setFilterMode(TextMatcherEditor.CONTAINS);

            TableFilterHeader filterHeader = new TableFilterHeader(masterTable, AutoChoices.DISABLED);
            filterHeader.setAdaptiveChoices(false);
            filterHeader.getParserModel().setIgnoreCase(true);
            filterHeader.setPosition(Position.TOP);

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
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblAsientos t WHERE t.asientoManual = true OR t.observacion NOT LIKE '%Anulacion%'");
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        dateToStringConverter1 = new com.gnadenheimer.mg.utils.DateToStringConverter();
        dateTableCellRenderer1 = new com.gnadenheimer.mg.utils.DateTimeTableCellRenderer();
        numberCellRenderer1 = new com.gnadenheimer.mg.utils.NumberCellRenderer();
        integerLongConverter1 = new com.gnadenheimer.mg.utils.IntegerLongConverter();
        queryCuentasContables = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCuentasContables t where t.imputable = true");
        listCuentasContables = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryCuentasContables.getResultList());
        queryCentrosDeCosto = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCentrosDeCosto t");
        listCentrosDeCosto = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryCentrosDeCosto.getResultList());
        localDateTimeToDateTimeConverter1 = new com.gnadenheimer.mg.utils.LocalDateTimeToDateTimeConverter();
        dateTimeTableCellRenderer1 = new com.gnadenheimer.mg.utils.DateTimeTableCellRenderer();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        fechahoraLabel = new javax.swing.JLabel();
        montoLabel = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        idField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        conceptoLabel1 = new javax.swing.JLabel();
        cboCentroDeCostoDebe = new javax.swing.JComboBox();
        montoField = new javax.swing.JFormattedTextField();
        conceptoLabel2 = new javax.swing.JLabel();
        cboCuentaDebe = new javax.swing.JComboBox();
        conceptoLabel3 = new javax.swing.JLabel();
        cboCuentaHaber = new javax.swing.JComboBox();
        conceptoLabel = new javax.swing.JLabel();
        conceptoField = new javax.swing.JTextField();
        dtpFecha = new com.github.lgooddatepicker.components.DateTimePicker(dps, tps);
        conceptoLabel4 = new javax.swing.JLabel();
        cboCentroDeCostoHaber = new javax.swing.JComboBox();

        FormListener formListener = new FormListener();

        dateTableCellRenderer1.setText("dateTableCellRenderer1");

        numberCellRenderer1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numberCellRenderer1.setText("numberCellRenderer1");

        dateTimeTableCellRenderer1.setText("dateTimeTableCellRenderer1");

        addInternalFrameListener(formListener);

        masterTable.setAutoCreateRowSorter(true);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha/Hora");
        columnBinding.setColumnClass(java.time.LocalDateTime.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCentroDeCostoDebe}"));
        columnBinding.setColumnName("Centro de Costo Debe");
        columnBinding.setColumnClass(com.gnadenheimer.mg.domain.TblCentrosDeCosto.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCuentaContableDebe}"));
        columnBinding.setColumnName("Cuenta Debe");
        columnBinding.setColumnClass(com.gnadenheimer.mg.domain.TblCuentasContables.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCentroDeCostoHaber}"));
        columnBinding.setColumnName("Centro de Costo Haber");
        columnBinding.setColumnClass(com.gnadenheimer.mg.domain.TblCentrosDeCosto.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCuentaContableHaber}"));
        columnBinding.setColumnName("Cuenta Haber");
        columnBinding.setColumnClass(com.gnadenheimer.mg.domain.TblCuentasContables.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${monto}"));
        columnBinding.setColumnName("Monto");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(1).setCellRenderer(dateTimeTableCellRenderer1);
            masterTable.getColumnModel().getColumn(6).setCellRenderer(numberCellRenderer1);
        }

        fechahoraLabel.setText("Fecha/Hora:");

        montoLabel.setText("Monto:");

        idLabel.setText("Nro:");

        idField.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.id}"), idField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), idField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        saveButton.setText("Guardar");
        saveButton.addActionListener(formListener);

        refreshButton.setText("Cancelar");
        refreshButton.addActionListener(formListener);

        newButton.setText("Nuevo");
        newButton.addActionListener(formListener);

        deleteButton.setText("Eliminar");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), deleteButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        deleteButton.addActionListener(formListener);

        conceptoLabel1.setText("Centro de Costo Debe:");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idCentroDeCostoDebe}"), cboCentroDeCostoDebe, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cboCentroDeCostoDebe, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cboCentroDeCostoDebe.addActionListener(formListener);

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

        conceptoLabel2.setText("Cuenta Debe:");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idCuentaContableDebe}"), cboCuentaDebe, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cboCuentaDebe, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cboCuentaDebe.addActionListener(formListener);

        conceptoLabel3.setText("Cuenta Haber:");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idCuentaContableHaber}"), cboCuentaHaber, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cboCuentaHaber, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cboCuentaHaber.addActionListener(formListener);

        conceptoLabel.setText("Observacion:");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.observacion}"), conceptoField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), conceptoField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.fechahora}"), dtpFecha, org.jdesktop.beansbinding.BeanProperty.create("dateTimeStrict"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), dtpFecha, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        conceptoLabel4.setText("Centro de Costo Haber:");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idCentroDeCostoHaber}"), cboCentroDeCostoHaber, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cboCentroDeCostoHaber, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cboCentroDeCostoHaber.addActionListener(formListener);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(newButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton))
                    .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(conceptoLabel)
                            .addComponent(conceptoLabel4)
                            .addComponent(montoLabel)
                            .addComponent(conceptoLabel1)
                            .addComponent(fechahoraLabel)
                            .addComponent(idLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(montoField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(conceptoField)))
                            .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(cboCentroDeCostoHaber, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cboCentroDeCostoDebe, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(conceptoLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboCuentaHaber, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(conceptoLabel2)
                                        .addGap(21, 21, 21)
                                        .addComponent(cboCuentaDebe, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, refreshButton, saveButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idLabel)
                    .addComponent(idField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechahoraLabel)
                    .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conceptoLabel1)
                    .addComponent(cboCentroDeCostoDebe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(conceptoLabel2)
                    .addComponent(cboCuentaDebe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboCuentaHaber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(conceptoLabel4)
                    .addComponent(cboCentroDeCostoHaber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(conceptoLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(montoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.MouseListener, javax.swing.event.InternalFrameListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                FrameAsientosManuales.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FrameAsientosManuales.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == newButton) {
                FrameAsientosManuales.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                FrameAsientosManuales.this.deleteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cboCentroDeCostoDebe) {
                FrameAsientosManuales.this.cboCentroDeCostoDebeActionPerformed(evt);
            }
            else if (evt.getSource() == montoField) {
                FrameAsientosManuales.this.montoFieldActionPerformed(evt);
            }
            else if (evt.getSource() == cboCuentaDebe) {
                FrameAsientosManuales.this.cboCuentaDebeActionPerformed(evt);
            }
            else if (evt.getSource() == cboCuentaHaber) {
                FrameAsientosManuales.this.cboCuentaHaberActionPerformed(evt);
            }
            else if (evt.getSource() == cboCentroDeCostoHaber) {
                FrameAsientosManuales.this.cboCentroDeCostoHaberActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == montoField) {
                FrameAsientosManuales.this.montoFieldFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == montoField) {
                FrameAsientosManuales.this.montoFieldMouseClicked(evt);
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
            if (evt.getSource() == FrameAsientosManuales.this) {
                FrameAsientosManuales.this.formInternalFrameActivated(evt);
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

    @SuppressWarnings("unchecked")
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        try {
            entityManager.getTransaction().rollback();
            entityManager.getTransaction().begin();
            java.util.List data = query.getResultList();
            for (Object entity : data) {
                entityManager.refresh(entity);
            }
            list.clear();
            list.addAll(data);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try {
            int[] selected = masterTable.getSelectedRows();
            List<TblAsientos> toRemove = new ArrayList<>(selected.length);
            for (Integer idx = 0; idx < selected.length; idx++) {
                com.gnadenheimer.mg.domain.TblAsientos t = list.get(masterTable.convertRowIndexToModel(selected[idx]));
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
            TblAsientos a = new TblAsientos();
            entityManager.persist(a);
            a.setIdUser(currentUser.getUser());
            a.setFechahora(LocalDateTime.now().toLocalDate().atStartOfDay());
            a.setAsientoManual(true);
            list.add(a);
            Integer row = list.size() - 1;
            masterTable.setRowSelectionInterval(row, row);
            masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_newButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            java.util.List data = query.getResultList();
            for (Object entity : data) {
                entityManager.refresh(entity);
            }
            list.clear();
            list.addAll(data);
        } catch (RollbackException ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage());

            entityManager.getTransaction().begin();
            List<com.gnadenheimer.mg.domain.TblAsientos> merged = new ArrayList<>(list.size());
            for (com.gnadenheimer.mg.domain.TblAsientos t : list) {
                merged.add(entityManager.merge(t));
            }
            list.clear();
            list.addAll(merged);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated

    }//GEN-LAST:event_formInternalFrameActivated

    private void cboCentroDeCostoDebeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCentroDeCostoDebeActionPerformed

    }//GEN-LAST:event_cboCentroDeCostoDebeActionPerformed

    private void montoFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_montoFieldFocusGained
        montoField.selectAll();
    }//GEN-LAST:event_montoFieldFocusGained

    private void montoFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_montoFieldMouseClicked

    }//GEN-LAST:event_montoFieldMouseClicked

    private void montoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_montoFieldActionPerformed

    }//GEN-LAST:event_montoFieldActionPerformed

    private void cboCuentaDebeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCuentaDebeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCuentaDebeActionPerformed

    private void cboCuentaHaberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCuentaHaberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCuentaHaberActionPerformed

    private void cboCentroDeCostoHaberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCentroDeCostoHaberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCentroDeCostoHaberActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboCentroDeCostoDebe;
    private javax.swing.JComboBox cboCentroDeCostoHaber;
    private javax.swing.JComboBox cboCuentaDebe;
    private javax.swing.JComboBox cboCuentaHaber;
    private javax.swing.JTextField conceptoField;
    private javax.swing.JLabel conceptoLabel;
    private javax.swing.JLabel conceptoLabel1;
    private javax.swing.JLabel conceptoLabel2;
    private javax.swing.JLabel conceptoLabel3;
    private javax.swing.JLabel conceptoLabel4;
    private com.gnadenheimer.mg.utils.DateTimeTableCellRenderer dateTableCellRenderer1;
    private com.gnadenheimer.mg.utils.DateTimeTableCellRenderer dateTimeTableCellRenderer1;
    private com.gnadenheimer.mg.utils.DateToStringConverter dateToStringConverter1;
    private javax.swing.JButton deleteButton;
    private com.github.lgooddatepicker.components.DateTimePicker dtpFecha;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel fechahoraLabel;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel idLabel;
    private com.gnadenheimer.mg.utils.IntegerLongConverter integerLongConverter1;
    private java.util.List<com.gnadenheimer.mg.domain.TblAsientos> list;
    private java.util.List<com.gnadenheimer.mg.domain.TblCentrosDeCosto> listCentrosDeCosto;
    private java.util.List<com.gnadenheimer.mg.domain.TblCuentasContables> listCuentasContables;
    private com.gnadenheimer.mg.utils.LocalDateTimeToDateTimeConverter localDateTimeToDateTimeConverter1;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private javax.swing.JFormattedTextField montoField;
    private javax.swing.JLabel montoLabel;
    private javax.swing.JButton newButton;
    private com.gnadenheimer.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query query;
    private javax.persistence.Query queryCentrosDeCosto;
    private javax.persistence.Query queryCuentasContables;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
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
            java.util.logging.Logger.getLogger(FrameTransferenciasAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameTransferenciasAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameTransferenciasAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameTransferenciasAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new FrameTransferenciasAdmin());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
