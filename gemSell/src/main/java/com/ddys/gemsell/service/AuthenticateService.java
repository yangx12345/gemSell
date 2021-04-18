package com.ddys.gemsell.service;

import com.ddys.gemsell.entity.Authenticate;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;


public interface AuthenticateService extends IService<Authenticate> {

    /**
     * 根据id查询实体
     */
    public Authenticate getEntityById(Integer id);

    /**
     * 根据条件查询实体列表
     */
    public List<Authenticate> getListByCondition(Authenticate condition);

    /**
     *  保存实体
     */
    public boolean saveEntity(Authenticate entity);

    /**
     *  更新实体
     */
    public boolean updateEntity(Authenticate entity);

    /**
     *  根据id删除实体
     */
    public boolean deleteById(Integer id);

    /**
     *  根据id列表删除
     */
    public boolean deleteByIds(String ids);

}
