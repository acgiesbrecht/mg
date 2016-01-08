/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adriang
 */
@Entity
@Table(name = "TBL_CONTRIBUYENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblContribuyentes.findAll", query = "SELECT t FROM TblContribuyentes t"),
    @NamedQuery(name = "TblContribuyentes.findByRuc", query = "SELECT t FROM TblContribuyentes t WHERE t.ruc = :ruc"),
    @NamedQuery(name = "TblContribuyentes.findByDv", query = "SELECT t FROM TblContribuyentes t WHERE t.dv = :dv"),
    @NamedQuery(name = "TblContribuyentes.findByRazonSocial", query = "SELECT t FROM TblContribuyentes t WHERE t.razonSocial = :razonSocial")})
public class TblContribuyentes implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RUC")
    private String ruc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "DV")
    private String dv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;

    public TblContribuyentes() {
    }

    public TblContribuyentes(String ruc) {
        this.ruc = ruc;
    }

    public TblContribuyentes(String ruc, String dv, String razonSocial) {
        this.ruc = ruc;
        this.dv = dv;
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        String oldRuc = this.ruc;
        this.ruc = ruc;
        changeSupport.firePropertyChange("ruc", oldRuc, ruc);
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        String oldDv = this.dv;
        this.dv = dv;
        changeSupport.firePropertyChange("dv", oldDv, dv);
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        String oldRazonSocial = this.razonSocial;
        this.razonSocial = razonSocial;
        changeSupport.firePropertyChange("razonSocial", oldRazonSocial, razonSocial);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruc != null ? ruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblContribuyentes)) {
            return false;
        }
        TblContribuyentes other = (TblContribuyentes) object;
        if ((this.ruc == null && other.ruc != null) || (this.ruc != null && !this.ruc.equals(other.ruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblContribuyentes[ ruc=" + ruc + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
