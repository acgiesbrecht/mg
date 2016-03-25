/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import com.parah.mg.domain.miembros.TblEntidades;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "TBL_FACTURAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblFacturas.findAll", query = "SELECT t FROM TblFacturas t"),
    @NamedQuery(name = "TblFacturas.findByNro", query = "SELECT t FROM TblFacturas t WHERE t.nro = :nro"),
    @NamedQuery(name = "TblFacturas.findByFechahora", query = "SELECT t FROM TblFacturas t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblFacturas.findByRazonSocial", query = "SELECT t FROM TblFacturas t WHERE t.razonSocial = :razonSocial"),
    @NamedQuery(name = "TblFacturas.findByRuc", query = "SELECT t FROM TblFacturas t WHERE t.ruc = :ruc"),
    @NamedQuery(name = "TblFacturas.findByImporteDonacion", query = "SELECT t FROM TblFacturas t WHERE t.importeDonacion = :importeDonacion"),
    @NamedQuery(name = "TblFacturas.findByImporteAporte", query = "SELECT t FROM TblFacturas t WHERE t.importeAporte = :importeAporte"),
    @NamedQuery(name = "TblFacturas.findByAnulado", query = "SELECT t FROM TblFacturas t WHERE t.anulado = :anulado"),
    @NamedQuery(name = "TblFacturas.findByDomicilio", query = "SELECT t FROM TblFacturas t WHERE t.domicilio = :domicilio"),
    @NamedQuery(name = "TblFacturas.findByCasillaDeCorreo", query = "SELECT t FROM TblFacturas t WHERE t.casillaDeCorreo = :casillaDeCorreo")})
public class TblFacturas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO")
    private Integer nro;
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
    @Column(name = "IMPORTE_DONACION")
    private int importeDonacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMPORTE_APORTE")
    private int importeAporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANULADO")
    private Boolean anulado;
    @Size(max = 255)
    @Column(name = "DOMICILIO")
    private String domicilio;
    @Column(name = "CASILLA_DE_CORREO")
    private Integer casillaDeCorreo;
    @JoinColumn(name = "ID_ENTIDAD", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblEntidades idEntidad;
    @JoinColumn(name = "ID_TIMBRADO", referencedColumnName = "NRO")
    @ManyToOne(optional = false)
    private TblTimbrados idTimbrado;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsers idUser;

    public TblFacturas() {
    }

    public TblFacturas(Integer nro) {
        this.nro = nro;
    }

    public TblFacturas(Integer nro, Date fechahora, String razonSocial, String ruc, int importeDonacion, int importeAporte, Boolean anulado) {
        this.nro = nro;
        this.fechahora = fechahora;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.importeDonacion = importeDonacion;
        this.importeAporte = importeAporte;
        this.anulado = anulado;
    }

    public Integer getNro() {
        return nro;
    }

    public void setNro(Integer nro) {
        this.nro = nro;
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

    public int getImporteDonacion() {
        return importeDonacion;
    }

    public void setImporteDonacion(int importeDonacion) {
        this.importeDonacion = importeDonacion;
    }

    public int getImporteAporte() {
        return importeAporte;
    }

    public void setImporteAporte(int importeAporte) {
        this.importeAporte = importeAporte;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Integer getCasillaDeCorreo() {
        return casillaDeCorreo;
    }

    public void setCasillaDeCorreo(Integer casillaDeCorreo) {
        this.casillaDeCorreo = casillaDeCorreo;
    }

    public TblEntidades getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(TblEntidades idEntidad) {
        this.idEntidad = idEntidad;
    }

    public TblTimbrados getIdTimbrado() {
        return idTimbrado;
    }

    public void setIdTimbrado(TblTimbrados idTimbrado) {
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
        hash += (nro != null ? nro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblFacturas)) {
            return false;
        }
        TblFacturas other = (TblFacturas) object;
        if ((this.nro == null && other.nro != null) || (this.nro != null && !this.nro.equals(other.nro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblFacturas[ nro=" + nro + " ]";
    }

}
