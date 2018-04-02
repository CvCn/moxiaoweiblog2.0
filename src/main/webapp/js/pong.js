$(function() {
	new Vue({
		el : "#edit",
		methods : {
			jump : function(src) {
				window.open(src)
			},
		}
	});
	new Vue({
		el : "#_list",
		data : {
			list : "",
			url : "",
			delurl : "",
			info : "",
			fg : true
		},
		methods : {
			getList : function(page, rows) {
				if (this.info) {
					if (page > this.info.pages || page <= 0)
						return
				}
				page = page == null ? 1 : page;
				rows = rows == null ? 10 : rows;
				this.$http.post(this.url, {
					pageNum : page,
					pageSize : rows
				}, {
					emulateJSON : true
				}).then(function(data) {
					var info = data.body;
					this.list = info.list;
					this.info = info
				}, function(resp) {
					console.log(resp)
				})
			},
			del : function(id) {
				if (confirm("确定删除？")) {
					this.$http.post(this.delurl, {
						id : id
					}, {
						emulateJSON : true
					}).then(function(data) {
						if (data.body) {
							alert("删除成功");
							this.getList()
						} else {
							alert("删除失败")
						}
					}, function(resp) {
						alert(resp)
					})
				}
			},
			formatCol : function(v) {
				if (v < 10) {
					v = "0" + v
				}
				return v
			},
			formatData : function(longTypeDate) {
				var date = new Date();
				date.setTime(longTypeDate);
				return date.getFullYear() + "-"
						+ this.formatCol(date.getMonth() + 1) + "-"
						+ this.formatCol(date.getDate()) + " "
						+ this.formatCol(date.getHours()) + ":"
						+ this.formatCol(date.getMinutes()) + ":"
						+ this.formatCol(date.getSeconds())
			},
			setUrl : function(url, delurl) {
				this.url = url;
				this.delurl = delurl
			},
			st : function(pageNum, pageSize) {
				if (this.fg) {
					this.getList(pageNum, pageSize);
					this.fg = false
				}
			},
			refresh : function(pageNum, pageSize) {
				this.list = "";
				this.getList(pageNum, pageSize)
			},
			updState : function() {
				this.$http.post("/third/updState")
			},
			empty : function() {
				if(confirm("确定要清空消息？")){
					this.$http.post("/third/empty");
					this.getList()
				}
			}
		},
		created : function() {
			this.getList()
		}
	})
});