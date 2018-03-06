/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.frames.operaciones.egresos;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.gnadenheimer.mg.domain.TblAsientos;
import com.gnadenheimer.mg.domain.TblAutofacturas;
import com.gnadenheimer.mg.domain.TblCentrosDeCosto;
import com.gnadenheimer.mg.domain.TblCuentasContables;
import com.gnadenheimer.mg.domain.TblCuentasContablesPorDefecto;
import com.gnadenheimer.mg.domain.TblFacturas;
import com.gnadenheimer.mg.domain.TblFacturasCompra;
import com.gnadenheimer.mg.utils.CurrentUser;
import com.gnadenheimer.mg.utils.Utils;
import java.awt.Color;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public class FrameAutofacturacion extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameAutofacturacion.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    Map<String, String> persistenceMap = new HashMap<>();
    JComboBox<TblCentrosDeCosto> cboCentroDeCostoDebe = new JComboBox();
    JComboBox<TblCentrosDeCosto> cboCentroDeCostoHaber = new JComboBox();
    JComboBox<TblCuentasContables> cboCuentaHaber = new JComboBox();
    JComboBox<TblCuentasContables> cboCuentaDebe = new JComboBox();
    DatePickerSettings dps = new DatePickerSettings();
    TimePickerSettings tps = new TimePickerSettings();

    public FrameAutofacturacion() {
        super("Auto Facturacion",
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

            AutoCompleteSupport support2 = AutoCompleteSupport.install(cboCentroDeCostoDebe, (EventList) GlazedLists.eventListOf(listCentrosDeCosto.toArray()));
            support2.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support5 = AutoCompleteSupport.install(cboCentroDeCostoHaber, (EventList) GlazedLists.eventListOf(listCentrosDeCosto.toArray()));
            support5.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support3 = AutoCompleteSupport.install(cboCuentaDebe, (EventList) GlazedLists.eventListOf(listCuentasContables.toArray()));
            support3.setFilterMode(TextMatcherEditor.CONTAINS);

            AutoCompleteSupport support4 = AutoCompleteSupport.install(cboCuentaHaber, (EventList) GlazedLists.eventListOf(listCuentasContables.toArray()));
            support4.setFilterMode(TextMatcherEditor.CONTAINS);

            refresh();

            txtCantidad.getDocument().addDocumentListener(new DocumentListener() {
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
                        if (txtCantidad.getText().length() > 0 && txtCantidad.getValue() != null) {
                            updateAsientoInicial();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
                        LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
                    }
                }
            });

            txtPrecioUnitario.getDocument().addDocumentListener(new DocumentListener() {
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
                        if (txtPrecioUnitario.getText().length() > 0 && txtPrecioUnitario.getValue() != null) {
                            updateAsientoInicial();
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
                            if (e.getNewValue() instanceof JFormattedTextField) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        JFormattedTextField textField = (JFormattedTextField) e.getNewValue();
                                        textField.selectAll();
                                    }
                                });
                            }
                            if (e.getNewValue() instanceof JTextField) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        JTextField textField = (JTextField) e.getNewValue();
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
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblAutofacturas t ORDER BY t.nro");
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        queryTimbrados = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblTimbradosAutofacturas t WHERE t.activo = true");
        listTimbrados = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryTimbrados.getResultList());
        queryAsientos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblAsientos t WHERE t.id = null");
        listAsientos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryAsientos.getResultList());
        queryCuentasContables = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCuentasContables t where t.imputable = true");
        listCuentasContables = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryCuentasContables.getResultList());
        queryCentrosDeCosto = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblCentrosDeCosto t");
        listCentrosDeCosto = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryCentrosDeCosto.getResultList());
        numberCellRenderer1 = new com.gnadenheimer.mg.utils.NumberCellRenderer();
        buttonGroup1 = new javax.swing.ButtonGroup();
        cancelarButton = new javax.swing.JButton();
        imprimirButton = new javax.swing.JButton();
        montoLabel = new javax.swing.JLabel();
        txtTimbrado = new javax.swing.JFormattedTextField();
        montoLabel1 = new javax.swing.JLabel();
        fecha1Label = new javax.swing.JLabel();
        montoLabel2 = new javax.swing.JLabel();
        rucField = new javax.swing.JTextField();
        ctacteLabel3 = new javax.swing.JLabel();
        montoLabel3 = new javax.swing.JLabel();
        montoLabel4 = new javax.swing.JLabel();
        txtPrecioUnitario = new javax.swing.JFormattedTextField();
        txtRazonSocial = new javax.swing.JTextField();
        montoLabel5 = new javax.swing.JLabel();
        txtDomicilio = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JFormattedTextField();
        txtDireccionTransaccion = new javax.swing.JTextField();
        montoLabel7 = new javax.swing.JLabel();
        montoLabel8 = new javax.swing.JLabel();
        cmdBorrarAsiento = new javax.swing.JButton();
        cmdAddAsiento = new javax.swing.JButton();
        montoLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        asientosTable = new javax.swing.JTable();
        txtNro = new javax.swing.JFormattedTextField();
        txtConcepto = new javax.swing.JTextField();
        conceptoLabel = new javax.swing.JLabel();
        txtObservacion = new javax.swing.JTextField();
        dtpFecha = new com.github.lgooddatepicker.components.DateTimePicker(dps, tps);
        rbContado = new javax.swing.JRadioButton();
        rbCredito = new javax.swing.JRadioButton();

        FormListener formListener = new FormListener();

        numberCellRenderer1.setText("numberCellRenderer1");

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(formListener);

        imprimirButton.setText("Guardar & Imprimir");
        imprimirButton.addActionListener(formListener);

        montoLabel.setText("Timbrado:");
        montoLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtTimbrado.setColumns(9);
        txtTimbrado.setEditable(false);
        txtTimbrado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtTimbrado.addFocusListener(formListener);
        txtTimbrado.addMouseListener(formListener);
        txtTimbrado.addActionListener(formListener);

        montoLabel1.setText("Factura Nro::");
        montoLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        fecha1Label.setText("Fecha:");

        montoLabel2.setText("Nombre y Apellido del Vendedor:");
        montoLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        ctacteLabel3.setText("C.I.N°:");

        montoLabel3.setText("Cantidad");
        montoLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        montoLabel4.setText("Concepto");
        montoLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtPrecioUnitario.setColumns(9);
        txtPrecioUnitario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        montoLabel5.setText("Domicilio del Vendedor:");
        montoLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtDomicilio.setText("Loma Plata");

        txtCantidad.setColumns(9);
        txtCantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        txtDireccionTransaccion.setText("Loma Plata");

        montoLabel7.setText("Direccion de la Transaccoion:");
        montoLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        montoLabel8.setText("Precio Unitario");
        montoLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        cmdBorrarAsiento.setText("-");
        cmdBorrarAsiento.addActionListener(formListener);

        cmdAddAsiento.setText("+");
        cmdAddAsiento.addActionListener(formListener);

        montoLabel6.setText("Asientos");

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listAsientos, asientosTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCentroDeCostoDebe}"));
        columnBinding.setColumnName("Centro de Costo Debe");
        columnBinding.setColumnClass(com.gnadenheimer.mg.domain.TblCentrosDeCosto.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCuentaContableDebe}"));
        columnBinding.setColumnName("Cuenta Debe");
        columnBinding.setColumnClass(com.gnadenheimer.mg.domain.TblCuentasContables.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCentroDeCostoHaber}"));
        columnBinding.setColumnName("Centro de Costo Haber");
        columnBinding.setColumnClass(com.gnadenheimer.mg.domain.TblCentrosDeCosto.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCuentaContableHaber}"));
        columnBinding.setColumnName("Cuenta Haber");
        columnBinding.setColumnClass(com.gnadenheimer.mg.domain.TblCuentasContables.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${monto}"));
        columnBinding.setColumnName("Importe");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(asientosTable);

        try {
            txtNro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-#######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        conceptoLabel.setText("Observacion:");

        buttonGroup1.add(rbContado);
        rbContado.setText("Pago en misma Fecha");
        rbContado.addActionListener(formListener);

        buttonGroup1.add(rbCredito);
        rbCredito.setText("Pago en otra Fecha");
        rbCredito.addActionListener(formListener);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(montoLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(montoLabel2)
                                            .addComponent(ctacteLabel3)
                                            .addComponent(montoLabel5)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(montoLabel)
                                            .addComponent(montoLabel1)
                                            .addComponent(fecha1Label))))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(rucField, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                        .addComponent(txtDireccionTransaccion)
                                        .addComponent(txtRazonSocial))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtNro, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtTimbrado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(38, 38, 38)
                                        .addComponent(rbContado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbCredito)))))
                        .addGap(0, 76, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(montoLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(montoLabel4)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtConcepto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(montoLabel8))
                                    .addComponent(txtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(conceptoLabel)
                                .addGap(8, 8, 8)
                                .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(montoLabel6)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmdAddAsiento)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmdBorrarAsiento)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imprimirButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelarButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelarButton, imprimirButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(montoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoLabel1)
                    .addComponent(txtNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fecha1Label)
                    .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbContado)
                        .addComponent(rbCredito)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoLabel2)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ctacteLabel3)
                    .addComponent(rucField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(montoLabel5))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccionTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(montoLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(montoLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(montoLabel4)
                        .addGap(26, 26, 26))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(montoLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(montoLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdAddAsiento)
                    .addComponent(cmdBorrarAsiento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conceptoLabel)
                    .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelarButton)
                    .addComponent(imprimirButton))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.MouseListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == cancelarButton) {
                FrameAutofacturacion.this.cancelarButtonActionPerformed(evt);
            }
            else if (evt.getSource() == imprimirButton) {
                FrameAutofacturacion.this.imprimirButtonActionPerformed(evt);
            }
            else if (evt.getSource() == txtTimbrado) {
                FrameAutofacturacion.this.txtTimbradoActionPerformed(evt);
            }
            else if (evt.getSource() == cmdBorrarAsiento) {
                FrameAutofacturacion.this.cmdBorrarAsientoActionPerformed(evt);
            }
            else if (evt.getSource() == cmdAddAsiento) {
                FrameAutofacturacion.this.cmdAddAsientoActionPerformed(evt);
            }
            else if (evt.getSource() == rbCredito) {
                FrameAutofacturacion.this.rbCreditoActionPerformed(evt);
            }
            else if (evt.getSource() == rbContado) {
                FrameAutofacturacion.this.rbContadoActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == txtTimbrado) {
                FrameAutofacturacion.this.txtTimbradoFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == txtTimbrado) {
                FrameAutofacturacion.this.txtTimbradoMouseClicked(evt);
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

    private void updateAsientoInicial() {
        try {
            if (txtCantidad.getValue() != null && txtPrecioUnitario.getValue() != null) {

                if (asientosTable.getModel().getRowCount() == 1) {
                    asientosTable.getModel().setValueAt(((Number) txtCantidad.getValue()).intValue() * ((Number) txtPrecioUnitario.getValue()).intValue(), 0, 4);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }

    private void refresh() {
        try {
            if (listTimbrados.size() > 0) {
                rbContado.setSelected(true);
                txtTimbrado.setText(listTimbrados.get(0).getNro());
                imprimirButton.setEnabled(true);
                if (list.size() > 0) {
                    txtNro.setValue(Utils.generateNextFacturaNroFull(list.get(list.size() - 1).getNro()));
                } else {
                    txtNro.setValue(Utils.generateFacturaNroFull(listTimbrados.get(0).getNroFacturaIncio()));
                }

                dtpFecha.setDateTimeStrict(LocalDateTime.now());

                txtRazonSocial.setText("");
                rucField.setText("");
                txtDomicilio.setText("");
                txtDireccionTransaccion.setText("");

                txtCantidad.setValue(1);
                txtConcepto.setText("");
                txtObservacion.setText("");

                listAsientos.clear();
                dtpFecha.requestFocusInWindow();
            } else {
                JOptionPane.showMessageDialog(null, "Debe tener un timbrado activo para poder facturar.");
                imprimirButton.setEnabled(false);
            }
        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }

    Boolean validar() {
        /*if ((Integer) txtNro.getValue() < 1) {
            txtNro.setBackground(Color.red);
            return false;
        }*/
        if (txtRazonSocial.getText().equals("")) {
            txtRazonSocial.setBackground(Color.red);
            return false;
        }
        if (rucField.getText().equals("")) {
            rucField.setBackground(Color.red);
            return false;
        }
        /*
        if (list.size() > 0) {
            TblAutofacturas ultimaFactura = list.get(list.size() - 1);
            if ((String) txtNro.getValue() <= ultimaFactura.getNro()) {
                return false;
            }

        }*/

        return true;
    }

    @SuppressWarnings("unchecked")
    private void imprimirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirButtonActionPerformed
        try {

            if (!validar()) {
                JOptionPane.showMessageDialog(null, "Datos no válidos!");
                return;
            }

            TblAutofacturas factura = new TblAutofacturas();
            entityManager.persist(factura);

            factura.setNro(txtNro.getValue().toString());
            factura.setIdTimbrado(listTimbrados.get(0));
            factura.setFechahora(dtpFecha.getDateTimeStrict());
            factura.setNombre(txtRazonSocial.getText());
            factura.setCi(rucField.getText());
            factura.setDomicilio(txtDomicilio.getText());
            factura.setDireccionDeTransaccion(txtDireccionTransaccion.getText());
            factura.setCantidad(((Number) txtCantidad.getValue()).intValue());
            factura.setConcepto(txtConcepto.getText());
            factura.setPrecioUnitario(((Number) txtPrecioUnitario.getValue()).intValue());
            factura.setMonto(factura.getCantidad() * factura.getPrecioUnitario());
            factura.setAnulado(false);
            factura.setObservacion(txtObservacion.getText());
            factura.setIdUser(currentUser.getUser());
            factura.setCondicionContado(rbContado.isSelected());

            for (TblAsientos a : listAsientos) {
                a.setFechahora(factura.getFechahora());
                a.setIdUser(factura.getIdUser());
            }

            factura.setTblAsientosList(listAsientos);

            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();

            Utils.getInstance().printAutofactura(factura);

            refresh();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_imprimirButtonActionPerformed

    void print(TblFacturas factura) {
        try {

            /*Map parameters = new HashMap();
             parameters.put("factura_id", factura.getNro());
             parameters.put("fechahora", factura.getFechahora());
             parameters.put("razon_social", factura.getRazonSocial());
             parameters.put("ruc", factura.getRuc());
             parameters.put("importe_aporte", factura.getImporteAporte());
             parameters.put("importe_donacion", factura.getImporteDonacion());

             parameters.put("logo", getClass().getResourceAsStream("/reports/cclogo200.png"));
             parameters.put("logo2", getClass().getResourceAsStream("/reports/cclogo200.png"));
             parameters.put("logo3", getClass().getResourceAsStream("/reports/cclogo200.png"));
             //JOptionPane.showMessageDialog(null, getClass().getResource("/reports/cclogo200.png").getPath());
             JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/factura.jrxml"));

             JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters);
             //JasperViewer jReportsViewer = new JasperViewer(jasperPrint, false);
             //jReportsViewer.setVisible(true);
             jasperPrint.setLeftMargin(Integer.getInteger(Preferences.userRoot().node("MG").get("facturaLeftMargin", "0")));
             jasperPrint.setTopMargin(Integer.getInteger(Preferences.userRoot().node("MG").get("facturaTopMargin", "0")));
             JasperPrintManager.printReport(jasperPrint, false);*/
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarButtonActionPerformed
        try {

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cancelarButtonActionPerformed

    private void txtTimbradoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimbradoFocusGained

    }//GEN-LAST:event_txtTimbradoFocusGained

    private void txtTimbradoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimbradoMouseClicked

    }//GEN-LAST:event_txtTimbradoMouseClicked

    private void txtTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimbradoActionPerformed

    }//GEN-LAST:event_txtTimbradoActionPerformed

    private void cmdBorrarAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBorrarAsientoActionPerformed
        try {
            int[] selected = asientosTable.getSelectedRows();
            List<TblAsientos> toRemove = new ArrayList<>(selected.length);
            for (Integer idx = 0; idx < selected.length; idx++) {
                TblAsientos t = listAsientos.get(asientosTable.convertRowIndexToModel(selected[idx]));
                toRemove.add(t);
            }
            listAsientos.removeAll(toRemove);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cmdBorrarAsientoActionPerformed

    private void cmdAddAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddAsientoActionPerformed
        addAsiento();
        updateAsientoInicial();
    }//GEN-LAST:event_cmdAddAsientoActionPerformed

    private void rbCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCreditoActionPerformed

    }//GEN-LAST:event_rbCreditoActionPerformed

    private void rbContadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbContadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbContadoActionPerformed

    private void addAsiento() {
        try {
            TblAsientos t = new TblAsientos();
            t.setFechahora(dtpFecha.getDateTimeStrict());
            t.setIdUser(currentUser.getUser());

            TblCuentasContablesPorDefecto tblCuentasContablesPorDefecto = (TblCuentasContablesPorDefecto) entityManager.createQuery("SELECT t FROM TblCuentasContablesPorDefecto t WHERE t.id = 1").getSingleResult();

            t.setIdCentroDeCostoDebe((TblCentrosDeCosto) entityManager.createQuery("SELECT t FROM TblCentrosDeCosto t WHERE t.preferido = true").getSingleResult());
            t.setIdCentroDeCostoHaber(t.getIdCentroDeCostoDebe());
            t.setIdCuentaContableDebe(tblCuentasContablesPorDefecto.getIdCuentaDebeCompras());
            if (rbContado.isSelected()) {
                t.setIdCuentaContableHaber(tblCuentasContablesPorDefecto.getIdCuentaHaberComprasFacturaContado());
            } else {
                t.setIdCuentaContableHaber(tblCuentasContablesPorDefecto.getIdCuentaHaberComprasFacturaCredito());
            }
            t.setMonto(0);
            listAsientos.add(t);
            Integer row = listAsientos.size() - 1;
            asientosTable.setRowSelectionInterval(row, row);
            asientosTable.scrollRectToVisible(asientosTable.getCellRect(row, 0, true));
            if (asientosTable.getColumnModel().getColumnCount() > 0 && asientosTable.getRowCount() == 1) {
                asientosTable.getColumn("Centro de Costo Debe").setCellEditor(new DefaultCellEditor(cboCentroDeCostoDebe));
                asientosTable.getColumn("Centro de Costo Haber").setCellEditor(new DefaultCellEditor(cboCentroDeCostoHaber));
                asientosTable.getColumn("Cuenta Debe").setCellEditor(new DefaultCellEditor(cboCuentaDebe));
                asientosTable.getColumn("Cuenta Haber").setCellEditor(new DefaultCellEditor(cboCuentaHaber));
                asientosTable.getColumnModel().getColumn(4).setCellRenderer(numberCellRenderer1);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable asientosTable;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelarButton;
    private javax.swing.JButton cmdAddAsiento;
    private javax.swing.JButton cmdBorrarAsiento;
    private javax.swing.JLabel conceptoLabel;
    private javax.swing.JLabel ctacteLabel3;
    private com.github.lgooddatepicker.components.DateTimePicker dtpFecha;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel fecha1Label;
    private javax.swing.JButton imprimirButton;
    private javax.swing.JScrollPane jScrollPane2;
    private java.util.List<com.gnadenheimer.mg.domain.TblAutofacturas> list;
    private java.util.List<com.gnadenheimer.mg.domain.TblAsientos> listAsientos;
    private java.util.List<com.gnadenheimer.mg.domain.TblCentrosDeCosto> listCentrosDeCosto;
    private java.util.List<com.gnadenheimer.mg.domain.TblCuentasContables> listCuentasContables;
    private java.util.List<com.gnadenheimer.mg.domain.TblTimbradosAutofacturas> listTimbrados;
    private javax.swing.JLabel montoLabel;
    private javax.swing.JLabel montoLabel1;
    private javax.swing.JLabel montoLabel2;
    private javax.swing.JLabel montoLabel3;
    private javax.swing.JLabel montoLabel4;
    private javax.swing.JLabel montoLabel5;
    private javax.swing.JLabel montoLabel6;
    private javax.swing.JLabel montoLabel7;
    private javax.swing.JLabel montoLabel8;
    private com.gnadenheimer.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query query;
    private javax.persistence.Query queryAsientos;
    private javax.persistence.Query queryCentrosDeCosto;
    private javax.persistence.Query queryCuentasContables;
    private javax.persistence.Query queryTimbrados;
    private javax.swing.JRadioButton rbContado;
    private javax.swing.JRadioButton rbCredito;
    private javax.swing.JTextField rucField;
    private javax.swing.JFormattedTextField txtCantidad;
    private javax.swing.JTextField txtConcepto;
    private javax.swing.JTextField txtDireccionTransaccion;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JFormattedTextField txtNro;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JFormattedTextField txtPrecioUnitario;
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
            java.util.logging.Logger.getLogger(FrameAutofacturacion.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameAutofacturacion.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameAutofacturacion.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameAutofacturacion.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                frame.setContentPane(new FrameAutofacturacion());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
