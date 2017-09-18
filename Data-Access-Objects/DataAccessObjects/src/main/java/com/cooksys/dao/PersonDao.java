package com.cooksys.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import com.cooksys.entity.Interest;
import com.cooksys.entity.Location;
import com.cooksys.entity.Person;

public class PersonDao {
	
	private final String JDBC_DRIVER = "org.postgresql.Driver";  
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "postgres";
    private final String PASS = "bondstone";
    
	
	
	public Person get(Long id)
	{
		Person person = new Person();
		try {
			ResultSet rs = query("SELECT DISTINCT ON(pid) " +
					"\"Person\".id as pid, \"firstName\", \"lastName\", age, " +
					"\"Location\".id as lid, city, state, country " + 
					"from \"Person\" " +
					"INNER JOIN \"Location\" " +
					"ON \"Person\".location_id = \"Location\".id " +
					"WHERE \"Person\".id = " + id);
			
			while (rs.next()) {
			    person.setId(id);
			    person.setFirstName(rs.getString("firstName"));
			    person.setLastName(rs.getString("lastName"));
			    person.setAge(rs.getInt("age"));
			    Location location = new Location();
			    location.setId(rs.getLong("lid"));
			    location.setCity(rs.getString("city"));
			    location.setState(rs.getString("state"));
			    location.setCountry(rs.getString("country"));
			    person.setLocation(location);
			}
			
	        ResultSet interestsSet = query("SELECT \"Interest\".id as iid, title " +
	    	        "from \"Person\" " +
	    	        "INNER JOIN \"PersonInterest\" " + 
	    	        "ON \"Person\".id = \"PersonInterest\".person_id " +
	    	        "INNER JOIN \"Interest\" " + 
	    	        "ON \"PersonInterest\".interest_id = \"Interest\".id " +
	    	        "WHERE \"Person\".id = " + id);
	        Set<Interest> interests = new HashSet<Interest>();
	        while (interestsSet.next())
	        {
	        	Interest interest = new Interest();
	        	interest.setId(interestsSet.getLong("iid"));
	        	interest.setTitle(interestsSet.getString("title"));
	        	interests.add(interest);
	        }
	        person.setInterests(interests);
        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return person;
	}
	
	public void save(Person person)
	{
		if (person == null)
		{
			return;
		}
		
		LocationDao locationDao = new LocationDao();
		locationDao.save(person.getLocation());

		InterestDao interestDao = new InterestDao();
		Set<Interest> interests = person.getInterests();
		for (Interest interest : interests) {
			interestDao.save(interest);
		}
		
		if (person.getId() == null) 
		{
			try {
				Connection connection = getConnection();
				String SQL_INSERT = "insert into \"Person\" (\"firstName\", \"lastName\", age, location_id) values (?, ?, ?, ?)";
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

				statement.setString(1, person.getFirstName());
				statement.setString(2, person.getLastName());
				statement.setLong(3, person.getAge());
				statement.setLong(4, person.getLocation().getId());
				// ...

				int affectedRows = statement.executeUpdate();

				if (affectedRows == 0) {
					throw new SQLException("Creating user failed, no rows affected.");
				}

				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						person.setId(generatedKeys.getLong(1));
					} else {
						throw new SQLException("Creating user failed, no ID obtained.");
					}
				}
				
				for (Interest interest : interests)
				{
					savePersonInterests(person.getId(), interest.getId());
				}
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		else 
		{

			
			
			Long pid = person.getId();
			String firstName = person.getFirstName();
			String lastName = person.getLastName();
			Integer age = person.getAge();
			Long locationId = person.getLocation().getId();

			int numUpdates = update("UPDATE \"Person\" " + 
					"SET \"firstName\" = '" + firstName + "', \"lastName\" = '" + lastName + "', " + 
					"age = " + age + ", location_id = " + locationId + " " + 
					"WHERE id = " + pid);

			if (numUpdates == 0) {
				throw new NoSuchElementException();
			}
			
			update("DELETE FROM \"PersonInterest\" " +
					"WHERE person_id = " + person.getId());
			for (Interest interest : interests) {
				savePersonInterests(person.getId(), interest.getId());
			}
		}
	}
	
	public Set<Person> findInterestGroup(Interest interest, Location location)
	{
		
		Set<Person> people = new HashSet<Person>();
		try {
			Long locationId = location.getId();
			Long interestId = interest.getId();
			
			ResultSet rs = query("SELECT " +
					"\"Person\".id as pid, \"firstName\", \"lastName\", age, " +
					"\"Location\".id as lid, city, state, country " +  
					"from \"Person\" " +
					"INNER JOIN \"PersonInterest\" " +
					"ON \"Person\".id = \"PersonInterest\".person_id " +
					"INNER JOIN \"Interest\" " +
					"ON \"PersonInterest\".interest_id = \"Interest\".id " +
					"INNER JOIN \"Location\" " +
					"ON \"Person\".location_id = \"Location\".id " +
					"where \"Interest\".id = " + interestId + " AND \"Location\".id = " + locationId);
			
			
			while (rs.next())
			{
				Person person = new Person();
				person.setId(rs.getLong("pid"));
			    person.setFirstName(rs.getString("firstName"));
			    person.setLastName(rs.getString("lastName"));
			    person.setAge(rs.getInt("age"));
			    Location loc = new Location();
			    loc.setId(rs.getLong("lid"));
			    loc.setCity(rs.getString("city"));
			    loc.setState(rs.getString("state"));
			    loc.setCountry(rs.getString("country"));
			    person.setLocation(loc);
			    
			    ResultSet interestsSet = query("SELECT \"Interest\".id as iid, title " +
		    	        "from \"Person\" " +
		    	        "INNER JOIN \"PersonInterest\" " + 
		    	        "ON \"Person\".id = \"PersonInterest\".person_id " +
		    	        "INNER JOIN \"Interest\" " + 
		    	        "ON \"PersonInterest\".interest_id = \"Interest\".id " +
		    	        "WHERE \"Person\".id = " + rs.getLong("pid"));
			    Set<Interest> interests = new HashSet<Interest>();
		        while (interestsSet.next())
		        {
		        	Interest ist = new Interest();
		        	ist.setId(interestsSet.getLong("iid"));
		        	ist.setTitle(interestsSet.getString("title"));
		        	interests.add(ist);
		        }
		        person.setInterests(interests);
			    
				people.add(person);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return people;
	}
	
	private void savePersonInterests(Long pid, Long iid)
	{
		
		try {
			
			
			Connection connection = getConnection();
			String SQL_INSERT = "insert into \"PersonInterest\" (person_id, interest_id) values (?, ?)";
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT);

			statement.setLong(1, pid);
			statement.setLong(2, iid);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
