package com.ddys.gemsell.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("address")
public class Address implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 地址id
     */
    @TableId(value = "address_id", type = IdType.AUTO)
    private Integer addressId;

    /**
     * 省份id
     */
    @TableField("province_id")
    private Integer provinceId;

    /**
     * 城市id
     */
    @TableField("city_id")
    private Integer cityId;

    /**
     * 详细地址
     */
    @TableField("detailed")
    private String detailed;

    /**
     * 收货人名
     */
    @TableField("name")
    private String name;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 所属人id
     */
    @TableField("user_id")
    private Integer userId;


}
