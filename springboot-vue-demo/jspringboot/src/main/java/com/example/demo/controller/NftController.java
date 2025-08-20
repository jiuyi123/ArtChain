package com.example.demo.controller;
import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.FingerPrint;
import com.example.demo.common.Result;
import com.example.demo.entity.Nft;
import com.example.demo.mapper.NftMapper;
import com.example.demo.entity.Similar;
import com.example.demo.mapper.SimilarMapper;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/nft")
public class NftController {
    
    class SimilarNfts
    {
        Nft newNfts;
        Nft oldNfts;
        float Similarity=0;
    }
    
    Nft currentNft = new Nft();
    @Resource
    NftMapper nftMapper;
    @Resource
    SimilarMapper similarMapper;



    //获取商品数据
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search)
    {
        System.out.println("findnftpage");
        LambdaQueryWrapper<Nft> wrapper = Wrappers.<Nft>lambdaQuery();
        if(StringUtils.isNotBlank(search)){
            wrapper.like(Nft::getNftName,search);
        }
        Page<Nft> nftPage = nftMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        //拿出结果
        System.out.println(nftPage);
        return Result.success(nftPage);
    }

    //前台数据传来并转化成user类，insert插入到数据库中
    @PostMapping("/insert")
    public Result<?> save(@RequestBody Nft nft){
        System.out.println("------------------insert nft------------------");
        nft.setNftCheck(0);
        nftMapper.insert(nft);
        return Result.success(nft.getNftId());
    }

    //更新作品数据
    @PutMapping("/update")
    public Result<?> update(@RequestBody Nft nft){
        System.out.println("------------------update nft------------------");
        nftMapper.updateById(nft);
        return Result.success();
    }

    //更新相似作品数据
    @PutMapping("/similar/update")
    public Result<?> similarupdate(@RequestBody Nft nft){
        System.out.println("------------------similar update nft--------------------");
        nftMapper.updateById(nft);
        //删除查重表
        LambdaQueryWrapper<Similar> wrapper = Wrappers.<Similar>lambdaQuery().eq(Similar::getSimilarNewNftId,nft.getNftId());
        similarMapper.delete(wrapper);
        return Result.success();
    }

    //处理交易改变nft
    @PostMapping("/trans")
    public Result<?> trans(@RequestBody Nft nft){
        System.out.println("------------------trans nft------------------");
        nftMapper.updateById(nft);
        return Result.success();
    }
    //加载我的艺术品
    @GetMapping("/myartload")
    public Result<?> myArt(@RequestParam(defaultValue = "1") Integer userId,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize)
    {
        System.out.println("------------------nft myartload--------------");
        LambdaQueryWrapper<Nft> wrapper = Wrappers.<Nft>lambdaQuery();
        wrapper.like(Nft::getNftOwnerId,userId);
        Page<Nft> nftPage;
        nftPage = nftMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        //拿出结果
        return Result.success(nftPage);
    }
    //获取商品数据
    @GetMapping("/marketload")
    public Result<?> market(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize)
    {
        System.out.println("------------------nft marketload------------------");
        LambdaQueryWrapper<Nft> wrapper = Wrappers.<Nft>lambdaQuery();
        //对已上架的艺术品显示
        wrapper.eq(Nft::getNftSta,"1");
        Page<Nft> nftPage;
        nftPage = nftMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        //拿出结果
        return Result.success(nftPage);
    }
    //获取需要审核内容的艺术品
    @GetMapping("/check")
    public Result<?> checkPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search)
    {
        System.out.println("------------------check nft------------------");
        LambdaQueryWrapper<Nft> wrapper = Wrappers.<Nft>lambdaQuery().eq(Nft::getNftCheck,0);
        if(StringUtils.isNotBlank(search)){
            wrapper.like(Nft::getNftName,search);
        }
        Page<Nft> nftPage = nftMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        //拿出结果
        return Result.success(nftPage);
    }

    //创作者上传艺术品,得到similar表
    @PostMapping("/create")
    public Result<?> create(@RequestBody Nft nft){
        System.out.println("------------------create nft------------------");
        //判断是否可以进行查重（只对img/jpeg格式查重）
        if(nft.getNftFingerPrint()!="")
        {
            //找出相似的艺术品
            LambdaQueryWrapper<Nft> wrapper = Wrappers.<Nft>lambdaQuery().eq(Nft::getNftCheck,0).like(Nft::getNftFingerPrint,1);
            List<Nft> nftList = nftMapper.selectList(wrapper);
            int listLen = nftList.size();
            System.out.println(listLen);
            for(int i=0;i<listLen;i++)
            {
                String oldNftFingerPrint = nftList.get(i).getNftFingerPrint();
                int oldNftId = nftList.get(i).getNftId();
                float similarity = FingerPrint.compare(nft.getNftFingerPrint(),oldNftFingerPrint);
                System.out.println(similarity);
                if(similarity >= 0.9999)
                {
                    //相同的艺术品直接拒绝
                    return Result.error("1","该作品已存在");
                }
                else if(similarity >= 0.9)
                {
                    //插入艺术品到nft表中
                    nft.setNftCheck(0);
                    nftMapper.insert(nft);
                    //插入相似的艺术品到similar表，送入第三方审核
                    Similar similar = new Similar();
//                    similar.setSimilarId(10);
                    similar.setSimilarSimilarity(similarity);
                    similar.setSimilarOldNftId(oldNftId);
                    similar.setSimilarNewNftId(nft.getNftId());
//                    System.out.println(similar);
                    similarMapper.insert(similar);
                    return Result.success(nft.getNftId());
                }
            }
            System.out.println("查重但没有相似的");
            nftMapper.insert(nft);
        }
        else{
            System.out.println("不需要查重");
            nftMapper.insert(nft);
        }
        return Result.success(nft.getNftId());
    }
    //获取需要查重的艺术品
    @GetMapping("/similar")
    public Result<?> similarPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String search)
    {
        System.out.println("-------------similar nftpage--------------------");

        LambdaQueryWrapper<Similar> similarWrapper = Wrappers.<Similar>lambdaQuery();
        //获取similarList
        List<SimilarNfts> similarNftsList = new ArrayList<>();//相似作品完整列表
        List<Similar> similarList = similarMapper.selectList(similarWrapper);//相似作品简表：similar表
        int similarListLen = similarList.size();//相似作品简表长度
        System.out.println(similarListLen);
        for(int i=0;i<similarListLen;i++) 
        {
            SimilarNfts similarNfts = new SimilarNfts();
            similarNfts.newNfts = nftMapper.selectById( similarList.get(i).getSimilarNewNftId());
            similarNfts.oldNfts = nftMapper.selectById( similarList.get(i).getSimilarOldNftId());
            similarNfts.Similarity = similarList.get(i).getSimilarSimilarity();
            boolean add = similarNftsList.add(similarNfts);
            System.out.println(add);
        }

        System.out.println(similarNftsList);
        System.out.println("similarNftsList");


        //List 转化为 Page
        PagedListHolder<SimilarNfts> similarNftsPage = new PagedListHolder<>(similarNftsList);
        similarNftsPage.setPageSize(pageSize);
        similarNftsPage.setPage(pageNum);
        System.out.println(similarNftsPage);
        //返回根据similarList得到相似的两对NFT的page
        return Result.success(similarNftsPage);
    }
    //获取作品数据
}
