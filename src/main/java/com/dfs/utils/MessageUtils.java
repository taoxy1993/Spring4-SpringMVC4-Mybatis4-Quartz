package com.dfs.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.dfs.model.AliyunMessageConfig;

/**
 * @author taoxy 2019/3/13
 */
public class MessageUtils {

    public static Boolean internalTextMessage(String mobile, String singnName, String templateName, String templateParam) throws ClientException {
        System.setProperty(AliyunMessageConfig.defaultConnectTimeout, AliyunMessageConfig.time);
        System.setProperty(AliyunMessageConfig.defaultReadTimeout, AliyunMessageConfig.time);
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AliyunMessageConfig.accessKeyId,
                AliyunMessageConfig.accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", AliyunMessageConfig.product, AliyunMessageConfig.domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(mobile);
        request.setSignName(singnName);
        request.setTemplateCode(templateName);
        request.setTemplateParam(templateParam);
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"));
    }
}
