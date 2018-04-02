package com.moxiaowei.blog.controller;

import com.moxiaowei.blog.service.NoticeService;
import com.moxiaowei.blog.util.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;

/**
 * 页面跳转相关的
 *
 * @author moxiaowei
 */
@RequestMapping("/api")
@Controller
public class PageController {

//	@Autowired
//	private AccessLogService accessLogServiceImpl;

    @Autowired
    private NoticeService noticeServiceImpl;

//	/**
//	 * 根目录到主页，以为要做静态化处理，所以不能通过ajax加载
//	 * @param model
//	 * @param req
//	 * @return
//	 * @author moxiaowei
//	 */
//	@RequestMapping("/")
//	public String getBlog(Model model, HttpServletRequest req){
//		List<Blog> blog = this.blogServiceImpl.getBlog(1, PageController.pageSize);
//		Blog blogNew = this.blogServiceImpl.getBlogNew();
//		blog.remove(blogNew);
//		model.addAttribute("list", blog);
//		model.addAttribute("blogNew", blogNew);
//		
//		//访问日志
//		ClassLoader classLoader = getClass().getClassLoader();
//		IpUtil.load(classLoader.getResource("17monipdb.dat").getFile());
//		String ip = IpUtil.getIp(req);
//		try {
//			String[] ipAddress = IpUtil.find(ip);
//			AccessLog al = new AccessLog(req.getServletPath(), ip, StringUtils.join(ipAddress, ","));
//			this.accessLogServiceImpl.addAccessLog(al);
//		} catch (StringIndexOutOfBoundsException e) {
//			e.printStackTrace();
//			return "index";
//		}
//		
//		return "index";
//	}

//	@RequestMapping(value="/getBlogListAsyn", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
//	@ResponseBody
//	public List<Blog> getBlogListAsyn(int pageNum){
//		List<Blog> blog = this.blogServiceImpl.getBlog(pageNum, PageController.pageSize);
//		Blog blogNew = this.blogServiceImpl.getBlogNew();
//		blog.remove(blogNew);
//		return blog;
//	}

//	@RequestMapping(value="/getBlogNew", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
//	@ResponseBody
//	public Blog getBlogListAsyn(){
//		return this.blogServiceImpl.getBlogNew();
//	}

    /**
     * 计算当前运营时间
     *
     * @return
     *
     * @author moxiaowei
     */
    @RequestMapping(
            value = "/day",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8",
            method = RequestMethod.GET)
    @ResponseBody
    public RestMessage<Double> getday() {
        Calendar cal = Calendar.getInstance();
        //参数月从0 开始，所以以下时间为2017-12-31 17:00:00
        cal.set(2017, 11, 31, 0, 0, 0);
        Date d = new Date();
        double num = (double) (d.getTime() - cal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        return new RestMessage<>(200, num);
    }

    @RequestMapping(
            value = "/notice",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8",
            method = RequestMethod.GET)
    @ResponseBody
    public RestMessage<String> notice() {
        String notice = this.noticeServiceImpl.getNotice();
        RestMessage<String> rm = new RestMessage<>();
        if (notice != null)
            rm.set200(notice);
        return rm;
    }


    /**
     * 关闭页面，微博登录完成，跳转到此，关闭当前打开窗口
     *
     * @return
     *
     * @author moxiaowei
     */
    @RequestMapping("/close")
    public String close() {
        return "close";
    }

    /**
     * 管理员登录
     *
     * @return
     *
     * @author moxiaowei
     */
    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }

}
