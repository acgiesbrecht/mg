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
    @NamedQuery(name = "TblEntidades.findByIsMiembroActivo", query = "SELECT t FROM TblEntidades t WHERE t.isMiembroActivo = :isMiembroActivo"),
    @NamedQuery(name = "TblEntidades.findByAporteMensual", query = "SELECT t FROM TblEntidades t WHERE t.aporteMensual = :aporteMensual"),
    @NamedQuery(name = "TblEntidades.findByFechaNacimiento", query = "SELECT t FROM TblEntidades t WHERE t.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "TblEntidades.findByFechaBautismo", query = "SELECT t FROM TblEntidades t WHERE t.fechaBautismo = :fechaBautismo"),
    @NamedQuery(name = "TblEntidades.findByFechaEntradaCongregacion", query = "SELECT t FROM TblEntidades t WHERE t.fechaEntradaCongregacion = :fechaEntradaCongregacion"),
    @NamedQuery(name = "TblEntidades.findByFechaSalidaCongregacion", query = "SELECT t FROM TblEntidades t WHERE t.fechaSalidaCongregacion = :fechaSalidaCongregacion"),
    @NamedQuery(name = "TblEntidades.findByFechaDefuncion", query = "SELECT t FROM TblEntidades t WHERE t.fechaDefuncion = :fechaDefuncion")})
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
    @Column(name = "IS_MIEMBRO_ACTIVO")
    private Boolean isMiembroActivo;
    @Column(name = "APORTE_MENSUAL")
    private Integer aporteMensual;
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "FECHA_BAUTISMO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBautismo;
    @Column(name = "FECHA_ENTRADA_CONGREGACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntradaCongregacion;
    @Column(name = "FECHA_SALIDA_CONGREGACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalidaCongregacion;
    @Column(name = "FECHA_DEFUNCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDefuncion;
    @OneToMany(mappedBy = "idEntidad2")
    private List<TblMiembrosRelaciones> tblMiembrosRelacionesList;
    @OneToMany(mappedBy = "idEntidad1")
    private List<TblMiembrosRelaciones> tblMiembrosRelacionesList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntidad")
    private List<TblEventoDetalle> tblEventoDetalleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntidad")
    private List<TblRecibos> tblRecibosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntidad")
    private List<TblTransferencias> tblTransferenciasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntidad")
    private List<TblFacturas> tblFacturasList;
    @JoinColumn(name = "ID_AREA_SERVICIO_EN_IGLESIA", referencedColumnName = "ID")
    @ManyToOne
    private TblAreasServicioEnIglesia idAreaServicioEnIglesia;
    @JoinColumn(name = "ID_FORMA_DE_PAGO_PREFERIDA", referencedColumnName = "ID")
    @ManyToOne
    private TblFormasDePago idFormaDePagoPreferida;
    @JoinColumn(name = "ID_MIEMBROS_ALERGIA", referencedColumnName = "ID")
    @ManyToOne
    private TblMiembrosAlergias idMiembrosAlergia;
    @JoinColumn(name = "ID_MIEMBROS_CATEGORIA_DE_PAGO", referencedColumnName = "ID")
    @ManyToOne
    private TblMiembrosCategoriasDePago idMiembrosCategoriaDePago;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne
    private TblUsers idUser;

    public TblEntidades() {
    }

    public TblEntidades(Integer id) {
        this.id = id;
    }

    public TblEntidades(Integer id, String nombres, String apellidos, Boolean isMiembroActivo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.isMiembroActivo = isMiembroActivo;
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

    public Boolean getIsMiembroActivo() {
        return isMiembroActivo;
    }

    public void setIsMiembroActivo(Boolean isMiembroActivo) {
        this.isMiembroActivo = isMiembroActivo;
    }

    public Integer getAporteMensual() {
        return aporteMensual;
    }

    public void setAporteMensual(Integer aporteMensual) {
        this.aporteMensual = aporteMensual;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaBautismo() {
        return fechaBautismo;
    }

    public void setFechaBautismo(Date fechaBautismo) {
        this.fechaBautismo = fechaBautismo;
    }

    public Date getFechaEntradaCongregacion() {
        return fechaEntradaCongregacion;
    }

    public void setFechaEntradaCongregacion(Date fechaEntradaCongregacion) {
        this.fechaEntradaCongregacion = fechaEntradaCongregacion;
    }

    public Date getFechaSalidaCongregacion() {
        return fechaSalidaCongregacion;
    }

    public void setFechaSalidaCongregacion(Date fechaSalidaCongregacion) {
        this.fechaSalidaCongregacion = fechaSalidaCongregacion;
    }

    public Date getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(Date fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    @XmlTransient
    public List<TblMiembrosRelaciones> getTblMiembrosRelacionesList() {
        return tblMiembrosRelacionesList;
    }

    public void setTblMiembrosRelacionesList(List<TblMiembrosRelaciones> tblMiembrosRelacionesList) {
        this.tblMiembrosRelacionesList = tblMiembrosRelacionesList;
    }

    @XmlTransient
    public List<TblMiembrosRelaciones> getTblMiembrosRelacionesList1() {
        return tblMiembrosRelacionesList1;
    }

    public void setTblMiembrosRelacionesList1(List<TblMiembrosRelaciones> tblMiembrosRelacionesList1) {
        this.tblMiembrosRelacionesList1 = tblMiembrosRelacionesList1;
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

    public TblAreasServicioEnIglesia getIdAreaServicioEnIglesia() {
        return idAreaServicioEnIglesia;
    }

    public void setIdAreaServicioEnIglesia(TblAreasServicioEnIglesia idAreaServicioEnIglesia) {
        this.idAreaServicioEnIglesia = idAreaServicioEnIglesia;
    }

    public TblFormasDePago getIdFormaDePagoPreferida() {
        return idFormaDePagoPreferida;
    }

    public void setIdFormaDePagoPreferida(TblFormasDePago idFormaDePagoPreferida) {
        this.idFormaDePagoPreferida = idFormaDePagoPreferida;
    }

    public TblMiembrosAlergias getIdMiembrosAlergia() {
        return idMiembrosAlergia;
    }

    public void setIdMiembrosAlergia(TblMiembrosAlergias idMiembrosAlergia) {
        this.idMiembrosAlergia = idMiembrosAlergia;
    }

    public TblMiembrosCategoriasDePago getIdMiembrosCategoriaDePago() {
        return idMiembrosCategoriaDePago;
    }

    public void setIdMiembrosCategoriaDePago(TblMiembrosCategoriasDePago idMiembrosCategoriaDePago) {
        this.idMiembrosCategoriaDePago = idMiembrosCategoriaDePago;
    }

    public TblUsers getIdUser() {
        return idUser;
    }

    public void setIdUser(TblUsers idUser) {
        this.idUser = idUser;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
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
