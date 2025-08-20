package com.example.demo.controller;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.server.HttpServerResponse;
import com.example.demo.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import com.example.demo.common.FingerPrint;

@RestController
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port;
    private static final String ip = "http://localhost";

    class Platform{
        public String nftHash;
        public String nftLoc;
        public String nftFingerPrint;
        public String nftUid;
    }
    /*
     *上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        System.out.println("----------------upload-----------------------");
        /**********MultipartFile上传****************/
        //获取源文件名称
        String originalFilename=file.getOriginalFilename();
        //创建文件的UID
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath=System.getProperty("user.dir")+"/jspringboot/src/main/resources/files/"+flag+'_'+originalFilename;//获取根路径
        //写入文件
        FileUtil.writeBytes(file.getBytes(),rootFilePath);//把文件写入上传路径
        //返回信息
        String url = ip+":"+port+"/files/"+flag;

        System.out.println("---------------------MultipartFile转化为File------------------------");
//        System.out.println(rootFilePath);
        File fileF = new File(rootFilePath);
//        System.out.println(fileF.length());


        //File转化为FileInputStream
        System.out.println("---------------------File转FileInputStream------------------------");
        FileInputStream fileInputStream = new FileInputStream(fileF);
        int len = fileInputStream.available();
        byte [] bytes=new byte[len];
        fileInputStream.read(bytes);
        //NftHash
        String NftHash = String.valueOf(new String(bytes).hashCode());
        System.out.println(NftHash);
        fileInputStream.close();

        //File生成FileFingerPrint
        String fileFingerPrint="";
        if(file.getContentType().equals("image/jpeg"))
        {
            System.out.println("---------------------artFingerPrint------------------------");
            FingerPrint NftFingerPrint=new FingerPrint(ImageIO.read(fileF));
            fileFingerPrint = String.valueOf(String.valueOf(NftFingerPrint));
        }

        Platform platform=new Platform();
        if(NftHash.startsWith("-")) {
            NftHash = NftHash.substring(1,NftHash.length());
        }
            platform.nftHash=NftHash;
        platform.nftLoc=url;
        platform.nftUid=flag;
        platform.nftFingerPrint=fileFingerPrint;
        return Result.success(platform);
    }
    /*
     *下载接口
     * @param flag
     * @param response
     * @throws IOException
     */
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag,HttpServletResponse response){
        OutputStream os;//新建一个输出流对象
        String basePath = System.getProperty("user.dir")+"/jspringboot/src/main/resources/files/";//定义文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);//获取所有的文件名称

        String fileName = fileNames.stream().filter(name->name.contains(flag)).findAny().orElse("");//找到跟参数一致的文件

        try
        {
            if(StrUtil.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath+fileName);//通过文件路径读取文件字节流
                os=response.getOutputStream(); //通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

