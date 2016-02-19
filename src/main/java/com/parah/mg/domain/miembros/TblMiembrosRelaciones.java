/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain.miembros;

import com.parah.mg.domain.TblUsers;
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
 * @author adriang
 */
@Entity
@Table(name = "TBL_MIEMBROS_RELACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMiembrosRelaciones.findAll", query = "SELECT t FROM TblMiembrosRelaciones t"),
    @NamedQuery(name = "TblMiembrosRelaciones.findById", query = "SELECT t FROM TblMiembrosRelaciones t WHERE t.id = :id"),
    @NamedQuery(name = "TblMiembrosRelaciones.findByFechaInicio", query = "SELECT t FROM TblMiembrosRelaciones t WHERE t.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "TblMiembrosRelaciones.findByFechaFin", query = "SELECT t FROM TblMiembrosRelaciones t WHERE t.fechaFin = :fechaFin")})
public class TblMiembrosRelaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @JoinColumn(name = "ID_ENTIDAD_2", referencedColumnName = "ID")
    @ManyToOne
    private TblEntidades idEntidad2;
    @JoinColumn(name = "ID_ENTIDAD_1", referencedColumnName = "ID")
    @ManyToOne
    private TblEntidades idEntidad1;
    @JoinColumn(name = "ID_MIEMBROS_FAMILIA", referencedColumnName = "ID")
    @ManyToOne
    private TblMiembrosFamilias idMiembrosFamilia;
    @JoinColumn(name = "ID_MIEMBROS_RELACIONES_ROL_2", referencedColumnName = "ID")
    @ManyToOne
    private TblMiembrosRelacionesRoles idMiembrosRelacionesRol2;
    @JoinColumn(name = "ID_MIEMBROS_RELACIONES_ROL_1", referencedColumnName = "ID")
    @ManyToOne
    private TblMiembrosRelacionesRoles idMiembrosRelacionesRol1;
    @JoinColumn(name = "ID_MIEMBROS_RELACIONES_TIPO", referencedColumnName = "ID")
    @ManyToOne
    private TblMiembrosRelacionesTipos idMiembrosRelacionesTipo;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne
    private TblUsers idUser;

    public TblMiembrosRelaciones() {
    }

    public TblMiembrosRelaciones(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public TblEntidades getIdEntidad2() {
        return idEntidad2;
    }

    public void setIdEntidad2(TblEntidades idEntidad2) {
        this.idEntidad2 = idEntidad2;
    }

    public TblEntidades getIdEntidad1() {
        return idEntidad1;
    }

    public void setIdEntidad1(TblEntidades idEntidad1) {
        this.idEntidad1 = idEntidad1;
    }

    public TblMiembrosFamilias getIdMiembrosFamilia() {
        return idMiembrosFamilia;
    }

    public void setIdMiembrosFamilia(TblMiembrosFamilias idMiembrosFamilia) {
        this.idMiembrosFamilia = idMiembrosFamilia;
    }

    public TblMiembrosRelacionesRoles getIdMiembrosRelacionesRol2() {
        return idMiembrosRelacionesRol2;
    }

    public void setIdMiembrosRelacionesRol2(TblMiembrosRelacionesRoles idMiembrosRelacionesRol2) {
        this.idMiembrosRelacionesRol2 = idMiembrosRelacionesRol2;
    }

    public TblMiembrosRelacionesRoles getIdMiembrosRelacionesRol1() {
        return idMiembrosRelacionesRol1;
    }

    public void setIdMiembrosRelacionesRol1(TblMiembrosRelacionesRoles idMiembrosRelacionesRol1) {
        this.idMiembrosRelacionesRol1 = idMiembrosRelacionesRol1;
    }

    public TblMiembrosRelacionesTipos getIdMiembrosRelacionesTipo() {
        return idMiembrosRelacionesTipo;
    }

    public void setIdMiembrosRelacionesTipo(TblMiembrosRelacionesTipos idMiembrosRelacionesTipo) {
        this.idMiembrosRelacionesTipo = idMiembrosRelacionesTipo;
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
        if (!(object instanceof TblMiembrosRelaciones)) {
            return false;
        }
        TblMiembrosRelaciones other = (TblMiembrosRelaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblMiembrosRelaciones[ id=" + id + " ]";
    }

}
