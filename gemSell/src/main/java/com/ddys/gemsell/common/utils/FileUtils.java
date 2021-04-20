package com.ddys.gemsell.common.utils;


import cn.hutool.core.io.FileUtil;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

public class FileUtils extends FileUtil {

	/**
	 * 项目地址
	 **/
	private static final String PROJECTPATH = System.getProperty("user.dir");

	public static final String  GOOD_UPLOAD_PATH = PROJECTPATH + "/src/main/resources/imgs/goods";

	public static final String BASH_URL = "http://localhost:8088/gemsell-api/imgs/";



}
