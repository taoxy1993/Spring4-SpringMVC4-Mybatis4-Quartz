package com.dfs.model;

import com.dfs.utils.ResourceUtil;

/**
 * @ author ezra
 * @ date 2019/1/28 10:50
 */
public class WechatConfig {
    //小程序appid
    public static final String appid = "xxxxxxxxxxxxxxx";
    //小程序appsecret
    public static final String appsecret = "xxxxxxxxxxxxxxx";
    //微信支付的商户id
    public static final String mch_id = "1525341361";
    //微信支付的商户密钥
    public static final String key = "xxxxxxxxxxxxxxx";
    //支付成功后的服务器回调url，这里填PayController里的回调函数地址
    public static final String notify_url = ResourceUtil.imageServer() + "/taoxy/pay/wxNotify";
    //签名方式，固定值
    public static final String SIGNTYPE = "MD5";
    //交易类型，小程序支付的固定值为JSAPI
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信统一查询接口地址
    public static final String pay_query = "https://api.mch.weixin.qq.com/pay/orderquery";
    // 微信退款接口地址
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    // 微信查询退款信息地址
    public static final String REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
    // 微信退款回调地址
    public static final String REFUND_NOTIFY_URL = ResourceUtil.imageServer() + "/taoxy/boss/refund/refundWechatCallback";
    // P12证书存放路径
    public static final String CERT_P12_PATH = "/home/cert/wechat/taoxy/apiclient_cert.p12";

    public static final String  openid_url = "https://api.weixin.qq.com/sns/jscode2session?appid=";

}
