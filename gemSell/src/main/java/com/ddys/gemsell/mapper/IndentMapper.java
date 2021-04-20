package com.ddys.gemsell.mapper;

import com.ddys.gemsell.entity.Indent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndentMapper extends BaseMapper<Indent> {

    /**
     * 根据查询条件查询实体列表
     */
    List<Indent> getListByCondition(Indent condition);

}
