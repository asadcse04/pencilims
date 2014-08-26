/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pencil.setting.user.AssignStuffType;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author SHOHUG-SQ
 */
@ManagedBean
@ViewScoped
public class AssignStuffTypeController {

    private String rePassword;

    private AssignStuffType assignUserType;

    private List<AssignStuffType> userTypeList;

    AssignStuffTypeService dao = new AssignStuffTypeServiceImpl();
    
/////////////////////////////////////////////////////////////
    
    public void insertStuffType() 
    {
        FacesContext context = FacesContext.getCurrentInstance();

        if (dao.addUserType(this.assignUserType)) 
        {
            this.userTypeList = dao.userList();

            this.assignUserType = null;

            context.addMessage(null, new FacesMessage("Successful", "Insert information."));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to insert info!", ""));
        }
    }
    
/////////////////////////////////////////////////////////////////
    
////////////////////////////////////////////////////////////////////
    public AssignStuffTypeController()
    {
        this.userTypeList = dao.userList();
    }

    public AssignStuffType getAssignUserType() {
        if (this.assignUserType == null) {
            this.assignUserType = new AssignStuffType();
        }
        return assignUserType;
    }

    public void setAssignUserType(AssignStuffType assignUserType) {
        this.assignUserType = assignUserType;
    }

    public List<AssignStuffType> getUserTypeList() {
        return userTypeList;
    }

    public void setUserTypeList(List<AssignStuffType> userTypeList) {
        this.userTypeList = userTypeList;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

}
