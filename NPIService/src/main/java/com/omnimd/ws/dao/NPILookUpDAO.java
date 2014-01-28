package com.omnimd.ws.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.omnimd.ws.server.NPIServer;
import com.omnimd.ws.utility.ConnectionHelper;

/**
 * @author Fenil Shah
 */
public class NPILookUpDAO {
	private static Logger logger = Logger.getLogger(NPILookUpDAO.class);
    public JSONObject findProviderDetails(int npi, int entityTypeCode) throws Exception {
    	String query = "SELECT * from NPIDataTable WHERE NPI = ? and [Entity Type Code] = ?";
			
    	Connection connection = null;
    	
        try {
            connection = ConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, npi);
            preparedStatement.setInt(2, entityTypeCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet!=null && resultSet.next()) {
            	return processRow(resultSet);
            }
        } catch (Exception e) {
            throw e;
            //throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(connection);
		}
        return null;
    }

    
   	protected JSONObject processRow(ResultSet resultSet) throws Exception{
    	try{
    		ResultSetMetaData rsmd = resultSet.getMetaData();
    		String name;
    		Object value;
    		//List<Map<String,String>> resultMapList = new ArrayList<Map<String,String>>();
    		//Map<String,String> resultMap = new HashMap<String, String>();
    		
    		//JSONArray json_arr=new JSONArray();
    		JSONObject json_obj=new JSONObject();
    		
    		for(int i = 1; i <= rsmd.getColumnCount() ; i++){
    			name = rsmd.getColumnName(i);
    			value = resultSet.getString(i);
    			
    			json_obj.put(name, value);
    			//json_arr.put(json_obj);
    		}
    		
    		return json_obj;
    	}catch(Exception e){
    		throw e;
    	}
    }
   	
   
   
}
