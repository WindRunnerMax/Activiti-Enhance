<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/global.jsp"%>
    <%@ include file="/common/meta.jsp" %>
    <link rel="stylesheet" href="${ctx }/css/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx }/css/bootstrap-responsive.min.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx }/css/style.css" type="text/css"/>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx }/js/common/bootstrap.min.js"></script>
    <title>组列表--management</title>
    <script type="text/javascript">
        
    </script>
    <style>
        .layui-layer-prompt.layui-layer-input
        {
            height: 90px;
            width: 100px;
        }
        /** 文件图标 */
        #table1 + .treeTable .treeTable-icon .layui-icon-file:before {
            content: "\e638";
        }

        /** 文件夹未展开 */
        #table1 + .treeTable .treeTable-icon .layui-icon-layer:before {
            content: "\e638";
        }

        /** 文件夹展开 */
        #table1 + .treeTable .treeTable-icon.open .layui-icon-layer:before {
            content: "\e638";
        }
    </style>
    <script type="text/html" id="oper-col">
        <a class="layui-btn layui-btn-xs" lay-event="viewUser">添加于组</a>  
</script>
<script type="text/html" id="add">
        <a class="layui-btn layui-btn-xs" lay-event="addUser">添加</a>  
</script>
<script type="text/javascript">
    function reset() {
        // body...
        $("#groupName").val("");
        $("#user").val("");
        $("#groupID").val("");
    }
    function addGroup() {
        // body...
        var users= $("#user").val();
        var groupName=$("#groupName").val();
        var groupID=$("#groupID").val();
        $.ajax({                                                //ajax
                type:"post",
                url:"/activiti/pressanykeytoac/tree/group/add",
                data:{users:users,groupName:groupName,groupID:groupID},                    
                dataType:"text",                            
                success:function(data)
                {
                	 layui.use('layer', function() {
                         var layer = layui.layer;
                         if(data=="1") {layer.msg('添加成功', {icon: 6});reset();}
                         else layer.msg('添加失败', {icon: 5});
                     })
                    
                },
                error:function()
                {
                	 layui.use('layer', function() {
                         var layer = layui.layer;
                         layer.msg('添加失败，网络错误', {icon: 5});
                     })
                   
                }
            });
    }
</script>
</head>
<body>
<ul class="nav nav-pills">
   <li ><a href="${ctx}/management/identity/user/list"><i class="icon-user"></i>用户管理</a></li>
    <li><a href="${ctx}/management/identity/group/list"><i class="icon-list"></i>组管理</a></li>
	<li><a href="${ctx}/pressanykeytoac/tree/jump/user"><i class="layui-icon" style="color: #000;">&#xe62e;</i>用户树管理</a></li>
    <li class="active"><a href="${ctx}/pressanykeytoac/tree/jump/group"><i class="layui-icon" style="color: #000;">&#xe613;</i>组角色管理</a></li>
</ul>
<hr class="layui-bg-gray" style="margin-top: -10px;margin-bottom: 5px;">
<div style="float: left;width: 65%;">
    <fieldset>
            <legend><small>用户树</small></legend>
    <table id="table1" class="layui-table" lay-filter="table1" style="margin-bottom: -20px;"></table>
    </fieldset>
