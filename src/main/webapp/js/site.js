(function() {
	var src = (document.location.protocol == "http:") ? "http://js.passport.qihucdn.com/11.0.1.js?f1cf1c4a944fc5035a92faa182a07d4a"
			: "https://jspassport.ssl.qhimg.com/11.0.1.js?f1cf1c4a944fc5035a92faa182a07d4a";
	document.write('<script src="' + src + '" id="sozz"><\/script>');
})();

(function() {
	var bp = document.createElement('script');
	var curProtocol = window.location.protocol.split(':')[0];
	if (curProtocol === 'https') {
		bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
	} else {
		bp.src = 'http://push.zhanzhang.baidu.com/push.js';
	}
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(bp, s);
})();
