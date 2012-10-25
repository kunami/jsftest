package com.jsf.datacontrol;

import com.jsf.model.User;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ban
 */
public class UserDAO 
{
    //Fake database
    private static Map<String, User> users = new HashMap<String, User>();
    static
    {
        users.put("bubba", new User("bubba", "gump", "Bubba", "Gump", new Date()));
        users.put("test", new User("test", "test", "Test", "User", new Date()));
        users.put("alpha", new User("alpha", "beta", "Gamma", "Delta", new Date()));        
    }
    
    protected final User getUser(String username)
    {
        return users.get(username);
    }

    protected final void insertUser(User user)
    {
        users.put(user.getUsername(), user);
    }
}
