package com.wyf.seckill.service;

import com.wyf.seckill.dao.SeckillUserDao;
import com.wyf.seckill.domain.SeckillUser;
import com.wyf.seckill.exception.GlobalException;
import com.wyf.seckill.redis.RedisService;
import com.wyf.seckill.redis.SeckillUserKey;
import com.wyf.seckill.result.CodeMsg;
import com.wyf.seckill.util.MD5Util;
import com.wyf.seckill.util.UUIDUtil;
import com.wyf.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: wyf
 * @date:2022/1/10 11:21
 */

@Service
public class SeckillUserService {

    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    SeckillUserDao seckillUserDao;

    @Autowired
    RedisService redisService;
    public SeckillUser getById(Long id){
        return seckillUserDao.getById(id);
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号不存在
        SeckillUser user = getById(Long.parseLong(mobile));
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
       // System.out.println("dbPass = " + dbPass);
        String saltDB = user.getSalt();
        //System.out.println("saltDB = " + saltDB);
        //System.out.println("formPass = " + formPass);
        String calcPass = MD5Util.formPassToDbPass(formPass,saltDB);
       // System.out.println("calcPass = " + calcPass);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        // 生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }
    private void addCookie(HttpServletResponse response,String token,SeckillUser user){
        // 生成cookie
        redisService.set(SeckillUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.token.expireSecond());
        cookie.setPath("/"); //表示浏览器的根目录
        response.addCookie(cookie);
    }

    public SeckillUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        } else {
             SeckillUser user = redisService.get(SeckillUserKey.token, token, SeckillUser.class);
             //延长有效期
           if(user != null){
               addCookie(response, token, user);
           }
            return user;
        }
    }
}
