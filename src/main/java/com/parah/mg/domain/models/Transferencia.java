/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parah.mg.domain.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrian Giesbrecht
 */
@Entity
@Table(name = "TRANSFERENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transferencia.findAll", query = "SELECT t FROM Transferencia t"),
    @NamedQuery(name = "Transferencia.findById", query = "SELECT t FROM Transferencia t WHERE t.id = :id"),
    @NamedQuery(name = "Transferencia.findByFechahora", query = "SELECT t FROM Transferencia t WHERE t.fechahora = :fechahora"),
    @NamedQuery(name = "Transferencia.findByNombre", query = "SELECT t FROM Transferencia t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Transferencia.findByConcepto", query = "SELECT t FROM Transferencia t WHERE t.concepto = :concepto"),
    @NamedQuery(name = "Transferencia.findByMonto", query = "SELECT t FROM Transferencia t WHERE t.monto = :monto")})
public class Transferencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Basic(optional = false)
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CONCEPTO")
    private String concepto;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private int monto;

    public Transferencia() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }
    
}
