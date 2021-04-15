package com.ddys.gemsell.mapper;

import com.ddys.gemsell.entity.TbCity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbCityMapper extends BaseMapper<TbCity> {

    /**
     * 根据查询条件查询实体列表
     */
    List<TbCity> getListByCondition(TbCity condition);

}
