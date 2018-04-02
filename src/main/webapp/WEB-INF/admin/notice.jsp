<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge,chrome=1">
<meta name="renderer" content="webkit">
<!-- jQuery -->
<script src="http://moxiaowei.com/js/jquery-3.2.1.min.js"></script>
<!-- Bootstrap -->
<link href="http://moxiaowei.com/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="http://moxiaowei.com/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<!-- vue -->
<script src="/js/vue.min.js"></script>
<script src="/js/vue-resource-1.3.5.js"></script>

<link rel="shortcut icon" href="/img/M.png" />
<link rel="stylesheet" href="/css/blog.css">
<title>页顶通知-moxiaowei.com</title>
<style>
body {
	background-color: #F5F5F5;
}
</style>
</head>

<body>
	<div class="container">
		<!-- top -->
		<!-- inverse -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#example-navbar-collapse">
						<span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
					</button>
					<a class="navbar-brand">moxiaowei.com</a>
				</div>
				<div class="collapse navbar-collapse bs-js-navbar-scrollspy" id="example-navbar-collapse">
					<ul class="nav navbar-nav">
						<li><a href="/user/admin">管理员主页</a></li>
					</ul>
					<!-- 搜索框 -->
					<ul class="nav navbar-nav navbar-right ">
						<form class="navbar-form" role="search" action="search.html" method="get">
							<div class="form-group input-group">
								<input type="text" class="form-control" placeholder="Search blog 查找博客"> <span class="input-group-btn">
									<button class="btn btn-default" type="submit">Go!</button>
								</span>
							</div>
						</form>
					</ul>
				</div>

			</div>
		</nav>
		<div style="margin-top: 100px; margin-left: -10%"></div>
		<div style="margin-bottom: 2%;" id="list">
			<h2 v-html="growList2" class="alert-danger notice"></h2>
			<p v-html="expirt" v-if="expirt > 0"></p>
			<p v-html="expirtData()" v-if="expirt > 0"></p>
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<div class="col-sm-10">
						<button type="button" class="btn btn-primary" @click="del()">删除</button>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
						<input type="text" class="form-control" id="notice" placeholder="内容">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
						<input type="text" class="form-control" id="second" placeholder="过期时间（ss）">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
						<button type="button" class="btn btn-primary" @click="smt()">提交</button>
					</div>
				</div>
			</form>
			<hr>
		</div>
	</div>
	<div style="background-color: #3190C0">&nbsp;</div>
</body>
<script>
	$(function() {
		$(".nav:first> li, .dropdown-menu > li").click(function() {
			if ($(".navbar-toggle").css("display") != "none") {
				$(".navbar-toggle").click();
			}
		});
		
		new Vue({
			el : "#list",
			data : {
				growList2 : "",
				expirt: -1,
				item : {
					page : 1,
					rows : 100
				}
			},
			methods : {
				_ajax : function() {
					this.$http.get("/api/notice").then(function(data) {
						this.growList2 = eval('(' + data.bodyText + ')').json;
					}, function(response) {
						console.info(response);
					})
					
					this.$http.get("/api/user/expirt").then(function(data) {
						this.expirt = eval('(' + data.bodyText + ')').json;
					}, function(response) {
						console.info(response);
					})
					
				},
				smt : function() {
					var notice = $("#notice").val();
					var second = $("#second").val();
					if(second == null || second == '')
						second = -1;
					if(/\*/.test(second)){
						var num = second.split("*");
						second = 1;
						for(var i=0; i<num.length; i++){
							second *= num[i];
						}
					}
					if (notice && notice != "") {
						this.$http.post("/api/user/noticeAdd", {
							notice : notice,
							second: second
						}, {
							emulateJSON : true
						}).then(function(b) {
							if (b) {
								alert("成功");
								this._ajax();
							} else {
								alert("失败");
							}
						}, function(response) {
							console.info(response);
						})
					}
				},
				del: function(){
					this.$http.post("/api/user/noticeDel").then(function(resp){
						this._ajax();
					}, function(resp){
						console.info(resp);
					});
				},
				formatData : function(longTypeDate) {
					var day, hour, minutes, second;
					day = Math.floor(longTypeDate/1000/60/60/24);
					hour = Math.floor((longTypeDate-day*1000*60*60*24)/1000/60/60);
					minutes = Math.floor((longTypeDate-hour*1000*60*60-day*1000*60*60*24)/1000/60);
					second = Math.floor((longTypeDate-hour*1000*60*60-day*1000*60*60*24-minutes*1000*60)/1000);
					return day + '天' + hour + '小时' + minutes + '分钟' + second + '秒';
				},
				expirtData: function(){
					var expirt = this.expirt;
					if(expirt == -1)
						return -1;
					var a = this.formatData(expirt * 1000);
					return a;
				}
			},
			created : function() {
				this._ajax();
			}

		})
	});
</script>

</html>