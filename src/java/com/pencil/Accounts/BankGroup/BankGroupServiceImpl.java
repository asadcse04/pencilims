/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pencil.Accounts.BankGroup;

import com.pencil.Connection.DB_Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Riad
 */
public class BankGroupServiceImpl implements BankGroupService {
  
 @Override
 public boolean addBankGroup(BankGroup bankGroup) {
        DB_Connection o = new DB_Connection();

        Connection con = o.getConnection();

        PreparedStatement prst = null;
        
        try 
        {
            prst = con.prepareStatement("insert into bank_group values (null, ?, ?)");
            
            prst.setString(1, bankGroup.getBankGroupName());
            
            prst.setString(2, bankGroup.getNote());

            int add = prst.executeUpdate();

            return true;

        } 
        catch (SQLException ex) 
        {
            System.out.println(ex);
        } 
       
        finally 
        {
            try 
            {
                if (prst != null) {

                    prst.close();

                }

                if (con != null) {

                    con.close();
                }

            } 
            catch (SQLException e) 
            {

                System.out.println(e);
            }
        } 
        return false;
    }

    @Override
    public List<BankGroup> getAllBankGroup() {
        
        List <BankGroup> bankGroupList = new ArrayList<BankGroup>();
       
        DB_Connection o = new DB_Connection();

        Connection con = o.getConnection();

        PreparedStatement prst = null;

        ResultSet rs = null;

        try {
            prst = con.prepareStatement("select * from bank_group");

            rs = prst.executeQuery();

            while (rs.next()) 
            {
               bankGroupList.add(new BankGroup(rs.getInt(1), rs.getString(2), rs.getString(3)));

            }

            return bankGroupList;

        } 
        
        catch (SQLException ex) {
            System.out.println(ex);
        } 
        
        finally {
            try {
                
                if (rs != null) {

                    rs.close();

                }
                
                if (prst != null) {

                    prst.close();

                }

                if (con != null) {

                    con.close();
                }

            } catch (SQLException e) {

                System.out.println(e);
            }
        }
       
       return bankGroupList;
    }
    
}
