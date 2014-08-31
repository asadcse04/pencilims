/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pencil.Student.Attendance;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author SHOHUG.SQ
 */

@ManagedBean
@ViewScoped

public class AttendanceInfoController implements Serializable{
      
   
    private AttendanceInfo attendanceInfo;
    
    private String ID;
    
    private AttendanceInfo studentBasicInfoList;
    
    private List<AttendanceInfo> studentInfoList;
    
    private List<AttendanceInfo> studentInfocount;
    
    AttendanceInfoService dao = new AttendanceInfoServiceImpl();

    public AttendanceInfoController() {
    }
    
    public void showDetails(){
        this.studentBasicInfoList=dao.getStudentAllinfo(ID);
    }
    
    public void showReport(){
        
        this.studentInfoList = dao.attenList(attendanceInfo, this);
        this.studentInfocount= dao.attenListCount(attendanceInfo, this);
    }

    public AttendanceInfo getAttendanceInfo() {
        if(this.attendanceInfo==null){
            this.attendanceInfo = new AttendanceInfo();
        }
        return attendanceInfo;
    }

    public void setAttendanceInfo(AttendanceInfo attendanceInfo) {
        this.attendanceInfo = attendanceInfo;
    }

    public List<AttendanceInfo> getStudentInfoList() {
        return studentInfoList;
    }

    public void setStudentInfoList(List<AttendanceInfo> studentInfoList) {
        this.studentInfoList = studentInfoList;
    }

    public List<AttendanceInfo> getStudentInfocount() {
        return studentInfocount;
    }

    public void setStudentInfocount(List<AttendanceInfo> studentInfocount) {
        this.studentInfocount = studentInfocount;
    }

    public AttendanceInfo getStudentBasicInfoList() {
        return studentBasicInfoList;
    }

    public void setStudentBasicInfoList(AttendanceInfo studentBasicInfoList) {
        this.studentBasicInfoList = studentBasicInfoList;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
