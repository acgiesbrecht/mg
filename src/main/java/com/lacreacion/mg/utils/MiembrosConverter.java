/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.utils;

import com.lacreacion.mg.domain.TblMiembros;
import javax.persistence.EntityManager;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author user
 */
public class MiembrosConverter extends Converter {

    private EntityManager entityManager;

    @Override
    public Object convertForward(Object value) {
        return ((TblMiembros) value).getNombre();
    }

    @Override
    public TblMiembros convertReverse(Object value) {
        TblMiembros res = new TblMiembros();
        try {
            System.out.println(value);
            res = entityManager.find(TblMiembros.class, value);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * @param entityManager the em to set
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
