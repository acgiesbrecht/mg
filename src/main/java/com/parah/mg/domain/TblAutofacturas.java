/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "TBL_AUTOFACTURAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblAutofacturas.findAll", query = "SELECT t FROM TblAutofacturas t"),
    @NamedQuery(name = "TblAutofacturas.findById", query = "SELECT t FROM TblAutofacturas t WHERE t.id = :id"),
    @NamedQuery(name = "TblAutofacturas.findByNro", query = "SELECT t FROM TblAutofacturas t WHERE t.nro = :nro"),
    @NamedQuery(name = "TblAutofacturas.findByFechahora", query = "SELECT t FROM TblAutofacturas t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblAutofacturas.findByNombre", query = "SELECT t FROM TblAutofacturas t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblAutofacturas.findByDomicilio", query = "SELECT t FROM TblAutofacturas t WHERE t.domicilio = :domicilio"),
    @NamedQuery(name = "TblAutofacturas.findByDireccionDeTransaccion", query = "SELECT t FROM TblAutofacturas t WHERE t.direccionDeTransaccion = :direccionDeTransaccion"),
    @NamedQuery(name = "TblAutofacturas.findByCi", query = "SELECT t FROM TblAutofacturas t WHERE t.ci = :ci"),
    @NamedQuery(name = "TblAutofacturas.findByCantidad", query = "SELECT t FROM TblAutofacturas t WHERE t.cantidad = :cantidad"),
    @NamedQuery(name = "TblAutofacturas.findByConcepto", query = "SELECT t FROM TblAutofacturas t WHERE t.concepto = :concepto"),
    @NamedQuery(name = "TblAutofacturas.findByPrecioUnitario", query = "SELECT t FROM TblAutofacturas t WHERE t.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "TblAutofacturas.findByMonto", query = "SELECT t FROM TblAutofacturas t WHERE t.monto = :monto"),
    @NamedQuery(name = "TblAutofacturas.findByObservacion", query = "SELECT t FROM TblAutofacturas t WHERE t.observacion = :observacion"),
    @NamedQuery(name = "TblAutofacturas.findByAnulado", query = "SELECT t FROM TblAutofacturas t WHERE t.anulado = :anulado")})
public class TblAutofacturas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "NRO")
    private String nro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DOMICILIO")
    private String domicilio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DIRECCION_DE_TRANSACCION")
    private String direccionDeTransaccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CI")
    private String ci;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CONCEPTO")
    private String concepto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRECIO_UNITARIO")
    private int precioUnitario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private int monto;
    @Size(max = 255)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANULADO")
    private Boolean anulado;
    @JoinTable(name = "TBL_AUTOFACTURAS_ASIENTOS", joinColumns = {
        @JoinColumn(name = "ID_AUTOFACTURA", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_ASIENTO", referencedColumnName = "ID")})
    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<TblAsientos> tblAsientosCollection;
    @JoinColumn(name = "ID_TIMBRADO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblTimbradosAutofacturas idTimbrado;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsers idUser;

    public TblAutofacturas() {
    }

    public TblAutofacturas(Integer id) {
        this.id = id;
    }

    public TblAutofacturas(Integer id, String nro, Date fechahora, String nombre, String domicilio, String direccionDeTransaccion, String ci, int cantidad, String concepto, int precioUnitario, int monto, Boolean anulado) {
        this.id = id;
        this.nro = nro;
        this.fechahora = fechahora;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.direccionDeTransaccion = direccionDeTransaccion;
        this.ci = ci;
        this.cantidad = cantidad;
        this.concepto = concepto;
        this.precioUnitario = precioUnitario;
        this.monto = monto;
        this.anulado = anulado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getDireccionDeTransaccion() {
        return direccionDeTransaccion;
    }

    public void setDireccionDeTransaccion(String direccionDeTransaccion) {
        this.direccionDeTransaccion = direccionDeTransaccion;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    @XmlTransient
    public Collection<TblAsientos> getTblAsientosCollection() {
        return tblAsientosCollection;
    }

    public void setTblAsientosCollection(Collection<TblAsientos> tblAsientosCollection) {
        this.tblAsientosCollection = tblAsientosCollection;
    }

    public TblTimbradosAutofacturas getIdTimbrado() {
        return idTimbrado;
    }

    public void setIdTimbrado(TblTimbradosAutofacturas idTimbrado) {
        this.idTimbrado = idTimbrado;
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
        if (!(object instanceof TblAutofacturas)) {
            return false;
        }
        TblAutofacturas other = (TblAutofacturas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblAutofacturas[ id=" + id + " ]";
    }

}
