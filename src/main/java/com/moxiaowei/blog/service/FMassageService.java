package com.moxiaowei.blog.service;

import java.util.List;

import com.moxiaowei.blog.pojo.FMassage;

/**
 * 消息
 * @author moxiaowei
 *
 */
public interface FMassageService {

	List<FMassage> getFMassage(long dfuserid, int pageNum, int pageSize);

	boolean addFMassage(FMassage fMassage);

	/**
	 * 将状态设置成已读1
	 * 
	 * @return
	 * @author moxiaowei
	 */
	boolean updstate(long dfuserid);

	boolean delFMassage(long id, long dfuserid);

	/**
	 * 清空当前用户消息
	 * @param dfuserid
	 * @return
	 * @author moxiaowei
	 */
	boolean delEmpty(long dfuserid);
	
	int getStateIsZero(long dfuserid);
}
