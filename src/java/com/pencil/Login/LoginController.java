/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pencil.Login;

import com.pencil.InstituteSetup.InstituteSetup;
import com.pencil.InstituteSetup.InstituteSetupService;
import com.pencil.InstituteSetup.InstituteSetupServiceImpl;
import com.pencil.SystemUser.SystemUser;
import java.io.Serializable;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

/**
 *
 * @author Mahfuj
 */
@ManagedBean
@RequestScoped

public class LoginController implements Serializable {

    InstituteSetupService instituteService = new InstituteSetupServiceImpl();
    
     InstituteSetup institute = new InstituteSetup();

    String username, password, role, usrName;

    String instituteId, instituteName, imageBackground;

    String user;

    //String roleName;
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public InstituteSetup getInstitute() {
        return institute;
    }

    public void setInstitute(InstituteSetup institute) {
        this.institute = institute;
    }

    public String getImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
    }
    

    private SystemUser system_user;

    /**
     *
     */
    public static String AUTH_KEY = "";

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {


    }


    public String checkLogin() {
        
         String[] temp;
        temp = username.split("-");
        usrName = temp[0];
        instituteId = temp[1];     

        institute = instituteService.getInstituteById(instituteId);
        
                
        
        FacesContext context = FacesContext.getCurrentInstance();
       
        context.getExternalContext().getSessionMap().put("instituteID",instituteId);
        context.getExternalContext().getSessionMap().put("instituteName",institute.getInstituteFullName());
        context.getExternalContext().getSessionMap().put("instituteLogo",institute.getImgPath());

        LoginDao dao = new LoginDaoImpl();

        boolean flag = dao.isLogin(usrName, password, role);

        if (flag) {
            if (role.equals("Admin")) {
                context.getExternalContext().getSessionMap().put("User_Name", this.getSystem_user().getUserName());

                context.getExternalContext().getSessionMap().put(AUTH_KEY, this.getSystem_user().getRole());

                this.system_user = null;

                return "/admin/index?faces-redirect=true";
            } else if (role.equals("Operator")) {
                context.getExternalContext().getSessionMap().put("User_Name", this.getSystem_user().getUserName());

                context.getExternalContext().getSessionMap().put(AUTH_KEY, this.getSystem_user().getRole());

                this.system_user = null;

                return "/operator/index?faces-redirect=true";

            } else if (role.equals("Teacher")) {
                context.getExternalContext().getSessionMap().put("User_Name", this.getSystem_user().getUserName());

                context.getExternalContext().getSessionMap().put(AUTH_KEY, this.getSystem_user().getRole());

                this.system_user = null;

                return "/teacher/index?faces-redirect=true";

            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid System User.", ""));
        }

        return "Login";
    }

    /**
     *
     * @param event
     */
    public void isAdmin(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();

        if (!"Admin".equals(fc.getExternalContext().getSessionMap().get(LoginController.AUTH_KEY))) {
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();

            nav.performNavigation("/ErrorPage.xhtml?faces-redirect=true");
        }
    }

    /**
     * @return the system_user
     */
    public SystemUser getSystem_user() {
        if (this.system_user == null) {
            this.system_user = new SystemUser();
        }

        return this.system_user;
    }

    /**
     * @param system_user the system_user to set
     */
    public void setSystem_user(SystemUser system_user) {
        this.system_user = system_user;
    }

}
