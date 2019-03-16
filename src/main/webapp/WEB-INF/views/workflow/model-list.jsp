<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<script>
		var notLogon = ${empty user};
		if (notLogon) {
			location.href = '${ctx }/login?error=nologon';
		}
	</script>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include-base-styles.jsp" %>
	<%@ include file="/common/include-jquery-ui-theme.jsp" %>
	<%@ include file="/common/include-custom-styles.jsp" %>
	<title>流程列表</title>
	<style  type="text/css">
	#create
	{
		background:#fff;
		padding:3px;                				
		border: 1px solid #c0c0c0;
		-moz-border-radius: 10px; 
		-webkit-border-radius: 10px; 
		border-radius:1px;
		font: 14px Tahoma, serif;
	}
	</style>
	<script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(function() {
    	$('#create').button({
    		icons: {
    			primary: 'ui-icon-plus'
    		}
    	}).click(function() {
    		$('#createModelTemplate').dialog({
    			modal: true,
    			width: 500,
    			buttons: [{
    				text: '创建',
    				click: function() {
    					if (!$('#name').val()) {
    						alert('请填写名称！');
    						$('#name').focus();
    						return;
    					}
                        setTimeout(function() {
                            location.reload();
                        }, 1000);
    					$('#modelForm').submit();
    				}
    			}]
    		});
    	});
    });

		function showSvgTip() {
			alert('点击"编辑"链接,在打开的页面中打开控制台执行\njQuery(".ORYX_Editor *").filter("svg")\n即可看到svg标签的内容.');
		}
		function delo(id) {
			window.location="${ctx }/workflow/model/deploy/"+id+"";
			
		}
		function edit(id) {
			 window.parent.location.href="${ctx}/modeler.html?modelId="+id+"";
		}
    </script>
</head>
<body>


<fieldset  style='margin-top:10px;margin-bottom:6px;' class="layui-elem-field layui-field-title">
  <legend style="font:14px Tahoma, serif;" >模型工作区</legend>
</fieldset>
  <div style="position:absolute;text-align: right;top:35px;left:0px;"><button id="create">创建新模型</button></div>
  <br><br>
	<c:if test="${not empty message}">
	<script type="text/javascript">
	layermsgsuccess("${message}");
	</script>
	</c:if>
	
	<table width="100%" class="layui-table" lay-size="sm"  >
		<thead>
			<tr>
				<th>ID</th>
				<th>KEY</th>
				<th>Name</th>
				<th>Version</th>
				<th>创建时间</th>
				<th>最后更新时间</th>
				<th style='text-align:center;'>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="model">
				<tr>
					<td>${model.id }</td>
					<td>${model.key }</td>
					<td>${model.name}</td>
					<td>${model.version}</td>
					<td>${model.createTime}</td>
					<td>${model.lastUpdateTime}</td>
					<td style='text-align:center;'>
						<a onclick="edit(${model.id})" style='cursor:pointer;' >编辑</a>
						<a onclick="delo(${model.id})" style='cursor:pointer;'>部署</a>
						<a href="${ctx}/workflow/model/export/${model.id}/bpmn" target="_blank">导出</a>
                  <a href="${ctx}/workflow/model/delete/${model.id}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="createModelTemplate" title="创建模型" class="template">
        <form id="modelForm" action="${ctx}/workflow/model/create" target="_blank" method="post">
		<table>
			<tr>
				<td>名称：</td>
				<td>
					<input id="name" name="name" type="text" />
				</td>
			</tr>
			<tr >
				<td >KEY：</td>
				<td>
					<input id="key" name="key" type="text" />
				</td>
			</tr>
			<tr>
				<td>描述：</td>
				<td>
					<textarea id="description" name="description" style="width:300px;height: 50px;"></textarea>
				</td>
			</tr>
		</table>
        </form>
	</div>
	<input id="qw" value="123" type="hidden"></input>
</body>
</html>
