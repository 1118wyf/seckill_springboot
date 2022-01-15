package com.wyf.seckill.service;

import com.wyf.seckill.domain.OrderInfo;
import com.wyf.seckill.domain.SeckillUser;
import com.wyf.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: wyf
 * @date:2022/1/13 12:29
 */

@Service
public class SeckillService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo excuteSeckill(SeckillUser user, GoodsVo goods) {
        //减库存、下订单、写入秒杀订单
        //减库存,goodsService
        goodsService.reduceStock(goods);

        //下订单 orderService
        return orderService.createOrder(user, goods);

    }
}
