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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrian Giesbrecht
 */
@Entity
@Table(name = "TBL_REMATES_CUOTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRematesCuotas.findAll", query = "SELECT t FROM TblRematesCuotas t"),
    @NamedQuery(name = "TblRematesCuotas.findByIdRemate", query = "SELECT t FROM TblRematesCuotas t WHERE t.idRemate = :idRemate"),
    @NamedQuery(name = "TblRematesCuotas.findByFecha1", query = "SELECT t FROM TblRematesCuotas t WHERE t.fecha1 = :fecha1"),
    @NamedQuery(name = "TblRematesCuotas.findByFecha2", query = "SELECT t FROM TblRematesCuotas t WHERE t.fecha2 = :fecha2"),
    @NamedQuery(name = "TblRematesCuotas.findByFecha3", query = "SELECT t FROM TblRematesCuotas t WHERE t.fecha3 = :fecha3"),
    @NamedQuery(name = "TblRematesCuotas.findByFecha4", query = "SELECT t FROM TblRematesCuotas t WHERE t.fecha4 = :fecha4"),
    @NamedQuery(name = "TblRematesCuotas.findByIdUser", query = "SELECT t FROM TblRematesCuotas t WHERE t.idUser = :idUser")})
public class TblRematesCuotas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_REMATE")
    private Integer idRemate;
    @Column(name = "FECHA_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha1;
    @Column(name = "FECHA_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha2;
    @Column(name = "FECHA_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha3;
    @Column(name = "FECHA_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha4;
    @Column(name = "ID_USER")
    private Integer idUser;
    @JoinColumn(name = "ID_REMATE", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TblRemates tblRemates;

    public TblRematesCuotas() {
    }

    public TblRematesCuotas(Integer idRemate) {
        this.idRemate = idRemate;
    }

    public Integer getIdRemate() {
        return idRemate;
    }

    public void setIdRemate(Integer idRemate) {
        this.idRemate = idRemate;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public Date getFecha3() {
        return fecha3;
    }

    public void setFecha3(Date fecha3) {
        this.fecha3 = fecha3;
    }

    public Date getFecha4() {
        return fecha4;
    }

    public void setFecha4(Date fecha4) {
        this.fecha4 = fecha4;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public TblRemates getTblRemates() {
        return tblRemates;
    }

    public void setTblRemates(TblRemates tblRemates) {
        this.tblRemates = tblRemates;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRemate != null ? idRemate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRematesCuotas)) {
            return false;
        }
        TblRematesCuotas other = (TblRematesCuotas) object;
        if ((this.idRemate == null && other.idRemate != null) || (this.idRemate != null && !this.idRemate.equals(other.idRemate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lacreacion.mg.domain.TblRematesCuotas[ idRemate=" + idRemate + " ]";
    }

}
