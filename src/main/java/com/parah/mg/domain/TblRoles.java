/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "TBL_ROLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRoles.findAll", query = "SELECT t FROM TblRoles t"),
    @NamedQuery(name = "TblRoles.findById", query = "SELECT t FROM TblRoles t WHERE t.id = :id"),
    @NamedQuery(name = "TblRoles.findByDescripcion", query = "SELECT t FROM TblRoles t WHERE t.descripcion = :descripcion")})
public class TblRoles implements Serializable {

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
    @ManyToMany(mappedBy = "tblRolesList")
    private Collection<TblUsers> tblUsersCollection;

    public TblRoles() {
    }

    public TblRoles(Integer id) {
        this.id = id;
    }

    public TblRoles(Integer id, String descripcion) {
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
    public Collection<TblUsers> getTblUsersCollection() {
        return tblUsersCollection;
    }

    public void setTblUsersCollection(Collection<TblUsers> tblUsersCollection) {
        this.tblUsersCollection = tblUsersCollection;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRoles)) {
            return false;
        }
        TblRoles other = (TblRoles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
