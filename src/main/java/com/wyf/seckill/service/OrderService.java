package com.wyf.seckill.service;

import com.wyf.seckill.dao.GoodsDao;
import com.wyf.seckill.dao.OrderDao;
import com.wyf.seckill.domain.OrderInfo;
import com.wyf.seckill.domain.SeckillOrder;
import com.wyf.seckill.domain.SeckillUser;
import com.wyf.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: wyf
 * @date:2022/1/10 11:21
 */

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;
    public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
         return orderDao.getSeckillOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(SeckillUser user, GoodsVo goods) {
        // OrderInfo 插入订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setOrderChannel(0);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insertOrderInfo(orderInfo);
        //SeckillOrder 插入订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderId(orderId);
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setUserId(user.getId());
        orderDao.insertSeckillOrder(seckillOrder);
        return orderInfo;
    }
}
