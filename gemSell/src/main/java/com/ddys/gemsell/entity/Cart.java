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
@TableName("cart")
public class Cart implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 购物车订单id
     */
    @TableId(value = "cart_id", type = IdType.AUTO)
    private Integer cartId;

    /**
     * 商品id
     */
    @TableField("good_id")
    private Integer goodId;

    /**
     * 商品名称
     */
    @TableField("good_name")
    private String goodName;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;


}
