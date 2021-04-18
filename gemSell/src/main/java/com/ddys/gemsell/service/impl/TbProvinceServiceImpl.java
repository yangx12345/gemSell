package com.ddys.gemsell.service.impl;

import com.ddys.gemsell.entity.TbProvince;
import com.ddys.gemsell.mapper.TbProvinceMapper;
import com.ddys.gemsell.service.TbProvinceService;
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
public class TbProvinceServiceImpl extends ServiceImpl<TbProvinceMapper, TbProvince>implements TbProvinceService {

    @Override
    public TbProvince getEntityById(Integer id){
        return this.getById(id);
    }

    @Override
    public List<TbProvince>getListByCondition(TbProvince condition){
		return baseMapper.getListByCondition(condition);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean saveEntity(TbProvince entity){
        return this.save(entity);
    }

    @Override
    @Transactional(readOnly=false,rollbackFor=Exception.class)
    public boolean updateEntity(TbProvince entity){
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
