package com.hcycom.voicerecognition.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 *
 * @author Silence
 * @create 2019-05-13 9:32
 */
@Component
@ConfigurationProperties(prefix = "baidu")
public class BaiDuConfig {

    private String APP_ID;
    private String API_KEY;
    private String SECRET_KEY;

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    public String getSECRET_KEY() {
        return SECRET_KEY;
    }

    public void setSECRET_KEY(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }
}
