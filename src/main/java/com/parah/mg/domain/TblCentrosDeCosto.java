/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import com.parah.mg.domain.eventos.TblEventos;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "TBL_CENTROS_DE_COSTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCentrosDeCosto.findAll", query = "SELECT t FROM TblCentrosDeCosto t"),
    @NamedQuery(name = "TblCentrosDeCosto.findById", query = "SELECT t FROM TblCentrosDeCosto t WHERE t.id = :id"),
    @NamedQuery(name = "TblCentrosDeCosto.findByDescripcion", query = "SELECT t FROM TblCentrosDeCosto t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TblCentrosDeCosto.findByCtaCte", query = "SELECT t FROM TblCentrosDeCosto t WHERE t.ctaCte = :ctaCte"),
    @NamedQuery(name = "TblCentrosDeCosto.findByPreferido", query = "SELECT t FROM TblCentrosDeCosto t WHERE t.preferido = :preferido")})
public class TblCentrosDeCosto implements Serializable {

    @OneToMany(mappedBy = "idCentroDeCosto")
    private List<TblEventos> tblEventosList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCentroDeCosto")
    private List<TblAsientos> tblAsientosList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTA_CTE")
    private int ctaCte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREFERIDO")
    private Boolean preferido;
    @JoinColumn(name = "ID_CUENTA_HABER_FACTURA_CREDITO_POR_DEFECTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaHaberFacturaCreditoPorDefecto;
    @JoinColumn(name = "ID_CUENTA_HABER_FACTURA_CONTADO_POR_DEFECTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaHaberFacturaContadoPorDefecto;

    public TblCentrosDeCosto() {
    }

    public TblCentrosDeCosto(Integer id) {
        this.id = id;
    }

    public TblCentrosDeCosto(Integer id, String descripcion, int ctaCte, Boolean preferido) {
        this.id = id;
        this.descripcion = descripcion;
        this.ctaCte = ctaCte;
        this.preferido = preferido;
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

    public int getCtaCte() {
        return ctaCte;
    }

    public void setCtaCte(int ctaCte) {
        this.ctaCte = ctaCte;
    }

    public Boolean getPreferido() {
        return preferido;
    }

    public void setPreferido(Boolean preferido) {
        this.preferido = preferido;
    }

    public TblCuentasContables getIdCuentaHaberFacturaCreditoPorDefecto() {
        return idCuentaHaberFacturaCreditoPorDefecto;
    }

    public void setIdCuentaHaberFacturaCreditoPorDefecto(TblCuentasContables idCuentaHaberFacturaCreditoPorDefecto) {
        this.idCuentaHaberFacturaCreditoPorDefecto = idCuentaHaberFacturaCreditoPorDefecto;
    }

    public TblCuentasContables getIdCuentaHaberFacturaContadoPorDefecto() {
        return idCuentaHaberFacturaContadoPorDefecto;
    }

    public void setIdCuentaHaberFacturaContadoPorDefecto(TblCuentasContables idCuentaHaberFacturaContadoPorDefecto) {
        this.idCuentaHaberFacturaContadoPorDefecto = idCuentaHaberFacturaContadoPorDefecto;
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
        if (!(object instanceof TblCentrosDeCosto)) {
            return false;
        }
        TblCentrosDeCosto other = (TblCentrosDeCosto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblCentrosDeCosto[ id=" + id + " ]";
    }

    @XmlTransient
    public List<TblAsientos> getTblAsientosList() {
        return tblAsientosList;
    }

    public void setTblAsientosList(List<TblAsientos> tblAsientosList) {
        this.tblAsientosList = tblAsientosList;
    }

    @XmlTransient
    public List<TblEventos> getTblEventosList() {
        return tblEventosList;
    }

    public void setTblEventosList(List<TblEventos> tblEventosList) {
        this.tblEventosList = tblEventosList;
    }

}
