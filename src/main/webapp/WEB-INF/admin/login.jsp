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
<script src="/js/jquery.md5.js"></script>
<!-- Bootstrap -->
<link href="http://moxiaowei.com/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="http://moxiaowei.com/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<!-- vue -->
<script src="/js/vue.min.js"></script>
<script src="/js/vue-resource-1.3.5.js"></script>
<!-- locahost -->
<link rel="stylesheet" href="/css/blog.css">
<link rel="shortcut icon" href="/img/M.png" />
<title>管理员登录</title>
<script>
	$(function() {
		$("input").focus();
		function smt() {
			var value = $("input").val();
			if (value != null && value != "") {
				$("input").val($.md5(value));
				$("form").attr("action", "/api/user/login");
				$("form").submit();
			}
		}
		$("button").click(function() {	
			smt();
		})
		$("input").keydown(function(event) {
			if (event.keyCode == 13) {
				smt();
			}
		})
	})
</script>
</head>

<body class="container">
	<div style="margin-top: 10%;">&nbsp;</div>
	<div class="alert alert-lg col-sm-offset-3 col-sm-6">
		<form action="javascript: void(0)" method="post">
			<input type="password" class="form-control" name="pwd" placeholder="密码"><br>
			<button type="button" class="form-control btn btn-primary">登录</button><br><br>
			<a class="form-control btn btn-info" href="/">返回主页</a>
		</form>
	</div>
</body>

</html>