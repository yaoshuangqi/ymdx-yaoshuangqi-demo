package cx.examination.enroll.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 报名记录表
 * </p>
 *
 * @author yaoshuangqi
 * @since 2022-05-06
 */
@Data
public class EnrollShowVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "报名号")
    private String enrollNum;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别 0：男 1：女")
    private String sex;

    @ApiModelProperty(value = "身份证号")
    private String cardNo;

    @ApiModelProperty(value = "出生日")
    private Date birthday;

    @ApiModelProperty(value = "政治面貌")
    private String political;

    @ApiModelProperty(value = "毕业院校")
    private String graduatedFrom;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "街道编号")
    private String streetNo;

    @ApiModelProperty(value = "户籍所在地 如：XX街道XX社区XX小区")
    private String registeredAddress;

    @ApiModelProperty(value = "联系方式")
    private String phoneContact;

    @ApiModelProperty(value = "加分项描述")
    private String plusItem;

    @ApiModelProperty(value = "电子照片路径（外网可访问）")
    private String faceImg;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @ApiModelProperty(value = "备注")
    private String remark;

}
