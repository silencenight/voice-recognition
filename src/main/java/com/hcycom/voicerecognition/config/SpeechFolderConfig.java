package com.hcycom.voicerecognition.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 创建语音识别的源目录和结果目录
 *
 * @author Silence
 * @create 2019-05-16 11:32
 */
@Component
public class SpeechFolderConfig {

    /** 文件上传存储目录. */
    @Value("${file.upload-folder}")
    private String uploadFolder;

    /** 语音识别结果存储目录. */
    @Value("${file.destination-folder}")
    private String destinationFolder;

    @Bean
    private void init() throws Exception{
        String uploadDir = System.getProperty("user.dir") + uploadFolder;
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        String destDir = System.getProperty("user.dir") + destinationFolder;
        File destFolder = new File(destDir);
        File resultFile = new File(destDir + "result.txt");
        if (!destFolder.exists()) {
            destFolder.mkdirs();
            resultFile.createNewFile();
        }
    }
}
