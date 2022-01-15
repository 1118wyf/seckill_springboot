package com.wyf.seckill.dao;

import com.wyf.seckill.domain.OrderInfo;
import com.wyf.seckill.domain.SeckillOrder;
import org.apache.ibatis.annotations.*;

/**
 * @author: wyf
 * @date:2022/1/13 11:46
 */
@Mapper
public interface OrderDao {

    @Select("select * from seckill_order where user_id = #{userId} and goods_id = #{goodsId}")
    public SeckillOrder getSeckillOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info (user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)" +
            "values(#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    public long insertOrderInfo(OrderInfo orderInfo);

    @Insert("insert into seckill_order (user_id, order_id, goods_id) values(#{userId}, #{orderId}, #{goodsId})")
    public int insertSeckillOrder(SeckillOrder seckillOrder);
}
