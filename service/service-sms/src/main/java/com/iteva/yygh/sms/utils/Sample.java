package com.iteva.yygh.sms.utils;

import org.springframework.data.redis.core.RedisTemplate;

public class Sample {

    /**
     * <b>description</b> :
     * <p>使用AK&amp;SK初始化账号Client</p>
     *
     * @return Client
     * @throws Exception
     */


    public static void main(String[] args_) throws Exception {
//        List<String> args = Arrays.asList(args_);
//        Client client = Sample.createClient();
//        SendSmsRequest sendSmsRequest = new SendSmsRequest()
//                .setSignName("阿里云短信测试")
//                .setTemplateCode("SMS_154950909")
//                .setPhoneNumbers("13794269918")
//                .setTemplateParam("{\"code\":\"1234\"}");
//        RuntimeOptions runtime = new RuntimeOptions();
//        try {
//            // 复制代码运行请自行打印 API 的返回值
//            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
//            System.out.println(sendSmsResponse);
//        } catch (TeaException error) {
//            System.out.println(error.getMessage());
//            System.out.println(error.getData().get("Recommend"));
//            Common.assertAsString(error.message);
//        } catch (Exception _error) {
//            TeaException error = new TeaException(_error.getMessage(), _error);
//            System.out.println(error.getMessage());
//            System.out.println(error.getData().get("Recommend"));
//            Common.assertAsString(error.message);
//        }


//        Config config = new Config()
//                .setAccessKeyId("LTAI5tFv1JR2y59Gx4dbYXQD")
//                .setAccessKeySecret("bCZNufXMRAuUD1bglzYf6as6ocBSLc");
//        config.endpoint = "dysmsapi.aliyuncs.com";
//        Client client = new Client(config);
//        SendSmsRequest request = new SendSmsRequest()
//                .setSignName("阿里云短信测试")
//                .setTemplateCode("SMS_154950909")
//                .setPhoneNumbers("13649568869")
//                .setTemplateParam("{\"code\":\"1234\"}");
//        SendSmsResponse response = client.sendSms(request);
//        System.out.println(JSON.toJSONString(response.body));


        System.out.println("hi".equals(null));
    }
}
