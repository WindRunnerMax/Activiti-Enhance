$(function() {
	$('#portlet-container').portlet({
		sortable : true,
		columns : [{
			width : '100%',
			portlets : [{
				title : '待办任务',
				content : {
					style : {
						maxHeight : 300
					},
					type : 'ajax',
					dataType : 'json',
					url : ctx + '/workflow/task/todo/list',
					formatter : function(o, pio, data) {
						if (data.length == 0) {
							return "无待办任务！";
						}
						var ct = "<ol>";
						$.each(data, function() {
							ct += "<li>"
									+ this.pdname
									+ "->PID:"
									+ this.pid
									+ "-><span class='ui-state-highlight ui-corner-all'>"
									+ this.name + "</span>";
							ct += "<span class='version' title='流程定义版本："
									+ this.pdversion + "'><b>V:</b>"
									+ this.pdversion + "</span>";
							ct += "<a class='trace' style='cursor: pointer;' pid='" + this.pid
									+ "' title='点击查看流程图'>跟踪</a>";
							ct += "<span class='status' title='任务状态'>"
									+ (this.status == 'claim' ? '未签收' : '')
									+ "</span>";
							ct += "</li>";
						});
						return ct + "</ol>";
					},
					afterShow : function() {
						$('.trace').click(graphTrace);
					}
				}
			}]
		}]
	});
});
