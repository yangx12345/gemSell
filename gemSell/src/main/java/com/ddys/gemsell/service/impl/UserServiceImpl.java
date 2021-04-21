package com.ddys.gemsell.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ddys.gemsell.common.utils.StringUtils;
import com.ddys.gemsell.entity.User;
import com.ddys.gemsell.mapper.UserMapper;
import com.ddys.gemsell.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.convert.Convert;



import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService {

    @Override
    public User getEntityById(Integer id){
        return this.getById(id);
    }

    @Override
    public List<User>getListByCondition(User condition){
		return baseMapper.getListByCondition(condition);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean saveEntity(User entity){
        return this.save(entity);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean updateEntity(User entity){
        return this.updateById(entity);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean deleteById(Integer id){
        return this.removeById(id);
    }

    @Override
    public User getByUsernameAndPassword(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id","role","user_name").eq("user_name",userName).eq("password", SecureUtil.md5(password));
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean deleteByIds(String ids){
        Integer[]Ids=Convert.toIntArray(ids);
        List<Integer>idList=Arrays.asList(Ids);
        return this.removeByIds(idList);
    }

    @Override
    public List<User> getUserList(String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(role))
        {
            queryWrapper.select("user_id","user_name");
        }
        else
        {
            queryWrapper.select("user_id","user_name").eq("role",role);
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public User getUserInfoByUserId(Integer userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_name","role","name").eq("user_id",userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public User getByUsername(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id").eq("user_name",userName);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public String getPasswordByUserName(String userName)
    {
        if (StringUtils.isEmpty(userName))
        {
            return "";
        }
        return baseMapper.getPasswordByUserName(userName);
    }
}
