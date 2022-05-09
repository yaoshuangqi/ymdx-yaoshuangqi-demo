package cx.examination.enroll.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import cx.examination.enroll.common.PoliticalEnum;
import cx.examination.enroll.common.StreetNoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 报名记录表
 * </p>
 *
 * @author yaoshuangqi
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cx_enroll")
@ApiModel(value="CxEnroll对象", description="报名记录表")
public class CxEnroll implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "报名号")
    @TableField("enroll_num")
    private String enrollNum;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "性别 0：男 1：女")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "身份证号")
    @TableField("card_no")
    private String cardNo;

    @ApiModelProperty(value = "出生日")
    @TableField("birthday")
    private Date birthday;

    @ApiModelProperty(value = "政治面貌")
    @TableField("political")
    private String political;

    @ApiModelProperty(value = "毕业院校")
    @TableField("graduated_from")
    private String graduatedFrom;

    @ApiModelProperty(value = "学历")
    @TableField("education")
    private String education;

    @ApiModelProperty(value = "街道编号")
    @TableField("street_no")
    private StreetNoEnum streetNo;

    @ApiModelProperty(value = "户籍所在地 如：XX街道XX社区XX小区")
    @TableField("registered_address")
    private String registeredAddress;

    @ApiModelProperty(value = "联系方式")
    @TableField("phone_contact")
    private String phoneContact;

    @ApiModelProperty(value = "加分项描述")
    @TableField("plus_item")
    private String plusItem;

    @ApiModelProperty(value = "电子照片路径（外网可访问）")
    @TableField("face_img")
    private String faceImg;

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
