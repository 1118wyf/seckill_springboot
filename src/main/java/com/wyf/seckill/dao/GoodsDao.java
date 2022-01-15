package com.wyf.seckill.dao;

import com.wyf.seckill.domain.Goods;
import com.wyf.seckill.domain.SeckillGoods;
import com.wyf.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: wyf
 * @date:2022/1/12 12:26
 */

@Mapper
public interface GoodsDao {

    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date,sg.seckill_price from goods g left join seckill_goods sg on g.id = sg.goods_id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date,sg.seckill_price from seckill_goods sg left join goods g on sg.goods_id = #{goodsId} where g.id = #{goodsId}")
    public GoodsVo getGoodVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update seckill_goods set stock_count = stock_count-1 where goods_id = #{goodsId}")
    public int reduceStock(SeckillGoods sg);
}
