package com.moxiaowei.blog.impl;

import com.github.pagehelper.PageHelper;
import com.moxiaowei.blog.mapper.BlogMapper;
import com.moxiaowei.blog.pojo.Blog;
import com.moxiaowei.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

	@Autowired
	private BlogMapper blogMapper;

	@Override
	public List<Blog> getBlog(int page, int rows) {
		PageHelper.startPage(page, rows);
		return this.blogMapper.getBlog();
	}

	@Override
	public Blog getBlogById(long id) {
		Blog blog = new Blog();
		blog.setId(id);
		List<Blog> blogBy = this.blogMapper.getBlogBy(blog);
		if(blogBy != null && blogBy.size() > 0)
			return blogBy.get(0);
		return null;
	}

	@Override
	public List<Blog> getBlogByBlog(Blog blog, int page, int rows) {
		PageHelper.startPage(page, rows);
		return this.blogMapper.getBlogBy2(blog);
	}


	@Override
	public boolean addreadcount(long id) {
		int addreadcount = this.blogMapper.addreadcount(id);
		return addreadcount > 0;
	}

	@Override
	public Blog getBlogNew() {
		PageHelper.startPage(1, 1);
		List<Blog> blogNew = this.blogMapper.getBlogNew();
		if(blogNew != null &&blogNew.size() > 0)
			return blogNew.get(0);
		return null;
	}

}
