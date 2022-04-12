package com.ysq.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ysq.mybatisplus.entity.CountAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 次数信息表 Mapper 接口
 * </p>
 *
 * @author yaoshuangqi
 * @since 2022-04-12
 */
@Repository
public interface CountAccountMapper extends BaseMapper<CountAccount> {

    /**
     * xml自定义分页
     * 根据年龄查询用户列表，分页显示
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位
     * @param appId appId
     * @return
     */
    Page<CountAccount> selectPageVo(@Param("page") Page<CountAccount> page, @Param("appId")
            String appId);
}
