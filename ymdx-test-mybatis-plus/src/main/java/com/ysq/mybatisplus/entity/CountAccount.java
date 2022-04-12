package com.ysq.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.ysq.mybatisplus.common.AccountStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 次数信息表
 * </p>
 *
 * @author yaoshuangqi
 * @since 2022-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("count_account")
public class CountAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private String userId;

    @TableField("organization_id")
    private String organizationId;

    @TableField("app_id")
    private String appId;

    @TableField("account_no")
    private String accountNo;

    @TableField("key_index")
    private String keyIndex;

    @TableField("public_key")
    private String publicKey;

    @TableField("qr_use_mode")
    private String qrUseMode;

    @TableField("card_type")
    private String cardType;

    @TableField("qr_version")
    private String qrVersion;

    @TableField("count")
    private Long count;

    @TableField("device_type")
    private String deviceType;

    @TableField("open_time")
    private Date openTime;

    @TableField("account_status")
    private AccountStatusEnum accountStatus;

    @TableField("del_flag")
    private Boolean delFlag;

    @TableField("create_time")
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    /**
     * 乐观锁版本号
     */
    @Version
    @TableField("version")
    private Integer version;


}
