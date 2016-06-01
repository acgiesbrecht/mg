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
import com.parah.mg.domain.TblContribuyentes;
import com.parah.mg.domain.TblFacturas;
import com.parah.mg.domain.TblRecibos;
import com.parah.mg.domain.TblTransferencias;
import com.parah.mg.domain.miembros.TblEntidades;
import com.parah.mg.domain.models.PagosRealizados;
import com.parah.mg.utils.CurrentUser;
import com.parah.mg.utils.Utils;
import com.parah.utils.CalcDV;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public class FrameFacturacionUnica extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameFacturacionUnica.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    Map<String, String> persistenceMap = new HashMap<>();
    EventList<TblEntidades> eventListMiembros = new BasicEventList<>();

    public FrameFacturacionUnica() {
        super("Facturacion Unica",
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

            eventListMiembros.clear();
            eventListMiembros.addAll(listEntidades);
            AutoCompleteSupport support1 = AutoCompleteSupport.install(cboEntidad, eventListMiembros);
            support1.setFilterMode(TextMatcherEditor.CONTAINS);

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
            refresh();
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
        query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblFacturas t ORDER BY t.nro");
        list = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(query.getResultList());
        queryGrupos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblGrupos t");
        listGrupos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryGrupos.getResultList());
        queryPagos = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblTransferencias t WHERE t.id = null");
        listPagos = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryPagos.getResultList());
        entityManager1 = java.beans.Beans.isDesignTime() ? null : Persistence.createEntityManagerFactory("mg_PU", persistenceMap).createEntityManager();
        queryEntidades = java.beans.Beans.isDesignTime() ? null : entityManager1.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        listEntidades = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEntidades.getResultList());
        queryTimbrados = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblTimbrados t WHERE t.activo = true");
        listTimbrados = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryTimbrados.getResultList());
        cancelarButton = new javax.swing.JButton();
        imprimirButton = new javax.swing.JButton();
        montoLabel = new javax.swing.JLabel();
        txtTimbrado = new javax.swing.JFormattedTextField();
        montoLabel1 = new javax.swing.JLabel();
        txtNro = new javax.swing.JFormattedTextField();
        fecha1Label = new javax.swing.JLabel();
        dtpFecha = new org.jdesktop.swingx.JXDatePicker();
        montoLabel2 = new javax.swing.JLabel();
        idMiembroLabel1 = new javax.swing.JLabel();
        txtCtaCte = new javax.swing.JTextField();
        idMiembroLabel2 = new javax.swing.JLabel();
        cboEntidad = new javax.swing.JComboBox();
        idMiembroLabel = new javax.swing.JLabel();
        rucField = new javax.swing.JTextField();
        ctacteLabel3 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        montoLabel5 = new javax.swing.JLabel();
        txtDomicilio = new javax.swing.JTextField();
        montoLabel6 = new javax.swing.JLabel();
        txtCdC = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        FormListener formListener = new FormListener();

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(formListener);

        imprimirButton.setText("Guardar & Imprimir");
        imprimirButton.addActionListener(formListener);

        montoLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        montoLabel.setText("Timbrado:");

        txtTimbrado.setEditable(false);
        txtTimbrado.setColumns(9);
        txtTimbrado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtTimbrado.addFocusListener(formListener);
        txtTimbrado.addMouseListener(formListener);
        txtTimbrado.addActionListener(formListener);

        montoLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        montoLabel1.setText("Factura Nro::");

        txtNro.setColumns(9);
        txtNro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtNro.addFocusListener(formListener);
        txtNro.addMouseListener(formListener);
        txtNro.addActionListener(formListener);

        fecha1Label.setText("Fecha:");

        montoLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        montoLabel2.setText("Razon Social:");

        idMiembroLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel1.setText("Cta. Cte.:");

        txtCtaCte.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCtaCte.addFocusListener(formListener);
        txtCtaCte.addInputMethodListener(formListener);
        txtCtaCte.addActionListener(formListener);
        txtCtaCte.addKeyListener(formListener);

        idMiembroLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel2.setText("Nombre:");

        cboEntidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboEntidad.addActionListener(formListener);

        idMiembroLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        idMiembroLabel.setText("Miembro:");

        ctacteLabel3.setText("RUC:");

        montoLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        montoLabel5.setText("Domicilio:");

        montoLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        montoLabel6.setText("Casilla de Correo:");

        txtCdC.setColumns(9);
        txtCdC.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listPagos, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${montoDonacion}"));
        columnBinding.setColumnName("Monto Donacion");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${montoAporte}"));
        columnBinding.setColumnName("Monto Aporte");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${facturado}"));
        columnBinding.setColumnName("Facturar");
        columnBinding.setColumnClass(Boolean.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(montoLabel)
                                .addComponent(montoLabel1)
                                .addComponent(fecha1Label))
                            .addGap(46, 46, 46)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNro, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(idMiembroLabel)
                            .addGap(67, 67, 67)
                            .addComponent(idMiembroLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(idMiembroLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(montoLabel2)
                                .addComponent(ctacteLabel3))
                            .addGap(44, 44, 44)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtRazonSocial)
                                .addComponent(txtDomicilio)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(rucField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 499, Short.MAX_VALUE)))))
                    .addComponent(montoLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(montoLabel6)
                        .addGap(20, 20, 20)
                        .addComponent(txtCdC, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(458, 458, 458)
                            .addComponent(imprimirButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelarButton))))
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
                    .addComponent(txtNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(montoLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dtpFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fecha1Label))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idMiembroLabel)
                    .addComponent(idMiembroLabel1)
                    .addComponent(txtCtaCte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idMiembroLabel2)
                    .addComponent(cboEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ctacteLabel3)
                    .addComponent(rucField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoLabel2)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoLabel5)
                    .addComponent(txtDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(montoLabel6)
                    .addComponent(txtCdC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelarButton)
                    .addComponent(imprimirButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.InputMethodListener, java.awt.event.KeyListener, java.awt.event.MouseListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == cancelarButton) {
                FrameFacturacionUnica.this.cancelarButtonActionPerformed(evt);
            }
            else if (evt.getSource() == imprimirButton) {
                FrameFacturacionUnica.this.imprimirButtonActionPerformed(evt);
            }
            else if (evt.getSource() == txtTimbrado) {
                FrameFacturacionUnica.this.txtTimbradoActionPerformed(evt);
            }
            else if (evt.getSource() == txtNro) {
                FrameFacturacionUnica.this.txtNroActionPerformed(evt);
            }
            else if (evt.getSource() == txtCtaCte) {
                FrameFacturacionUnica.this.txtCtaCteActionPerformed(evt);
            }
            else if (evt.getSource() == cboEntidad) {
                FrameFacturacionUnica.this.cboEntidadActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == txtTimbrado) {
                FrameFacturacionUnica.this.txtTimbradoFocusGained(evt);
            }
            else if (evt.getSource() == txtNro) {
                FrameFacturacionUnica.this.txtNroFocusGained(evt);
            }
            else if (evt.getSource() == txtCtaCte) {
                FrameFacturacionUnica.this.txtCtaCteFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
        }

        public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
        }

        public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            if (evt.getSource() == txtCtaCte) {
                FrameFacturacionUnica.this.txtCtaCteInputMethodTextChanged(evt);
            }
        }

        public void keyPressed(java.awt.event.KeyEvent evt) {
        }

        public void keyReleased(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == txtCtaCte) {
                FrameFacturacionUnica.this.txtCtaCteKeyReleased(evt);
            }
        }

        public void keyTyped(java.awt.event.KeyEvent evt) {
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == txtTimbrado) {
                FrameFacturacionUnica.this.txtTimbradoMouseClicked(evt);
            }
            else if (evt.getSource() == txtNro) {
                FrameFacturacionUnica.this.txtNroMouseClicked(evt);
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
        listTimbrados.clear();
        listTimbrados.addAll(queryTimbrados.getResultList());
        if (listTimbrados.size() > 0) {
            txtTimbrado.setText(listTimbrados.get(0).getNro().toString());
            imprimirButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Debe tener un timbrado activo para poder facturar.");
            imprimirButton.setEnabled(false);
        }

        list.clear();
        list.addAll(query.getResultList());
        if (list.size() > 0) {
            txtNro.setValue(list.get(list.size() - 1).getNro() + 1);
        } else {
            txtNro.setValue(listTimbrados.get(0).getNroFacturaIncio());
        }
        dtpFecha.setDate(new Date());
        txtCtaCte.setText("");
        cboEntidad.setSelectedItem(null);
        txtRazonSocial.setText("");
        rucField.setText("");
        txtDomicilio.setText("");
        txtCdC.setValue(null);
        txtCtaCte.requestFocus();

    }

    Boolean validar() {
        if (((Number) txtNro.getValue()).intValue() < 1) {
            txtNro.setBackground(Color.red);
            JOptionPane.showMessageDialog(null, "Numero de factura no valido!");
            return false;
        }
        if (txtRazonSocial.getText().equals("")) {
            txtRazonSocial.setBackground(Color.red);
            JOptionPane.showMessageDialog(null, "Razon Social no valido!");
            return false;
        }
        if (!CalcDV.isValidRUC(rucField.getText())) {
            rucField.setBackground(Color.red);
            JOptionPane.showMessageDialog(null, "RUC no valido!");
            return false;
        }

        /*if (list.size() > 0) {
            TblFacturas ultimaFactura = list.get(list.size() - 1);
            if (((Number) txtNro.getValue()).intValue() <= ultimaFactura.getNro()) {
                JOptionPane.showMessageDialog(null, "Numero de Factura menor a la ultima factura!");
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

            TblFacturas factura = new TblFacturas();
            entityManager.persist(factura);

            factura.setNro(((Number) txtNro.getValue()).intValue());
            factura.setIdTimbrado(listTimbrados.get(0));
            factura.setFechahora(dtpFecha.getDate());
            factura.setIdEntidad((TblEntidades) cboEntidad.getSelectedItem());
            factura.setRazonSocial(txtRazonSocial.getText());
            factura.setRuc(rucField.getText());
            factura.setDomicilio(txtDomicilio.getText());
            if (txtCdC.getValue() != null) {
                factura.setCasillaDeCorreo(((Number) txtCdC.getValue()).intValue());
            }
            Collection<TblAsientos> ts = factura.getTblAsientosCollection();
            if (ts == null) {
                ts = new LinkedList<>();
                factura.setTblAsientosCollection((List) ts);
            }
            int montoAporte = 0;
            int montoDonacion = 0;
            for (PagosRealizados pago : listPagos) {
                if (pago.getFacturado()) {
                    montoAporte += pago.getMontoAporte();
                    montoDonacion += pago.getMontoDonacion();
                    for (TblAsientosTemporales at : pago.getAsientosTemporalesList()) {
                        at.setFacturado(true);

                        TblAsientos asiento = new TblAsientos();
                        asiento.setFechahora(factura.getFechahora());
                        asiento.setIdCentroDeCosto(at.getIdCentroDeCosto());
                        asiento.setIdCuentaContableDebe(at.getIdCuentaContableDebe());
                        asiento.setIdCuentaContableHaber(at.getIdCuentaContableHaber());
                        asiento.setMonto(at.getMonto());
                        asiento.setIdUser(currentUser.getUser());

                        Collection<TblAsientosTemporales> asientosT = asiento.getTblAsientosTemporalesCollection();
                        if (asientosT == null) {
                            asientosT = new LinkedList<>();
                            asiento.setTblAsientosTemporalesCollection((List) asientosT);
                        }
                        asientosT.add(at);

                        ts.add(asiento);

                        entityManager.merge(at);
                    }

                }
            }

            factura.setImporteDonacion(montoDonacion);
            factura.setImporteAporte(montoAporte);
            factura.setAnulado(false);
            factura.setIdUser(currentUser.getUser());

            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();

            Utils.getInstance().printFactura(factura);

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
            refresh();
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

    private void txtNroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNroFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNroFocusGained

    private void txtNroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNroMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNroMouseClicked

    private void txtNroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNroActionPerformed

    private void txtCtaCteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtaCteFocusGained
        try {
            txtCtaCte.selectAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteFocusGained

    private void txtCtaCteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCtaCteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteActionPerformed

    private void txtCtaCteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCtaCteKeyReleased
        try {
            txtCtaCte.setBackground(Color.white);
            if (txtCtaCte.getText().length() > 4) {
                List<TblEntidades> list = listEntidades;
                Optional<TblEntidades> value = list.stream().filter(
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
        if (cboEntidad.getSelectedItem() != null) {
            txtCtaCte.setText(((TblEntidades) cboEntidad.getSelectedItem()).getCtacte().toString());
            rucField.setText(((TblEntidades) cboEntidad.getSelectedItem()).getRucSinDv() + "-" + CalcDV.Pa_Calcular_Dv_11_A(((TblEntidades) cboEntidad.getSelectedItem()).getRucSinDv(), 11)
            );
            loadPagos();
        } else {
            txtCtaCte.setText("");
        }
    }//GEN-LAST:event_cboEntidadActionPerformed

    private void loadPagos() {
        try {
            imprimirButton.setEnabled(false);
            listPagos.clear();
            TblEntidades selectedEntidad = (TblEntidades) cboEntidad.getSelectedItem();
            Calendar c = Calendar.getInstance();
            c.setTime(dtpFecha.getDate());
            c.set(Calendar.HOUR, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);

            Query queryT = entityManager.createQuery("SELECT distinct t FROM TblTransferencias t JOIN t.tblAsientosTemporalesCollection a WHERE t.idEntidad = :entidad AND t.fechahora <= :fecha AND a.facturado = false");
            queryT.setParameter("fecha", c.getTime());
            queryT.setParameter("entidad", selectedEntidad);
            List<TblTransferencias> listT = (List<TblTransferencias>) queryT.getResultList();
            if (listT.size() > 0) {
                imprimirButton.setEnabled(true);
                for (TblTransferencias t : listT) {
                    PagosRealizados p = new PagosRealizados();
                    Collection<TblAsientosTemporales> pagosATList = p.getAsientosTemporalesList();
                    if (pagosATList == null) {
                        pagosATList = new LinkedList<>();
                        p.setAsientosTemporalesList((List) pagosATList);
                    }
                    p.getAsientosTemporalesList().addAll(t.getTblAsientosTemporalesCollection());
                    p.setEntidad(t.getIdEntidad());
                    p.setFechahora(t.getFechahora());
                    p.setMontoAporte(t.getMontoAporte());
                    p.setMontoDonacion(t.getMontoDonacion());
                    listPagos.add(p);
                }
            }
            Query queryR = entityManager.createQuery("SELECT distinct t FROM TblRecibos t JOIN t.tblAsientosTemporalesCollection a WHERE t.idEntidad = :entidad AND t.fechahora <= :fecha AND a.facturado = false");
            queryR.setParameter("fecha", c.getTime());
            queryR.setParameter("entidad", selectedEntidad);
            List<TblRecibos> listR = (List<TblRecibos>) queryR.getResultList();
            if (listR.size() > 0) {
                imprimirButton.setEnabled(true);
                for (TblRecibos r : listR) {
                    PagosRealizados p = new PagosRealizados();
                    Collection<TblAsientosTemporales> pagosATList = p.getAsientosTemporalesList();
                    if (pagosATList == null) {
                        pagosATList = new LinkedList<>();
                        p.setAsientosTemporalesList((List) pagosATList);
                    }
                    p.getAsientosTemporalesList().addAll(r.getTblAsientosTemporalesCollection());
                    p.setEntidad(r.getIdEntidad());
                    p.setFechahora(r.getFechahora());
                    p.setMontoAporte(r.getMontoAporte());
                    p.setMontoDonacion(r.getMontoDonacion());
                    listPagos.add(p);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }

    private void txtCtaCteInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtCtaCteInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCtaCteInputMethodTextChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelarButton;
    private javax.swing.JComboBox cboEntidad;
    private javax.swing.JLabel ctacteLabel3;
    private org.jdesktop.swingx.JXDatePicker dtpFecha;
    private javax.persistence.EntityManager entityManager;
    private javax.persistence.EntityManager entityManager1;
    private javax.swing.JLabel fecha1Label;
    private javax.swing.JLabel idMiembroLabel;
    private javax.swing.JLabel idMiembroLabel1;
    private javax.swing.JLabel idMiembroLabel2;
    private javax.swing.JButton imprimirButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.util.List<com.parah.mg.domain.TblFacturas> list;
    private java.util.List listEntidades;
    private java.util.List<com.parah.mg.domain.TblGrupos> listGrupos;
    private java.util.List<com.parah.mg.domain.models.PagosRealizados> listPagos;
    private java.util.List<com.parah.mg.domain.TblTimbrados> listTimbrados;
    private javax.swing.JLabel montoLabel;
    private javax.swing.JLabel montoLabel1;
    private javax.swing.JLabel montoLabel2;
    private javax.swing.JLabel montoLabel5;
    private javax.swing.JLabel montoLabel6;
    private javax.persistence.Query query;
    private javax.persistence.Query queryEntidades;
    private javax.persistence.Query queryGrupos;
    private javax.persistence.Query queryPagos;
    private javax.persistence.Query queryTimbrados;
    private javax.swing.JTextField rucField;
    private javax.swing.JFormattedTextField txtCdC;
    private javax.swing.JTextField txtCtaCte;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JFormattedTextField txtNro;
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
            java.util.logging.Logger.getLogger(FrameFacturacionUnica.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameFacturacionUnica.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameFacturacionUnica.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameFacturacionUnica.class
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

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new FrameFacturacionUnica());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
