package com.xyling.wechatorder.wechat_order.controller;

import com.xyling.wechatorder.wechat_order.config.WeChatAccountConfig;
import com.xyling.wechatorder.wechat_order.enums.ResultEnum;
import com.xyling.wechatorder.wechat_order.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by: xyling
 * 2018-03-07 21:25
 */
@Controller
@Slf4j
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private WeChatAccountConfig weChatMPConfig;

    @Autowired
    private WxMpService wxMpService;

    private String userInfoURL = "http://fairy.nat300.top/wechat/userInfo";

    /**
     * 授权方法
     * 测试连接:
     *      http://xyling.free.ngrok.cc/wechat/authorize?returnUrl=http://xyling.free.ngrok.cc/sell/buyer/product/list
     *      http://xyling.free.ngrok.cc/wechat/authorize?returnUrl=http://xyling.free.ngrok.cc/sell/wechat/test
     * @param returnUrl
     * @return
     */
    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        System.err.println("================================authorize方法================================");
        String redirectURL = null;
        try {
            /**
             * 该方法会帮我们封装参数, 返回微信的授权链接. 我们需要执行完此方法后, 访问微信的授权链接.
             * arg0     授权成功后会携带code和state回调该url
             * arg1     scope域
             * arg2     回调到url中会携带该state参数
             */
            redirectURL = wxMpService.oauth2buildAuthorizationUrl(userInfoURL, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("[微信网页授权] 编码returnUrl出错, 不支持的编码 {}", e);
            throw new SellException(ResultEnum.ENCODED_ERROR.getCode(), ResultEnum.ENCODED_ERROR.getMsg());
        }
        return "redirect:" + redirectURL;
    }

    /**
     * 获取用户信息(包括openid)的方法
     * 授权成功后回调访问该方法
     * @param code
     * @param returnUrl
     * @return
     */
    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        System.err.println("================================userInfo方法================================");
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            /**
             * 使用code访问微信接口, 返回access_token对象. 可从该对象获取openid
             * 也可使用该access_token对象访问微信接口, 获取用户的详细信息
             */
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信网页授权] {}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" + openid;
    }

    /**
     * 测试方法
     * @param openid
     * @return
     */
    @RequestMapping("/test")
    public String test(@RequestParam("openid") String openid) {
        System.err.println("======================测试方法, openid = " + openid + "=======================");
        return "index";
    }

}
