<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/global.jsp"%>
    <%@ include file="/common/meta.jsp" %>
    <link rel="stylesheet" href="${ctx }/css/bootstrap2.min.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx }/css/bootstrap-responsive.min.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx }/css/style.css" type="text/css"/>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx }/js/common/bootstrap.min.js"></script>
    <title>用户列表--management</title>
    <script type="text/html" id="oper-col">
        <a class="layui-btn layui-btn-xs" lay-event="viewUser">查看单位用户</a>  
        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="addUser">添加单位用户</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="addNext">添加下级单位</a>
</script>
<script type="text/html" id="userbarDemo">
     	<a class="layui-btn layui-btn-xs" lay-event="taskTurn">添加</a>
</script>
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<style type="text/css">
    body{overflow-y: scroll;}
</style>
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
    <script type="text/javascript">
        function addUser(comID,userID) {

            
            // body...
                $.ajax({                                                //ajax
                    type:"post",
                    url:"/activiti/pressanykeytoac/tree/user/save",
                    data:{comID:comID,userID:userID},                    
                    dataType:"text",                            
                    success:function(data)
                    {
                    	layui.use('layer', function() {
                            var layer = layui.layer;
                            if(data=="1") layer.msg('绑定成功', {icon: 1,time:1000},function(){window.location.reload();});
                            else if(data=="2") layer.msg('不存在该用户', {icon: 7});
                            else layer.msg('绑定失败', {icon: 5});
                        })
                        
                    },
                    error:function()
                    {
                    	layui.use('layer', function() {
                            var layer = layui.layer;
                            layer.msg('绑定失败，网络错误', {icon: 5});
                        })
                        
                    }
             });
        }
        function addCompany(comID,comName) {
            
            // body...
            $.ajax({                                                //ajax
                    type:"post",
                    url:"/activiti/pressanykeytoac/tree/save",
                    data:{pid:comID,name:comName},                    
                    dataType:"text",                            
                    success:function(data)
                    {
                    	layui.use('layer', function() {
                            var layer = layui.layer;
                            if(data=="1") layer.msg('添加成功',{icon: 1,time:1000},function(){window.location.reload();});
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
	<li class="active"><a href="${ctx}/pressanykeytoac/tree/jump/user"><i class="layui-icon" style="color: #000;">&#xe62e;</i>用户树管理</a></li>
    <li><a href="${ctx}/pressanykeytoac/tree/jump/group"><i class="layui-icon" style="color: #000;">&#xe613;</i>组角色管理</a></li>
</ul>
<hr class="layui-bg-gray" style="margin-top: -10px;margin-bottom: 5px;">
<!-- <div style="float: left;width: 65%;">
    <fieldset>
            <legend><small>用户树</small></legend>
    <table id="table1" class="layui-table" lay-filter="table1" style="margin-bottom: -20px;"></table>
    </fieldset>
</div>
<div style="float: right;width: 30%;">
    <fieldset>
        <legend><small>添加单位</small></legend>
         <center>
            <div><div style="display: inline;">单位名称 ：</div><div id="name" style="display: inline;"><input style="margin-top:5px;width: 200px;" type="text" name="name" /></div></div>
            <div><div style="display: inline;">上级单位 ：</div>
            <div style="display: inline;" >
                <select id="selectCompany"  style="margin-top:5px;width: 215px;">
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
        </center>
        <br><hr>
        <legend><small>添加用户</small></legend>
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
        </center>
    </fieldset>
    <hr>
</div>
 -->



    <div>
        <fieldset style="margin:5px 5px 5px 5px;">
            <legend><small>用户树</small></legend>
    <table id="table1" class="layui-table" lay-filter="table1" style="margin-bottom: -20px;"></table>
    </fieldset>
    </div>

 
</body>

<script>
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
                    {templet: '#oper-col',align:'center',width:330,fixed: 'right',title: '操作'}

                ]],
                done: function () {
                    layer.closeAll('loading');
                }
            });
        };

        renderTable();

        //监听工具条
        table.on('tool(table1)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'viewUser') {
                var height=$(window).height()*0.55;
                addPopUp=layer.open({
                    type : 1,
                    title : false,
                    closeBtn : 0,
                    offset : 't',
                    area : '370px',
                    shadeClose : true,
                    content : "<div style='padding:10px;max-height:"+height+"px;overflow-y:scroll;'><div id='choice'  lay-filter='demo'></div></div>"
                });
                table.render({
                elem : '#choice',
                url : '/activiti/pressanykeytoac/tree/user/list?comID='+data.id,
                cols : [[
                        {field:'id', width:'120', title:
                        '用户ID',sort: true}, 
                    {
                    field : 'name',
                    title : '用户名称',
                    width :139,
                    sort : true
                },{
                    align:'center', toolbar: '#barDemo',title:'操作'
                }

                ]]
            });

                 table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                    var deldata = obj.data //获得当前行数据
                    ,layEvent = obj.event; //获得 lay-event 对应的值
                    if(layEvent === 'del'){
                      layer.confirm('确定删除此人在该单位的信息吗？', function(index){
                        obj.del(); //删除对应行（tr）的DOM结构
                        layer.close(index);
                        //向服务端发送删除指令
                        // alert(data.id+" "+deldata.id);
                         $.ajax({                                                //ajax
                                    type:"post",
                                    url:"/activiti/pressanykeytoac/tree/user/delete",
                                    data:{r_id:deldata.r_id},                    
                                    dataType:"text",                            
                                    success:function(data)
                                    {
                                        if(data=="1") layer.msg('删除成功', {icon: 6});
                                        else layer.msg('删除失败', {icon: 5});
                                    },
                                    error:function()
                                    {
                                        layer.msg('删除失败，网络错误', {icon: 5});
                                    }
                            });
                      });
                    }
                  });

            } else if (layEvent === 'addUser') {
                // layer.prompt({title: '请输入用户ID'},function(val, index){
                  // addUser(data.id,val);
				   layer.open({
                          type: 1,
                          title: false,
                          closeBtn: 0,
                           offset:'t',
                            area: '410px',
                          shadeClose: true,
                          content: "<div style='padding:10px;max-height:"+height+"px;overflow-y:auto;'><div id='userchoice'  lay-filter='userchoiceBar'></div></div>"
                        });

                          table.render({
                            //skin: 'line',
                            elem: '#userchoice',
                            url : '/activiti/pressanykeytoac/users/load'
                            ,cols: [[
                              {field:'id', width:'120', title: 'ID',sort: true}
                              ,{field:'name', width:'130', title: '用户名称',sort: true}
                              ,{title: '操作',align:'center' , toolbar: '#userbarDemo',style:'text-align: center;'}
                            ]],
                          });
                          table.on('tool(userchoiceBar)', function(obj){
                                var adddata = obj.data,layEvent = obj.event;
                                if(layEvent === 'taskTurn'){
                                    addUser(data.id,adddata.id);
                                  };
                                });
               
            }else if (layEvent === 'addNext') {
                layer.prompt({title: '请输入下级单位名称'},function(val, index){
                  addCompany(data.id,val);
                });
            }
        });
    });
</script>
</html>