/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain.models;

import com.parah.mg.domain.TblEntidades;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author adriang
 */
@Entity
public class PagosMensualesPendientes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private TblEntidades entidad;

    private Object mes;

    private Object ano;

    private Long montoAporte;

    private Long montoDonacion;

    private Boolean cobrado;

    public PagosMensualesPendientes() {
    }

    public PagosMensualesPendientes(TblEntidades entidad, Object mes, Object ano, Long montoAporte, Long montoDonacion) {
        this.entidad = entidad;
        this.mes = mes;
        this.ano = ano;
        this.montoAporte = montoAporte;
        this.montoDonacion = montoDonacion;
    }

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
     * @return the mes
     */
    public Object getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(Object mes) {
        this.mes = mes;
    }

    /**
     * @return the ano
     */
    public Object getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(Object ano) {
        this.ano = ano;
    }

    /**
     * @return the cobrado
     */
    public Boolean getCobrado() {
        return cobrado;
    }

    /**
     * @param cobrado the cobrado to set
     */
    public void setCobrado(Boolean cobrado) {
        this.cobrado = cobrado;
    }

    /**
     * @return the montoAporte
     */
    public Long getMontoAporte() {
        return montoAporte;
    }

    /**
     * @param montoAporte the montoAporte to set
     */
    public void setMontoAporte(Long montoAporte) {
        this.montoAporte = montoAporte;
    }

    /**
     * @return the montoDonacion
     */
    public Long getMontoDonacion() {
        return montoDonacion;
    }

    /**
     * @param montoDonacion the montoDonacion to set
     */
    public void setMontoDonacion(Long montoDonacion) {
        this.montoDonacion = montoDonacion;
    }

}
