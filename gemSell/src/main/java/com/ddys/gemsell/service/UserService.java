package com.ddys.gemsell.service;

import com.ddys.gemsell.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;


public interface UserService extends IService<User> {

    /**
     * 根据id查询实体
     */
    public User getEntityById(Integer id);

    /**
     * 根据条件查询实体列表
     */
    public List<User> getListByCondition(User condition);

    /**
     *  保存实体
     */
    public boolean saveEntity(User entity);

    /**
     *  更新实体
     */
    public boolean updateEntity(User entity);

    /**
     *  根据id删除实体
     */
    public boolean deleteById(Integer id);

    /**
     *  根据id列表删除
     */
    public boolean deleteByIds(String ids);

    /**
     *
     *根据用户名密码查询用户
     *
     **/
	User getByUsernameAndPassword(String userName, String password);

	String getPasswordByUserName(String userName);

	User getByUsername(String userName);

	User getUserInfoByUserId(Integer userId);

	List<User> getUserList();
}
