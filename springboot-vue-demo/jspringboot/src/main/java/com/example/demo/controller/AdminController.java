package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLSyntaxErrorException;
@ResponseBody
@RestController
@RequestMapping("/admin")
public class AdminController {
    Admin currentAdmin = new Admin();

    @Resource
    AdminMapper AdminMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin admin){
        System.out.println("admin login");
        System.out.println(admin);
        //查询数据库中的数据
        Admin res= AdminMapper.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAdminAccount,admin.getAdminAccount()).eq(Admin::getAdminPassword,admin.getAdminPassword()));
        System.out.println(res);
        if(res==null) {
            return Result.error("-1", "用户名或者密码错误");
        }
        System.out.println("Admin Login Sucessful");
        currentAdmin=res;
        return Result.success();
    }
}
