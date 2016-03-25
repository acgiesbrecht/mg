/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrian Giesbrecht
 */
@Entity
@Table(name = "TBL_CUENTAS_CONTABLES_POR_DEFECTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCuentasContablesPorDefecto.findAll", query = "SELECT t FROM TblCuentasContablesPorDefecto t"),
    @NamedQuery(name = "TblCuentasContablesPorDefecto.findById", query = "SELECT t FROM TblCuentasContablesPorDefecto t WHERE t.id = :id"),
    @NamedQuery(name = "TblCuentasContablesPorDefecto.findByIdCuentaACobrar", query = "SELECT t FROM TblCuentasContablesPorDefecto t WHERE t.idCuentaACobrar = :idCuentaACobrar")})
public class TblCuentasContablesPorDefecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "ID_CUENTA_A_COBRAR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaACobrar;
    @JoinColumn(name = "ID_CUENTA_DEBE_COMPRAS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaDebeCompras;
    @JoinColumn(name = "ID_CUENTA_DEBE_DONACIONES", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaDebeDonaciones;
    @JoinColumn(name = "ID_CUENTA_DEBE_APORTES", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaDebeAportes;
    @JoinColumn(name = "ID_CUENTA_HABER_FACTURA_CREDITO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaHaberFacturaCredito;
    @JoinColumn(name = "ID_CUENTA_HABER_FACTURA_CONTADO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCuentasContables idCuentaHaberFacturaContado;

    public TblCuentasContablesPorDefecto() {
    }

    public TblCuentasContablesPorDefecto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TblCuentasContables getIdCuentaACobrar() {
        return idCuentaACobrar;
    }

    public void setIdCuentaACobrar(TblCuentasContables idCuentaACobrar) {
        this.idCuentaACobrar = idCuentaACobrar;
    }

    public TblCuentasContables getIdCuentaDebeCompras() {
        return idCuentaDebeCompras;
    }

    public void setIdCuentaDebeCompras(TblCuentasContables idCuentaDebeCompras) {
        this.idCuentaDebeCompras = idCuentaDebeCompras;
    }

    public TblCuentasContables getIdCuentaDebeDonaciones() {
        return idCuentaDebeDonaciones;
    }

    public void setIdCuentaDebeDonaciones(TblCuentasContables idCuentaDebeDonaciones) {
        this.idCuentaDebeDonaciones = idCuentaDebeDonaciones;
    }

    public TblCuentasContables getIdCuentaDebeAportes() {
        return idCuentaDebeAportes;
    }

    public void setIdCuentaDebeAportes(TblCuentasContables idCuentaDebeAportes) {
        this.idCuentaDebeAportes = idCuentaDebeAportes;
    }

    public TblCuentasContables getIdCuentaHaberFacturaCredito() {
        return idCuentaHaberFacturaCredito;
    }

    public void setIdCuentaHaberFacturaCredito(TblCuentasContables idCuentaHaberFacturaCredito) {
        this.idCuentaHaberFacturaCredito = idCuentaHaberFacturaCredito;
    }

    public TblCuentasContables getIdCuentaHaberFacturaContado() {
        return idCuentaHaberFacturaContado;
    }

    public void setIdCuentaHaberFacturaContado(TblCuentasContables idCuentaHaberFacturaContado) {
        this.idCuentaHaberFacturaContado = idCuentaHaberFacturaContado;
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
        if (!(object instanceof TblCuentasContablesPorDefecto)) {
            return false;
        }
        TblCuentasContablesPorDefecto other = (TblCuentasContablesPorDefecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.parah.mg.domain.TblCuentasContablesPorDefecto[ id=" + id + " ]";
    }

}
