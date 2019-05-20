package com.hcycom.voicerecognition.exception;

import com.hcycom.voicerecognition.enums.ResultEnum;
import lombok.Getter;

/**
 * 自定义异常处理
 *
 * @author Silence
 * @create 2019-05-13 17:05
 */
@Getter
public class RecoException extends RuntimeException {

    private Integer code;

    public RecoException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public RecoException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
