package otoparkYazilimiJavaPackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.bean.RequestScoped;
import java.util.Arrays;
import java.io.Serializable;
import java.math.BigDecimal;


@ManagedBean(name="beanGaleri")
@SessionScoped

public class Galeri {
    public String  goToGaleri()
    {
     return "galeri";
    }
}
