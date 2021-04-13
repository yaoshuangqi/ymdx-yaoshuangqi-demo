package com.ymdx.pay.demo.pay.ali;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayOpenOperationOpenbizmockBizQueryModel;
import com.alipay.api.request.AlipayOpenOperationOpenbizmockBizQueryRequest;
import com.alipay.api.request.AlipayUserAgreementPageSignRequest;
import com.alipay.api.response.AlipayOpenOperationOpenbizmockBizQueryResponse;
import com.alipay.api.response.AlipayUserAgreementPageSignResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/3/4
 * @Description
 */
public class AliPayTest {

    static AlipayClient alipayClient = null;

    static {
        if (Objects.isNull(alipayClient)) {
            // 1. 创建AlipayClient实例
            alipayClient = new DefaultAlipayClient(AliConfig.URL, AliConfig.APP_ID
                    , AliConfig.APP_PRIVATE_KEY, AliConfig.FORMAT, AliConfig.CHARSET, AliConfig.ALIPAY_PUBLIC_KEY, AliConfig.SIGN_TYPE);
        }
    }

    public static void main(String[] args) throws AlipayApiException {
        //getAgreementRequest();
      /*  String ddd = "null";
        final Optional<Object> empty = Optional.ofNullable(ddd);
        empty.ifPresent(u -> u.toString());
        System.out.println(Optional.ofNullable(ddd).orElse("dd11111"));*/


    }

    //个人签约接口request
    private static void getAgreementRequest() throws AlipayApiException {

        final AlipayUserAgreementPageSignRequest agreementPageSignRequest = new AlipayUserAgreementPageSignRequest();

        //模型全靠手动拼，不可用支付宝通用模型
        Map<String, Object> ret = new HashMap<>();
        //一级channel
        JSONObject acc_p = new JSONObject();
        //
        acc_p.put("channel", "ALIPAYAPP");
        //biz_content层级（关键参数）
        Map bizModel = new HashMap<>();
        //将一级channel传入
        bizModel.put("access_params", acc_p);
        //商户协议号 此处传入用户卡号
        bizModel.put("external_agreement_no", "test1111111");
        //固定参数
        bizModel.put("personal_product_code", "GENERAL_WITHHOLDING_P");
        //代扣场景 出行行业INDUSTRY|GENERAL_TRAVEL
        bizModel.put("sign_scene", "INDUSTRY|GENERAL_TRAVEL");

        agreementPageSignRequest.setBizContent(JSONObject.toJSONString(bizModel));

        AlipayUserAgreementPageSignResponse response = alipayClient.pageExecute(agreementPageSignRequest, "get");
        //获取需提交的form表单
        String submitFormData = response.getBody();
        //客户端拿到submitFormData做表单提交
        System.out.println("==>> "+submitFormData);
        if(response.isSuccess()){

            System.out.println("==>> 调用成功");

        }
    }




