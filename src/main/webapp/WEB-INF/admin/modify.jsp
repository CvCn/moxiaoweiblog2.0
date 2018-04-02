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

<link rel="stylesheet" href="/css/wangEditor.min.css">
<link rel="shortcut icon" href="/img/M.png" />
<link rel="stylesheet" href="/css/blog.css">
<title>${blog.id }-moxiaowei.com</title>
<style>
body {
	background-color: #F5F5F5;
}

.toolbar {
	border: 1px solid #ccc;
}

.text {
	height: 510px;
	border: 1px solid #3190C0;
}
</style>
</head>

<body>
	<div class="container" id="editor">
		<div style="margin-bottom: 2%;">
			<div class="form-group">
				<h4>
					<p id="blogId">${blog.id }</p>
					标题
					<small>（20字以内）</small>
				</h4>
				<div class="form-group input-group">
					<input type="text" class="form-control" id="title" value="${blog.title }">
					<span class="input-group-btn">
						<button class="btn btn-primary" type="button" id="btn1">提交</button>
					</span>
				</div>
				<P>作者：</P>
				<input type="text" class="form-control" id="author" value="${blog.author }">
			</div>
			<div id="div1" class="toolbar"></div>
			<div style="padding: 5px 0;"></div>
			<div id="div2" class="text">${content }</div>
		</div>
		<div class="alert alert-sm alert-danger"></div>
	</div>
</body>
<!-- 加载编辑器 -->
<script type="text/javascript" src="/js/wangEditor.min.js"></script>
<script src="/js/grow.js"></script>
<script>
	$(function() {
		$(".nav:first> li, .dropdown-menu > li").click(function() {
			if ($(".navbar-toggle").css("display") != "none") {
				$(".navbar-toggle").click();
			}
		});

		var E = window.wangEditor;
		var editor = new E('#div1', '#div2');
		editor.customConfig.uploadImgShowBase64 = true;
		editor.create();
		
		$.post("/user/getTempModify", function(blog) {
			$("#title").val(blog.title);
			$("#author").val(blog.author);
			editor.txt.html(blog.content2);
		});
		
		function formatCol(v) {
			if (v < 10) {
				v = "0" + v
			}
			return v
		}

		function formatData(longTypeDate) {
			var date = new Date();
			date.setTime(longTypeDate);
			return date.getFullYear() + "-"
					+ formatCol(date.getMonth() + 1) + "-"
					+ formatCol(date.getDate()) + " "
					+ formatCol(date.getHours()) + ":"
					+ formatCol(date.getMinutes()) + ":"
					+ formatCol(date.getSeconds())
		}

		var timer = setInterval(function() {
			var title = $("#title").val();
			var __html = editor.txt.html();
			var author = $("#author").val();
			__html = __html == null ? "" : __html;
			title = title == null ? "" : title;
			author = author == null ? "" : author;
			$.post("/user/tempModify", {
				title : title,
				author : author,
				content : __html
			}, function(date) {
				$(".alert").html("自动临时保存：" + formatData(date));
			});
		}, 10 * 1000);

		$("#btn1").click(function() {
			var remark = editor.txt.text();
			remark = remark.substr(0, 200);
			if (remark != "") {
				remark += "...";
			}
			var title = $("#title").val();
			var __html = editor.txt.html();
			var author = $("#author").val();
			var id = $("#blogId").html();
			if (title == "" || __html == "" || author == "" || id == "")
				return;
			$.post("/api/user/updata", {
				title : title,
				content : __html,
				remark : remark,
				author : author,
				id : id
			}, function(b) {
				if (b == true) {
					alert("提交成功");
				} else {
					alert("提交失败");
				}
			});
		})
	});
</script>
</html>