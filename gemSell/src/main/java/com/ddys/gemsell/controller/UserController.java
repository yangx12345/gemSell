package com.ddys.gemsell.controller;


import cn.hutool.crypto.SecureUtil;
import com.ddys.gemsell.common.utils.JwtUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ddys.gemsell.common.result.Result;
import com.ddys.gemsell.common.result.ResultUtil;
import com.ddys.gemsell.common.utils.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ddys.gemsell.service.UserService;
import com.ddys.gemsell.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
    * 根据id查询实体
    */
    @GetMapping("getById/{id}")
    public Result getById(@PathVariable Integer id)
    {
        if(id==null)
        {
            return ResultUtil.parameterError();
        }
        return ResultUtil.success(userService.getEntityById(id));
    }

    /**
    * 根据条件查询实体列表
    */
    @PostMapping("getListByCondition")
    public Result getListByCondition(@RequestBody User condition,@RequestParam("pageIndex") Integer pageIndex,@RequestParam("pageSize") Integer pageSize)
    {
        if(condition==null || pageIndex==null || pageIndex< 1||pageSize==null||pageSize< 1){
		 return ResultUtil.parameterError();
        }
		PageHelper.startPage(pageIndex, pageSize);
        List<User> list= userService.getListByCondition(condition);
		PageInfo<User> result = new PageInfo<>(list);
        return ResultUtil.success(result);
    }

    /**
    * 增加实体
    */
    @PostMapping("add")
    public Result add(@RequestBody User entity)
    {
        if(entity==null){
		return ResultUtil.parameterError();
        }
        User findUser = userService.getByUsername(entity.getUserName());
        if (findUser!=null)
        {
            return ResultUtil.error("用户名已存在");
        }
        entity.setPassword(SecureUtil.md5(entity.getPassword()));
        return ResultUtil.judgmentResult(userService.saveEntity(entity));
    }


    /**
    * 更新实体
    */
    @PutMapping("update")
    public Result update(@RequestBody User entity)
    {
        if(entity==null){
		return ResultUtil.parameterError();
        }
        return ResultUtil.judgmentResult(userService.updateEntity(entity));
    }


    /**
    * 根据id删除
    */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id)
    {
        if(id==null){
		 return ResultUtil.parameterError();
        }
        return ResultUtil.judgmentResult(userService.deleteById(id));
    }

    /**
    * 根据id列表批量删除
    */
    @DeleteMapping("batchDelete")
    public Result batchDelete(@RequestParam String ids)
    {
        if(StringUtils.isBlank(ids))
        {
		  return ResultUtil.parameterError();
        }
        return ResultUtil.judgmentResult(userService.deleteByIds(ids));
    }

    /**
     *
     * 用户登录
     *
     * @date 2021-04-16 13:19
     * @param user 登录信息
     * @return com.ddys.gemsell.common.result.Result 返回结果
     **/
    @PostMapping("login")
    public Result login(@RequestBody User user)
    {
        if (user == null || StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword()))
        {
            return ResultUtil.parameterError();
        }
        User findUser  =  userService.getByUsernameAndPassword(user.getUserName(),user.getPassword());
        if (findUser == null)
        {
            return ResultUtil.error("账号密码错误或用户不存在");
        }
        Map<String,String> claimMap = new HashMap<>(4);
        claimMap.put("role",findUser.getRole());
        claimMap.put("userId",findUser.getUserId().toString());
        claimMap.put("userName",findUser.getUserName());
        String token = JwtUtils.sign(claimMap, SecureUtil.md5(user.getPassword()),null);
        return ResultUtil.success("登录成功",token);
    }

    @PostMapping("getUserInfoByToken")
    public Result getUserInfoByToken(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        Integer userId = Integer.valueOf(JwtUtils.getClaimValueByToken(token,"userId"));
        return ResultUtil.success(userService.getUserInfoByUserId(userId));
    }

    @PostMapping("restPassword")
    public Result changePassword(@RequestParam Integer userId)
    {
        if (userId == null)
        {
            return ResultUtil.parameterError();
        }
        User user  =  userService.getEntityById(userId);
        if (user == null)
        {
            return ResultUtil.error("用户不存在");
        }
        user.setPassword(SecureUtil.md5("123456"));
        return ResultUtil.judgmentResult(userService.updateEntity(user));
    }

    @PostMapping("register")
    public Result register(@RequestBody User user)
    {
        if (user == null || StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword()))
        {
            return ResultUtil.parameterError();
        }
        User findUser  =  userService.getByUsernameAndPassword(user.getUserName(),user.getPassword());
        if (findUser != null)
        {
            return ResultUtil.error("用户名已存在");
        }
        return ResultUtil.judgmentResult(userService.saveEntity(user));
    }

    @PostMapping("changePassword")
    public Result changePassword(@RequestParam String oldPassword,@RequestParam String newPassword,@RequestParam String userName)
    {
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword) || StringUtils.isBlank(userName))
        {
            return ResultUtil.parameterError();
        }
        User user  =  userService.getByUsernameAndPassword(userName,oldPassword);
        if (user == null)
        {
            return ResultUtil.error("原密码错误");
        }
        user.setPassword(SecureUtil.md5(newPassword));
        return ResultUtil.judgmentResult(userService.updateEntity(user));
    }

    @GetMapping("/getUserList")
    public Result getUserList(@RequestParam String role)
    {
        List<User> users = userService.getUserList(role);
        return ResultUtil.success(users);
    }

}

