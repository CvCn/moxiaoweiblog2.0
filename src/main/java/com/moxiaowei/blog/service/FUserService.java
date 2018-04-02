package com.moxiaowei.blog.service;

import com.moxiaowei.blog.pojo.FUser;

/**
 * 用户操作
 * @author moxiaowei
 *
 */
public interface FUserService {

	boolean addFUser(FUser user);
	
	/**
	 * 通过accesstoken获取用户
	 * @param accesstoken
	 * @return
	 * @author moxiaowei
	 */
	FUser getFUser(String accesstoken);
	
	/**
	 * 通过uid获取用户
	 * @param uid
	 * @return
	 * @author moxiaowei
	 */
	FUser getFUserByUid(String uid);
	
	/**
	 * 通过id获取用户
	 * @param in
	 * @return
	 * @author moxiaowei
	 */
	FUser getFUserById(long id);
	
	/**
	 * 用户主动注销操作，待添加功能
	 * @param accesstoken
	 * @return
	 * @author moxiaowei
	 */
	boolean delFUser(String accesstoken);
	/**
	 * 更新用户信息
	 * @param fuser
	 * @return
	 * @author moxiaowei
	 */
	boolean updFUser(FUser fuser);
}
