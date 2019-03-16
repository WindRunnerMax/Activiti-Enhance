<%@page import="me.kafeitu.demo.activiti.util.PropertyFileUtil"%>
<%@page import="org.springframework.beans.factory.config.PropertiesFactoryBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
PropertyFileUtil.init();
%>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<script>
		var notLogon = ${empty user};
		if (notLogon) {
			location.href = '${ctx}/login?timeout=true';
		}
	</script>
	<%@ include file="/common/meta.jsp" %>
    <title>Activiti-Explore</title>
    <%@ include file="/common/include-base-styles.jsp" %>
	<%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx }/css/menu.css" />
    <%@ include file="/common/include-custom-styles.jsp" %>
	<link href="${ctx }/css/main.css" type="text/css" rel="stylesheet"/>
	<style type="text/css">
	.ui-tabs-panel {height: 100%; width: 100%;}
	.ui-tabs .ui-tabs-nav li a {padding-right: .5em;}
	#tabs li .ui-icon-close { float: left; margin: 0.5em 0.2em 0 0; cursor: pointer; }
	#add_tab { cursor: pointer; }
	 .ui-layout-resizer  {     
     background:     #fff;    
     border:         0px solid #fff;    
     border-width:   0;    
     }
	 .ui-layout-west
	 {
		 padding:1px;  
	 }
	.ui-tabs-active.ui-corner-top,.ui-tabs,.ui-tabs-nav,.ui-layout-pane,li.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default
	 {
		 background:#fff;
		padding:3px;                				
		border: 1px solid #c0c0c0;
		-moz-border-radius: 10px; 
		-webkit-border-radius: 10px; 
		border-radius:1px;
	 }
	 .ui-tabs-nav
	 {
		 border:none;
		 border-bottom: 1px solid #c0c0c0;
	 }
	 .ui-layout-north
	 {
		 border:none;
		 border-bottom: 1px solid #c0c0c0;
	 }
	 .ui-state-default a, .ui-state-default a:link, .ui-state-default a:visited
	 {
		 color:#c0c0c0;
		 font: 14px Tahoma, serif;
	 }
	 .ui-state-active a, .ui-state-active a:link, .ui-state-active a:visited
	 {
		 color:#000;
		 font: 14px Tahoma, serif;
	 }
	 #name{
		height:20px;
		min-width:60px;
		position:absolute;
		top:5px;
		right:91px;
		font-size:15px;
		text-align: center;
		padding:7px;                
		border: 1px solid #c0c0c0;
		-moz-border-radius:0px; 
		-webkit-border-radius: 0px; 
		border-radius:0px;   
	}  
	 #loginOut
	 {
		 height:20px;
		width:60PX;
		position:absolute;
		top:5px;
		right:16px;
		font-size:15px;
		text-align: center;
		padding:7px;                
		border: 1px solid #c0c0c0;
		-moz-border-radius:0px; 
		-webkit-border-radius: 0px; 
		border-radius:0px;
		cursor:pointer;
	 }
	</style>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/themeswitcher/jquery.ui.switcher.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/tools/jquery.cookie.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/layout/jquery.layout.min.js?v=1.3" type="text/javascript"></script>
	<script src='${ctx }/js/common/common.js' type="text/javascript"></script>
    <script src='${ctx }/js/module/main/main-frame.js' type="text/javascript"></script>
	<script type="text/javascript" >
function indexlayermsgsuccess(mes)
{
layui.use('layer', function(){
	  var layer = layui.layer;
	  layer.msg(mes, {icon: 1});
	//layer.msg('TEST', {icon: 2});
	});
	
}
function indexlayermsgwarning(mes)
{
layui.use('layer', function(){
	  var layer = layui.layer;
	  layer.msg(mes, {icon: 7});
	//layer.msg('TEST', {icon: 2});
	});
	
}
</script>
</head>
<body>
<!-- #TopPane -->
<div id="topPane" class="ui-layout-north ui-widget ui-widget-header ui-widget-content">
	<div style="padding-left:5px; font-size: 16px; margin-top: 1px;">
       	<table id="topTable" style="padding: 0px;margin: 0px;margin-top: -5px" width="100%">
       		<tr>
       			<td width="40px">
       				<img src="${ctx }/images/logo.png" height="39" align="top"  style="margin-top:5px" />
       			</td>
       			<td>
       			</td>
       			<td>
       				<div style="float:right; color: #000;font-size: 12px;margin-top: 2px">
		        		<div>
		        			<span id='name' title="${groupNames }">${user.id }</span>
		        		</div>
		        		<div style="text-align: right;">
		       				<a href="#" id="loginOut" style='color:#000;'>注销</a>
		        		</div>
		        	</div>
       			</td>
       		</tr>
       	</table>
       </div>
</div>

<!-- RightPane -->
<div id="centerPane" class="ui-layout-center ui-helper-reset ui-widget-content">
	<div id="tabs">
		<ul><li><a class="tabs-title" href="#tab-index">首页</a><span class='ui-icon ui-icon-close' title='关闭标签页'></span></li></ul>
		<div id="tab-index">
			<iframe id="mainIframe" name="mainIframe" src="welcome" class="module-iframe" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>
		</div>
	</div>
</div>

<div  class="ui-layout-west">
	
</div>

<%@ include file="menu.jsp" %>
<div id="themeswitcherDialog"><div id="themeSwitcher"></div></div>
</body>
</html>
