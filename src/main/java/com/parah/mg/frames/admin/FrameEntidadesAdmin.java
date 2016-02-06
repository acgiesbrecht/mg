/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.frames.admin;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.parah.mg.domain.TblContribuyentes;
import com.parah.mg.domain.TblEntidades;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import com.parah.utils.CalcDV;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author user
 */
public class FrameEntidadesAdmin extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameEntidadesAdmin.class);
    Map<String, String> persistenceMap = new HashMap<>();
    CurrentUser currentUser = CurrentUser.getInstance();

    public FrameEntidadesAdmin() {
        super("Administrar Entidades",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        try {

            persistenceMap = Utils.getInstance().getPersistenceMap();
            initComponents();
            txtAporteBase.setValue(0);
            if (!Beans.isDesignTime()) {
                entityManager.getTransaction().begin();
            }

            TableFilterHeader filterHeader = new TableFilterHeader(masterTable, AutoChoices.ENABLED);
            filterHeader.setAdaptiveChoices(false);
            filterHeader.setPosition(TableFilterHeader.Position.TOP);

            AutoCompleteSupport support = AutoCompleteSupport.install(cboFormaDePago, GlazedLists.eventListOf(listFormasDePago.toArray()));
            support.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support1 = AutoCompleteSupport.install(cboEntidad, GlazedLists.eventListOf(list.toArray()));
            support1.setFilterMode(TextMatcherEditor.CONTAINS);

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
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        ctaCteTableCellRenderer1 = new com.parah.mg.utils.CtaCteTableCellRenderer();
        numberCellRenderer1 = new com.parah.mg.utils.NumberCellRenderer();
        rucTableCellRenderer1 = new com.parah.mg.utils.RucTableCellRenderer();
        queryFormasDePago = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblFormasDePago t");
        listFormasDePago = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryFormasDePago.getResultList());
        integerLongConverter1 = new com.parah.mg.utils.IntegerLongConverter();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        idLabel = new javax.swing.JLabel();
        nombreLabel = new javax.swing.JLabel();
        ctacteLabel = new javax.swing.JLabel();
        direccionLabel = new javax.swing.JLabel();
        idField = new javax.swing.JTextField();
        nombreField = new javax.swing.JTextField();
        ctacteField = new javax.swing.JTextField();
        direccionField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        ctacteLabel1 = new javax.swing.JLabel();
        direccionLabel1 = new javax.swing.JLabel();
        ctacteField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        ctacteLabel2 = new javax.swing.JLabel();
        ctacteLabel3 = new javax.swing.JLabel();
        rucField = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        ctacteLabel5 = new javax.swing.JLabel();
        txtAporteBase = new javax.swing.JFormattedTextField();
        txtAporteBase1 = new javax.swing.JFormattedTextField();
        ctacteLabel6 = new javax.swing.JLabel();
        idCategoriaLabel = new javax.swing.JLabel();
        cboFormaDePago = new javax.swing.JComboBox();
        apellidosField = new javax.swing.JTextField();
        nombreLabel1 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        montoLabel2 = new javax.swing.JLabel();
        txtCtaCte = new javax.swing.JTextField();
        idMiembroLabel2 = new javax.swing.JLabel();
        cboEntidad = new javax.swing.JComboBox();
        idMiembroLabel = new javax.swing.JLabel();
        idMiembroLabel1 = new javax.swing.JLabel();

        FormListener formListener = new FormListener();

        ctaCteTableCellRenderer1.setText("ctaCteTableCellRenderer1");

        numberCellRenderer1.setText("numberCellRenderer1");

        rucTableCellRenderer1.setText("rucTableCellRenderer1");

        addInternalFrameListener(formListener);

        masterTable.setAutoCreateRowSorter(true);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Nro");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombreCompleto}"));
        columnBinding.setColumnName("Nombre");
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ctacte}"));
        columnBinding.setColumnName("Ctacte");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${rucSinDv}"));
        columnBinding.setColumnName("RUC");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${domicilio}"));
        columnBinding.setColumnName("Domicilio");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${box}"));
        columnBinding.setColumnName("Casilla Correo");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${aporteMensual}"));
        columnBinding.setColumnName("Aporte Menusal");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idFormaDePagoPreferida}"));
        columnBinding.setColumnName("Form de Pago pref.");
        columnBinding.setColumnClass(com.parah.mg.domain.TblFormasDePago.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(2).setCellRenderer(ctaCteTableCellRenderer1);
            masterTable.getColumnModel().getColumn(3).setCellRenderer(rucTableCellRenderer1);
            masterTable.getColumnModel().getColumn(5).setCellRenderer(numberCellRenderer1);
            masterTable.getColumnModel().getColumn(6).setCellRenderer(numberCellRenderer1);
        }

        idLabel.setText("Nro:");

        nombreLabel.setText("Nombres:");

        ctacteLabel.setText("Cta Cte:");

        direccionLabel.setText("Domicilio:");

        idField.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.id}"), idField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), idField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.nombres}"), nombreField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), nombreField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.ctacte}"), ctacteField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), ctacteField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.domicilio}"), direccionField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), direccionField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
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

        ctacteLabel1.setForeground(new java.awt.Color(153, 153, 153));
        ctacteLabel1.setText("Formato: 7123801 201237 (Solo numeros sin simbolos como - o /)");

        direccionLabel1.setText("Casilla Correo:");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.box}"), ctacteField1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), ctacteField1, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        ctacteField1.addActionListener(formListener);

        jButton3.setText("Cargar EXCEL de Miembros");
        jButton3.addActionListener(formListener);

        ctacteLabel2.setForeground(new java.awt.Color(153, 153, 153));
        ctacteLabel2.setText("Formato XLS: Nombres, Apellidos, Cta.Cte, Cedula, Domicilio, Box");

        ctacteLabel3.setText("Cedula / RUC sin DV:");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.rucSinDv}"), rucField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), rucField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        rucField.addKeyListener(formListener);

        jButton4.setText("Cargar");
        jButton4.addActionListener(formListener);

        ctacteLabel5.setText("Cargar Importe de Aporte Base a todos los Miembros:");

        txtAporteBase.setColumns(9);
        txtAporteBase.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtAporteBase.addMouseListener(formListener);
        txtAporteBase.addActionListener(formListener);

        txtAporteBase1.setColumns(9);
        txtAporteBase1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtAporteBase1.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtAporteBase1.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.aporteMensual}"), txtAporteBase1, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(integerLongConverter1);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtAporteBase1, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtAporteBase1.addMouseListener(formListener);
        txtAporteBase1.addActionListener(formListener);

        ctacteLabel6.setText("Aporte Base:");

        idCategoriaLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idCategoriaLabel.setText("Forma de Pago preferida:");

        cboFormaDePago.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listFormasDePago, cboFormaDePago);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idFormaDePagoPreferida}"), cboFormaDePago, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cboFormaDePago, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cboFormaDePago.addActionListener(formListener);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.apellidos}"), apellidosField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), apellidosField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        nombreLabel1.setText("Apellidos:");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.razonSocial}"), txtRazonSocial, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtRazonSocial, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        montoLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        montoLabel2.setText("Razon Social:");

        txtCtaCte.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtCtaCte, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtCtaCte.addFocusListener(formListener);
        txtCtaCte.addInputMethodListener(formListener);
        txtCtaCte.addActionListener(formListener);
        txtCtaCte.addKeyListener(formListener);

        idMiembroLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel2.setText("Nombre:");

        cboEntidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, cboEntidad);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idEntidadPaganteAportes}"), cboEntidad, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cboEntidad, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cboEntidad.addActionListener(formListener);

        idMiembroLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel.setText("Miembro pagante de aporte:");

        idMiembroLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel1.setText("Cta. Cte.:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(idMiembroLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ctacteLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtAporteBase, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ctacteLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(masterScrollPane)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ctacteLabel3)
                                    .addComponent(ctacteLabel))
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(ctacteField, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ctacteLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 478, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rucField, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(direccionLabel)
                                    .addComponent(direccionLabel1)
                                    .addComponent(montoLabel2))
                                .addGap(93, 93, 93)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtRazonSocial)
                                    .addComponent(ctacteField1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(direccionField, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(idLabel)
                                        .addGap(141, 141, 141)
                                        .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ctacteLabel6)
                                            .addComponent(idCategoriaLabel))
                                        .addGap(25, 25, 25)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cboFormaDePago, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtAporteBase1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(idMiembroLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)
                                                .addComponent(idMiembroLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombreLabel1)
                                    .addComponent(nombreLabel))
                                .addGap(116, 116, 116)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombreField)
                                    .addComponent(apellidosField))))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(newButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, refreshButton, saveButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreLabel)
                    .addComponent(nombreField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreLabel1)
                    .addComponent(apellidosField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ctacteLabel)
                    .addComponent(ctacteField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ctacteLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rucField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ctacteLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoLabel2)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccionLabel)
                    .addComponent(direccionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccionLabel1)
                    .addComponent(ctacteField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAporteBase1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ctacteLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboFormaDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idCategoriaLabel))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idMiembroLabel)
                    .addComponent(idMiembroLabel1)
                    .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idMiembroLabel2)
                    .addComponent(cboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(refreshButton)
                    .addComponent(deleteButton)
                    .addComponent(newButton))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(ctacteLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ctacteLabel5)
                    .addComponent(txtAporteBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)))
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.InputMethodListener, java.awt.event.KeyListener, java.awt.event.MouseListener, javax.swing.event.InternalFrameListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                FrameEntidadesAdmin.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FrameEntidadesAdmin.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == newButton) {
                FrameEntidadesAdmin.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                FrameEntidadesAdmin.this.deleteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == ctacteField1) {
                FrameEntidadesAdmin.this.ctacteField1ActionPerformed(evt);
            }
            else if (evt.getSource() == jButton3) {
                FrameEntidadesAdmin.this.jButton3ActionPerformed(evt);
            }
            else if (evt.getSource() == jButton4) {
                FrameEntidadesAdmin.this.jButton4ActionPerformed(evt);
            }
            else if (evt.getSource() == txtAporteBase) {
                FrameEntidadesAdmin.this.txtAporteBaseActionPerformed(evt);
            }
            else if (evt.getSource() == txtAporteBase1) {
                FrameEntidadesAdmin.this.txtAporteBase1ActionPerformed(evt);
            }
            else if (evt.getSource() == cboFormaDePago) {
                FrameEntidadesAdmin.this.cboFormaDePagoActionPerformed(evt);
            }
            else if (evt.getSource() == txtCtaCte) {
                FrameEntidadesAdmin.this.txtCtaCteActionPerformed(evt);
            }
            else if (evt.getSource() == cboEntidad) {
                FrameEntidadesAdmin.this.cboEntidadActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == txtCtaCte) {
                FrameEntidadesAdmin.this.txtCtaCteFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
        }

        public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
        }

        public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            if (evt.getSource() == txtCtaCte) {
                FrameEntidadesAdmin.this.txtCtaCteInputMethodTextChanged(evt);
            }
        }

        public void keyPressed(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == rucField) {
                FrameEntidadesAdmin.this.rucFieldKeyPressed(evt);
            }
        }

        public void keyReleased(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == rucField) {
                FrameEntidadesAdmin.this.rucFieldKeyReleased(evt);
            }
            else if (evt.getSource() == txtCtaCte) {
                FrameEntidadesAdmin.this.txtCtaCteKeyReleased(evt);
            }
        }

        public void keyTyped(java.awt.event.KeyEvent evt) {
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == txtAporteBase) {
                FrameEntidadesAdmin.this.txtAporteBaseMouseClicked(evt);
            }
            else if (evt.getSource() == txtAporteBase1) {
                FrameEntidadesAdmin.this.txtAporteBase1MouseClicked(evt);
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
            if (evt.getSource() == FrameEntidadesAdmin.this) {
                FrameEntidadesAdmin.this.formInternalFrameActivated(evt);
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

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try {
            int[] selected = masterTable.getSelectedRows();
            List<TblEntidades> toRemove = new ArrayList<>(selected.length);
            TblEntidades t;
            for (int idx = 0; idx < selected.length; idx++) {
                t = list.get(masterTable.convertRowIndexToModel(selected[idx]));
                toRemove.add(t);
                entityManager.remove(t);
            }
            //list.removeAll(toRemove);
            list.removeAll(new HashSet<>(toRemove));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        try {
            TblEntidades t = new TblEntidades();
            entityManager.persist(t);
            t.setIdUser(currentUser.getUser());
            t.setIsMiembroActivo(false);
            t.setAporteMensual(0);
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
            List<TblEntidades> merged = new ArrayList<>(list.size());
            list.stream().forEach((t) -> {
                merged.add(entityManager.merge(t));
            });
            list.clear();
            list.addAll(merged);
        } catch (Exception exx) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + exx.getMessage());
            logger.error(Thread.currentThread().getStackTrace()[1].getMethodName(), exx);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated

    }//GEN-LAST:event_formInternalFrameActivated

    @SuppressWarnings("unchecked")
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        try {
            entityManager.getTransaction().rollback();
            entityManager.getTransaction().begin();
            java.util.Collection data = query.getResultList();
            data.stream().forEach((entity) -> {
                entityManager.refresh(entity);
            });
            list.clear();
            list.addAll(data);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void ctacteField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ctacteField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ctacteField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                File file = fc.getSelectedFile();
                POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
                HSSFWorkbook wb = new HSSFWorkbook(fs);
                HSSFSheet sheet = wb.getSheetAt(0);
                HSSFRow row;
                HSSFCell cell;

                int rows; // No of rows
                rows = sheet.getPhysicalNumberOfRows();

                int cols = 0; // No of columns
                int tmp = 0;

                // This trick ensures that we get the data properly even if it doesn't start from first few rows
                for (int i = 0; i < 10 || i < rows; i++) {
                    row = sheet.getRow(i);
                    if (row != null) {
                        tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                        if (tmp > cols) {
                            cols = tmp;
                        }
                    }
                }

                for (int r = 1; r < rows; r++) {
                    row = sheet.getRow(r);
                    if (row != null) {
                        TblEntidades miembro = new TblEntidades();

                        miembro.setNombres(row.getCell(0).getStringCellValue());

                        if (row.getCell(1) != null) {
                            miembro.setApellidos(row.getCell(1).getStringCellValue());
                        } else {
                            miembro.setApellidos("");
                        }
                        if (row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
                            miembro.setCtacte(Integer.valueOf(row.getCell(2).getStringCellValue().replaceAll("[^\\d.]", "")));
                        } else if (row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            miembro.setCtacte((int) (row.getCell(2).getNumericCellValue()));
                        }
                        List<TblEntidades> duplicadoList = entityManager.createQuery("SELECT t FROM TblEntidades t where t.nombres = '" + miembro.getNombres() + "' and t.ctacte = " + miembro.getCtacte().toString(), TblEntidades.class).getResultList();
                        if (duplicadoList.size() > 0) {
                            miembro = null;
                            miembro = duplicadoList.get(0);
                        }

                        if (row.getCell(3) != null) {
                            DecimalFormat df = new DecimalFormat("#0");
                            miembro.setRucSinDv(df.format(row.getCell(3).getNumericCellValue()));
                        }
                        if (row.getCell(4) != null) {
                            miembro.setDomicilio(row.getCell(4).getStringCellValue());
                        }
                        if (row.getCell(5) != null) {
                            miembro.setBox((int) row.getCell(5).getNumericCellValue());
                        }
                        miembro.setAporteMensual(0);
                        miembro.setIdFormaDePagoPreferida(listFormasDePago.get(0));
                        miembro.setIdUser(currentUser.getUser());
                        miembro.setIsMiembroActivo(false);
                        entityManager.persist(miembro);
                        list.add(miembro);

                    }
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void rucFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucFieldKeyPressed

    }//GEN-LAST:event_rucFieldKeyPressed

    private void rucFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucFieldKeyReleased

    }//GEN-LAST:event_rucFieldKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            if (((Number) txtAporteBase.getValue()).intValue() > -1) {
                int reply = JOptionPane.showConfirmDialog(null, "Esta seguro que desea actualizar el valor del aporte base para todos los miembros a " + txtAporteBase.getValue().toString() + "Gs?", title, JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    int val = ((Number) txtAporteBase.getValue()).intValue();
                    list.stream().forEach((miembro) -> {
                        miembro.setAporteMensual(val);
                    });
                    entityManager.getTransaction().commit();
                    entityManager.getTransaction().begin();
                    List data = query.getResultList();
                    data.stream().forEach((entity) -> {
                        entityManager.refresh(entity);
                    });
                    list.clear();
                    list.addAll(data);
                }
            }
        } catch (RollbackException ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            entityManager.getTransaction().begin();
            List<TblEntidades> merged = new ArrayList<>(list.size());
            list.stream().forEach((t) -> {
                merged.add(entityManager.merge(t));
            });
            list.clear();
            list.addAll(merged);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            logger.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtAporteBaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAporteBaseMouseClicked

    }//GEN-LAST:event_txtAporteBaseMouseClicked

    private void txtAporteBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAporteBaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAporteBaseActionPerformed

    private void txtAporteBase1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAporteBase1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAporteBase1MouseClicked

    private void txtAporteBase1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAporteBase1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAporteBase1ActionPerformed

    private void cboFormaDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFormaDePagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboFormaDePagoActionPerformed

    private void txtCtaCteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtaCteFocusGained
        try {
            txtCtaCte.selectAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteFocusGained

    private void txtCtaCteInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtCtaCteInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteInputMethodTextChanged

    private void txtCtaCteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCtaCteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteActionPerformed

    private void txtCtaCteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCtaCteKeyReleased
        try {
            txtCtaCte.setBackground(Color.white);
            if (txtCtaCte.getText().length() > 4) {
                List<TblEntidades> listE = list;
                Optional<TblEntidades> value = listE.stream().filter(
                        a -> a.getCtacte().equals(Integer.valueOf(txtCtaCte.getText())))
                        .findFirst();
                if (value.isPresent()) {
                    cboEntidad.setSelectedItem(value.get());
                    txtCtaCte.setBackground(Color.green);
                    rucField.requestFocus();
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteKeyReleased

    private void cboEntidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEntidadActionPerformed
        /*if (cboEntidad.getSelectedItem() != null) {
         txtCtaCte.setText(((TblEntidades) cboEntidad.getSelectedItem()).getCtacte().toString());
         } else {
         txtCtaCte.setText("");
         }*/
    }//GEN-LAST:event_cboEntidadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidosField;
    private javax.swing.JComboBox cboEntidad;
    private javax.swing.JComboBox cboFormaDePago;
    private com.parah.mg.utils.CtaCteTableCellRenderer ctaCteTableCellRenderer1;
    private javax.swing.JTextField ctacteField;
    private javax.swing.JTextField ctacteField1;
    private javax.swing.JLabel ctacteLabel;
    private javax.swing.JLabel ctacteLabel1;
    private javax.swing.JLabel ctacteLabel2;
    private javax.swing.JLabel ctacteLabel3;
    private javax.swing.JLabel ctacteLabel5;
    private javax.swing.JLabel ctacteLabel6;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField direccionField;
    private javax.swing.JLabel direccionLabel;
    private javax.swing.JLabel direccionLabel1;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel idCategoriaLabel;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel idMiembroLabel;
    private javax.swing.JLabel idMiembroLabel1;
    private javax.swing.JLabel idMiembroLabel2;
    private com.parah.mg.utils.IntegerLongConverter integerLongConverter1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private java.util.List<com.parah.mg.domain.TblEntidades> list;
    private java.util.List<com.parah.mg.domain.TblFormasDePago> listFormasDePago;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private javax.swing.JLabel montoLabel2;
    private javax.swing.JButton newButton;
    private javax.swing.JTextField nombreField;
    private javax.swing.JLabel nombreLabel;
    private javax.swing.JLabel nombreLabel1;
    private com.parah.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query query;
    private javax.persistence.Query queryFormasDePago;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField rucField;
    private com.parah.mg.utils.RucTableCellRenderer rucTableCellRenderer1;
    private javax.swing.JButton saveButton;
    private javax.swing.JFormattedTextField txtAporteBase;
    private javax.swing.JFormattedTextField txtAporteBase1;
    private javax.swing.JTextField txtCtaCte;
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
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameEntidadesAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameEntidadesAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameEntidadesAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameEntidadesAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                frame.setContentPane(new FrameEntidadesAdmin());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
