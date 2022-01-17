/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.domain.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author AdrianGiesbrecht
 */
@Entity
public class LibroRes90 implements Serializable {
 
    @Column(name = "tipo_registro")
    private Integer tipoRegistro;
    @Column(name = "tipo_id_proveedor")
    private Integer tipoIdProveedor;
    @Column(name = "ruc")
    private String ruc;
    @Column(name = "razon_social")
    private String razonSocial;
    @Column(name = "tipo_comprobante")
    private Integer tipoComprobante;
    @Column(name = "fechahora")
    private LocalDateTime fechahora;
    @Id
    @Column(name = "nro_timbrado")
    private Integer nroTimbrado;
    @Column(name = "nro")
    @Id
    private String nro;
    @Column(name = "monto_iva10")
    private Long montoIva10 = 0L;
    @Column(name = "monto_iva5")
    private Long montoIva5 = 0L;
    @Column(name = "monto_exentas")
    private Long montoExentas = 0L;
    @Column(name = "monto_total")
    private Long montoTotal = 0L;
    @Column(name = "condicion")
    private Integer condicion;
    @Column(name = "op_moneda_extranjera")
    private String opMonedaExtranjera;
    @Column(name = "imputa_iva")
    private String imputaIva;
    @Column(name = "imputa_ire")
    private String imputaIre;
    @Column(name = "imputa_irp")
    private String imputaIrp;
    @Column(name = "no_imputa")
    private String noImputa;
    @Column(name = "nro_comprobante_asoc")
    private String nroComprobanteAsoc;
    @Column(name = "timbrado_comprobante_asoc")
    private Integer timbradoComprobanteAsoc;
    @Column(name = "anulado")
    private Boolean anulado;

    public Integer getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Integer tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Integer getTipoIdProveedor() {
        return tipoIdProveedor;
    }

    public void setTipoIdProveedor(Integer tipoIdProveedor) {
        this.tipoIdProveedor = tipoIdProveedor;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc.split("-")[0];
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Integer getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(Integer tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    public Integer getNroTimbrado() {
        return nroTimbrado;
    }

    public void setNroTimbrado(Integer nroTimbrado) {
        this.nroTimbrado = nroTimbrado;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public Long getMontoIva10() {
        return montoIva10;
    }

    public void setMontoIva10(Long montoIva10) {
        this.montoIva10 = montoIva10;
    }

    public Long getMontoIva5() {
        return montoIva5;
    }

    public void setMontoIva5(Long montoIva5) {
        this.montoIva5 = montoIva5;
    }

    public Long getMontoExentas() {
        return montoExentas;
    }

    public void setMontoExentas(Long montoExentas) {
        this.montoExentas = montoExentas;
    }

    public Long getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Long montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Integer getCondicion() {
        return condicion;
    }

    public void setCondicion(Integer condicion) {
        this.condicion = condicion;
    }

    public String getOpMonedaExtranjera() {
        return opMonedaExtranjera;
    }

    public void setOpMonedaExtranjera(String opMonedaExtranjera) {
        this.opMonedaExtranjera = opMonedaExtranjera;
    }
   
    
    public String getImputaIva() {
        return imputaIva;
    }

    public void setImputaIva(String imputaIva) {
        this.imputaIva = imputaIva;
    }

    public String getImputaIre() {
        return imputaIre;
    }

    public void setImputaIre(String imputaIre) {
        this.imputaIre = imputaIre;
    }

    public String getImputaIrp() {
        return imputaIrp;
    }

    public void setImputaIrp(String imputaIrp) {
        this.imputaIrp = imputaIrp;
    }

    public String getNoImputa() {
        return noImputa;
    }

    public void setNoImputa(String noImputa) {
        this.noImputa = noImputa;
    }

    public String getNroComprobanteAsoc() {
        return nroComprobanteAsoc;
    }

    public void setNroComprobanteAsoc(String nroComprobanteAsoc) {
        this.nroComprobanteAsoc = nroComprobanteAsoc;
    }

    public Integer getTimbradoComprobanteAsoc() {
        return timbradoComprobanteAsoc;
    }

    public void setTimbradoComprobanteAsoc(Integer timbradoComprobanteAsoc) {
        this.timbradoComprobanteAsoc = timbradoComprobanteAsoc;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    
}
