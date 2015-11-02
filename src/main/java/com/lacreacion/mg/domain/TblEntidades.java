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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author adriang
 */
@Entity
@Table(name = "TBL_ENTIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblEntidades.findAll", query = "SELECT t FROM TblEntidades t"),
    @NamedQuery(name = "TblEntidades.findById", query = "SELECT t FROM TblEntidades t WHERE t.id = :id"),
    @NamedQuery(name = "TblEntidades.findByNombres", query = "SELECT t FROM TblEntidades t WHERE t.nombres = :nombres"),
    @NamedQuery(name = "TblEntidades.findByApellidos", query = "SELECT t FROM TblEntidades t WHERE t.apellidos = :apellidos"),
    @NamedQuery(name = "TblEntidades.findByRuc", query = "SELECT t FROM TblEntidades t WHERE t.ruc = :ruc"),
    @NamedQuery(name = "TblEntidades.findByCtacte", query = "SELECT t FROM TblEntidades t WHERE t.ctacte = :ctacte"),
    @NamedQuery(name = "TblEntidades.findByDomicilio", query = "SELECT t FROM TblEntidades t WHERE t.domicilio = :domicilio"),
    @NamedQuery(name = "TblEntidades.findByBox", query = "SELECT t FROM TblEntidades t WHERE t.box = :box"),
    @NamedQuery(name = "TblEntidades.findByAporteMensual", query = "SELECT t FROM TblEntidades t WHERE t.aporteMensual = :aporteMensual")})
public class TblEntidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "RUC")
    private Integer ruc;
    @Column(name = "CTACTE")
    private Integer ctacte;
    @Size(max = 50)
    @Column(name = "DOMICILIO")
    private String domicilio;
    @Column(name = "BOX")
    private Integer box;
    @Basic(optional = false)
    @NotNull
    @Column(name = "APORTE_MENSUAL")
    private int aporteMensual;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntidad")
    private List<TblEventoDetalle> tblEventoDetalleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntidad")
    private List<TblRecibos> tblRecibosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntidad")
    private List<TblTransferencias> tblTransferenciasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntidad")
    private List<TblFacturas> tblFacturasList;
    @JoinColumn(name = "ID_FORMA_DE_PAGO_PREFERIDA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblFormasDePago idFormaDePagoPreferida;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne
    private TblUsers idUser;

    public TblEntidades() {
    }

    public TblEntidades(Integer id) {
        this.id = id;
    }

    public TblEntidades(Integer id, String nombres, String apellidos, int aporteMensual) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.aporteMensual = aporteMensual;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
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

    @XmlTransient
    public List<TblFacturas> getTblFacturasList() {
        return tblFacturasList;
    }

    public void setTblFacturasList(List<TblFacturas> tblFacturasList) {
        this.tblFacturasList = tblFacturasList;
    }

    public TblFormasDePago getIdFormaDePagoPreferida() {
        return idFormaDePagoPreferida;
    }

    public void setIdFormaDePagoPreferida(TblFormasDePago idFormaDePagoPreferida) {
        this.idFormaDePagoPreferida = idFormaDePagoPreferida;
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
        if (!(object instanceof TblEntidades)) {
            return false;
        }
        TblEntidades other = (TblEntidades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }

}
