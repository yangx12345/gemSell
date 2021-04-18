package com.ddys.gemsell.mapper;

import com.ddys.gemsell.entity.Authenticate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthenticateMapper extends BaseMapper<Authenticate> {

    /**
     * 根据查询条件查询实体列表
     */
    List<Authenticate> getListByCondition(Authenticate condition);

}
