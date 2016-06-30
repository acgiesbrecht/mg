/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "TBL_NOTAS_DE_CREDITO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblNotasDeCredito.findAll", query = "SELECT t FROM TblNotasDeCredito t"),
    @NamedQuery(name = "TblNotasDeCredito.findById", query = "SELECT t FROM TblNotasDeCredito t WHERE t.id = :id"),
    @NamedQuery(name = "TblNotasDeCredito.findByNro", query = "SELECT t FROM TblNotasDeCredito t WHERE t.nro = :nro"),
    @NamedQuery(name = "TblNotasDeCredito.findByNroTimbrado", query = "SELECT t FROM TblNotasDeCredito t WHERE t.nroTimbrado = :nroTimbrado"),
    @NamedQuery(name = "TblNotasDeCredito.findByVencimientoTimbrado", query = "SELECT t FROM TblNotasDeCredito t WHERE t.vencimientoTimbrado = :vencimientoTimbrado"),
    @NamedQuery(name = "TblNotasDeCredito.findByFechahora", query = "SELECT t FROM TblNotasDeCredito t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblNotasDeCredito.findByRazonSocial", query = "SELECT t FROM TblNotasDeCredito t WHERE t.razonSocial = :razonSocial"),
    @NamedQuery(name = "TblNotasDeCredito.findByRuc", query = "SELECT t FROM TblNotasDeCredito t WHERE t.ruc = :ruc"),
    @NamedQuery(name = "TblNotasDeCredito.findByMontoExentas", query = "SELECT t FROM TblNotasDeCredito t WHERE t.montoExentas = :montoExentas"),
    @NamedQuery(name = "TblNotasDeCredito.findByMontoIva5", query = "SELECT t FROM TblNotasDeCredito t WHERE t.montoIva5 = :montoIva5"),
    @NamedQuery(name = "TblNotasDeCredito.findByMontoIva10", query = "SELECT t FROM TblNotasDeCredito t WHERE t.montoIva10 = :montoIva10"),
    @NamedQuery(name = "TblNotasDeCredito.findByIva5", query = "SELECT t FROM TblNotasDeCredito t WHERE t.iva5 = :iva5"),
    @NamedQuery(name = "TblNotasDeCredito.findByIva10", query = "SELECT t FROM TblNotasDeCredito t WHERE t.iva10 = :iva10"),
    @NamedQuery(name = "TblNotasDeCredito.findByObservacion", query = "SELECT t FROM TblNotasDeCredito t WHERE t.observacion = :observacion"),
    @NamedQuery(name = "TblNotasDeCredito.findByIdUser", query = "SELECT t FROM TblNotasDeCredito t WHERE t.idUser = :idUser")})
public class TblNotasDeCredito implements Serializable {

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
    @Size(min = 1, max = 8)
    @Column(name = "NRO_TIMBRADO")
    private String nroTimbrado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VENCIMIENTO_TIMBRADO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime vencimientoTimbrado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechahora;
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
    @Size(max = 255)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_USER")
    private int idUser;
    @JoinTable(name = "TBL_NOTAS_DE_CREDITO_ASIENTOS", joinColumns = {
        @JoinColumn(name = "ID_NOTA_DE_CREDITO", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_ASIENTO", referencedColumnName = "ID")})
    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<TblAsientos> tblAsientosCollection;

    public TblNotasDeCredito() {
    }

    public TblNotasDeCredito(Integer id) {
        this.id = id;
    }

    public TblNotasDeCredito(Integer id, String nro, String nroTimbrado, LocalDateTime vencimientoTimbrado, LocalDateTime fechahora, String razonSocial, String ruc, int montoExentas, int montoIva5, int montoIva10, int iva5, int iva10, int idUser) {
        this.id = id;
        this.nro = nro;
        this.nroTimbrado = nroTimbrado;
        this.vencimientoTimbrado = vencimientoTimbrado;
        this.fechahora = fechahora;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.montoExentas = montoExentas;
        this.montoIva5 = montoIva5;
        this.montoIva10 = montoIva10;
        this.iva5 = iva5;
        this.iva10 = iva10;
        this.idUser = idUser;
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

    public String getNroTimbrado() {
        return nroTimbrado;
    }

    public void setNroTimbrado(String nroTimbrado) {
        this.nroTimbrado = nroTimbrado;
    }

    public LocalDateTime getVencimientoTimbrado() {
        return vencimientoTimbrado;
    }

    public void setVencimientoTimbrado(LocalDateTime vencimientoTimbrado) {
        this.vencimientoTimbrado = vencimientoTimbrado;
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @XmlTransient
    public Collection<TblAsientos> getTblAsientosCollection() {
        return tblAsientosCollection;
    }

    public void setTblAsientosCollection(Collection<TblAsientos> tblAsientosCollection) {
        this.tblAsientosCollection = tblAsientosCollection;
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
        if (!(object instanceof TblNotasDeCredito)) {
            return false;
        }
        TblNotasDeCredito other = (TblNotasDeCredito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblNotasDeCredito[ id=" + id + " ]";
    }

}
