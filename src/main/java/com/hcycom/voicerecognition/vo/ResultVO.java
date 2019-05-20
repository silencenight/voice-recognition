package com.hcycom.voicerecognition.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * HTTP请求统一返回对象
 *
 * @author Silence
 * @create 2019-05-13 8:37
 */
@Data
public class ResultVO<T> implements Serializable{

    private static final long serialVersionUID = 3068837394742385883L;

    /** 错误代码. */
    private Integer code;

    /** 提示信息. */
    private String message;

    /** 具体内容. */
    private T data;
}
