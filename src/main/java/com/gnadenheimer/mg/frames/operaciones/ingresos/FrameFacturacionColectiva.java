/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.frames.operaciones.ingresos;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.gnadenheimer.mg.domain.TblAsientos;
import com.gnadenheimer.mg.domain.TblAsientosTemporales;
import com.gnadenheimer.mg.domain.TblFacturas;
import com.gnadenheimer.mg.domain.TblRecibos;
import com.gnadenheimer.mg.domain.TblTransferencias;
import com.gnadenheimer.mg.domain.miembros.TblEntidades;
import com.gnadenheimer.mg.domain.models.PagosRealizados;
import com.gnadenheimer.mg.utils.CurrentUser;
import com.gnadenheimer.mg.utils.Utils;
import com.gnadenheimer.utils.CalcDV;
import java.awt.EventQueue;
import java.beans.Beans;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public class FrameFacturacionColectiva extends JInternalFrame {

    private static final Logger LOGGER = LogManager.getLogger(FrameFacturacionColectiva.class);
    CurrentUser currentUser = CurrentUser.getInstance();
    String databaseIP;
    Map<String, String> persistenceMap = new HashMap<>();
    Integer siguienteFacturaNro;
    DatePickerSettings datePickerSettings = new DatePickerSettings(Locale.getDefault());
    DatePickerSettings datePickerSettings1 = new DatePickerSettings(Locale.getDefault());

    public FrameFacturacionColectiva() {
        super("Facturacion Unica",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        try {

            persistenceMap = Utils.getInstance().getPersistenceMap();
            datePickerSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
            datePickerSettings1.setFormatForDatesCommonEra("dd/MM/yyyy");

            initComponents();
            if (!Beans.isDesignTime()) {
                entityManager.getTransaction().begin();
            }
            if (listTimbrados.size() < 1) {
                JOptionPane.showMessageDialog(null, "Debe tener un timbrado activo para poder facturar.");
                return;
            }
            if (masterTable.getRowCount() > 0) {
                imprimirButton.setEnabled(true);
            }

            dtpFechaDesde.setDate(LocalDate.now().withDayOfYear(1));

            dtpFechaHasta.setDate(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()));

            list.clear();
            list.addAll(query.getResultList());
            if (list.size() > 0) {
                siguienteFacturaNro = list.get(list.size() - 1).getNro() + 1;
                if (siguienteFacturaNro < listTimbrados.get(0).getNroFacturaIncio()) {
                    siguienteFacturaNro = listTimbrados.get(0).getNroFacturaIncio();
                }
                if (siguienteFacturaNro > listTimbrados.get(0).getNroFacturaFin()) {
                    JOptionPane.showMessageDialog(null, "Ha alcanzado el nro maximo de facturas para el timbrado activo.");
                    return;
                }
            } else {
                siguienteFacturaNro = listTimbrados.get(0).getNroFacturaIncio();
            }
            list.clear();

            TableFilterHeader filterHeader = new TableFilterHeader(masterTable, AutoChoices.DISABLED);
            filterHeader.setAdaptiveChoices(false);
            filterHeader.getParserModel().setIgnoreCase(true);
            filterHeader.setPosition(TableFilterHeader.Position.TOP);

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
        queryEntidades = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblEntidades t ORDER BY t.ctacte");
        listEntidades = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryEntidades.getResultList());
        queryTimbrados = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT t FROM TblTimbrados t WHERE t.activo = true");
        listTimbrados = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : org.jdesktop.observablecollections.ObservableCollections.observableList(queryTimbrados.getResultList());
        rucTableCellRenderer1 = new com.gnadenheimer.mg.utils.RucTableCellRenderer();
        numberCellRenderer1 = new com.gnadenheimer.mg.utils.NumberCellRenderer();
        facturaNroTableCellRenderer1 = new com.gnadenheimer.mg.utils.FacturaNroTableCellRenderer();
        dateTimeTableCellRenderer1 = new com.gnadenheimer.mg.utils.DateTimeTableCellRenderer();
        cancelarButton = new javax.swing.JButton();
        imprimirButton = new javax.swing.JButton();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cmdCalcular = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        dtpFechaDesde = new DatePicker(datePickerSettings);
        dtpFechaHasta = new DatePicker(datePickerSettings1);

        FormListener formListener = new FormListener();

        rucTableCellRenderer1.setText("rucTableCellRenderer1");

        numberCellRenderer1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numberCellRenderer1.setText("numberCellRenderer1");

        facturaNroTableCellRenderer1.setText("facturaNroTableCellRenderer1");

        dateTimeTableCellRenderer1.setText("dateTimeTableCellRenderer1");

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(formListener);

        imprimirButton.setText("Guardar & Imprimir");
        imprimirButton.addActionListener(formListener);

        masterTable.setAutoCreateRowSorter(true);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nro}"));
        columnBinding.setColumnName("Nro");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechahora}"));
        columnBinding.setColumnName("Fecha/Hora");
        columnBinding.setColumnClass(java.time.LocalDateTime.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idMiembro}"));
        columnBinding.setColumnName("Miembro");
        columnBinding.setColumnClass(com.gnadenheimer.mg.domain.miembros.TblEntidades.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${razonSocial}"));
        columnBinding.setColumnName("Razon Social");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ruc}"));
        columnBinding.setColumnName("RUC");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${importeAporte}"));
        columnBinding.setColumnName("Importe Aporte");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${importeDonacion}"));
        columnBinding.setColumnName("Importe Donacion");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idTimbrado}"));
        columnBinding.setColumnName("Timbrado");
        columnBinding.setColumnClass(com.gnadenheimer.mg.domain.TblTimbrados.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        masterScrollPane.setViewportView(masterTable);
        if (masterTable.getColumnModel().getColumnCount() > 0) {
            masterTable.getColumnModel().getColumn(0).setCellRenderer(facturaNroTableCellRenderer1);
            masterTable.getColumnModel().getColumn(1).setCellRenderer(dateTimeTableCellRenderer1);
            masterTable.getColumnModel().getColumn(5).setCellRenderer(numberCellRenderer1);
            masterTable.getColumnModel().getColumn(6).setCellRenderer(numberCellRenderer1);
        }

        jLabel1.setText("Fecha:  Desde:");

        cmdCalcular.setText("Calcular");
        cmdCalcular.addActionListener(formListener);

        jLabel2.setText("Hasta (Fecha de Facturacion):");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(imprimirButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelarButton))
                    .addComponent(masterScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dtpFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtpFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmdCalcular)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelarButton, imprimirButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addComponent(dtpFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtpFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdCalcular))
                .addGap(10, 10, 10)
                .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelarButton)
                    .addComponent(imprimirButton))
                .addContainerGap())
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == cancelarButton) {
                FrameFacturacionColectiva.this.cancelarButtonActionPerformed(evt);
            }
            else if (evt.getSource() == imprimirButton) {
                FrameFacturacionColectiva.this.imprimirButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cmdCalcular) {
                FrameFacturacionColectiva.this.cmdCalcularActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("unchecked")
    private void imprimirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirButtonActionPerformed
        try {

            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();

            list.stream().forEach((factura) -> {

                Utils.getInstance().printFactura(factura);

            });
            java.util.List data = query.getResultList();
            data.stream().forEach((entity) -> {
                entityManager.refresh(entity);
            });
            list.clear();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_imprimirButtonActionPerformed

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarButtonActionPerformed
        try {
            this.setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }//GEN-LAST:event_cancelarButtonActionPerformed

    private void cmdCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCalcularActionPerformed
//        Utils.getInstance().exectueBackUp(Utils.getInstance().getPersistenceMap().get("backUpDir"));
        generate();
        if (masterTable.getRowCount() > 0) {
            imprimirButton.setEnabled(true);
        }
    }//GEN-LAST:event_cmdCalcularActionPerformed

    private void generate() {
        try {

            List<PagosRealizados> pagosList = new ArrayList<>();

            Query queryE = entityManager.createQuery("SELECT distinct e FROM TblEntidades e JOIN e.tblTransferenciasList t WHERE e.rucSinDv != 44444401 AND t.fechahora >= :fechaDesde AND t.fechahora <= :fechaHasta");
            queryE.setParameter("fechaDesde", dtpFechaDesde.getDate());
            queryE.setParameter("fechaHasta", dtpFechaHasta.getDate());
            List<TblEntidades> listE = (List<TblEntidades>) queryE.getResultList();

            Integer facturaNroInicial = siguienteFacturaNro;

            for (TblEntidades e : listE) {
                if (siguienteFacturaNro <= listTimbrados.get(0).getNroFacturaFin()) {
                    Query queryT = entityManager.createQuery("SELECT distinct t FROM TblTransferencias t JOIN t.tblAsientosTemporalesList a WHERE t.idEntidad = :entidad AND t.fechahora >= :fechaDesde AND t.fechahora <= :fechaHasta AND a.facturado = false");
                    queryT.setParameter("fechaDesde", dtpFechaDesde.getDate());
                    queryT.setParameter("fechaHasta", dtpFechaHasta.getDate());
                    queryT.setParameter("entidad", e);
                    List<TblTransferencias> listT = (List<TblTransferencias>) queryT.getResultList();
                    if (listT.size() > 0) {
                        PagosRealizados p = new PagosRealizados();
                        p.setEntidad(e);
                        Integer montoAporte = 0;
                        Integer montoDonacion = 0;
                        List<TblAsientosTemporales> ts = p.getAsientosTemporalesList();
                        if (ts == null) {
                            ts = new LinkedList<>();
                            p.setAsientosTemporalesList((List) ts);
                        }
                        for (TblTransferencias t : listT) {

                            ts.addAll(t.getTblAsientosTemporalesList());
                            for (TblAsientosTemporales at : t.getTblAsientosTemporalesList()) {
                                if (at.getEsAporte()) {
                                    montoAporte += at.getMonto();
                                } else {
                                    montoDonacion += at.getMonto();
                                }
                                at.setFacturado(true);
                                entityManager.merge(at);
                            }
                        }
                        p.setMontoAporte(montoAporte);
                        p.setMontoDonacion(montoDonacion);
                        pagosList.add(p);
                        siguienteFacturaNro++;
                    }

                }
            }

            queryE = entityManager.createQuery("SELECT distinct e FROM TblEntidades e JOIN e.tblRecibosList t WHERE t.fechahora >= :fechaDesde AND t.fechahora <= :fechaHasta");
            queryE.setParameter("fechaDesde", dtpFechaDesde.getDate());
            queryE.setParameter("fechaHasta", dtpFechaHasta.getDate());
            listE = (List<TblEntidades>) queryE.getResultList();

            for (TblEntidades e : listE) {
                if (siguienteFacturaNro <= listTimbrados.get(0).getNroFacturaFin()) {
                    Query queryRecibos = entityManager.createQuery("SELECT distinct t FROM TblRecibos t JOIN t.tblAsientosTemporalesList a WHERE t.idEntidad = :entidad AND t.fechahora >= :fechaDesde AND t.fechahora <= :fechaHasta AND a.facturado = false");
                    queryRecibos.setParameter("fechaDesde", dtpFechaDesde.getDate());
                    queryRecibos.setParameter("fechaHasta", dtpFechaHasta.getDate());
                    queryRecibos.setParameter("entidad", e);
                    List<TblRecibos> listR = (List<TblRecibos>) queryRecibos.getResultList();
                    if (listR.size() > 0) {
                        PagosRealizados p = new PagosRealizados();
                        p.setEntidad(e);
                        Integer montoAporte = 0;
                        Integer montoDonacion = 0;
                        List<TblAsientosTemporales> ts = p.getAsientosTemporalesList();
                        if (ts == null) {
                            ts = new LinkedList<>();
                            p.setAsientosTemporalesList((List) ts);
                        }
                        for (TblRecibos r : listR) {
                            ts.addAll(r.getTblAsientosTemporalesList());
                            for (TblAsientosTemporales at : r.getTblAsientosTemporalesList()) {
                                if (at.getEsAporte()) {
                                    montoAporte += at.getMonto();
                                } else {
                                    montoDonacion += at.getMonto();
                                }
                                at.setFacturado(true);
                                entityManager.merge(at);
                            }
                        }
                        p.setMontoAporte(montoAporte);
                        p.setMontoDonacion(montoDonacion);
                        pagosList.add(p);
                        siguienteFacturaNro++;
                    }
                }
            }

            //FACTURA DE IMPORTES CONSOLIDADOS
            queryE = entityManager.createQuery("SELECT distinct e FROM TblEntidades e JOIN e.tblTransferenciasList t WHERE e.rucSinDv = 44444401 AND t.fechahora >= :fechaDesde AND t.fechahora <= :fechaHasta");
            queryE.setParameter("fechaDesde", dtpFechaDesde.getDate());
            queryE.setParameter("fechaHasta", dtpFechaHasta.getDate());
            listE = (List<TblEntidades>) queryE.getResultList();

            if (listE.size() > 0) {
                PagosRealizados p = new PagosRealizados();
                TblEntidades en = (TblEntidades) entityManager.createQuery("SELECT e FROM TblEntidades e WHERE e.nombres = 'Clientes Varios'").getSingleResult();
                if (en == null) {
                    JOptionPane.showMessageDialog(null, "No existe una Entidad con Nombre Clientes Varios para facturar los importes consolidados.");
                }
                en.setRazonSocial("Importes Consolidados");
                en.setRucSinDv("44444401");
                p.setEntidad(en);
                Integer montoAporte = 0;
                Integer montoDonacion = 0;

                for (TblEntidades e : listE) {
                    if (siguienteFacturaNro <= listTimbrados.get(0).getNroFacturaFin()) {
                        Query queryT = entityManager.createQuery("SELECT distinct t FROM TblTransferencias t JOIN t.tblAsientosTemporalesList a WHERE t.idEntidad = :entidad AND t.fechahora <= :fechaDesde AND t.fechahora <= :fechaHasta AND a.facturado = false");
                        queryT.setParameter("fechaDesde", dtpFechaDesde.getDate());
                        queryT.setParameter("fechaHasta", dtpFechaHasta.getDate());
                        queryT.setParameter("entidad", e);
                        List<TblTransferencias> listT = (List<TblTransferencias>) queryT.getResultList();
                        if (listT.size() > 0) {
                            List<TblAsientosTemporales> ts = p.getAsientosTemporalesList();
                            if (ts == null) {
                                ts = new LinkedList<>();
                                p.setAsientosTemporalesList((List) ts);
                            }
                            for (TblTransferencias t : listT) {

                                ts.addAll(t.getTblAsientosTemporalesList());
                                for (TblAsientosTemporales at : t.getTblAsientosTemporalesList()) {
                                    if (at.getEsAporte()) {
                                        montoAporte += at.getMonto();
                                    } else {
                                        montoDonacion += at.getMonto();
                                    }
                                    at.setFacturado(true);
                                    entityManager.merge(at);
                                }
                            }
                        }

                    }
                }
                p.setMontoAporte(montoAporte);
                p.setMontoDonacion(montoDonacion);
                pagosList.add(p);
                siguienteFacturaNro++;
            }

            siguienteFacturaNro = facturaNroInicial;

            list.clear();
            for (PagosRealizados pago : pagosList) {
                if (siguienteFacturaNro <= listTimbrados.get(0).getNroFacturaFin()) {
                    TblFacturas f = new TblFacturas();
                    entityManager.persist(f);
                    f.setNro(siguienteFacturaNro);
                    f.setIdTimbrado(listTimbrados.get(0));
                    f.setFechahora(dtpFechaHasta.getDate().atStartOfDay());
                    f.setIdEntidad(pago.getEntidad());
                    if (pago.getEntidad().getRazonSocial() != null) {
                        if (!pago.getEntidad().getRazonSocial().equals("")) {
                            f.setRazonSocial(pago.getEntidad().getRazonSocial());
                        } else {
                            f.setRazonSocial(pago.getEntidad().getNombreCompleto());
                        }
                    } else {
                        f.setRazonSocial(pago.getEntidad().getNombreCompleto());
                    }
                    if (pago.getEntidad().getRucSinDv() != null) {
                        f.setRuc(CalcDV.getRucEntero(pago.getEntidad().getRucSinDv()));
                    } else {
                        f.setRuc(CalcDV.getRucEntero("44444401"));
                    }

                    f.setAnulado(false);
                    f.setDomicilio(pago.getEntidad().getDomicilio());
                    f.setCasillaDeCorreo(pago.getEntidad().getBox());

                    f.setImporteAporte(pago.getMontoAporte());
                    f.setImporteDonacion(pago.getMontoDonacion());
                    f.setIdUser(currentUser.getUser());

                    List<TblAsientos> ts = f.getTblAsientosList();
                    if (ts == null) {
                        ts = new LinkedList<>();
                        f.setTblAsientosList((List) ts);
                    }
                    for (TblAsientosTemporales aT : pago.getAsientosTemporalesList()) {
                        TblAsientos asiento = new TblAsientos();
                        asiento.setFechahora(f.getFechahora());
                        asiento.setIdCentroDeCostoDebe(aT.getIdCentroDeCostoDebe());
                        asiento.setIdCentroDeCostoHaber(aT.getIdCentroDeCostoHaber());
                        asiento.setIdCuentaContableDebe(aT.getIdCuentaContableDebe());
                        asiento.setIdCuentaContableHaber(aT.getIdCuentaContableHaber());
                        asiento.setMonto(aT.getMonto());
                        asiento.setIdUser(currentUser.getUser());

                        List<TblAsientosTemporales> asientosT = asiento.getTblAsientosTemporalesList();
                        if (asientosT == null) {
                            asientosT = new LinkedList<>();
                            asiento.setTblAsientosTemporalesList((List) asientosT);
                        }
                        asientosT.add(aT);

                        ts.add(asiento);
                    }
                    list.add(f);
                    Integer row = list.size() - 1;

                    masterTable.setRowSelectionInterval(row, row);

                    masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));
                    siguienteFacturaNro++;
                }
            }

        } catch (Exception ex) {
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelarButton;
    private javax.swing.JButton cmdCalcular;
    private com.gnadenheimer.mg.utils.DateTimeTableCellRenderer dateTimeTableCellRenderer1;
    private com.github.lgooddatepicker.components.DatePicker dtpFechaDesde;
    private com.github.lgooddatepicker.components.DatePicker dtpFechaHasta;
    private javax.persistence.EntityManager entityManager;
    private com.gnadenheimer.mg.utils.FacturaNroTableCellRenderer facturaNroTableCellRenderer1;
    private javax.swing.JButton imprimirButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private java.util.List<com.gnadenheimer.mg.domain.TblFacturas> list;
    private java.util.List<com.gnadenheimer.mg.domain.miembros.TblEntidades> listEntidades;
    private java.util.List<com.gnadenheimer.mg.domain.TblTimbrados> listTimbrados;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private com.gnadenheimer.mg.utils.NumberCellRenderer numberCellRenderer1;
    private javax.persistence.Query query;
    private javax.persistence.Query queryEntidades;
    private javax.persistence.Query queryTimbrados;
    private com.gnadenheimer.mg.utils.RucTableCellRenderer rucTableCellRenderer1;
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
            java.util.logging.Logger.getLogger(FrameFacturacionColectiva.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameFacturacionColectiva.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameFacturacionColectiva.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameFacturacionColectiva.class
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
                frame.setContentPane(new FrameFacturacionColectiva());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
