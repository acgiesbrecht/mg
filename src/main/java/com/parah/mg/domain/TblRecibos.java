/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import com.parah.mg.domain.eventos.TblEventoTipos;
import com.parah.mg.domain.miembros.TblEntidades;
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
    @NamedQuery(name = "TblRecibos.findByConcepto", query = "SELECT t FROM TblRecibos t WHERE t.concepto = :concepto")})
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
    @Column(name = "MONTO_APORTE")
    private int montoAporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_DONACION")
    private int montoDonacion;
    @JoinColumn(name = "ID_ENTIDAD", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblEntidades idEntidad;
    @JoinColumn(name = "ID_EVENTO_TIPO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblEventoTipos idEventoTipo;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsers idUser;

    public TblRecibos() {
    }

    public TblRecibos(Integer id) {
        this.id = id;
    }

    public TblRecibos(Integer id, Date fechahora, int montoAporte, int montoDonacion) {
        this.id = id;
        this.fechahora = fechahora;
        this.montoAporte = montoAporte;
        this.montoDonacion = montoDonacion;
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

    public int getMontoAporte() {
        return montoAporte;
    }

    public void setMontoAporte(int montoAporte) {
        this.montoAporte = montoAporte;
    }

    public int getMontoDonacion() {
        return montoDonacion;
    }

    public void setMontoDonacion(int montoDonacion) {
        this.montoDonacion = montoDonacion;
    }

    public TblEntidades getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(TblEntidades idEntidad) {
        this.idEntidad = idEntidad;
    }

    public TblEventoTipos getIdEventoTipo() {
        return idEventoTipo;
    }

    public void setIdEventoTipo(TblEventoTipos idEventoTipo) {
        this.idEventoTipo = idEventoTipo;
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
        return "com.parah.mg.domain.TblRecibos[ id=" + id + " ]";
    }

    public Integer getMontoTotal() {
        return montoAporte + montoDonacion;
    }

}
