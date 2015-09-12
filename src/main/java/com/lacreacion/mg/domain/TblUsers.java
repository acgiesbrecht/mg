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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "TBL_USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUsers.findAll", query = "SELECT t FROM TblUsers t"),
    @NamedQuery(name = "TblUsers.findById", query = "SELECT t FROM TblUsers t WHERE t.id = :id"),
    @NamedQuery(name = "TblUsers.findByNombre", query = "SELECT t FROM TblUsers t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TblUsers.findByPassword", query = "SELECT t FROM TblUsers t WHERE t.password = :password")})
public class TblUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @ManyToMany(mappedBy = "tblUsersList")
    private List<TblRoles> tblRolesList;
    @JoinTable(name = "TBL_GRUPOS_USERS", joinColumns = {
        @JoinColumn(name = "ID_USER", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_GRUPO", referencedColumnName = "ID")})
    @ManyToMany
    private List<TblGrupos> tblGruposList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<TblColectas> tblColectasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<TblRecibos> tblRecibosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<TblTransferencias> tblTransferenciasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<TblRemates> tblRematesList;
    @OneToMany(mappedBy = "idUser")
    private List<TblMiembros> tblMiembrosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<TblColectasDetalle> tblColectasDetalleList;

    public TblUsers() {
    }

    public TblUsers(Integer id) {
        this.id = id;
    }

    public TblUsers(Integer id, String nombre, String password) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<TblRoles> getTblRolesList() {
        return tblRolesList;
    }

    public void setTblRolesList(List<TblRoles> tblRolesList) {
        this.tblRolesList = tblRolesList;
    }

    @XmlTransient
    public List<TblGrupos> getTblGruposList() {
        return tblGruposList;
    }

    public void setTblGruposList(List<TblGrupos> tblGruposList) {
        this.tblGruposList = tblGruposList;
    }

    @XmlTransient
    public List<TblColectas> getTblColectasList() {
        return tblColectasList;
    }

    public void setTblColectasList(List<TblColectas> tblColectasList) {
        this.tblColectasList = tblColectasList;
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

    @XmlTransient
    public List<TblRemates> getTblRematesList() {
        return tblRematesList;
    }

    public void setTblRematesList(List<TblRemates> tblRematesList) {
        this.tblRematesList = tblRematesList;
    }

    @XmlTransient
    public List<TblMiembros> getTblMiembrosList() {
        return tblMiembrosList;
    }

    public void setTblMiembrosList(List<TblMiembros> tblMiembrosList) {
        this.tblMiembrosList = tblMiembrosList;
    }

    @XmlTransient
    public List<TblColectasDetalle> getTblColectasDetalleList() {
        return tblColectasDetalleList;
    }

    public void setTblColectasDetalleList(List<TblColectasDetalle> tblColectasDetalleList) {
        this.tblColectasDetalleList = tblColectasDetalleList;
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
        if (!(object instanceof TblUsers)) {
            return false;
        }
        TblUsers other = (TblUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
