package com.ddys.gemsell.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("type")
public class Type implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 分类id
     */
    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId;

    /**
     * 分类名
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 一级分类父id为0
     */
    @TableField("parent_id")
    private Integer parentId;

    @TableField(exist = false)
    private List<Type> children;


}
