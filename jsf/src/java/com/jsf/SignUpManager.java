package com.jsf;

import com.jsf.datacontrol.UserDelegate;
import com.jsf.model.User;
import com.jsf.model.UserBean;
import java.util.Date;
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
public class SignUpManager 
{
    private String username;
    private String password;
    private String password2;
    private String firstName;
    private String lastName;

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

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPassword2()
    {
        return password2;
    }

    public void setPassword2(String password2)
    {
        this.password2 = password2;
    }
    
    public String create()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        //todo check the required fields are filled out (validators should handle this)
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);        
        if(isValid(context))
        {//Good submit, create user object and add to map         
            //TODO create user object, add to map
            User user = new User(username, password, firstName, lastName, new Date());
            UserDelegate.insertUser(user);
            userBean.setUser(user);
            userBean.setLoggedIn(true);
            return "welcome";
        } else
        {
            userBean.setUser(null);
            userBean.setLoggedIn(false);
            return "signup";
        }
        
    }
    
    private boolean isValid(FacesContext context)
    {
        //validate password == password2
        if(password.equals(password2))
        {
            //Need to check against existing users, return an error if duplicate.
            User user = UserDelegate.getUser(username);
            if(user == null)
            {
                return true;
            } else
            {
                context.addMessage("signup:hidden", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username already taken", "Username already taken"));
            }
        } else
        {
            context.addMessage("signup:hidden", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match", "Passwords do not match"));
        }
        return false;
    }
}
