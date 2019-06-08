package otoparkYazilimiJavaPackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.bean.RequestScoped;

import java.util.Arrays;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import javax.sql.*;

import javax.annotation.Resource;

import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.http.HttpSession;


import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import java.io.*;

import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
  

@ManagedBean(name="GirisYap")

@SessionScoped 

public class GirisYap implements Serializable{
    
    final String iv = "0123456789123456"; // This has to be 16 characters
        final String secretKey = "Replace this by your secret key";
        final Crypto crypto = new Crypto();
    
    
    DataSource dataSource;
   
    String email;
    String password;
    String name;
    String surname;
    String telephone;
    String plaka;
    String gun;
    String ay;
    String yil;
    String cinsiyet;
    String location;
    String girisEmail;
    String girisPassword;
    String girisPasswordCheck;
      
    String duzenleTelephone;
    String decryptedPassword;
        
    String passwordCheck;
    
    String dbPassword;
    String dbEmail;
    
    String tmpmail;
    
    int id;
    
    boolean deneme = true;
    public boolean getDeneme(){
        return deneme;
    }
    
    public GirisYap(){
        try{
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/otoparkYazilimiDB");
        }
        catch (NamingException e){
            e.printStackTrace();
        }
    }
    
   
    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbEmail() {
        return dbEmail;
    }

       
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTmpmail(String email) {
        this.tmpmail = tmpmail;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }
    
    public void setGun(String gun) {
        this.gun = gun;
    }
    public void setAy(String ay) {
        this.ay = ay;
    }
    
    public void setYil(String yil) {
        this.yil = yil;
    }
    
    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setPasswordCheck(String passwordCheck){
        this.passwordCheck = passwordCheck;
    }
    
    public void setGirisEmail(String girisEmail){
        this.girisEmail = girisEmail;
    }
    public void setGirisPassword(String girisPassword){
        this.girisPassword = girisPassword;
    }
    public String getEmail(){
        return email;
    }
    public String getTmpmail(){
        return tmpmail;
    }
    public String getPassword(){
        return password;
    }
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getTelephone(){
        return telephone;
    }
    public String getPlaka(){
        return plaka;
    }
    public String getGun(){
        return gun;
    }
    public String getAy(){
        return ay;
    }
    public String getYil(){
        return yil;
    }
    public String getCinsiyet(){
        return cinsiyet;
    }
    public String getPasswordCheck(){
        return passwordCheck;
    }
    public String getGirisEmail(){
        return girisEmail;
    }
    public String getGirisPassword(){
        return girisPassword;
    }
    public String getLocation(){
        return location;
    }
    public int getId(){
        return id;
    }
    
    void setId(int id){
        this.id = id;
    }
    
    public void setDecryptedPassword(String decryptedPassword){
        this.decryptedPassword = decryptedPassword;
    }
    
    public String getDecryptedPassword(){
        decryptedPassword =  crypto.decrypt(getPassword(), iv, secretKey);
        return decryptedPassword;
    }
    
    String tempVar;
    public void setTempVar(String tempVar){
        this.tempVar=tempVar;
    }
    public String getTempVar(){
        return tempVar;
    }
        public void setDuzenleTelephone(String duzenleTelephone){
        this.duzenleTelephone = duzenleTelephone;
    }
        public String getDuzenleTelephone(){
        return duzenleTelephone;
    }
        
    public String decryptPassword(String password){
        final String decryptedData = crypto.decrypt(password, iv, secretKey);
        password=decryptedData;
        return password;
    }
   
