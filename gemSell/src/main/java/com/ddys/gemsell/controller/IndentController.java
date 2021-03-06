package com.ddys.gemsell.controller;


import com.ddys.gemsell.common.result.Result;
import com.ddys.gemsell.common.result.ResultUtil;
import com.ddys.gemsell.common.utils.StringUtils;
import com.ddys.gemsell.entity.Goods;
import com.ddys.gemsell.entity.Indent;
import com.ddys.gemsell.service.GoodsService;
import com.ddys.gemsell.service.IndentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class IndentController {
    @Autowired
    private IndentService indentService;

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
        return ResultUtil.success(indentService.getEntityById(id));
    }

    /**
    * 根据条件查询实体列表
    */
    @PostMapping("getListByCondition")
    public Result getListByCondition(@RequestBody Indent condition,@RequestParam("pageIndex") Integer pageIndex,@RequestParam("pageSize") Integer pageSize)
    {
        if(condition==null || pageIndex==null || pageIndex< 1||pageSize==null||pageSize< 1){
		 return ResultUtil.parameterError();
        }
		PageHelper.startPage(pageIndex, pageSize);
        List<Indent> list= indentService.getListByCondition(condition);
		PageInfo<Indent> result = new PageInfo<>(list);
        return ResultUtil.success(result);
    }

    /**
    * 增加实体
    */
    @PostMapping("add")
    public Result add(@RequestBody Indent entity)
    {
        if(entity==null){
		return ResultUtil.parameterError();
        }
        entity.setCreateTime(LocalDateTime.now());
        if ("3".equals(entity.getStatus()) || "4".equals(entity.getStatus()))
        {
            entity.setSuccessTime(LocalDateTime.now());
        }
        boolean flag = indentService.saveEntity(entity);
        if (flag)
        {
            return ResultUtil.success(entity.getOrderId());
        }
        else
        {
            return ResultUtil.error("添加失败");
        }
    }


    /**
    * 更新实体
    */
    @PutMapping("update")
    public Result update(@RequestBody Indent entity)
    {
        if(entity==null){
		return ResultUtil.parameterError();
        }
        Indent findIndent = indentService.getEntityById(entity.getOrderId());
        if ("3".equals(findIndent.getStatus()) || "4".equals(findIndent.getStatus()))
        {
            return ResultUtil.judgmentResult(indentService.updateEntity(entity));
        }
        else
        {
            if ("3".equals(entity.getStatus()) || "4".equals(entity.getStatus()))
            {
                entity.setSuccessTime(LocalDateTime.now());
            }
            return ResultUtil.judgmentResult(indentService.updateEntity(entity));
        }

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
        return ResultUtil.judgmentResult(indentService.deleteById(id));
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
        return ResultUtil.judgmentResult(indentService.deleteByIds(ids));
    }

    /**
     * 根据ids批量查找订单
     */
    @GetMapping("getByIds")
    public Result getByIds(@RequestParam String ids)
    {
        if(StringUtils.isBlank(ids))
        {
            return ResultUtil.parameterError();
        }
        return ResultUtil.success(indentService.getByIds(ids));
    }

    /**
     * 批量添加订单
     */
    @PostMapping("batchAdd")
    public Result batchAdd(@RequestBody List<Indent> Indents)
    {
        if(Indents.isEmpty())
        {
            return ResultUtil.parameterError();
        }
        Indents.forEach(indent -> indent.setCreateTime(LocalDateTime.now()));
        boolean flag = indentService.batchAdd(Indents);
        if (flag) {
            List<Integer> ids = Indents.stream().map(Indent::getOrderId).collect(Collectors.toList());
            return ResultUtil.success(ids);
        }
        return ResultUtil.error("批量增加失败");
    }

    /**
     * 批量添加订单
     */
    @PostMapping("batchUpdate")
    public Result batchUpdate(@RequestBody List<Indent> Indents)
    {
        if(Indents.isEmpty())
        {
            return ResultUtil.parameterError();
        }
        return ResultUtil.judgmentResult(indentService.batchUpdate(Indents));
    }

    @PostMapping("audit")
    public Result audit(@RequestBody Indent entity)
    {
        if (entity == null)
        {
            return ResultUtil.parameterError();
        }
        if ("3".equals(entity.getStatus()))
        {
            Goods goods = goodsService.getEntityById(entity.getGoodId());
            if (goods == null)
            {
                return ResultUtil.error("该订单商品不存在");
            }
            goods.setRemainNumber(goods.getRemainNumber()+1);
            entity.setCancalTime(LocalDateTime.now());
            goodsService.updateEntity(goods);
        }
        indentService.updateEntity(entity);
        return ResultUtil.success("审核成功");
    }
}

