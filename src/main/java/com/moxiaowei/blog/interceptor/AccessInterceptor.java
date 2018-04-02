package com.moxiaowei.blog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.moxiaowei.blog.pojo.AccessLog;
import com.moxiaowei.blog.service.AccessLogService;
import com.moxiaowei.blog.util.IpUtil;

/**
 * 访问日志
 * @author moxiaowei
 *
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

	
	@Autowired
	private AccessLogService accessLogServiceImpl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		IpUtil.load(classLoader.getResource("17monipdb.dat").getFile());
		String ip = IpUtil.getIp(request);
		try {
			String[] ipAddress = IpUtil.find(ip);
			AccessLog al = new AccessLog(request.getServletPath(), ip, StringUtils.join(ipAddress, ","));
			this.accessLogServiceImpl.addAccessLog(al);
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
