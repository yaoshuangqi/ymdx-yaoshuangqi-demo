package com.ymdx.pay.demo.constant;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-ymx-pay
 * @date 2021/1/15
 * @Description 支付系统中各表中枚举值
 */
public class PayEnum {

    public static enum PayTypeEnum {
        PREPAY("prepay", "支付"),
        LATERPAY("laterPay", "代扣");

        private String code;
        private String msg;

        private PayTypeEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return this.code;
        }

        public String getMsg() {
            return this.msg;
        }

        public static String getPayChannelEnumByCode(String code) {
            PayEnum.PayTypeEnum[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                PayEnum.PayTypeEnum value = var1[var3];
                if (code.equals(value.getCode())) {
                    return value.getMsg();
                }
            }

            return null;
        }
    }
    //支付渠道绑定状态
    public static enum BindStatusEnum {
        INIT(0, "初始化"),
        BIND(1, "已绑定"),
        UNBIND(2, "已解绑"),
        FAILED(3, "绑定失败");

        private Integer value;
        private String name;

        private BindStatusEnum(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static String getValueByCode(Integer value) {
            if (value == null) {
                return null;
            } else {
                PayEnum.BindStatusEnum[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    PayEnum.BindStatusEnum e = var1[var3];
                    if (e.getValue().equals(value)) {
                        return e.getName();
                    }
                }

                return null;
            }
        }
    }

    public static enum PayReqfundsCallbackStatusEnum {
        success("00", "成功"),
        failed("99", "失败");

        private String value;
        private String name;

        private PayReqfundsCallbackStatusEnum(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }
    }

    //交易状态, 0:初始化, 1：交易中，2：交易失败，3：交易成功，4：交易异常'
    public static enum PaidStatusEnum {
        INIT(0, "初始化"),
        PAYING(1, "交易中"),
        FAILED(2, "交易失败"),
        PAID(3, "交易成功"),
        EXPIRED(4, "交易异常");

        private Integer value;
        private String name;

        private PaidStatusEnum(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }
    }
    //流水状态 0:初始化 1:创建交易订单成功
    public static enum PayRecordStatusEnum {
        init("0", "初始化"),
        success_order("1", "创建交易订单成功");

        private String value;
        private String name;

        private PayRecordStatusEnum(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }
    }
}
