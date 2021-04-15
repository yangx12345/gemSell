package com.ddys.gemsell.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("goods")
public class Goods implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 商品id
     */
    @TableId(value = "good_id", type = IdType.AUTO)
    private Integer goodId;

    /**
     * 商品名称
     */
    @TableField("good_name")
    private String goodName;

    /**
     * 类型id
     */
    @TableField("type_id")
    private Integer typeId;

    /**
     * 类型名称
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 商品介绍
     */
    @TableField("
introduce")
    private String 
introduce;

    /**
     * 图片地址
     */
    @TableField("img_address")
    private String imgAddress;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

    @TableField("purchase_price")
    private BigDecimal purchasePrice;

    /**
     * 商品状态（0未发布，1已发布未售，2已售）
     */
    @TableField("status")
    private Integer status;


}
