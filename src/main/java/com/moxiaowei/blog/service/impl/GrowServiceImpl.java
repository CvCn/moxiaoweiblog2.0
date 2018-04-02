package com.moxiaowei.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.moxiaowei.blog.mapper.GrowMapper;
import com.moxiaowei.blog.pojo.Grow;
import com.moxiaowei.blog.service.GrowService;

@Service
public class GrowServiceImpl implements GrowService {

	@Autowired
	private GrowMapper growMapper;
	
	@Override
	public List<Grow> getGrow(int page, int rows) {
		PageHelper.startPage(page, rows);
		return this.growMapper.selGrow();
	}

}
