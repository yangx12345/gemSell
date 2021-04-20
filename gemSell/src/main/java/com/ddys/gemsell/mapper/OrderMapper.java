package com.ddys.gemsell.mapper;

import com.ddys.gemsell.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据查询条件查询实体列表
     */
    List<Order> getListByCondition(Order condition);

}
