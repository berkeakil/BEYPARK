package otoparkYazilimiJavaPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.time.LocalDateTime;  // Import the LocalDateTime class
import java.time.format.DateTimeFormatter;  // Import the DateTimeFormatter class
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;


@ManagedBean(name="beanRapor")
@SessionScoped


public class Rapor {
    DataSource dataSource;
    public Rapor(){
        try{
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/otoparkYazilimiDB");
        }
        catch (NamingException e){
            e.printStackTrace();
        }
    }
    
        
    String date, plaka;
    
    int inCars, notLike34sNumber, yesterdaysCars;
    double dolulukOrani, top3;
    int year, month, day; 
    String location;
    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation(){
        return location;
    }
    public void setYear(int year){
        this.year = year - 1900;
    }
    public void setMonth(int month){
        this.month = month;
    }
    public void setDay(int day){
        this.day = day;
    }
    public int getYear(){
        return year;
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
    public void setInCar(int inCars){
        this.inCars = inCars;
    }
    public int getIntCar(){
        return inCars;
    }
    public void setNotLike34sNumber(int notLike34sNumber){
        this.notLike34sNumber = notLike34sNumber;
    }
    public int getNotLike34sNumber(){
        return notLike34sNumber;
    }
    public void setDolulukOrani(double dolulukOrani){
        this.dolulukOrani = dolulukOrani;
    }
    public double getDolulukOrani(){
        return dolulukOrani;
    }
    
    public void setYesterdaysCars(int yesterdaysCars){
        this.yesterdaysCars = yesterdaysCars;
    }
    public int getYesterdaysCars(){
        return yesterdaysCars;
    }
    public void setTop3(double top3){
        this.top3 = top3;
    }
    public double getTop3(){
        return top3;
    }
    public String date() 
    {  
        LocalDateTime myDateObj = LocalDateTime.now(); 
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  

        String formattedDate = myDateObj.format(myFormatObj);  
        return formattedDate;
    }  
    
    public int inOtopark() throws SQLException{
       //SELECT COUNT(PLAKA) FROM OTOPARK WHERE CIKIS_ZAMANI IS NULL;
       if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement("SELECT COUNT(PLAKA) FROM OTOPARK WHERE CIKIS_ZAMANI IS NULL");
       
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate( psSorgu.executeQuery() );
            
            while(psSorguResultSet.next()){
                setInCar(psSorguResultSet.getInt("1"));                
            }
        }finally{
            connection.close();
        }
        return inCars;
    }
    
    //SELECT * FROM OTOPARK WHERE GIRIS_ZAMANI>= ? ORDER BY GIRIS_ZAMANI; //Gunluk rapor.
    public void getYearMonthDate(){
        Calendar calendar = Calendar.getInstance();
        setYear(calendar.get(Calendar.YEAR));
        setMonth(calendar.get(Calendar.MONTH));
        setDay(calendar.get(Calendar.DAY_OF_MONTH));
    }
    
