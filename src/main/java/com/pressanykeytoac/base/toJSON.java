package com.pressanykeytoac.base;

import java.sql.*;
import org.json.*;

public class toJSON {
	public static JSONObject layui(int count,ResultSet result) throws SQLException {
		 JSONObject jsonobey = null ;
		 ResultSetMetaData metaData = result.getMetaData();
		 int columnCount = metaData.getColumnCount();
			try {
				jsonobey = new JSONObject();
				jsonobey.put("count",count);
				jsonobey.put("code",0);
				
				JSONArray array=new JSONArray();
				while(result.next())
				{
					JSONObject json = new JSONObject();
					for(int i=1;i<=columnCount;++i)
					{
						String columnName = metaData.getColumnLabel(i);
						String value=result.getString(columnName);
						json.put(columnName,value);
					}
					
					array.put(json);
				}
				jsonobey.put("data",array);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			return jsonobey;
	}
}
