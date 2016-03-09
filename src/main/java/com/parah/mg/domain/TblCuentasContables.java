/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import java.io.Serializable;
import java.util.List;
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
    private List<TblCuentasContables> tblCuentasContablesList;
    @JoinColumn(name = "ID_CUENTA_MADRE", referencedColumnName = "ID")
    @ManyToOne
    private TblCuentasContables idCuentaMadre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaContable")
    private List<TblAsientos> tblAsientosList;

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
    public List<TblCuentasContables> getTblCuentasContablesList() {
        return tblCuentasContablesList;
    }

    public void setTblCuentasContablesList(List<TblCuentasContables> tblCuentasContablesList) {
        this.tblCuentasContablesList = tblCuentasContablesList;
    }

    public TblCuentasContables getIdCuentaMadre() {
        return idCuentaMadre;
    }

    public void setIdCuentaMadre(TblCuentasContables idCuentaMadre) {
        this.idCuentaMadre = idCuentaMadre;
    }

    @XmlTransient
    public List<TblAsientos> getTblAsientosList() {
        return tblAsientosList;
    }

    public void setTblAsientosList(List<TblAsientos> tblAsientosList) {
        this.tblAsientosList = tblAsientosList;
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
        return "com.parah.mg.domain.TblCuentasContables[ id=" + id + " ]";
    }

}
