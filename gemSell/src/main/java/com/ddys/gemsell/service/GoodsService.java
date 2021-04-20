package com.ddys.gemsell.service;

import com.ddys.gemsell.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;


public interface GoodsService extends IService<Goods> {

    /**
     * 根据id查询实体
     */
    public Goods getEntityById(Integer id);

    /**
     * 根据条件查询实体列表
     */
    public List<Goods> getListByCondition(Goods condition);

    /**
     *  保存实体
     */
    public boolean saveEntity(Goods entity);

    /**
     *  更新实体
     */
    public boolean updateEntity(Goods entity);

    /**
     *  根据id删除实体
     */
    public boolean deleteById(Integer id);

    /**
     *  根据id列表删除
     */
    public boolean deleteByIds(String ids);

	List<Goods> getGoodsList();
}
