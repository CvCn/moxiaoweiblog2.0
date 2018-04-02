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
<title>编辑成长历程-moxiaowei.com</title>
<style>
body {
	background-color: #F5F5F5;
}
</style>
</head>

<body>
	<div class="container minHeight">
		<!-- top -->
		<!-- inverse -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#example-navbar-collapse">
						<span class="sr-only">切换导航</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand">moxiaowei.com</a>
				</div>
				<div class="collapse navbar-collapse bs-js-navbar-scrollspy" id="example-navbar-collapse">
					<ul class="nav navbar-nav">
						<li>
							<a href="/user/admin">管理员主页</a>
						</li>
					</ul>
					<!-- 搜索框 -->
					<ul class="nav navbar-nav navbar-right ">
						<form class="navbar-form" role="search" action="search.html" method="get">
							<div class="form-group input-group">
								<input type="text" class="form-control" placeholder="Search blog 查找博客">
								<span class="input-group-btn">
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
			<div>
				<div class="form-group input-group">
					<input type="text" class="form-control" id="grow">
					<span class="input-group-btn">
						<button class="btn btn-primary" type="button" @click="smt()">提交</button>
					</span>
				</div>

			</div>
			<ul>
				<li v-for="(value, key) in growList2">{{ value.content}}</li>
			</ul>
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
				item : {
					page : 1,
					rows : 100
				}
			},
			methods : {
				_ajax : function() {
					this.$http.post("/grow/getGrow", {
						page : 1,
						rows : 100
					}, {
						emulateJSON : true
					}).then(function(data) {
						this.growList2 = data.body;
					}, function(response) {
						alert(response);
					})
				},
				smt : function() {
					var tx = $("#grow").val();
					if (tx != null && tx != "") {
						this.$http.post("/user/addGrow", {
							content : tx
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
							alert(response);
						})
					}
				}
			},
			created : function() {
				this._ajax();
			}

		})
	});
</script>

</html>