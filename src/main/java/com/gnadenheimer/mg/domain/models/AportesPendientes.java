/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.domain.models;

import java.io.Serializable;

public class AportesPendientes implements Serializable {

    private String nombre;
    private String ctacte;
    private Long importeCompromiso;
    private Long importePagos;

    public AportesPendientes() {
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the ctacte
     */
    public String getCtacte() {
        return ctacte;
    }

    /**
     * @param ctacte the ctacte to set
     */
    public void setCtacte(String ctacte) {
        this.ctacte = ctacte;
    }

    /**
     * @return the compromiso
     */
    public Long getImporteCompromiso() {
        return importeCompromiso;
    }

    /**
     * @param compromiso the compromiso to set
     */
    public void setImporteCompromiso(Long importeCompromiso) {
        this.importeCompromiso = importeCompromiso;
    }

    /**
     * @return the importePagos
     */
    public Long getImportePagos() {
        return importePagos;
    }

    /**
     * @param importePagos the importePagos to set
     */
    public void setImportePagos(Long importePagos) {
        this.importePagos = importePagos;
    }

}
