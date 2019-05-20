package com.hcycom.voicerecognition.controller;

import com.hcycom.voicerecognition.enums.ResultEnum;
import com.hcycom.voicerecognition.service.BdApiService;
import com.hcycom.voicerecognition.util.AudioUtil;
import com.hcycom.voicerecognition.util.KeyUtil;
import com.hcycom.voicerecognition.util.ResultUtil;
import com.hcycom.voicerecognition.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传
 * 包含单文件上传、多文件上传
 *w文件写入借助commons-io中的FileUtils.copyInputStreamToFile(inputStream,file)
 *
 * @author Silence
 * @create 2019-05-13 8:34
 */
@RestController
@Slf4j
@RequestMapping("/speech")
public class FileUploadController {

    @Value("${file.upload-folder}")
    private String uploadFolder;

    @Value("${file.destination-folder}")
    private String destFolder;

    @Autowired
    private BdApiService bdApiService;

    /**
     * 接口服务测试接口
     * @return
     */
    @GetMapping("/test")
    public ResultVO hello() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("接口服务测试正常");
        return ResultUtil.success(resultVO);
    }

    /**
     * 语音识别测试接口
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/upload3")
    public ResultVO upload3(HttpServletRequest request) throws Exception{
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        byte[] pcmBytes = AudioUtil.mp3ConvertPcm2(file.getInputStream());
        JSONObject asrRes = bdApiService.speechBdApi(pcmBytes);
        return ResultUtil.success(asrRes.toString());
    }

    /**
     * 单文件上传，然后语音识别
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public ResultVO upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        JSONObject asrRes = null;
        try{
            log.info("【用户ID】 - {}",request.getParameter("userId"));
            log.info("【文件类型】 - {}",file.getContentType());
            log.info("【文件名称】 - {}",file.getOriginalFilename());
            log.info("【文件大小】 - {}",file.getSize());

            if (file.getContentType().equals("audio/mpeg")) {
                //mp3转pcm
                byte[] pcmBytes = AudioUtil.mp3ConvertPcm2(file.getInputStream());
                //调用百度语音识别api
                asrRes = bdApiService.speechBdApi(pcmBytes);
            }else{
                //调用百度语音识别api
                asrRes = bdApiService.speechBdApi(FileCopyUtils.copyToByteArray(file.getInputStream()));
            }

            if (asrRes.getInt("err_no") != 0 || asrRes == null) {
                return ResultUtil.error(ResultEnum.RECOGNITION_FAIL.getCode(),ResultEnum.RECOGNITION_FAIL.getMessage());
            }else{
                //语音识别成功，保存源文件
                String sourceDir = System.getProperty("user.dir") + uploadFolder;
                file.transferTo(new File(sourceDir + KeyUtil.genUniqueKey() + "_"+ file.getOriginalFilename()));
                //语音识别成功，保存识别结果
                String destFile = System.getProperty("user.dir") + destFolder + "result.txt";
                FileWriter fo = new FileWriter(destFile,true);
                fo.write(KeyUtil.genUniqueKey() +" : "+asrRes.toString() + "\n");
                fo.close();
            }
        }catch (Exception e){
            log.error("【文件上传错误】 - {}",e);
        }

        return ResultUtil.success(asrRes.toString());
    }

    /**
     * 多文件上传，语音识别
     * @param files
     * @return
     */
    @PostMapping("/uploads")
    public ResultVO uploads(@RequestParam("files") MultipartFile[] files,HttpServletRequest request) throws IOException{
        log.info("【用户ID】 - {}",request.getParameter("userId"));
        List<String> list = new ArrayList<String>();
        for (MultipartFile file : files) {
            log.info("【文件类型】 - {}",file.getContentType());
            log.info("【文件名称】 - {}",file.getOriginalFilename());
            log.info("【文件大小】 - {}",file.getSize());
            JSONObject asrRes = bdApiService.speechBdApi(FileCopyUtils.copyToByteArray(file.getInputStream()));
            if ("success.".equals(asrRes.getString("err_msg"))) {
                //语音识别成功，保存源文件
                String userDir = System.getProperty("user.dir") + uploadFolder;
                file.transferTo(new File(userDir + file.getOriginalFilename()));
                //语音识别成功，保存识别结果
                String destFile = System.getProperty("user.dir") + destFolder + "result.txt";
                FileWriter fo = new FileWriter(destFile,true);
                fo.write(KeyUtil.genUniqueKey() +" : "+asrRes.toString() + "\n");
                fo.close();
            }
            list.add(asrRes.toString());
        }
        return ResultUtil.success(list);
    }
}
