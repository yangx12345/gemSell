package com.ddys.gemsell.mapper;

import com.ddys.gemsell.entity.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {

    /**
     * 根据查询条件查询实体列表
     */
    List<Address> getListByCondition(Address condition);

}
