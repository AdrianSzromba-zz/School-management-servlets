package pl.coderslab.warsztat3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class User {
	private long id;
	private String username;
	private String email;
	private String password;
	private int userGroupId;

	public User() {
		super();
		this.id = 0l;
		this.username = "";
		this.email = "";
		this.password = "";
		this.userGroupId = 0;
	}
	
	public User(String username, String email, String password) {
		super();
		this.id = 0l;
		this.username = username;
		this.email = email;
		setPassword(password);
		this.userGroupId = 0;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public void checkPassword(String password) {
		BCrypt.checkpw(password, this.password);
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public long getId() {
		return id;
	}

	public void saveToDB(Connection conn) throws SQLException {
		if(this.id == 0) {
			String sql = "INSERT INTO users(username, email, password, user_group_id)"
					+ "VALUES(?,?,?,?);";
			String[] generatedColumns ={"ID"};
			PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.setInt(4, this.userGroupId);
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				this.id = resultSet.getLong(1);
			}
			resultSet.close();
			preparedStatement.close();
		} else {
			String sql = "UPDATE users SET username=?, email=?, password=?, user_group_id=? "
					+ "WHERE id=?;";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.setInt(4, this.userGroupId);
			preparedStatement.setLong(5, this.id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		}
	}
	
	static public User loadUserById(Connection conn, long id) throws SQLException {
		String sql = "SELECT * FROM users where id=?;";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setLong(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getLong("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			return loadedUser;
		}
		return null;
	}
	
	static public User[] loadAllUsers(Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM users;";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getLong("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM users WHERE id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
	
	public static User getById(long id) {
		String sql ="";
		//execute
		User u = new User();
		return u;
	}
	
}
