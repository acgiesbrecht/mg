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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adriang
 */
@Entity
@Table(name = "TBL_RECIBOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRecibos.findAll", query = "SELECT t FROM TblRecibos t"),
    @NamedQuery(name = "TblRecibos.findById", query = "SELECT t FROM TblRecibos t WHERE t.id = :id"),
    @NamedQuery(name = "TblRecibos.findByFechahora", query = "SELECT t FROM TblRecibos t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "TblRecibos.findByConcepto", query = "SELECT t FROM TblRecibos t WHERE t.concepto = :concepto"),
    @NamedQuery(name = "TblRecibos.findByMonto", query = "SELECT t FROM TblRecibos t WHERE t.monto = :monto"),
    @NamedQuery(name = "TblRecibos.findByPorcentajeAporte", query = "SELECT t FROM TblRecibos t WHERE t.porcentajeAporte = :porcentajeAporte")})
public class TblRecibos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Size(max = 50)
    @Column(name = "CONCEPTO")
    private String concepto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private int monto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PORCENTAJE_APORTE")
    private int porcentajeAporte;
    @JoinColumn(name = "ID_ENTIDAD", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblEntidades idEntidad;
    @JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblEventos idEvento;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsers idUser;

    public TblRecibos() {
    }

    public TblRecibos(Integer id) {
        this.id = id;
    }

    public TblRecibos(Integer id, Date fechahora, int monto, int porcentajeAporte) {
        this.id = id;
        this.fechahora = fechahora;
        this.monto = monto;
        this.porcentajeAporte = porcentajeAporte;
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

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getPorcentajeAporte() {
        return porcentajeAporte;
    }

    public void setPorcentajeAporte(int porcentajeAporte) {
        this.porcentajeAporte = porcentajeAporte;
    }

    public TblEntidades getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(TblEntidades idEntidad) {
        this.idEntidad = idEntidad;
    }

    public TblEventos getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(TblEventos idEvento) {
        this.idEvento = idEvento;
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
        if (!(object instanceof TblRecibos)) {
            return false;
        }
        TblRecibos other = (TblRecibos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblRecibos[ id=" + id + " ]";
    }

}
