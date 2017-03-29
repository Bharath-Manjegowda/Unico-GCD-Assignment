package org.bharath.unico.gcdassignment.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bharath.unico.gcdassignment.model.InputNumbers;

/**
 * Created by Bharath on 29-03-2017.
 */

public class DatabaseUtil {

	public static Connection getConnection() throws Exception{
		try
		{
			String connectionURL = "jdbc:oracle:thin:@localhost:1521:xe";
			Connection connection = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(connectionURL, "bharath", "bharath");
			return connection;
		} 
		catch (Exception e){
			throw e;
		} 
	}

}
