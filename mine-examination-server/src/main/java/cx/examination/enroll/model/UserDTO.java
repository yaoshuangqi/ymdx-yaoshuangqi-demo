package cx.examination.enroll.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/5/6 21:06
 * @Version 1.0
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "微信昵称")
    private String nickName;

    @ApiModelProperty(value = "微信头像url")
    private String avatarUrl;

    @ApiModelProperty(value = "授权微信登录，微信openId")
    @NotBlank(message = "微信openId不能为空")
    private String openId;

    @ApiModelProperty(value = "姓名 0：男 1：女")
    private String gender;

}
