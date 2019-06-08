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
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;



@ManagedBean(name="beanOdeme")
@SessionScoped

public class Odeme {
    String cardno,password,date,ccv;
    DataSource dataSource;
    double balance=999999999.999999;
    double price;
    double newprice;
    String errormsg="";
    String tarife="STANDARD";
    String email;
     public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    public Odeme(){
        try{
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/otoparkYazilimiDB");
        }
        catch (NamingException e){
            e.printStackTrace();
        }
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
     public void setErrorMsg(String errormsg) {
        this.errormsg = errormsg;
    }
    public void setCcv(String ccv) {
        this.ccv = ccv;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setNewPrice(double newprice) {
        this.newprice = newprice;
    }
    
    public String getCardno(){
        return cardno;
    }
    public String getPassword(){
        return password;
    }
    public String getDate(){
        return date;
    }
    public String getErrorMsg(){
        return errormsg;
    }
    public String getCcv(){
        return ccv;
    }
    public double getBalance(){
        return balance;
    }
    public double getPrice(){
        return price;
    }
    public double getNewPrice(){
        return newprice;
    }
    public void setTarife(String tarife) {
        this.tarife = tarife;
    }
    
    public String getTarife(){
        return tarife;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }

    public String ode(String package_name) throws SQLException
    {   
        
            if ( dataSource == null )
                throw new SQLException( "Unable to obtain DataSource" );

            Connection connection = dataSource.getConnection();

            if ( connection == null )
                throw new SQLException( "Unable to connect to DataSource" );

            try{

                PreparedStatement psSorgu = connection.prepareStatement(
                        "SELECT BALANCE FROM MUSTERI_BAKIYELERI WHERE CARDNO='"+cardno+"' AND PASSWORD= ? AND DATE='"+date+"' AND CCV='"+ccv+"'");
                psSorgu.setString( 1, getPassword() );
                CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
                psSorguResultSet.populate( psSorgu.executeQuery() );


                while(psSorguResultSet.next()){

                    setBalance(psSorguResultSet.getDouble("balance"));
                }
                if(tarife.equals("1")) 
                {
                    setPrice(10.0);   
                    setNewPrice(getBalance()-getPrice());
                }
                if(tarife.equals("2")) 
                {
                    setPrice(69.0);   
                    setNewPrice(getBalance()-getPrice());
                }
                if(tarife.equals("3")) 
                {
                    setPrice(73.0);   
                    setNewPrice(getBalance()-getPrice());
                }
                if(tarife.equals("4")) 
                {
                    setPrice(131.0);   
                    setNewPrice(getBalance()-getPrice());
                }

                if(balance!=999999999.999999)
                {
                   if(balance>0)
                   {
                       if(balance>price)
                       {
                           try
                           {
                               PreparedStatement guncelleObj = 
                                       connection.prepareStatement( "UPDATE MUSTERI_BAKIYELERI SET " + 
                                       "BALANCE="+newprice+" WHERE CARDNO='"+cardno+"'" );
                               //guncelleObj.setDouble( 1,  getNewPrice());
                               guncelleObj.executeUpdate(); 
                           }
                           finally
                           {
                               connection.close(); 
                           }
                           return "index";

                       }
                       else
                       {
                           errormsg="Yeterli bakiye bulunmamaktadır!";
                       }
                   }
                   else
                   {
                    errormsg="Hesabınızda para bulunmamaktadır.Lütfen para yükleyiniz";
                   }
                } 
            } 
            finally{
                connection.close();
            }
        
        return "odeme";

    }
}