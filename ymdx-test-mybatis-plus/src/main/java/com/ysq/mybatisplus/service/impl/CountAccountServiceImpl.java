package com.ysq.mybatisplus.service.impl;

import com.ysq.mybatisplus.entity.CountAccount;
import com.ysq.mybatisplus.mapper.CountAccountMapper;
import com.ysq.mybatisplus.service.ICountAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 次数信息表 服务实现类
 * </p>
 *
 * @author yaoshuangqi
 * @since 2022-04-12
 */
@Service
public class CountAccountServiceImpl extends ServiceImpl<CountAccountMapper, CountAccount> implements ICountAccountService {

}
