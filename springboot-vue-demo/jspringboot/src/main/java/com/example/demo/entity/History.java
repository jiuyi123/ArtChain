package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@TableName("history")
@Data
public class History {
    @TableId(value="History_nft_id",type = IdType.AUTO)
    @Autowired
    private Integer historyNftId;
    private Integer historyNumber;
    private Integer historyOrderId;
}
