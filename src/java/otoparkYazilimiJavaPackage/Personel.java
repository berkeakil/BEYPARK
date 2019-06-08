package otoparkYazilimiJavaPackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.bean.RequestScoped;


@ManagedBean(name="beanPersonel")
@SessionScoped

public class Personel {
    public String goToGirisCikis(){
        return "girisCikis";
    }
    
    public String goToPersonel(){
        return "personel";
    }
    
    public String goToRapor(){
        return "rapor";
    }
    
}
