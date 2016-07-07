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
@Table(name = "TBL_EVENTO_TIPOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblEventoTipos.findAll", query = "SELECT t FROM TblEventoTipos t"),
    @NamedQuery(name = "TblEventoTipos.findById", query = "SELECT t FROM TblEventoTipos t WHERE t.id = :id"),
    @NamedQuery(name = "TblEventoTipos.findByDescripcion", query = "SELECT t FROM TblEventoTipos t WHERE t.descripcion = :descripcion")})
public class TblEventoTipos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEventoTipo")
    private Collection<TblRecibos> tblRecibosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEventoTipo")
    private Collection<TblEventos> tblEventosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEventoTipo")
    private Collection<TblTransferencias> tblTransferenciasCollection;

    public TblEventoTipos() {
    }

    public TblEventoTipos(Integer id) {
        this.id = id;
    }

    public TblEventoTipos(Integer id, String descripcion) {
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
    public Collection<TblRecibos> getTblRecibosCollection() {
        return tblRecibosCollection;
    }

    public void setTblRecibosCollection(Collection<TblRecibos> tblRecibosCollection) {
        this.tblRecibosCollection = tblRecibosCollection;
    }

    @XmlTransient
    public Collection<TblEventos> getTblEventosCollection() {
        return tblEventosCollection;
    }

    public void setTblEventosCollection(Collection<TblEventos> tblEventosCollection) {
        this.tblEventosCollection = tblEventosCollection;
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
        Integer hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblEventoTipos)) {
            return false;
        }
        TblEventoTipos other = (TblEventoTipos) object;
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
