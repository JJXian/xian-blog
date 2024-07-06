package com.xian.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.xian.common.result.Result;
import com.xian.file.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 文件接口
 */
@RestController
@RequestMapping("/files")
public class FileController {
    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/files/";

    @Value("${server.port:9000}")
    private String port;

    @Value("${ip:localhost}")
    private String ip;

    /**
     * 文件上传
//     */
//    @PostMapping("/upload")
//    public Result upload(MultipartFile file) {
//        String flag;
//        synchronized (FileController.class) {
//            flag = System.currentTimeMillis() + "";
//            ThreadUtil.sleep(1L);
//        }
//        String fileName = file.getOriginalFilename();
//        try {
//            if (!FileUtil.isDirectory(filePath)) {
//                FileUtil.mkdir(filePath);
//            }
//            // 文件存储形式：时间戳-文件名
//            FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);  // ***/manager/files/1697438073596-avatar.png
//            System.out.println(fileName + "--上传成功");
//
//        } catch (Exception e) {
//            System.err.println(fileName + "--文件上传失败");
//        }
//        String http = "http://" + ip + ":" + port + "/files/";
//        return Result.success(http + flag + "-" + fileName);  //  http://localhost:9090/files/1697438073596-avatar.png
//    }


    /**
     * 上传到minio
     */
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) {
//        获取原始文件名
        String originalFilename = file.getOriginalFilename();

        //        截取最后一个.xxx
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));

        //2.上传图片到minIO中
        String fileName = UUID.randomUUID().toString().replace("-", "");

        try {
//            返回文件上传路径
             String uploadedPath = fileStorageService.uploadImgFile("",fileName+suffixName,file.getInputStream());
//            String uploadedPath = fileStorageService.upload(file.getBytes(), objectName);
            System.out.println(uploadedPath);
            return Result.success(uploadedPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 富文本文件上传
     */
    @PostMapping("/editor/upload")
    public Dict editorUpload(MultipartFile file) {
        String flag;
        synchronized (FileController.class) {
            flag = System.currentTimeMillis() + "";
            ThreadUtil.sleep(1L);
        }
        String fileName = file.getOriginalFilename();
        try {
            if (!FileUtil.isDirectory(filePath)) {
                FileUtil.mkdir(filePath);
            }
            // 文件存储形式：时间戳-文件名
            FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);  // ***/manager/files/1697438073596-avatar.png
            System.out.println(fileName + "--上传成功");

        } catch (Exception e) {
            System.err.println(fileName + "--文件上传失败");
        }
        String http = "http://" + ip + ":" + port + "/files/";
        return Dict.create().set("errno", 0).set("data", CollUtil.newArrayList(Dict.create().set("url", http + flag + "-" + fileName)));
    }


    /**
     * 获取文件
     *
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")   //  1697438073596-avatar.png
    public void avatarPath(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;
        try {
            if (StrUtil.isNotEmpty(flag)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(flag, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + flag);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
                System.out.println("文件获取成功。"+filePath +"flag"+flag);
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }




    /**
     * 删除文件
     *
     * @param flag
     */
    @DeleteMapping("/{flag}")
    public void delFile(@PathVariable String flag) {
        FileUtil.del(filePath + flag);
        System.out.println("删除文件" + flag + "成功");
    }


}
