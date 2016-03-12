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
 * @author adriang
 */
@Entity
@Table(name = "TBL_CUENTAS_CONTABLES", catalog = "", schema = "MG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCuentasContables.findAll", query = "SELECT t FROM TblCuentasContables t"),
    @NamedQuery(name = "TblCuentasContables.findById", query = "SELECT t FROM TblCuentasContables t WHERE t.id = :id"),
    @NamedQuery(name = "TblCuentasContables.findByDescripcion", query = "SELECT t FROM TblCuentasContables t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TblCuentasContables.findByImputable", query = "SELECT t FROM TblCuentasContables t WHERE t.imputable = :imputable")})
public class TblCuentasContables implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaDebeCompras")
    private List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaDebeDonaciones")
    private List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaDebeAportes")
    private List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaHaberFacturaCredito")
    private List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaHaberFacturaContado")
    private List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList4;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaIvaCredito")
    private List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList5;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaContableHaber")
    private List<TblAsientos> tblAsientosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuentaContableDebe")
    private List<TblAsientos> tblAsientosList1;

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

    @XmlTransient
    public List<TblAsientos> getTblAsientosList1() {
        return tblAsientosList1;
    }

    public void setTblAsientosList1(List<TblAsientos> tblAsientosList1) {
        this.tblAsientosList1 = tblAsientosList1;
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
    public List<TblCuentasContablesPorDefecto> getTblCuentasContablesPorDefectoList() {
        return tblCuentasContablesPorDefectoList;
    }

    public void setTblCuentasContablesPorDefectoList(List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList) {
        this.tblCuentasContablesPorDefectoList = tblCuentasContablesPorDefectoList;
    }

    @XmlTransient
    public List<TblCuentasContablesPorDefecto> getTblCuentasContablesPorDefectoList1() {
        return tblCuentasContablesPorDefectoList1;
    }

    public void setTblCuentasContablesPorDefectoList1(List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList1) {
        this.tblCuentasContablesPorDefectoList1 = tblCuentasContablesPorDefectoList1;
    }

    @XmlTransient
    public List<TblCuentasContablesPorDefecto> getTblCuentasContablesPorDefectoList2() {
        return tblCuentasContablesPorDefectoList2;
    }

    public void setTblCuentasContablesPorDefectoList2(List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList2) {
        this.tblCuentasContablesPorDefectoList2 = tblCuentasContablesPorDefectoList2;
    }

    @XmlTransient
    public List<TblCuentasContablesPorDefecto> getTblCuentasContablesPorDefectoList3() {
        return tblCuentasContablesPorDefectoList3;
    }

    public void setTblCuentasContablesPorDefectoList3(List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList3) {
        this.tblCuentasContablesPorDefectoList3 = tblCuentasContablesPorDefectoList3;
    }

    @XmlTransient
    public List<TblCuentasContablesPorDefecto> getTblCuentasContablesPorDefectoList4() {
        return tblCuentasContablesPorDefectoList4;
    }

    public void setTblCuentasContablesPorDefectoList4(List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList4) {
        this.tblCuentasContablesPorDefectoList4 = tblCuentasContablesPorDefectoList4;
    }

    @XmlTransient
    public List<TblCuentasContablesPorDefecto> getTblCuentasContablesPorDefectoList5() {
        return tblCuentasContablesPorDefectoList5;
    }

    public void setTblCuentasContablesPorDefectoList5(List<TblCuentasContablesPorDefecto> tblCuentasContablesPorDefectoList5) {
        this.tblCuentasContablesPorDefectoList5 = tblCuentasContablesPorDefectoList5;
    }

}
