package cx.examination.enroll;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Desc 表格数据，属性与列一一对应
 * @Author Mr.Yao
 * @Date 2022/5/12 15:52
 * @Version 1.0
 */
@Data
public class ExamineModel implements Serializable {

    @ApiModelProperty(value = "用户")
    private String userNick;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "报名号")
    private String enrollNum;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String cardNo;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "毕业院校")
    private String graduatedFrom;

    @ApiModelProperty(value = "街道名称")
    private String streetNo;

    @ApiModelProperty(value = "户籍所在地 如：XX街道XX社区XX小区")
    private String registeredAddress;

    @ApiModelProperty(value = "联系方式")
    private String phoneContact;

    @ApiModelProperty(value = "加分项描述")
    private String plusItem;

    @ApiModelProperty(value = "电子照片路径（外网可访问）")
    private String faceImg;

    @ApiModelProperty(value = "政治面貌")
    private String political;

    @ApiModelProperty(value = "学历")
    private String education;
}
