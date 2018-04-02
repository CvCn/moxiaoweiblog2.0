package com.moxiaowei.blog.controller;

import com.moxiaowei.blog.pojo.FUser;
import com.moxiaowei.blog.service.FUserService;
import com.moxiaowei.blog.util.IDUtils;
import com.moxiaowei.blog.util.JedisClientSingle;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.http.HttpClient;
import com.qq.connect.utils.http.PostParameter;
import com.qq.connect.utils.http.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登录详解：
 * 因为登录页不在当前session下，所以无法通过session保存当前用户状态，
 * 用户点击登录后，会在此cookies下存放一个id，并保存进redis里， 当用户登录完成后，会回传此id，用来判断不同用户，
 * 每次验证用户，从cookies中取出此id验证， 为了安全考虑，并且关闭了js操作cookie的权限，所以用此办法。
 *
 * @author moxiaowei
 */
@Controller
@RequestMapping("/api/third")
public class ThirdPartyController {

    @Autowired
    private FUserService fUserServiceImpl;

    @Autowired
    private JedisClientSingle jedisClientSingle;

    @Value("${MOXIAOWEI_LOGIN_TOKEN}")
    private String MOXIAOWEI_LOGIN_TOKEN;

    @Value("${LOGIN_EXPIRE}")
    private int LOGIN_EXPIRE;


    private static Logger logger;

    static {
        logger = Logger.getLogger(ThirdPartyController.class);
    }


    /**
     * 微博登录回调方法， 微博授权成功后，会回调此连接，并且回传当前用户id
     *
     * @param code
     * @param state
     *
     * @return
     *
     * @author moxiaowei
     */
    @RequestMapping("/weibo/login")
    public String weiboLogin(String code, String state, HttpServletRequest req) {
        // xxxxx:xxxxx:mobile:http://xxxxx
        //是否为移动端
        String url = "";
        if (state.indexOf("mobile") > 0) {
            String[] split = state.split(":");
            if (split.length > 3) {
                state = split[0] + ":" + split[1];
                url = split[3] + ":" + split[4];
            }
        }
        String uid = this.jedisClientSingle.get(state);
        if (uid != null) {
            Oauth oauth = new Oauth();
            try {
                AccessToken token = oauth.getAccessTokenByCode(code);
                if (token != null) {
                    Users um = new Users(token.getAccessToken());
                    User user = um.showUserById(token.getUid());
                    if (user.getDescription() == null)
                        user.setDescription("");

                    FUser fUser = this.fUserServiceImpl.getFUserByUid(token.getUid());
                    FUser fu = new FUser(IDUtils.genItemId(), user.getScreenName(), new Date(), 0L, new Date(),
                            token.getAccessToken(), token.getUid(), user.getAvatarLarge(), user.getDescription(), "weibo");
                    if (fUser == null) {
                        boolean addFUser = this.fUserServiceImpl.addFUser(fu);
                        if (!addFUser)
                            this.fUserServiceImpl.addFUser(fu);
                    } else {
                        FUser f = new FUser();
                        f.setId(fUser.getId());
                        f.setLastlogin(new Date());
                        f.setUid(token.getUid());
                        f.setAccesstoken(token.getAccessToken());
                        f.setImg(user.getAvatarLarge());
                        f.setRemark(user.getDescription());
                        f.setUsername(user.getScreenName());

                        this.fUserServiceImpl.updFUser(f);
                    }
                    this.jedisClientSingle.set(state, token.getUid());
                    this.jedisClientSingle.expire(state, LOGIN_EXPIRE);
                    req.setAttribute("url", url);
                    return "close";
                }
            } catch (WeiboException e) {
                e.printStackTrace();
                return "404";
            }
            return "404";
        }
        return "404";
    }

