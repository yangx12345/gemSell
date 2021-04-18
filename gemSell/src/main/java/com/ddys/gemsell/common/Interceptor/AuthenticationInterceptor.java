package com.ddys.gemsell.common.Interceptor;

import com.ddys.gemsell.common.exception.BusinessException;
import com.ddys.gemsell.common.result.ResultEnum;
import com.ddys.gemsell.common.utils.JwtUtils;
import com.ddys.gemsell.common.utils.StringUtils;
import com.ddys.gemsell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Description: 权限过滤
 *
 **/
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
    private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("token");
		if (StringUtils.isEmpty(token))
		{
			throw new BusinessException(ResultEnum.NO_AUTH.getCode(),ResultEnum.NO_AUTH.getMessage());
		}
		String userName = JwtUtils.getClaimValueByToken(token,"userName");
		String password = userService.getPasswordByUserName(userName);
		if (StringUtils.isEmpty(password) || JwtUtils.verify(token,password) == null)
		{
			throw new BusinessException(ResultEnum.ERROR.getCode(),"用户登陆信息失效，请重新登录");
		}
		return true;
	}

	private void setCorsMappings(HttpServletRequest request, HttpServletResponse response){
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
		response.setHeader("Access-Control-Allow-Credentials", "true");
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}
