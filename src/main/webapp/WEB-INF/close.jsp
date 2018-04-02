<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="ie=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>跳转中请稍候...</title>
</head>
<body>
<script>
	var url = "${url}";
	if(url && url != ""){
		window.location.href = url;
	}else{
		self.close();
	}
</script>
</body>
</html>