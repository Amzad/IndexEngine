import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.sql.*;
import javax.imageio.ImageIO;

/**
 * This class handles the database and transactions of the database.
 * @author Amzad
 *
 */

public class Database {
	
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://192.168.1.107/books";
	 static final String USER = "Lays";
	 static final String PASS = "Lays";
	 static Connection conn = null;
	 static Statement stmt = null;
	/* Database Array Index Order
	 * 
	 * 0 - ISBN10
	 * 1 - ISBN13
	 * 2 - Title
	 * 3 - Author
	 * 4 - Published Year
	 * 5 - Publisher
	 */
	/**
	 * Default Database Constructor
	 */
	public Database() {
		try {	
			System.out.println("Database: Connected");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (SQLException e) {
			System.out.println("Unable to connect to database");
		}
	}
	
	public void openConnection() {
		try {	
			System.out.println("Database: Connected");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (SQLException e) {
			System.out.println("Unable to connect to database");
		}
	}
	
	public void closeConnection() {
		try {
			conn.close();
			System.out.println("Database: Disconnected");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void dequeue(String keyword, int count, String time) {
		try {	
			openConnection();
			stmt = conn.createStatement();
			String sql = "UPDATE indexqueue SET status = 'Done' WHERE keyword = '" + keyword + "'";
			stmt.executeUpdate(sql);
			sql = "UPDATE indexqueue SET count = '" + count + "' WHERE keyword = '" + keyword + "'";
			stmt.executeUpdate(sql);
			sql = "UPDATE indexqueue SET time = '" + time + "' WHERE keyword = '" + keyword + "'";
			stmt.executeUpdate(sql);
			System.out.println(keyword + " caching completed");
			closeConnection();
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	
	public void updateResult(String keyword, int resultcount) {
		try {	
			openConnection();
			stmt = conn.createStatement();
			String sql = "UPDATE indexqueue SET resultcount = '" + resultcount + "' WHERE keyword = '" + keyword + "'";
			stmt.executeUpdate(sql);
			closeConnection();
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	
	public void checkQueue() {
		if (hasNext()) {
			System.out.println("Queue has items");
		} else {
			System.out.println("Queue is empty");
		}
	}
	
	public boolean hasNext() {
		try {	
			openConnection();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM indexqueue WHERE status = 'Pending'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) return true;

		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		
		return false;
		
	}
	
	public String getNext() {
		try {	
			openConnection();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM indexqueue WHERE status = 'Pending'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String keyword = rs.getString("keyword");
				return keyword;
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		
		return null;
	
	}

	/**
	 * Add a new book to the database
	 * @param item Book object to be added
	 */
	public boolean add(Book item) {
		try {	
			openConnection();
			stmt = conn.createStatement();
			String sql = "INSERT INTO db VALUES ('" + item.ISBN13 + "', '" 
													+ item.title + "', '" 
													+ item.author + "', '" 
													+ item.publisher + "', '" 
													+ item.year + "', '" 
													+ item.ISBN10 + "', '" 
													+ item.link + "', '1')";
			stmt.executeUpdate(sql);
			System.out.println(item.title + " added into the database");
			return true;
		} catch (SQLException e) {		
			//e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}
	
	/**
	 * Remove a book object
	 * @param isbn ISBN13 number of the book to remove
	 */
	public Book remove(String isbn13) {
		

		return null;
	}
	
	/**
	 * Check of the ISBN13 is in the database
	 * @param isbn The ISBN13 number to check
	 * @return Returns true if it exists
	 */
	public boolean ifExists(String isbn13) {
		return false;

	}

	
	/**
	 * Saves the image into the image directory.
	 * @param link Link to the image.
	 */
	public void saveImage(Book link) {
		URL url;
		try {
			String workingDirectory = "C:\\AppServ\\www\\pictures\\";
			url = new URL(link.imgLink);
			BufferedImage img = ImageIO.read(url);
			String file = url.getPath();
			file = file.substring(file.lastIndexOf("/")+1, file.length());
			File imgFile = new File(workingDirectory + file);
			String extension = file.substring(file.lastIndexOf(".")+1, file.length());
			//file = link.ISBN13;
			ImageIO.write(img, extension, imgFile);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
