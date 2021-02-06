package com.itheima.domain;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 传智@左
 * @date 2021/1/13 9:09
 */
@Data
public class Cart {
    private Integer cartNum;  //购买所有商品的数量
    private Double cartTotal;  //总金额

    //购物项集合
    //key:线路的rid
    //value: 购物项对象
    private LinkedHashMap<Integer,CartItem> cartItemMap=new LinkedHashMap<>();

    //重写封装总数量：循环累加每个购物项中的数量求和
    public Integer getCartNum(){
        cartNum=0;
        Set<Map.Entry<Integer, CartItem>> entrySet = cartItemMap.entrySet();
        for (Map.Entry<Integer, CartItem> entry : entrySet) {
            CartItem cartItem = entry.getValue();
            cartNum += cartItem.getNum();
        }
            return cartNum;
    }
    //总金额
    public Double getCartTotal(){
        cartTotal=0.00;
        for (Map.Entry<Integer, CartItem> entry : cartItemMap.entrySet()) {
            CartItem cartItem = entry.getValue();
            cartTotal += cartItem.getSubTotal();
        }
        return cartTotal;
    }


}
