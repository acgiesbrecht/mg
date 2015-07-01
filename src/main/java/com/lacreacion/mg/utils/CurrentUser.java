/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lacreacion.mg.utils;

import com.lacreacion.mg.domain.TblRoles;
import com.lacreacion.mg.domain.TblUsers;
import java.awt.Component;

public class CurrentUser extends Component {

    private TblUsers user;

    private static CurrentUser currentUser = new CurrentUser();

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private CurrentUser() {
    }

    /* Static 'instance' method */
    public static CurrentUser getInstance() {
        return currentUser;
    }

    /**
     * @return the user
     */
    public TblUsers getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(TblUsers user) {
        this.user = user;
    }

    public boolean hasRole(TblRoles role) {
        return user.getTblRolesCollection().contains(role);
    }

}
