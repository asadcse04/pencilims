/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pencil.Login;



import com.pencil.SystemUser.SystemUser;
import com.pencil.Connection.DB_Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mahfuj
 */
public class LoginDaoImpl implements LoginDao {

    /**
     *
     * @param userName
     * @param password
     * @param role
     * @return
     */
    @Override
    public boolean isLogin(String userName, String password, String role)
    {
       
        DB_Connection o=new DB_Connection();
        
        Connection con=o.getConnection();
        
        PreparedStatement pst=null;
        
        ResultSet rs=null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        try
        {
         
            pst = con.prepareStatement("select u.user_name, user_pass, role_name  from users u, user_roles r where  r.user_name=u.user_name\n" +
                                        " and u.user_name=? and user_pass=? and role_name=?;");
            
            pst.setString(1,userName);
            
            pst.setString(2,password);
            
            pst.setString(3,role);
            
        //    pst.setBoolean(4,true);
            
            rs = pst.executeQuery();
            
            while(rs.next())   
            {
                context.getExternalContext().getSessionMap().put("UserID",rs.getInt(1));
                
                return true;
            }

        }
        
        catch(SQLException e)
        {
           System.out.println(e); 
        }
        finally
        {
            try
            {
                if(rs!=null) 
                { 
                    rs.close(); 
                } 
                if(pst!=null) 
                { 
                    pst.close(); 
                } 
                if(con!=null) 
                { 
                    con.close(); 
                } 
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }
            
            System.out.println("Hello user:"+userName);
            
              
        }
        
        return false;
    } 

    @Override
    public boolean isLogin(SystemUser obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
