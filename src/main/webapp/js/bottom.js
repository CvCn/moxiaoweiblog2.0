document.write("<script language=javascript src='/js/jquery.toTop.js'></script>");
document.write("<script language=javascript src='http://moxiaowei.com/StaticResources/js/sockjs.min.js'></script>");
$(function() {
	$('.to-top').toTop();
	
	Vue.component('bottom-mod', {
		props: ['about', 'center', 'growList', 'icp'],
		template: '\
			<div>\
				<div style="background-color: #3190C0">&nbsp;</div>\
				<div class="alert-sm" style="background-color: #222222; color: #9D9D9D;">\
					<table class="hidden-xs">\
					<tr>\
						<td class="tb-bottom-td" v-html="about"></td>\
						<td class="tb-bottom-td" v-html="center"></td>\
						<td class="tb-bottom-td">\
							<p v-for="value in growList" :key="value.id" v-html="value.content"></p>\
						</td>\
					</tr>\
				</table>\
					<p style="text-align: center" v-html="icp"></p>\
				</div>\
			</div>'
	})
	var botm = new Vue(
			{
				el : "#icp",
				data : {
					icp : "<a href=\"/\" style=\"color: #9D9D9D\">moxiaowei.com</a> | <a href=\"/user/admin\" style=\"color: #9D9D9D\">后台入口</a> | 网备: 冀ICP备18002459号-1",
					growList : [ {
						content : '成长历程：'
					} ],
					day : "",
					center : "<p>特别感谢：</p>"
							+ "<p>"
							+ "<a href=\"http://glyphicons.com/\" style=\"color: #9D9D9D\" target=\"_black\">GLYPHICONS</a>&nbsp;&nbsp;&nbsp;"
							+ "<a href=\"http://www.bootcss.com/\" style=\"color: #9D9D9D\" target=\"_black\">Bootstrap中文网</a>&nbsp;&nbsp;&nbsp;"
							+ "<a href=\"https://cn.vuejs.org/\" style=\"color: #9D9D9D\" target=\"_black\">Vue.js</a>&nbsp;&nbsp;&nbsp;"
							+ "</p>"
							+ "<p>"
							+ "<a href=\"https://www.aliyun.com\" style=\"color: #9D9D9D\" target=\"_black\">阿里云</a>&nbsp;&nbsp;&nbsp;"
							+ "</p>",
					onlineCount : "",
				},
				computed : {
					about : function() {
						return "<p>关于：</p><p>莫小伟个人博客，专注于web后端开发及前后端新技术发现</p><p>邮箱：moxiaoweiblog@163.com</p>"
								+ this.day
								+ "<p>当前活跃页面："
								+ this.onlineCount
								+ "</p>"
								+ "<p>已开源并托管到GitHub：</p><a href=\"https://github.com/CvCn/moxiaowei-blog\" style=\"color: #9D9D9D\" target=\"_black\">https://github.com/CvCn/moxiaowei-blog</a>";
					}
				},
				created : function() {
					this.$http.get("/grow/getGrow").then(function(data) {
						this.growList.push.apply(this.growList, data.body);
					}, function(resp) {
						console.info(resp);
					});
					this.$http.get("/day").then(function(data) {
						this.day = "<p>运营第" + data.body + "天</p>";
					}, function(resp) {
						console.info(resp);
					})
				}
			});

	// websocket相关操作
	var websocket = null;
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://" + document.location.host
				+ "/webSocketServer");
	} else if ('MozWebSocket' in window) {
		websocket = new MozWebSocket("ws://" + document.location.host
				+ "/webSocketServer");
	} else {
		websocket = new SockJS("http://" + document.location.host
				+ "/sockjs/webSocketServer");
	}
	websocket.onopen = onOpen;
	websocket.onmessage = onMessage;
	websocket.onerror = onError;
	websocket.onclose = onClose;

	function onOpen(event) {
	}

	function onMessage(event) {
		botm.onlineCount = event.data;
	}
	function onError(event) {
		console.info(event.data);
	}
	function onClose() {
	}

	window.close = function() {
		websocket.onclose();
	}
	// #websocket相关操作

})