package com.example.demo.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Orders;
import com.example.demo.entity.User;
import com.example.demo.mapper.OrdersMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;
import java.text.SimpleDateFormat;
@ResponseBody
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Resource
    OrdersMapper ordersMapper;
    //获取数据
    @GetMapping("/load")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search)
    {
        System.out.println("findOrderPage");
        LambdaQueryWrapper<Orders> wrapper = Wrappers.<Orders>lambdaQuery();
        if(StringUtils.isNotBlank(search)){
            wrapper.like(Orders::getOrderNftId,search);
        }
        Page<Orders> orderPage = ordersMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        //拿出结果
        System.out.println(orderPage);
        return Result.success(orderPage);
    }

    //前台数据传来并转化成order类，insert插入到数据库中
    @PostMapping("/insert")
    public Result<?> save(@RequestBody Orders order){
        System.out.println("------------------------insert order---------------");
        System.out.println(order);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        order.setOrderDate(df.format(new Date()));
        ordersMapper.insert(order);
        return Result.success();
    }
}
