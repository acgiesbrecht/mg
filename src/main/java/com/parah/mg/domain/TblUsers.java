/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import com.parah.mg.domain.miembros.TblEntidades;
import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "TblUsers.findByPassword", query = "SELECT t FROM TblUsers t WHERE t.password = :password"),
    @NamedQuery(name = "TblUsers.findByNombrecompleto", query = "SELECT t FROM TblUsers t WHERE t.nombrecompleto = :nombrecompleto")})
public class TblUsers implements Serializable {

    @OneToMany(mappedBy = "idUser")
    private Collection<TblEventoDetalle> tblEventoDetalleCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<TblRecibos> tblRecibosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<TblEventos> tblEventosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<TblTransferencias> tblTransferenciasCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<TblTimbrados> tblTimbradosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<TblFacturas> tblFacturasCollection;
    @OneToMany(mappedBy = "idUser")
    private Collection<TblEntidades> tblEntidadesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<TblTimbradosAutofacturas> tblTimbradosAutofacturasCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<TblFacturasCompra> tblFacturasCompraCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<TblAutofacturas> tblAutofacturasCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<TblAsientos> tblAsientosCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRECOMPLETO")
    private String nombrecompleto;
    @JoinTable(name = "TBL_GRUPOS_USERS", joinColumns = {
        @JoinColumn(name = "ID_USER", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_GRUPO", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<TblGrupos> tblGruposList;
    @JoinTable(name = "TBL_ROLES_USERS", joinColumns = {
        @JoinColumn(name = "ID_USER", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<TblRoles> tblRolesList;

    public TblUsers() {
    }

    public TblUsers(Integer id) {
        this.id = id;
    }

    public TblUsers(Integer id, String nombre, String password, String nombrecompleto) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.nombrecompleto = nombrecompleto;
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

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    @XmlTransient
    public Collection<TblGrupos> getTblGruposList() {
        return tblGruposList;
    }

    public void setTblGruposList(Collection<TblGrupos> tblGruposList) {
        this.tblGruposList = tblGruposList;
    }

    @XmlTransient
    public Collection<TblRoles> getTblRolesList() {
        return tblRolesList;
    }

    public void setTblRolesList(Collection<TblRoles> tblRolesList) {
        this.tblRolesList = tblRolesList;
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
        return "com.parah.mg.domain.TblUsers[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<TblAsientos> getTblAsientosCollection() {
        return tblAsientosCollection;
    }

    public void setTblAsientosCollection(Collection<TblAsientos> tblAsientosCollection) {
        this.tblAsientosCollection = tblAsientosCollection;
    }

    @XmlTransient
    public Collection<TblFacturasCompra> getTblFacturasCompraCollection() {
        return tblFacturasCompraCollection;
    }

    public void setTblFacturasCompraCollection(Collection<TblFacturasCompra> tblFacturasCompraCollection) {
        this.tblFacturasCompraCollection = tblFacturasCompraCollection;
    }

    @XmlTransient
    public Collection<TblAutofacturas> getTblAutofacturasCollection() {
        return tblAutofacturasCollection;
    }

    public void setTblAutofacturasCollection(Collection<TblAutofacturas> tblAutofacturasCollection) {
        this.tblAutofacturasCollection = tblAutofacturasCollection;
    }

    @XmlTransient
    public Collection<TblTimbradosAutofacturas> getTblTimbradosAutofacturasCollection() {
        return tblTimbradosAutofacturasCollection;
    }

    public void setTblTimbradosAutofacturasCollection(Collection<TblTimbradosAutofacturas> tblTimbradosAutofacturasCollection) {
        this.tblTimbradosAutofacturasCollection = tblTimbradosAutofacturasCollection;
    }

    @XmlTransient
    public Collection<TblTimbrados> getTblTimbradosCollection() {
        return tblTimbradosCollection;
    }

    public void setTblTimbradosCollection(Collection<TblTimbrados> tblTimbradosCollection) {
        this.tblTimbradosCollection = tblTimbradosCollection;
    }

    @XmlTransient
    public Collection<TblFacturas> getTblFacturasCollection() {
        return tblFacturasCollection;
    }

    public void setTblFacturasCollection(Collection<TblFacturas> tblFacturasCollection) {
        this.tblFacturasCollection = tblFacturasCollection;
    }

    @XmlTransient
    public Collection<TblEntidades> getTblEntidadesCollection() {
        return tblEntidadesCollection;
    }

    public void setTblEntidadesCollection(Collection<TblEntidades> tblEntidadesCollection) {
        this.tblEntidadesCollection = tblEntidadesCollection;
    }

    @XmlTransient
    public Collection<TblRecibos> getTblRecibosCollection() {
        return tblRecibosCollection;
    }

    public void setTblRecibosCollection(Collection<TblRecibos> tblRecibosCollection) {
        this.tblRecibosCollection = tblRecibosCollection;
    }

    @XmlTransient
    public Collection<TblEventos> getTblEventosCollection() {
        return tblEventosCollection;
    }

    public void setTblEventosCollection(Collection<TblEventos> tblEventosCollection) {
        this.tblEventosCollection = tblEventosCollection;
    }

    @XmlTransient
    public Collection<TblTransferencias> getTblTransferenciasCollection() {
        return tblTransferenciasCollection;
    }

    public void setTblTransferenciasCollection(Collection<TblTransferencias> tblTransferenciasCollection) {
        this.tblTransferenciasCollection = tblTransferenciasCollection;
    }

    @XmlTransient
    public Collection<TblEventoDetalle> getTblEventoDetalleCollection() {
        return tblEventoDetalleCollection;
    }

    public void setTblEventoDetalleCollection(Collection<TblEventoDetalle> tblEventoDetalleCollection) {
        this.tblEventoDetalleCollection = tblEventoDetalleCollection;
    }

}
