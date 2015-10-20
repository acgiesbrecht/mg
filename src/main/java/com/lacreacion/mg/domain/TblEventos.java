/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.domain;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adrian Giesbrecht
 */
@Entity
@Table(name = "TBL_EVENTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblEventos.findAll", query = "SELECT t FROM TblEventos t"),
    @NamedQuery(name = "TblEventos.findById", query = "SELECT t FROM TblEventos t WHERE t.id = :id"),
    @NamedQuery(name = "TblEventos.findByFecha", query = "SELECT t FROM TblEventos t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TblEventos.findByDescripcion", query = "SELECT t FROM TblEventos t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TblEventos.findByPorcentajeAporte", query = "SELECT t FROM TblEventos t WHERE t.porcentajeAporte = :porcentajeAporte")})
public class TblEventos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private List<TblRecibos> tblRecibosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private List<TblTransferencias> tblTransferenciasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private List<TblEventoDetalle> tblEventoDetalleList;
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
    @Basic(optional = false)
    @Column(name = "PORCENTAJE_APORTE")
    private int porcentajeAporte;
    @JoinColumn(name = "ID_EVENTO_TIPO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblEventoTipos idEventoTipo;
    @JoinColumn(name = "ID_GRUPO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblGrupos idGrupo;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsers idUser;

    public TblEventos() {
    }

    public TblEventos(Integer id) {
        this.id = id;
    }

    public TblEventos(Integer id, Date fecha, int porcentajeAporte) {
        this.id = id;
        this.fecha = fecha;
        this.porcentajeAporte = porcentajeAporte;
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

    public int getPorcentajeAporte() {
        return porcentajeAporte;
    }

    public void setPorcentajeAporte(int porcentajeAporte) {
        this.porcentajeAporte = porcentajeAporte;
    }

    public TblEventoTipos getIdEventoTipo() {
        return idEventoTipo;
    }

    public void setIdEventoTipo(TblEventoTipos idEventoTipo) {
        this.idEventoTipo = idEventoTipo;
    }

    public TblGrupos getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(TblGrupos idGrupo) {
        this.idGrupo = idGrupo;
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
        if (!(object instanceof TblEventos)) {
            return false;
        }
        TblEventos other = (TblEventos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblEventos[ id=" + id + " ]";
    }

    @XmlTransient
    public List<TblEventoDetalle> getTblEventoDetalleList() {
        return tblEventoDetalleList;
    }

    public void setTblEventoDetalleList(List<TblEventoDetalle> tblEventoDetalleList) {
        this.tblEventoDetalleList = tblEventoDetalleList;
    }

    @XmlTransient
    public List<TblRecibos> getTblRecibosList() {
        return tblRecibosList;
    }

    public void setTblRecibosList(List<TblRecibos> tblRecibosList) {
        this.tblRecibosList = tblRecibosList;
    }

    @XmlTransient
    public List<TblTransferencias> getTblTransferenciasList() {
        return tblTransferenciasList;
    }

    public void setTblTransferenciasList(List<TblTransferencias> tblTransferenciasList) {
        this.tblTransferenciasList = tblTransferenciasList;
    }

}
