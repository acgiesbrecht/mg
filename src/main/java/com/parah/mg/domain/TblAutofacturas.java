/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adriang
 */
@Entity
@Table(name = "TBL_AUTOFACTURAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblAutofacturas.findAll", query = "SELECT t FROM TblAutofacturas t"),
    @NamedQuery(name = "TblAutofacturas.findById", query = "SELECT t FROM TblAutofacturas t WHERE t.id = :id"),
    @NamedQuery(name = "TblAutofacturas.findByNro", query = "SELECT t FROM TblAutofacturas t WHERE t.nro = :nro"),
    @NamedQuery(name = "TblAutofacturas.findByCondicionContado", query = "SELECT t FROM TblAutofacturas t WHERE t.condicionContado = :condicionContado"),
    @NamedQuery(name = "TblAutofacturas.findByFechahora", query = "SELECT t FROM TblAutofacturas t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblAutofacturas.findByRazonSocial", query = "SELECT t FROM TblAutofacturas t WHERE t.razonSocial = :razonSocial"),
    @NamedQuery(name = "TblAutofacturas.findByRuc", query = "SELECT t FROM TblAutofacturas t WHERE t.ruc = :ruc"),
    @NamedQuery(name = "TblAutofacturas.findByMontoExentas", query = "SELECT t FROM TblAutofacturas t WHERE t.montoExentas = :montoExentas"),
    @NamedQuery(name = "TblAutofacturas.findByMontoIva5", query = "SELECT t FROM TblAutofacturas t WHERE t.montoIva5 = :montoIva5"),
    @NamedQuery(name = "TblAutofacturas.findByMontoIva10", query = "SELECT t FROM TblAutofacturas t WHERE t.montoIva10 = :montoIva10"),
    @NamedQuery(name = "TblAutofacturas.findByIva5", query = "SELECT t FROM TblAutofacturas t WHERE t.iva5 = :iva5"),
    @NamedQuery(name = "TblAutofacturas.findByIva10", query = "SELECT t FROM TblAutofacturas t WHERE t.iva10 = :iva10"),
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
    @Column(name = "CONDICION_CONTADO")
    private Boolean condicionContado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RUC")
    private String ruc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_EXENTAS")
    private int montoExentas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_IVA5")
    private int montoIva5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_IVA10")
    private int montoIva10;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IVA5")
    private int iva5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IVA10")
    private int iva10;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANULADO")
    private Boolean anulado;
    @JoinColumn(name = "NRO_TIMBRADO", referencedColumnName = "NRO")
    @ManyToOne(optional = false)
    private TblTimbradosAutofacturas nroTimbrado;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsers idUser;

    public TblAutofacturas() {
    }

    public TblAutofacturas(Integer id) {
        this.id = id;
    }

    public TblAutofacturas(Integer id, String nro, Boolean condicionContado, Date fechahora, String razonSocial, String ruc, int montoExentas, int montoIva5, int montoIva10, int iva5, int iva10, Boolean anulado) {
        this.id = id;
        this.nro = nro;
        this.condicionContado = condicionContado;
        this.fechahora = fechahora;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.montoExentas = montoExentas;
        this.montoIva5 = montoIva5;
        this.montoIva10 = montoIva10;
        this.iva5 = iva5;
        this.iva10 = iva10;
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

    public Boolean getCondicionContado() {
        return condicionContado;
    }

    public void setCondicionContado(Boolean condicionContado) {
        this.condicionContado = condicionContado;
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

    public int getMontoExentas() {
        return montoExentas;
    }

    public void setMontoExentas(int montoExentas) {
        this.montoExentas = montoExentas;
    }

    public int getMontoIva5() {
        return montoIva5;
    }

    public void setMontoIva5(int montoIva5) {
        this.montoIva5 = montoIva5;
    }

    public int getMontoIva10() {
        return montoIva10;
    }

    public void setMontoIva10(int montoIva10) {
        this.montoIva10 = montoIva10;
    }

    public int getIva5() {
        return iva5;
    }

    public void setIva5(int iva5) {
        this.iva5 = iva5;
    }

    public int getIva10() {
        return iva10;
    }

    public void setIva10(int iva10) {
        this.iva10 = iva10;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    public TblTimbradosAutofacturas getNroTimbrado() {
        return nroTimbrado;
    }

    public void setNroTimbrado(TblTimbradosAutofacturas nroTimbrado) {
        this.nroTimbrado = nroTimbrado;
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
