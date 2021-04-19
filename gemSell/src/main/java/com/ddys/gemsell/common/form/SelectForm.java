package com.ddys.gemsell.common.form;

import lombok.Data;

import java.util.List;

/**
 * Description: 1
 *
 **/
@Data
public class SelectForm {

	private Integer id;

	private String label;

	private List<SelectForm> children;

}
