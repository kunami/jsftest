package com.jsf.datacontrol;

import com.jsf.model.User;

/**
 *
 * @author ban
 */
public class UserDelegate 
{
    private static final UserDAO dao = new UserDAO();
    
    public static User getUser(String username)
    {
        return dao.getUser(username);
    }
    
    public static void insertUser(User user)
    {
        dao.insertUser(user);
    }
}
