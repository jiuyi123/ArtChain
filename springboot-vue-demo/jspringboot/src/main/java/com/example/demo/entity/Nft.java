package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@TableName("nft")
@Data
public class Nft{
    @TableId(value="Nft_Id",type = IdType.AUTO)
    @Autowired
    private  Integer nftId;

    private  String nftHash;
    private  String nftName;
    private  String nftDescri;
    private  Integer nftPrice;
    private  Integer nftWriterId;
    private  Integer nftOwnerId;
    private  Integer nftCheck;
    private  Integer nftSta;
    private  String nftLoc;
    private  Integer nftClass;
    private String nftFingerPrint;
    private String nftUid;

}
