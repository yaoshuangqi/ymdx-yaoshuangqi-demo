package com.ymdx.pay.demo.reconciliation;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.ToString;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/2/20
 * @Description 对账文件 按照订单结算时间生成
 * 订单号, 订单金额, 业务类型, 订单类型, 订单状态, 已退款金额, 清算状态(代付单交易为实时扣除手续费), 交易时间, 处理时间, 手续费, 原订单号, 账户名称, 门店号, 终端号
 */
@Data
@ToString
public class AccountWaterBean {

    @CsvBindByName(column = "订单号")
    private String orderNo;

    @CsvBindByName(column = "订单金额")
    private String orderAmount;

    @CsvBindByName(column = "业务类型")
    private String businessType;

    @CsvBindByName(column = "订单类型")
    private String orderType;

    @CsvBindByName(column = "订单状态")
    private String orderStatus;

    @CsvBindByName(column = "已退款金额")
    private String refundAmount;

    @CsvBindByName(column = "清算状态(代付单交易为实时扣除手续费)")
    private String settlementStatus;

    @CsvBindByName(column = "交易时间")
    private String transactionTime;

    @CsvBindByName(column = "处理时间")
    private String handleTime;

    @CsvBindByName(column = "手续费")
    private String serviceCharge;

    @CsvBindByName(column = "原订单号")
    private String originalOrderNo;

    @CsvBindByName(column = "账户名称")
    private String accountName;

    @CsvBindByName(column = "门店号")
    private String storeNo;

    @CsvBindByName(column = "终端号")
    private String terminalNo;



}
