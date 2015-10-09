/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author adriang
 */
@Embeddable
public class TblFacturasPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "NRO")
    private int nro;
    @Basic(optional = false)
    @Column(name = "ID_TIMBRADO")
    private int idTimbrado;

    public TblFacturasPK() {
    }

    public TblFacturasPK(int nro, int idTimbrado) {
        this.nro = nro;
        this.idTimbrado = idTimbrado;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public int getIdTimbrado() {
        return idTimbrado;
    }

    public void setIdTimbrado(int idTimbrado) {
        this.idTimbrado = idTimbrado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) nro;
        hash += (int) idTimbrado;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblFacturasPK)) {
            return false;
        }
        TblFacturasPK other = (TblFacturasPK) object;
        if (this.nro != other.nro) {
            return false;
        }
        if (this.idTimbrado != other.idTimbrado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblFacturasPK[ nro=" + nro + ", idTimbrado=" + idTimbrado + " ]";
    }

}
