package com.xyling.wechatorder.wechat_order.controller;

import com.xyling.wechatorder.wechat_order.config.WeChatAccountConfig;
import com.xyling.wechatorder.wechat_order.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by: xyling
 * 2018-03-07 15:05
 */
//@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeiXinController {

    @Autowired
    private WeChatAccountConfig weChatMPConfig;

    private static final String WEIXIN_URL_BASE = "https://api.weixin.qq.com/sns/";

    /**
     * 1. 引导用户在微信内访问URL地址:
     *      https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http://xyling.free.ngrok.cc/sell/weixin/auth&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
     *  其中:     appid   公众号appid
     *            redirect_uri  用户确认授权后跳转的地址
     *            response_type  返回类型, 固定填写为code
     *            scope 应用授权作用域. snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）
     *                                 snsapi_userinfo （弹出授权页面，只要用户授权, 即使在未关注情况下, 也可通过openid拿到昵称、性别、所在地。）
     *            state 重定向后携带的参数. 非必须. 可填写a-zA-Z0-9的参数值，最多128字节.
     *
     * 2. 经过第一步后, 用户会跳转到授权页面, 同意授权后会重定向到我们的redirect_uri地址中.
     *      那么我们编写redirect_uri的一个授权方法, 在方法中携带获取到的code参数, 请求url地址:
     *          https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code
     *      如果code有效, 那么会响应一个JSON数据包. 格式如下:
     *          { "access_token":"ACCESS_TOKEN",
                "expires_in":7200,
                "refresh_token":"REFRESH_TOKEN",
                "openid":"OPENID",
                "scope":"SCOPE" }
     */
    @RequestMapping("/auth")
    public void auth(String code, String state) {
        log.info("进入auth方法, code = {}, state = {}", code, state);
        String getAccessTokenURL = WEIXIN_URL_BASE + "oauth2/access_token?appid=" + weChatMPConfig.getMyAppId() + "&secret=" + weChatMPConfig.getMyAppSecret() + "&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String responseStr = restTemplate.getForObject(getAccessTokenURL, String.class);
        log.info("responseStr = {}", responseStr);
        Map<String, String> response = JacksonUtils.json2Object(responseStr, Map.class);

        Map userInfo = userInfo(response.get("access_token"), response.get("openid"));

    }

    /**
     *
     * 3. 如果有需要, 可刷新access_token    略
     *
     * 4. 拉取用户信息(需scope为 snsapi_userinfo)
     *  请求url地址
     *      https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     *  正确时返回的JSON数据包如下：
            { "openid":" OPENID",
             " nickname": NICKNAME,
             "sex":"1",
             "province":"PROVINCE"
             "city":"CITY",
             "country":"COUNTRY",
             "headimgurl":    "http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
             "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
             "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
             }
     *
     */
    private Map<String, String> userInfo(String access_token, String openid) {

        if ( isValidAccessToken(access_token, openid) ) {
            String getUserInfoURL = WEIXIN_URL_BASE + "userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
            String userInfoStr = new RestTemplate().getForObject(getUserInfoURL, String.class);
            try {
                // 微信采用的是“ISO-8859-1”编码, 那么我们需要对获取的结果进行编码.
                userInfoStr = new String(userInfoStr.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            log.info("userInfoStr = {}", userInfoStr);

            return JacksonUtils.json2Object(userInfoStr, Map.class);
        }
        return null;
    }

    /**
     * 判断access_token是否有效
     *  请求url地址
     *      https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
     *  如果正确, 返回 { "errcode":0,"errmsg":"ok"}
     * @param access_token
     * @param openid
     * @return
     */
    private boolean isValidAccessToken(String access_token, String openid) {
        String url = WEIXIN_URL_BASE + "auth?access_token=" + access_token + "&openid=" + openid;
        Map<String, String> map = JacksonUtils.json2Object(new RestTemplate().getForObject(url, String.class), Map.class);
        if ( ("0").equalsIgnoreCase(map.get("errcode")) ) {
            return true;
        } else {
            log.error("access_token无效, errcode = {}", map.get("errcode"));
            return false;
        }
    }
}
