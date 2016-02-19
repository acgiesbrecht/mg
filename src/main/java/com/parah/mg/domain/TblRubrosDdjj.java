/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 * @author Adrian Giesbrecht
 */
@Entity
@Table(name = "TBL_RUBROS_DDJJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRubrosDdjj.findAll", query = "SELECT t FROM TblRubrosDdjj t"),
    @NamedQuery(name = "TblRubrosDdjj.findById", query = "SELECT t FROM TblRubrosDdjj t WHERE t.id = :id"),
    @NamedQuery(name = "TblRubrosDdjj.findByDescripcion", query = "SELECT t FROM TblRubrosDdjj t WHERE t.descripcion = :descripcion")})
public class TblRubrosDdjj implements Serializable {
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
    @OneToMany(mappedBy = "rubroDdjj")
    private List<TblCuentasContables> tblCuentasContablesList;

    public TblRubrosDdjj() {
    }

    public TblRubrosDdjj(Integer id) {
        this.id = id;
    }

    public TblRubrosDdjj(Integer id, String descripcion) {
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
    public List<TblCuentasContables> getTblCuentasContablesList() {
        return tblCuentasContablesList;
    }

    public void setTblCuentasContablesList(List<TblCuentasContables> tblCuentasContablesList) {
        this.tblCuentasContablesList = tblCuentasContablesList;
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
        if (!(object instanceof TblRubrosDdjj)) {
            return false;
        }
        TblRubrosDdjj other = (TblRubrosDdjj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblRubrosDdjj[ id=" + id + " ]";
    }

}
