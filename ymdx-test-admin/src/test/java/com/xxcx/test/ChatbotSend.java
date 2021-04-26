package com.xxcx.test;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ChatbotSend {

    //WebHook地址xxx
    public static String WEBHOOK_TOKEN = "https://open.feishu.cn/open-apis/bot/v2/hook/ce47aeb7-c407-4069-89d5-512293336235";

    public static void main(String args[]) throws Exception {
        /*HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        //构建一个json格式字符串textMsg，其内容是接收方需要的参数和消息内容
        String textMsg = "{\"msg_type\":\"text\",\"content\":{\"text\":\"你好，我是机器人\"},\"at\":{\"atMobiles\":[\"xxx\"],\"isAtAll\":true}}";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }*/
        ClassLoader classLoader = ChatbotSend.class.getClassLoader();
        System.out.println(classLoader);
        Class<?> aClass = classLoader.loadClass("com.xxcx.test.Test2");
        Object o = aClass.newInstance();
        Class.forName("com.xxcx.test.Test2", false, classLoader);
    }
}
class Test2 {
    //只有
    static {
        System.out.println("静态初始化块执行了！");
    }
}