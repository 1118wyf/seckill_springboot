package com.wyf.seckill.controller;

import com.wyf.seckill.domain.OrderInfo;
import com.wyf.seckill.domain.SeckillOrder;
import com.wyf.seckill.domain.SeckillUser;
import com.wyf.seckill.redis.RedisService;
import com.wyf.seckill.result.CodeMsg;
import com.wyf.seckill.result.Result;
import com.wyf.seckill.service.GoodsService;
import com.wyf.seckill.service.OrderService;
import com.wyf.seckill.service.SeckillService;
import com.wyf.seckill.service.SeckillUserService;
import com.wyf.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: wyf
 * @date:2022/1/8 13:22
 */
@Controller
@RequestMapping("user")
public class UserController {
    @RequestMapping("info")
    @ResponseBody
    public Result<SeckillUser> list(Model model, SeckillUser user){
        model.addAttribute("user", user);
        return Result.success(user);
    }

}
