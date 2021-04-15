package com.ddys.gemsell.mapper;

import com.ddys.gemsell.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {

    /**
     * 根据查询条件查询实体列表
     */
    List<Cart> getListByCondition(Cart condition);

}
