package com.wyf.seckill.controller;

import com.wyf.seckill.domain.SeckillUser;
import com.wyf.seckill.redis.GoodsKey;
import com.wyf.seckill.redis.RedisService;
import com.wyf.seckill.service.GoodsService;
import com.wyf.seckill.service.SeckillUserService;
import com.wyf.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;
    private  final Logger log = LoggerFactory.getLogger(GoodsController.class);
    @RequestMapping(value = "to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, SeckillUser user){
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
        //从缓存中取静态页面缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);

        //手动渲染
        WebContext ctx = new WebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
        //return "goods_list";
    }
    @RequestMapping(value = "to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response,Model model,SeckillUser user,
                         @PathVariable("goodsId")long goodsId) {
        //id snowflake
        model.addAttribute("user", user);
        //从缓存中取静态页面缓存
        String html = redisService.get(GoodsKey.getGoodsList, "" + goodsId, String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
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
        //手动渲染
        WebContext ctx = new WebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,"" + goodsId, html);
        }
        return html;
       // return "goods_detail";
    }

}
