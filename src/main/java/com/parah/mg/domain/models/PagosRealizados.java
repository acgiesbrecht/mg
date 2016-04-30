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
import java.util.Date;
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
    private Date fechahora;
    private Collection<TblAsientosTemporales> asientosTemporalesList;
    private Integer montoAporte;
    private Integer montoDonacion;
    private Boolean facturado = false;

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

    /**
     * @return the montoAporte
     */
    public Integer getMontoAporte() {
        return montoAporte;
    }

    /**
     * @param montoAporte the montoAporte to set
     */
    public void setMontoAporte(Integer montoAporte) {
        this.montoAporte = montoAporte;
    }

    /**
     * @return the montoDonacion
     */
    public Integer getMontoDonacion() {
        return montoDonacion;
    }

    /**
     * @param montoDonacion the montoDonacion to set
     */
    public void setMontoDonacion(Integer montoDonacion) {
        this.montoDonacion = montoDonacion;
    }

    /**
     * @return the fechahora
     */
    public Date getFechahora() {
        return fechahora;
    }

    /**
     * @param fechahora the fechahora to set
     */
    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    /**
     * @return the facturado
     */
    public Boolean getFacturado() {
        return facturado;
    }

    /**
     * @param facturado the facturado to set
     */
    public void setFacturado(Boolean facturado) {
        this.facturado = facturado;
    }

}
