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
    private Integer anomes;

    public AportesPendientes() {
    }

    public AportesPendientes(String nombre, Integer ctacte, Integer anomes) {
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
     * @return the anomes
     */
    public Integer getAnomes() {
        return anomes;
    }

    /**
     * @param anomes the anomes to set
     */
    public void setAnomes(Integer anomes) {
        this.anomes = anomes;
    }

}
