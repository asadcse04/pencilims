/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pencil.Student.AttendanceReport;

import com.pencil.Connection.DB_Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author salim
 */
public class AttendanceReportServiceImpl implements AttendanceReportService
{
    @Override
    public List<AttendanceReport> sectionWisePsnAbs_List(Date currentDate)
    {
        DB_Connection o = new DB_Connection();
  
        Connection con = o.getConnection();

        PreparedStatement prst = null;

        ResultSet rs = null;

        List<AttendanceReport> stdAttendance_List = new ArrayList<AttendanceReport>();
        
        String query = "select s.shiftname, d.departmentname, c.classname, sc.sectionname,"
                + " (select count(studentid) from student_attendence where attendancedate=? and"
                + " studentid in(select studentid from student_identification where classconfigid=cg.scconfigid)) total,"
                + " (select count(studentid) from student_attendence where absent=false and attendancedate=?"
                + " and studentid in(select studentid from student_identification where classconfigid=cg.scconfigid)) present,"
                + " (select count(studentid) from student_attendence where absent=true and attendancedate=?"
                + " and studentid in(select studentid from student_identification where classconfigid=cg.scconfigid)) absent"
                + " from shift s, department d, class c, section sc, classconfig cg"
                + " where s.shiftid=cg.shiftid and d.departmentid=cg.deptid and c.classid=cg.classid and sc.sectionid=cg.sectionid"
                + " order by sectionname, shiftname,departmentname,classname  asc";
        
        try {
            prst = con.prepareStatement(query);

            prst.setDate(1, new java.sql.Date(currentDate.getTime()));

            prst.setDate(2, new java.sql.Date(currentDate.getTime()));

            prst.setDate(3, new java.sql.Date(currentDate.getTime()));

            rs = prst.executeQuery();
            
            while (rs.next())
            {
                double total;
                double presentp;
                double absentp;
                
                total = (double)(rs.getInt("present")+rs.getInt("absent"));
                
                if(total==0)
                {
                     presentp=0;
                     absentp=0;
                }
                else
                {
                    presentp=(double)(rs.getInt("present")*100)/total;
                    absentp = (double)(rs.getInt("absent")*100)/total;
                }
                
                stdAttendance_List.add(new AttendanceReport(rs.getString("c.classname"), rs.getString("s.shiftname"), rs.getString("sc.sectionname"), rs.getInt("total"),
                
                        rs.getInt("present"),presentp, rs.getInt("absent"),absentp));
            }
        } 
        catch (Exception ex)
        {
           System.out.println(ex);
        }
        finally 
        {
            try {
                if (rs != null) 
                {
                    rs.close();
                }
                if (prst != null) 
                {
                    prst.close();
                }
                if (con != null) 
                {
                    con.close();
                }
            } 
            catch (SQLException e) 
            {
                System.out.println(e);
            }
        }
        
        return stdAttendance_List;
    }

    @Override
    public List<AttendanceReport> specificSectionAttendance_List(int scCnfID, Date specificDate)
    {
       DB_Connection o = new DB_Connection();
  
        Connection con = o.getConnection();

        PreparedStatement prst = null;

        ResultSet rs = null;

        List<AttendanceReport> specificAttendance_List = new ArrayList<AttendanceReport>();
        
        String query = "select sbi.studentid, sbi.studentname, sbi.studentroll,absent,note, attendancedate"
                + " from student_basic_info sbi, student_attendence sa, student_identification si where"
                + " sbi.studentid= sa.studentid and sbi.studentid= si.studentid and si.classconfigid=? and attendancedate=? order by sbi.studentroll";

        try {
            prst = con.prepareStatement(query);

            prst.setInt(1, scCnfID);

            prst.setDate(2, new java.sql.Date(specificDate.getTime()));

            rs = prst.executeQuery();
            
            while (rs.next())
            {
                specificAttendance_List.add(new AttendanceReport(rs.getString("sbi.studentid"), rs.getString("sbi.studentname"), rs.getInt("sbi.studentroll"), rs.getInt("absent"), rs.getString("note")));
            }
            
        }
         catch (Exception ex)
        {
           System.out.println(ex);
        }
        finally 
        {
            try {
                if (rs != null) 
                {
                    rs.close();
                }
                if (prst != null) 
                {
                    prst.close();
                }
                if (con != null) 
                {
                    con.close();
                }
            } 
            catch (SQLException e) 
            {
                System.out.println(e);
            }
        }
        
        return specificAttendance_List;
    
    }

 
    
}
