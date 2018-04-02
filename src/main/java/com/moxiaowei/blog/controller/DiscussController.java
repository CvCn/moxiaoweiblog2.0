package com.moxiaowei.blog.controller;

import com.github.pagehelper.PageInfo;
import com.moxiaowei.blog.pojo.Discuss;
import com.moxiaowei.blog.pojo.FUser;
import com.moxiaowei.blog.service.DiscussService;
import com.moxiaowei.blog.service.ThirdPartyService;
import com.moxiaowei.blog.util.IDUtils;
import com.moxiaowei.blog.util.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 博客下方评论
 * @author moxiaowei
 *
 */
@Controller
@RequestMapping("/api")
public class DiscussController {
	
	@Autowired
	private DiscussService discussServiceImpl;
	
	@Autowired
	private ThirdPartyService thirdPartyServiceImpl;
	
	
	/**
	 * 获取当前博客的评论
	 * @param blogId 博客id
	 * @param pageNum 页数
	 * @return
	 * @author moxiaowei
	 */
	@RequestMapping(
			value="/discuss/{pageNum}/{blogId}", 
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
			method=RequestMethod.GET)
	@ResponseBody
	public RestMessage<PageInfo<Discuss>> getDiscuss(
			@PathVariable Long blogId,
			@PathVariable int pageNum){ 
		Discuss dis = new Discuss();
		dis.setBlogid(blogId);
		dis.setParentid(0);
		List<Discuss> discuss = this.discussServiceImpl.getDiscuss(dis, pageNum, 10);
		RestMessage<PageInfo<Discuss>> rm = new RestMessage<>();
		if(discuss != null && discuss.size() > 0){
			PageInfo<Discuss> info = new PageInfo<>(discuss);
			rm.set200(info);;
		}
		return rm;
	}
	
	@RequestMapping(
			value="/discussOfUser/{pageNum}/{userId}", 
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
			method=RequestMethod.GET)
	@ResponseBody
	public RestMessage<PageInfo<Discuss>> getDisByUser(
			@PathVariable Long userId,
			@PathVariable int pageNum){
		RestMessage<PageInfo<Discuss>> rm = new RestMessage<>();
		List<Discuss> discussByFUser = this.discussServiceImpl.getDiscussByFUser(userId, pageNum, 10);
		if(discussByFUser != null && discussByFUser.size() > 0){
			PageInfo<Discuss> info = new PageInfo<>(discussByFUser);
			rm.set200(info);
		}
		return rm;
	}

	/**
	 * 一级评论
	 * @author moxiaowei    2018/4/1 23:13
	 *
	 * @param content 评论内容
	 * @param blogId 评论的博客id
	 * @param req req
	 * @return
	 */
	@RequestMapping(
			value="/oneLevelDis", 
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
			method=RequestMethod.POST)
	@ResponseBody
	public RestMessage<Boolean> oneLevelDis(
			@RequestParam(required=true) String content,
			@RequestParam(required=true) long blogId, HttpServletRequest req){
		FUser fUser = this.thirdPartyServiceImpl.getFUser(req);
		RestMessage<Boolean> rm = new RestMessage<>();
		if(fUser == null)
			return rm;
		long fuserId = fUser.getId();
		Discuss discuss = new Discuss();
		discuss.setId(IDUtils.genItemId());
		discuss.setBlogid(blogId);
		discuss.setFuserid(fuserId);
		discuss.setContent(content);
		discuss.setParentid(0);
		boolean addDiscuss = this.discussServiceImpl.addDiscuss(discuss);
		rm.set200(addDiscuss);
		return rm;
	}

	/**
	 * 二级评论
	 * @author moxiaowei    2018/4/1 23:13
	 *
	 * @param content 内容
	 * @param blogId 评论的博客id
	 * @param parentId 父级评论id
	 * @param req req
	 * @return
	 */
	@RequestMapping(
			value="/twoLevelDis", 
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
			method=RequestMethod.POST)
	@ResponseBody
	public RestMessage<Boolean> twoLevelDis(
			@RequestParam(required=true) String content, 
			@RequestParam(required=true) long blogId, 
			@RequestParam(required=true) long parentId, HttpServletRequest req){
		RestMessage<Boolean> rm = new RestMessage<>();
		FUser fUser = this.thirdPartyServiceImpl.getFUser(req);
		if(fUser == null)
			return rm;
		long fuserid = fUser.getId();
		Discuss discuss = new Discuss();
		discuss.setBlogid(blogId);
		discuss.setFuserid(fuserid);
		discuss.setContent(content);
		discuss.setParentid(parentId);
		long childid = IDUtils.genItemId();
		discuss.setId(childid);
		boolean addDiscuss = this.discussServiceImpl.addDiscuss(discuss);
		rm.set200(addDiscuss);
		return rm;
	}
	
	@RequestMapping(
			value="/discuss/{disId}",
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
			method=RequestMethod.DELETE)
	@ResponseBody
	public RestMessage<Boolean> delDis(@PathVariable Long disId, HttpServletRequest req){
		RestMessage<Boolean> rm = new RestMessage<>();
		FUser fUser = this.thirdPartyServiceImpl.getFUser(req);
		Discuss dis = new Discuss();
		dis.setId(disId);
		if(disId == null && fUser == null)
			return rm;
		boolean delDiscuss = this.discussServiceImpl.delDiscuss(disId, fUser.getId());
		rm.set200(delDiscuss);
		return rm;
			
	}
	
}
