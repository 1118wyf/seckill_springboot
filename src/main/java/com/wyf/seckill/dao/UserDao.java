package com.wyf.seckill.dao;

import com.wyf.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: wyf
 * @date:2022/1/8 14:46
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") int id);

    @Insert("insert into user (id, name) values(#{id}, #{name})")
    public int insert(User user);
}
