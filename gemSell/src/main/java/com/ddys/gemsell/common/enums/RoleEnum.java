package com.ddys.gemsell.common.enums;

public enum RoleEnum {
	ADMIN(0,"系统管理员"),
	APPRAISAL(1,"鉴定"),
	GENERAL_USER(2,"普通用户")
	;

	private Integer code;
	private String description;

	RoleEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	RoleEnum() {
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
