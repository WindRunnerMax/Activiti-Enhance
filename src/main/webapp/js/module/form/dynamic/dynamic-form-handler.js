/**
 * 动态Form办理功能
 */
$(function() {

			$('.handle').click(handle);

		});

/*
 * 处理自定义表单
 */
var edit;
function submitMyForm(id, tempID) {
	var text = layedit.getContent(edit);
	$("#" + id).val(text);
	layer.closeAll();
}
function popupTextarea(id, name) {
	// body...
	var tempID = id + "id";
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			type : 1,
			title : false,
			closeBtn : 0,
			offset : 'auto',
			area : ['600px', '400px'],
			shadeClose : true,
			content : "<textarea  id='"
					+ tempID
					+ "' style='display:none;' ></textarea><div style='position : absolute;right :10px;bottom:15px;' class='layui-btn layui-btn-normal' onclick=submitMyForm(\'"
					+ id + "\',\'" + tempID + "\')>确定</div>"
		});
	})

	// var result = "<td width='120'>" + prop.name + "：</td><td><textarea id='"
	// + prop.id + "' name='fp_" + prop.id + "' class='" + className +
	// "'></textarea></td>";
	layui.use('layedit', function() {
				layedit = layui.layedit;
				layedit.set({
							uploadImage : {
								url : '/activiti/pressanykeytoac/upload/img',
								type : 'post'
							}
						});
				edit = layedit.build(tempID); // 建立编辑器
				layedit.setContent(edit, $("#" + id).val());
			});

}

function downloadFileｍｙ(id, name) {
	var text = $('#' + id).val();
	if (text == "") {
		layui.use('layer', function() {
					var layer = layui.layer;
					layer.msg('没有附件', {
								icon : 5
							});
				});
		return;
	}
	var uploadarr = text.split(",");
	var url = uploadarr[0];
	var title = uploadarr[1];
	layui.use('layer', function() {
				var layer = layui.layer;
				layer.confirm('确定下载 ' + title + ' ？', {
					btn : ['确定', '取消']
						// 按钮
					}, function() {
					window.open(url);
					layer.closeAll();
				}, function() {
				});
			});

}

function downloadFile(text) {
	if (text == "") {
		layui.use('layer', function() {
					var layer = layui.layer;
					layer.msg('没有附件', {
								icon : 5
							});
				});
		return;
	}
	var uploadarr = text.split(",");
	var url = uploadarr[0];
	var title = uploadarr[1];
	layui.use('layer', function() {
				var layer = layui.layer;
				layer.confirm('确定下载 ' + title + ' ？', {
					btn : ['确定', '取消']
						// 按钮
					}, function() {
					window.open(url);
					layer.closeAll();
				}, function() {
				});
			});

}

/**
 * 打开办理对话框
 */
function handle() {
	var $ele = $(this);

	// 当前节点的英文名称
	var tkey = $(this).attr('tkey');

	// 当前节点的中文名称
	var tname = $(this).attr('tname');

	// 任务ID
	var taskId = $(this).attr('tid');

	$('#handleTemplate').html('').dialog({
				modal : true,
				width : 400,
				title : '办理任务[' + tname + ']',
				open : function() {
					readFormFields.call(this, taskId);
				},
				buttons : [{
							text : '提交',
							click : function() {
								$('.dynamic-form').submit();
							}
						}, {
							text : '关闭',
							click : function() {
								$(this).dialog('close');
							}
						}]
			});
}

/**
 * 读取表单字段
 */
function readFormFields(taskId) {
	var dialog = this;

	// 清空对话框内容
	$(dialog)
			.html("<form class='dynamic-form' method='post'><table class='dynamic-form-table'></table></form>");
	var $form = $('.dynamic-form');

	// 设置表单提交id
	$form.attr('action', ctx + '/form/dynamic/task/complete/' + taskId);

	// 添加隐藏域
	if ($('#processType').length == 0) {
		$('<input/>', {
					'id' : 'processType',
					'name' : 'processType',
					'type' : 'hidden'
				}).val(processType).appendTo($form);
	}

	// 读取启动时的表单
	$.getJSON(ctx + '/form/dynamic/get-form/task/' + taskId, function(datas) {
				var trs = "";
				$.each(datas.taskFormData.formProperties, function() {
					// console.log(this.type.name+"."+this.id+"."+this.writable);
					var className = this.required === true ? "required" : "";
					this.value = this.value ? this.value : "";
					trs += "<tr>" + createFieldHtml(this, datas, className)
					if (this.required === true) {
						trs += " <span class='layui-badge-dot layui-bg-blue'></span>";
					}
					trs += "</td></tr>";
						// if(this.type.name==="file" && this.writable===false)
						// $("#testiddownload").hide();
						// if(this.type.name==="file" && this.readable===false)
						// $("#"+this.id+"upload").css("display","none");
				});

				// 添加table内容
				$('.dynamic-form-table').html(trs).find('tr').hover(function() {
							$(this).addClass('ui-state-hover');
						}, function() {
							$(this).removeClass('ui-state-hover');
						});

				// 初始化日期组件
				$form.find('.date').datepicker();

				// 表单验证
				$form.validate($.extend({}, $.common.plugin.validator));
			});
}

