/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.utils;

import java.util.Locale;
import javax.swing.JOptionPane;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Industria
 */
public abstract class NumeroALetras {

    private static final Logger LOGGER = LogManager.getLogger(NumeroALetras.class);
    public static String convertNumberToLetter(String number)
            throws NumberFormatException {
        NumberFormat formatter = new RuleBasedNumberFormat(Locale.forLanguageTag("es"), RuleBasedNumberFormat.SPELLOUT);
        return formatter.format(number).replaceAll("-", "");
    }

    public static String convertNumberToLetter(Long number){
        try {
            NumberFormat formatter = new RuleBasedNumberFormat(new Locale("es"), RuleBasedNumberFormat.SPELLOUT);
            String res = formatter.format(number);
            return res.replaceAll("­", "");
        } catch (Exception exx) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + exx.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), exx);
            return "error";
        }
    }
    
    public static String convertNumberToLetter(Integer number){
        try {
            NumberFormat formatter = new RuleBasedNumberFormat(new Locale("es"), RuleBasedNumberFormat.SPELLOUT);
            String res = formatter.format(number);
            return res.replaceAll("­", "");
        } catch (Exception exx) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + exx.getMessage());
            LOGGER.error(Thread.currentThread().getStackTrace()[1].getMethodName(), exx);
            return "error";
        }
    }

}
