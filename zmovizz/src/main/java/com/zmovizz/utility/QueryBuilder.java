
package com.zmovizz.utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zmovizz.exceptions.MovieException;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class QueryBuilder {
	
	private String url = "jdbc:postgresql://localhost:5432/zmovizz";
	private String userName = "postgres";
	private String password = "";
    private String tableName;
    String driverClassName = "org.postgresql.Driver";
    
    private List<Integer> usedColumns;
    private List<Integer> whereConditions;
    private boolean isForLikeOperator = false;
    private boolean isLimit = false;
    private boolean isOffset = false;
    private List<Integer> between;
    private boolean isNeedCount = false;
    
    
    private Connection getConnection() throws SQLException {
    	try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return DriverManager.getConnection(url, userName, password);
	}
    
    public void seturl(String url) {
    	this.url = url;
    }
    public void setUserName(String userName) {
    	this.userName = userName;
    }
    public void setPassword(String password) {
    	this.password = password;
    }

    public QueryBuilder(String tableName) {
        this.tableName = tableName;
        this.usedColumns = new ArrayList<>();
        this.whereConditions = new ArrayList<>();
        this.between = new ArrayList<>();
        
    }

    public QueryBuilder column(int... columns) {
        for (int column : columns) {
        	usedColumns.add(column);
        }
        return this;
    }
 
    public QueryBuilder where(int... conditions) {
    	
    	for(int condition : conditions) {
    		whereConditions.add(condition);
    	}
        
        return this;
    }
 
    public QueryBuilder limit() {
    	this.isLimit = true;
    	return this;
    }
    
    public QueryBuilder offset() {
    	this.isOffset = true;
    	return this;
    }
    
    public QueryBuilder between(int... columns) {
    	for(int column : columns) {
    		between.add(column);
    	}
     	return this;
    }
    
	public QueryBuilder count(int count) {
		this.usedColumns.add(count);
		this.isNeedCount =true;
		return this;
	}

  
    public String buildSelect() throws SQLException {
        StringBuilder query = new StringBuilder("SELECT ");
      
        if (usedColumns.isEmpty()) {
        	
            query.append("*");
        } else {
        	if(isNeedCount) {
        		query.append(" COUNT(").append(getColumnNames(usedColumns).get(0)).append(")");
        		
        	}else {
        	
        	 List<String> selectColumns = getColumnNames(usedColumns);
        	 
            for (int i = 0; i < selectColumns.size(); i++) {
                query.append(selectColumns.get(i));
                if (i < selectColumns.size() - 1) {
                    query.append(", ");
                }
            }
        }
        }
        query.append(" FROM ").append(tableName);
        
        if (!whereConditions.isEmpty()) {
            query.append(" WHERE ");
            for (int i = 0; i < getColumnNames(whereConditions).size(); i++) {
                query.append(getColumnNames(whereConditions).get(i)).append(" = ? ");
                
                if (i < whereConditions.size() - 1) {
                    query.append(" AND ");
                }
            }
        }
        if(!between.isEmpty()) {
        	
        	query.append(" AND ").append(getColumnNames(between).get(0)).append(" BETWEEN ? AND ? ");
        }
        if(isLimit) {
        	query.append(" LIMIT ? ");
        }
        if(isOffset) {
        	query.append(" OFFSET ? ");
        }
        System.out.println(query);

        return query.toString();

    }
    
    public String buildUpdate() throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        List<String> updateColumns = getColumnNames(this.usedColumns);
        
        for (int i = 0; i < updateColumns.size(); i++) {
            query.append(updateColumns.get(i)).append(" = ?");
            if (i < updateColumns.size() - 1) {
                query.append(", ");
            }
        }

        if (!whereConditions.isEmpty()) {
        	
            query.append(" WHERE ");
            for (int i = 0; i < whereConditions.size(); i++) {
                query.append(getColumnNames(whereConditions).get(i)).append(" = ? ");
                if (i < whereConditions.size() - 1) {
                    query.append(" AND ");
                }
            }
        }
        System.out.println(query);
        return query.toString();
    }

    public String buildInsert() throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName);

        List<String> insertColumns = getColumnNames(usedColumns);
      
        if(!insertColumns.isEmpty()) {
        	query.append(" ( ");
        	
        	 for (int i = 0; i < insertColumns.size(); i++) {
                 query.append(insertColumns.get(i));
                 if (i < insertColumns.size() - 1) {
                     query.append(", ");
                 }
             }
        	 query.append(") ");
        }
 
        query.append(" VALUES (");

        for (int i = 0; i < insertColumns.size(); i++) {
            query.append("?");
            if (i < insertColumns.size() - 1) {
                query.append(", ");
            }
        }

        query.append(")");
 
        System.out.println(query);
        return query.toString();
    }
    
    private <V> List<String> getColumnNames(List<V> column) throws SQLException {
    	
    	List<String> columnNames = new ArrayList<String>();
        try(Connection connection = getConnection()) {
        	DatabaseMetaData metaData = connection.getMetaData();
          
        	 try (ResultSet rs = metaData.getColumns("zmovizz", null, tableName, null)) {
        		 
        		 if(column.isEmpty()) {
        			 while(rs.next()) {
        				 columnNames.add(rs.getString("COLUMN_NAME"));
        			 }
        		 }else {
        			 int count =1;
        			 while (rs.next()) {

        				 if(column.contains(count)) {
        					 
        					 columnNames.add(rs.getString("COLUMN_NAME")); 
        				 }
        				 count = count+1;
        			 }
        			 
        		 }
        			
        		return columnNames;
            }
        }
    }
	public  List<Object> executeQuery(Class<?> clas,String query,Object... values) throws SQLException, MovieException  {

	    	try (Connection connection = getConnection()) {
	    		
	    		PreparedStatement statement = connection.prepareStatement(query);
	    		setValues(statement, values);
	    			
	    		ResultSet resultSet = statement.executeQuery() ;
	    		try(resultSet){
	    			return setObject(clas, resultSet);
	    		}
	    	}    
		}
	
	public void execute(String query, Object...values ) throws SQLException {
		
		try (Connection connection = getConnection()) {
    		PreparedStatement statement = connection.prepareStatement(query);
    		
    		setValues(statement, values);
    		
    		statement.execute();
		}
	}
	
	private void setValues(PreparedStatement statement,Object[] values) throws SQLException {
		
		
		
		for(int i=1;i <= values.length;i++) {
		
			statement.setObject(i,values[i-1]);
		}
	}
	
	private List<Object> setObject(Class<?> objClass,ResultSet resultSet) throws MovieException {
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			
			List<String> columns = getColumnNames(usedColumns);
			//get the number of columns from meta data
			
			int columnCount = metaData.getColumnCount();
			
			//get the column names
			if(usedColumns.isEmpty()) {
				for(int i=1;i<=columnCount;i++) {
					columns.add(metaData.getColumnName(i));
				}
				
			//if i have specific column then get that column alone
			}else {
				for(int column : usedColumns) {
					columns.add(metaData.getColumnName(column));
				}
			}
			
			//get the target class using reflection
			Class<?> targetObj = Class.forName(objClass.getName());
			
			List<Object> result = new ArrayList<Object>();
			
		
			
			//create a object for each entry
			while(resultSet.next()) {
				
				Object obj = targetObj.getDeclaredConstructor().newInstance();
				for(int i=0;i<columnCount;i++) {
					
					//get the column type
					
					Class<?> columnType =Class.forName(metaData.getColumnClassName(i+1));
			
					//get setter for that column using reflection
					Method method = targetObj.getDeclaredMethod("set"+getCamelCase(columns.get(i)),columnType);
					
					//call the method to set value
					method.invoke(obj,resultSet.getObject(i+1));
				}	
				
				// add all the object in list
				result.add(obj);
			}
			return result;
			
		}catch(ClassNotFoundException| IllegalAccessException| IllegalArgumentException| InvocationTargetException| NoSuchMethodException| SecurityException| InstantiationException|SQLException e)  {
			e.printStackTrace();
			throw new MovieException(e.getStackTrace().toString());
		}
	}


	private String getCamelCase(String str) {
		
		
		StringBuilder strBuilder =  new StringBuilder(str);
		
		//get the index of _ 
		int index = strBuilder.indexOf("_");
		
		//remove and convert it into camel case
		if(index != -1){
			strBuilder.deleteCharAt(index);
			strBuilder.setCharAt(index, String.valueOf( strBuilder.charAt(index)).toUpperCase().charAt(0));
		}
		return strBuilder.substring(0, 1).toUpperCase() + strBuilder.substring(1);
		
	}
	

	
}