package com.moxiaowei.blog.controller;

import com.github.pagehelper.PageInfo;
import com.moxiaowei.blog.pojo.Blog;
import com.moxiaowei.blog.service.BlogService;
import com.moxiaowei.blog.util.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * 
 * @author moxiaowei
 *
 */
@Controller
@RequestMapping("/api")
public class BlogController {

	@Autowired
	private BlogService blogServiceImpl;
	
	
	@RequestMapping(
			value="/blogList/{pageNum}",
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
			method=RequestMethod.GET)
	@ResponseBody
	public RestMessage<List<Blog>> getBlogListAsyn(@PathVariable int pageNum){
		List<Blog> blog = this.blogServiceImpl.getBlog(pageNum, 5);
		Blog blogNew = this.blogServiceImpl.getBlogNew();
		RestMessage<List<Blog>> rm = new RestMessage<>();
		if(blog != null && blog.size() > 0){
			blog.remove(blogNew);
			rm.set200(blog);
		}
		return rm;
	}
	
	@RequestMapping(
			value="/blogNew", 
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
			method=RequestMethod.GET)
	@ResponseBody
	public RestMessage<Blog> getBlogNew(){
		Blog blogNew = this.blogServiceImpl.getBlogNew();
		RestMessage<Blog> rm = new RestMessage<>();
		if(blogNew != null)
			rm.set200(blogNew);
		return rm;
	}
	
	
	@RequestMapping(
			value="/blog/{id}", 
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8", 
			method=RequestMethod.GET)
	@ResponseBody
	public RestMessage<Blog> getBlog(@PathVariable long id){
		Blog blog = this.blogServiceImpl.getBlogById(id);
		RestMessage<Blog> rm = new RestMessage<>();
		if(blog == null) return rm;
		blog.setContent2(new String(blog.getContent()));
		blog.setContent(null);
		this.blogServiceImpl.addreadcount(id);
		rm.set200(blog);
		return rm;
	}

	
	@RequestMapping(
			value="/searchBlog/{pageNum}/{searchText}", 
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
			method=RequestMethod.GET)
	@ResponseBody
	public RestMessage<PageInfo<Blog>> search(
			@PathVariable String searchText, 
			@PathVariable Integer pageNum){
		RestMessage<PageInfo<Blog>> rm = new RestMessage<>();
		
		Blog blog = new Blog();
		if (searchText != null && !searchText.equals("")){
			
			try {
				searchText = URLDecoder.decode(searchText, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return rm;
			}
			
			searchText = searchText.toLowerCase();
			blog.setTitle(searchText);
		}
		List<Blog> blogByBlog = this.blogServiceImpl.getBlogByBlog(blog, pageNum, 10);
		
		if (blogByBlog != null && blogByBlog.size() > 0) {
			PageInfo<Blog> info = new PageInfo<>(blogByBlog);
			rm.set200(info);
		}
		return rm;
	}
	
	@RequestMapping(
			value="/searchBlog/{pageNum}", 
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
			method=RequestMethod.GET)
	@ResponseBody
	public RestMessage<PageInfo<Blog>> search(
			@PathVariable Integer pageNum){
		return this.search(null, pageNum);
	}
}
