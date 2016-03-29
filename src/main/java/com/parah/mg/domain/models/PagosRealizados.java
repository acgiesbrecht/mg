/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain.models;

import com.parah.mg.domain.TblAsientosTemporales;
import com.parah.mg.domain.miembros.TblEntidades;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author adriang
 */
@Entity
public class PagosRealizados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private TblEntidades entidad;

    private Collection<TblAsientosTemporales> asientosTemporalesList;

    /**
     * @return the entidad
     */
    public TblEntidades getEntidad() {
        return entidad;
    }

    /**
     * @param entidad the entidad to set
     */
    public void setEntidad(TblEntidades entidad) {
        this.entidad = entidad;
    }

    /**
     * @return the asientosTemporalesList
     */
    public Collection<TblAsientosTemporales> getAsientosTemporalesList() {
        return asientosTemporalesList;
    }

    /**
     * @param asientosTemporalesList the asientosTemporalesList to set
     */
    public void setAsientosTemporalesList(Collection<TblAsientosTemporales> asientosTemporalesList) {
        this.asientosTemporalesList = asientosTemporalesList;
    }

}
