package com.ddys.gemsell.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("authenticate")
public class Authenticate implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 鉴定单id
     */
    @TableId(value = "authenticate_id", type = IdType.AUTO)
    private Integer authenticateId;

    /**
     * 请求人id
     */
    @TableField("owner_id")
    private Integer ownerId;

    /**
     * 请求人名
     */
    @TableField("owner_name")
    private String ownerName;

    /**
     * 鉴品图片
     */
    @TableField("img_address")
    private String imgAddress;

    /**
     * 鉴品名称
     */
    @TableField("treasure_name")
    private String treasureName;

    /**
     * 鉴品编码
     */
    @TableField("treasure_code")
    private String treasureCode;

    /**
     * 分类id
     */
    @TableField("type_id")
    private Integer typeId;

    /**
     * 分类名称
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 质地
     */
    @TableField("texture")
    private String texture;

    /**
     * 重量
     */
    @TableField("weight")
    private Double weight;

    /**
     * 产地
     */
    @TableField("form_city")
    private String formCity;

    /**
     * 鉴定人id
     */
    @TableField("auth_user_id")
    private Integer authUserId;

    /**
     * 鉴定人名
     */
    @TableField("auth_user_name")
    private String authUserName;

    /**
     * 鉴定结果
     */
    @TableField("result")
    private String result;


    /**
     * 鉴定状态
     */
    @TableField("status")
    private String status;

    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 鉴定时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("success_time")
    private LocalDateTime successTime;


}
