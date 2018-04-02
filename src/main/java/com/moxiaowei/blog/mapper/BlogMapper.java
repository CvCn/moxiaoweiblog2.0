package com.moxiaowei.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moxiaowei.blog.pojo.Blog;

public interface BlogMapper {

	int addBlog(@Param("blog") Blog blog);
	
	int delBlog(Long id);
	
	int updataBlog(@Param("blog") Blog blog);
	
	/**
	 * 获取博客，用于主页
	 * @return
	 * @author moxiaowei
	 */
	List<Blog> getBlog();
	
	/**
	 * 根据条件查询
	 * @param blog
	 * @return
	 * @author moxiaowei
	 */
	List<Blog> getBlogBy(@Param("blog") Blog blog);
	
	/**
	 * 不包含content字段
	 * @param blog
	 * @return
	 * @author moxiaowei
	 */
	List<Blog> getBlogBy2(@Param("blog") Blog blog);
	
	/**
	 * 添加
	 * @param id
	 * @return
	 * @author moxiaowei
	 */
	int addreadcount(long id);
	
	/**
	 * 获取最新的一个博客
	 * @return
	 * @author moxiaowei
	 */
	List<Blog> getBlogNew();
}
