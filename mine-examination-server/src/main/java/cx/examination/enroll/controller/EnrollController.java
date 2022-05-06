package cx.examination.enroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whxd.saas.core.response.IResult;
import com.whxd.saas.core.response.Result;
import cx.examination.enroll.common.PoliticalEnum;
import cx.examination.enroll.common.StreetNoEnum;
import cx.examination.enroll.entity.CxEnroll;
import cx.examination.enroll.entity.CxUser;
import cx.examination.enroll.model.EnrollDTO;
import cx.examination.enroll.model.EnrollShowVO;
import cx.examination.enroll.model.UserDTO;
import cx.examination.enroll.service.ICxEnrollService;
import cx.examination.enroll.service.ICxUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Desc 报名记录controller
 * @Author Mr.Yao
 * @Date 2022/5/6 17:50
 * @Version 1.0
 */
@RestController
@RequestMapping("/v1/mini/1.0/enroll")
@Slf4j
public class EnrollController {

    @Autowired
    private ICxEnrollService cxEnrollService;
    /**
     * 通过用户id获取报名记录
     * @param userId
     * @return
     */
    @ApiOperation("通过用户id获取报名记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "微信用户id", defaultValue = "")
    })
    @GetMapping("/getUserEnroll")
    public Result<EnrollShowVO> getUserEnroll(@RequestParam("userId") String userId) {

        Assert.notBlank(userId, "微信用户id不能为空");

        CxEnroll cxEnroll = cxEnrollService.getOne(Wrappers.lambdaQuery(CxEnroll.class)
                .eq(CxEnroll::getUserId, userId));
        if(cxEnroll == null){
            log.info("当前用户{{}】没有报名记录",userId);
            return Result.succeed((IResult) null);
        }
        EnrollShowVO enrollShowVO = BeanUtil.copyProperties(cxEnroll, EnrollShowVO.class);
        enrollShowVO.setPolitical(cxEnroll.getPolitical().getName());
        enrollShowVO.setStreetNo(cxEnroll.getStreetNo().getName());
        log.info("获取用户[{}]报名记录：[{}]", userId, JSONUtil.toJsonStr(enrollShowVO));

        return Result.succeed(enrollShowVO);
    }

    @ApiOperation("保存报名记录")
    @PostMapping("/saveUserEnroll")
    public Result<Boolean> saveUserEnroll(@RequestBody @Valid EnrollDTO enrollDTO) {

        final CxEnroll cxEnroll = cxEnrollService.getOne(Wrappers.lambdaQuery(CxEnroll.class)
                .eq(CxEnroll::getUserId, enrollDTO.getUserId()));

        Assert.isTrue(cxEnroll == null, "已提交，无须重复提交");

        CxEnroll cxEnroll1 = BeanUtil.copyProperties(enrollDTO, CxEnroll.class);
        cxEnroll1.setPolitical(PoliticalEnum.getPoliticalEnum(enrollDTO.getPolitical()));
        cxEnroll1.setStreetNo(StreetNoEnum.getStreetNoEnum(enrollDTO.getStreetNo()));

        boolean save = cxEnrollService.save(cxEnroll1);

        return Result.succeed(save);
    }
}
