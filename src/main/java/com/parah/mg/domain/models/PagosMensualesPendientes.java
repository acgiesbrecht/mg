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

    private Integer mes;

    private Integer ano;

    private Integer monto;

    public PagosMensualesPendientes() {
    }

    /**
     * @return the mes
     */
    public Integer getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(Integer mes) {
        this.mes = mes;
    }

    /**
     * @return the eno
     */
    public Integer getAno() {
        return ano;
    }

    /**
     * @param eno the eno to set
     */
    public void setAno(Integer ano) {
        this.ano = ano;
    }

    /**
     * @return the monto
     */
    public Integer getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(Integer monto) {
        this.monto = monto;
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

}
