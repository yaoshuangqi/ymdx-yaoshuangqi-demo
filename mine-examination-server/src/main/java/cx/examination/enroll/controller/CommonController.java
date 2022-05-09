package cx.examination.enroll.controller;

import com.whxd.saas.core.response.Result;
import cx.examination.enroll.model.EnrollShowVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

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
     　　* @description: 图片上传
     　　* @date 2022-05-07 16:13
     　　*/
    @Value("${upload-path}")
    private String realPath;

    @RequestMapping(value ="upload", method = RequestMethod.POST)
//    图片是以content-type为multipart/form-data的格式上传的，所以使用spring-mvc可以通过使用参数的形式以二进制的格式获取到该图片。
    public String upload(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        log.info("执行图片上传");
        String userId = request.getParameter("userId");
        String path ;
        String type ;
        String avator;
        if(!file.isEmpty()) {
            log.info("成功获取照片");
            String fileName = file.getOriginalFilename();
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            log.info("图片初始名称为：" + fileName + " 类型为：" + type);
            if (type != null) {
                if ("PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {

                    log.info("图片根路径："+realPath);
                    // 自定义的文件名称
                    String trueFileName ="cx"+"-"+System.currentTimeMillis()+"." + type;
                    log.info("图片自定义名称为：" + trueFileName + " 类型为：" + type);
                    // 设置存放图片文件的路径
                    path = realPath +trueFileName;
                    log.info("存放图片文件的路径:" + path);
                    //判断文件父目录是否存在
                    File dest=new File(path);
                    if(!dest.getParentFile().exists()){
                        dest.getParentFile().mkdir();
                    }
                    //保存文件
                    file.transferTo(new File(path));
                    log.info("文件成功上传到指定目录下");
                    avator=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/image/" + trueFileName;
                    log.info("数据库存放图片文件的路径:" + avator);
                }else {
                    log.info("不是我们想要的文件类型,请按要求重新上传");
                    return "error";
                }
            }else {
                log.info("文件类型为空");
                return "error";
            }
        }else {
            log.info("没有找到相对应的文件");
            return "error";
        }
        return avator;
    }

}
