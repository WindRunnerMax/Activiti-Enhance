package com.pressanykeytoac.searchActivitiError;
import java.sql.*;
import com.pressanykeytoac.base.connMysql;

public class ConnectDatabase{
	    public static  String  bpmnXml;
	    public static void init(int Id) {
	        Connection conn = null;
	        Statement stmt = null;
	        try{
	            conn = connMysql.connectMsql();
	        
	            // 执行查询
	           // System.out.println(" 实例化Statement对象...");
	            stmt = conn.createStatement();
	            String sql;
	            sql = "select ID_ from ACT_GE_BYTEARRAY where DEPLOYMENT_ID_= "+Id;
	            ResultSet rs1 = stmt.executeQuery(sql);
	            int id= 0;
	            if(rs1.next())
	            	id = rs1.getInt("ID_");
	            sql = "select BYTES_ from ACT_GE_BYTEARRAY where ID_= "+id;//24019 36407 17050 46979 46986 46994 46998 47007
	            ResultSet rs = stmt.executeQuery(sql);
	            
	            // 展开结果集数据库
	            while(rs.next()){
	                // 通过字段检索
	            	Blob bb = rs.getBlob("BYTES_");
	        		byte[] b = bb.getBytes(1, (int)bb.length());	
	        		 bpmnXml = new String(b,"utf-8");
					//将blob类型转化为string类型。
	                //String bpmnXml = rs.getString("BYTES_");
	                // 输出数据               
	                //System.out.print(bpmnXml);
	                //System.out.print("\n");
	            }
	            // 完成后关闭
	            rs.close();
	            stmt.close();
	            conn.close();
	        }catch(SQLException se){
	            // 处理 JDBC 错误
	            se.printStackTrace();
	        }catch(Exception e){
	            // 处理 Class.forName 错误
	            e.printStackTrace();
	        }finally{
	            // 关闭资源
	            try{
	                if(stmt!=null) stmt.close();
	            }catch(SQLException se2){
	            }// 什么都不做
	            try{
	                if(conn!=null) conn.close();
	            }catch(SQLException se){
	                se.printStackTrace();
	            }
	        }
	      
	    }
	}