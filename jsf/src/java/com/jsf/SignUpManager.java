package com.jsf;

import com.jsf.datacontrol.UserDelegate;
import com.jsf.model.User;
import com.jsf.model.UserBean;
import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ban
 */
@ManagedBean
@RequestScoped
public class SignUpManager implements Serializable 
{
    private String username;
    private String password;
    private String password2;
    private String firstName;
    private String lastName;
    private User user;
    
    @ManagedProperty("#{userBean}")
    private UserBean userBean;

    public SignUpManager()
    {
    }

    public void setUser(User user)
    {
        this.user = user;
        this.username = user.getUsername();
        this.password = this.password2 = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUserBean(UserBean userBean)
    {
        this.userBean = userBean;
    }
    
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
        if(isValid(context, true))
        {//Good submit, create user object and add to map         
            //TODO create user object, add to map
            User newUser = new User(username, password, firstName, lastName, new Date());
            UserDelegate.insertUser(newUser);
            userBean.setUser(newUser);
            userBean.setLoggedIn(true);
            return "welcome";
        } else
        {
            userBean.setUser(null);
            userBean.setLoggedIn(false);
            return "signup";
        }
        
    }
    
    private boolean isValid(FacesContext context, boolean checkExists)
    {
        //validate password == password2
        if(password.equals(password2))
        {
            //Need to check against existing users, return an error if duplicate.
            User existing = UserDelegate.getUser(username);
            if(existing == null || !checkExists)
            {
                return true;
            } else if (checkExists)
            {
                context.addMessage("signup:hidden", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username already taken", "Username already taken"));
            }
        } else
        {
            context.addMessage("signup:hidden", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match", "Passwords do not match"));
        }
        return false;
    }
    
    public String update(User original)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("in update: " + username);
        //username = original.getUsername();
        //todo check the required fields are filled out (validators should handle this)
        if(isValid(context, false))
        {//Good submit, create user object and add to map         
            //TODO create user object, add to map
            User updated = new User(username, password, firstName, lastName, new Date());
            UserDelegate.updateUser(updated);
            //For this example we are updating the exact object reference, but I am going to overwrite the userbean anyway.
            if(updated.getUsername().equals(username))
            {
                userBean.setUser(updated);
            }
            System.out.println("returning welcome");
            return "welcome";
        } else
        {
            return "update";
        }
    }
    
    public String prepareUpdate(User user)
    {
        setUser(user);
        return "update";
    }
}
