package com.moxiaowei.blog.mapper;

import java.util.List;

import com.moxiaowei.blog.pojo.Grow;

public interface GrowMapper {

	List<Grow> selGrow();
	
	int insGrow(String content);
}
