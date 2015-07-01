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
@Table(name = "TBL_CATEGORIAS_TRIBUTARIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCategoriasTributarias.findAll", query = "SELECT t FROM TblCategoriasTributarias t"),
    @NamedQuery(name = "TblCategoriasTributarias.findById", query = "SELECT t FROM TblCategoriasTributarias t WHERE t.id = :id"),
    @NamedQuery(name = "TblCategoriasTributarias.findByDescripcion", query = "SELECT t FROM TblCategoriasTributarias t WHERE t.descripcion = :descripcion")})
public class TblCategoriasTributarias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategoriaTributaria")
    private Collection<TblColectas> tblColectasCollection;

    public TblCategoriasTributarias() {
    }

    public TblCategoriasTributarias(Integer id) {
        this.id = id;
    }

    public TblCategoriasTributarias(Integer id, String descripcion) {
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

    @XmlTransient
    public Collection<TblColectas> getTblColectasCollection() {
        return tblColectasCollection;
    }

    public void setTblColectasCollection(Collection<TblColectas> tblColectasCollection) {
        this.tblColectasCollection = tblColectasCollection;
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
        if (!(object instanceof TblCategoriasTributarias)) {
            return false;
        }
        TblCategoriasTributarias other = (TblCategoriasTributarias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblCategoriasTributarias[ id=" + id + " ]";
    }

}