    public void login() throws SQLException{
        
        if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement(
                    "(SELECT * FROM MUSTERILER_CENNET WHERE EMAIL='"+ girisEmail +"') "
                            + "UNION (SELECT * FROM MUSTERILER_BAGCILAR WHERE EMAIL='"+ girisEmail +"') "
                            + "UNION (SELECT * FROM MUSTERILER_BAYRAMPASA WHERE EMAIL='"+ girisEmail +"') "
                            + "UNION (SELECT * FROM MUSTERILER_ARNAVUTKOY WHERE EMAIL='"+ girisEmail +"') ORDER BY NAME ");
                            
            
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate( psSorgu.executeQuery() );
            
            
            while(psSorguResultSet.next()){
                setId(psSorguResultSet.getInt("CUSTOMER_ID"));
                girisPasswordCheck = psSorguResultSet.getString("password");
                setName(psSorguResultSet.getString("name"));
                setSurname(psSorguResultSet.getString("surname"));
                setTelephone(psSorguResultSet.getString("telephone"));
                setPassword(psSorguResultSet.getString("password"));
                setPlaka(psSorguResultSet.getString("plaka"));
                
            }
             FacesContext context = FacesContext.getCurrentInstance();
            if(girisEmail.equals("admin@beypark.com")){
                    context.getExternalContext().getSessionMap().put("kullanici", email);
                    try {
                        context.getExternalContext().redirect("personel.xhtml");
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            girisPasswordCheck = decryptPassword(girisPasswordCheck);
           
            
            if(girisPassword.equals(girisPasswordCheck)){
                
                    context.getExternalContext().getSessionMap().put("kullanici", email);
                    try {
                        setEmail(girisEmail);
                        context.getExternalContext().redirect("hesabim.xhtml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                
            }else{
                context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password."));
            } 
        } 
        finally{
            connection.close();
        }
    }
    
    
    
    
    public void logout() {
     	FacesContext context = FacesContext.getCurrentInstance();
     	context.getExternalContext().invalidateSession();
        
        try {
            context.getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public String kaydet() throws SQLException{
        
        final String encryptedData = crypto.encrypt(getPassword(), iv, secretKey);
   
       
            if ( dataSource == null )
                throw new SQLException( "Unable to obtain DataSource" );
            Connection connection = dataSource.getConnection();
            if ( connection == null )
                throw new SQLException( "Unable to connect to DataSource" );
            try{

                
                
                PreparedStatement object2 =
                connection.prepareStatement( "INSERT INTO MUSTERILER_" +location+
                " (EMAIL, PASSWORD, NAME, SURNAME, TELEPHONE, PLAKA, GUN, AY, YIL, CINSIYET)" +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" );


                object2.setString( 1, getEmail() );
                object2.setString( 2, encryptedData );
                object2.setString( 3, getName() );
                object2.setString( 4, getSurname() );
                object2.setString( 5, getTelephone() );
                object2.setString( 6, getPlaka() );
                object2.setString( 7, getGun() );
                object2.setString( 8, getAy() );
                object2.setString( 9, getYil() );
                object2.setString( 10, getCinsiyet() );

                object2.executeUpdate(); 
                return "girisYap"; 
            }
            finally{
                connection.close(); 
            }
        
    }
    
    
    public void guncelle(int counter) throws SQLException
    {
           counter=0;
            if ( dataSource == null )
                throw new SQLException( "Unable to obtain DataSource" );
            Connection connection = dataSource.getConnection();
            if ( connection == null )
                throw new SQLException( "Unable to connect to DataSource" );
            try{
                getTelephone();
               
                String tempTelephone=telephone;
                setTelephone(telephone);
                System.out.print(telephone);
               
                    location="someplace";
                    String tmpmail=email;
                    PreparedStatement psSorgu = connection.prepareStatement(
                            "SELECT EMAIL FROM MUSTERILER_CENNET");

                    CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
                    psSorguResultSet.populate( psSorgu.executeQuery() );


                    while(psSorguResultSet.next())
                    {
                        setEmail(psSorguResultSet.getString("email"));           
                        if(email.equalsIgnoreCase(tmpmail)){setLocation("CENNET");}
                    }
                    if (location.equalsIgnoreCase("someplace"))
                    {
                        PreparedStatement psSorgu2 = connection.prepareStatement(
                            "SELECT EMAIL FROM MUSTERILER_BAGCILAR");

                        CachedRowSet psSorguResultSet2 = new com.sun.rowset.CachedRowSetImpl();
                        psSorguResultSet2.populate( psSorgu2.executeQuery() );


                        while(psSorguResultSet2.next())
                        {
                            setEmail(psSorguResultSet2.getString("email"));           
                            if(email.equalsIgnoreCase(tmpmail)){setLocation("BAGCILAR");}
                        }
                        if (location.equalsIgnoreCase("someplace"))
                        {
                            PreparedStatement psSorgu3 = connection.prepareStatement(
                                "SELECT EMAIL FROM MUSTERILER_BAYRAMPASA");

                            CachedRowSet psSorguResultSet3 = new com.sun.rowset.CachedRowSetImpl();
                            psSorguResultSet3.populate( psSorgu3.executeQuery() );


                            while(psSorguResultSet3.next())
                            {
                                setEmail(psSorguResultSet3.getString("email"));           
                                if(email.equalsIgnoreCase(tmpmail)){setLocation("BAYRAMPASA");}
                            }
                            if (location.equalsIgnoreCase("someplace"))
                            {
                                PreparedStatement psSorgu4 = connection.prepareStatement(
                                    "SELECT EMAIL FROM MUSTERILER_BAGCILAR");

                                CachedRowSet psSorguResultSet4 = new com.sun.rowset.CachedRowSetImpl();
                                psSorguResultSet4.populate( psSorgu4.executeQuery() );


                                while(psSorguResultSet4.next())
                                {
                                    setEmail(psSorguResultSet4.getString("email"));           
                                    if(email.equalsIgnoreCase(tmpmail)){setLocation("ARNAVUTKOY");}
                                }

                            }
                            else
                            {}

                        }
                        else
                        {}

                    }
                    else
                    {}            
                    email=tmpmail;
                
                
                
                
                PreparedStatement guncelleObj = 
                        connection.prepareStatement( "UPDATE MUSTERILER_"+location+" SET " + 
                        "EMAIL = ? , PASSWORD = ? , NAME = ?, SURNAME = ?, TELEPHONE =  '"+tempTelephone+"', PLAKA =  ? " +
                        "WHERE CUSTOMER_ID = ?" );
                final String encryptedData = crypto.encrypt(getPassword(), iv, secretKey);
                
                String decryptedData = crypto.decrypt(getPassword(), iv, secretKey);
                String decryptedDataCheck = crypto.decrypt(decryptedData, iv, secretKey);
                guncelleObj.setString( 1, getEmail() );
                if(decryptedData.equals(decryptedDataCheck))
                {
                    decryptedDataCheck = crypto.encrypt(decryptedDataCheck, iv, secretKey);
                    guncelleObj.setString( 2, decryptedDataCheck );
                }
                
                else
                {
                    decryptedData = crypto.encrypt(decryptedData, iv, secretKey);
                    guncelleObj.setString( 2, decryptedData );
                    
                }  

                
                guncelleObj.setString( 3, getName() );
                guncelleObj.setString( 4, getSurname() );
               // guncelleObj.setString( 5, getTelephone() );
                guncelleObj.setString( 5, getPlaka() );
                guncelleObj.setInt( 6, getId() );
                System.out.println(guncelleObj);
                guncelleObj.executeUpdate(); 
                //return "index"; 

            }
            finally
            {
                connection.close(); 
            }
            counter++;
            
    }
    
    public void sil() throws SQLException
    {       
       
        if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        Connection connection = dataSource.getConnection();
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        boolean loopCheck = true;
        try{
            PreparedStatement psSorgu1 = connection.prepareStatement("(SELECT EMAIL FROM MUSTERILER_CENNET)");
              
            CachedRowSet psSorgu1ResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorgu1ResultSet.populate( psSorgu1.executeQuery() );
            
            while(psSorgu1ResultSet.next()){
                if(email.equals(psSorgu1ResultSet.getString("email")) && loopCheck){
                    setLocation("CENNET"); 
                    loopCheck = false;
                }
            }
            
            PreparedStatement psSorgu2 = connection.prepareStatement("(SELECT EMAIL FROM MUSTERILER_BAGCILAR)");
            CachedRowSet psSorgu2ResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorgu2ResultSet.populate( psSorgu2.executeQuery() );
            
            while(psSorgu2ResultSet.next()){
                if(email.equals(psSorgu2ResultSet.getString("email")) && loopCheck){
                    setLocation("BAGCILAR");   
                    loopCheck = false;
                }
            }
            
            PreparedStatement psSorgu3 = connection.prepareStatement("(SELECT EMAIL FROM MUSTERILER_BAYRAMPASA)");
            CachedRowSet psSorgu3ResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorgu3ResultSet.populate( psSorgu3.executeQuery() );
            
            while(psSorgu3ResultSet.next()){
                if(email.equals(psSorgu3ResultSet.getString("email"))&& loopCheck){
                    setLocation("BAYRAMPASA");               
                    loopCheck = false;
                }
            }
            
            PreparedStatement psSorgu4 = connection.prepareStatement("(SELECT EMAIL FROM MUSTERILER_ARNAVUTKOY)");
            CachedRowSet psSorgu4ResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorgu4ResultSet.populate( psSorgu4.executeQuery() );
            
            while(psSorgu4ResultSet.next()){
                if(email.equals(psSorgu4ResultSet.getString("email"))&& loopCheck){
                    setLocation("ARNAVUTKOY");  
                    loopCheck = false;
                }
            }
            
            
            PreparedStatement guncelleObj = 
                connection.prepareStatement( "DELETE FROM MUSTERILER_"+location+" WHERE EMAIL = ?" );
                
                         
                guncelleObj.setString( 1, getEmail() );
                guncelleObj.executeUpdate(); 
            }
            finally
            {
                connection.close(); 
            }
        logout();
    }
}

 


