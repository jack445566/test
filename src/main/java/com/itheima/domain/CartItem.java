package com.itheima.domain;

import lombok.Data;

/**
 * @author 传智@左
 * @date 2021/1/13 9:06
 */
@Data
public class CartItem {
    private Route route; //商品线路对象
    private Integer num; //一个商品购买的数量
    private Double subTotal; //小计金额=商品价格*数量

    //重写subTotal的get封装方法，
    //计算小计金额
    public  Double getSubTotal(){
        subTotal=route.getPrice()*num;
        return subTotal;
    }
}
