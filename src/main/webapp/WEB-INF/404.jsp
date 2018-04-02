<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge,chrome=1">
<meta name="renderer" content="webkit">
<!-- saved from url=(0014) about:internet  -->  
<!-- jQuery -->
<script src="http://moxiaowei.com/StaticResources/js/jquery-3.2.1.min.js"></script>
<!-- Bootstrap -->
<link href="http://moxiaowei.com/StaticResources/css/bootstrap.min.css" rel="stylesheet">
<script src="http://moxiaowei.com/StaticResources/js/bootstrap.min.js"></script>
<!-- vue -->
<script src="http://moxiaowei.com/StaticResources/js/vue.min.js"></script>
<script src="http://moxiaowei.com/StaticResources/js/vue-resource-1.3.5.js"></script>
<link rel="shortcut icon" href="/img/M.png" />
<link rel="stylesheet" href="/css/blog.css">
<script src="/js/bottom.js"></script>
<script src="/js/grow.js"></script>
<script src="/js/site.js"></script>
<title>404</title>
<style>
.con {
	text-align: center;
	margin-top: 2%;
}

.error {
	font-size: 160px;
	font-weight: bold;
}

canvas {
	display: block;
}
</style>
</head>

<body>
<a href="/" class="btn btn-info" style="width: 100%; margin-top: -60px;">返回主页</a>
<script>
	$(function(){
		window.setTimeout(function(){
			$('a').animate({
				marginTop: 0
			}, 200);
		},1000);
	})
</script>
	<canvas id="ca">
		<div class="container minHeight">
			<div class="con">
				<div class="alert alert-warning">
					<h3 class="error">404</h3>
					<hr>
					<h2>页面 不！！&nbsp&nbsp 不！ &nbsp不！ 不见了！！</h2>
				</div>
			</div>
		</div>
	</canvas>
	<bottom-mod :about="about" :center="center" :grow-list="growList" :icp="icp" id="icp"></bottom-mod>
</body>
<script>
	var canvas = document.getElementById('ca');
	var height = canvas.height = window.innerHeight;
	var width = canvas.width = window.innerWidth;
	var ctx = canvas.getContext('2d');

	function random(min, max) {
		return Math.random() * (max - min + 1) + min;
	}

	function range_map(value, in_min, in_max, out_min, out_max) {
		return (value - in_min) * (out_max - out_min) / (in_max - in_min)
				+ out_min;
	}

	var word_arr = [];
	var txt_min_size = 5;
	var txt_max_size = 25;
	var keypress = false;
	var acclerate = 2;
	for (var i = 0; i < 25; i++) {
		word_arr.push({
			x : random(0, width),
			y : random(0, height),
			text : '404',
			size : random(txt_min_size, txt_max_size)
		});

		word_arr.push({
			x : random(0, width),
			y : random(0, height),
			text : '页面没有找到',
			size : random(txt_min_size, txt_max_size)
		});

		word_arr.push({
			x : random(0, width),
			y : random(0, height),
			text : '404',
			size : Math.floor(random(txt_min_size, txt_max_size))
		});
	}

	function render() {
		ctx.fillStyle = "rgba(0,0,0,1)";
		ctx.fillRect(0, 0, width, height);

		ctx.fillStyle = "#fff";
		for (var i = 0; i < word_arr.length; i++) {
			ctx.font = word_arr[i].size + "px sans-serif";
			var w = ctx.measureText(word_arr[i].text);
			ctx.fillText(word_arr[i].text, word_arr[i].x, word_arr[i].y);

			if (keypress) {
				word_arr[i].x += range_map(word_arr[i].size, txt_min_size,
						txt_max_size, 2, 4)
						* acclerate;
			} else {
				word_arr[i].x += range_map(word_arr[i].size, txt_min_size,
						txt_max_size, 2, 3);
			}

			if (word_arr[i].x >= width) {
				word_arr[i].x = -w.width * 2;
				word_arr[i].y = random(0, height);
				word_arr[i].size = Math
						.floor(random(txt_min_size, txt_max_size));

			}
		}

		ctx.fill();

		requestAnimationFrame(render);
	}

	render();

	window.addEventListener('keydown', function() {
		keypress = true;
	}, true);
	window.addEventListener('keyup', function() {
		keypress = false;
	}, true);
</script>
</html>