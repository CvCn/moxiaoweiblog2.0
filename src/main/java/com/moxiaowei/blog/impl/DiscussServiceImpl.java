package com.moxiaowei.blog.impl;

import com.github.pagehelper.PageHelper;
import com.moxiaowei.blog.mapper.BlogMapper;
import com.moxiaowei.blog.mapper.DiscussMapper;
import com.moxiaowei.blog.pojo.Blog;
import com.moxiaowei.blog.pojo.Discuss;
import com.moxiaowei.blog.service.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiscussServiceImpl implements DiscussService {

	
	@Autowired
	private DiscussMapper discussMapper;
	
	@Autowired
	private BlogMapper blogMapper;
	
	@Override
	public boolean addDiscuss(Discuss discuss) {
		discuss.setCreatedate(new Date());
		discuss.setUpdatedate(new Date());
		int addDiscuss = this.discussMapper.addDiscuss(discuss);
		return addDiscuss > 0;
	}

	@Override
	public boolean delDiscuss(long id, long fuserid) {
		int delDiscussById = this.discussMapper.delDiscuss(id, fuserid);
		this.discussMapper.delDiscussByParentid(id);
		return delDiscussById > 0;
	}

	@Override
	public boolean updDiscuss(Discuss discuss) {
		discuss.setUpdatedate(new Date());
		int updDiscuss = this.discussMapper.updDiscuss(discuss);
		return updDiscuss > 0;
	}

	@Override
	public List<Discuss> getDiscuss(Discuss discuss, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.discussMapper.getDiscussBy(discuss, false);
	}

	@Override
	public List<Discuss> getDiscussByFUser(long fuserid, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Discuss> discussByFUser = this.discussMapper.getDiscussByFUser(fuserid);
		for (Discuss discuss : discussByFUser) {
			Blog blog = new Blog();
			blog.setId(discuss.getBlogid());
			List<Blog> blogBy2 = this.blogMapper.getBlogBy2(blog);
			if(blogBy2 != null && blogBy2.size() > 0)
				discuss.setBlog(blogBy2.get(0));
		}
		return discussByFUser;
	}

}
