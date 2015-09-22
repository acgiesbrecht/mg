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
 * @author Adrian Giesbrecht
 */
@Embeddable
public class TblFacturasPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "NRO")
    private int nro;
    @Basic(optional = false)
    @Column(name = "TIMBRADO")
    private int timbrado;

    public TblFacturasPK() {
    }

    public TblFacturasPK(int nro, int timbrado) {
        this.nro = nro;
        this.timbrado = timbrado;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public int getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(int timbrado) {
        this.timbrado = timbrado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) nro;
        hash += (int) timbrado;
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
        if (this.timbrado != other.timbrado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblFacturasPK[ nro=" + nro + ", timbrado=" + timbrado + " ]";
    }

}
