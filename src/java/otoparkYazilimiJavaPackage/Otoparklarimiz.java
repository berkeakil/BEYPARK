package otoparkYazilimiJavaPackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.bean.RequestScoped;
import java.util.Arrays;
import java.io.Serializable;
import java.math.BigDecimal;

@ManagedBean(name="beanOtoparklarimiz")
@SessionScoped

public class Otoparklarimiz {
    public String  goToOtoparklarimiz()
    {
     return "otoparklarimiz";
    }
}
