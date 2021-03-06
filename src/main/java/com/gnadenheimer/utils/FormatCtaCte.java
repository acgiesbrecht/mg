package com.gnadenheimer.utils;

import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Industria
 */
public class FormatCtaCte {

    private static final Logger LOGGER = LogManager.getLogger(FormatCtaCte.class);

    public static String format(Integer ctaCte) {
        try {
            String sCta = String.valueOf(ctaCte);
            if (sCta.length() == 6 || sCta.length() == 8) {
                sCta = new StringBuilder("0").append(sCta).toString();
            }

            if (sCta.length() == 9) {
                sCta = new StringBuilder(sCta.substring(0, 2)).append("-").append(sCta.substring(2, 6)).append("-").append(sCta.substring(6, 7)).append("/").append(sCta.substring(7, 9)).toString();
            }
            if (sCta.length() == 7) {
                sCta = new StringBuilder(sCta.substring(0, 2)).append("-").append(sCta.substring(2, 6)).append("-").append(sCta.substring(6, 7)).toString();
            }
            return sCta;
        } catch (Exception exx) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + exx.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), exx);
            return "error";
        }
    }

    public static void main(String[] args) {

    }
}