    /*public Map<String, Object> createAliContractSDK(String cardNo) throws AlipayApiException, Exception {
        //模型全靠手动拼，不可用支付宝通用模型
        Map<String, Object> ret = new HashMap<>();
        //一级channel
        JSONObject acc_p = new JSONObject();
        acc_p.put("channel", "ALIPAYAPP");
        //biz_content层级（关键参数）
        Map bizModel = new HashMap<>();
        //将一级channel传入
        bizModel.put("access_params", acc_p);
        //唯一标识位，此处传入用户卡号 商户协议号，手动生成
        bizModel.put("external_agreement_no", cardNo);
        //固定参数
        bizModel.put("personal_product_code", "CYCLE_PAY_AUTH_P");
        //代扣场景 出行行业INDUSTRY|GENERAL_TRAVEL
        bizModel.put("sign_scene", "INDUSTRY|GENERAL_TRAVEL");

        //拼接通用参数
        Map request = new HashMap<>();
        request.put("app_id", AliConfig.APP_ID);
        //将biz_content层级转化为JSON格式传入
        request.put("biz_content", JSONObject.toJSONString(bizModel));

        Date date = DateUtil.getAsiaDate();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        request.put("timestamp",dateString);
        //request.put("timestamp","2019-06-12 14:39:02");
        request.put("sign_type", config.getSignType());
        request.put("charset", config.getCharset());
        request.put("method", "alipay.user.agreement.page.sign");
        request.put("notify_url", config.getContractNotiyfyUrl());
        //request.put("return_url", config.getContractReturnUrl());
        request.put("version", "1.0");
        //将拼好的map按ASC2码进行排序，并以key,value进行拼接
        String toSignStr = UrlUtil.getUrlParamsByMapAli(request);

        String sign = null;
        //将拼接好的字符串toSignStr进行RSA2自签名
        sign = AlipaySignature.rsa256Sign(toSignStr,config.getQrPrivateKey(), "utf-8");
        //将签名进行encode处理后再次拼接在toSignStr后面
        String orderStr = toSignStr+"&sign="+URLEncoder.encode(sign, "UTF-8");;
        //再对拼接好的完整字符串orderStr进行一次encode处理
        orderStr = URLEncoder.encode(orderStr, "UTF-8");
        //System.out.println("生成signStr："+orderStr);
        ret.put("returnCode", "0");
        ret.put("sdkParams", orderStr);
        return ret;
    }*/






















    public static void test(){
        try {
            // 2. 创建使用的Open API对应的Request请求对象
            AlipayOpenOperationOpenbizmockBizQueryRequest request = getRequest();
            // 3. 发起请求并处理响应
            //AlipayOpenOperationOpenbizmockBizQueryResponse response = alipayClient.certificateExecute(request);
//            final AlipayOpenOperationOpenbizmockBizQueryResponse bizQueryResponse = alipayClient.sdkExecute(request);
            final AlipayOpenOperationOpenbizmockBizQueryResponse bizQueryResponse = alipayClient.pageExecute(request, "get");
            if (bizQueryResponse.isSuccess()) {
                System.out.println("调用成功。");
            } else {
                System.out.println("调用失败，原因：" + bizQueryResponse.getMsg() + "，" + bizQueryResponse.getSubMsg());
            }
        } catch (Exception e) {
            System.out.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    private static CertAlipayRequest getClientParams() {
        CertAlipayRequest certParams = new CertAlipayRequest();
        certParams.setServerUrl("https://openapi.alipay.com/gateway.do");
        //请更换为您的AppId
        certParams.setAppId("2021001181673265");
        //请更换为您的PKCS8格式的应用私钥
        certParams.setPrivateKey("MIIEvQIBADANB ... ...");
        //请更换为您使用的字符集编码，推荐采用utf-8
        certParams.setCharset("utf-8");
        certParams.setFormat("json");
        certParams.setSignType("RSA2");
        //请更换为您的应用公钥证书文件路径
        certParams.setCertPath("/home/foo/appCertPublicKey_2019091767145019.crt");
        //请更换您的支付宝公钥证书文件路径
        certParams.setAlipayPublicCertPath("/home/foo/alipayCertPublicKey_RSA2.crt");
        //更换为支付宝根证书文件路径
        certParams.setRootCertPath("/home/foo/alipayRootCert.crt");
        return certParams;
    }

    private static AlipayOpenOperationOpenbizmockBizQueryRequest getRequest() {
        // 初始化Request，并填充Model属性。实际调用时请替换为您想要使用的API对应的Request对象。

        AlipayOpenOperationOpenbizmockBizQueryRequest request = new AlipayOpenOperationOpenbizmockBizQueryRequest();
        AlipayOpenOperationOpenbizmockBizQueryModel model = new AlipayOpenOperationOpenbizmockBizQueryModel();
        model.setBizNo("test");
        request.setBizModel(model);
        return request;
    }
}
