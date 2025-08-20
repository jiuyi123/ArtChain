package com.example.demo.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.common.Result;
import com.example.demo.mapper.UserMapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@ResponseBody
@RestController
@RequestMapping("/user")
public class UserController {
    User currentUser = new User();
    @Resource
    UserMapper userMapper;
    //前台数据传来并转化成user类，insert插入到数据库中
    @GetMapping("/personSpace")
    public Result<?> userInfo()
    {
//        System.out.println("person");
//        System.out.println(currentUser);
        return Result.success(currentUser);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        System.out.println("user login");
        System.out.println(user);
        //查询数据库中的数据
        User res= userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName,user.getUserName()).eq(User::getUserPassword,user.getUserPassword()));
//        System.out.println(res);
        if(res==null) {
            return Result.error("-1", "用户名或者密码错误");
        }
        System.out.println("Login Sucessful");
        currentUser=res;
        return Result.success();
    }
    //注册
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        //判断用户名是否存在
        System.out .println("user register");
        System.out .println(user);
        User res= userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName,user.getUserName()));
        if(res!=null)
        {
            return Result.error("-1", "用户名重复");
        }
        if(user.getUserPassword()==null)
        {
            user.setUserPassword("123456");
        }
        String flag = IdUtil.fastSimpleUUID();
        user.setUserCode(flag);
        userMapper.insert(user);
        System.out.println("Register Sucessful");
        return Result.success();
    }

    //获取数据
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search)
    {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StringUtils.isNotBlank(search)){
            wrapper.like(User::getUserName,search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        //拿出结果
        return Result.success(userPage);
    }

    //前台数据传来并转化成user类，insert插入到数据库中
    @PostMapping("insert")
    public Result<?> save(@RequestBody User user){
        System.out.println("insert:"+user);
        if(user.getUserPassword()==null)
        {
            user.setUserPassword(user.getUserName());
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PutMapping("update")
    public Result<?> update(@RequestBody User user){
        System.out.println("update:"+user);
        userMapper.updateById(user);
        return Result.success();
    }

    @GetMapping("/trans")
    public Result<?> trans(@RequestParam(defaultValue = "0") Integer sellId,
                           @RequestParam(defaultValue = "0") Integer buyId,
                           @RequestParam(defaultValue = "0") Integer price){
//        System.out.println(sellId);
//        System.out.println(buyId);
//        System.out.println(price);
        User seller= userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserId,sellId));
        User buyer= userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserId,buyId));
//        System.out.println(seller);
//        System.out.println(buyer);
        seller.setUserMoney(seller.getUserMoney()+price);
        buyer.setUserMoney(buyer.getUserMoney()-price);
        currentUser=buyer;
//        System.out.println(seller.getUserMoney());
//        System.out.println(buyer.getUserMoney());

         userMapper.updateById(seller);
         userMapper.updateById(buyer);
//        userMapper.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")//占位符
    public Result<?> delete(@PathVariable Long id) {
        userMapper.deleteById(id);
        return Result.success();
    }
}
