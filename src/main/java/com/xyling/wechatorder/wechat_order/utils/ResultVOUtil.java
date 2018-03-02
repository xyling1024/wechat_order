package com.xyling.wechatorder.wechat_order.utils;

import com.xyling.wechatorder.wechat_order.VO.ProductVO;
import com.xyling.wechatorder.wechat_order.VO.ResultVO;
import com.xyling.wechatorder.wechat_order.enums.ResultEnum;

/**
 * Created by: xyling
 * 2018-03-02 8:25
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setMsg(ResultEnum.SUCCESS.getMsg());
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(message);
        return resultVO;
    }
}
