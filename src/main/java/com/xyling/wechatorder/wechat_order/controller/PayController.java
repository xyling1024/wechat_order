package com.xyling.wechatorder.wechat_order.controller;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;
import com.xyling.wechatorder.wechat_order.enums.ResultEnum;
import com.xyling.wechatorder.wechat_order.exception.SellException;
import com.xyling.wechatorder.wechat_order.service.OrderService;
import com.xyling.wechatorder.wechat_order.service.PayService;
import com.xyling.wechatorder.wechat_order.utils.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.util.Map;
import java.util.Random;

/**
 * 微信支付流程:
 *      1. 生成商户订单
 *      2. 调用统一下单API, 返回预付单信息prepay_id
 *      3. 生成JSAPI页面调用的支付参数并签名
 *      4. 返回支付参数(prepay_id、paySign等)给微信客户端(微信客户端: 展示给用户的页面)
 *      5. 调用JSAPI接口请求支付
 * Created by: xyling
 * 2018-03-13 15:50
 */
@Slf4j
@Controller
//@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @Autowired
    private BestPayServiceImpl bestPayService;

    /**
     * 用户支付流程
     * 用户点击前端支付链接时, 会携带openid, orderId和returnUrl参数访问支付方法(配置在前端index.js中的wechatPayUrl)
     *      openid  用户访问该项目时, 会首先访问项目中的授权方法(配置在前端index.js中的openidUrl), 得到openid
     *      returnUrl   用户支付成功后需要跳转的页面(订单详情页面). 由前端配好的, 无需我们定义.
     * 借用账号地址: https://github.com/Pay-Group/best-pay-sdk/blob/master/doc/borrowAccount.md
     * 借用账号支付请求流程:
     *          支付授权目录(wechatPayUrl) -> 你的外网 -> 你的电脑
     *          支付授权目录 http://sell.springboot.cn/sell/pay?openid=xxxxxxxxx
     *          你的外网 http://xxx.natapp.cc/pay?openid=xxxxxxxxx
     *          你的电脑 http://127.0.0.1:8080/pay?openid=xxxxxxxxxx
     *   pay方法业务逻辑:
     *      1. 根据orderId查询订单
     *      2. 调用统一下单API, 生成预付单信息
     *          (生成JSAPI页面调用的支付参数并签名, 这一步由第三方SDK帮我们完成了
     *          第三方SDK链接: https://github.com/Pay-Group/best-pay-sdk )
     *      3. 将returnUrl、预付单信息及签名等, 返回给前端页面
     *   前端页面:
     *      获取到传递过来的参数信息, 调用JSAPI接口请求支付.
     *      支付完成后跳转到returnUrl页面
//     * @param openid   前端传入
     * @param orderId   前端传入
     * @param returnUrl   前端传入    支付完成后要跳转的url
     * @param map
     * @return
     */
    @GetMapping("/pay")
    public ModelAndView pay( //@RequestParam("openid") String openid,   // 正式开发可不传. 测试时传入, 设置到order中
                               String orderId,
                               String returnUrl,
                               Map<String, Object> map) {

/*      PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(openid);
        payRequest.setOrderAmount(0.01);
        payRequest.setOrderId(String.valueOf(new Random().nextInt(1000000000)));
        payRequest.setOrderName("微信点餐实战");
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信支付] request = {}", GsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("[微信支付] response = {}", GsonUtil.toJson(payResponse));

        map.put("payResponse", payResponse);
*/

        if (StringUtils.isEmpty(orderId)||StringUtils.isEmpty(returnUrl)){
            log.error("[微信支付] 参数错误");
            throw  new SellException(ResultEnum.PARAM_ERROR);
        }

        // 1. 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if ( orderDTO == null ) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
//        orderDTO.setBuyerOpenid(openid);

        // 2. 发起支付
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl", URLDecoder.decode(returnUrl));

        return new ModelAndView("pay/create", map);

    }

    /**
     * 微信异步通知调用
     * @param notifyData
     * @return
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {

        payService.notify(notifyData);
        return new ModelAndView("pay/success");

    }

}
