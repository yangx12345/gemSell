package com.ddys.gemsell.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ddys.gemsell.entity.Type;
import com.ddys.gemsell.mapper.TypeMapper;
import com.ddys.gemsell.service.TypeService;
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
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>implements TypeService {

    @Override
    public Type getEntityById(Integer id){
        return this.getById(id);
    }

    @Override
    public List<Type>getListByCondition(){
		List<Type> list = baseMapper.getListByCondition();
        for (Type type:list
             ) {
            QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id",type.getTypeId());
            type.setChildren(baseMapper.selectList(queryWrapper));
        }
        return list;
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean saveEntity(Type entity){
        return this.save(entity);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean updateEntity(Type entity){
        return this.updateById(entity);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean deleteById(Integer id){
        return this.removeById(id);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean deleteByIds(String ids){
        Integer[]Ids=Convert.toIntArray(ids);
        List<Integer>idList=Arrays.asList(Ids);
        return this.removeByIds(idList);
    }
}
