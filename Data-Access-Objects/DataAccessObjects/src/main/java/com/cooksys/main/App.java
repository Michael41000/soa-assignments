package com.cooksys.main;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.Test;

import com.cooksys.dao.InterestDao;
import com.cooksys.dao.LocationDao;
import com.cooksys.dao.PersonDao;
import com.cooksys.entity.Interest;
import com.cooksys.entity.Location;
import com.cooksys.entity.Person;


public class App 
{
    
	static final String JDBC_DRIVER = "org.postgresql.Driver";  
	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
 
	static final String USER = "postgres";
	static final String PASS = "bondstone";
    
	private static void deleteAll()
	{
		
		try {
			Class.forName(JDBC_DRIVER);
			// STEP 3: Open a connection
			Connection conn;
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM \"PersonInterest\"; "
					+ "DELETE FROM \"Person\"; " + "DELETE FROM \"Interest\"; " + "DELETE FROM \"Location\"; ");
			pstmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static void main( String[] args ) throws Exception
    {
    	
    	
    }
    
    
    @Test
    // A new Person object can be stored
    public void test1()
    {	
    	Location location = new Location();
    	location.setCity("Memphis");
    	location.setState("Tennessee");
    	location.setCountry("USA");
    	
    	Set<Interest> interests = new HashSet<Interest>();
    	Interest interest1 = new Interest();
    	interest1.setTitle("Reading");
    	Interest interest2 = new Interest();
    	interest2.setTitle("Video Games");		
    	new InterestDao().save(interest1);
    	new InterestDao().save(interest2);
    	interests.add(interest1);
    	interests.add(interest2);
    	
    	PersonDao personDao = new PersonDao();
    	
    	Person toSave = new Person();
    	toSave.setFirstName("Michael");
    	toSave.setLastName("Rollberg");
    	toSave.setAge(22);
    	toSave.setLocation(location);
    	toSave.setInterests(interests);
    	personDao.save(toSave);
    	
    	Person gotten = personDao.get(toSave.getId());
    	
    	assertTrue(comparePerson(toSave, gotten));
    }
    
    @Test
    // A Person object can be retrieved
    public void test2()
    {
    	//Works but no code yet
    	Location location = new Location();
    	location.setCity("Memphis");
    	location.setState("Tennessee");
    	location.setCountry("USA");
    	
    	Set<Interest> interests = new HashSet<Interest>();
    	Interest interest1 = new Interest();
    	interest1.setTitle("Reading");
    	Interest interest2 = new Interest();
    	interest2.setTitle("Video Games");		
    	new InterestDao().save(interest1);
    	new InterestDao().save(interest2);
    	interests.add(interest1);
    	interests.add(interest2);
    	
    	PersonDao personDao = new PersonDao();
    	
    	Person toSave = new Person();
    	toSave.setFirstName("Michael");
    	toSave.setLastName("Rollberg");
    	toSave.setAge(22);
    	toSave.setLocation(location);
    	toSave.setInterests(interests);
    	personDao.save(toSave);
    	
    	Person gotten = personDao.get(toSave.getId());
    	assertTrue(comparePerson(toSave, gotten));
    }
    
    @Test
    // An existing Person can be updated
    public void test3()
    {
    	// Works but no code
    	Location location = new Location();
    	location.setCity("Memphis");
    	location.setState("Tennessee");
    	location.setCountry("USA");
    	
    	Set<Interest> interests = new HashSet<Interest>();
    	Interest interest1 = new Interest();
    	interest1.setTitle("Reading");
    	Interest interest2 = new Interest();
    	interest2.setTitle("Video Games");		
    	new InterestDao().save(interest1);
    	new InterestDao().save(interest2);
    	interests.add(interest1);
    	interests.add(interest2);
    	
    	PersonDao personDao = new PersonDao();
    	
    	Person toSave = new Person();
    	toSave.setFirstName("Michael");
    	toSave.setLastName("Rollberg");
    	toSave.setAge(22);
    	toSave.setLocation(location);
    	toSave.setInterests(interests);
    	personDao.save(toSave);
    	
    	Person gotten = personDao.get(toSave.getId());
    	assertTrue(comparePerson(toSave, gotten));
    	
    	Location location2 = new Location();
    	location2.setCity("Nashville");
    	location2.setState("Tennessee");
    	location2.setCountry("USA");
    	
    	Interest interest3 = new Interest();
    	interest3.setTitle("Programming");	
    	new InterestDao().save(interest3);
    	interests.remove(interest1);
    	interests.add(interest3);
    	
    	toSave.setFirstName("Jake");
    	toSave.setLastName("Long");
    	toSave.setAge(50);
    	toSave.setLocation(location2);
    	toSave.setInterests(interests);
    	personDao.save(toSave);
    	
    	Person gotten2 = personDao.get(toSave.getId());
    	
    	assertTrue(!comparePerson(toSave, gotten));
    	assertTrue(comparePerson(toSave, gotten2));
    	
    	try {
    		toSave.setId(toSave.getId() + 1);
        	personDao.save(toSave);
            fail();
        } catch (NoSuchElementException e) {
            
        }
    }
    
    @Test
    // A new Location can be stored
    public void test4()
    {
    	Location location = new Location();
    	location.setCity("Memphis");
    	location.setState("Tennessee");
    	location.setCountry("USA");
    	
    	LocationDao locationDao = new LocationDao();
    	locationDao.save(location);
    	
    	Location gotten = locationDao.get(location.getId());
    	
    	assertTrue(compareLocation(location, gotten));
    }
    
    @Test
    // A Location object can be retrieved 
    public void test5()
    {
    	Location location = new Location();
    	location.setCity("Memphis");
    	location.setState("Tennessee");
    	location.setCountry("USA");
    	
    	LocationDao locationDao = new LocationDao();
    	locationDao.save(location);
    	
    	Location gotten = locationDao.get(location.getId());
    	
    	assertTrue(compareLocation(location, gotten));
    }
    
    @Test
    // An existing Location can be updated
    public void test6()
    {
    	Location location = new Location();
    	location.setCity("Memphis");
    	location.setState("Tennessee");
    	location.setCountry("USA");
    	
    	LocationDao locationDao = new LocationDao();
    	locationDao.save(location);
    	
    	Location gotten = locationDao.get(location.getId());
    	
    	assertTrue(compareLocation(location, gotten));
    	
    	location.setCity("Delhi");
    	location.setState("Delhi");
    	location.setCountry("India");
    	
    	locationDao.save(location);
    	
    	Location gotten2 = locationDao.get(location.getId());
    	
    	assertTrue(!compareLocation(location, gotten));
    	assertTrue(compareLocation(location, gotten2));
    	
    	try {
    		location.setId(location.getId() + 1);
        	locationDao.save(location);
            fail();
        } catch (NoSuchElementException e) {
            
        }
    }
    
    @Test
    // A relationship between a Person and a Location can be stored
    public void test7()
    {
    	Location location = new Location();
    	location.setCity("Memphis");
    	location.setState("Tennessee");
    	location.setCountry("USA");
    	
    	PersonDao personDao = new PersonDao();
    	
    	Person toSave = new Person();
    	toSave.setFirstName("Michael");
    	toSave.setLastName("Rollberg");
    	toSave.setAge(22);
    	toSave.setLocation(location);
    	personDao.save(toSave);
    	
    	ResultSet rs = null;
    	try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT ON(pid) " +
					"\"Person\".id as pid, \"Location\".id as lid " + 
					"from \"Person\" " +
					"INNER JOIN \"Location\" " +
					"ON \"Person\".location_id = \"Location\".id " +
					"WHERE \"Person\".id = " + toSave.getId());
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			while (rs.next())
			{
				assertTrue(location.getId().equals(rs.getLong("lid")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    // A new Interest can be stored
    public void test8()
    {
    	Interest interest = new Interest();
    	interest.setTitle("Reading");
    	
    	InterestDao interestDao = new InterestDao();
    	interestDao.save(interest);
    	
    	Interest gotten = interestDao.get(interest.getId());
    	
    	assertTrue(compareInterest(interest, gotten));
    }
    
    @Test
    // An Interest object can be retrieved
    public void test9()
    {
    	Interest interest = new Interest();
    	interest.setTitle("Reading");
    	
    	InterestDao interestDao = new InterestDao();
    	interestDao.save(interest);
    	
    	Interest gotten = interestDao.get(interest.getId());
    	
    	assertTrue(compareInterest(interest, gotten));
    }
    
    @Test
    // An exiting Interest can be updated
    public void test10()
    {
    	Interest interest = new Interest();
    	interest.setTitle("Reading");
    	
    	InterestDao interestDao = new InterestDao();
    	interestDao.save(interest);
    	
    	Interest gotten = interestDao.get(interest.getId());
    	
    	assertTrue(compareInterest(interest, gotten));
    	
    	interest.setTitle("Programming");
    	
    	interestDao.save(interest);
    	
    	Interest gotten2 = interestDao.get(interest.getId());
    	
    	assertTrue(!compareInterest(interest, gotten));
    	assertTrue(compareInterest(interest, gotten2));
    	
    	try {
    		interest.setId(interest.getId() + 1);
        	interestDao.save(interest);
            fail();
        } catch (NoSuchElementException e) {
            
        }
    }
    
    @Test
    // A relationship between a Person and a series of Interests can be stored
    public void test11()
    {
    	Location location = new Location();
    	location.setCity("Memphis");
    	location.setState("Tennessee");
    	location.setCountry("USA");
    	
    	Set<Interest> interests = new HashSet<Interest>();
    	Interest interest1 = new Interest();
    	interest1.setTitle("Reading");
    	Interest interest2 = new Interest();
    	interest2.setTitle("Video Games");		
    	new InterestDao().save(interest1);
    	new InterestDao().save(interest2);
    	interests.add(interest1);
    	interests.add(interest2);
    	
    	PersonDao personDao = new PersonDao();
    	
    	Person toSave = new Person();
    	toSave.setFirstName("Michael");
    	toSave.setLastName("Rollberg");
    	toSave.setAge(22);
    	toSave.setInterests(interests);
    	toSave.setLocation(location);
    	personDao.save(toSave);
    	
    	ResultSet rs = null;
    	try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT " +
					"person_id as pid, interest_id as iid " + 
					"from \"PersonInterest\" " +
					"WHERE person_id = " + toSave.getId() + " AND " +
					"(interest_id = " + interest1.getId() + " OR interest_id = " + interest2.getId() + ")");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			while (rs.next())
			{
				assertTrue(rs.getLong("pid") == toSave.getId());
				boolean containsInterest = false;
				for (Interest interest : interests)
				{
					if (interest.getId() == rs.getLong("iid"))
					{
						containsInterest = true;
					}
				}
				if (containsInterest == false)
				{
					fail();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test 
    // The PersonDao.findInterestGroup(Interest, Location) method operates with accurate results
    public void test12()
    {
    	Location location1 = new Location();
    	location1.setCity("Memphis");
    	location1.setState("Tennessee");
    	location1.setCountry("USA");
    	
    	Location location2 = new Location();
    	location2.setCity("Nashville");
    	location2.setState("Tennessee");
    	location2.setCountry("USA");
    	
    	Interest interest1 = new Interest();
    	interest1.setTitle("Reading");
    	Interest interest2 = new Interest();
    	interest2.setTitle("Video Games");		
    	new InterestDao().save(interest1);
    	new InterestDao().save(interest2);
    	
    	Set<Interest> interests1 = new HashSet<Interest>();
    	interests1.add(interest1);
    	
    	Set<Interest> interests2 = new HashSet<Interest>();
    	interests2.add(interest2);
    	
    	PersonDao personDao = new PersonDao();
    	
    	Person person1 = new Person();
    	person1.setFirstName("Michael");
    	person1.setLastName("Rollberg");
    	person1.setAge(22);
    	person1.setLocation(location1);
    	person1.setInterests(interests1);
    	personDao.save(person1);
    	
    	Person person2 = new Person();
    	person2.setFirstName("Jake");
    	person2.setLastName("Long");
    	person2.setAge(22);
    	person2.setLocation(location1);
    	person2.setInterests(interests2);
    	personDao.save(person2);
    	
    	Person person3 = new Person();
    	person3.setFirstName("Chris");
    	person3.setLastName("Guy");
    	person3.setAge(22);
    	person3.setLocation(location2);
    	person3.setInterests(interests1);
    	personDao.save(person3);
    	
    	Person person4 = new Person();
    	person4.setFirstName("Larry");
    	person4.setLastName("Dude");
    	person4.setAge(22);
    	person4.setLocation(location1);
    	person4.setInterests(interests1);
    	personDao.save(person4);
    	
    	Set<Person> interestGroup = personDao.findInterestGroup(interest1, location1);
    	Set<Person> realInterestGroup = new HashSet<Person>();
    	realInterestGroup.add(person1);
    	realInterestGroup.add(person4);
    	
    	assertTrue(compareSetPerson(interestGroup, realInterestGroup));
    }
    
    private boolean comparePerson(Person person1, Person person2)
    {
		if (person1 == person2)
			return true;
		if (person2 == null)
			return false;
		if (person1.getClass() != person2.getClass())
			return false;
		Person other = (Person) person2;
		if (person1.getAge() == null) {
			if (other.getAge() != null)
				return false;
		} else if (!person1.getAge().equals(other.getAge()))
			return false;
		if (person1.getFirstName() == null) {
			if (other.getFirstName() != null)
				return false;
		} else if (!person1.getFirstName().equals(other.getFirstName()))
			return false;
		if (person1.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!person1.getId().equals(other.getId()))
			return false;
		if (person1.getInterests() == null) {
			if (other.getInterests() != null)
				return false;
		} else if (!compareSetInterest(person1.getInterests(), other.getInterests()))
			return false;
		if (person1.getLastName() == null) {
			if (other.getLastName() != null)
				return false;
		} else if (!person1.getLastName().equals(other.getLastName()))
			return false;
		if (person1.getLocation() == null) {
			if (other.getLocation() != null)
				return false;
		} else if (!compareLocation(person1.getLocation(), other.getLocation()))
			return false;
		return true;
    }
    
    
	private boolean compareLocation(Location location1, Location location2) {
		if (location1 == location2)
			return true;
		if (location2 == null)
			return false;
		if (location1.getClass() != location2.getClass())
			return false;
		Location other = (Location) location2;
		if (location1.getCity() == null) {
			if (other.getCity() != null)
				return false;
		} else if (!location1.getCity().equals(other.getCity()))
			return false;
		if (location1.getCountry() == null) {
			if (other.getCountry() != null)
				return false;
		} else if (!location1.getCountry().equals(other.getCountry()))
			return false;
		if (location1.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!location1.getId().equals(other.getId()))
			return false;
		if (location1.getState() == null) {
			if (other.getState() != null)
				return false;
		} else if (!location1.getState().equals(other.getState()))
			return false;
		return true;
	}
	
	private boolean compareInterest(Interest interest1, Interest interest2)
	{
		if (interest1 == interest2)
			return true;
		if (interest2 == null)
			return false;
		if (interest1.getClass() != interest2.getClass())
			return false;
		Interest other = (Interest) interest2;
		if (interest1.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!interest1.getId().equals(other.getId()))
			return false;
		if (interest1.getTitle() == null) {
			if (other.getTitle() != null)
				return false;
		} else if (!interest1.getTitle().equals(other.getTitle()))
			return false;
		return true;
	}
	
	private boolean compareSetInterest(Set<Interest> interests1, Set<Interest> interests2)
	{
		for (Interest interest1 : interests1)
		{
			boolean i1IsIni2 = false;
			for (Interest interest2 : interests2)
			{
				if (compareInterest(interest1, interest2))
				{
					i1IsIni2 = true;
					break;
				}
			}
			if (i1IsIni2 == false)
			{
				return false;
			}
		}
		
		return true;
	}
	
	private boolean compareSetPerson(Set<Person> people1, Set<Person> people2)
	{
		for (Person person1 : people1)
		{
			boolean i1IsIni2 = false;
			for (Person person2 : people2)
			{
				if (comparePerson(person1, person2))
				{
					i1IsIni2 = true;
					break;
				}
			}
			if (i1IsIni2 == false)
			{
				return false;
			}
		}
		
		return true;
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
}
