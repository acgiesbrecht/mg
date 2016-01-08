/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.lacreacion.mg.domain.TblCategoriasArticulos;
import com.lacreacion.mg.domain.TblEntidades;
import com.lacreacion.mg.domain.TblEventoDetalle;
import com.lacreacion.mg.domain.TblEventos;
import com.lacreacion.mg.domain.TblRecibos;
import com.lacreacion.mg.domain.TblTransferencias;
import com.lacreacion.mg.utils.CurrentUser;
import com.lacreacion.mg.utils.Utils;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class FrameAportesDetalle extends JInternalFrame {

    String databaseIP;
    Map<String, String> persistenceMap = new HashMap<>();
    EventList<TblEntidades> eventListEntidades = new BasicEventList<>();
    CurrentUser currentUser = CurrentUser.getInstance();

    public FrameAportesDetalle() {
        super("Aportes",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {
            persistenceMap = Utils.getInstance().getDatabaseIP();

            initComponents();

            cboEntidad.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        montoField.requestFocusInWindow();
                    }
                }
            });
            /*this.dateTimeTableCellRenderer1.setEnProceso(true);
             this.numberCellRenderer1.setEnProceso(true);
             this.normalTableCellRenderer1.setEnProceso(true);
             */
            if (!Beans.isDesignTime()) {
                entityManager.getTransaction().begin();
                entityManager1.getTransaction().begin();
            }

            //AutoCompleteDecorator.decorate(cboFechaRemate);
            //AutoCompleteDecorator.decorate(cboCategoria);
            AutoCompleteSupport support = AutoCompleteSupport.install(cboFechaAporte, GlazedLists.eventListOf(listEventos.toArray()));
            support.setFilterMode(TextMatcherEditor.CONTAINS);

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
                cboFechaAporte.setSelectedItem(value.get());
            } else {
                cboFechaAporte.setSelectedIndex(-1);
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
        queryEntidades = java.beans.Beans.isDesignTime() ? null : entityManager1.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        listEntidades = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEntidades.getResultList());
        normalTableCellRenderer1 = new com.lacreacion.mg.utils.NormalTableCellRenderer();
        categoriasConverter1 = new com.lacreacion.mg.utils.CategoriasConverter();
        queryEventos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventos t WHERE t.idEventoTipo.id = 3 AND t.idGrupo IN :grupos ORDER BY t.fecha");
        queryEventos.setParameter("grupos", currentUser.getUser().getTblGruposList());
        listEventos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventos.getResultList());
        queryEventoDetalle = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEventoDetalle t WHERE t.idEvento = :eventoId ORDER BY t.id");
        queryEventoDetalle.setParameter("eventoId", null) ;
        listEventoDetalle = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEventoDetalle.getResultList());
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        dateTimeTableCellRenderer1 = new com.lacreacion.mg.utils.DateTimeTableCellRenderer();
        integerLongConverter1 = new com.lacreacion.mg.utils.IntegerLongConverter();
        numberCellRenderer1 = new com.lacreacion.mg.utils.NumberCellRenderer();
        dateTimeToStringConverter1 = new com.lacreacion.mg.utils.DateTimeToStringConverter();
        tblCategoriasArticulosQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCategoriasArticulos t ORDER BY t.descripcion");
        tblCategoriasArticulosList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(tblCategoriasArticulosQuery.getResultList());
        tblFormasDePagoQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblFormasDePago t ORDER BY t.id");
        tblFormasDePagoList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(tblFormasDePagoQuery.getResultList());
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
        dateTableCellRenderer1 = new com.lacreacion.mg.utils.DateTimeTableCellRenderer();
        cboEntidad = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblTotalOperaciones = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboFechaAporte = new javax.swing.JComboBox();
        montoField = new javax.swing.JFormattedTextField();
        jButton2 = new javax.swing.JButton();
        montoLabel1 = new javax.swing.JLabel();
        cboForma = new javax.swing.JComboBox();
        cmdGenerar = new javax.swing.JButton();

        FormListener formListener = new FormListener();

        normalTableCellRenderer1.setText("normalTableCellRenderer1");

        dateTimeTableCellRenderer1.setText("dateTimeTableCellRenderer1");

        numberCellRenderer1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numberCellRenderer1.setText("numberCellRenderer1");

        addInternalFrameListener(formListener);

        masterTable.setAutoCreateRowSorter(true);
        masterTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        masterTable.setRowHeight(20);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEventoDetalle, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idEntidad}"));
        columnBinding.setColumnName("Miembro");
        columnBinding.setColumnClass(com.lacreacion.mg.domain.TblEntidades.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${monto}"));
        columnBinding.setColumnName("Monto");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(0).setPreferredWidth(80);
            masterTable.getColumnModel().getColumn(1).setPreferredWidth(20);
            masterTable.getColumnModel().getColumn(1).setCellRenderer(numberCellRenderer1);
        }

        montoLabel.setDisplayedMnemonic('M');
        montoLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        montoLabel.setLabelFor(montoField);
        montoLabel.setText("Monto:");

        idMiembroLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel.setText("Miembro:");

        saveButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        saveButton.setMnemonic('G');
        saveButton.setText("Guardar");
        saveButton.addActionListener(formListener);

        refreshButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        refreshButton.setMnemonic('C');
        refreshButton.setText("Cancelar");
        refreshButton.addActionListener(formListener);

        newButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        newButton.setMnemonic('N');
        newButton.setText("Nuevo");
        newButton.addFocusListener(formListener);
        newButton.addActionListener(formListener);

        deleteButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        deleteButton.setMnemonic('E');
        deleteButton.setText("Eliminar");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), deleteButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
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

        idMiembroLabel2.setDisplayedMnemonic('O');
        idMiembroLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel2.setLabelFor(cboEntidad);
        idMiembroLabel2.setText("Nombre:");

        cboEntidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEntidades, cboEntidad);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idEntidad}"), cboEntidad, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblTotalOperaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        jLabel2.setText("Fecha de Aporte:");

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listEventos, cboFechaAporte);
        bindingGroup.addBinding(jComboBoxBinding);

        cboFechaAporte.addActionListener(formListener);

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

        jButton2.setMnemonic('A');
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

        cmdGenerar.setText("Generar Aportes");
        cmdGenerar.setEnabled(false);
        cmdGenerar.addActionListener(formListener);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(masterScrollPane)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 398, Short.MAX_VALUE)
                                .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(refreshButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(deleteButton, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(332, 332, 332)
                                .addComponent(dateTableCellRenderer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboFechaAporte, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmdGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(montoLabel1)
                            .addComponent(montoLabel)
                            .addComponent(idMiembroLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(montoField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboForma, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idMiembroLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(idMiembroLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, refreshButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboFechaAporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdGenerar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(masterScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idMiembroLabel)
                    .addComponent(idMiembroLabel1)
                    .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idMiembroLabel2)
                    .addComponent(cboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoLabel1)
                    .addComponent(cboForma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(montoLabel))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(deleteButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(refreshButton))))
                .addGap(97, 97, 97)
                .addComponent(dateTableCellRenderer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.KeyListener, java.awt.event.MouseListener, javax.swing.event.InternalFrameListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                FrameAportesDetalle.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FrameAportesDetalle.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == newButton) {
                FrameAportesDetalle.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                FrameAportesDetalle.this.deleteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == txtCtaCte) {
                FrameAportesDetalle.this.txtCtaCteActionPerformed(evt);
            }
            else if (evt.getSource() == cboEntidad) {
                FrameAportesDetalle.this.cboEntidadActionPerformed(evt);
            }
            else if (evt.getSource() == cboFechaAporte) {
                FrameAportesDetalle.this.cboFechaAporteActionPerformed(evt);
            }
            else if (evt.getSource() == montoField) {
                FrameAportesDetalle.this.montoFieldActionPerformed(evt);
            }
            else if (evt.getSource() == jButton2) {
                FrameAportesDetalle.this.jButton2ActionPerformed(evt);
            }
            else if (evt.getSource() == cboForma) {
                FrameAportesDetalle.this.cboFormaActionPerformed(evt);
            }
            else if (evt.getSource() == cmdGenerar) {
                FrameAportesDetalle.this.cmdGenerarActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == txtCtaCte) {
                FrameAportesDetalle.this.txtCtaCteFocusGained(evt);
            }
            else if (evt.getSource() == montoField) {
                FrameAportesDetalle.this.montoFieldFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == newButton) {
                FrameAportesDetalle.this.newButtonFocusLost(evt);
            }
        }

        public void keyPressed(java.awt.event.KeyEvent evt) {
        }

        public void keyReleased(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == txtCtaCte) {
                FrameAportesDetalle.this.txtCtaCteKeyReleased(evt);
            }
            else if (evt.getSource() == montoField) {
                FrameAportesDetalle.this.montoFieldKeyReleased(evt);
            }
            else if (evt.getSource() == cboForma) {
                FrameAportesDetalle.this.cboFormaKeyReleased(evt);
            }
        }

        public void keyTyped(java.awt.event.KeyEvent evt) {
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == montoField) {
                FrameAportesDetalle.this.montoFieldMouseClicked(evt);
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
            if (evt.getSource() == FrameAportesDetalle.this) {
                FrameAportesDetalle.this.formInternalFrameActivated(evt);
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
            if (cboFechaAporte.getSelectedItem() != null && entityManager.getTransaction().isActive()) {
                newButton.setEnabled(true);
                cmdGenerar.setEnabled(true);
                entityManager.getTransaction().rollback();
                entityManager.getTransaction().begin();
                queryEventoDetalle.setParameter("eventoId", ((TblEventos) cboFechaAporte.getSelectedItem()));
                java.util.List data = queryEventoDetalle.getResultList();
                data.stream().forEach((entity) -> {
                    entityManager.refresh(entity);
                });
                listEventoDetalle.clear();
                listEventoDetalle.addAll(data);

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

                lblTotal.setText(String.format("%,d", listEventoDetalle.stream().mapToInt(a -> a.getMonto()).sum()));
                lblTotalOperaciones.setText(String.format("%,d", listEventoDetalle.stream().mapToInt(a -> a.getMonto()).count()));
                txtCtaCte.setText("");
            } else {
                newButton.setEnabled(false);
                cmdGenerar.setEnabled(false);
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
                TblEventoDetalle t = listEventoDetalle.get(masterTable.convertRowIndexToModel(selected[idx]));
                toRemove.add(t);
                entityManager.remove(t);
            }
            listEventoDetalle.removeAll(toRemove);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void newDetalle() {
        try {
            TblEventoDetalle t = new TblEventoDetalle();
            entityManager.persist(t);
            TblEventos currEvento = (TblEventos) cboFechaAporte.getSelectedItem();
            t.setIdEvento(currEvento);
            t.setFechahora(currEvento.getFecha());
            t.setIdCategoriaArticulo(entityManager.find(TblCategoriasArticulos.class, 2));
            t.setIdUser(currentUser.getUser());

            listEventoDetalle.add(t);
            int row = listEventoDetalle.size() - 1;
            masterTable.setRowSelectionInterval(row, row);
            masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));

            cboEntidad.requestFocusInWindow();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
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
            TblEventos currentEvento = (TblEventos) cboFechaAporte.getSelectedItem();

            //actialuazr transferencias y recibos
            if ((int) entityManager.createQuery("SELECT COUNT(t) FROM TblTransferencias t WHERE t.cobrado = true AND t.idEvento.id = " + String.valueOf(currentEvento.getId())).getSingleResult() > 0) {
                JOptionPane.showMessageDialog(null, "Los cambios realizados a este evento no tendrán efecto sobre los pagos realizados porque ya hay transferencias cobradas para este evento. Deberá modificar las transferencias y los recibos manualmente.");
            } else {
                int res = entityManager.createQuery("DELETE FROM TblTransferencias t WHERE t.idEvento.id = " + String.valueOf(currentEvento.getId())).executeUpdate();
                res = entityManager.createQuery("DELETE FROM TblRecibos t WHERE t.idEvento.id = " + String.valueOf(currentEvento.getId())).executeUpdate();

                for (TblEventoDetalle t : listEventoDetalle) {
                    if (t.getIdFormaDePagoPreferida().getId().equals(1)) {
                        TblTransferencias transf = new TblTransferencias();
                        transf.setFechahora(t.getFechahora());
                        transf.setConcepto(currentEvento.getDescripcion());
                        transf.setIdEntidad(t.getIdEntidad());
                        transf.setIdEvento(currentEvento);
                        transf.setMonto(t.getMonto());
                        transf.setPorcentajeAporte(currentEvento.getPorcentajeAporte());
                        transf.setIdUser(t.getIdUser());
                        entityManager.persist(transf);
                    } else {
                        TblRecibos recibo = new TblRecibos();
                        recibo.setFechahora(t.getFechahora());
                        recibo.setConcepto(currentEvento.getDescripcion());
                        recibo.setIdEntidad(t.getIdEntidad());
                        recibo.setIdEvento(currentEvento);
                        recibo.setMonto(t.getMonto());
                        recibo.setPorcentajeAporte(currentEvento.getPorcentajeAporte());
                        recibo.setIdUser(t.getIdUser());
                        entityManager.persist(recibo);
                    }
                }
            }
            //------------------------------------
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            refresh();
            newButton.requestFocus();
        } catch (RollbackException rex) {
            JOptionPane.showMessageDialog(null, rex.getMessage());

            entityManager.getTransaction().begin();
            List<com.lacreacion.mg.domain.TblEventoDetalle> merged = new ArrayList<>(listEventoDetalle.size());
            listEventoDetalle.stream().forEach((t) -> {
                merged.add(entityManager.merge(t));
            });
            listEventoDetalle.clear();
            listEventoDetalle.addAll(merged);
        }

    }
    private void txtCtaCteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCtaCteKeyReleased
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                cboEntidad.requestFocusInWindow();
            }
            txtCtaCte.setBackground(Color.white);
            if (txtCtaCte.getText().length() > 4) {
                List<TblEntidades> list = listEntidades;
                Optional<TblEntidades> value = list.stream().filter(a -> a.getCtacte().equals(Integer.valueOf(txtCtaCte.getText())))
                        .findFirst();
                if (value.isPresent()) {
                    cboEntidad.setSelectedItem(value.get());
                    txtCtaCte.setBackground(Color.green);
                    montoField.requestFocus();
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

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        refresh();
    }//GEN-LAST:event_formInternalFrameActivated

    @SuppressWarnings("unchecked")
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed

        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void cboFechaAporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFechaAporteActionPerformed
        try {
            refresh();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cboFechaAporteActionPerformed

    private void montoFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_montoFieldFocusGained
        montoField.selectAll();
    }//GEN-LAST:event_montoFieldFocusGained

    private void montoFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_montoFieldMouseClicked

    }//GEN-LAST:event_montoFieldMouseClicked

    private void montoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_montoFieldActionPerformed

    }//GEN-LAST:event_montoFieldActionPerformed

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
            montoField.requestFocusInWindow();
        } else {
            txtCtaCte.setText("");
        }
    }//GEN-LAST:event_cboEntidadActionPerformed

    private void txtCtaCteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCtaCteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteActionPerformed

    private void cboFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFormaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboFormaActionPerformed

    private void cmdGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGenerarActionPerformed
        try {
            if (cboFechaAporte.getSelectedItem() != null) {
                TblEventos currEvento = (TblEventos) cboFechaAporte.getSelectedItem();
                listEventoDetalle.stream().forEach((aporte) -> {
                    entityManager.remove(aporte);

                });
                listEventoDetalle.clear();
                listEntidades.stream().forEach((miembro) -> {
                    if (miembro.getAporteMensual() > 0) {
                        TblEventoDetalle t = new TblEventoDetalle();
                        entityManager.persist(t);
                        t.setFechahora(currEvento.getFecha());
                        t.setIdEvento(currEvento);
                        t.setIdUser(currentUser.getUser());
                        t.setIdCategoriaArticulo(entityManager.find(TblCategoriasArticulos.class, 2));
                        t.setIdEntidad(miembro);
                        t.setIdFormaDePagoPreferida(miembro.getIdFormaDePagoPreferida());
                        t.setMonto(miembro.getAporteMensual());
                        listEventoDetalle.add(t);
                        int row = listEventoDetalle.size() - 1;
                        masterTable.setRowSelectionInterval(row, row);
                        //masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));
                    }
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            ex.printStackTrace();
        }

    }//GEN-LAST:event_cmdGenerarActionPerformed

    private void newButtonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newButtonFocusLost
        cboEntidad.requestFocusInWindow();
    }//GEN-LAST:event_newButtonFocusLost

    private void montoFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_montoFieldKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (((Number) montoField.getValue()).intValue() == 0) {
                JOptionPane.showMessageDialog(null, "El monto no puede ser 0.");
                montoField.requestFocusInWindow();
                return;
            }
            save();
            newDetalle();
        }
    }//GEN-LAST:event_montoFieldKeyReleased

    private void cboFormaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboFormaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            montoField.requestFocusInWindow();
        }
    }//GEN-LAST:event_cboFormaKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.lacreacion.mg.utils.CategoriasConverter categoriasConverter1;
    private javax.swing.JComboBox cboEntidad;
    private javax.swing.JComboBox cboFechaAporte;
    private javax.swing.JComboBox cboForma;
    private javax.swing.JButton cmdGenerar;
    private com.lacreacion.mg.utils.DateTimeTableCellRenderer dateTableCellRenderer1;
    private com.lacreacion.mg.utils.DateTimeTableCellRenderer dateTimeTableCellRenderer1;
    private com.lacreacion.mg.utils.DateTimeToStringConverter dateTimeToStringConverter1;
    private javax.swing.JButton deleteButton;
    private javax.persistence.EntityManager entityManager;
    private javax.persistence.EntityManager entityManager1;
    private javax.swing.JLabel idMiembroLabel;
    private javax.swing.JLabel idMiembroLabel1;
    private javax.swing.JLabel idMiembroLabel2;
    private com.lacreacion.mg.utils.IntegerLongConverter integerLongConverter1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalOperaciones;
    private java.util.List<com.lacreacion.mg.domain.TblEntidades> listEntidades;
    private java.util.List<com.lacreacion.mg.domain.TblEventoDetalle> listEventoDetalle;
    private java.util.List<com.lacreacion.mg.domain.TblEventos> listEventos;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private javax.swing.JFormattedTextField montoField;
    private javax.swing.JLabel montoLabel;
    private javax.swing.JLabel montoLabel1;
    private javax.swing.JButton newButton;
    private com.lacreacion.mg.utils.NormalTableCellRenderer normalTableCellRenderer1;
    private com.lacreacion.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query queryEntidades;
    private javax.persistence.Query queryEventoDetalle;
    private javax.persistence.Query queryEventos;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private java.util.List<com.lacreacion.mg.domain.TblCategoriasArticulos> tblCategoriasArticulosList;
    private javax.persistence.Query tblCategoriasArticulosQuery;
    private java.util.List<com.lacreacion.mg.domain.TblCategoriasArticulos> tblFormasDePagoList;
    private javax.persistence.Query tblFormasDePagoQuery;
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
            java.util.logging.Logger.getLogger(FrameAportesDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameAportesDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameAportesDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameAportesDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                frame.setContentPane(new FrameAportesDetalle());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