    /**
     * qq登录回调地址
     *
     * @param code
     * @param state
     * @param req
     *
     * @return
     *
     * @author moxiaowei
     */
    @RequestMapping("/qq/login")
    public String qqLogin(String code, String state, HttpServletRequest req) {
        // xxxxx:xxxxx:mobile:http://xxxxx
        //是否为移动端
        String url = "";
        if (state.indexOf("mobile") > 0) {
            String[] split = state.split(":");
            if (split.length > 3) {
                state = split[0] + ":" + split[1];
                url = split[3] + ":" + split[4];
            }
        }
        String uid = this.jedisClientSingle.get(state);
        if (uid != null) {
            try {
                String accessToken = null, openID = null;

                HttpClient client = new HttpClient();
                Response post = client.post(QQConnectConfig.getValue("accessTokenURL"), new PostParameter[]{new PostParameter("client_id", QQConnectConfig.getValue("app_ID")), new PostParameter("client_secret", QQConnectConfig.getValue("app_KEY")), new PostParameter("grant_type", "authorization_code"), new PostParameter("code", code), new PostParameter("redirect_uri", QQConnectConfig.getValue("redirect_URI"))}, false);
                com.qq.connect.javabeans.AccessToken accessTokenObj = new com.qq.connect.javabeans.AccessToken(post);

                if (accessTokenObj.getAccessToken().equals("")) {
                    // 我们的网站被CSRF攻击了或者用户取消了授权
                    // 做一些数据统计工作
                    logger.error("没有获取到响应参数");
                } else {
                    accessToken = accessTokenObj.getAccessToken();
                    OpenID openIDObj = new OpenID(accessToken);
                    openID = openIDObj.getUserOpenID();

                    FUser fUser = this.fUserServiceImpl.getFUserByUid(openID);
                    UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                    UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();

                    if (userInfoBean.getRet() == 0) {
                        FUser fu = new FUser(IDUtils.genItemId(), userInfoBean.getNickname(), new Date(), 0L,
                                new Date(), accessToken, openID, userInfoBean.getAvatar().getAvatarURL100(),
                                "", "qq");
                        if (fUser == null) {
                            boolean addFUser = this.fUserServiceImpl.addFUser(fu);
                            if (!addFUser)
                                this.fUserServiceImpl.addFUser(fu);
                        } else {
                            FUser f = new FUser();
                            f.setId(fUser.getId());
                            f.setLastlogin(new Date());
                            f.setUid(openID);
                            f.setAccesstoken(accessToken);
                            f.setImg(userInfoBean.getAvatar().getAvatarURL100());
                            f.setRemark("");
                            f.setUsername(userInfoBean.getNickname());

                            this.fUserServiceImpl.updFUser(f);
                        }
                        this.jedisClientSingle.set(state, openID);
                        this.jedisClientSingle.expire(state, LOGIN_EXPIRE);
                        req.setAttribute("url", url);
                        return "close";
                    } else {
                        logger.error("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
                        return "404";
                    }
                }
            } catch (QQConnectException e) {
                e.printStackTrace();
                return "404";
            }
            return "404";
        }
        return "404";
    }


//
//	/**
//	 * 用户详情页
//	 * 
//	 * @param id
//	 * @param req
//	 * @param pageNum
//	 * @param pageSize
//	 * @return
//	 * @author moxiaowei
//	 */
//	@RequestMapping("/info")
//	public String info(String id, HttpServletRequest req,
//			@RequestParam(required = false, defaultValue = "1") int pageNum,
//			@RequestParam(required = false, defaultValue = "10") int pageSize) {
//		FUser fuser = null;
//		try {
//			fuser = this.fUserServiceImpl.getFUserById(Long.parseLong(id));
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			return "404";
//		}
//		if (fuser != null) {
//			req.setAttribute("user", fuser);
//			List<Discuss> discussByOne = this.discussServiceImpl.getDiscussByFUser(fuser.getId(), pageNum, pageSize);
//			PageInfo<Discuss> info = new PageInfo<>(discussByOne);
//			req.setAttribute("info", info);
//			return "information";
//		}
//		return "404";
//	}

//	/**
//	 * 消息
//	 * 
//	 * @param session
//	 * @param pageNum
//	 * @param pageSize
//	 * @return
//	 * @author moxiaowei
//	 */
//	@RequestMapping(value = "/getMassage", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public PageInfo<FMassage> getFMassage(HttpServletRequest req,
//			@RequestParam(defaultValue = "1", required = false) Integer pageNum,
//			@RequestParam(defaultValue = "10", required = false) Integer pageSize) {
//		FUser fUser = this.thirdPartyServiceImpl.getFUser(req);
//		List<FMassage> fMassage = this.fMassageServiceImpl.getFMassage(fUser.getId(), pageNum, pageSize);
//		PageInfo<FMassage> info = new PageInfo<>(fMassage);
//		return info;
//	}

//	/**
//	 * 用来标记已读
//	 * 
//	 * @param session
//	 * @return
//	 * @author moxiaowei
//	 */
//	@RequestMapping(value = "/updState", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public boolean updState(HttpServletRequest req) {
//		FUser fUser = this.thirdPartyServiceImpl.getFUser(req);
//		return this.fMassageServiceImpl.updstate(fUser.getId());
//	}
//
//	/**
//	 * 删除一个消息
//	 * 
//	 * @param id
//	 * @param session
//	 * @return
//	 * @author moxiaowei
//	 */
//	@RequestMapping(value = "/delMassage", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public boolean delFMassage(long id, HttpServletRequest req) {
//		FUser fUser = this.thirdPartyServiceImpl.getFUser(req);
//		return this.fMassageServiceImpl.delFMassage(id, fUser.getId());
//	}
//
//	/**
//	 * 清空所有消息
//	 * 
//	 * @param session
//	 * @return
//	 * @author moxiaowei
//	 */
//	@RequestMapping(value = "/empty", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public boolean empty(HttpServletRequest req) {
//		FUser fUser = this.thirdPartyServiceImpl.getFUser(req);
//		if(fUser != null)
//			return this.fMassageServiceImpl.delEmpty(fUser.getId());
//		return false;
//	}
//
//	@RequestMapping("/massage")
//	public String massage() {
//		return "massage";
//	}
//
//	/**
//	 * 当前用户有多少未读
//	 * 
//	 * @param session
//	 * @return
//	 * @author moxiaowei
//	 */
//	@RequestMapping(value = "/getUnread", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public int getstateIsZero(HttpServletRequest req) {
//		FUser fUser = this.thirdPartyServiceImpl.getFUser(req);
//		if (fUser == null)
//			return 0;
//		return this.fMassageServiceImpl.getStateIsZero(fUser.getId());
//	}

}
