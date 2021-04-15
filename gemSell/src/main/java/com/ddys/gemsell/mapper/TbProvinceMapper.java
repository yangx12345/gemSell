package com.ddys.gemsell.mapper;

import com.ddys.gemsell.entity.TbProvince;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbProvinceMapper extends BaseMapper<TbProvince> {

    /**
     * 根据查询条件查询实体列表
     */
    List<TbProvince> getListByCondition(TbProvince condition);

}