/**
 * form对应的string/date/long/enum/boolean类型表单组件生成器 fp_的意思是form paremeter
 */
var formFieldCreator = {
	'string' : function(prop, datas, className) {
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><input type='text' id='" + prop.id + "' name='fp_"
					+ prop.id + "' class='" + className + "' value='"
					+ prop.value + "' />";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'date' : function(prop, datas, className) {
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><input type='text' id='" + prop.id + "' name='fp_"
					+ prop.id + "' class='date " + className + "' value='"
					+ prop.value + "'/>";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'enum' : function(prop, datas, className) {
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><select id='" + prop.id + "' name='fp_" + prop.id
					+ "' class='" + className + "'>";
			$.each(datas[prop.id], function(k, v) {
						result += "<option value='" + k + "'>" + v
								+ "</option>";
					});
			result += "</select>";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'users' : function(prop, datas, className) {
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><input type='text' id='" + prop.id + "' name='fp_"
					+ prop.id + "' class='" + className + "' value='"
					+ prop.value + "' />";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'voucher' : function(prop, datas, className) {
		var result = "<td width='120'>"
				+ prop.name
				+ "：</td><td><div class='layui-btn layui-btn-primary layui-btn-xs' onclick=popupTextarea(\'"
				+ prop.id + "\',\'" + prop.name
				+ "\')>View</div></td><td><input  type='hidden' id='" + prop.id
				+ "' name='fp_" + prop.id + "' value='" + prop.value
				+ "' class='" + className + "' /></td>";
		/*
		 * layui.use('layedit', function(){ layedit = layui.layedit;
		 * layedit.set({ uploadImage:{ url:'/activiti/upload/upload/img',
		 * type:'post' } }); de1=layedit.build(prop.id); //建立编辑器 });
		 */
		return result;
	},
	'file' : function(prop, datas, className) {
		// console.log(prop.writable);
		// var downloadbtn= prop.readable===true ? "<div
		// id='"+prop.id+"download' class='layui-btn layui-btn-xs
		// layui-btn-normal'
		// onclick=downloadFile(\'"+prop.value+"\')>下载附件</div>" : "";
		// var downloadbtn= (prop.readable===true && prop.writable===true) ?
		// "<div id='"+prop.id+"download' class='layui-btn layui-btn-xs
		// layui-btn-normal'
		// onclick=downloadFileｍｙ(\'"+prop.id+"\',\'"+prop.name+"\')>下载附件</div>"
		// : "";
		if (prop.readable === true) {
			if (prop.writable === true)
				downloadbtn = "<div id='"
						+ prop.id
						+ "download' class='layui-btn layui-btn-xs layui-btn-normal' onclick=downloadFileｍｙ(\'"
						+ prop.id + "\',\'" + prop.name + "\')>下载附件</div>";
			else
				downloadbtn = "<div id='"
						+ prop.id
						+ "download' class='layui-btn layui-btn-xs layui-btn-normal' onclick=downloadFile(\'"
						+ prop.value + "\')>下载附件</div>";
		} else
			downloadbtn = "";
		var uploadbtn = prop.writable === true
				? "<div id='"
						+ prop.id
						+ "upload' class='layui-btn layui-btn-xs layui-btn-normal' )>上传附件</div>"
				: "";
		var inputin = prop.writable === true
				? "<input style='display:none;' type='text'  id='" + prop.id
						+ "' name='fp_" + prop.id + "' value='" + prop.value
						+ "' class='" + className + "' />"
				: "";
		var result = "<td width='120'>" + prop.name + "：</td><td>"
				+ downloadbtn + "" + uploadbtn + "</td><td>" + inputin
				+ "</td>";
		if (prop.writable === true) {
			layui.use('upload', function() {
						var $ = layui.jquery, upload = layui.upload;

						upload.render({
									elem : '#' + prop.id + 'upload',
									url : '/activiti/pressanykeytoac/upload/img',
									accept : 'file' // 普通文件
									,
									done : function(res) {
										layui.use('layer', function() {
													var layer = layui.layer;
													layer.msg('上传成功', {
																icon : 6
															});
												});
										$('#' + prop.id).val(res.data.src + ","
												+ res.data.title);
									}
								});
					})
		}

		return result;
	}
};

/**
 * 生成一个field的html代码
 */
function createFieldHtml(prop, className) {
	return formFieldCreator[prop.type.name](prop, className);
}