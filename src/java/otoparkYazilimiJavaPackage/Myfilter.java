/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otoparkYazilimiJavaPackage;

import java.io.IOException;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@ManagedBean (name="beanMyfilter")
@SessionScoped
public class Myfilter implements Filter{
    boolean loggedIn;
    public boolean getLoggedIn(){
        return loggedIn;
    }
 public void doFilter(ServletRequest req, ServletResponse resp,  
     FilterChain chain) throws IOException, ServletException {  
           
    HttpServletRequest request = (HttpServletRequest) req;
     HttpServletResponse response = (HttpServletResponse) resp;
     HttpSession session = request.getSession(false);
     
     String loginURI = request.getContextPath() + "/faces/girisYap.xhtml";
 
     loggedIn = session != null && session.getAttribute("kullanici") != null;
     boolean loginRequest = request.getRequestURI().equals(loginURI);
     boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);
 
     if (loggedIn || loginRequest || resourceRequest) {
         chain.doFilter(request, response);
     } else {
         response.sendRedirect(loginURI);
     }
     
     }  
 
 @Override
     public void destroy() {}
 
 @Override
 public void init(FilterConfig arg0) throws ServletException {

 
 }  
 
}