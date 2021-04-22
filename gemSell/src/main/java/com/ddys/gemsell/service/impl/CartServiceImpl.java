package com.ddys.gemsell.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ddys.gemsell.entity.Cart;
import com.ddys.gemsell.mapper.CartMapper;
import com.ddys.gemsell.service.CartService;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart>implements CartService {

    @Override
    public Cart getEntityById(Integer id){
        return this.getById(id);
    }

    @Override
    public List<Cart>getListByCondition(Cart condition){
		return baseMapper.getListByCondition(condition);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean saveEntity(Cart entity){
        return this.save(entity);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean updateEntity(Cart entity){
        return this.updateById(entity);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean deleteById(Integer id){
        return this.removeById(id);
    }

    @Override
    public Cart getByUserIdAndGoodId(Integer userId, Integer goodId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("good_id",goodId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean deleteByIds(String ids){
        Integer[]Ids=Convert.toIntArray(ids);
        List<Integer>idList=Arrays.asList(Ids);
        return this.removeByIds(idList);
    }
}
