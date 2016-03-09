/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Table(name = "TBL_ASIENTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblAsientos.findAll", query = "SELECT t FROM TblAsientos t"),
    @NamedQuery(name = "TblAsientos.findById", query = "SELECT t FROM TblAsientos t WHERE t.id = :id"),
    @NamedQuery(name = "TblAsientos.findByFechahora", query = "SELECT t FROM TblAsientos t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblAsientos.findByTipoAsiento", query = "SELECT t FROM TblAsientos t WHERE t.tipoAsiento = :tipoAsiento")})
public class TblAsientos implements Serializable {

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
    @Column(name = "TIPO_ASIENTO")
    private int tipoAsiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private Integer monto;
    @ManyToMany(mappedBy = "tblAsientosList")
    private List<TblFacturasCompra> tblFacturasCompraList;
    @JoinColumn(name = "ID_CENTRO_DE_COSTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCentrosDeCosto idCentroDeCosto;
    @JoinColumn(name = "ID_CUENTA_CONTABLE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaContable;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsers idUser;

    public TblAsientos() {
    }

    public TblAsientos(Integer id) {
        this.id = id;
    }

    public TblAsientos(Integer id, Date fechahora, int tipoAsiento) {
        this.id = id;
        this.fechahora = fechahora;
        this.tipoAsiento = tipoAsiento;
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

    public int getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(int tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    @XmlTransient
    public List<TblFacturasCompra> getTblFacturasCompraList() {
        return tblFacturasCompraList;
    }

    public void setTblFacturasCompraList(List<TblFacturasCompra> tblFacturasCompraList) {
        this.tblFacturasCompraList = tblFacturasCompraList;
    }

    public TblCentrosDeCosto getIdCentroDeCosto() {
        return idCentroDeCosto;
    }

    public void setIdCentroDeCosto(TblCentrosDeCosto idCentroDeCosto) {
        this.idCentroDeCosto = idCentroDeCosto;
    }

    public TblCuentasContables getIdCuentaContable() {
        return idCuentaContable;
    }

    public void setIdCuentaContable(TblCuentasContables idCuentaContable) {
        this.idCuentaContable = idCuentaContable;
    }

    public TblUsers getIdUser() {
        return idUser;
    }

    public void setIdUser(TblUsers idUser) {
        this.idUser = idUser;
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
        if (!(object instanceof TblAsientos)) {
            return false;
        }
        TblAsientos other = (TblAsientos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblAsientos[ id=" + id + " ]";
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

}
