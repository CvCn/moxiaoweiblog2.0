$(function() {
	
	Vue.component('list-mod', {
		props: ['info', 'initpar'],
		template: '\
			<div>\
				<div v-if="info.total > 0">\
				<ul class="pagination">\
					<li class="page-item" :class="info.hasPreviousPage ? \'\' : \'disabled\'"><a class="page-link" href="javascript: void(0)"\
						@click="getList(1)">首页</a></li>\
					<li class="page-item" :class="info.hasPreviousPage ? \'\' : \'disabled\'"><a class="page-link" href="javascript: void(0)"\
						@click="getList(info.prePage)">上一页</a></li>\
					<li class="page-item" v-for="value in info.navigatepageNums" :class="value == info.pageNum ? \'active\' : \'\'"><a\
						class="page-link" href="javascript: void(0)" @click="getList(value)">{{value < 10 ? \'&nbsp;\'+value+\'&nbsp;\': value}}</a></li>\
					<li class="page-item" :class="info.hasNextPage ? \'\' : \'disabled\'"><a class="page-link" href="javascript: void(0)"\
						@click="getList(info.nextPage)">下一页</a></li>\
					<li class="page-item" :class="info.hasNextPage ? \'\' : \'disabled\'"><a class="page-link" href="javascript: void(0)"\
						@click="getList(info.pages)">尾页</a></li>\
				</ul>\
				<p>\
				当前第{{info.pageNum}}页， 共{{info.pages}}页，总共{{info.total}}条记录\
				<a v-if="info.list.length > 0" class="btn btn-default" href="javascript: void(0)" @click="refresh(1)" style="float: right;">\
					<span class="glyphicon glyphicon-repeat"></span>\
				</a>\
				</p><br>\
			</div>\
			<slot><slot>\
		</div>',
		created: function(){
			this.$emit('seturl', this.initpar.url, this.initpar.delurl, this.initpar.pageNum, this.initpar.pageSize);
		},
		methods: {
			getList: function(start){
				this.$emit('getlist', start, this.initpar.pageSize);
			},
			refresh: function(start){
				this.$emit('refresh', start, this.initpar.pageSize);
			}
		}
	})
	
	new Vue({
		el : "#edit",
		methods : {
			jump : function(src, newDialog) {
				if(newDialog)
					window.open(src);
				else
					location.href=src;
			},
		}
	});
	new Vue({
		el : "#list",
		data : {
			list : "",
			url : "",
			delurl : "",
			info : "",
			fg : true,
			pageSize: 10
		},
		methods : {
			getList : function(page, rows) {
				if (this.info) {
					if (page > this.info.pages || page <= 0)
						return
				}
				page = page == null ? 1 : page;
				rows = rows == null ? 10 : rows;
				this.$http.get('/api/searchBlog'+'/' + page).then(function(data) {
					var info = eval('('+data.bodyText+')').json;
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
							this.getList(1, this.pageSize);
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
			setUrl : function(url, delurl, pageNum, pageSize) {
				this.url = url;
				this.delurl = delurl
				this.pageSize = pageSize;
				if (this.fg) {
					this.getList(pageNum, pageSize);
					this.fg = false
				}
			},
			refresh : function(pageNum, pageSize) {
				this.list = "";
				this.getList(pageNum, pageSize)
			}
		},
		created : function() {
			this.getList()
		}
	})
});