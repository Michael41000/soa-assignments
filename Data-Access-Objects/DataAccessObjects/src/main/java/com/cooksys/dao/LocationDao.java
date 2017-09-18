package com.cooksys.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import com.cooksys.entity.Location;

public class LocationDao {
	
	private final String JDBC_DRIVER = "org.postgresql.Driver";  
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "postgres";
    private final String PASS = "bondstone";
	
	
	
	public Location get(Long id)
	{
		Location location = new Location();
		try {
			ResultSet rs = query("SELECT * FROM \"Location\" WHERE id = " + id);
			
			while (rs.next()) {
			    location.setId(id);
			    location.setCity(rs.getString("city"));
			    location.setState(rs.getString("state"));
			    location.setCountry(rs.getString("country"));
			}
        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}

	public void save(Location location)
	{
		if (location == null)
		{
			return;
		}
		
		if (location.getId() == null)
		{
			try {
				 Connection connection = getConnection();
				 String SQL_INSERT = "insert into \"Location\" (city, state, country) values (?, ?, ?)";
			     PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
					                                  Statement.RETURN_GENERATED_KEYS);
				
			     statement.setString(1, location.getCity());
		         statement.setString(2, location.getState());
		         statement.setString(3, location.getCountry());

				 int affectedRows = statement.executeUpdate();

				 if (affectedRows == 0) {
					 throw new SQLException("Creating user failed, no rows affected.");
				 }

				 try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					 if (generatedKeys.next()) {
						 location.setId(generatedKeys.getLong(1));
					 }
					 else {
						 throw new SQLException("Creating user failed, no ID obtained.");
					 }
				 }
				 
				 connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Long lid = location.getId();
			String city = location.getCity();
			String state = location.getState();
			String country = location.getCountry();
			
			int numUpdates = update("UPDATE \"Location\" " +
					"SET city = '" + city + "', state = '" + state + "', " +
					"country = '" + country + "' " +
					"WHERE id = " + lid);
			
			if (numUpdates == 0)
			{
				throw new NoSuchElementException();
			}
		}
	}
	
	private Connection getConnection()
	{
		Connection conn = null;
		
		//STEP 2: Register JDBC driver
		try {
			Class.forName(JDBC_DRIVER);
			//STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	private ResultSet query(String query)
	{
		
		try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			conn.close();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Integer update(String query)
	{
		try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			Integer rs = stmt.executeUpdate(query);
			conn.close();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
