package com.ddys.gemsell.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ddys.gemsell.common.utils.FileUtils;
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

import com.ddys.gemsell.service.GoodsService;
import com.ddys.gemsell.entity.Goods;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

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
        return ResultUtil.success(goodsService.getEntityById(id));
    }

    /**
    * 根据条件查询实体列表
    */
    @PostMapping("getListByCondition")
    public Result getListByCondition(@RequestBody Goods condition,@RequestParam("pageIndex") Integer pageIndex,@RequestParam("pageSize") Integer pageSize)
    {
        if(condition==null || pageIndex==null || pageIndex< 1||pageSize==null||pageSize< 1){
		 return ResultUtil.parameterError();
        }
		PageHelper.startPage(pageIndex, pageSize);
        List<Goods> list= goodsService.getListByCondition(condition);
		PageInfo<Goods> result = new PageInfo<>(list);
        return ResultUtil.success(result);
    }

    /**
    * 增加实体
    */
    @PostMapping("add")
    public Result add(@RequestBody Goods entity)
    {
        if(entity==null){
		return ResultUtil.parameterError();
        }
        return ResultUtil.judgmentResult(goodsService.saveEntity(entity));
    }


    /**
    * 更新实体
    */
    @PutMapping("update")
    public Result update(@RequestBody Goods entity)
    {
        if(entity==null){
		return ResultUtil.parameterError();
        }
        return ResultUtil.judgmentResult(goodsService.updateEntity(entity));
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
        return ResultUtil.judgmentResult(goodsService.deleteById(id));
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
        return ResultUtil.judgmentResult(goodsService.deleteByIds(ids));
    }

    @PostMapping(value = "/goodsImageUpload")
    public Result goodsImageUpload(
                                  @RequestParam(value = "goodId", required = false) Integer goodId,
                                  @RequestParam(value = "file", required = false) MultipartFile[] files) {

        JSONArray pathJsonArray = new JSONArray();
        for(MultipartFile file:files)
        {
            if (file.isEmpty()) {
                return ResultUtil.error("图片为空");
            } else {
                String fileName = file.getOriginalFilename();  // 文件名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                String filePath = FileUtils.UPLOAD_PATH;
                fileName = "good"+ goodId + suffixName; // 新文件名
                File dest = new File(filePath + fileName);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(dest);
                    filePath = filePath+FileUtils.PICTURE_PATH+fileName;
                    JSONObject path = new JSONObject();
                    path.put(fileName,filePath);
                    pathJsonArray.add(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        }
        Goods goods = goodsService.getById(goodId);
        goods.setImgAddress(pathJsonArray.toString());
        boolean flag = goodsService.updateEntity(goods);
        if (flag) {
            return ResultUtil.success("上传成功");
        }
        return ResultUtil.error("上传失败");
    }
}

