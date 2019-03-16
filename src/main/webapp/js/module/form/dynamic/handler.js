$(function() {
			$('.handle').click(handle);
		});
		
// var myWindow=window.open('','','width=200,height=100')
// myWindow.document.write("<b>This is <br/>'myWindow'</b>")
// myWindow.focus()

// function popupHandler()
// {
// 	layui.use('layer', function(){
// 		var layer = layui.layer;
// 		 layer.open({
// 		  type: 1,
// 		  title: false,
// 		  closeBtn: 0,
// 		   offset:'auto',
//   			area: '410px',
// 		  shadeClose: true,
// 		  content: "<div id='asd' style='padding:10px;max-height:500px;overflow-y:auto;'>123<div id='handleForm'>456</div></div>"
// 		});
// 	})
// }
var dateList;
var requireList;
var editList;
var uploadList;
var downEditList = new Array();
// console.log($(window).height());
var height=$(window).height()*0.55;
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
				var confirmDonload=layer.confirm('确定下载 ' + title + ' ？', {
					btn : ['确定', '取消']
						// 按钮
					}, function() {
					window.open(url);
					layer.close(confirmDonload);
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
				var onlyConfirmDonload=layer.confirm('确定下载 ' + title + ' ？', {
					btn : ['确定', '取消']
						// 按钮
					}, function() {
					window.open(url);
					layer.close(confirmDonload);
				}, function() {
				});
			});

}
function closeHandle()
{
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.closeAll();
	});
}
function submitHandle()
{

	// $("#dynamic").submit();
	// console.log(requireList);

	var flag=1;
	var n=requireList.length;
	layui.use('layer', function(){
		var layer = layui.layer;
		for(var i=0;i<n;++i)
		{
			var idObj=requireList[i].id;
			var typeObj=requireList[i].type;
			if(typeObj=="voucher") layedit.sync(downEditList[idObj]);
			// console.log(requireList[i]);
			if($("#"+idObj).val()=="")
			{
				if(typeObj=="file")
					{
						layer.tips('必须上传附件', '#upload'+idObj,{tips: 1,tipsMore: true});
						flag=0;
					}
				else if(typeObj=="voucher")
					{
						layer.tips('文本框为必填', '#textarea'+idObj,{tips: 1,tipsMore: true});
						flag=0;
					}
				else 
					{
						layer.tips('此项为必填', '#'+idObj,{tips: 1,tipsMore: true});
						flag=0;
					}
			}
		}
	});
	if(flag==1) $("#dynamic").submit();
}
function loadModel()
{
	layui.use('laydate', function(){
		  var laydate = layui.laydate;
		  var n=dateList.length;
		  for(var i=0;i<n;++i)
		  {
		  	laydate.render({
		    	elem: '#'+dateList[i]
		  	});
		  }
	});
	layui.use('layedit', function() {
				layedit = layui.layedit;
				layedit.set({
					uploadImage : {
						url : '/activiti/pressanykeytoac/upload/img',
						type : 'post'
					}
				});
			var n=editList.length;
			for(var i=0;i<n;++i)
				downEditList[editList[i]]=layedit.build(editList[i]); // 建立编辑器
			});

	layui.use('upload', function() {
		var $ = layui.jquery, upload = layui.upload;
		var n=uploadList.length;
		for(var i=0;i<n;++i)
		{
			var id=uploadList[i];
			upload.render({
					elem : '#upload' + id ,
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
						$('#' + id).val(res.data.src + ","
								+ res.data.title);
						$("#fileName"+id).html(res.data.title);
					}
				});
		}
	})
}
function handle()
{
	dateList=[];
	requireList=[];
	editList=[];
	uploadList=[];
	//$.ajaxSettings.async = false;
	//popupHandler();
	var tkey = $(this).attr('tkey');
	var tname = $(this).attr('tname');
	var taskId = $(this).attr('tid');
	layui.use('layer', function(){
		var layer = layui.layer;
		 layer.open({
		  type: 1,
		  anim: 1,
		  title: "任务办理 "+tname,
		  closeBtn: 0,
		   offset:'t',
  			area: '60%',
		  shadeClose: true,
		  content: "<div style='padding:10px;max-height:"+height+"px;overflow-y:auto;white-space:nowrap;'><div id='handleForm'></div></div><hr class='layui-bg-gray'><div style='height:60px;'><div style='position: absolute;bottom:10px;left:10px;'><span class='layui-badge-dot layui-bg-orange'></span> 为必填项 <br><span class='layui-badge-dot layui-bg-green'></span> 为只读项 <br><span class='layui-badge-dot layui-bg-blue'></span> 不作要求</div><div style='position : absolute;right :90px;bottom:10px;' class='layui-btn layui-btn-normal' onclick='submitHandle()'>确定</div><div style='position : absolute;right :10px;bottom:10px;' class='layui-btn layui-btn-primary' onclick='closeHandle()'>取消</div></div>"
			,success: function(){
    				$("#handleForm").html("<center><i class='layui-icon layui-icon-loading'></i> 表单加载中</center>");
  				}
		});
	})
	var trs = "<form id='dynamic' class='dynamic-form' method='post' action='"+ctx+"/form/dynamic/task/complete/"+taskId+"'><table id='dy-table' lay-skin='line' class='layui-table'>";
	$.getJSON(ctx + '/form/dynamic/get-form/task/' + taskId, function(datas) {
		// console.log(datas); 
		$.each(datas.taskFormData.formProperties, function() {
			// console.log(trs);
			//console.log(this);
			// if(this.required==true) requireList.push(this.id);
			if(this.required==true && this.writable==true)
			{
				var detailObject={type:this.type.name,id:this.id};
				requireList.push(detailObject);
			}
			var className = (this.required === true ? "required" : "");
			this.value = this.value ? this.value : "";
			trs += "<tr>" + createFieldHtml(this, datas, className)+"<td>";
			if(this.writable==false)
				trs += "<span class='layui-badge-dot layui-bg-green'></span>";
			else if (this.required === true) 
				trs += "<span class='layui-badge-dot layui-bg-orange'></span>";
			else
				trs += "<span class='layui-badge-dot layui-bg-blue'></span>";
			trs += "</td></tr>";
		});
		trs+="</table></form>";
		$("#handleForm").html(trs);
		loadModel();
	});
	// console.log(trs);
}

