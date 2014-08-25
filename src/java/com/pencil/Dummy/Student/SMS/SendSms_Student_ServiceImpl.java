/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pencil.Dummy.Student.SMS;

import com.pencil.Connection.DB_Connection;
import com.pencil.Dummy.Student.Student_Registration;
import com.pencil.SMS.SMS_Service;
import com.pencil.SMS.SMS_ServiceImpl;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author AR Mamun
 */
public class SendSms_Student_ServiceImpl implements SendSms_Student_Service,Serializable
{   

    /**
     *
     * @param selectedStudentArry
     * @param message
     * @return
     */
    @Override
    public boolean sendSms(List<Student_Registration> selectedStudentArry, String message)
    {       
        DB_Connection o = new DB_Connection();

        Connection con = o.getConnection();
        
        Connection cn = o.getSms_db_Connection();

        PreparedStatement prst = null;
        
        CallableStatement cs = null;
        
        SMS_Service service=new SMS_ServiceImpl();
        
        StringBuilder grdn_cntnoList=new StringBuilder();
        
        try
        {    
            con.setAutoCommit(false);

            prst=con.prepareStatement("insert into student_sms_record values(null,?,?,?,now())");
            
            for(Student_Registration student : selectedStudentArry)
            {
                prst.setString(1, student.getStudentID());

                prst.setString(2, student.getGuardianContactNo());

                prst.setString(3, message);

                prst.addBatch();
                
                grdn_cntnoList.append(student.getGuardianContactNo());
                
                grdn_cntnoList.append(",");
            }
            
            if(grdn_cntnoList.length()>0)
            {
                grdn_cntnoList.setLength(grdn_cntnoList.length()-1);
                
                if(service.sendBulkSms(grdn_cntnoList,message)==200)
                {
                    int[] update = prst.executeBatch();
                    
                    cs = cn.prepareCall("{call smsCntManage(?,?)}");
            
                    cs.setInt(1,update.length);

                    cs.setInt(2,1);

                    cs.execute();

                    con.commit();

                    return true;
                }
            }
        } 
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
        finally
        {
            try
            {
                if (prst != null)
                {
                    prst.close();
                }
                if (cs != null)
                {
                    cs.close();
                }
                if (con != null)
                {
                    con.close();
                }
            } 
            catch (SQLException ex)
            {
                System.out.println(ex);
            }
            
            selectedStudentArry.clear();
            
            message=null;
        }
        
        return false;
    }    
}
