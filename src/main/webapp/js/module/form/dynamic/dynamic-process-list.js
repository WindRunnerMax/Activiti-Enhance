/**
 * 动态表单Javascript，负责读取表单元素、启动流程
 */
$(function() {
			$('.startup-process').button({
						icons : {
							primary : 'ui-icon-play'
						}
					}).click(showStartupProcessDialog);
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

function downloadFile(id, name) {
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

var count = 0;
 var ids =new Array();
 var table_data=new Array();
 function defineUsers(id)
 {
 	if(ids.length==0) $("#"+id).val("");
 	for(var i=0;i<ids.length;i++){
 		//console.log(ids[i]);
 		if(i==0) $("#"+id).val(ids[0]);
 		else $("#"+id).val($("#"+id).val()+","+ids[i]);
 	}
 	layer.closeAll();
 }
function addUsers(id) {
	ids = [];
	table_data = [];
	$("#"+id).blur();
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			type : 1,
			title : false,
			closeBtn : 0,
			offset : 't',
			area : '223px',
			shadeClose : true,
			content : "<div style='padding:10px;max-height:500px;overflow-y:scroll;'><div id='choice'  lay-filter='choiceBar'></div></div><div style='height:60px;'><div style='position : absolute;right :10px;bottom:10px;' class='layui-btn layui-btn-normal' onclick=defineUsers(\'"+id+"\')>确定</div></div>"
		});
	})
	layui.use('table', function() {
				var table = layui.table,element=layui.element;
				table.render({
							// skin: 'line',

							elem : '#choice',
							url : '/activiti/pressanykeytoac/users/load',
							cols : [[
									// {field:'id', width:'120', title:
									// '用户ID',sort: true},
								 
								{
								field : 'name',
								width:'135',
								title : '用户名称',
								sort : true
							},{checkbox: true}
							
							// , {
							// 	field:'tool',
							// 	title : '操作',
							// 	align : 'center',
							// 	toolbar : '#barDemo',
							// 	style : 'text-align: center;'
							// }
							]]
							,
							done:function(res){
							table_data=res.data;
							
							} 
						});
				  table.on('checkbox(choiceBar)', function (obj) {
           if(obj.checked==true){
               if(obj.type=='one'){
                   ids.push(obj.data.id);
              }else{
                   for(var i=0;i<table_data.length;i++){
                       ids.push(table_data[i].id);
                   }
               }
           }else{
               if(obj.type=='one'){
                   for(var i=0;i<ids.length;i++){
                      if(ids[i]==obj.data.id){
                           ids.splice(i,1);
                       }
                  }
               }else{
                   for(var i=0;i<ids.length;i++){
                       for(var j=0;j<table_data.length;j++){
                           if(ids[i]==table_data[j].id){
                              ids.splice(i,1);
                          }
                       }
                   }
               }
           }
        });
			});
}

/**
 * 打开启动流程
 */

function showStartupProcessDialog() {
	var $ele = $(this);
	$('<div/>', {
		'class' : 'dynamic-form-dialog',
		title : '启动流程[' + $ele.parents('tr').find('.process-name').text() + ']',
		html : '<span class="ui-loading">正在读取表单……</span>'
	}).dialog({
		modal : true,
		width : 400,
		height : $.common.window.getClientHeight() / 2,
		open : function() {
			// 获取json格式的表单数据，就是流程定义中的所有field
			readFormFields.call(this, $ele.parents('tr').find('.process-id')
							.text());
		},
		buttons : [{
					text : '启动流程',
					click : sendStartupRequest
				}]
	});
}

/**
 * 读取表单字段
 */

