package otoparkYazilimiJavaPackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.bean.RequestScoped;
import java.util.Arrays;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ManagedBean(name="beanBizeUlasin")
@SessionScoped


public class BizeUlasin {
    String email;
    String name;
    String surname;
    String telephone;
    String msg;
    
    public String  goToBizeUlasin() 
    {
     return "bizeUlasin";
    }
    
     public void setEmail(String email) {
        this.email = email;
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
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
    
    
    public String getEmail(){
        return email;
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
    public String getMsg(){
        return msg;
    }
    
        public  void mailAt(){
             final String username = "beypark@yandex.com";
             final String password = "berkeeminyunus";
             Properties properties = new Properties();
             properties.put("mail.smtp.auth", "true");
             properties.put("mail.smtp.starttls.enable", "true");
             properties.put("mail.smtp.host", "smtp.yandex.com");
             properties.put("mail.smtp.port", "587");
 
             Session session = Session.getInstance(properties,
                           new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                           return new PasswordAuthentication(username, password);
                    }
             }); 
             try {
 

                 Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("beypark@yandex.com"));
                    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("beypark@yandex.com"));
                    message.setSubject("MESAJ");
                 String ad;
                    message.setText(email+"'den gelen mail şu şekilde:\n\nAd-Soyad              : "+name+" "+surname +"\nTelefon Numarası : " + telephone + "\n------------------------------------------------------------------\n" + msg ); 
                    Transport.send(message);
 
             } catch (MessagingException ex) {
                    throw new RuntimeException(ex);
             }
       }
   
}
