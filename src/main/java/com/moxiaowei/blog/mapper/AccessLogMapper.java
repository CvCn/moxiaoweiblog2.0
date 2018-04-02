package com.moxiaowei.blog.mapper;

import java.util.List;

import com.moxiaowei.blog.pojo.AccessLog;

/**
 * 访问日志
 * @author moxiaowei
 *
 */
public interface AccessLogMapper {

	/**
	 * 添加日志
	 * @param al
	 * @return
	 * @author moxiaowei
	 */
	int addAccessLog(AccessLog al);
	
	/**
	 * 获取所以日志
	 * @return
	 * @author moxiaowei
	 */
	List<AccessLog> getAccessLog();
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @author moxiaowei
	 */
	int delAccess(long id);
}
