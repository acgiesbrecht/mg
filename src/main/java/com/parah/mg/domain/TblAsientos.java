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
    @NamedQuery(name = "TblAsientos.findByMonto", query = "SELECT t FROM TblAsientos t WHERE t.monto = :monto")})
public class TblAsientos implements Serializable {

    @ManyToMany(mappedBy = "tblAsientosList")
    private List<TblFacturasCompra> tblFacturasCompraList;

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
    @ManyToMany(mappedBy = "tblAsientosList")
    private List<TblNotasDeCredito> tblNotasDeCreditoList;
    @ManyToMany(mappedBy = "tblAsientosList")
    private List<TblAutofacturas> tblAutofacturasList;
    @ManyToMany(mappedBy = "tblAsientosList")
    private List<TblRecibosCompra> tblRecibosCompraList;
    @JoinColumn(name = "ID_CENTRO_DE_COSTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCentrosDeCosto idCentroDeCosto;
    @JoinColumn(name = "ID_CUENTA_CONTABLE_HABER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaContableHaber;
    @JoinColumn(name = "ID_CUENTA_CONTABLE_DEBE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaContableDebe;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsers idUser;

    public TblAsientos() {
    }

    public TblAsientos(Integer id) {
        this.id = id;
    }

    public TblAsientos(Integer id, Date fechahora, int monto) {
        this.id = id;
        this.fechahora = fechahora;
        this.monto = monto;
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

    @XmlTransient
    public List<TblNotasDeCredito> getTblNotasDeCreditoList() {
        return tblNotasDeCreditoList;
    }

    public void setTblNotasDeCreditoList(List<TblNotasDeCredito> tblNotasDeCreditoList) {
        this.tblNotasDeCreditoList = tblNotasDeCreditoList;
    }

    @XmlTransient
    public List<TblAutofacturas> getTblAutofacturasList() {
        return tblAutofacturasList;
    }

    public void setTblAutofacturasList(List<TblAutofacturas> tblAutofacturasList) {
        this.tblAutofacturasList = tblAutofacturasList;
    }

    @XmlTransient
    public List<TblRecibosCompra> getTblRecibosCompraList() {
        return tblRecibosCompraList;
    }

    public void setTblRecibosCompraList(List<TblRecibosCompra> tblRecibosCompraList) {
        this.tblRecibosCompraList = tblRecibosCompraList;
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

    @XmlTransient
    public List<TblFacturasCompra> getTblFacturasCompraList() {
        return tblFacturasCompraList;
    }

    public void setTblFacturasCompraList(List<TblFacturasCompra> tblFacturasCompraList) {
        this.tblFacturasCompraList = tblFacturasCompraList;
    }

}
