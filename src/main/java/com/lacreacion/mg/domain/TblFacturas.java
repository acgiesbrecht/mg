/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrian Giesbrecht
 */
@Entity
@Table(name = "TBL_FACTURAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblFacturas.findAll", query = "SELECT t FROM TblFacturas t"),
    @NamedQuery(name = "TblFacturas.findByNro", query = "SELECT t FROM TblFacturas t WHERE t.tblFacturasPK.nro = :nro"),
    @NamedQuery(name = "TblFacturas.findByTimbrado", query = "SELECT t FROM TblFacturas t WHERE t.tblFacturasPK.timbrado = :timbrado"),
    @NamedQuery(name = "TblFacturas.findByFechahora", query = "SELECT t FROM TblFacturas t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblFacturas.findByIdMiembro", query = "SELECT t FROM TblFacturas t WHERE t.idMiembro = :idMiembro"),
    @NamedQuery(name = "TblFacturas.findByMonto", query = "SELECT t FROM TblFacturas t WHERE t.monto = :monto"),
    @NamedQuery(name = "TblFacturas.findByAnulado", query = "SELECT t FROM TblFacturas t WHERE t.anulado = :anulado")})
public class TblFacturas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TblFacturasPK tblFacturasPK;
    @Basic(optional = false)
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    @Column(name = "ID_MIEMBRO")
    private int idMiembro;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private int monto;
    @Basic(optional = false)
    @Column(name = "ANULADO")
    private Boolean anulado;

    public TblFacturas() {
    }

    public TblFacturas(TblFacturasPK tblFacturasPK) {
        this.tblFacturasPK = tblFacturasPK;
    }

    public TblFacturas(TblFacturasPK tblFacturasPK, Date fechahora, int idMiembro, int monto, Boolean anulado) {
        this.tblFacturasPK = tblFacturasPK;
        this.fechahora = fechahora;
        this.idMiembro = idMiembro;
        this.monto = monto;
        this.anulado = anulado;
    }

    public TblFacturas(int nro, int timbrado) {
        this.tblFacturasPK = new TblFacturasPK(nro, timbrado);
    }

    public TblFacturasPK getTblFacturasPK() {
        return tblFacturasPK;
    }

    public void setTblFacturasPK(TblFacturasPK tblFacturasPK) {
        this.tblFacturasPK = tblFacturasPK;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tblFacturasPK != null ? tblFacturasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblFacturas)) {
            return false;
        }
        TblFacturas other = (TblFacturas) object;
        if ((this.tblFacturasPK == null && other.tblFacturasPK != null) || (this.tblFacturasPK != null && !this.tblFacturasPK.equals(other.tblFacturasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblFacturas[ tblFacturasPK=" + tblFacturasPK + " ]";
    }

}
