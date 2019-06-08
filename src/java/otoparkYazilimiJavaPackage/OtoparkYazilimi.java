import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.bean.RequestScoped;
import java.util.Arrays;
import java.io.Serializable;
import java.math.BigDecimal;

import java.sql.*;

import javax.annotation.Resource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




@ManagedBean(name="user")
@SessionScoped

public class OtoparkYazilimi{	
    public String  goToIndex(){
     return "index";
    }
    
    public String  goToGirisYap(){
     return "girisYap2";
    }
        
    public String[] termsAndCondList;
    public String[] termsAndCondSelected;

    public void setTermsAndCondSelected(String[] termsAndCondSelected) {
        this.termsAndCondSelected = termsAndCondSelected;
    }

    public String[] getTermsAndCondSelected() {
        return termsAndCondSelected;
    }
    public String[] getTermsAndCondList() 
    {
	termsAndCondList = new String[1];
	termsAndCondList[0] = "Üyelik sözleşmesini kabul ediyorum.*";
	return termsAndCondList;
    }
	
    public String getTermsAndCondSelectedInString() 
    {
        return Arrays.toString(termsAndCondSelected);
    }
    public String[] acceptionList;
    public String[] acceptionSelected;

    public void setacceptionSelected(String[] acceptionSelected) {
        this.acceptionSelected = acceptionSelected;
    }

    public String[] getacceptionSelected() {
        return acceptionSelected;
    }
    public String[] getacceptionList() 
    {
	acceptionList = new String[2];
	acceptionList[0] = "Kişisel verilerimin işlenmesini kabul ediyorum.";
	acceptionList[1] = "Promosyon amaçlı reklamların tarafıma iletilmesini istiyorum.";
	return acceptionList;
    }
	
    public String getacceptionSelectedInString() 
    {
        return Arrays.toString(acceptionSelected);
    }
    
    

	
}
