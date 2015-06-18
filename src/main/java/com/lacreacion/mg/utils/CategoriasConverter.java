/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.utils;

import com.lacreacion.mg.domain.TblRematesCategorias;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author user
 */
public class CategoriasConverter extends Converter {

    private EntityManager entityManager;

    @Override
    public Object convertForward(Object value) {
        return ((TblRematesCategorias) value).getDescripcion();
    }

    @Override
    public TblRematesCategorias convertReverse(Object value) {
        TblRematesCategorias res = new TblRematesCategorias();
        try {
            System.out.println(value);
            //res = entityManager.find(TblRematesCategorias.class, value);
            if (value.toString().length() > 0) {
                res = (TblRematesCategorias) entityManager.createQuery("SELECT t FROM TblRematesCategorias t WHERE t.descripcion = '" + value + "'").getSingleResult();
            }
        } catch (NoResultException ex) {
            res = null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
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