    public ResultSet gunlukRapor() throws SQLException{
        
        getYearMonthDate();
        Timestamp timestampTime = new Timestamp(getYear(), getMonth(), getDay(), 0, 0, 0, 0);
        
        if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement("SELECT PLAKA, GIRIS_ZAMANI, CIKIS_ZAMANI FROM OTOPARK WHERE GIRIS_ZAMANI >= ? ORDER BY GIRIS_ZAMANI");
            psSorgu.setTimestamp(1, timestampTime);
            
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate(psSorgu.executeQuery());
            return psSorguResultSet;
        }finally{
            connection.close();
        }
        
    }
    
    //SELECT COUNT(PLAKA) FROM OTOPARK WHERE PLAKA NOT LIKE '34%';
    public int notLike34s() throws SQLException{
       
       if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement("SELECT COUNT(PLAKA) FROM OTOPARK WHERE PLAKA NOT LIKE '34%'");
       
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate( psSorgu.executeQuery() );
            
            while(psSorguResultSet.next()){
                setNotLike34sNumber(psSorguResultSet.getInt("1"));                
            }
        }finally{
            connection.close();
        }
        return notLike34sNumber;
    }
    
    //SELECT COUNT(PLAKA)*100/150 FROM OTOPARK WHERE CIKIS_ZAMANI IS NULL;
    public double dolulukOrani() throws SQLException{
       
       if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement("SELECT COUNT(PLAKA)*100/CAST(150 AS NUMERIC(10, 3)) FROM OTOPARK WHERE CIKIS_ZAMANI IS NULL");
       
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate( psSorgu.executeQuery() );
            
            while(psSorguResultSet.next()){
                setDolulukOrani(psSorguResultSet.getDouble("1"));                
            }
        }finally{
            connection.close();
        }
        return dolulukOrani;
    }
    
    //SELECT COUNT(PLAKA) FROM OTOPARK WHERE GIRIS_ZAMANI IS ?;
    public int yesterdayInOtopark() throws SQLException{
       getYearMonthDate();
       Timestamp timestampTimeYesterday = new Timestamp(getYear(), getMonth(), getDay() - 1, 0, 0, 0, 0);
       Timestamp timestampTimeToday = new Timestamp(getYear(), getMonth(), getDay(), 0, 0, 0, 0);
       
       if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement("SELECT COUNT(PLAKA) FROM OTOPARK WHERE GIRIS_ZAMANI BETWEEN ? AND ?");
            psSorgu.setTimestamp(1, timestampTimeYesterday);
            psSorgu.setTimestamp(2, timestampTimeToday);
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate( psSorgu.executeQuery() );
            
            while(psSorguResultSet.next()){
                setYesterdaysCars(psSorguResultSet.getInt("1"));                
            }
        }finally{
            connection.close();
        }
        return yesterdaysCars;
    }
    
    //SELECT (dun)*100/CAST((bugun) AS NUMERIC(10, 3)) from (Select Count([Plaka]) as dun FROM Products WHERE GIRIS_ZAMANI=?) , (Select Count([Plaka]) as bugun FROM Products WHERE GIRIS_ZAMANI=?);
    /*public double dununBuguneOrani() throws SQLException{
       getYearMonthDate();
       Timestamp timestampTimeYesterday = new Timestamp(getYear(), getMonth(), getDay() - 1, 0, 0, 0, 0);
       Timestamp timestampTimeToday = new Timestamp(getYear(), getMonth(), getDay(), 0, 0, 0, 0);
       
       if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement("SELECT (dun)*100/CAST((bugun) AS NUMERIC(10, 3)) from (SELECT COUNT(PLAKA) AS dun FROM OTOPARK WHERE GIRIS_ZAMANI=?) , (SELECT COUNT(PLAKA) ASN  bugun FROM OTOPARK WHERE GIRIS_ZAMANI=?)");
            psSorgu.setTimestamp(1, timestampTimeYesterday);
            psSorgu.setTimestamp(2, timestampTimeToday);
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate( psSorgu.executeQuery() );
            
            while(psSorguResultSet.next()){
                setDununBuguneOrani(psSorguResultSet.getDouble("1"));                
            }
        }finally{
            connection.close();
        }
        return dununBuguneOrani;
    }*/
    
    public double ortalamaYas(String location) throws SQLException
  {
       
       if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement("SELECT  AVG(CAST(YIL as DECIMAL(9,2))) FROM MUSTERILER_"+location);
       
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate( psSorgu.executeQuery() );
            
            while(psSorguResultSet.next()){
                setTop3(psSorguResultSet.getDouble("1"));
                
                
            }
        }finally{
            connection.close();
        }
        Calendar calendar = Calendar.getInstance();
        double yearnow=calendar.get(Calendar.YEAR);
        return yearnow - top3;  
  }
        
public ResultSet buVeGelecekAyDogumGunu(String location) throws SQLException{
        
        getYearMonthDate();
        int thisMonth = getMonth();
        int nextMonth = getMonth();        
String[] monthName = {"NULL", "Ocak", "Şubat",
                "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz",
                "Ağustos", "Eylül", "Ekim", "Kasım",
                "Aralık"};

        String thisMonthName = monthName[thisMonth];
        String nextMonthName = monthName[nextMonth + 1];
        
        if ( dataSource == null )
            throw new SQLException( "Unable to obtain DataSource" );
        
        Connection connection = dataSource.getConnection();
        
        if ( connection == null )
            throw new SQLException( "Unable to connect to DataSource" );
        
        try{
            
            PreparedStatement psSorgu = connection.prepareStatement("SELECT NAME, SURNAME FROM MUSTERILER_"+location+" WHERE AY IN (?,?)");
            psSorgu.setString(1, thisMonthName);
            psSorgu.setString(2, nextMonthName);
            
            CachedRowSet psSorguResultSet = new com.sun.rowset.CachedRowSetImpl();
            psSorguResultSet.populate(psSorgu.executeQuery());
            return psSorguResultSet;
        }finally{
            connection.close();
        }
        
    }

    
}


//SELECT * FROM Customers WHERE Country IN ('Germany', 'France', 'UK');