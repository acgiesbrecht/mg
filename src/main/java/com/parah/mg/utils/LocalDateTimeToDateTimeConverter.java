/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author user
 */
public class LocalDateTimeToDateTimeConverter extends Converter {

    @Override
    public Object convertForward(Object value) {
        return LocalDateTime.ofInstant(((Date) value).toInstant(), ZoneId.systemDefault());
    }

    @Override
    public Date convertReverse(Object value) {

        try {
            return Date.from(((LocalDateTime) value).atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }
}
