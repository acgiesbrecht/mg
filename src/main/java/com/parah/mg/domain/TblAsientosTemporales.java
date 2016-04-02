/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adrian Giesbrecht
 */
@Entity
@Table(name = "TBL_ASIENTOS_TEMPORALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblAsientosTemporales.findAll", query = "SELECT t FROM TblAsientosTemporales t"),
    @NamedQuery(name = "TblAsientosTemporales.findById", query = "SELECT t FROM TblAsientosTemporales t WHERE t.id = :id"),
    @NamedQuery(name = "TblAsientosTemporales.findByFechahora", query = "SELECT t FROM TblAsientosTemporales t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblAsientosTemporales.findByMonto", query = "SELECT t FROM TblAsientosTemporales t WHERE t.monto = :monto"),
    @NamedQuery(name = "TblAsientosTemporales.findByFacturado", query = "SELECT t FROM TblAsientosTemporales t WHERE t.facturado = :facturado")})
public class TblAsientosTemporales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private int monto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FACTURADO")
    private Boolean facturado;
    @Column(name = "ES_APORTE")
    private Boolean esAporte;
    @ManyToMany(mappedBy = "tblAsientosTemporalesCollection")
    private Collection<TblRecibos> tblRecibosCollection;
    @ManyToMany(mappedBy = "tblAsientosTemporalesCollection")
    private Collection<TblTransferencias> tblTransferenciasCollection;
    @JoinColumn(name = "ID_CENTRO_DE_COSTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCentrosDeCosto idCentroDeCosto;
    @JoinColumn(name = "ID_CUENTA_CONTABLE_HABER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaContableHaber;
    @JoinColumn(name = "ID_CUENTA_CONTABLE_DEBE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaContableDebe;

    public TblAsientosTemporales() {
    }

    public TblAsientosTemporales(Integer id) {
        this.id = id;
    }

    public TblAsientosTemporales(Integer id, Date fechahora, int monto, Boolean facturado) {
        this.id = id;
        this.fechahora = fechahora;
        this.monto = monto;
        this.facturado = facturado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Boolean getFacturado() {
        return facturado;
    }

    public void setFacturado(Boolean facturado) {
        this.facturado = facturado;
    }

    @XmlTransient
    public Collection<TblRecibos> getTblRecibosCollection() {
        return tblRecibosCollection;
    }

    public void setTblRecibosCollection(Collection<TblRecibos> tblRecibosCollection) {
        this.tblRecibosCollection = tblRecibosCollection;
    }

    @XmlTransient
    public Collection<TblTransferencias> getTblTransferenciasCollection() {
        return tblTransferenciasCollection;
    }

    public void setTblTransferenciasCollection(Collection<TblTransferencias> tblTransferenciasCollection) {
        this.tblTransferenciasCollection = tblTransferenciasCollection;
    }

    public TblCentrosDeCosto getIdCentroDeCosto() {
        return idCentroDeCosto;
    }

    public void setIdCentroDeCosto(TblCentrosDeCosto idCentroDeCosto) {
        this.idCentroDeCosto = idCentroDeCosto;
    }

    public TblCuentasContables getIdCuentaContableHaber() {
        return idCuentaContableHaber;
    }

    public void setIdCuentaContableHaber(TblCuentasContables idCuentaContableHaber) {
        this.idCuentaContableHaber = idCuentaContableHaber;
    }

    public TblCuentasContables getIdCuentaContableDebe() {
        return idCuentaContableDebe;
    }

    public void setIdCuentaContableDebe(TblCuentasContables idCuentaContableDebe) {
        this.idCuentaContableDebe = idCuentaContableDebe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblAsientosTemporales)) {
            return false;
        }
        TblAsientosTemporales other = (TblAsientosTemporales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblAsientosTemporales[ id=" + id + " ]";
    }

    /**
     * @return the esAporte
     */
    public Boolean getEsAporte() {
        return esAporte;
    }

    /**
     * @param esAporte the esAporte to set
     */
    public void setEsAporte(Boolean esAporte) {
        this.esAporte = esAporte;
    }

}