package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@TableName("orders")
@Data
public class Orders {
    @TableId(value="Order_id",type = IdType.AUTO)
    @Autowired
    private Integer orderId;
    private Integer orderNftId;
    private Integer orderSaleId;
    private Integer orderBuyerId;
    private String orderDate;
    private String orderPrice;
    private Integer orderSta;
    private String orderHash;
}
