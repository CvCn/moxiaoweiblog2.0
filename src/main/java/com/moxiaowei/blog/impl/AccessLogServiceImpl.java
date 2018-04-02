package com.moxiaowei.blog.impl;

import com.github.pagehelper.PageHelper;
import com.moxiaowei.blog.mapper.AccessLogMapper;
import com.moxiaowei.blog.pojo.AccessLog;
import com.moxiaowei.blog.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {

	@Autowired
	private AccessLogMapper accessLogMapper;
	
	@Override
	public boolean addAccessLog(AccessLog al) {
		int addAccessLog = this.accessLogMapper.addAccessLog(al);
		return addAccessLog > 0;
	}

	@Override
	public List<AccessLog> getAccessLog(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.accessLogMapper.getAccessLog();
	}

	@Override
	public boolean delAccessLog(long id) {
		int delAccess = this.accessLogMapper.delAccess(id);
		return delAccess > 0;
	}

}
