package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@TableName("similar")
@Data
public class Similar {
    @TableId(value="Similar_id",type = IdType.AUTO)
    @Autowired
    private Integer similarId;
    private Integer similarNewNftId;
    private Integer similarOldNftId;
    private Float similarSimilarity;
}
