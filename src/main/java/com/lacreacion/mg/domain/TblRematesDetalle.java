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
@Table(name = "TBL_REMATES_DETALLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRematesDetalle.findAll", query = "SELECT t FROM TblRematesDetalle t"),
    @NamedQuery(name = "TblRematesDetalle.findById", query = "SELECT t FROM TblRematesDetalle t WHERE t.id = :id"),
    @NamedQuery(name = "TblRematesDetalle.findByFechahora", query = "SELECT t FROM TblRematesDetalle t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblRematesDetalle.findByObservacion", query = "SELECT t FROM TblRematesDetalle t WHERE t.observacion = :observacion"),
    @NamedQuery(name = "TblRematesDetalle.findByMonto", query = "SELECT t FROM TblRematesDetalle t WHERE t.monto = :monto")})
public class TblRematesDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private int monto;
    @JoinColumn(name = "ID_MIEMBRO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblMiembros idMiembro;
    @JoinColumn(name = "ID_REMATE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblRemates idRemate;
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblRematesCategorias idCategoria;

    public TblRematesDetalle() {
    }

    public TblRematesDetalle(Integer id) {
        this.id = id;
    }

    public TblRematesDetalle(Integer id, Date fechahora, int monto) {
        this.id = id;
        this.fechahora = fechahora;
        this.monto = monto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public TblMiembros getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(TblMiembros idMiembro) {
        this.idMiembro = idMiembro;
    }

    public TblRemates getIdRemate() {
        return idRemate;
    }

    public void setIdRemate(TblRemates idRemate) {
        this.idRemate = idRemate;
    }

    public TblRematesCategorias getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(TblRematesCategorias idCategoria) {
        this.idCategoria = idCategoria;
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
        if (!(object instanceof TblRematesDetalle)) {
            return false;
        }
        TblRematesDetalle other = (TblRematesDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblRematesDetalle[ id=" + id + " ]";
    }

}
