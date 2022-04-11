package com.ysq.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/8 17:02
 * @Version 1.0
 */
public interface OrderMapper {

    @Select("select 'order'")
    String getOrder();

}
