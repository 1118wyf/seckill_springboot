package com.wyf.seckill.controller;

import com.wyf.seckill.domain.SeckillUser;
import com.wyf.seckill.redis.RedisService;
import com.wyf.seckill.service.GoodsService;
import com.wyf.seckill.service.SeckillUserService;
import com.wyf.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: wyf
 * @date:2022/1/8 13:22
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    SeckillUserService seckillUserService;

    @Autowired
    RedisService redisService;
    @Autowired
    GoodsService goodsService;
    private  final Logger log = LoggerFactory.getLogger(GoodsController.class);
    @RequestMapping("to_list")
    //@ResponseBody
    public String list(Model model, SeckillUser user){
//                         @CookieValue(value = SeckillUserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
//                         @RequestParam(value = SeckillUserService.COOKIE_NAME_TOKEN, required = false) String paramToken,
//                         HttpServletResponse response

//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
//            return "login";
//        } else {
//            String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
//            SeckillUser user = seckillUserService.getByToken(response, token);

//        }
        model.addAttribute("user", user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }
    @RequestMapping("to_detail/{goodsId}")
    //@ResponseBody
    public String detail(Model model,SeckillUser user,
                         @PathVariable("goodsId")long goodsId) {
        //id snowflake
        model.addAttribute("user", user);
        //根据商品Id查询
        GoodsVo goods = goodsService.getGoodVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        //
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int seckillState = 0;
        int remainSeconds = 0;
        if(now < startAt){ //秒杀未开始
            seckillState = 0;
            remainSeconds = (int)(startAt - now) /1000;
        } else if(now > endAt){  //秒杀结束
            seckillState = 2;
            remainSeconds = -1;
        } else { //秒杀进行中
            seckillState = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillState", seckillState);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }

}
