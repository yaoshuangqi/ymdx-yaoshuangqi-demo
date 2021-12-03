package com.xxcx.pay.untionUtil;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2021/11/18 12:14
 * @Version 1.0
 */
@Slf4j
public class UnionSignUtil {

    public static final String SIGNMETHOD_RSA = "01";
    public static final String SIGNMETHOD_SHA256 = "11";
    public static final String SIGNMETHOD_SM3 = "12";

    /**
     * 功能：前台交易构造HTTP POST自动提交表单<br>
     * @param action 表单提交地址<br>
     * @param hiddens 以MAP形式存储的表单键值<br>
     * @param encoding 上送请求报文域encoding字段的值<br>
     * @return 构造好的HTTP POST交易表单<br>
     */
    public static String createAutoFormHtml(String reqUrl, Map<String, String> hiddens,String encoding) {
        StringBuffer sf = new StringBuffer();
        sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset="+encoding+"\"/></head><body>");
        sf.append("<form id = \"pay_form\" action=\"" + reqUrl
                + "\" method=\"post\">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Map.Entry<String, String>> set = hiddens.entrySet();
            Iterator<Map.Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> ey = it.next();
                String key = ey.getKey();
                String value = ey.getValue();
                sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
                        + key + "\" value=\"" + value + "\"/>");
            }
        }
        sf.append("</form>");
        sf.append("</body>");
        sf.append("<script type=\"text/javascript\">");
        sf.append("document.all.pay_form.submit();");
        sf.append("</script>");
        sf.append("</html>");
        return sf.toString();
    }

    /**
     * 请求报文签名(使用配置文件中配置的私钥证书或者对称密钥签名)<br>
     * 功能：对请求报文进行签名,并计算赋值certid,signature字段并返回<br>
     * @param reqData 请求报文map<br>
     * @param secureKey 上送请求安全密钥值<br>
     * @return　签名后的map对象<br>
     */
    public static Map<String, String> sign(Map<String, String> reqData, String secureKey) {
        reqData = filterBlank(reqData);
        signMap(reqData, secureKey);
        return reqData;
    }

    /**
     * 过滤请求报文中的空字符串或者空字符串
     * @param contentData
     * @return
     */
    public static Map<String, String> filterBlank(Map<String, String> contentData){
        log.info("打印请求报文域 :");
        Map<String, String> submitFromData = new HashMap<String, String>();
        Set<String> keyset = contentData.keySet();

        for(String key:keyset){
            String value = contentData.get(key);
            if (value != null && !"".equals(value.trim())) {
                // 对value值进行去除前后空处理
                submitFromData.put(key, value.trim());
                log.info(key + "-->" + value);
            }
        }
        return submitFromData;
    }

    /**
     * 根据signMethod的值，提供三种计算签名的方法
     *
     * @param data
     *            待签名数据Map键值对形式
     * @param secureKey
     *            安全密钥(SHA256和SM3计算时使用) = 商户私钥证书内容
     * @return 签名是否成功
     */
    public static boolean signMap(Map<String, String> data, String secureKey) {

        String signMethod = data.get("signMethod");
        String version = data.get("version");

        if (SIGNMETHOD_RSA.equals(signMethod)) {
            return signBySecureKey(data, secureKey, SDKConstants.UTF_8_ENCODING);
        }
        return false;
    }

    /**
     * 通过传入的证书绝对路径和证书密码读取签名证书进行签名并返回签名值<br>
     *
     * @param data
     *            待签名数据Map键值对形式
     * @param encoding
     *            编码
     * @param certPath
     *            证书绝对路径
     * @param certPwd
     *            证书密码
     * @return 签名值
     */
    public static boolean signBySecureKey(Map<String, String> data, String secureKey,
                                          String encoding) {

        if (StrUtil.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        if (StrUtil.isEmpty(secureKey)) {
            log.error("secureKey is empty");
            return false;
        }
        String signMethod = data.get("signMethod");
        if (StrUtil.isEmpty(signMethod)) {
            log.error("signMethod must Not null");
            return false;
        }
        //====================================version=5.1.0  signMethod=01 固定值==============================================
        // 设置签名证书序列号
        data.put(SDKConstants.param_certId, CertUtil.getSignCertId());
        // 将Map信息转换成key1=value1&key2=value2的形式
        String stringData = coverMap2String(data);
        log.info("打印待签名请求报文串（交易返回11验证签名失败时可以用来同正确的进行比对）:[" + stringData + "]");
        byte[] byteSign = null;
        String stringSign = null;
        // 通过SHA256进行摘要并转16进制
        byte[] signDigest = SecureUtil
                .sha256X16(stringData, encoding);
        log.info("打印摘要（交易返回11验证签名失败可以用来同正确的进行比对）:[" + new String(signDigest)+ "]");
        try {
            byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft256(
                    CertUtil.getSignCertPrivateKey(), signDigest));
        } catch (Exception e) {
            e.printStackTrace();
        }
        stringSign = new String(byteSign);
        // 设置签名域值
        data.put(SDKConstants.param_signature, stringSign);
        return true;

       /* // 将Map信息转换成key1=value1&key2=value2的形式
        String stringData = coverMap2String(data);
        log.info("待签名请求报文串:[" + stringData + "]");
        String strBeforeSha256 = stringData
                + SDKConstants.AMPERSAND
                + SecureUtil.sha256X16Str(secureKey, encoding);
        String strAfterSha256 = SecureUtil.sha256X16Str(strBeforeSha256,
                encoding);
        // 设置签名域值
        data.put(SDKConstants.param_signature, strAfterSha256);
        return true;*/
    }

    /**
     * 将Map中的数据转换成key1=value1&key2=value2的形式 不包含签名域signature
     *
     * @param data
     *            待拼接的Map数据
     * @return 拼接好后的字符串
     */
    public static String coverMap2String(Map<String, String> data) {
        TreeMap<String, String> tree = new TreeMap<String, String>();
        Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> en = it.next();
            if (SDKConstants.param_signature.equals(en.getKey().trim())) {
                continue;
            }
            tree.put(en.getKey(), en.getValue());
        }
        it = tree.entrySet().iterator();
        StringBuffer sf = new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, String> en = it.next();
            sf.append(en.getKey() + SDKConstants.EQUAL + en.getValue()
                    + SDKConstants.AMPERSAND);
        }
        return sf.substring(0, sf.length() - 1);
    }
}
