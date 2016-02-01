/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.utils;

import com.parah.mg.domain.TblEntidades;
import com.parah.mg.domain.TblEventoCuotas;
import com.parah.mg.domain.TblFacturas;
import com.parah.mg.domain.models.CuotaModel;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class Utils extends Component {

    private static final Utils utils = new Utils();
    private static final Logger LOGGER = LogManager.getLogger(Utils.class);

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private Utils() {
    }

    /* Static 'instance' method */
    public static Utils getInstance() {
        return utils;
    }

    public List<CuotaModel> getCuotas(TblEventoCuotas eventoCuotas, Integer monto) {
        List<Date> fechas = getCuotasFechas(eventoCuotas);
        List<CuotaModel> listCuotas = new ArrayList<>();
        float divi = monto * 1.0F / fechas.size();
        Integer montoCuota = Math.round(divi);
        for (Date fecha : fechas) {
            CuotaModel cuota = new CuotaModel();
            cuota.setFecha(fecha);
            cuota.setMonto(montoCuota);
            listCuotas.add(cuota);
        }
        if (montoCuota * fechas.size() > monto) {
            CuotaModel cuota = new CuotaModel();
            cuota.setFecha(listCuotas.get(listCuotas.size() - 1).getFecha());
            cuota.setMonto(montoCuota - 1);
            listCuotas.set(listCuotas.size() - 1, cuota);
        } else if (montoCuota * fechas.size() < monto) {
            CuotaModel cuota = new CuotaModel();
            cuota.setFecha(listCuotas.get(listCuotas.size() - 1).getFecha());
            cuota.setMonto(montoCuota + 1);
            listCuotas.set(listCuotas.size() - 1, cuota);
        }

        return listCuotas;
    }

    public List<Date> getCuotasFechas(TblEventoCuotas cuotas) {
        List<Date> cuotasList = new ArrayList<>();
        if (cuotas.getFecha1() != null) {
            cuotasList.add(cuotas.getFecha1());
        }
        if (cuotas.getFecha2() != null) {
            cuotasList.add(cuotas.getFecha2());
        }
        if (cuotas.getFecha3() != null) {
            cuotasList.add(cuotas.getFecha3());
        }
        if (cuotas.getFecha4() != null) {
            cuotasList.add(cuotas.getFecha4());
        }
        return cuotasList;
    }

    public Map<String, String> getPersistenceMap() {
        try {
            Properties p = System.getProperties();
            p.setProperty("derby.system.home", Preferences.userRoot().node("MG").get("Datadir", (new JFileChooser()).getFileSystemView().getDefaultDirectory().toString() + "\\javadb"));
            p.setProperty("derby.drda.host", "0.0.0.0");
            String databaseIP;
            databaseIP = Preferences.userRoot().node("MG").get("DatabaseIP", "127.0.0.1");
            Map<String, String> persistenceMap = new HashMap<>();
            persistenceMap.put("javax.persistence.jdbc.url", "jdbc:derby://" + databaseIP + ":1527/mgdb;create=true");
            persistenceMap.put("javax.persistence.jdbc.user", "mg");
            persistenceMap.put("javax.persistence.jdbc.password", "123456");
            persistenceMap.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.ClientDriver");
            return persistenceMap;
        } catch (Exception exx) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + exx.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), exx);
            return null;
        }
    }

    public void printFactura(TblFacturas factura) {
        try {

            Map parameters = new HashMap();
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

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            jasperPrint.setLeftMargin(Integer.getInteger(Preferences.userRoot().node("MG").get("facturaLeftMargin", "0")));
            jasperPrint.setTopMargin(Integer.getInteger(Preferences.userRoot().node("MG").get("facturaTopMargin", "0")));

            //JasperViewer jReportsViewer = new JasperViewer(jasperPrint, false);
            //jReportsViewer.setVisible(true);
            jasperPrint.setLeftMargin(Integer.getInteger(Preferences.userRoot().node("MG").get("facturaLeftMargin", "0")));
            jasperPrint.setTopMargin(Integer.getInteger(Preferences.userRoot().node("MG").get("facturaTopMargin", "0")));
            JasperPrintManager.printReport(jasperPrint, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
    }

    public int getIndexOfModel(ListModel model, Object value) {
        if (value == null) {
            return -1;
        }
        if (model instanceof DefaultListModel) {
            return ((DefaultListModel) model).indexOf(value);
        }
        for (int i = 0; i < model.getSize(); i++) {
            if (value.equals(model.getElementAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public String getNombreCompleto(TblEntidades ent) {
        return ent.getNombres() + " " + ent.getApellidos();
    }

}
