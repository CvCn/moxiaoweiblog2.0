package com.moxiaowei.blog.controller;

import com.moxiaowei.blog.pojo.Grow;
import com.moxiaowei.blog.service.GrowService;
import com.moxiaowei.blog.util.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api")
public class GrowController {

	@Autowired
	private GrowService growServiceImpl;
	
	@RequestMapping(
			value="/grow/{pageNum}", 
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
			method=RequestMethod.GET)
	@ResponseBody
	public RestMessage<List<Grow>> getGrow(@PathVariable int pageNum){
		List<Grow> grow = this.growServiceImpl.getGrow(pageNum, 5);
		return new RestMessage<>(200, grow);
	}
}
