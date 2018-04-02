package com.moxiaowei.blog.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.moxiaowei.blog.pojo.AccessLog;
import com.moxiaowei.blog.pojo.Blog;
import com.moxiaowei.blog.pojo.Discuss;
import com.moxiaowei.blog.service.AccessLogService;
import com.moxiaowei.blog.service.BlogService;
import com.moxiaowei.blog.service.UserService;
import com.moxiaowei.blog.util.IDUtils;
import com.moxiaowei.blog.util.JedisClientSingle;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private BlogService blogServiceImpl;

    @Autowired
    private AccessLogService accessLogServiceImpl;

    @Autowired
    private JedisClientSingle jedisClientSingle;

    @RequestMapping("/login")
    public String login(String pwd, HttpSession session, HttpServletRequest req) {
        if (pwd != null && pwd.equals("")) {
            Boolean login = this.userServiceImpl.login(pwd);
            session.setAttribute("flag", login);
            if (login) {
                return "redirect:/api/user/admin";
            }
        }
        return "admin/login";
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        session.setAttribute("flag", false);
        return "redirect:/";
    }

    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public boolean addBlog(String title, String content, String remark, String author) {
        if (title.equals("") && content.equals("")) {
            boolean addBlog = this.userServiceImpl.addBlog(new Blog(IDUtils.genItemId(), title, content.getBytes(),
                    new Date(), new Date(), 0, remark, author));
            if (addBlog) {
                this.jedisClientSingle.del("tempAdd");
                return true;
            }
        }
        return false;
    }

    /**
     * 临时保存
     *
     * @param title
     * @param content
     * @param author
     *
     * @return
     *
     * @author moxiaowei
     */
    @RequestMapping(value = "/tempAdd", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public Date tempAdd(String title, String content, String author) {
        content = content == null ? "" : content;
        title = title == null ? "" : title;
        author = author == null ? "" : author;
        return this.userServiceImpl.temp("tempAdd", title, content, author);
    }


    @RequestMapping(value = "/getTempAdd", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public Blog getTempAdd() {
        return this.userServiceImpl.getTemp("tempAdd");
    }

    //修改临时保存暂时搁置

//	@RequestMapping(value = "/tempModify", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public Date tempModify(String title, String content, String author) {
//		content = content == null ? "" : content;
//		title = title == null ? "" : title;
//		author = author == null ? "" : author;
//		return this.userServiceImpl.temp("tempModify", title, content, author);
//	}

//	@RequestMapping(value = "/getTempModify", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public Blog getTempModify() {
//		return this.userServiceImpl.getTemp("tempModify");
//	}

    @RequestMapping(value = "/del", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public boolean delBlog(Long id) {
        if (id == null)
            return false;
        return this.userServiceImpl.delBlog(id);
    }

    @RequestMapping(value = "/updata", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public boolean updataBlog(Blog blog) {
        if (blog == null)
            return false;
        blog.setUpdatadate(new Date());
        boolean updBlog = this.userServiceImpl.updBlog(blog);
        if (updBlog)
            this.jedisClientSingle.del("tempModify");
        return updBlog;
    }

    @RequestMapping(value = "/modify", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public String modify(long id, Model model) {
        Blog blogById = this.blogServiceImpl.getBlogById(id);
        model.addAttribute("content", new String(blogById.getContent()));
        blogById.setContent(null);
        model.addAttribute("blog", blogById);
        return "admin/modify";
    }

    @RequestMapping(value = "/addGrow", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public boolean addGrow(String content) {
        return this.userServiceImpl.addGrow(content);
    }

    /**
     * 获取博客列表，用于管理员界面
     *
     * @param page
     * @param rows
     *
     * @return
     *
     * @author moxiaowei
     */
    @RequestMapping(value = "/getBlog", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public PageInfo<Blog> getBlog(@RequestParam(defaultValue = "1", required = false) Integer pageNum,
                                  @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        List<Blog> list = this.blogServiceImpl.getBlog(pageNum, pageSize);
        PageInfo<Blog> info = new PageInfo<>(list);
        return info;
    }

    @RequestMapping(value = "/getDiscuss", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public PageInfo<Discuss> getDiscuss(@RequestParam(defaultValue = "1", required = false) Integer pageNum,
                                        @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        List<Discuss> discuss = this.userServiceImpl.getDiscuss(pageNum, pageSize);
        PageInfo<Discuss> info = new PageInfo<>(discuss);
        return info;
    }

    @RequestMapping(value = "/delDiscuss", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public boolean delDiscuss(long id) {
        return this.userServiceImpl.delDiscuss(id);
    }

    @RequestMapping(value = "/getAccessLog", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public PageInfo<AccessLog> getAccessLog(@RequestParam(defaultValue = "1", required = false) Integer pageNum,
                                            @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        List<AccessLog> accessLog = this.accessLogServiceImpl.getAccessLog(pageNum, pageSize);
        PageInfo<AccessLog> info = new PageInfo<>(accessLog);
        return info;
    }

    @RequestMapping(value = "/delAccessLog", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public boolean delAccessLog(long id) {
        return this.accessLogServiceImpl.delAccessLog(id);
    }

    @RequestMapping(value = "/noticeAdd", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public boolean noticeAdd(String notice, Integer second) {
        return this.userServiceImpl.noticeAdd(notice, second);
    }

    @RequestMapping(value = "/noticeDel", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public boolean noticeDel() {
        return this.userServiceImpl.noticeDel();
    }

    @RequestMapping(value = "/noticeSetExpirt", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public boolean noticeSetExpirt(Integer second) {
        return this.userServiceImpl.noticeSetExpirt(second);
    }

    @RequestMapping(value = "/expirt", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public long expirt() {
        return this.userServiceImpl.expirt();
    }

    @RequestMapping("/{page}")
    public String admin(@PathVariable String page, HttpServletRequest req) {
        return "admin/" + page;
    }
}
