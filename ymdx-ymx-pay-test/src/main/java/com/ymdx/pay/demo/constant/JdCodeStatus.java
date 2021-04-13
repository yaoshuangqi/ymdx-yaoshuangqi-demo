package com.ymdx.pay.demo.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-ymx-pay
 * @date 2021/1/15
 * @Description 京东三方接口的编码转换我们自己的编码
 */
public class JdCodeStatus {

    public static final String RETURN_PARAM_NULL = "RETURN_PARAM_NULL";
    public static final String SYSTEM_ERROR = "SYSTEM_ERROR";
    public static final String OUT_TRADE_NO_EXIST = "OUT_TRADE_NO_EXIST";
    public static final String TRADE_NOT_EXIST = "TRADE_NOT_EXIST";
    public static final String SUCCESS = "0000";
    public static final String TRADE_FINI = "FINI";
    public static final String TRADE_CLOS = "CLOS";
    public static final String TRADE_WPAR = "WPAR";
    public static final String TRADE_REFU = "REFU";
    public static final String TRADE_BUID = "BUID";
    public static final String TRADE_ACSU = "ACSU";
    public static final String REFUND_TRADE_FINI = "S";
    public static final String REFUND_TRADE_CLOS = "F";
    public static final String REFUND_TRADE_WPAR = "I";
    public static final String AGREEMENT_STATUS_BUID = "BUID";
    public static final String AGREEMENT_STATUS_WPAR = "WPAR";
    public static final String AGREEMENT_STATUS_CLOS = "CLOS";
    public static final String AGREEMENT_STATUS_FINI = "FINI";
    public static final String AGREEMENT_STATUS_FAIL = "FAIL";
    private static Map<String, String> codeMap = new HashMap();

    public static final String MY_CODE = "my_error";

    static {
        codeMap.put("0000", "成功");
        codeMap.put("EXPARTNER_INFO_UNCORRECT", "传入商户接口信息不正确");
        codeMap.put("ILLEGAL_SIGN", "签名验证出错");
        codeMap.put("ILLEGAL_ARGUMENT", "输入参数有错误");
        codeMap.put("ILLEGAL_AUTHORITY", "权限不正确");
        codeMap.put("CUSTOMER_NOT_EXIST", "提交会员不存在");
        codeMap.put("ILLEGAL_CHARSET", "字符集不合法");
        codeMap.put("ILLEGAL_CLIENT_IP", "客户端IP地址无权访问服务");
        codeMap.put("SYSTEM_ERROR", "系统错误");
        codeMap.put("OUT_TRADE_NO_EXIST", "外部交易号已经存在");
        codeMap.put("TRADE_NOT_EXIST", "交易不存在");
        codeMap.put("ILLEGAL_TRADE_TYPE", "无效交易类型");
        codeMap.put("BUYER_USER_NOT_EXIST", "买家会员不存在");
        codeMap.put("SELLER_USER_NOT_EXIST", "卖家会员不存在");
        codeMap.put("BUYER_SELLER_EQUAL", "买家、卖家是同一帐户 ");
        codeMap.put("USER_STATE_ERROR", "会员状态不正确");
        codeMap.put("COMMISION_ID_NOT_EXIST", "佣金收取帐户不存在");
        codeMap.put("COMMISION_SELLER_DUPLICATE", "收取佣金帐户和卖家是同一帐户");
        codeMap.put("COMMISION_FEE_OUT_OF_RANGE", "佣金金额超出范围");
        codeMap.put("TOTAL_FEE_OUT_OF_RANGE", "交易总金额超出范围");
        codeMap.put("ILLEGAL_AMOUNT_FORMAT", "非法金额格式");
        codeMap.put("ILLEGAL_TRADE_AMMOUT", "交易金额不正确");
        codeMap.put("ILLEGAL_TRADE_CURRENCY", "交易币种不正确");
        codeMap.put("SELF_TIMEOUT_NOT_SUPPORT", "不支持自定义超时");
        codeMap.put("COMMISION_NOT_SUPPORT", "不支持佣金 ");
        codeMap.put("VIRTUAL_NOT_SUPPORT", "不支持虚拟収货方式");
        codeMap.put("PAYMENT_LIMITED", "支付受限");
        codeMap.put("ILLEGAL_BANK_CARD_NO", "卡号不正确");
        codeMap.put("ILLEGAL_BANK_CARD_VALID_PERIOD", "卡有效期不正确");
        codeMap.put("ILLEGAL_ID_CARD_NO", "身份证号码不正确");
        codeMap.put("ILLEGAL_BANK_CARD_NAME", "持卡人姓名不正确");
        codeMap.put("ILLEGAL_BANK_CARD_TYPE", "卡类型不正确 ");
        codeMap.put("REFUND_FAILED", "退款失败");
        codeMap.put("CURRENT_PAY_CANNOT_REVOKE", "当前支付请求状态无法撤销");
        codeMap.put("CURRENT_USER_DIFFERENT_FROM", "当前用户和已登录绑定用户不一致");
        codeMap.put("ILLEGAL_PAY_TYPE", "无效支付类型");
        codeMap.put("USER_STATE_ERROR", "用户状态错误");
        codeMap.put("ACCOUNT_BALANCE_NOT_ENOUGH", "账户余额不足");
    }

    public JdCodeStatus() {
    }

}
