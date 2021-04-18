package com.ddys.gemsell.service;

import com.ddys.gemsell.entity.TbProvince;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;


public interface TbProvinceService extends IService<TbProvince> {

    /**
     * 根据id查询实体
     */
    public TbProvince getEntityById(Integer id);

    /**
     * 根据条件查询实体列表
     */
    public List<TbProvince> getListByCondition(TbProvince condition);

    /**
     *  保存实体
     */
    public boolean saveEntity(TbProvince entity);

    /**
     *  更新实体
     */
    public boolean updateEntity(TbProvince entity);

    /**
     *  根据id删除实体
     */
    public boolean deleteById(Integer id);

    /**
     *  根据id列表删除
     */
    public boolean deleteByIds(String ids);

}
