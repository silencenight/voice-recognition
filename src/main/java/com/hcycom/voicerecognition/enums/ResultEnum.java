package com.hcycom.voicerecognition.enums;

import lombok.Getter;

/**
 * @author Silence
 * @create 2019-05-13 17:00
 */
@Getter
public enum ResultEnum {

    UNKOWN_ERROR(-1,"未知错误"),
    FILE_NOT_EXISTS(10,"上传失败，请选择文件。"),
    MAXIMUM_UPLOAD_SIZE_EXCEEDED(11,"超过上传文件最大限制"),
    RECOGNITION_FAIL(12,"语音识别失败"),
        ;
    private Integer code;
    private String message;
    ResultEnum(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
