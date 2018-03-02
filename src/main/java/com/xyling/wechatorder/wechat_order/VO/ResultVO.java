package com.xyling.wechatorder.wechat_order.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * http请求返回的最外层对象
 * Created by: xyling
 * 2018-03-01 17:17
 */
@Data
@NoArgsConstructor
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
