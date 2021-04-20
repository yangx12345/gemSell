package com.ddys.gemsell.service;

import com.ddys.gemsell.entity.Indent;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;


public interface IndentService extends IService<Indent> {

    /**
     * 根据id查询实体
     */
    public Indent getEntityById(Integer id);

    /**
     * 根据条件查询实体列表
     */
    public List<Indent> getListByCondition(Indent condition);

    /**
     *  保存实体
     */
    public boolean saveEntity(Indent entity);

    /**
     *  更新实体
     */
    public boolean updateEntity(Indent entity);

    /**
     *  根据id删除实体
     */
    public boolean deleteById(Integer id);

    /**
     *  根据id列表删除
     */
    public boolean deleteByIds(String ids);

}
