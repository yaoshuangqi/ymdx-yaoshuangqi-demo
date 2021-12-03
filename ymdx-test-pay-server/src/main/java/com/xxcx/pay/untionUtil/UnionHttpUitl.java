package com.xxcx.pay.untionUtil;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2021/11/19 16:07
 * @Version 1.0
 */
@Slf4j
public class UnionHttpUitl {

    /**
     * 功能：后台交易提交请求报文并接收同步应答报文<br>
     * @param reqData 请求报文<br>
     * @param rspData 应答报文<br>
     * @param reqUrl  请求地址<br>
     * @param encoding<br>
     * @return 应答http 200返回true ,其他false<br>
     */
    public static Map<String,String> post(
            Map<String, String> reqData,String reqUrl,String encoding) {
        Map<String, String> rspData = new HashMap<String,String>();
        log.info("请求银联地址:" + reqUrl);
        //发送后台请求数据
        HttpClient hc = new HttpClient(reqUrl, 30000, 30000);//连接超时时间，读超时时间（可自行判断，修改）
        try {
            int status = hc.send(reqData, encoding);
            if (200 == status) {
                String resultString = hc.getResult();
                if (null != resultString && !"".equals(resultString)) {
                    // 将返回结果转换为map
                    Map<String,String> tmpRspData  = convertResultStringToMap(resultString);
                    rspData.putAll(tmpRspData);
                }
            }else{
                log.info("返回http状态码["+status+"]，请检查请求报文或者请求地址是否正确");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rspData;
    }


    /**
     * 将形如key=value&key=value的字符串转换为相应的Map对象
     *
     * @param result
     * @return
     */
    public static Map<String, String> convertResultStringToMap(String result) {
        Map<String, String> map = null;

        if (result != null && !"".equals(result.trim())) {
            if (result.startsWith("{") && result.endsWith("}")) {
                result = result.substring(1, result.length() - 1);
            }
            map = parseQString(result);
        }

        return map;
    }


    /**
     * 解析应答字符串，生成应答要素
     *
     * @param str
     *            需要解析的字符串
     * @return 解析的结果map
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> parseQString(String str) {

        Map<String, String> map = new HashMap<String, String>();
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        char curChar;
        String key = null;
        boolean isKey = true;
        boolean isOpen = false;//值里有嵌套
        char openName = 0;
        if(len>0){
            for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
                curChar = str.charAt(i);// 取当前字符
                if (isKey) {// 如果当前生成的是key

                    if (curChar == '=') {// 如果读取到=分隔符
                        key = temp.toString();
                        temp.setLength(0);
                        isKey = false;
                    } else {
                        temp.append(curChar);
                    }
                } else  {// 如果当前生成的是value
                    if(isOpen){
                        if(curChar == openName){
                            isOpen = false;
                        }

                    }else{//如果没开启嵌套
                        if(curChar == '{'){//如果碰到，就开启嵌套
                            isOpen = true;
                            openName ='}';
                        }
                        if(curChar == '['){
                            isOpen = true;
                            openName =']';
                        }
                    }

                    if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
                        putKeyValueToMap(temp, isKey, key, map);
                        temp.setLength(0);
                        isKey = true;
                    } else {
                        temp.append(curChar);
                    }
                }

            }
            putKeyValueToMap(temp, isKey, key, map);
        }
        return map;
    }

    private static void putKeyValueToMap(StringBuilder temp, boolean isKey,
                                         String key, Map<String, String> map) {
        if (isKey) {
            key = temp.toString();
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, "");
        } else {
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, temp.toString());
        }
    }
}
