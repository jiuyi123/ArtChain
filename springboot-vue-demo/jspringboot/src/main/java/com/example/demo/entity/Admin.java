package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@TableName("admin")
@Data
public class Admin {
    @TableId(value="Admin_id",type = IdType.AUTO)
    @Autowired
    private Integer adminId;
    private String adminAccount;
    private  String adminPassword;
}
