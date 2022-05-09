package cx.examination.enroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
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
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Desc 用户模块
 * @Author Mr.Yao
 * @Date 2022/5/6 17:50
 * @Version 1.0
 */
@RestController
@RequestMapping("/v1/mini/1.0/user")
@Slf4j
@Deprecated
public class UserController {

    @Autowired
    private ICxUserService cxUserService;


    /**
     * 获取用户openId
     * @return 返回openId
     */
//    @ApiOperation("获取用户openId并保存用户信息")
//    @GetMapping("/getUserId")
//    public Result<String> getUserId(@RequestParam("codeId") String codeId) {
//
//        Assert.notBlank(codeId， "用户code不能为空");
//
//        //先固定写
//        String appId = "wx1a6b5a6a223665c8";
//        String secret = "a448f8f8a30ca92665934e95c0decfe2";
//
//        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
//        //第一次请求 获取access_token 和 openid
//        String  oppid = HttpUtil.get(requestUrl);
//
//
//        JSONObject oppidObj = JSONUtil.parseObj(oppid);
//        String openid = (String) oppidObj.get("openid");
//        log.info("获取到的openId:{},响应结果：{}",openid, oppid);
//        //对openId加密处理
//        openid = openid.concat("fed4dfdq");
//
//        userDTO.setOpenId(openid);
//        userDTO.setUserId(userDTO.getOpenId());
//
//        CxUser cxUser = BeanUtil.copyProperties(userDTO, CxUser.class);
//        cxUser.setCreateTime(new Date());
//        boolean save = cxUserService.saveOrUpdate(cxUser,Wrappers.lambdaQuery(CxUser.class).eq(CxUser::getUserId, openid));
//        log.info("保存授权登录信息: {}",save);
//
//
//        return Result.succeed(openid, "成功");
//    }


    /**
     * 验证是否登录过
     * @param userId
     * @return
     */
    @ApiOperation("验证是否授权登录过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "微信用户id", defaultValue = "")
    })
    @GetMapping("/validationLogin")
    public Result<Boolean> validationLogin(@RequestParam("userId") String userId) {

        Assert.notBlank(userId, "微信用户id不能为空");

        CxUser cxUser = cxUserService.getOne(Wrappers.lambdaQuery(CxUser.class)
                .eq(CxUser::getUserId, userId));
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
