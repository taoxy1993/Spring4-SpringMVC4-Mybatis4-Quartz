package com.dfs.utils.pay.wechat;


/**
 * 
 * 商户号向个人付款po
 * @see TransferPo
 * @date 2018年9月7日
 * @author bodong.liu@ttxn.com
 */
public class TransferPo
{
    /** 商户账号appid*/
    public String mch_appid;
    /** 微信支付商户号*/
    public String mchid;
    /** 随机串*/
    public String nonce_str;
    /** 签名*/
    public String sign;
    /** 商户订单号*/
    public String partner_trade_no;
    /** 用户id*/
    public String openid;
    /** 是否校验用户姓名 NO_CHECK：不校验真实姓名  FORCE_CHECK：强校验真实姓名*/
    public String check_name;
    /** 金额 单位：分*/
    public Integer amount;
    /** 企业付款描述信息*/
    public String desc;
    /** ip地址*/
    public String spbill_create_ip;
    public String getMchAppid() {
        return mch_appid;
    }
    public void setMchAppid(String mch_appid) {
        this.mch_appid = mch_appid;
    }
    public String getMchid() {
        return mchid;
    }
    public void setMchid(String mchid) {
        this.mchid = mchid;
    }
    public String getNonceStr() {
        return nonce_str;
    }
    public void setNonceStr(String nonce_str) {
        this.nonce_str = nonce_str;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getPartnerTradeNo() {
        return partner_trade_no;
    }
    public void setPartnerTradeNo(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getCheckName() {
        return check_name;
    }
    public void setCheckName(String check_name) {
        this.check_name = check_name;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getSpbillCreateIp() {
        return spbill_create_ip;
    }
    public void setSpbillCreateIp(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }
    @Override
    public String toString()
    {
        return "TransferPo [mch_appid=" + mch_appid + ", mchid=" + mchid + ", nonce_str="
               + nonce_str + ", sign=" + sign + ", partner_trade_no=" + partner_trade_no + ", openid="
               + openid + ", check_name=" + check_name + ", amount=" + amount + ", desc=" + desc
               + ", spbill_create_ip=" + spbill_create_ip + "]";
    }
}
