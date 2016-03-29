/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain.models;

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
public class PagosRealizados_bak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private int id;
    @Column(name = "T_APORTE")
    private int tAporte;
    @Column(name = "T_DONACION")
    private int tDonacion;
    @Column(name = "R_APORTE")
    private int rAporte;
    @Column(name = "R_DONACION")
    private int rDonacion;
    @Column(name = "F_APORTE")
    private int fAporte;
    @Column(name = "F_DONACION")
    private int fDonacion;

    public PagosRealizados_bak() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTAporte() {
        return tAporte;
    }

    public void setTAporte(int tAporte) {
        this.tAporte = tAporte;
    }

    public int getTDonacion() {
        return tDonacion;
    }

    public void setTDonacion(int tDonacion) {
        this.tDonacion = tDonacion;
    }

    public int getRAporte() {
        return rAporte;
    }

    public void setRAporte(int rAporte) {
        this.rAporte = rAporte;
    }

    public int getRDonacion() {
        return rDonacion;
    }

    public void setRDonacion(int rDonacion) {
        this.rDonacion = rDonacion;
    }

    public int getFAporte() {
        return fAporte;
    }

    public void setFAporte(int fAporte) {
        this.fAporte = fAporte;
    }

    public int getFDonacion() {
        return fDonacion;
    }

    public void setFDonacion(int fDonacion) {
        this.fDonacion = fDonacion;
    }

}
