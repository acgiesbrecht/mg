/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.mg.domain.models;

import com.gnadenheimer.mg.domain.*;
import java.io.Serializable;

public class BalanceGeneral implements Serializable {

    private static final long serialVersionUID = 1L;

    private TblCuentasContables cuentaContable;
    private String nroCuentaIndentada;
    private String nombreCuenta;
    private Long monto;

    public BalanceGeneral() {
    }

    @Override
    public String toString() {
        return "";
    }

    /**
     * @return the cuentaContable
     */
    public TblCuentasContables getCuentaContable() {
        return cuentaContable;
    }

    /**
     * @param cuentaContable the cuentaContable to set
     */
    public void setCuentaContable(TblCuentasContables cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    /**
     * @return the monto
     */
    public Long getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(Long monto) {
        this.monto = monto;
    }

    /**
     * @return the nroCuentaIndentada
     */
    public String getNroCuentaIndentada() {
        return nroCuentaIndentada;
    }

    /**
     * @param nroCuentaIndentada the nroCuentaIndentada to set
     */
    public void setNroCuentaIndentada(String nroCuentaIndentada) {
        this.nroCuentaIndentada = nroCuentaIndentada;
    }

    /**
     * @return the nombreCuenta
     */
    public String getNombreCuenta() {
        return nombreCuenta;
    }

    /**
     * @param nombreCuenta the nombreCuenta to set
     */
    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

}
