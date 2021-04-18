package com.ddys.gemsell.mapper;

import com.ddys.gemsell.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 根据查询条件查询实体列表
     */
    List<Goods> getListByCondition(Goods condition);

}
