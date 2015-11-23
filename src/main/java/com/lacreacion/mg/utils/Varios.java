/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.utils;

import com.lacreacion.mg.domain.TblEntidades;
import com.lacreacion.mg.domain.TblEventoCuotas;
import com.lacreacion.mg.domain.models.CuotaModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author user
 */
public class Varios {

    public static List<CuotaModel> getCuotas(TblEventoCuotas eventoCuotas, Integer monto) {
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

    public static List<Date> getCuotasFechas(TblEventoCuotas cuotas) {
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

    public static Map<String, String> getDatabaseIP() {
        Properties p = System.getProperties();
        p.setProperty("derby.system.home", Preferences.userRoot().node("MG").get("Datadir", "C:\\javadb"));
        p.setProperty("derby.drda.host", "0.0.0.0");
        String databaseIP;
        databaseIP = Preferences.userRoot().node("MG").get("DatabaseIP", "127.0.0.1");
        Map<String, String> persistenceMap = new HashMap<>();
        persistenceMap.put("javax.persistence.jdbc.url", "jdbc:derby://" + databaseIP + ":1527/mgdb");
        persistenceMap.put("javax.persistence.jdbc.user", "mg");
        persistenceMap.put("javax.persistence.jdbc.password", "123456");
        persistenceMap.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.ClientDriver");
        return persistenceMap;
    }

    public static int getIndexOfModel(ListModel model, Object value) {
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

    public static String getNombreCompleto(TblEntidades ent) {
        return ent.getNombres() + " " + ent.getApellidos();
    }

}
