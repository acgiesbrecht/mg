/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.utils;

import com.lacreacion.mg.domain.TblContribuyentes;
import java.net.URL;
import java.util.Scanner;
import java.util.zip.ZipInputStream;
import javax.persistence.EntityManager;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author adriang
 */
public class GetRucDatabase {

    public static void updateRucDatabase(EntityManager em) {
        try {
            em.createQuery("delete from TblContribuyentes t").executeUpdate();
            for (int i = 0; i <= 9; i++) {
                URL url = new URL("http://www.set.gov.py/rest/contents/download/collaboration/sites/PARAGUAY-SET/documents/informes-periodicos/ruc/ruc" + String.valueOf(i) + ".zip");
                ZipInputStream zipStream = new ZipInputStream(url.openStream());
                zipStream.getNextEntry();

                Scanner sc = new Scanner(zipStream);
                while (sc.hasNextLine()) {
                    String[] ruc = sc.nextLine().split("\\|");
                    TblContribuyentes c = new TblContribuyentes();
                    em.persist(c);
                    c.setRuc(ruc[0]);
                    c.setRazonSocial(StringEscapeUtils.escapeSql(ruc[1]));
                    c.setDv(ruc[2]);
                }
            }
            em.getTransaction().commit();
            em.getTransaction().begin();
        } catch (Exception ex) {

        }

    }
}
