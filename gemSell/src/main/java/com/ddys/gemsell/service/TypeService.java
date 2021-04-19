package com.ddys.gemsell.service;

import com.ddys.gemsell.common.form.SelectForm;
import com.ddys.gemsell.entity.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;


public interface TypeService extends IService<Type> {

    /**
     * 根据id查询实体
     */
    public Type getEntityById(Integer id);

    /**
     * 根据条件查询实体列表
     */
    public List<Type> getListByCondition();

    /**
     *  保存实体
     */
    public boolean saveEntity(Type entity);

    /**
     *  更新实体
     */
    public boolean updateEntity(Type entity);

    /**
     *  根据id删除实体
     */
    public boolean deleteById(Integer id);

    /**
     *  根据id列表删除
     */
    public boolean deleteByIds(String ids);

	List<SelectForm> getTypeSelect();
}
