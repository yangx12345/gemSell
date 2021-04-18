package com.ddys.gemsell.service;

import com.ddys.gemsell.entity.TbCity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;


public interface TbCityService extends IService<TbCity> {

    /**
     * 根据id查询实体
     */
    public TbCity getEntityById(Integer id);

    /**
     * 根据条件查询实体列表
     */
    public List<TbCity> getListByCondition(TbCity condition);

    /**
     *  保存实体
     */
    public boolean saveEntity(TbCity entity);

    /**
     *  更新实体
     */
    public boolean updateEntity(TbCity entity);

    /**
     *  根据id删除实体
     */
    public boolean deleteById(Integer id);

    /**
     *  根据id列表删除
     */
    public boolean deleteByIds(String ids);

}
