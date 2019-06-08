/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otoparkYazilimiJavaPackage;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.sql.*;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import java.time.Duration;
import javax.sql.rowset.CachedRowSet;

@ManagedBean(name="beanPersonelGC")
@SessionScoped 
//@ViewScoped

public class GirisCikis 
{
    DataSource dataSource;

    String name,surname,plaka,packagename, tempPlaka;

    boolean isMusteri = false, isGuest = false;
    
    public GirisCikis(){
        try{
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/otoparkYazilimiDB");
        }
        catch (NamingException e){
            e.printStackTrace();
        }
    }
    public void setIsMusteri(boolean isMusteri) {
        this.isMusteri = isMusteri;
    }
    public void setIsGuest(boolean isGuest) {
        this.isGuest = isGuest;
    }
    public boolean getIsMusteri(){
        return isMusteri;
    }
    public boolean getIsGuest(){
        return isGuest;
    }
    public void setTempPlaka(String tempPlaka){
        this.tempPlaka = tempPlaka;
    }
    public String getTempPlaka(){
        return tempPlaka;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }
    public void setPackageName(String packagename) {
        this.packagename = packagename;
    }
 
    public String getPackageName(){
        return packagename;
    }    
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getPlaka() {
        return plaka;
    } 
    
    long diffMinutes;
    
    public void setDiffMinutes(long diffMinutes){
        this.diffMinutes = diffMinutes;
    }
    
    public long getDiffMinutes(){
        return diffMinutes;
    }
    
    public void giris() throws SQLException{
        
        Calendar calendar = Calendar.getInstance();
        Timestamp giris_zamani = new Timestamp(calendar.getTime().getTime());
        
        if ( dataSource == null )
                throw new SQLException( "Unable to obtain DataSource" );
            Connection connection = dataSource.getConnection();
            if ( connection == null )
                throw new SQLException( "Unable to connect to DataSource" );
            try{

                PreparedStatement object2 =
                connection.prepareStatement( "INSERT INTO OTOPARK (PLAKA,GIRIS_ZAMANI) VALUES( ? , ? )");
                
                object2.setString( 1, getPlaka() );
                object2.setTimestamp( 2, giris_zamani );
                
                object2.executeUpdate(); 
            }
            finally{
                connection.close(); 
                setPlaka("");
            }
        
    }
    
    public void cikis() throws SQLException{
    
        Calendar calendar = Calendar.getInstance();
        Timestamp cikis_zamani = new Timestamp(calendar.getTime().getTime());
        Timestamp giris_zamaniTemp;
        setTempPlaka(plaka);
        try{
            
            if ( dataSource == null )
                throw new SQLException( "Unable to obtain DataSource" );
            
            Connection connection = dataSource.getConnection();
            
            if ( connection == null )
                throw new SQLException( "Unable to connect to DataSource" );           
            
            try{
                PreparedStatement psSorguGirisZamani = connection.prepareStatement(
                    "SELECT GIRIS_ZAMANI "
                            + "FROM OTOPARK "
                            + "WHERE CIKIS_ZAMANI IS NULL AND PLAKA = ?");
                
                psSorguGirisZamani.setString(1, plaka);
                
                CachedRowSet psSorguGirisZamaniResultSet = new com.sun.rowset.CachedRowSetImpl();
                
                psSorguGirisZamaniResultSet.populate( psSorguGirisZamani.executeQuery() );

                while(psSorguGirisZamaniResultSet.next()){
                    giris_zamaniTemp = psSorguGirisZamaniResultSet.getTimestamp("GIRIS_ZAMANI");
                    Duration duration = Duration.between(giris_zamaniTemp.toInstant(), cikis_zamani.toInstant());
                    setDiffMinutes(duration.toMinutes());
                }
                
                PreparedStatement psSorgu = connection.prepareStatement(
                  "UPDATE OTOPARK SET CIKIS_ZAMANI = ? WHERE CIKIS_ZAMANI IS NULL AND PLAKA = ?");
                
                psSorgu.setTimestamp(1, cikis_zamani);
                psSorgu.setString(2, plaka);
                psSorgu.executeUpdate();
                
                printColumns(plaka, connection);
            }
            finally{
                setPlaka("");
                connection.close();
            }
           
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------ 

    public void printColumns(String plaka, Connection con) throws SQLException{
        String tempName = printName(plaka, con);
        if (tempName.equalsIgnoreCase("NULL")){    
            //SELECT CUSTOMER_ID,NAME,SURNAME,PACKAGE_NAME FROM MUSTERILER WHERE NAME='Guest';
            setIsGuest(true);
            setName("Guest");
            setSurname("");
            setPackageName("Standard Tarife");
        }
        else
        {
            // SELECT CUSTOMER_ID,NAME,SURNAME,PACKAGE_NAME FROM MUSTERILER WHERE PLAKA=?;
                    
            PreparedStatement psInnerSorgu = con.prepareStatement(
            "SELECT NAME,SURNAME,PACKAGE_NAME FROM MUSTERILER WHERE PLAKA='"+plaka+"'");

            CachedRowSet psInnerSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psInnerSorguResultSet.populate( psInnerSorgu.executeQuery() );

            while(psInnerSorguResultSet.next()){
                setIsMusteri(true);
                setName(psInnerSorguResultSet.getString("name"));  
                setSurname(psInnerSorguResultSet.getString("surname"));           
                setPackageName(psInnerSorguResultSet.getString("package_name"));                               
            }
         }
    }   
    
    public String printName(String plaka, Connection conn) throws SQLException{
            setName("NULL");
            PreparedStatement psPrintNameSorgu = conn.prepareStatement(
                "SELECT NAME FROM MUSTERILER WHERE PLAKA='"+plaka+"'");
       
            CachedRowSet psPrintNameSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psPrintNameSorguResultSet.populate( psPrintNameSorgu.executeQuery() );
            
            while(psPrintNameSorguResultSet.next()){
                setName(psPrintNameSorguResultSet.getString("name"));           
            }
       
        return name;
    }
}