var formFieldCreator = {
	'string' : function(prop, datas, className) {
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><input type='text' style='border:none;' placeholder='请输入' id='" + prop.id + "' name='fp_"
					+ prop.id + "' class='" + className + " inputclass' value='"
					+ prop.value + "' /></td>";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'long' : function(prop, datas, className) {
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><input type='text' style='border:none;' placeholder='请输入' id='" + prop.id + "' name='fp_"
					+ prop.id + "' class='" + className + " inputclass' value='"
					+ prop.value + "' /></td>";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'date' : function(prop, datas, className) {
		// var randomNum=Math.floor(Math.random()*10+1);
		// var tempID=prop.id+randomNum;
		// <input id='"+tempID+"' class='inputclass' oninput=loadContent(\'"+ tempID + "\',\'"+ prop.id + "\')/>
		dateList.push(prop.id);
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><input type='text' id='" + prop.id + "' name='fp_"
					+ prop.id + "' style='border:none;' placeholder='yyyy-mm-dd' class='date " + className + " inputclass' value='"
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
			result += "<td><input type='text' style='border:none;' id='" + prop.id + "' name='fp_"
					+ prop.id + "' class='" + className + " inputclass' value='"
					+ prop.value + "' />";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'voucher' : function(prop, datas, className) {
		var result;
		if(prop.writable==true)
		{
			editList.push(prop.id);
			result = "<td  width='120'>"
				+ prop.name
				+ "：</td><td><div id='textarea"+prop.id+"' ><textarea id='" + prop.id
				+ "' name='fp_" + prop.id + "' display='none' class='" + className + "' >"+prop.value+"</textarea></div></td>";
		}
		else 
		{
			result = "<td  width='120'>"
				+ prop.name
				+ "：</td><td><div max-height:300px;overflow-y:auto;>"+prop.value+"</div></td>";
		}
		
		// buildEdit(prop.id);
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
		var fileName="没有附件";
		if(prop.value!="")
		{
			var uploadarr = prop.value.split(",");
			fileName=uploadarr[1];
		}
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
				? "<div id='upload" + prop.id+ "' class='layui-btn layui-btn-xs layui-btn-normal' )>上传附件</div>"
				: "";
		var inputin = prop.writable === true
				? "<input style='display:none;' type='text'  id='" + prop.id
						+ "' name='fp_" + prop.id + "' value='" + prop.value
						+ "' class='" + className + "' />"
				: "";
		var result = "<td width='120'>" + prop.name + "：</td><td>"
				+ downloadbtn + uploadbtn + inputin
				+ "<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><i class='layui-icon'>&#xe64c;</i> <span id='fileName"+prop.id+"'>"+fileName+"</span></td>";
		if (prop.writable === true) {
			//console.log(prop.id);
			uploadList.push(prop.id);
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