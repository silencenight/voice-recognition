package com.hcycom.voicerecognition.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * 配置文件上传的限制
 *
 * @author Silence
 * @create 2019-05-13 12:47
 */
@Configuration
public class MultipartConfig {

    @Bean
    MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/data/tmp";
        File tempFile = new File(location);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        //1MB
        DataSize maxFileSize = DataSize.of(1, DataUnit.MEGABYTES);
        //1MB
        DataSize maxRequestSize = DataSize.of(1,DataUnit.MEGABYTES);
        factory.setLocation(location);
        factory.setMaxFileSize(maxFileSize);
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }
}
