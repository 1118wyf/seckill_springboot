package com.wyf.seckill.controller;

import com.wyf.seckill.domain.User;
import com.wyf.seckill.redis.RedisService;
import com.wyf.seckill.redis.UserKey;
import com.wyf.seckill.result.CodeMsg;
import com.wyf.seckill.result.Result;
import com.wyf.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: wyf
 * @date:2022/1/8 13:22
 */
@Controller
@RequestMapping("demo")
public class SampleController {

    @Autowired
    public UserService userService;

    @Autowired
    public RedisService redisService;

    @ResponseBody
    @RequestMapping("hello")
    public String hello(){
        return "hello seckill";
    }
    //1、rest api json的输出 2、页面

    @ResponseBody
    @RequestMapping("helloSuccess")
    public Result<String> helloSuccess(){
        return Result.success("hello success");
       // return new Result(0,"success", "hello success");
    }

    @ResponseBody
    @RequestMapping("helloError")
    public Result<String> helloError(){
        return Result.error(CodeMsg.SERVER_ERROR);
       // return new Result(500100,"session失效");
    }
    @RequestMapping("thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","imooc");
        return "thymeleaf";
    }

    @ResponseBody
    @RequestMapping("getUser")
    public Result<User> getUser(){
       return Result.success(userService.getById(1));
    }
    @ResponseBody
    @RequestMapping("transaction")
    public Result<Boolean> transaction(){
        userService.tran();
        return Result.success(true);
    }

//    @ResponseBody
//    @RequestMapping("redis/get")
//    public Result<Long> redisGet(){
//      Long v1 =  redisService.get("testGet",Long.class);
//        return Result.success(v1);
//    }
//加前缀之后的key 的测试
    @ResponseBody
    @RequestMapping("redis/get")
    public Result<User> redisGet(){
        User user =  redisService.get(UserKey.getById,"" + 4, User.class);
        return Result.success(user);
    }

    @ResponseBody
    @RequestMapping("redis/set")
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(4);
        user.setName("4444");
        redisService.set(UserKey.getById,"" + 4, user);
       // String str = redisService.get(UserKey.getByName, "testSet", String.class);
        return Result.success(true);
    }
}
