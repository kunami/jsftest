package com.jsf;

import com.jsf.datacontrol.UserDelegate;
import com.jsf.model.User;
import com.jsf.model.UserBean;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ban
 */
@ManagedBean
@RequestScoped
public class LoginManager implements Serializable
{
    private String username;
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String login()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        System.out.println("in login: " + username + ":" + password);
        User user = UserDelegate.getUser(username);
        UserBean userBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{userBean}", UserBean.class);
        if(user == null || !user.getPassword().equals(password))
        {//Bad Login
            facesContext.addMessage("login:hidden", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Username or Password", "Invalid Username or Password"));
            userBean.setUser(null);
            userBean.setLoggedIn(false);
            return "index";
        } else
        {//Good Login            
            userBean.setUser(user);
            userBean.setLoggedIn(true);
            return "welcome";
        }
    }
    
    public String logout()
    {
        System.out.println("in logout");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UserBean userBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{userBean}", UserBean.class);
        userBean.setUser(null);
        userBean.setLoggedIn(false);
        return "index";
    }
}
