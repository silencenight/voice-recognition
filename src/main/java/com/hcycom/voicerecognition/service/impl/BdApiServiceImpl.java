package com.hcycom.voicerecognition.service.impl;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.util.Util;
import com.hcycom.voicerecognition.config.BaiDuConfig;
import com.hcycom.voicerecognition.service.BdApiService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;

/**
 * 百度语音识别
 *
 * @author Silence
 * @create 2019-05-13 8:56
 */
@Service
@Slf4j
public class BdApiServiceImpl implements BdApiService {

    @Autowired
    private BaiDuConfig baiDuProperties;

    /**
     * 百度语音识别
     * 格式支持：pcm（不压缩）、wav（不压缩，pcm编码）、amr（压缩格式）。推荐pcm 采样率 ：16000 固定值。 编码：16bit 位深的单声道。
     * @param pcmBytes
     * @return
     */
    @Override
    public JSONObject speechBdApi(byte[] pcmBytes) {
        //初始化一个AipSpeech
        AipSpeech client = new AipSpeech(baiDuProperties.getAPP_ID(), baiDuProperties.getAPI_KEY(),baiDuProperties.getSECRET_KEY());

        //可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(6000);

        //调用百度语音识别API
        JSONObject asrRes = client.asr(pcmBytes, "pcm", 16000, null);
        log.info("【语音识别】 -  {}",asrRes);
        return asrRes;
    }


    public static void main(String[] args) throws Exception{
        //设置APPID/AK/SK
        final String APP_ID = "16214243";
        final String API_KEY = "0RuBP7AMKBqTxMQIa583KK3Q";
        final String SECRET_KEY = "sllGd0wYYTiTmXIwRSRGjRZmFSpgOXzL";
        System.out.println("=====对本地语音文件进行识别");
        // 对本地语音文件进行识别
        String path = "E:\\16k.pcm";
        AipSpeech client = new AipSpeech(APP_ID,API_KEY,SECRET_KEY);
        JSONObject asrRes = client.asr(path, "pcm", 16000, null);
        System.out.println(asrRes);

        System.out.println("=====对语音二进制数据进行识别");
        // 对语音二进制数据进行识别
        byte[] data = Util.readFileByBytes(path);
        JSONObject asrRes2 = client.asr(data, "pcm", 16000, null);
        System.out.println(asrRes2);

        System.out.println("====方法测试");
        BdApiServiceImpl bdApiService = new BdApiServiceImpl();
        bdApiService.speechBdApi(Util.readFileByBytes(path));
    }
}
