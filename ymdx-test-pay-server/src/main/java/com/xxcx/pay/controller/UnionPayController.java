package com.xxcx.pay.controller;

import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.xxcx.pay.untionUtil.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Desc 云闪付交易测试
 * @Author Mr.Yao
 * @Date 2021/11/18 12:00
 * @Version 1.0
 */
@Slf4j
@RestController
public class UnionPayController {

    /**
     * 云闪付网页（WAP）消费交易：属前台地址
     * 测试通过
     * @return
     */
    @RequestMapping(value = "/unionPayWAP", method = RequestMethod.GET)
    public String unionPayWAP(){
        Map<String, String> requestData = new HashMap<>();

        String orderId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + new Random().nextInt(9);
        String secureKey = "安全密钥";//暂未使用
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        //固定值
        requestData.put("version", "5.1.0");
        requestData.put("encoding", SDKConstants.UTF_8_ENCODING);
        /**
         * 非对称签名：
         * 01（表示采用 RSA 签名）（固定采用证书方式01）
         * HASH 表示散列算法
         * 11：支持散列方式验证 SHA-256
         * 12：支持散列方式验证 SM3
         */
        requestData.put("signMethod", "01");
        /**
         * 交易类型 01：消费
         */
        requestData.put("txnType", "01");
        /**
         * 	01：自助消费，通过地址的方式区分前台消费和后台消费（含无跳转支付） 03：分期付款
         */
        requestData.put("txnSubType", "01");
        requestData.put("bizType", "000201");
        requestData.put("channelType", "07");

        /***商户接入参数***/
        //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("merId", "777290058194191");
        /**
         * 0：商户直连接入
         * 1：收单机构接入
         * 2：平台商户接入
         */
        requestData.put("accessType", "0");
        requestData.put("orderId",orderId);
        requestData.put("txnTime", yyyyMMddHHmmss.format(new Date()));
        requestData.put("currencyCode", "156");
        //交易金额 1分
        requestData.put("txnAmt", "1");

        requestData.put("riskRateInfo", "乘车套餐");

        //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
        requestData.put("frontUrl", "http://www.baidu.com");

        //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
        requestData.put("backUrl", "http://www.specialUrl.com");

        // 订单超时时间。此时间建议取支付时的北京时间加15分钟。
        requestData.put("payTimeout", yyyyMMddHHmmss.format(System.currentTimeMillis() + 15 * 60 * 1000));

        /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
        //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        Map<String, String> submitFromData = UnionSignUtil.sign(requestData,secureKey);

        //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
        String requestFrontUrl = "https://gateway.test.95516.com/gateway/api/frontTransReq.do";
        //String requestFrontUrl = "https://gateway.test.95516.com/gateway/api/appTransReq.do";
        //生成自动跳转的Html表单
        String html = UnionSignUtil.createAutoFormHtml(requestFrontUrl, submitFromData,SDKConstants.UTF_8_ENCODING);

        log.info("打印请求HTML，此为请求报文，为联调排查问题的依据："+ html);

        return html;
    }


    /**
     * 云闪付APP消费交易：本接口获取tn值给客户端
     * tn值有时效性。半小时内需要拉起客户端支付控件，1小时内需要发起支付
     * 测试通过
     * @return
     */
    @RequestMapping(value = "/unionPayAPP", method = RequestMethod.GET)
    public String unionPayAPP(){
        Map<String, String> requestData = new HashMap<>();

        String orderId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + new Random().nextInt(9);
        String secureKey = "安全密钥";//暂未使用
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        //固定值
        requestData.put("version", "5.1.0");
        requestData.put("encoding", SDKConstants.UTF_8_ENCODING);
        /**
         * 非对称签名：
         * 01（表示采用 RSA 签名）（固定采用证书方式01）
         * HASH 表示散列算法
         * 11：支持散列方式验证 SHA-256
         * 12：支持散列方式验证 SM3
         */
        requestData.put("signMethod", "01");
        /**
         * 交易类型 01：消费
         */
        requestData.put("txnType", "01");
        /**
         * 	01：自助消费，通过地址的方式区分前台消费和后台消费（含无跳转支付） 03：分期付款
         */
        requestData.put("txnSubType", "01");
        requestData.put("bizType", "000201");
        requestData.put("channelType", "08");

        /***商户接入参数***/
        //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("merId", "777290058194191");
        /**
         * 0：商户直连接入
         * 1：收单机构接入
         * 2：平台商户接入
         */
        requestData.put("accessType", "0");
        requestData.put("orderId",orderId);
        requestData.put("txnTime", yyyyMMddHHmmss.format(new Date()));
        requestData.put("currencyCode", "156");
        //交易金额 1分
        requestData.put("txnAmt", "1");

        requestData.put("orderDesc", "钱包充值");

        //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
        requestData.put("frontUrl", "http://www.baidu.com");

        //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
        requestData.put("backUrl", "http://www.specialUrl.com");

        // 订单超时时间。此时间建议取支付时的北京时间加15分钟。
        requestData.put("payTimeout", yyyyMMddHHmmss.format(System.currentTimeMillis() + 15 * 60 * 1000));

        //requestData.put("accType", "01");					 	//账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
        /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
        //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        Map<String, String> submitFromData = UnionSignUtil.sign(requestData,secureKey);

        //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
        String requestAPPUrl = "https://gateway.test.95516.com/gateway/api/appTransReq.do";

        /**
         * 发送请求报文并接受同步应答
         * 银联自己的sdk写法
         */
        //Map<String, String> rspData = UnionHttpUitl.post(submitFromData,requestAPPUrl,SDKConstants.UTF_8_ENCODING);

        String requestParamString = HttpClient.getRequestParamString(submitFromData, SDKConstants.UTF_8_ENCODING);
        String post = HttpUtil.post(requestAPPUrl, requestParamString);

        Map<String, String> stringStringMap = UnionRespUtil.convertResultStringToMap(post);
        log.info("===>>>获取的响应：{}", stringStringMap);
        return JSONUtil.toJsonStr(stringStringMap);

        //return JSONUtil.toJsonStr(rspData);
    }


