/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.domain;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adriang
 */
@Entity
@Table(name = "TBL_COLECTAS_DETALLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblColectasDetalle.findAll", query = "SELECT t FROM TblColectasDetalle t"),
    @NamedQuery(name = "TblColectasDetalle.findById", query = "SELECT t FROM TblColectasDetalle t WHERE t.id = :id"),
    @NamedQuery(name = "TblColectasDetalle.findByMonto", query = "SELECT t FROM TblColectasDetalle t WHERE t.monto = :monto")})
public class TblColectasDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private int monto;
    @JoinColumn(name = "ID_COLECTA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblColectas idColecta;
    @JoinColumn(name = "ID_MIEMBRO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblMiembros idMiembro;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsers idUser;

    public TblColectasDetalle() {
    }

    public TblColectasDetalle(Integer id) {
        this.id = id;
    }

    public TblColectasDetalle(Integer id, int monto) {
        this.id = id;
        this.monto = monto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public TblColectas getIdColecta() {
        return idColecta;
    }

    public void setIdColecta(TblColectas idColecta) {
        this.idColecta = idColecta;
    }

    public TblMiembros getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(TblMiembros idMiembro) {
        this.idMiembro = idMiembro;
    }

    public TblUsers getIdUser() {
        return idUser;
    }

    public void setIdUser(TblUsers idUser) {
        this.idUser = idUser;
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
        if (!(object instanceof TblColectasDetalle)) {
            return false;
        }
        TblColectasDetalle other = (TblColectasDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblColectasDetalle[ id=" + id + " ]";
    }

}
