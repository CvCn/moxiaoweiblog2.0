package com.moxiaowei.blog.service;

import java.util.Date;
import java.util.List;

import com.moxiaowei.blog.pojo.Blog;
import com.moxiaowei.blog.pojo.Discuss;

/**
 * 管理员操作
 * @author moxiaowei
 *
 */
public interface UserService {

	boolean login(String pwd);
	
	boolean addGrow(String content);
	
	boolean addBlog(Blog blog);
	
	boolean delBlog(long id);

	boolean updBlog(Blog blog);
	
	boolean delDiscuss(long id);
	
	boolean noticeAdd(String notice, int second);
	
	boolean noticeSetExpirt(int second);
	
	boolean noticeDel();
	
	long expirt(); 
	/**
	 * 获取所以评论
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author moxiaowei
	 */
	List<Discuss> getDiscuss(int pageNum, int pageSize);
	
	/**
	 * 临时保存
	 * @param key
	 * @param title
	 * @param content
	 * @param author
	 * @return
	 * @author moxiaowei
	 */
	Date temp(String key, String title, String content, String author);
	
	/**
	 * 获取临时数据
	 * @param key
	 * @return
	 * @author moxiaowei
	 */
	Blog getTemp(String key);
	
}
