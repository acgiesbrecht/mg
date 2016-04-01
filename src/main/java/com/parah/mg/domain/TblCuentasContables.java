/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adrian Giesbrecht
 */
@Entity
@Table(name = "TBL_CUENTAS_CONTABLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCuentasContables.findAll", query = "SELECT t FROM TblCuentasContables t"),
    @NamedQuery(name = "TblCuentasContables.findById", query = "SELECT t FROM TblCuentasContables t WHERE t.id = :id"),
    @NamedQuery(name = "TblCuentasContables.findByDescripcion", query = "SELECT t FROM TblCuentasContables t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TblCuentasContables.findByImputable", query = "SELECT t FROM TblCuentasContables t WHERE t.imputable = :imputable")})
public class TblCuentasContables implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaContableHaber")
    private Collection<TblAsientos> tblAsientosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaContableDebe")
    private Collection<TblAsientos> tblAsientosCollection1;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaContableHaber")
    private Collection<TblAsientosTemporales> tblAsientosTemporalesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaContableDebe")
    private Collection<TblAsientosTemporales> tblAsientosTemporalesCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaContableCtaCtePorDefecto")
    private Collection<TblCentrosDeCosto> tblCentrosDeCostoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaContableEfectivoPorDefecto")
    private Collection<TblCentrosDeCosto> tblCentrosDeCostoCollection1;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "IMPUTABLE")
    private Boolean imputable;
    @OneToMany(mappedBy = "idCuentaMadre")
    private Collection<TblCuentasContables> tblCuentasContablesCollection;
    @JoinColumn(name = "ID_CUENTA_MADRE", referencedColumnName = "ID")
    @ManyToOne
    private TblCuentasContables idCuentaMadre;

    public TblCuentasContables() {
    }

    public TblCuentasContables(Integer id) {
        this.id = id;
    }

    public TblCuentasContables(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getImputable() {
        return imputable;
    }

    public void setImputable(Boolean imputable) {
        this.imputable = imputable;
    }

    @XmlTransient
    public Collection<TblCuentasContables> getTblCuentasContablesCollection() {
        return tblCuentasContablesCollection;
    }

    public void setTblCuentasContablesCollection(Collection<TblCuentasContables> tblCuentasContablesCollection) {
        this.tblCuentasContablesCollection = tblCuentasContablesCollection;
    }

    public TblCuentasContables getIdCuentaMadre() {
        return idCuentaMadre;
    }

    public void setIdCuentaMadre(TblCuentasContables idCuentaMadre) {
        this.idCuentaMadre = idCuentaMadre;
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
        if (!(object instanceof TblCuentasContables)) {
            return false;
        }
        TblCuentasContables other = (TblCuentasContables) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    @XmlTransient
    public Collection<TblAsientosTemporales> getTblAsientosTemporalesCollection() {
        return tblAsientosTemporalesCollection;
    }

    public void setTblAsientosTemporalesCollection(Collection<TblAsientosTemporales> tblAsientosTemporalesCollection) {
        this.tblAsientosTemporalesCollection = tblAsientosTemporalesCollection;
    }

    @XmlTransient
    public Collection<TblAsientosTemporales> getTblAsientosTemporalesCollection1() {
        return tblAsientosTemporalesCollection1;
    }

    public void setTblAsientosTemporalesCollection1(Collection<TblAsientosTemporales> tblAsientosTemporalesCollection1) {
        this.tblAsientosTemporalesCollection1 = tblAsientosTemporalesCollection1;
    }

    @XmlTransient
    public Collection<TblCentrosDeCosto> getTblCentrosDeCostoCollection() {
        return tblCentrosDeCostoCollection;
    }

    public void setTblCentrosDeCostoCollection(Collection<TblCentrosDeCosto> tblCentrosDeCostoCollection) {
        this.tblCentrosDeCostoCollection = tblCentrosDeCostoCollection;
    }

    @XmlTransient
    public Collection<TblCentrosDeCosto> getTblCentrosDeCostoCollection1() {
        return tblCentrosDeCostoCollection1;
    }

    public void setTblCentrosDeCostoCollection1(Collection<TblCentrosDeCosto> tblCentrosDeCostoCollection1) {
        this.tblCentrosDeCostoCollection1 = tblCentrosDeCostoCollection1;
    }

    @XmlTransient
    public Collection<TblAsientos> getTblAsientosCollection() {
        return tblAsientosCollection;
    }

    public void setTblAsientosCollection(Collection<TblAsientos> tblAsientosCollection) {
        this.tblAsientosCollection = tblAsientosCollection;
    }

    @XmlTransient
    public Collection<TblAsientos> getTblAsientosCollection1() {
        return tblAsientosCollection1;
    }

    public void setTblAsientosCollection1(Collection<TblAsientos> tblAsientosCollection1) {
        this.tblAsientosCollection1 = tblAsientosCollection1;
    }

}
