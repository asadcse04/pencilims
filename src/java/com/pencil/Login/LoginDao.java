/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pencil.Login;

import com.pencil.SystemUser.SystemUser;

/**
 *
 * @author Mahfuj
 */
public interface LoginDao
{   
    public boolean isLogin(SystemUser obj); 
    
     public boolean isLogin(String userName, String password, String role);   
}
