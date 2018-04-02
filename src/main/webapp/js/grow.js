$(function() {
	
	Vue.component('unread-mod', {
        props: ['isLogin', 'unread'],
        template: '\
        	<li v-if="isLogin == true">\
        		<a href="/third/massage"><span class="glyphicon glyphicon-envelope"></span><span class="badge">{{unread}}</span></a>\
        	</li>'
    })
	
	Vue.component('login-mod-bf', {
        props: ['isLogin', 'unread', 'user', 'info', 'himg'],
        template: '\
        	<li v-if="isLogin == false">\
        		<a href="javascript: void(0)" data-toggle="modal" data-target="#myModal">\
        			<img alt="头像" :src="himg" height="20" width="20"> {{user}}\
				</a>\
        	</li>'
    })
    
    Vue.component('login-mod-af', {
        props: ['isLogin', 'unread', 'user', 'info', 'himg'],
        template: '\
        	<li v-if="isLogin == true" class="dropdown">\
        		<a class="dropdown-toggle" data-toggle="dropdown" href="javascript: void(0)">\
					<img alt="头像" :src="himg" height="20" width="20"> {{user}}\
				</a>\
				<ul class="dropdown-menu">\
					<li><a :href="\'/third/info?id=\'+info.id">个人信息</a></li>\
					<li><a href="javascript: void(0)" @click="exit()">退出</a></li>\
				</ul>\
        	</li>',
		methods: {
			exit: function(){
				this.$emit('exit');
			}
		}
    })
    
    var vtop = new Vue({
        el: "#top",
        data: {
            user: "登录",
            himg: "/img/user.png",
            id: "",
            timer: 0,
            isLogin: false,
            info: "",
            ph: "400字以内，请您遵守相关法律法规`(*∩_∩*)′",
            unread: 0,
            loginFrameMassage: "登录过的用户请沿用之前的登录方式",
        },
        methods: {
            getUnread: function() {
                this.$http.post("/third/getUnread").then(
                    function(resp) {
                        this.unread = resp.body
                    },
                    function(resp) {
                        console.info(resp)
                    })
            },
            login: function(event) {
            	this.getUser();
                if (this.isLogin) {
                    return ;
                }
                
                var place = $(event.currentTarget).attr("data-style");
                
                var weiboUrl = "https://api.weibo.com/oauth2/authorize?";
                var qqurl = "https://graph.qq.com/oauth2.0/authorize?";
                var code;
                var url;
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                    this.$http
                        .post("/third/id")
                        .then(function(resp) {
                            this.id = resp.bodyText;
                            if(place == "weibo"){
                            	code = "client_id=128422561&redirect_uri=" + escape("http://moxiaowei.com/third/weibo/login") + "&state=" + this.id + ":mobile:" + escape(window.location.href) + "&response_type=code&display=mobile";
                                url = weiboUrl;
                            }else if(place == "qq"){
                            	url = qqurl;
                            	code = "client_id=101460343&redirect_uri=" + escape("http://www.moxiaowei.com/third/qq/login") + "&state=" + this.id + ":mobile:" + escape(window.location.href) + "&response_type=code&display=mobile";
                            }
                            window.location.href = url + code;
                        }, function(resp) {
                            console.info(resp)
                        })
                } else {
                	var weiboDialog = window.open("", "login", "width=800,height=500");
                    this.$http
                        .post("/third/id")
                        .then(function(resp) {
                            this.id = resp.bodyText;
                            if(place == "weibo"){
                            	code = "client_id=128422561&redirect_uri=" + escape("http://moxiaowei.com/third/weibo/login") + "&state=" + this.id + "&response_type=code";
                                url = weiboUrl;
                            }else if(place == "qq"){
                            	url = qqurl;
                            	code = "client_id=101460343&redirect_uri=" + escape("http://www.moxiaowei.com/third/qq/login") + "&state=" + this.id + "&response_type=code";;
                            }
                            weiboDialog.location = url + code;
                            if (this.timer != 0)
                                window.clearTimeout(this.timer);
                            this.timer = window
                                .setInterval(
                                    function() {
                                        if (weiboDialog.closed) {
                                            vtop.getUser();
                                            window.clearTimeout(this.timer);
                                        }
                                    }, 1000);
                            window.setTimeout(function() {
                                window.clearTimeout(this.timer)
                            }, 1000 * 60 * 8);
                        }, function(resp) {
                            console.info(resp)
                        })
                }

            },
            getUser: function() {
                this.getUnread();
                this.$http.post("/third/getUser").then(function(user) {
                    var u = user.body;
                    if (u && u != "") {
                        this.user = u.username;
                        vtop2.user = u.username;
                        this.himg = u.img;
                        this.info = u;
                        window.clearTimeout(this.timer);
                        this.isLogin = true;
                        vtop2.isLogin = true
                        $('#myModal').modal("hide");
                    } else {
                        this.user = "登录";
                        vtop2.user = "登录";
                        this.isLogin = false;
                        vtop2.isLogin = false;
                        this.himg = "/img/user.png"
                    }
                }, function(resp) {
                    console.info(resp)
                })
            },
            exit: function() {
                if (confirm("您确定要退出登录吗？")) {
                    this.$http.post("/third/exit").then(function(b) {
                        if (b) {
                            this.getUser()
                        }
                    }, function(resp) {
                        console.info(reso)
                    })
                }
            }
        },
        created: function() {
            this.getUser()
        },
        computed: {
            _getUser: function() {
                this.getUser()
            }
        }
    });
	//博客内容页评论列表
    var vtop2 = new Vue({
        el: "#top2",
        data: {
            isLogin: false,
            isDiscuss: false,
            info: "",
            user: "",
            color: "#DDEDFE",
            ph: vtop.ph
        },
        methods: {
            login: function() {
                vtop.login()
            },
            tog: function(event) {
                $(event.currentTarget).parent().parent()
                    .children(".panel-body").slideToggle("fast");
            },
            openDis: function(event) {
                var $dis = $(event.currentTarget).parent().parent().parent()
                    .children(".panel-body").children(".form-group")
                    .first();
                $dis.fadeToggle("fast")
            },
            getDis: function(pageNum, pageSize) {
                if (this.info) {
                    if (pageNum > this.info.pages || pageNum <= 0 ||
                        pageNum == this.info.pageNum)
                        return
                }
                var blogid = $("#blogid").val();
                if (blogid == null || blogid == "")
                    return;
                this.$http.post("/discuss/getDiscuss", {
                    blogid: blogid,
                    pageNum: pageNum,
                    pageSize: pageSize
                }, {
                    emulateJSON: true
                }).then(function(list) {
                    this.info = list.body
                }, function(resp) {
                    console.info(resp)
                })
            },
            oneLevelDis: function() {
                var blogid = $("#blogid").val();
                var content = $("#ct").val();
                if (content == null || content == "") {
                    $("#ct").focus();
                    return
                }
                this.$http.post("/discuss/oneLevelDis", {
                    content: content,
                    blogid: blogid
                }, {
                    emulateJSON: true
                }).then(function(resp) {
                    var b = resp.body;
                    if (b) {
                    	window.setTimeout(function(){
                    		 this.getDis();
                    	}, 300);
                        $("#ct").val("");
                    } else {
                        alert("评论失败，您已退出，请您重新登录");
                        vtop.getUser();
                    }
                }, function(resp) {
                    console.info(resp)
                })
            },
            twoLevelDis: function(event) {
                var _this = event.currentTarget;
                var parentid = $(_this).parent().parent().prev().prev().val();
                var blogid = $("#blogid").val();
                var content = $(_this).prev().val();
                if (content == null || content == "") {
                    $(_this).prev().focus();
                    return
                }
                this.$http.post("/discuss/twoLevelDis", {
                    parentid: parentid,
                    blogid: blogid,
                    content: content
                }, {
                    emulateJSON: true
                }).then(function(resp) {
                    var b = resp.body;
                    if (b) {
                    	window.setTimeout(function(){
                   		 	this.getDis();
                    	}, 300);
                        $(_this).prev().val("");
                        $(_this).parent().fadeToggle("fast")
                    } else {
                        alert("评论失败，您已退出登录，请重新登录");
                        vtop.getUser();
                    }
                }, function(resp) {
                    console.info(resp)
                })
            },
            delDis: function(event) {
                if (confirm("确定要删除此评论吗？")) {
                    var id = $(event.currentTarget).parent().parent().prev()
                        .val();
                    this.$http.post("/discuss/delDiscuss", {
                        id: id
                    }, {
                        emulateJSON: true
                    }).then(function(resp) {
                        var b = resp.body;
                        if (b) {
                            this.getDis()
                        } else {
                            alert("删除失败，您已退出登录，请重新登录");
                            vtop.getUser();
                        }
                    }, function(resp) {
                        console.info(resp)
                    })
                }
            },
            refresh: function(pageNum, pageSize) {
                this.info = "";
                this.getDis(pageNum, pageSize)
            },
            pos: function(pageNum, pageSize, flag){
            	if(!flag){
            		var pid = $("#initpos").offset().top-40;
                	var _this = this;
                	$("html,body").animate({scrollTop: pid}, 300, function(){
                		_this.getDis(pageNum, pageSize);
                	})
            	}else{
            		setTimeout(function(){
            			var pid = $("#initpos").offset().top-40;
                    	$("html,body").animate({scrollTop: pid}, 300);
            		}, 500);
            	}
            }
        },
        created: function() {
            this.getDis(1, 10)
        },
    })
    
    Vue.component('notice-mod', {
    	props: ['noticeText'],
    	template: '<div class="alert-danger notice" v-html="noticeText"></div>'
    })

	new Vue({
		el : "#notice",
		data: {
			notice_txt: ''
		},
		created : function(){
			this.$http.post("/notice").then(function(resp) {
					this.notice_txt = resp.bodyText;
			}, function(resp) {
				console.info(resp)
			})
		}
	});
    
    
})
