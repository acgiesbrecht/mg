/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author adriang
 */
@Entity
@Table(name = "TBL_FORMAS_DE_PAGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblFormasDePago.findAll", query = "SELECT t FROM TblFormasDePago t"),
    @NamedQuery(name = "TblFormasDePago.findById", query = "SELECT t FROM TblFormasDePago t WHERE t.id = :id"),
    @NamedQuery(name = "TblFormasDePago.findByDescripcion", query = "SELECT t FROM TblFormasDePago t WHERE t.descripcion = :descripcion")})
public class TblFormasDePago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFormaDePagoPreferida")
    private List<TblMiembros> tblMiembrosList;

    public TblFormasDePago() {
    }

    public TblFormasDePago(Integer id) {
        this.id = id;
    }

    public TblFormasDePago(Integer id, String descripcion) {
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
    public List<TblMiembros> getTblMiembrosList() {
        return tblMiembrosList;
    }

    public void setTblMiembrosList(List<TblMiembros> tblMiembrosList) {
        this.tblMiembrosList = tblMiembrosList;
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
        if (!(object instanceof TblFormasDePago)) {
            return false;
        }
        TblFormasDePago other = (TblFormasDePago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblFormasDePago[ id=" + id + " ]";
    }

}
