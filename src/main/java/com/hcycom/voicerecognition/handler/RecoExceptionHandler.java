package com.hcycom.voicerecognition.handler;

import com.hcycom.voicerecognition.enums.ResultEnum;
import com.hcycom.voicerecognition.exception.RecoException;
import com.hcycom.voicerecognition.util.ResultUtil;
import com.hcycom.voicerecognition.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Silence
 * @create 2019-05-13 17:21
 */
@ControllerAdvice
@Slf4j
public class RecoExceptionHandler {

    /**
     * 捕获全局异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultVO errorHandler(Exception e) {
        log.error("【系统异常】",e);
        return ResultUtil.error(ResultEnum.UNKOWN_ERROR.getCode(),ResultEnum.UNKOWN_ERROR.getMessage());
    }

    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RecoException.class)
    public ResultVO myErrorHandler(RecoException e) {
        log.error("【自定义异常】",e);
        return ResultUtil.error(e.getCode(),e.getMessage());
    }
}
