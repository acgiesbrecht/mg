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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adrian Giesbrecht
 */
@Entity
@Table(name = "TBL_FACTURAS_COMPRAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblFacturasCompras.findAll", query = "SELECT t FROM TblFacturasCompras t"),
    @NamedQuery(name = "TblFacturasCompras.findById", query = "SELECT t FROM TblFacturasCompras t WHERE t.id = :id"),
    @NamedQuery(name = "TblFacturasCompras.findByNro", query = "SELECT t FROM TblFacturasCompras t WHERE t.nro = :nro"),
    @NamedQuery(name = "TblFacturasCompras.findByNroTimbrado", query = "SELECT t FROM TblFacturasCompras t WHERE t.nroTimbrado = :nroTimbrado"),
    @NamedQuery(name = "TblFacturasCompras.findByVencimientoTimbrado", query = "SELECT t FROM TblFacturasCompras t WHERE t.vencimientoTimbrado = :vencimientoTimbrado"),
    @NamedQuery(name = "TblFacturasCompras.findByFechahora", query = "SELECT t FROM TblFacturasCompras t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblFacturasCompras.findByRazonSocial", query = "SELECT t FROM TblFacturasCompras t WHERE t.razonSocial = :razonSocial"),
    @NamedQuery(name = "TblFacturasCompras.findByRuc", query = "SELECT t FROM TblFacturasCompras t WHERE t.ruc = :ruc"),
    @NamedQuery(name = "TblFacturasCompras.findByImporte", query = "SELECT t FROM TblFacturasCompras t WHERE t.importe = :importe"),
    @NamedQuery(name = "TblFacturasCompras.findByAnulado", query = "SELECT t FROM TblFacturasCompras t WHERE t.anulado = :anulado"),
    @NamedQuery(name = "TblFacturasCompras.findByIdUser", query = "SELECT t FROM TblFacturasCompras t WHERE t.idUser = :idUser")})
public class TblFacturasCompras implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO")
    private int nro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_TIMBRADO")
    private int nroTimbrado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VENCIMIENTO_TIMBRADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimientoTimbrado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RUC")
    private String ruc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMPORTE")
    private int importe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANULADO")
    private Boolean anulado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_USER")
    private int idUser;
    @JoinTable(name = "TBL_FACTURAS_TRANSACCIONES", joinColumns = {
        @JoinColumn(name = "ID_FACTURA", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_TRANSACCION", referencedColumnName = "ID")})
    @ManyToMany
    private List<TblTransacciones> tblTransaccionesList;

    public TblFacturasCompras() {
    }

    public TblFacturasCompras(Integer id) {
        this.id = id;
    }

    public TblFacturasCompras(Integer id, int nro, int nroTimbrado, Date vencimientoTimbrado, Date fechahora, String razonSocial, String ruc, int importe, Boolean anulado, int idUser) {
        this.id = id;
        this.nro = nro;
        this.nroTimbrado = nroTimbrado;
        this.vencimientoTimbrado = vencimientoTimbrado;
        this.fechahora = fechahora;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.importe = importe;
        this.anulado = anulado;
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public int getNroTimbrado() {
        return nroTimbrado;
    }

    public void setNroTimbrado(int nroTimbrado) {
        this.nroTimbrado = nroTimbrado;
    }

    public Date getVencimientoTimbrado() {
        return vencimientoTimbrado;
    }

    public void setVencimientoTimbrado(Date vencimientoTimbrado) {
        this.vencimientoTimbrado = vencimientoTimbrado;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @XmlTransient
    public List<TblTransacciones> getTblTransaccionesList() {
        return tblTransaccionesList;
    }

    public void setTblTransaccionesList(List<TblTransacciones> tblTransaccionesList) {
        this.tblTransaccionesList = tblTransaccionesList;
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
        if (!(object instanceof TblFacturasCompras)) {
            return false;
        }
        TblFacturasCompras other = (TblFacturasCompras) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblFacturasCompras[ id=" + id + " ]";
    }

}
