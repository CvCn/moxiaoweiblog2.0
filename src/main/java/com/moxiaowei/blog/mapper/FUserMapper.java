package com.moxiaowei.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moxiaowei.blog.pojo.FUser;

public interface FUserMapper {
	
	List<FUser> getFUserBy(FUser fuser);
	
	int addFUser(@Param("u") FUser fuser);
	
	int delFUser(String accesstoken);
	
	int updFUser(@Param("u") FUser fuser);
}