function readFormFields(processDefinitionId) {
	var dialog = this;
	alert(processDefinitionId);
	// 清空对话框内容
	$('.dynamic-form-dialog')
			.html("<form class='dynamic-form' method='post'><table class='dynamic-form-table'></table></form>");
	var $form = $('.dynamic-form');

	// 设置表单提交id
	$form.attr('action', ctx + '/form/dynamic/start-process/'
					+ processDefinitionId);
	alert($form.attr("action"));
	// 添加隐藏域
	if ($('#processType').length == 0) {
		$('<input/>', {
					'id' : 'processType',
					'name' : 'processType',
					'type' : 'hidden'
				}).val(processType).appendTo($form);
	}

	// 读取启动时的表单
	$.getJSON(ctx + '/form/dynamic/get-form/start/' + processDefinitionId,
			function(data) {
				var trs = "";
				$.each(data.form.formProperties, function() {
					var className = this.required === true ? "required" : "";
					trs += "<tr>" + createFieldHtml(data, this, className)
					if (this.required === true) {
						trs += " <span class='layui-badge-dot layui-bg-blue'></span>";
					}
					trs += "</td></tr>";
				});

				// 添加table内容
				$('.dynamic-form-table').html(trs).find('tr').hover(function() {
							$(this).addClass('ui-state-hover');
						}, function() {
							$(this).removeClass('ui-state-hover');
						});

				// 初始化日期组件
				$form.find('.dateISO').datepicker();

				// 表单验证
				$form.validate($.extend({}, $.common.plugin.validator));
			});
}

/**
 * form对应的string/date/long/enum/boolean类型表单组件生成器 fp_的意思是form paremeter
 */
var formFieldCreator = {
	string : function(formData, prop, className) {
		var result = "<td width='120'>" + prop.name
				+ "：</td><td><input type='text' id='" + prop.id + "' name='fp_"
				+ prop.id + "' class='" + className + "' />";
		return result;
	},
	date : function(formData, prop, className) {
		var result = "<td>" + prop.name + "：</td><td><input type='text' id='"
				+ prop.id + "' name='fp_" + prop.id + "' class='dateISO "
				+ className + "' />";
		return result;
	},
	'enum' : function(formData, prop, className) {
		console.log(prop);
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><select id='" + prop.id + "' name='fp_" + prop.id
					+ "' class='" + className + "'>";
			// result += "<option>" + datas + "</option>";

			$.each(formData['enum_' + prop.id], function(k, v) {
						result += "<option value='" + k + "'>" + v
								+ "</option>";
					});

			result += "</select>";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'users' : function(formData, prop, className) {
		var result = "<td width='120'>" + prop.name
				+ "：</td><td><input type='text' onfocus=addUsers(\'" + prop.id
				+ "\')  id='" + prop.id + "' name='fp_" + prop.id + "' class='"
				+ className + "' />";
		return result;
	},
	'voucher' : function(formData, prop, className) {
		var result = "<td width='120'>"
				+ prop.name
				+ "：</td><td><div class='layui-btn layui-btn-primary layui-btn-xs' onclick=popupTextarea(\'"
				+ prop.id
				+ "\',\'"
				+ prop.name
				+ "\')>View</div></td><td><input  type='text' style='display:none;' id='"
				+ prop.id + "' name='fp_" + prop.id + "' class='" + className
				+ "' />";
		/*
		 * var result = "<td width='120'>" + prop.name + "：</td><td><textarea
		 * id='" + prop.id + "' name='fp_" + prop.id + "' class='" + className +
		 * "'></textarea></td>"; layui.use('layedit', function(){ layedit =
		 * layui.layedit; layedit.set({ uploadImage:{
		 * url:'/activiti/upload/upload/img', type:'post' } });
		 * de1=layedit.build(prop.id); //建立编辑器 });
		 */
		return result;
	},
	'file' : function(formData, prop, className) {
		var result = "<td width='120'>"
				+ prop.name
				+ "：</td><td><div class='layui-btn layui-btn-xs layui-btn-normal' onclick=downloadFile(\'"
				+ prop.id
				+ "\',\'"
				+ prop.name
				+ "\')>下载附件</div><div id='"
				+ prop.id
				+ "upload' class='layui-btn layui-btn-xs layui-btn-normal' )>上传附件</div></td><td><input style='display:none;' type='text'  id='"
				+ prop.id + "' name='fp_" + prop.id + "' class='" + className
				+ "' />";
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
		return result;
	}
};

/**
 * 生成一个field的html代码
 */

function createFieldHtml(formData, prop, className) {
	return formFieldCreator[prop.type.name](formData, prop, className);
}

/**
 * 发送启动流程请求
 */

function sendStartupRequest() {
	if ($(".dynamic-form").valid()) {
		$('.dynamic-form').submit();
	}
}