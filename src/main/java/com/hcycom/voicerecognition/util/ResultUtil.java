package com.hcycom.voicerecognition.util;

import com.hcycom.voicerecognition.vo.ResultVO;

/**
 * @author Silence
 * @create 2019-05-13 8:40
 */
public class ResultUtil {

    /**
     *请求成功
     * @param object
     * @return
     */
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("success");
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 请求成功
     * @return
     */
    public static ResultVO success() {
        return success(null);
    }

    /**
     * 请求失败
     * @param code
     * @param message
     * @return
     */
    public static ResultVO error(Integer code,String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }
}
