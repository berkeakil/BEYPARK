package otoparkYazilimiJavaPackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.bean.RequestScoped;
import java.util.Arrays;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



@ManagedBean(name="beanHesabim")
@SessionScoped
//@RequestScoped
public class Hesabim implements Serializable{
    
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
    String girisEmail;
    String girisPassword;
    String girisPasswordCheck;
      
    String passwordCheck;
    
    String dbPassword;
    String dbEmail;
    
    int id;
    
    Myfilter myfilterObj = new Myfilter();
    
    public Hesabim(){
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
    
    public int getId(){
        return id;
    }
    
    void setId(int id){
        this.id = id;
    }
    public String  goToHesabim(){
        
            return "hesabim";
        
    }
    private static final long serialVersionUID = 1L;
   public String data = "1";

   public String getData() {
      return data;
   }

   public void setData(String data) {
      this.data = data;
   }

   public void attributeListener(ActionEvent event) {
      data = (String)event.getComponent().getAttributes().get("username");	
   }
     public String  getAd()
    {
     return "Ad";
    }
    
    
     
    public void guncelle() throws SQLException
    {
            if ( dataSource == null )
                throw new SQLException( "Unable to obtain DataSource" );
            Connection connection = dataSource.getConnection();
            if ( connection == null )
                throw new SQLException( "Unable to connect to DataSource" );
            try{
                getTelephone();
                String tempTelephone=telephone;
                PreparedStatement guncelleObj = 
                        connection.prepareStatement( "UPDATE MUSTERILER SET " + 
                        "EMAIL = ? , PASSWORD = ? , NAME = ?, SURNAME = ?, TELEPHONE = '"+tempTelephone+"', PLAKA =  ? " +
                        "WHERE CUSTOMER_ID = ?" );
                
                guncelleObj.setString( 1, getEmail() );
                guncelleObj.setString( 2, getPassword() );
                guncelleObj.setString( 3, getName() );
                guncelleObj.setString( 4, getSurname() );
                //guncelleObj.setString( 5, getTelephone() );
                guncelleObj.setString( 5, getPlaka() );
                guncelleObj.setInt( 6, getId() );
                guncelleObj.executeUpdate(); 
                //return "index"; 
            }
            finally
            {
                connection.close(); 
            }
    }
    
    
     
}