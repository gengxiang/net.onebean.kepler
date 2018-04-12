package net.onebean.kepler;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class SimpleJDBC {

	public SimpleJDBC(){
	}
	
	public Connection getConnect(){
	     String url = null;     
	     String username = null ;    
	     String password = null ;    
		 Properties props = new Properties();
		
		 try {
			 	String proPath = System.getProperty("user.dir");
			    InputStream in = new FileInputStream(new File(proPath+"/net.onebean.kepler/net.onebean.kepler.web/src/main/resources/META-INF/database.properties"));
		     	 props.load(in);
				Class.forName( props.getProperty("database.driverClassName")) ;  
				url = props.getProperty("database.url");
				username = props.getProperty("database.username");
				password = props.getProperty("database.password");
				Connection con = DriverManager.getConnection(url , username , password );
		    	return con;
			 } catch (Exception e) {
				e.printStackTrace();
			}
	     return null;
	}
	
	public List<Map<String,Object>> getColumns(String tableName){
		String sql = "SHOW COLUMNS FROM "+tableName;
		Connection con = getConnect();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
		try {
			 pstmt = con.prepareStatement(sql);
		     rs = pstmt.executeQuery();
		     List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		     while(rs.next()){
		    	 Map<String,Object> m = new HashMap<String, Object>();
		    	 m.put("name", rs.getString("Field"));
		    	 m.put("type", rs.getString("Type"));
		    	 list.add(m);
		     }  
		     return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll(con,pstmt,rs);
		}
		return null;
	}
	
	public void closeAll(Connection con, PreparedStatement pstmt,ResultSet rs){
		try {
			if(con != null && !con.isClosed()){
				con.close();
				con = null;
			}
			if(pstmt != null && !pstmt.isClosed()){
				pstmt.close();
				pstmt = null;
			}
			if(rs != null && !rs.isClosed()){
				rs.close();
				rs  = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
