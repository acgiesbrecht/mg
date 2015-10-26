/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adriang
 */
@Entity
@Table(name = "PAGOS_REALIZADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagosRealizados.findAll", query = "SELECT p FROM PagosRealizados p"),
    @NamedQuery(name = "PagosRealizados.findById", query = "SELECT p FROM PagosRealizados p WHERE p.id = :id"),
    @NamedQuery(name = "PagosRealizados.findByTAporte", query = "SELECT p FROM PagosRealizados p WHERE p.tAporte = :tAporte"),
    @NamedQuery(name = "PagosRealizados.findByTDonacion", query = "SELECT p FROM PagosRealizados p WHERE p.tDonacion = :tDonacion"),
    @NamedQuery(name = "PagosRealizados.findByRAporte", query = "SELECT p FROM PagosRealizados p WHERE p.rAporte = :rAporte"),
    @NamedQuery(name = "PagosRealizados.findByRDonacion", query = "SELECT p FROM PagosRealizados p WHERE p.rDonacion = :rDonacion")})
public class PagosRealizados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private int id;
    @Column(name = "T_APORTE")
    private Integer tAporte;
    @Column(name = "T_DONACION")
    private Integer tDonacion;
    @Column(name = "R_APORTE")
    private Integer rAporte;
    @Column(name = "R_DONACION")
    private Integer rDonacion;
    @Column(name = "F_APORTE")
    private Integer fAporte;
    @Column(name = "F_DONACION")
    private Integer fDonacion;

    public PagosRealizados() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTAporte() {
        return tAporte;
    }

    public void setTAporte(Integer tAporte) {
        this.tAporte = tAporte;
    }

    public Integer getTDonacion() {
        return tDonacion;
    }

    public void setTDonacion(Integer tDonacion) {
        this.tDonacion = tDonacion;
    }

    public Integer getRAporte() {
        return rAporte;
    }

    public void setRAporte(Integer rAporte) {
        this.rAporte = rAporte;
    }

    public Integer getRDonacion() {
        return rDonacion;
    }

    public void setRDonacion(Integer rDonacion) {
        this.rDonacion = rDonacion;
    }

    /**
     * @return the fAporte
     */
    public Integer getFAporte() {
        return fAporte;
    }

    /**
     * @param fAporte the fAporte to set
     */
    public void setFAporte(Integer fAporte) {
        this.fAporte = fAporte;
    }

    /**
     * @return the fDonacion
     */
    public Integer getFDonacion() {
        return fDonacion;
    }

    /**
     * @param fDonacion the fDonacion to set
     */
    public void setFDonacion(Integer fDonacion) {
        this.fDonacion = fDonacion;
    }

}
