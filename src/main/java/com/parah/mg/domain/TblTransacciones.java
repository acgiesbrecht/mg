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
@Table(name = "TBL_TRANSACCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblTransacciones.findAll", query = "SELECT t FROM TblTransacciones t"),
    @NamedQuery(name = "TblTransacciones.findById", query = "SELECT t FROM TblTransacciones t WHERE t.id = :id"),
    @NamedQuery(name = "TblTransacciones.findByFechahora", query = "SELECT t FROM TblTransacciones t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblTransacciones.findByTipoTransaccion", query = "SELECT t FROM TblTransacciones t WHERE t.tipoTransaccion = :tipoTransaccion")})
public class TblTransacciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_TRANSACCION")
    private int tipoTransaccion;
    @ManyToMany(mappedBy = "tblTransaccionesList")
    private List<TblFacturasCompras> tblFacturasComprasList;
    @JoinColumn(name = "ID_CENTRO_DE_COSTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCentrosDeCosto idCentroDeCosto;
    @JoinColumn(name = "ID_CUENTA_CONTABLE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaContable;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsers idUser;

    public TblTransacciones() {
    }

    public TblTransacciones(Integer id) {
        this.id = id;
    }

    public TblTransacciones(Integer id, Date fechahora, int tipoTransaccion) {
        this.id = id;
        this.fechahora = fechahora;
        this.tipoTransaccion = tipoTransaccion;
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

    public int getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(int tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    @XmlTransient
    public List<TblFacturasCompras> getTblFacturasComprasList() {
        return tblFacturasComprasList;
    }

    public void setTblFacturasComprasList(List<TblFacturasCompras> tblFacturasComprasList) {
        this.tblFacturasComprasList = tblFacturasComprasList;
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
        if (!(object instanceof TblTransacciones)) {
            return false;
        }
        TblTransacciones other = (TblTransacciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblTransacciones[ id=" + id + " ]";
    }

}
