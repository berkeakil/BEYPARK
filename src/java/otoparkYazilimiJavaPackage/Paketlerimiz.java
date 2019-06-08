
package otoparkYazilimiJavaPackage;

import java.io.IOException;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;


@ManagedBean(name="beanPaketlerimiz")
@SessionScoped

public class Paketlerimiz implements Serializable 
{
    DataSource dataSource;
   
    int id;
    String name;
    double price;
    int hourtime;
    double totalprice;
    int secim;

    public int getId()
    {
        return id;
    }
    void setId(int id){
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice(){
        return price;
    }
    public void setHourtime(int hourtime) {
        this.hourtime = hourtime;
    }
    public int getHourtime(){
        return hourtime;
    }
    
    public double getTotalPrice()
    {
        return totalprice;
    }
    void setTotalPrice(double totalprice){
        this.totalprice = totalprice;
    }
    
        public Paketlerimiz(){
        try{
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/otoparkYazilimiDB");
        }
        catch (NamingException e){
            e.printStackTrace();
        }
    }
    

 public String name(int id) throws SQLException{
        
        if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement(
                    "SELECT NAME FROM PAKETLERIMIZ WHERE OPTION_ID="+id);
       
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate( psSorgu.executeQuery() );
            
            
            while(psSorguResultSet.next()){
                setName(psSorguResultSet.getString("name"));           
            }
           
            FacesContext context = FacesContext.getCurrentInstance();
        } 
        finally{
            connection.close();
        }
                return name;

    }
 
    public double price(int id) throws SQLException{
        
        if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement(
                    "SELECT PRICE FROM PAKETLERIMIZ WHERE OPTION_ID="+id);
       
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate( psSorgu.executeQuery() );
            
            
            while(psSorguResultSet.next()){
                setPrice(psSorguResultSet.getDouble("price"));
            }
           
            FacesContext context = FacesContext.getCurrentInstance();
        } 
        finally{
            connection.close();
        }
        return price;
    }
    
    public int hourtime(int id) throws SQLException{
        
        if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement(
                    "SELECT HOURTIME FROM PAKETLERIMIZ WHERE OPTION_ID="+id);
       
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate( psSorgu.executeQuery() );
            
            
            while(psSorguResultSet.next()){
                setHourtime(psSorguResultSet.getInt("hourtime"));                
            }
           
            FacesContext context = FacesContext.getCurrentInstance();
        } 
        finally{
            connection.close();
        }
        return hourtime;
    }
    
    public double totalprice(int id) throws SQLException{
        
        price(id);
        hourtime(id);
        totalprice=price*hourtime;
        totalprice=totalprice*15/100;
        double total=(int) totalprice;
        totalprice=total;
        return totalprice;
    }
    public String  goToPaketlerimiz()
    {
     return "paketlerimiz";
    }
    
    
    
    
    
}
