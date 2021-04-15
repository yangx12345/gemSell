package com.ddys.gemsell.mapper;

import com.ddys.gemsell.entity.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    /**
     * 根据查询条件查询实体列表
     */
    List<Type> getListByCondition(Type condition);

}
