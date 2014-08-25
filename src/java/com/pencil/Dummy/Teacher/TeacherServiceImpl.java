/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pencil.Dummy.Teacher;

import com.pencil.Connection.DB_Connection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class TeacherServiceImpl implements Serializable,TeacherService
{

    /**
     *
     * @param tchr
     * @return
     */
    @Override
    public boolean saveTeacher(Teacher tchr) 
    {
        DB_Connection o=new DB_Connection(); 
       
        Connection con=o.getConnection();
        
        PreparedStatement  prst = null;
        
        ResultSet rs=null;
        
        int teacherID=0;
         
        try
        {
            con.setAutoCommit(false);
            
            Long.parseLong(tchr.getContactNo());
            
            
            
            prst = con.prepareStatement("SELECT max(TeacherID) as TeacherID FROM teacher");
           
            rs = prst.executeQuery();

            if (rs.next()) 
            {
                teacherID = rs.getInt("TeacherID");
            }
            if (teacherID ==0)
            {
                teacherID = 20000;  
            }
            
            
            tchr.setTeacherID(++teacherID);
            
            
            
            prst = con.prepareStatement("insert into teacher values(?,?,?,?,?,?,?,?,?,?,?,?)");
               
            prst.setInt(1,tchr.getTeacherID());
             
            prst.setString(2,tchr.getName());
                 
            prst.setString(3,tchr.getGender());
            
            prst.setDate(4,new java.sql.Date(tchr.getDob().getTime()));
            
            prst.setString(5,tchr.getFatherName());
            
            prst.setString(6,tchr.getMotherName());
            
            prst.setString(7,tchr.getQualification());
            
            prst.setString(8,tchr.getPassedFrom());
            
            prst.setString(9,tchr.getBloodGroup());
            
            prst.setString(10,tchr.getMaritalStatus());
            
            prst.setString(11,tchr.getReligion());

            prst.setString(12,tchr.getTeacherID()+"_"+tchr.getImgPath());
            
            prst.execute();
            
            prst.close();
            
            
            prst = con.prepareStatement("insert into teacher_contact_info values(?,?,?,?,(select max(TeacherID) from teacher))");
            
            prst.setString(1,tchr.getContactNo());
            
            prst.setString(2,tchr.getEmail());
            
            prst.setString(3,tchr.getPresentAddress());
            
            prst.setString(4,tchr.getPermanentAddress());
            
            prst.execute();
           
            con.commit();

            return true;    
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        catch(NumberFormatException n)
        {
            System.out.println(n);
        }
        finally
        {
            try
            {
                if(rs!=null)
                {
                   rs.close();
                } 
                if(prst!=null)
                {
                    prst.close();
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
            
           // tchr=null;
        }
        
        return false;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<Teacher> teacherList() 
    {
        DB_Connection o=new DB_Connection(); 
       
        Connection con=o.getConnection();
        
        PreparedStatement prst=null;
        
        ResultSet rs=null;
        
        List<Teacher> tchrList=new ArrayList<Teacher>();
        
        String sql="select t.TeacherID,t.TeacherName,t.Gender,t.DOB,t.FatherName,t.MotherName,t.Qualification,t.PassedFrom,"
                + "t.BloodGroup,t.MaritalStatus,t.Religion,t.ImgPath,tc.ContactNo,tc.Email,tc.PresentAddress,tc.PermanentAddress"
                + " from teacher t,teacher_contact_info tc where tc.TeacherID=t.TeacherID";
        
        try
        {
           prst = con.prepareStatement(sql);
            
           rs = prst.executeQuery();
            
           while(rs.next())
           {
               tchrList.add(new Teacher(rs.getInt("t.TeacherID"),rs.getString("t.TeacherName"),rs.getString("t.Gender"),rs.getDate("t.DOB"),rs.getString("t.FatherName"),
                       
                       rs.getString("t.MotherName"),rs.getString("t.Qualification"),rs.getString("t.PassedFrom"),rs.getString("t.BloodGroup"),rs.getString("t.MaritalStatus"),rs.getString("t.Religion"),rs.getString("t.ImgPath"),rs.getString("tc.ContactNo"),rs.getString("tc.Email"),rs.getString("tc.PresentAddress"),rs.getString("tc.PermanentAddress")));
           }
        } 
        catch (SQLException e)
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
                if(prst!=null)
                {
                    prst.close();
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
        } 
        
        return tchrList;
    }

    /**
     *
     * @param tchr
     * @return
     */
    @Override
    public boolean updateTeacher(Teacher tchr)
    {
        DB_Connection o=new DB_Connection(); 
       
        Connection con=o.getConnection();

        PreparedStatement prst = null;

        try
        {
            con.setAutoCommit(false);

            Long.parseLong(tchr.getContactNo());

            prst = con.prepareStatement("update teacher set TeacherName=?, Gender=?, DOB=?, FatherName=?, MotherName=?, Qualification=?, PassedFrom=?, BloodGroup=?, MaritalStatus=?, Religion=?, ImgPath=? where TeacherID=?");

            prst.setString(1, tchr.getName());

            prst.setString(2, tchr.getGender());

            prst.setDate(3, new java.sql.Date(tchr.getDob().getTime()));

            prst.setString(4, tchr.getFatherName());

            prst.setString(5, tchr.getMotherName());

            prst.setString(6, tchr.getQualification());

            prst.setString(7, tchr.getPassedFrom());

            prst.setString(8, tchr.getBloodGroup());

            prst.setString(9, tchr.getMaritalStatus());

            prst.setString(10, tchr.getReligion());

            prst.setString(11, tchr.getImgPath());

            prst.setInt(12, tchr.getTeacherID());
            
            prst.executeUpdate();
            
            prst.close();
            
            
            
            prst = con.prepareStatement("update teacher_contact_info set ContactNo=?, Email=?, PresentAddress=?, PermanentAddress=? where TeacherID=?");

            prst.setString(1, tchr.getContactNo());

            prst.setString(2, tchr.getEmail());

            prst.setString(3, tchr.getPresentAddress());

            prst.setString(4, tchr.getPermanentAddress());

            prst.setInt(5, tchr.getTeacherID());
            
            prst.executeUpdate();

            prst.close();

            con.commit();
            
            return true;
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        finally
        {
            try
            {
                if (prst != null)
                {
                    prst.close();
                }
                if (con != null) 
                {
                    con.close();
                }
            }
            catch(SQLException ex)
            {
                System.out.println(ex);
            }
            
            tchr=null;
        }
        
        return false;
    }
}

    