    /**
     * 云闪付APP交易状态查询：本接口获取tn值给客户端
     * tn值有时效性。半小时内需要拉起客户端支付控件，1小时内需要发起支付
     * 测试通过
     * @return
     */
    @RequestMapping(value = "/unionQueryAPP", method = RequestMethod.GET)
    public String unionQueryAPP(){
        Map<String, String> requestData = new HashMap<>();

        String orderId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + new Random().nextInt(9);
        String secureKey = "安全密钥";//暂未使用
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        //固定值
        requestData.put("version", "5.1.0");
        requestData.put("encoding", SDKConstants.UTF_8_ENCODING);
        /**
         * 非对称签名：
         * 01（表示采用 RSA 签名）（固定采用证书方式01）
         * HASH 表示散列算法
         * 11：支持散列方式验证 SHA-256
         * 12：支持散列方式验证 SM3
         */
        requestData.put("signMethod", "01");
        /**
         * 交易类型 00：查询
         */
        requestData.put("txnType", "00");
        /**
         * 	01：自助消费，通过地址的方式区分前台消费和后台消费（含无跳转支付） 03：分期付款
         */
        requestData.put("txnSubType", "00");
        requestData.put("bizType", "000000");

        /***商户接入参数***/
        //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("merId", "777290058194191");
        /**
         * 0：商户直连接入
         * 1：收单机构接入
         * 2：平台商户接入
         */
        requestData.put("accessType", "0");
        requestData.put("orderId",orderId);
        requestData.put("txnTime", yyyyMMddHHmmss.format(new Date()));
        /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
        Map<String, String> submitFromData = UnionSignUtil.sign(requestData,secureKey);
        String requestAPPUrl = "https://gateway.test.95516.com/gateway/api/queryTrans.do";
        log.info("请求地址：{}", requestAPPUrl);
        log.info("请求参数：{}", JSONUtil.toJsonStr(submitFromData));
        String requestParamString = HttpClient.getRequestParamString(submitFromData, SDKConstants.UTF_8_ENCODING);
        String post = HttpUtil.post(requestAPPUrl, requestParamString);

        Map<String, String> stringStringMap = UnionRespUtil.convertResultStringToMap(post);
        log.info("===>>>交易查询状态响应结果：{}", stringStringMap);
        return JSONUtil.toJsonStr(stringStringMap);
    }

    /**
     * 测试使用
     */
        /*byte[] priByte = toByteArray("F:/Yaosq/production/saas-支付服务/第三方支付平台/对接银联/云闪付WAP支付/证书/5.1.0/acp_test_sign.pfx");
        byte[] rootByte = toByteArray("F:\\Yaosq\\production\\saas-支付服务\\第三方支付平台\\对接银联\\云闪付WAP支付\\证书\\5.1.0\\acp_test_root.cer");
        byte[] middleByte = toByteArray("F:\\Yaosq\\production\\saas-支付服务\\第三方支付平台\\对接银联\\云闪付WAP支付\\证书\\5.1.0\\acp_test_middle.cer");
        onlineUnionProperties.setPriByte(priByte);
        onlineUnionProperties.setRootByte(rootByte);
        onlineUnionProperties.setMiddleByte(middleByte);*/
    /**
     * 测试，读取本地文件，获得证书byte[]
     * @param filePath
     * @return
     * @throws IOException
     */
    private byte[] toByteArray(String filePath){

        InputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            in = new FileInputStream(filePath);
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return out.toByteArray();
    }
}
