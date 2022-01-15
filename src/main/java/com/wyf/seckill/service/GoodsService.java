package com.wyf.seckill.service;

import com.wyf.seckill.dao.GoodsDao;
import com.wyf.seckill.dao.SeckillUserDao;
import com.wyf.seckill.domain.Goods;
import com.wyf.seckill.domain.SeckillGoods;
import com.wyf.seckill.domain.SeckillUser;
import com.wyf.seckill.exception.GlobalException;
import com.wyf.seckill.redis.RedisService;
import com.wyf.seckill.redis.SeckillUserKey;
import com.wyf.seckill.result.CodeMsg;
import com.wyf.seckill.util.MD5Util;
import com.wyf.seckill.util.UUIDUtil;
import com.wyf.seckill.vo.GoodsVo;
import com.wyf.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: wyf
 * @date:2022/1/10 11:21
 */

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

   public GoodsVo getGoodVoByGoodsId(long goodsId){
        return goodsDao.getGoodVoByGoodsId(goodsId);
   }

    public void reduceStock(GoodsVo goods) {
        SeckillGoods sg = new SeckillGoods();
        sg.setGoodsId(goods.getId());
        goodsDao.reduceStock(sg);
    }
}
