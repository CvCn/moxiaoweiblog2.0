package com.moxiaowei.blog.controller;

import com.moxiaowei.blog.pojo.FUser;
import com.moxiaowei.blog.service.FUserService;
import com.moxiaowei.blog.service.ThirdPartyService;
import com.moxiaowei.blog.util.CookieUtils;
import com.moxiaowei.blog.util.IDUtils;
import com.moxiaowei.blog.util.JedisClientSingle;
import com.moxiaowei.blog.util.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import weibo4j.model.WeiboException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/api")
@Controller
public class FUserController {

    @Autowired
    private FUserService fUserServiceImpl;

    @Autowired
    private JedisClientSingle jedisClientSingle;

    @Autowired
    private ThirdPartyService thirdPartyServiceImpl;

    @Value("${MOXIAOWEI_LOGIN_TOKEN}")
    private String MOXIAOWEI_LOGIN_TOKEN;

    @Value("${LOGIN_EXPIRE}")
    private int LOGIN_EXPIRE;

    /**
     * 生产随机id，于当前cookies一对一
     *
     * @param req
     * @param resp
     *
     * @return
     *
     * @throws WeiboException
     * @throws IOException
     * @author moxiaowei
     */
    @RequestMapping(
            value = "/id",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8",
            method = RequestMethod.GET)
    @ResponseBody
    public RestMessage<String> randomId(HttpServletRequest req, HttpServletResponse resp) {
        RestMessage<String> rm = new RestMessage<>();
        String id = CookieUtils.getCookieValue(req, MOXIAOWEI_LOGIN_TOKEN);
        if (id != null && id.equals("")) {
            String uid = this.jedisClientSingle.get(id);
            if (uid != null) {
                rm.set200(id);
                return rm;
            }
        }
        String currid = UUID.randomUUID().toString().replace("-", "").toUpperCase() + ":" + IDUtils.genItemId();
        this.jedisClientSingle.set(currid, "0");
        this.jedisClientSingle.expire(currid, LOGIN_EXPIRE);
        CookieUtils.setCookie(req, resp, MOXIAOWEI_LOGIN_TOKEN, currid);
        rm.set200(currid);
        return rm;
    }

    /**
     * 获取用户登录状态
     *
     * @param req
     *
     * @return
     *
     * @author moxiaowei    2018/4/1 12:53
     */
    @RequestMapping(
            value = "/fuser",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8",
            method = RequestMethod.GET)
    @ResponseBody
    public RestMessage<FUser> getUser(HttpServletRequest req) {
        return this.getUser(null, req);
    }

    @RequestMapping(
            value = "/fuser/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8",
            method = RequestMethod.GET)
    @ResponseBody
    public RestMessage<FUser> getUser(@PathVariable() Long id, HttpServletRequest req) {
        RestMessage<FUser> rm = new RestMessage<>();
        if (id != null) {
            FUser fUserById = this.fUserServiceImpl.getFUserById(id);
            if (fUserById != null)
                rm.set200(fUserById);
            return rm;
        }
        FUser fUser = this.thirdPartyServiceImpl.getFUser(req);
        if (fUser != null) {
            rm.set200(fUser);
            return rm;
        }
        return rm;
    }

    /**
     * @param req
     * @param resp
     *
     * @return
     *
     * @author moxiaowei    2018/4/1 12:54
     */
    @RequestMapping(
            value = "/exit",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8",
            method = RequestMethod.GET)
    @ResponseBody
    public RestMessage<Boolean> exit(HttpServletRequest req, HttpServletResponse resp) {
        String id = CookieUtils.getCookieValue(req, MOXIAOWEI_LOGIN_TOKEN);
        RestMessage<Boolean> rm = new RestMessage<>();
        if (id != null) {
            CookieUtils.deleteCookie(req, resp, MOXIAOWEI_LOGIN_TOKEN);
            long del = this.jedisClientSingle.del(id);
            rm.set200(del == 1);
        }
        return rm;
    }


}
