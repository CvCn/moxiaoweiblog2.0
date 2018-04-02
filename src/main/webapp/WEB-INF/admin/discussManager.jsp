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

<script type="text/javascript" src="/js/admin.js"></script>
<link rel="shortcut icon" href="/img/M.png" />
<link rel="stylesheet" href="/css/blog.css">
<title>评论审核-moxiaowei.com</title>
<style>
#list td {
	width: 200px;
	border: 1px solid white;
	padding: 1%;
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
		<list-mod :info="info" 
			:initpar="{
						url: '/user/getDiscuss', 
						delurl: '/user/delDiscuss',
						pageNum: 1,
						pageSize: 10
					}" 
			id="list" 
			@seturl="setUrl" 
			@getlist="getList" 
			@refresh="refresh">
			<ul class="list-group">
				<li class="list-group-item" v-for="d in list">
					<button class="btn btn-primary" type="button" @click="del(d.id)" style="float: right;">删除</button>
					<h4>{{d.content}}</h4>
					编号：{{d.id}}&nbsp;父类编号：{{d.parentid}}<br>
					<a :href="'/blog/'+d.blogid+'.html'" target="_blank">博客：{{d.blogid}}《{{d.blog != null ? d.blog.title : '博客已删除'}}》</a>
					创建时间：{{formatData(d.createdate)}}&nbsp;更新时间：{{formatData(d.updatedate)}}<br>
					<a :href="'/third/info?id='+d.fuser.uid" target="_blank">用户：{{d.fuser.id}}——{{d.fuser.username}}</a>
				</li>
			</ul>
		</list-mod>
		</div>
		<div style="background-color: #3190C0">&nbsp;</div>
</body>
</html>