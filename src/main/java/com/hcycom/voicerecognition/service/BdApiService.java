package com.hcycom.voicerecognition.service;

import org.json.JSONObject;

/**
 * @author Silence
 * @create 2019-05-13 8:55
 */
public interface BdApiService {

    /**
     * 百度语音识别
     * @param pcmBytes
     * @return
     */
    JSONObject speechBdApi(byte[] pcmBytes);
}
