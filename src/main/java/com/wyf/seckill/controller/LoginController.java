package com.wyf.seckill.controller;

import com.wyf.seckill.result.Result;
import com.wyf.seckill.service.SeckillUserService;
import com.wyf.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author: wyf
 * @date:2022/1/8 13:22
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    SeckillUserService seckillUserService;

    private  final Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * 使用jMeter进行压测：5000个线程， 0秒启动，循环10次， 测试结果 QPS为：1104 /s
     *
     */
    @RequestMapping("to_login")
    public String toLogin(){
        return "login";
    }

    @ResponseBody
    @RequestMapping("do_login")
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        log.info(loginVo.toString());
        //使用JSR303参数校验，就不用这种校验方法了
//        //参数校验
//        String inputPass = loginVo.getPassword();
//        String mobile = loginVo.getMobile();
//        if(StringUtils.isEmpty(inputPass)){
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//        if(StringUtils.isEmpty(mobile)){
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        }
//        if(!ValidateUtil.isMobile(mobile)){
//            return Result.error(CodeMsg.MOBILE_ERROR);
//        }
        //登录 由于异常抛出，所以直接返回成功
//        CodeMsg codeMsg = seckillUserService.login(loginVo);
//        if(codeMsg.getCode() == 0){
//            return Result.success(true);
//        }else{
//            return Result.error(codeMsg);
//        }
       String token = seckillUserService.login(response, loginVo);
        return Result.success(token);
    }

}
