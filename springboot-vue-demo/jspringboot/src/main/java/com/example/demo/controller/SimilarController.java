package com.example.demo.controller;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.Similar;
import com.example.demo.mapper.SimilarMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLSyntaxErrorException;
@ResponseBody
@RestController
@RequestMapping("/similar")
public class SimilarController {

    @Resource
    SimilarMapper SimilarMapper;


}
