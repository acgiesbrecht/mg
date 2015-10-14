/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.utils;

import com.lacreacion.utils.CalcDV;

/**
 *
 * @author user
 */
public class RucTableCellRenderer extends NormalTableCellRenderer {

    @Override
    public void setValue(Object value) {
        setText((value == null) ? "" : String.valueOf(value) + "-" + String.valueOf(CalcDV.Pa_Calcular_Dv_11_A(String.valueOf(value), 11)));
    }
}
