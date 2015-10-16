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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TBL_MIEMBROS", catalog = "", schema = "MG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMiembros.findAll", query = "SELECT t FROM TblMiembros t"),
    @NamedQuery(name = "TblMiembros.findById", query = "SELECT t FROM TblMiembros t WHERE t.id = :id"),
    @NamedQuery(name = "TblMiembros.findByNombre", query = "SELECT t FROM TblMiembros t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblMiembros.findByRuc", query = "SELECT t FROM TblMiembros t WHERE t.ruc = :ruc"),
    @NamedQuery(name = "TblMiembros.findByCtacte", query = "SELECT t FROM TblMiembros t WHERE t.ctacte = :ctacte"),
    @NamedQuery(name = "TblMiembros.findByDomicilio", query = "SELECT t FROM TblMiembros t WHERE t.domicilio = :domicilio"),
    @NamedQuery(name = "TblMiembros.findByBox", query = "SELECT t FROM TblMiembros t WHERE t.box = :box"),
    @NamedQuery(name = "TblMiembros.findByAporteMensual", query = "SELECT t FROM TblMiembros t WHERE t.aporteMensual = :aporteMensual")})
public class TblMiembros implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMiembro")
    private List<TblEventoDetalle> tblEventoDetalleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMiembro")
    private List<TblFacturas> tblFacturasList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "RUC")
    private Integer ruc;
    @Column(name = "CTACTE")
    private Integer ctacte;
    @Column(name = "DOMICILIO")
    private String domicilio;
    @Column(name = "BOX")
    private Integer box;
    @Basic(optional = false)
    @Column(name = "APORTE_MENSUAL")
    private int aporteMensual;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne
    private TblUsers idUser;

    public TblMiembros() {
    }

    public TblMiembros(Integer id) {
        this.id = id;
    }

    public TblMiembros(Integer id, String nombre, int aporteMensual) {
        this.id = id;
        this.nombre = nombre;
        this.aporteMensual = aporteMensual;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getRuc() {
        return ruc;
    }

    public void setRuc(Integer ruc) {
        this.ruc = ruc;
    }

    public Integer getCtacte() {
        return ctacte;
    }

    public void setCtacte(Integer ctacte) {
        this.ctacte = ctacte;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Integer getBox() {
        return box;
    }

    public void setBox(Integer box) {
        this.box = box;
    }

    public int getAporteMensual() {
        return aporteMensual;
    }

    public void setAporteMensual(int aporteMensual) {
        this.aporteMensual = aporteMensual;
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
        if (!(object instanceof TblMiembros)) {
            return false;
        }
        TblMiembros other = (TblMiembros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @XmlTransient
    public List<TblFacturas> getTblFacturasList() {
        return tblFacturasList;
    }

    public void setTblFacturasList(List<TblFacturas> tblFacturasList) {
        this.tblFacturasList = tblFacturasList;
    }

    @XmlTransient
    public List<TblEventoDetalle> getTblEventoDetalleList() {
        return tblEventoDetalleList;
    }

    public void setTblEventoDetalleList(List<TblEventoDetalle> tblEventoDetalleList) {
        this.tblEventoDetalleList = tblEventoDetalleList;
    }

}
