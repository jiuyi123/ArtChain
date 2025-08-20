package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@TableName("user")
@Data
public class User {
    @TableId(value="User_Id",type = IdType.AUTO)
    @Autowired
    private  Integer userId;
    private  String userName;
    private  String userPassword;
    private  String userCard;
    private  String userAddr;
    private  Float userMoney;
    private  String userPict;
    private  String userEmail;
    private  String userCode;
    private  String userIdentity;
}
