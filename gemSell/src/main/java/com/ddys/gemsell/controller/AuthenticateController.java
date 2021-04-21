package com.ddys.gemsell.controller;


import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ddys.gemsell.common.utils.FileUtils;
import com.ddys.gemsell.entity.Goods;
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

import com.ddys.gemsell.service.AuthenticateService;
import com.ddys.gemsell.entity.Authenticate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/authenticate")
public class AuthenticateController {
    @Autowired
    private AuthenticateService authenticateService;

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
        return ResultUtil.success(authenticateService.getEntityById(id));
    }

    /**
    * 根据条件查询实体列表
    */
    @PostMapping("getListByCondition")
    public Result getListByCondition(@RequestBody Authenticate condition,@RequestParam("pageIndex") Integer pageIndex,@RequestParam("pageSize") Integer pageSize)
    {
        if(condition==null || pageIndex==null || pageIndex< 1||pageSize==null||pageSize< 1){
		 return ResultUtil.parameterError();
        }
		PageHelper.startPage(pageIndex, pageSize);
        List<Authenticate> list= authenticateService.getListByCondition(condition);
		PageInfo<Authenticate> result = new PageInfo<>(list);
        return ResultUtil.success(result);
    }

    /**
    * 增加实体
    */
    @PostMapping("add")
    public Result add(@RequestBody Authenticate entity)
    {
        if(entity==null){
		return ResultUtil.parameterError();
        }
        entity.setCreateTime(LocalDateTime.now());
        return ResultUtil.judgmentResult(authenticateService.saveEntity(entity));
    }


    /**
    * 更新实体
    */
    @PutMapping("update")
    public Result update(@RequestBody Authenticate entity)
    {
        if(entity==null){
		return ResultUtil.parameterError();
        }
        return ResultUtil.judgmentResult(authenticateService.updateEntity(entity));
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
        return ResultUtil.judgmentResult(authenticateService.deleteById(id));
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
        return ResultUtil.judgmentResult(authenticateService.deleteByIds(ids));
    }

    @PostMapping(value = "/imageUpload")
    public Result goodsImageUpload(@RequestParam(value = "file", required = false) MultipartFile file) {
        JSONObject path = new JSONObject();
        if (file.isEmpty()) {
            return ResultUtil.error("图片为空");
        } else {
            String fileName = file.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String filePath = FileUtils.AUTH_UPLOAD_PATH;
            fileName = "auth" + "-" + UUID.randomUUID() + suffixName; // 新文件名
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                filePath = FileUtils.BASH_URL + "auth/" + fileName;
                path.put("name",file.getOriginalFilename());
                path.put("url",filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
          return ResultUtil.success("上传成功",path);
    }
}

