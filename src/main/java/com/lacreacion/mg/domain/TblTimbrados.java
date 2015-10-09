/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author adriang
 */
@Entity
@Table(name = "TBL_TIMBRADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblTimbrados.findAll", query = "SELECT t FROM TblTimbrados t"),
    @NamedQuery(name = "TblTimbrados.findByNro", query = "SELECT t FROM TblTimbrados t WHERE t.nro = :nro"),
    @NamedQuery(name = "TblTimbrados.findByNroFacturaIncio", query = "SELECT t FROM TblTimbrados t WHERE t.nroFacturaIncio = :nroFacturaIncio"),
    @NamedQuery(name = "TblTimbrados.findByNroFacturaFin", query = "SELECT t FROM TblTimbrados t WHERE t.nroFacturaFin = :nroFacturaFin"),
    @NamedQuery(name = "TblTimbrados.findByFechaVencimiento", query = "SELECT t FROM TblTimbrados t WHERE t.fechaVencimiento = :fechaVencimiento")})
public class TblTimbrados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NRO")
    private Integer nro;
    @Basic(optional = false)
    @Column(name = "NRO_FACTURA_INCIO")
    private int nroFacturaIncio;
    @Basic(optional = false)
    @Column(name = "NRO_FACTURA_FIN")
    private int nroFacturaFin;
    @Basic(optional = false)
    @Column(name = "FECHA_VENCIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblTimbrados")
    private List<TblFacturas> tblFacturasList;

    public TblTimbrados() {
    }

    public TblTimbrados(Integer nro) {
        this.nro = nro;
    }

    public TblTimbrados(Integer nro, int nroFacturaIncio, int nroFacturaFin, Date fechaVencimiento) {
        this.nro = nro;
        this.nroFacturaIncio = nroFacturaIncio;
        this.nroFacturaFin = nroFacturaFin;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getNro() {
        return nro;
    }

    public void setNro(Integer nro) {
        this.nro = nro;
    }

    public int getNroFacturaIncio() {
        return nroFacturaIncio;
    }

    public void setNroFacturaIncio(int nroFacturaIncio) {
        this.nroFacturaIncio = nroFacturaIncio;
    }

    public int getNroFacturaFin() {
        return nroFacturaFin;
    }

    public void setNroFacturaFin(int nroFacturaFin) {
        this.nroFacturaFin = nroFacturaFin;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @XmlTransient
    public List<TblFacturas> getTblFacturasList() {
        return tblFacturasList;
    }

    public void setTblFacturasList(List<TblFacturas> tblFacturasList) {
        this.tblFacturasList = tblFacturasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nro != null ? nro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTimbrados)) {
            return false;
        }
        TblTimbrados other = (TblTimbrados) object;
        if ((this.nro == null && other.nro != null) || (this.nro != null && !this.nro.equals(other.nro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblTimbrados[ nro=" + nro + " ]";
    }

}
