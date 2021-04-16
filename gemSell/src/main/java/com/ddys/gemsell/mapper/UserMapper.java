package com.ddys.gemsell.mapper;

import com.ddys.gemsell.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据查询条件查询实体列表
     */
    List<User> getListByCondition(User condition);

	String getPasswordByUserName(String userName);
}
