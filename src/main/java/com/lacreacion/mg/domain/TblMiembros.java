/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Industria
 */
@Entity
@Table(name = "TBL_MIEMBROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMiembros.findAll", query = "SELECT t FROM TblMiembros t"),
    @NamedQuery(name = "TblMiembros.findById", query = "SELECT t FROM TblMiembros t WHERE t.id = :id"),
    @NamedQuery(name = "TblMiembros.findByNombre", query = "SELECT t FROM TblMiembros t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblMiembros.findByCtacte", query = "SELECT t FROM TblMiembros t WHERE t.ctacte = :ctacte"),
    @NamedQuery(name = "TblMiembros.findByDomicilio", query = "SELECT t FROM TblMiembros t WHERE t.domicilio = :domicilio"),
    @NamedQuery(name = "TblMiembros.findByBox", query = "SELECT t FROM TblMiembros t WHERE t.box = :box")})
public class TblMiembros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CTACTE")
    private Integer ctacte;
    @Column(name = "DOMICILIO")
    private String domicilio;
    @Column(name = "BOX")
    private Integer box;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMiembro")
    private Collection<TblRematesDetalle> tblRematesDetalleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMiembro")
    private Collection<TblRecibos> tblRecibosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMiembro")
    private Collection<TblTransferencias> tblTransferenciasCollection;

    public TblMiembros() {
    }

    public TblMiembros(Integer id) {
        this.id = id;
    }

    public TblMiembros(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCtacte() {
        return ctacte;
    }

    public void setCtacte(Integer ctacte) {
        this.ctacte = ctacte;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Integer getBox() {
        return box;
    }

    public void setBox(Integer box) {
        this.box = box;
    }

    @XmlTransient
    public Collection<TblRematesDetalle> getTblRematesDetalleCollection() {
        return tblRematesDetalleCollection;
    }

    public void setTblRematesDetalleCollection(Collection<TblRematesDetalle> tblRematesDetalleCollection) {
        this.tblRematesDetalleCollection = tblRematesDetalleCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblMiembros)) {
            return false;
        }
        TblMiembros other = (TblMiembros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
