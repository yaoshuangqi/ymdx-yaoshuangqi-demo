package cx.examination.enroll.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdcardUtil;
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
import java.util.Date;

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
     * 获取总报名人数
     * @return
     */
    @ApiOperation("获取总报名人数")
    @GetMapping("/getEnrollTotal")
    public Result<String> getEnrollTotal() {
        long count = cxEnrollService.count();
        return Result.succeed(String.valueOf(count), "成功");
    }

    /**
     * 通过用户id获取报名记录
     * @param openId
     * @return
     */
//    @ApiOperation("通过用户id获取报名记录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "openId", value = "微信用户openId", defaultValue = "")
//    })
//    @GetMapping("/getUserEnroll")
//    public Result<EnrollShowVO> getUserEnroll(@RequestParam("openId") String openId) {
//
//        Assert.notBlank(openId, "微信用户id不能为空");
//
//        CxEnroll cxEnroll = cxEnrollService.getOne(Wrappers.lambdaQuery(CxEnroll.class)
//                .eq(CxEnroll::getUserId, openId));
//        if(cxEnroll == null){
//            log.info("当前用户{{}】没有报名记录",openId);
//            return Result.failed((IResult) null);
//        }
//        EnrollShowVO enrollShowVO = BeanUtil.copyProperties(cxEnroll, EnrollShowVO.class);
//        enrollShowVO.setStreetNo(cxEnroll.getStreetNo().getName());
//        log.info("获取用户[{}]报名记录：[{}]", openId, JSONUtil.toJsonStr(enrollShowVO));
//
//        return Result.succeed(enrollShowVO);
//    }

    @ApiOperation("保存报名记录")
    @PostMapping("/saveUserEnroll")
    public Result<Boolean> saveUserEnroll(@RequestBody @Valid EnrollDTO enrollDTO) {

        CxEnroll cxEnroll = cxEnrollService.getOne(Wrappers.lambdaQuery(CxEnroll.class)
                .eq(CxEnroll::getEnrollNum, enrollDTO.getEnrollNum())
                .eq(CxEnroll::getPhoneContact, enrollDTO.getPhoneContact()));

        Assert.isTrue(cxEnroll == null, "请勿重复提交");

        CxEnroll cxEnroll1 = BeanUtil.copyProperties(enrollDTO, CxEnroll.class);

        //出生日期根据身份证号获取
        cxEnroll1.setBirthday(DateUtil.parse(IdcardUtil.getBirth(enrollDTO.getCardNo()), "yyyyMMdd"));


        //街道名称 --> 街道编号
        cxEnroll1.setStreetNo(StreetNoEnum.getStreetNoEnumByName(enrollDTO.getStreetNo()));
        cxEnroll1.setRegisteredAddress(enrollDTO.getStreetNo() + enrollDTO.getStreetNoCity());

        //电子照片 网络地址
        cxEnroll1.setFaceImg(enrollDTO.getPicture());

        cxEnroll1.setCreateTime(new Date());

        boolean save = cxEnrollService.save(cxEnroll1);

        return Result.succeed(save);
    }
}
