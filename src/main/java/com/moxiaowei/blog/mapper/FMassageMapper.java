package com.moxiaowei.blog.mapper;

import java.util.List;

import com.moxiaowei.blog.pojo.FMassage;

public interface FMassageMapper {

	List<FMassage> getFMassage(long dfuserid);
	
	int addFMassage(FMassage fMassage);
	
	/**
	 * 将状态设置成已读1
	 * @return
	 * @author moxiaowei
	 */
	int updstate(long dfuserid);
	
	int delFMassage(long id, long dfuserid);
	
	/**
	 * 清空当前用户消息
	 * @param dfuserid
	 * @return
	 * @author moxiaowei
	 */
	int empty(long dfuserid);
	
	/**
	 * 获取新消息
	 * @param dfuserid
	 * @return
	 * @author moxiaowei
	 */
	int getStateIsZero(long dfuserid);

}
