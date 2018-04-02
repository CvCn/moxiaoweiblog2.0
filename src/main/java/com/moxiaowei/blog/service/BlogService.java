package com.moxiaowei.blog.service;

import java.util.List;

import com.moxiaowei.blog.pojo.Blog;

/**
 * 博客操作
 * @author moxiaowei
 *
 */
public interface BlogService {

	/**
	 * 博客列表，按点击排序
	 * @param page
	 * @param rows
	 * @return
	 * @author moxiaowei
	 */
	List<Blog> getBlog(int page, int rows);

	/**
	 * 博客详情
	 * @param id
	 * @return
	 * @author moxiaowei
	 */
	Blog getBlogById(long id);

	/**
	 * 用于搜索
	 * @param blog
	 * @param page
	 * @param rows
	 * @return
	 * @author moxiaowei
	 */
	List<Blog> getBlogByBlog(Blog blog, int page, int rows);

	boolean addreadcount(long id);
	
	/**
	 * 获取最新添加的一个博客（按创建时间排序）
	 * @return
	 * @author moxiaowei
	 */
	Blog getBlogNew();
}
