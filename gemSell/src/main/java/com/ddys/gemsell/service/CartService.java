package com.ddys.gemsell.service;

import com.ddys.gemsell.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;


public interface CartService extends IService<Cart> {

    /**
     * 根据id查询实体
     */
    public Cart getEntityById(Integer id);

    /**
     * 根据条件查询实体列表
     */
    public List<Cart> getListByCondition(Cart condition);

    /**
     *  保存实体
     */
    public boolean saveEntity(Cart entity);

    /**
     *  更新实体
     */
    public boolean updateEntity(Cart entity);

    /**
     *  根据id删除实体
     */
    public boolean deleteById(Integer id);

    /**
     *  根据id列表删除
     */
    public boolean deleteByIds(String ids);

}
