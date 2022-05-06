package cx.examination.enroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whxd.saas.core.response.Result;
import cx.examination.enroll.entity.CxUser;
import cx.examination.enroll.model.UserDTO;
import cx.examination.enroll.service.ICxUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Desc 用户模块
 * @Author Mr.Yao
 * @Date 2022/5/6 17:50
 * @Version 1.0
 */
@RestController
@RequestMapping("/v1/mini/1.0/user")
@Slf4j
public class UserController {

    @Autowired
    private ICxUserService cxUserService;
    /**
     * 验证是否登录过
     * @param userId
     * @param mobile
     * @return
     */
    @ApiOperation("验证是否授权登录过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "微信用户id", defaultValue = ""),
            @ApiImplicitParam(name = "mobile", value = "授权登录手机号", defaultValue = "")
    })
    @GetMapping("/validationLogin")
    public Result<Boolean> validationLogin(@RequestParam("userId") String userId,
                                       @RequestParam(value = "mobile") String mobile) {

        Assert.notBlank(userId, "微信用户id不能为空");
        Assert.notBlank(mobile, "手机号不能为空");

        CxUser cxUser = cxUserService.getOne(Wrappers.lambdaQuery(CxUser.class)
                .eq(CxUser::getUserId, userId).eq(CxUser::getMobile, mobile));
        return Result.succeed(cxUser != null);
    }

    /**
     * 授权登录，并保存登录信息
     * @param userDTO
     * @return
     */
    @ApiOperation("保存授权登录信息")
    @PostMapping("/saveLogin")
    public Result<Boolean> saveLogin(@RequestBody @Valid UserDTO userDTO) {
        log.info("保存授权登录信息[{}]", JSONUtil.toJsonStr(userDTO));
        CxUser cxUser = BeanUtil.copyProperties(userDTO, CxUser.class);
        boolean save = cxUserService.save(cxUser);
        log.info("保存授权登录信息: {}",save);
        return Result.succeed(save);
    }
}
