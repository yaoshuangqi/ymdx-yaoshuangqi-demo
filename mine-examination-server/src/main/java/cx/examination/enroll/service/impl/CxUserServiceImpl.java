package cx.examination.enroll.service.impl;

import cx.examination.enroll.entity.CxUser;
import cx.examination.enroll.mapper.CxUserMapper;
import cx.examination.enroll.service.ICxUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yaoshuangqi
 * @since 2022-05-06
 */
@Service
public class CxUserServiceImpl extends ServiceImpl<CxUserMapper, CxUser> implements ICxUserService {

}
