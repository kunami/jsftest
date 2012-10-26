package com.jsf.datacontrol;

import com.jsf.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ban
 */
@ManagedBean(name="users")
@ViewScoped
public class UserSearchBean implements Serializable
{
    private User selected;
    private UserDataModel model;

    public UserSearchBean()
    {
        Map<String, User> users = UserDelegate.getUsers();
        List<User> list = new ArrayList<User>(users.values());
        model = new UserDataModel(list);
    }

    public User getSelected()
    {
        return selected;
    }

    public void setSelected(User selected)
    {
        this.selected = selected;
    }

    public UserDataModel getModel()
    {
        return model;
    }
    
}
