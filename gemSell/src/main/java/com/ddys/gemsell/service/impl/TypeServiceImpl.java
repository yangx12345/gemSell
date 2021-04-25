package com.ddys.gemsell.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ddys.gemsell.common.form.SelectForm;
import com.ddys.gemsell.entity.Type;
import com.ddys.gemsell.mapper.TypeMapper;
import com.ddys.gemsell.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.convert.Convert;


import java.util.ArrayList;
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
    public List<SelectForm> getTypeSelect() {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("type_id","type_name").eq("parent_id","0");
        List<Type> list = baseMapper.selectList(queryWrapper);
        List<SelectForm> selectFormList = new ArrayList<>();
        for(Type type:list)
        {
            SelectForm selectForm = new SelectForm();
            selectForm.setId(type.getTypeId());
            selectForm.setLabel(type.getTypeName());
            QueryWrapper<Type> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.select("type_id","type_name").eq("parent_id",type.getTypeId());
            List<Type> list1 = baseMapper.selectList(queryWrapper1);
            List<SelectForm> selectFormList1 = new ArrayList<>();
            for (Type type1:list1)
            {
                SelectForm selectForm1 = new SelectForm();
                selectForm1.setId(type1.getTypeId());
                selectForm1.setLabel(type1.getTypeName());
                selectForm1.setChildren(new ArrayList<>());
                selectFormList1.add(selectForm1);
            }
            selectForm.setChildren(new ArrayList<>());
            if (!selectFormList1.isEmpty()) {
                selectForm.setChildren(selectFormList1);
            }
            selectFormList.add(selectForm);
        }
        return selectFormList;
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean deleteByIds(String ids){
        Integer[]Ids=Convert.toIntArray(ids);
        List<Integer>idList=Arrays.asList(Ids);
        return this.removeByIds(idList);
    }
}
