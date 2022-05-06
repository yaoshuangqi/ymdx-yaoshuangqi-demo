package cx.examination.enroll.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 保存用户报名记录
 * </p>
 *
 * @author yaoshuangqi
 * @since 2022-05-06
 */
@Data
public class EnrollDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(value = "性别 0：男 1：女")
    @NotBlank(message = "性别不能为空")
    private String sex;

    @ApiModelProperty(value = "身份证号")
    @NotBlank(message = "身份证号不能为空")
    private String cardNo;

    @ApiModelProperty(value = "出生日")
    @NotBlank(message = "出生日不能为空")
    private Date birthday;

    @ApiModelProperty(value = "政治面貌")
    @NotBlank(message = "政治面貌不能为空")
    private String political;

    @ApiModelProperty(value = "毕业院校")
    @NotBlank(message = "毕业院校不能为空")
    private String graduatedFrom;

    @ApiModelProperty(value = "学历")
    @NotBlank(message = "学历不能为空")
    private String education;

    @ApiModelProperty(value = "街道编号")
    @NotBlank(message = "街道编号不能为空")
    private String streetNo;

    @ApiModelProperty(value = "户籍所在地 如：XX街道XX社区XX小区")
    @NotBlank(message = "户籍所在地不能为空")
    private String registeredAddress;

    @ApiModelProperty(value = "联系方式")
    @NotBlank(message = "联系方式不能为空")
    private String phoneContact;

    @ApiModelProperty(value = "加分项描述")
    private String plusItem;

    @ApiModelProperty(value = "电子照片）")
    @NotBlank(message = "电子照片不能为空")
    private String faceImg;

    @ApiModelProperty(value = "备注")
    private String remark;

}
