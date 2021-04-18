package com.ddys.gemsell.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order")
public class Order implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 订单id
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

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
     * 状态，0待付款，1已付代发，2已发，3取消，4完成
     */
    @TableField("status")
    private String status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 完成时间（包含完成和取消订单时间）
     */
    @TableField("success_time")
    private LocalDateTime successTime;


}
