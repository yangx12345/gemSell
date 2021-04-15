package com.ddys.gemsell.service;

import com.ddys.gemsell.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;


public interface AddressService extends IService<Address> {

    /**
     * 根据id查询实体
     */
    public Address getEntityById(Integer id);

    /**
     * 根据条件查询实体列表
     */
    public List<Address> getListByCondition(Address condition);

    /**
     *  保存实体
     */
    public boolean saveEntity(Address entity);

    /**
     *  更新实体
     */
    public boolean updateEntity(Address entity);

    /**
     *  根据id删除实体
     */
    public boolean deleteById(Integer id);

    /**
     *  根据id列表删除
     */
    public boolean deleteByIds(String ids);

}
