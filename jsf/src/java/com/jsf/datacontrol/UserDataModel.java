package com.jsf.datacontrol;

import com.jsf.model.User;
import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author ban
 */
public class UserDataModel extends ListDataModel<User> implements SelectableDataModel<User>, Serializable
{
    public UserDataModel(){}
    
    public UserDataModel(List<User> users)
    {
        super(users);
    }

    public Object getRowKey(User t)
    {
        return t.getUsername();
    }

    public User getRowData(String username)
    {
        List<User> users = (List<User>) getWrappedData();
        if(users != null)
        {
            for(User u: users)
            {
                if(u.getUsername().equals(username))
                {
                    return  u;
                }
            }
        }
        return null;
    }

}
