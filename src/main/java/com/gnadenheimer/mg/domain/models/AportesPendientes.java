/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.domain.models;

import java.io.Serializable;

public class AportesPendientes implements Serializable {

    private String nombre;
    private Integer ctacte;
    private Long importeCompromiso;

    public AportesPendientes() {
    }

    public AportesPendientes(String nombre, Integer ctacte, Long importeCompromiso) {
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
    public Integer getCtacte() {
        return ctacte;
    }

    /**
     * @param ctacte the ctacte to set
     */
    public void setCtacte(Integer ctacte) {
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

}
