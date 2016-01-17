/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.frames.operaciones;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.lacreacion.mg.domain.TblCategoriasArticulos;
import com.lacreacion.mg.domain.TblEntidades;
import com.lacreacion.mg.domain.TblEventoCuotas;
import com.lacreacion.mg.domain.TblEventoDetalle;
import com.lacreacion.mg.domain.TblEventos;
import com.lacreacion.mg.domain.TblTransferencias;
import com.lacreacion.mg.domain.models.CuotaModel;
import com.lacreacion.mg.utils.CurrentUser;
import com.lacreacion.mg.utils.Utils;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.prefs.Preferences;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author user
 */
public class FrameRematesDetalle extends JInternalFrame {

    CurrentUser currentUser = CurrentUser.getInstance();
    String databaseIP;
    Map<String, String> persistenceMap = new HashMap<>();
    EventList<TblEntidades> eventListEntidades = new BasicEventList<>();
    EventList<TblCategoriasArticulos> eventListTblCategoriasArticulos = new BasicEventList<>();

    public FrameRematesDetalle() {
        super("Remates",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {
            persistenceMap = Utils.getInstance().getDatabaseIP();
            initComponents();
            this.dateTimeTableCellRenderer1.setEnProceso(true);
            this.numberCellRenderer1.setEnProceso(true);
            this.normalTableCellRenderer1.setEnProceso(true);

            if (!Beans.isDesignTime()) {
                entityManager.getTransaction().begin();
                entityManager1.getTransaction().begin();
            }

            //AutoCompleteDecorator.decorate(cboFechaRemate);
            //AutoCompleteDecorator.decorate(cboCategoria);
            AutoCompleteSupport support = AutoCompleteSupport.install(cboFechaRemate, GlazedLists.eventListOf(listEventos.toArray()));
            support.setFilterMode(TextMatcherEditor.CONTAINS);

            eventListTblCategoriasArticulos.clear();
            eventListTblCategoriasArticulos.addAll(tblCategoriasArticulosList);
            AutoCompleteSupport support1 = AutoCompleteSupport.install(cboCategoria, eventListTblCategoriasArticulos);
            support1.setFilterMode(TextMatcherEditor.CONTAINS);

            eventListEntidades.clear();
            eventListEntidades.addAll(listEntidades);
            AutoCompleteSupport support2 = AutoCompleteSupport.install(cboEntidad, eventListEntidades);
            support2.setFilterMode(TextMatcherEditor.CONTAINS);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            Date today = sdf.parse(s);
            Optional<TblEventos> value = listEventos.stream().filter(a -> a.getFecha().equals(today))
                    .findFirst();
            if (value.isPresent()) {
                cboFechaRemate.setSelectedItem(value.get());
            } else {
                cboFechaRemate.setSelectedIndex(-1);
            }

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
            ex.printStackTrace();
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
        queryEventosDetalle = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventoDetalle t WHERE t.idEvento = :eventoId ORDER BY t.fechahora");
        queryEventosDetalle.setParameter("eventoId", null) ;
        listEventosDetalle = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventosDetalle.getResultList());
        tblCategoriasArticulosQuery = java.beans.Beans.isDesignTime() ? null : entityManager1.createQuery("SELECT t FROM TblCategoriasArticulos t ORDER BY t.descripcion");
        tblCategoriasArticulosList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(tblCategoriasArticulosQuery.getResultList());
        queryEntidades = java.beans.Beans.isDesignTime() ? null : entityManager1.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        listEntidades = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEntidades.getResultList());
        normalTableCellRenderer1 = new com.lacreacion.mg.utils.NormalTableCellRenderer();
        categoriasConverter1 = new com.lacreacion.mg.utils.CategoriasConverter();
        queryEventos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventos t WHERE t.idEventoTipo.id = 1 AND t.idGrupo IN :grupos ORDER BY t.fecha");
        queryEventos.setParameter("grupos", currentUser.getUser().getTblGruposList());
        listEventos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventos.getResultList());
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        dateTimeTableCellRenderer1 = new com.lacreacion.mg.utils.DateTimeTableCellRenderer();
        integerLongConverter1 = new com.lacreacion.mg.utils.IntegerLongConverter();
        numberCellRenderer1 = new com.lacreacion.mg.utils.NumberCellRenderer();
        dateTimeToStringConverter1 = new com.lacreacion.mg.utils.DateTimeToStringConverter();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        fechahoraLabel = new javax.swing.JLabel();
        idCategoriaLabel = new javax.swing.JLabel();
        montoLabel = new javax.swing.JLabel();
        observacionLabel = new javax.swing.JLabel();
        idMiembroLabel = new javax.swing.JLabel();
        fechahoraField = new javax.swing.JTextField();
        observacionField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        idMiembroLabel1 = new javax.swing.JLabel();
        txtCtaCte = new javax.swing.JTextField();
        idMiembroLabel2 = new javax.swing.JLabel();
        dateTableCellRenderer1 = new com.lacreacion.mg.utils.DateTimeTableCellRenderer();
        cboCategoria = new javax.swing.JComboBox();
        cboEntidad = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblTotalOperaciones = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboFechaRemate = new javax.swing.JComboBox();
        montoField = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        saveButton1 = new javax.swing.JButton();

