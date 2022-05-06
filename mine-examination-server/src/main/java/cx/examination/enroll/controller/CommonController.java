package cx.examination.enroll.controller;

import com.whxd.saas.core.response.Result;
import cx.examination.enroll.model.EnrollShowVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Desc 通用工具
 * @Author Mr.Yao
 * @Date 2022/5/6 21:51
 * @Version 1.0
 */
@RestController
@RequestMapping("/v1/mini/1.0/utils")
@Slf4j
public class CommonController {

    /**
     * 文件图片上传
     * @param userId
     * @param mobile
     * @return
     */
    @ApiOperation("文件图片上传")
    @PostMapping("/uploadImag")
    public Result<EnrollShowVO> uploadImag(@RequestPart("file") MultipartFile file) {


        return null;
    }
}
