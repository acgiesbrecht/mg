/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain.miembros;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author adriang
 */
@Entity
@Table(name = "TBL_MIEMBROS_RELACIONES_ROLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMiembrosRelacionesRoles.findAll", query = "SELECT t FROM TblMiembrosRelacionesRoles t"),
    @NamedQuery(name = "TblMiembrosRelacionesRoles.findById", query = "SELECT t FROM TblMiembrosRelacionesRoles t WHERE t.id = :id"),
    @NamedQuery(name = "TblMiembrosRelacionesRoles.findByDescripcion", query = "SELECT t FROM TblMiembrosRelacionesRoles t WHERE t.descripcion = :descripcion")})
public class TblMiembrosRelacionesRoles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idMiembrosRelacionesRol2")
    private Collection<TblMiembrosRelaciones> tblMiembrosRelacionesCollection;
    @OneToMany(mappedBy = "idMiembrosRelacionesRol1")
    private Collection<TblMiembrosRelaciones> tblMiembrosRelacionesCollection1;

    public TblMiembrosRelacionesRoles() {
    }

    public TblMiembrosRelacionesRoles(Integer id) {
        this.id = id;
    }

    public TblMiembrosRelacionesRoles(Integer id, String descripcion) {
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
    public Collection<TblMiembrosRelaciones> getTblMiembrosRelacionesCollection() {
        return tblMiembrosRelacionesCollection;
    }

    public void setTblMiembrosRelacionesCollection(Collection<TblMiembrosRelaciones> tblMiembrosRelacionesCollection) {
        this.tblMiembrosRelacionesCollection = tblMiembrosRelacionesCollection;
    }

    @XmlTransient
    public Collection<TblMiembrosRelaciones> getTblMiembrosRelacionesCollection1() {
        return tblMiembrosRelacionesCollection1;
    }

    public void setTblMiembrosRelacionesCollection1(Collection<TblMiembrosRelaciones> tblMiembrosRelacionesCollection1) {
        this.tblMiembrosRelacionesCollection1 = tblMiembrosRelacionesCollection1;
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
        if (!(object instanceof TblMiembrosRelacionesRoles)) {
            return false;
        }
        TblMiembrosRelacionesRoles other = (TblMiembrosRelacionesRoles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblMiembrosRelacionesRoles[ id=" + id + " ]";
    }

}