        FormListener formListener = new FormListener();

        normalTableCellRenderer1.setText("normalTableCellRenderer1");

        dateTimeTableCellRenderer1.setText("dateTimeTableCellRenderer1");

        numberCellRenderer1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numberCellRenderer1.setText("numberCellRenderer1");

        addInternalFrameListener(formListener);

        masterTable.setAutoCreateRowSorter(true);
        masterTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        masterTable.setRowHeight(20);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEventosDetalle, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha/Hora");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idMiembro}"));
        columnBinding.setColumnName("Miembro");
        columnBinding.setColumnClass(com.lacreacion.mg.domain.TblEntidades.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCategoriaArticulo}"));
        columnBinding.setColumnName("Categoria");
        columnBinding.setColumnClass(com.lacreacion.mg.domain.TblCategoriasArticulos.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${observacion}"));
        columnBinding.setColumnName("Observacion");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${monto}"));
        columnBinding.setColumnName("Monto");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(0).setCellRenderer(dateTimeTableCellRenderer1);
            masterTable.getColumnModel().getColumn(1).setCellRenderer(normalTableCellRenderer1);
            masterTable.getColumnModel().getColumn(2).setCellRenderer(normalTableCellRenderer1);
            masterTable.getColumnModel().getColumn(3).setCellRenderer(normalTableCellRenderer1);
            masterTable.getColumnModel().getColumn(4).setCellRenderer(numberCellRenderer1);
        }

        fechahoraLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fechahoraLabel.setText("Fecha/Hora:");

        idCategoriaLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idCategoriaLabel.setText("Categoria:");

        montoLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        montoLabel.setText("Monto:");

        observacionLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        observacionLabel.setText("Observacion:");

        idMiembroLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel.setText("Cliente:");

        fechahoraField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.fechahora}"), fechahoraField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue("null");
        binding.setConverter(dateTimeToStringConverter1);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), fechahoraField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        observacionField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.observacion}"), observacionField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), observacionField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), deleteButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        deleteButton.addActionListener(formListener);

        idMiembroLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel1.setText("Cta. Cte.:");

        txtCtaCte.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), txtCtaCte, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        txtCtaCte.addFocusListener(formListener);
        txtCtaCte.addActionListener(formListener);
        txtCtaCte.addKeyListener(formListener);

        idMiembroLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel2.setText("Nombre:");

        cboCategoria.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblCategoriasArticulosList, cboCategoria);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idCategoriaArticulo}"), cboCategoria, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cboCategoria, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cboCategoria.addActionListener(formListener);

        cboEntidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEntidades, cboEntidad);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idMiembro}"), cboEntidad, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), cboEntidad, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        cboEntidad.addActionListener(formListener);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Total Operaciones:");

        lblTotal.setBackground(new java.awt.Color(204, 255, 204));
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        lblTotalOperaciones.setBackground(new java.awt.Color(204, 255, 204));
        lblTotalOperaciones.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotalOperaciones.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Total Guaranies:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(lblTotalOperaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalOperaciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel2.setText("Fecha de Remate:");

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEventos, cboFechaRemate);
        bindingGroup.addBinding(jComboBoxBinding);

        cboFechaRemate.addActionListener(formListener);

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

        jButton1.setText("Actualizar");
        jButton1.setFocusable(false);
        jButton1.addActionListener(formListener);

        jButton2.setText("Actualizar");
        jButton2.addActionListener(formListener);

        saveButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        saveButton1.setText("Guardar & Imprimir");
        saveButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton1.addActionListener(formListener);

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
                                    .addComponent(montoLabel)
                                    .addComponent(observacionLabel)
                                    .addComponent(idMiembroLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(montoField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(observacionField, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(idMiembroLabel1)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(12, 12, 12)
                                                    .addComponent(idMiembroLabel2)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(cboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jButton2)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(258, 258, 258)
                                                .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(saveButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(refreshButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(deleteButton, javax.swing.GroupLayout.Alignment.TRAILING)))))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(332, 332, 332)
                                        .addComponent(dateTableCellRenderer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cboFechaRemate, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fechahoraLabel)
                                    .addComponent(idCategoriaLabel))
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboCategoria, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fechahoraField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(masterScrollPane))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, refreshButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboFechaRemate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(masterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fechahoraLabel)
                    .addComponent(fechahoraField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idCategoriaLabel)
                    .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(observacionLabel)
                    .addComponent(observacionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoLabel)
                    .addComponent(montoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idMiembroLabel)
                            .addComponent(idMiembroLabel1)
                            .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idMiembroLabel2)
                            .addComponent(cboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(deleteButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(refreshButton))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(saveButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(dateTableCellRenderer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(470, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.KeyListener, java.awt.event.MouseListener, javax.swing.event.InternalFrameListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                FrameRematesDetalle.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FrameRematesDetalle.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == newButton) {
                FrameRematesDetalle.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                FrameRematesDetalle.this.deleteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == txtCtaCte) {
                FrameRematesDetalle.this.txtCtaCteActionPerformed(evt);
            }
            else if (evt.getSource() == cboCategoria) {
                FrameRematesDetalle.this.cboCategoriaActionPerformed(evt);
            }
            else if (evt.getSource() == cboEntidad) {
                FrameRematesDetalle.this.cboEntidadActionPerformed(evt);
            }
            else if (evt.getSource() == cboFechaRemate) {
                FrameRematesDetalle.this.cboFechaRemateActionPerformed(evt);
            }
            else if (evt.getSource() == montoField) {
                FrameRematesDetalle.this.montoFieldActionPerformed(evt);
            }
            else if (evt.getSource() == jButton1) {
                FrameRematesDetalle.this.jButton1ActionPerformed(evt);
            }
            else if (evt.getSource() == jButton2) {
                FrameRematesDetalle.this.jButton2ActionPerformed(evt);
            }
            else if (evt.getSource() == saveButton1) {
                FrameRematesDetalle.this.saveButton1ActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == txtCtaCte) {
                FrameRematesDetalle.this.txtCtaCteFocusGained(evt);
            }
            else if (evt.getSource() == montoField) {
                FrameRematesDetalle.this.montoFieldFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
        }

        public void keyPressed(java.awt.event.KeyEvent evt) {
        }

        public void keyReleased(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == txtCtaCte) {
                FrameRematesDetalle.this.txtCtaCteKeyReleased(evt);
            }
        }

        public void keyTyped(java.awt.event.KeyEvent evt) {
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == montoField) {
                FrameRematesDetalle.this.montoFieldMouseClicked(evt);
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
            if (evt.getSource() == FrameRematesDetalle.this) {
                FrameRematesDetalle.this.formInternalFrameActivated(evt);
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
    void refresh() {
        try {
            if (cboFechaRemate.getSelectedItem() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                entityManager.getTransaction().begin();
                queryEventosDetalle.setParameter("eventoId", ((TblEventos) cboFechaRemate.getSelectedItem()));
                java.util.Collection data = queryEventosDetalle.getResultList();
                data.stream().forEach((entity) -> {
                    entityManager.refresh(entity);
                });
                listEventosDetalle.clear();
                listEventosDetalle.addAll(data);

                entityManager1.getTransaction().rollback();
                entityManager1.getTransaction().begin();
                data = queryEntidades.getResultList();
                data.stream().forEach((entity) -> {
                    entityManager1.refresh(entity);
                });
                listEntidades.clear();
                listEntidades.addAll(data);
                eventListEntidades.clear();
                eventListEntidades.addAll(data);

                data = tblCategoriasArticulosQuery.getResultList();
                data.stream().forEach((entity) -> {
                    entityManager1.refresh(entity);
                });
                tblCategoriasArticulosList.clear();
                tblCategoriasArticulosList.addAll(data);
                eventListTblCategoriasArticulos.clear();
                eventListTblCategoriasArticulos.addAll(data);

                lblTotal.setText(String.format("%,d", listEventosDetalle.stream().mapToInt(a -> a.getMonto()).sum()));
                lblTotalOperaciones.setText(String.format("%,d", listEventosDetalle.stream().mapToInt(a -> a.getMonto()).count()));
                txtCtaCte.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try {
            int[] selected = masterTable.getSelectedRows();
            List<TblEventoDetalle> toRemove = new ArrayList<>(selected.length);
            for (int idx = 0; idx < selected.length; idx++) {
                TblEventoDetalle t = listEventosDetalle.get(masterTable.convertRowIndexToModel(selected[idx]));
                toRemove.add(t);
                entityManager.remove(t);
            }
            listEventosDetalle.removeAll(toRemove);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        try {
            TblEventoDetalle t = new TblEventoDetalle();
            entityManager.persist(t);
            t.setIdEvento((TblEventos) cboFechaRemate.getSelectedItem());
            t.setIdUser(currentUser.getUser());
            listEventosDetalle.add(t);
            int row = listEventosDetalle.size() - 1;
            masterTable.setRowSelectionInterval(row, row);
            masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));

            fechahoraField.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            cboCategoria.requestFocusInWindow();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
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
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            refresh();
            newButton.requestFocus();
        } catch (RollbackException rex) {
            JOptionPane.showMessageDialog(null, rex.getMessage());

            entityManager.getTransaction().begin();
            List<TblEventoDetalle> merged = new ArrayList<>(listEventosDetalle.size());
            listEventosDetalle.stream().forEach((t) -> {
                merged.add(entityManager.merge(t));
            });
            listEventosDetalle.clear();
            listEventosDetalle.addAll(merged);
        }

    }
    private void txtCtaCteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCtaCteKeyReleased
        try {
            txtCtaCte.setBackground(Color.white);
            if (txtCtaCte.getText().length() > 4) {
                List<TblEntidades> list = listEntidades;
                Optional<TblEntidades> value = list.stream().filter(a -> a.getCtacte().equals(Integer.valueOf(txtCtaCte.getText())))
                        .findFirst();
                System.out.println(Integer.valueOf(txtCtaCte.getText()));
                System.out.println(value.isPresent());
                if (value.isPresent()) {
                    cboEntidad.setSelectedItem(value.get());
                    txtCtaCte.setBackground(Color.green);
                    saveButton.requestFocus();
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteKeyReleased

    private void txtCtaCteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtaCteFocusGained
        try {
            txtCtaCte.selectAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteFocusGained

    private void cboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCategoriaActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        refresh();
    }//GEN-LAST:event_formInternalFrameActivated

    @SuppressWarnings("unchecked")
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed

        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void cboFechaRemateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFechaRemateActionPerformed
        try {
            refresh();
            /*if (cboFechaRemate.getSelectedIndex() > -1) {

             //queryRematesDetalle = entityManager.createQuery("SELECT r FROM TblRematesDetalle r WHERE r.idRemate.id = " + idRemate);
             queryRematesDetalle.setParameter("remateId", ((TblRemates) cboFechaRemate.getSelectedItem()));
             listRematesDetalle.clear();
             listRematesDetalle.addAll(queryRematesDetalle.getResultList());
             }*/
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cboFechaRemateActionPerformed

    private void montoFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_montoFieldFocusGained
        montoField.selectAll();
    }//GEN-LAST:event_montoFieldFocusGained

    private void montoFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_montoFieldMouseClicked

    }//GEN-LAST:event_montoFieldMouseClicked

    private void montoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_montoFieldActionPerformed

    }//GEN-LAST:event_montoFieldActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Collection data = tblCategoriasArticulosQuery.getResultList();
        data.stream().forEach((entity) -> {
            entityManager1.refresh(entity);
        });
        tblCategoriasArticulosList.clear();
        tblCategoriasArticulosList.addAll(data);
        eventListTblCategoriasArticulos.clear();
        eventListTblCategoriasArticulos.addAll(data);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        Collection data = queryEntidades.getResultList();
        data.stream().forEach((entity) -> {
            entityManager1.refresh(entity);
        });
        listEntidades.clear();
        listEntidades.addAll(data);
        eventListEntidades.clear();
        eventListEntidades.addAll(data);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cboEntidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEntidadActionPerformed
        if (cboEntidad.getSelectedItem() != null) {
            txtCtaCte.setText(((TblEntidades) cboEntidad.getSelectedItem()).getCtacte().toString());
        } else {
            txtCtaCte.setText("");
        }
    }//GEN-LAST:event_cboEntidadActionPerformed

    private void saveButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButton1ActionPerformed
        try {
            if (((Number) montoField.getValue()).intValue() < 1) {
                JOptionPane.showMessageDialog(null, "El Monto a abonar no puede ser 0.");
                montoField.requestFocusInWindow();
                return;
            }
            entityManager.getTransaction().commit();
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + databaseIP + ":5432/remate", "postgres", "123456");
            Integer remateId = ((TblEventos) cboFechaRemate.getSelectedItem()).getId();
            TblEventoCuotas remateCuotas = entityManager.find(TblEventoCuotas.class, remateId);
            //if (Integer.valueOf(txtTransferencia.getValue()) > 0) {
            Integer transferenciaMonto = ((Number) montoField.getValue()).intValue();
            if (transferenciaMonto > 0) {
                //List<CuotaModel> cuotasList = Varios.getCuotas(remateCuotas, Integer.valueOf(txtTransferencia1.getText()));
                List<CuotaModel> cuotasList = Utils.getInstance().getCuotas(remateCuotas, transferenciaMonto);
                for (CuotaModel cuota : cuotasList) {
                    TblTransferencias transferencia = new TblTransferencias();
                    transferencia.setFechahora(cuota.getFecha());
                    transferencia.setIdEntidad((TblEntidades) cboEntidad.getSelectedItem());
                    transferencia.setConcepto(((TblEventos) cboFechaRemate.getSelectedItem()).getDescripcion());
                    transferencia.setMonto(cuota.getMonto());
                    transferencia.setIdEvento((TblEventos) cboFechaRemate.getSelectedItem());
                    entityManager.getTransaction().begin();
                    entityManager.persist(transferencia);
                    entityManager.flush();
                    entityManager.getTransaction().commit();

                    if (transferencia.getId() > 0) {
                        Map parameters = new HashMap();
                        parameters.put("transferencia_id", transferencia.getId());
                        parameters.put("logo", getClass().getResourceAsStream("/reports/cclogo200.png"));
                        parameters.put("logo2", getClass().getResourceAsStream("/reports/cclogo200.png"));
                        parameters.put("logo3", getClass().getResourceAsStream("/reports/cclogo200.png"));

                        JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/transferencia.jrxml"));

                        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                        //JasperViewer jReportsViewer = new JasperViewer(jasperPrint, false);
                        //jReportsViewer.setVisible(true);
                        JasperPrintManager.printReport(jasperPrint, false);
                    }
                }

            }
            entityManager.getTransaction().begin();
            refresh();
            newButton.requestFocusInWindow();
        } catch (RollbackException rex) {
            JOptionPane.showMessageDialog(null, rex.getMessage());

            entityManager.getTransaction().begin();
            List<TblEventoDetalle> merged = new ArrayList<>(listEventosDetalle.size());
            listEventosDetalle.stream().forEach((t) -> {
                merged.add(entityManager.merge(t));
            });
            listEventosDetalle.clear();
            listEventosDetalle.addAll(merged);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_saveButton1ActionPerformed

    private void txtCtaCteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCtaCteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteActionPerformed

    private void getDatabaseIP() {
        try {
            databaseIP = Preferences.userRoot().node("Remates").get("DatabaseIP", "127.0.0.1");

            persistenceMap.put("javax.persistence.jdbc.url", "jdbc:derby://" + databaseIP + ":5432/mgdb");
            persistenceMap.put("javax.persistence.jdbc.user", "mg");
            persistenceMap.put("javax.persistence.jdbc.password", "123456");
            persistenceMap.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.ClientDriver");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.lacreacion.mg.utils.CategoriasConverter categoriasConverter1;
    private javax.swing.JComboBox cboCategoria;
    private javax.swing.JComboBox cboEntidad;
    private javax.swing.JComboBox cboFechaRemate;
    private com.lacreacion.mg.utils.DateTimeTableCellRenderer dateTableCellRenderer1;
    private com.lacreacion.mg.utils.DateTimeTableCellRenderer dateTimeTableCellRenderer1;
    private com.lacreacion.mg.utils.DateTimeToStringConverter dateTimeToStringConverter1;
    private javax.swing.JButton deleteButton;
    private javax.persistence.EntityManager entityManager;
    private javax.persistence.EntityManager entityManager1;
    private javax.swing.JTextField fechahoraField;
    private javax.swing.JLabel fechahoraLabel;
    private javax.swing.JLabel idCategoriaLabel;
    private javax.swing.JLabel idMiembroLabel;
    private javax.swing.JLabel idMiembroLabel1;
    private javax.swing.JLabel idMiembroLabel2;
    private com.lacreacion.mg.utils.IntegerLongConverter integerLongConverter1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalOperaciones;
    private java.util.List listEntidades;
    private java.util.List<com.lacreacion.mg.domain.TblEventos> listEventos;
    private java.util.List<com.lacreacion.mg.domain.TblEventoDetalle> listEventosDetalle;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private javax.swing.JFormattedTextField montoField;
    private javax.swing.JLabel montoLabel;
    private javax.swing.JButton newButton;
    private com.lacreacion.mg.utils.NormalTableCellRenderer normalTableCellRenderer1;
    private com.lacreacion.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.swing.JTextField observacionField;
    private javax.swing.JLabel observacionLabel;
    private javax.persistence.Query queryEntidades;
    private javax.persistence.Query queryEventos;
    private javax.persistence.Query queryEventosDetalle;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton saveButton1;
    private java.util.List<com.lacreacion.mg.domain.TblCategoriasArticulos> tblCategoriasArticulosList;
    private javax.persistence.Query tblCategoriasArticulosQuery;
    private javax.swing.JTextField txtCtaCte;
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
            java.util.logging.Logger.getLogger(FrameRematesDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameRematesDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameRematesDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameRematesDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new FrameRematesDetalle());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
