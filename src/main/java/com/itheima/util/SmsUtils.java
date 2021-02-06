package com.itheima.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;


/**
 * 发送短信息的工具类
 */
public class SmsUtils {

    //这里要换成在阿里云申请的：accessKeyId和secret
    private static String accessKeyId = "LTAI4G9yqgBpWEnJWjUwqPkc";
    private static String secret = "ocsztCsEsY7EaGcQPauyAU1UWY9t31";

    /**
     * 发送短信的方法
     * @param phoneNumbers 11位电话号码，格式：15900000000
     * @param signName 签名名称
     * @param templateCode 模板CODE
     * @param code 要发的验证码
     * @return 返回的信息字符串
     */
    public static CommonResponse send(String phoneNumbers, String signName, String templateCode, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            return client.getCommonResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
//测试工具
//    public static void main(String[] args) {
//        CommonResponse response = SmsUtils.send("17736601872", "黑马旅游网", "SMS_198917972", "123456");
//        //得到返回的JSON字符串 {"Message":"OK","RequestId":"E3C3BE8F-2294-4541-A3F3-A98A29BB6F55","BizId":"375420194646745909^0","Code":"OK"}
//        System.out.println(response.getData());
//        //得到服务器响应的状态码 200
//        System.out.println(response.getHttpStatus());
//    }
}