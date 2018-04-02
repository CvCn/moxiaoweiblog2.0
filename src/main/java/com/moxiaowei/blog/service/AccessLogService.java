package com.moxiaowei.blog.service;

import java.util.List;

import com.moxiaowei.blog.pojo.AccessLog;

/**
 * 访问日志
 * @author moxiaowei
 *
 */
public interface AccessLogService {

	boolean addAccessLog(AccessLog al);
	
	List<AccessLog> getAccessLog(int pageNum, int pageSize);
	
	boolean delAccessLog(long id);
}
