package com.moxiaowei.blog.service;

import java.util.List;

import com.moxiaowei.blog.pojo.Discuss;

/**
 * 博客评论
 * @author moxiaowei
 *
 */
public interface DiscussService {

	boolean addDiscuss(Discuss discuss);
	
	boolean delDiscuss(long id, long fuserid);
	
	boolean updDiscuss(Discuss discuss);
	/**
	 * 获取评论列表，用于博客下方
	 * @param discuss
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author moxiaowei
	 */
	List<Discuss> getDiscuss(Discuss discuss, int pageNum, int pageSize);
	
	/**
	 * 获取评论列表，用于用户详情
	 * @param fuserid
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author moxiaowei
	 */
	List<Discuss> getDiscussByFUser(long fuserid, int pageNum, int pageSize);
}
