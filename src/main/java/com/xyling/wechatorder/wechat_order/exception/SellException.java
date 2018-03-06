package com.xyling.wechatorder.wechat_order.exception;

import com.xyling.wechatorder.wechat_order.enums.ResultEnum;
import lombok.Data;

/**
 * Created by: xyling
 * 2018-03-02 17:11
 */
@Data
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
