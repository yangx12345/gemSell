package com.ddys.gemsell.service;

import com.ddys.gemsell.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;


public interface OrderService extends IService<Order> {

    /**
     * 根据id查询实体
     */
    public Order getEntityById(Integer id);

    /**
     * 根据条件查询实体列表
     */
    public List<Order> getListByCondition(Order condition);

    /**
     *  保存实体
     */
    public boolean saveEntity(Order entity);

    /**
     *  更新实体
     */
    public boolean updateEntity(Order entity);

    /**
     *  根据id删除实体
     */
    public boolean deleteById(Integer id);

    /**
     *  根据id列表删除
     */
    public boolean deleteByIds(String ids);

}
