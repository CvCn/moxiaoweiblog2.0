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
<link rel="stylesheet" href="/css/blog.css">
<link rel="shortcut icon" href="/img/M.png" />
<title>后台管理-moxiaowei.com</title>
<style>
.card {
	width: 200px;
	height: 150px;
	border-radius: 5px;
	background-color: #4C9050;
	color: white;
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
	text-align: center;
	margin: 2%;
	-o-transition-property: height, width, opacity, background-color, color;
	-o-transition-duration: 0.7s, 0.7s;
	-moz-transition-property: height, width, opacity, background-color, color;
	-moz-transition-duration: 0.7s, 0.7s;
	transition-property: height, width, opacity, background-color, color;
	transition-duration: 0.7s, 0.7s, 0.7s, 0.7s, 0.7s;
}

.card:hover {
	color: black;
	cursor: pointer;
	background-color: #4CAF50;
}

#list td {
	width: 200px;
	border: 1px solid white;
	padding: 1%;
}
</style>
</head>

<body>
	<div class="container">
		<div>
			<div id="edit">
				<label @click="jump('/api/user/editor', true)" class="card">
					<br>
					<h2>写博客</h2>
				</label>
				<label @click="jump('/api/user/growEdit', true)" class="card">
					<br>
					<h2>成长历程</h2>
				</label>
				<label @click="jump('/api/user/discussManager', true)" class="card">
					<br>
					<h2>评论审核</h2>
				</label>
				<label @click="jump('/api/user/accessLog', true)" class="card">
					<br>
					<h2>访问日志</h2>
				</label><label @click="jump('/api/user/notice', true)" class="card">
					<br>
					<h2>页顶通知</h2>
				</label>
				<label @click="jump('/api/user/exit')" class="card">
					<br>
					<h2>退出</h2>
				</label>
			</div>
			<hr>
		</div>
		<list-mod :info="info" 
			:initpar="{
						url: '/api/blogList', 
						delurl: '/api/user/del',
						pageNum: 1,
						pageSize: 5
					}" 
			id="list" 
			@seturl="setUrl" 
			@getlist="getList" 
			@refresh="refresh">
			<table>
				<tr v-if="list != null">
					<th>编号</th>
					<th>标题</th>
					<th>摘要</th>
					<th>创建时间</th>
					<th>更新时间</th>
					<th>作者</th>
					<th>操作</th>
				</tr>
				<tr v-for="(b, index) in list" :key="index">
					<td>
						<a :href="'/blog/' + b.id + '.html'" target="_blank">{{b.id}}</a>
					</td>
					<td>{{b.title}}</td>
					<td :title="b.remark">{{b.remark.substr(0, 50)}}</td>
					<td>{{formatData(b.creatdate)}}</td>
					<td>{{formatData(b.updatadate)}}</td>
					<td>{{b.author}}</td>
					<td>
						<a :href="'/api/user/modify?id=' + b.id" class="btn btn-primary">编辑</a>
						<a class="btn btn-primary" href="javascript: void(0)" @click="del(b.id)">删除</a>
					</td>
				</tr>
			</table>
		</list-mod>
	</div>
	<div style="background-color: #3190C0">&nbsp;</div>
</body>
</html>