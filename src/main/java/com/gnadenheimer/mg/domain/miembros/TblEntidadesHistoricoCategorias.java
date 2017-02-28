/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.domain.miembros;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AdminLocal
 */
@Entity
@Table(name = "TBL_ENTIDADES_HISTORICO_CATEGORIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblEntidadesHistoricoCategorias.findAll", query = "SELECT t FROM TblEntidadesHistoricoCategorias t")
    , @NamedQuery(name = "TblEntidadesHistoricoCategorias.findById", query = "SELECT t FROM TblEntidadesHistoricoCategorias t WHERE t.id = :id")
    , @NamedQuery(name = "TblEntidadesHistoricoCategorias.findByAnoMes", query = "SELECT t FROM TblEntidadesHistoricoCategorias t WHERE t.anoMes = :anoMes")})
public class TblEntidadesHistoricoCategorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANO_MES")
    private int anoMes;
    @JoinColumn(name = "ID_ENTIDAD", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblEntidades idEntidad;
    @JoinColumn(name = "ID_CATEGORIA_DE_PAGO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblMiembrosCategoriasDePago idCategoriaDePago;

    public TblEntidadesHistoricoCategorias() {
    }

    public TblEntidadesHistoricoCategorias(Integer id) {
        this.id = id;
    }

    public TblEntidadesHistoricoCategorias(Integer id, int anoMes) {
        this.id = id;
        this.anoMes = anoMes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAnoMes() {
        return anoMes;
    }

    public void setAnoMes(int anoMes) {
        this.anoMes = anoMes;
    }

    public TblEntidades getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(TblEntidades idEntidad) {
        this.idEntidad = idEntidad;
    }

    public TblMiembrosCategoriasDePago getIdCategoriaDePago() {
        return idCategoriaDePago;
    }

    public void setIdCategoriaDePago(TblMiembrosCategoriasDePago idCategoriaDePago) {
        this.idCategoriaDePago = idCategoriaDePago;
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
        if (!(object instanceof TblEntidadesHistoricoCategorias)) {
            return false;
        }
        TblEntidadesHistoricoCategorias other = (TblEntidadesHistoricoCategorias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gnadenheimer.mg.domain.miembros.TblEntidadesHistoricoCategorias[ id=" + id + " ]";
    }

}