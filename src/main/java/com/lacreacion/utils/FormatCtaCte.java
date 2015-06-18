package com.lacreacion.utils;

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

    public static String format(int ctaCte) {
        String sCta = String.valueOf(ctaCte);
        if (sCta.length() == 5 || sCta.length() == 7) {
            sCta = "0" + sCta;
        }

        if (sCta.length() == 8) {
            sCta = sCta.substring(0, 2) + "-" + sCta.substring(2, 5) + "-" + sCta.substring(5, 6) + "/" + sCta.substring(6, 8);
        }
        if (sCta.length() == 6) {
            sCta = sCta.substring(0, 2) + "-" + sCta.substring(2, 5) + "-" + sCta.substring(5, 6);
        }
        return sCta;
    }

    public static void main(String[] args) {
        System.out.print(format(73717));
    }
}
