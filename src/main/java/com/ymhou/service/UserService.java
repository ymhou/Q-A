package com.ymhou.service;

import com.ymhou.dao.UserDao;
import com.ymhou.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ymhou on 2017/3/25.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getUser(int id){
        return userDao.selectById(id);
    }
}
