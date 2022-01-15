package com.wyf.seckill.vo;

import com.wyf.seckill.domain.Goods;

import java.util.Date;

/**
 * @author: wyf
 * @date:2022/1/12 12:29
 */
public class GoodsVo extends Goods {
    private Double seckillPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

    public Double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(Double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
