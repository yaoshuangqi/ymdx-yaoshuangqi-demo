package cx.examination.enroll.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author yaoshuangqi
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cx_user")
@ApiModel(value="CxUser对象", description="用户表")
public class CxUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "手机号")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "微信昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "微信头像url")
    @TableField("avatar_url")
    private String avatarUrl;

    @ApiModelProperty(value = "授权微信登录，微信openId")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty(value = "姓名 0：男 1：女")
    @TableField("gender")
    private String gender;

    @ApiModelProperty(value = "状态 0：启用 1：禁用")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;


}