</div>
<div style="float: right;width: 30%;">
    <fieldset>
        <legend><small>新建组</small></legend>
         <center>
            <div>
			<div style="display: inline;">组用户 ：</div><div  style="display: inline;"><input id="user" style="margin-top:5px;width: 200px;" type="text" name="users" /></div></div>
            <div><div style="display: inline;">&nbsp;&nbsp;&nbsp;组ID ：</div>
            <div style="display: inline;" >
               <input id="groupID" style="margin-top:5px;width: 200px;" type="text" name="groupID" />
            </div></div>
			<div>
            <div style="display: inline;">组名称 ：</div>
            <div style="display: inline;" >
               <input id="groupName" style="margin-top:5px;width: 200px;" type="text" name="groupName" />
            </div>
			</div>
        <div style="margin-top: 20px;">
           <div onclick="addGroup()" class="layui-btn layui-btn-normal layui-btn-sm"><i class="layui-icon">&#xe605;</i>保存</div> 
            <div  onclick="reset()" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon">&#xe9aa;</i>重置</div>
        </div>
        </center>
        <br/><hr/>
        <!-- <legend><small>添加组用户</small></legend>
        <center>
            <div><div style="display: inline;">用户ID ：</div><div id="id" style="display: inline;"><input style="margin-top:5px;width: 200px;" type="text" name="id" /></div></div>
            <div><div style="display: inline;">所属单位 ：</div>
            <div style="display: inline;" >
                <select id="selectUser"  style="margin-top:5px;width: 200px;">
                    <option value="0"  selected="">选择类型</option>
                    <option value="1">顺序结构</option>
                    <option value="2">排他关系</option>
                    <option value="3">并发关系</option>
                    <option value="4">条件关系</option>
                    <option value="5">结束流程</option>
                </select>
            </div>
        <div style="margin-top: 20px;">
           <div type="submit" onclick="" class="layui-btn layui-btn-normal layui-btn-sm"><i class="layui-icon">&#xe605;</i>保存</div> 
            <div type="reset" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon">&#xe9aa;</i>重置</div>
        </div>
        </center> -->
    </fieldset>
    
</div>




    <!-- <div>
        <fieldset style="margin:5px 5px 5px 5px;">
            <legend><small>用户树</small></legend>
    <table id="table1" class="layui-table" lay-filter="table1" style="margin-bottom: -20px;"></table>
    </fieldset>
    </div> -->

 
</body>
<script type="text/javascript">
    var  ids,table_data;
    var height=$(window).height()*0.55;

    function defineUsers(id)
 {
    for(var i=0;i<ids.length;i++){
        console.log(ids[i]);
        if($("#"+id).val()=="") $("#"+id).val(ids[0]);
        else $("#"+id).val($("#"+id).val()+","+ids[i]);
    }
    layer.close(addPopUp);
 }



     layui.config({
        base: '/activiti/js/module/'
    }).extend({
        treetable: 'treetable-lay/treetable'
    }).use(['layer', 'table', 'treetable'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var layer = layui.layer;
        var treetable = layui.treetable;

        // 渲染表格
        var renderTable = function () {
            layer.load(2);
            treetable.render({
                treeColIndex: 1,
                treeSpid: 0,
                treeIdName: 'id',
                treePidName: 'pid',
                treeDefaultClose: false,
                treeLinkage: false,
                elem: '#table1',
                url: '/activiti/pressanykeytoac/tree/list',
                page: false,
                cols: [[
                    {type: 'numbers',title:"序号",width:60},
                    {field: 'name', title: '单位'},
                    {templet: '#oper-col',align:'center',width:100,fixed: 'right',title: '操作'}

                ]],
                done: function () {
                    layer.closeAll('loading');
                }
            });
        };

        renderTable();

         table.on('tool(table1)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'viewUser') {
               ids = [];
            table_data = [];
            layui.use('layer', function() {
                var layer = layui.layer;
                    addPopUp=layer.open({
                    type : 1,
                    title : false,
                    closeBtn : 0,
                    offset : 't',
                    area : '360px',
                    shadeClose : true,
                    content : "<div style='padding:10px;max-height:"+height+"px;overflow-y:scroll;'><div id='choice'  lay-filter='choiceBar'></div></div><div style='height:60px;'><div style='position : absolute;right :10px;bottom:10px;' class='layui-btn layui-btn-normal' onclick=defineUsers('user')>确定</div></div>"
                });
            })
            layui.use('table', function() {
                        var table = layui.table,element=layui.element;
                        table.render({
                                    // skin: 'line',

                                    elem : '#choice',
                                    url : '/activiti/pressanykeytoac/tree/user/list?comID='+data.id,
                                    cols : [[
                                    {checkbox: true},
                                            {field:'id', width:'120', title:
                                            '用户ID',sort: true},
                                         
                                        {
                                        field : 'name',
                                        title : '用户名称',
                                        sort : true
                                    }
                                    
                                    // , {
                                    //  field:'tool',
                                    //  title : '操作',
                                    //  align : 'center',
                                    //  toolbar : '#barDemo',
                                    //  style : 'text-align: center;'
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
     })
 })
</script>
</html>