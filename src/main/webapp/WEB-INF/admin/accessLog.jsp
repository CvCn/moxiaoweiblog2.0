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
<title>访问日志-moxiaowei.com</title>
<style>
#list td {
	width: 200px;
	border: 1px solid white;
	padding: 1%;
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
		<list-mod :info="info" 
			:initpar="{
						url: '/user/getAccessLog', 
						delurl: '/user/delAccessLog',
						pageNum: 1,
						pageSize: 10
					}" 
			id="list" 
			@seturl="setUrl" 
			@getlist="getList" 
			@refresh="refresh">
			<table>
				<tr v-if="list != null">
					<th>编号</th>
					<th>路径</th>
					<th>访问时间</th>
					<th>ip</th>
					<th>地址</th>
				</tr>
				<tr v-for="b in list">
					<td>{{b.id}}</td>
					<td>{{b.path}}</td>
					<td>{{formatData(b.accessdate)}}</td>
					<td>{{b.ip}}</td>
					<td>{{b.address}}</td>
					<td><a class="btn btn-primary" href="javascript: void(0)" @click="del(b.id)">删除</a></td>
				</tr>
			</table>
		</list-mod>
	</div>
		<div style="background-color: #3190C0">&nbsp;</div>
</body>
</html>