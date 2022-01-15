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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: wyf
 * @date:2022/1/8 13:22
 */
@Controller
@RequestMapping("seckill")
public class SeckillController {

    @Autowired
    SeckillUserService seckillUserService;

    @Autowired
    RedisService redisService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    SeckillService seckillService;

    private  final Logger log = LoggerFactory.getLogger(SeckillController.class);
    @RequestMapping("do_seckill")
    //@ResponseBody
    public String list(Model model, SeckillUser user, @RequestParam long goodsId){
        model.addAttribute("user", user);
        if(user == null){
            return "login";
        }
        GoodsVo goods = goodsService.getGoodVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            model.addAttribute("errMsg", CodeMsg.SECKILL_OVER.getMsg());
            return "seckill_fail";
        }
        //判断用户是否已经秒杀
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order != null){
            model.addAttribute("errMsg", CodeMsg.REPEAT_SECKILL.getMsg());
            return "seckill_fail";
        }
        //开始秒杀： 减库存，下订单，将秒杀记录写入订单表中，需要事务操作，写在一个秒杀的操作中
        OrderInfo orderInfo = seckillService.excuteSeckill(user, goods);
        System.out.println("orderInfo = " + orderInfo.toString());
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }

}
