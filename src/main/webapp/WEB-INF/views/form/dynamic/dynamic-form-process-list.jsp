<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>流程列表</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="${ctx }/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <%@ include file="/common/include-custom-styles.jsp" %>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/validate/jquery.validate.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/validate/messages_cn.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/common.js" type="text/javascript"></script>
    <script type="text/javascript">
        // 利用动态表单的功能，做一个标示
        var processType = '${empty processType ? param.processType : processType}';
    </script>
	<script src="${ctx }/js/module/form/dynamic/process.js" type="text/javascript"></script>
	<style type="text/css">
	.ui-state-hover{background:#fff;}
	.inputclass{
		width: 100%;
		border: none;
	}
	.layui-table tbody tr:hover{
	background-color:transparent;
	}
	</style>
</head>

<body>
<fieldset  style='margin-top:10px;margin-bottom:10px;' class="layui-elem-field layui-field-title">
  <legend style="font:14px Tahoma, serif;" >流程列表</legend>
</fieldset>
	<c:if test="${not empty message}">
	<script type="text/javascript">
	layermsgsuccess("${message}");
	</script>
	</c:if>
	<table width="100%"  class="layui-table" lay-size="sm" >
		<thead>
			<tr>
				<th>ID</th>
				<!--<th>DID</th>-->
				<th>名称</th>
				<th>KEY</th>
				<th>Version</th>
				<th style='text-align:center;'>图片</th>
				<th style='text-align:center;'>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result }" var="process">
				<tr>
					<td class='process-id'>${process.id }</td>
					<!--<td>${process.deploymentId }</td>-->
					<td class='process-name'>${process.name }</td>
					<td>${process.key }</td>
					<td>${process.version }</td>
					<td style='text-align:center;'><a target="_blank" href='${ctx }/workflow/resource/read?processDefinitionId=${process.id}&resourceType=image'>查看流程</a></td>
					<td style='text-align:center;'><a style='background:#fff;
		padding:0px;                				
		border: 1px solid #c0c0c0;
		-moz-border-radius: 10px; 
		-webkit-border-radius: 10px; 
		font:12px Tahoma, serif;
		border-radius:1px;' class="startup-process" onclick="startupHandle('${process.name }','${process.id }')">启动</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>

</body>
</html>
