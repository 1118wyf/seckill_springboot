package com.wyf.seckill.service;

import com.wyf.seckill.dao.UserDao;
import com.wyf.seckill.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: wyf
 * @date:2022/1/8 14:50
 */
@Service
public class UserService {

    @Autowired
    public UserDao userDao;
    public User getById(@Param("id") int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean tran() {
        User user1 = new User();
        user1.setId(4);
        user1.setName("4");
        User user2 = new User();
        user2.setId(1);
        user2.setName("1");
        userDao.insert(user1);
        userDao.insert(user2);
        return true;
    }
}
