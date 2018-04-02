package com.moxiaowei.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.moxiaowei.blog.mapper.BlogMapper;
import com.moxiaowei.blog.mapper.DiscussMapper;
import com.moxiaowei.blog.mapper.GrowMapper;
import com.moxiaowei.blog.mapper.UserMapper;
import com.moxiaowei.blog.pojo.Blog;
import com.moxiaowei.blog.pojo.Discuss;
import com.moxiaowei.blog.pojo.User;
import com.moxiaowei.blog.service.UserService;
import com.moxiaowei.blog.util.JedisClientSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private GrowMapper growMapper;

	@Autowired
	private BlogMapper blogMapper;

	@Autowired
	private DiscussMapper discussMapper;

	@Autowired
	private JedisClientSingle jedisClientSingle;

	@Value("${NOTICE_STR}")
	private String NOTICE_STR;

	@Override
	public boolean login(String pwd) {
		// 前台传过来的时候已经md5加密，此处再次加密
		User user = this.userMapper.getUser(DigestUtils.md5DigestAsHex(pwd.getBytes()));
		return user != null;
	}

	@Override
	public boolean addGrow(String content) {
		int insGrow = this.growMapper.insGrow(content);
		return insGrow > 0;
	}

	@Override
	public boolean addBlog(Blog blog) {
		int addBlog = this.blogMapper.addBlog(blog);
		return addBlog > 0;
	}

	@Override
	public boolean delBlog(long id) {
		int delBlog = this.blogMapper.delBlog(id);
		return delBlog > 0;
	}

	@Override
	public boolean updBlog(Blog blog) {
		return this.blogMapper.updataBlog(blog) > 0;
	}

	@Override
	public boolean delDiscuss(long id) {
		return this.discussMapper.delDiscuss2(id) > 0;
	}

	@Override
	public List<Discuss> getDiscuss(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Discuss discuss = new Discuss();
		discuss.setParentid(-1);
		List<Discuss> discussBy = this.discussMapper.getDiscussBy(discuss, true);
		for (Discuss dis : discussBy) {
			long blogid = dis.getBlogid();
			Blog blog = new Blog();
			blog.setId(blogid);
			List<Blog> blogBy2 = this.blogMapper.getBlogBy2(blog);
			if (blogBy2 != null && blogBy2.size() > 0)
				dis.setBlog(blogBy2.get(0));
		}
		return discussBy;
	}

	@Override
	public Date temp(String key, String title, String content, String author) {
		this.jedisClientSingle.hset(key, "title", title);
		this.jedisClientSingle.hset(key, "content", content);
		this.jedisClientSingle.hset(key, "author", author);
		Date date = new Date();
		this.jedisClientSingle.hset(key, "time", String.valueOf(date.getTime()));
		return date;
	}

	@Override
	public Blog getTemp(String key) {
		String title = this.jedisClientSingle.hget(key, "title");
		String content = this.jedisClientSingle.hget(key, "content");
		String author = this.jedisClientSingle.hget(key, "author");
		Blog blog = new Blog();
		blog.setAuthor(author);
		blog.setTitle(title);
		blog.setContent2(content);
		return blog;
	}

	@Override
	public boolean noticeAdd(String notice, int second) {
		String set = this.jedisClientSingle.set(this.NOTICE_STR, notice);
		if (second >= 0)
			this.jedisClientSingle.expire(this.NOTICE_STR, second);
		return set != null;
	}

	@Override
	public boolean noticeSetExpirt(int second) {
		long expire = this.jedisClientSingle.expire(this.NOTICE_STR, second);
		return expire == second;
	}

	@Override
	public boolean noticeDel() {
		return this.jedisClientSingle.del(this.NOTICE_STR) <= 0;
	}

	@Override
	public long expirt() {
		return this.jedisClientSingle.ttl(this.NOTICE_STR);
	}

}
