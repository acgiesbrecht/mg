/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.domain;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Industria
 */
@Entity
@Table(name = "TBL_COLECTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblColectas.findAll", query = "SELECT t FROM TblColectas t"),
    @NamedQuery(name = "TblColectas.findById", query = "SELECT t FROM TblColectas t WHERE t.id = :id"),
    @NamedQuery(name = "TblColectas.findByFecha", query = "SELECT t FROM TblColectas t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TblColectas.findByDescripcion", query = "SELECT t FROM TblColectas t WHERE t.descripcion = :descripcion")})
public class TblColectas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ID_CATEGORIA_TRIBUTARIA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCategoriasTributarias idCategoriaTributaria;

    public TblColectas() {
    }

    public TblColectas(Integer id) {
        this.id = id;
    }

    public TblColectas(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TblCategoriasTributarias getIdCategoriaTributaria() {
        return idCategoriaTributaria;
    }

    public void setIdCategoriaTributaria(TblCategoriasTributarias idCategoriaTributaria) {
        this.idCategoriaTributaria = idCategoriaTributaria;
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
        if (!(object instanceof TblColectas)) {
            return false;
        }
        TblColectas other = (TblColectas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblColectas[ id=" + id + " ]";
    }

}
