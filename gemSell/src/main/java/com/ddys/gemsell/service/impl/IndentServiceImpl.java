package com.ddys.gemsell.service.impl;

import com.ddys.gemsell.entity.Indent;
import com.ddys.gemsell.mapper.IndentMapper;
import com.ddys.gemsell.service.IndentService;
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
public class IndentServiceImpl extends ServiceImpl<IndentMapper, Indent>implements IndentService {

    @Override
    public Indent getEntityById(Integer id){
        return this.getById(id);
    }

    @Override
    public List<Indent>getListByCondition(Indent condition){
		return baseMapper.getListByCondition(condition);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean saveEntity(Indent entity){
        return this.save(entity);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean updateEntity(Indent entity){
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
        List<Integer> idList = Arrays.asList(Ids);
        return this.removeByIds(idList);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public Boolean batchUpdate(List<Indent> indents) {
        return this.updateBatchById(indents);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public Boolean batchAdd(List<Indent> indents) {
        return this.saveBatch(indents);
    }

    @Override
    public List<Indent> getByIds(String ids) {
        Integer[]Ids=Convert.toIntArray(ids);
        List<Integer> idList = Arrays.asList(Ids);
        return this.listByIds(idList);
    }


}
