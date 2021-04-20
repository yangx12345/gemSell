package com.ddys.gemsell.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ddys.gemsell.entity.Goods;
import com.ddys.gemsell.entity.User;
import com.ddys.gemsell.mapper.GoodsMapper;
import com.ddys.gemsell.service.GoodsService;
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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>implements GoodsService {

    @Override
    public Goods getEntityById(Integer id){
        return this.getById(id);
    }

    @Override
    public List<Goods>getListByCondition(Goods condition){
		return baseMapper.getListByCondition(condition);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean saveEntity(Goods entity){
        return this.save(entity);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean updateEntity(Goods entity){
        return this.updateById(entity);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean deleteById(Integer id){
        return this.removeById(id);
    }

    @Override
    public List<Goods> getGoodsList() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("good_id","good_name","price","remain_number");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean deleteByIds(String ids){
        Integer[]Ids=Convert.toIntArray(ids);
        List<Integer>idList=Arrays.asList(Ids);
        return this.removeByIds(idList);
    }
}
