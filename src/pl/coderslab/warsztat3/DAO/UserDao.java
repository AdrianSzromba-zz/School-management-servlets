package pl.coderslab.warsztat3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.coderslab.warsztat3.model.User;

public class UserDao {

	
	public void saveToDB(Connection conn, User u) throws SQLException {
		if(u.getId() == 0) {
			String sql = "INSERT INTO users(username, email, password, user_group_id)"
					+ "VALUES(?,?,?,?);";
			String[] generatedColumns ={"ID"};
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getEmail());
			ps.setString(3, u.getPassword());
			ps.setInt(4, u.getUserGroupId());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				u.setId(resultSet.getLong(1));
			}
			resultSet.close();
			ps.close();
		} else {
			String sql = "UPDATE users SET username=?, email=?, password=?, user_group_id=? "
					+ "WHERE id=?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getEmail());
			ps.setString(3, u.getPassword());
			ps.setInt(4, u.getUserGroupId());
			ps.setLong(5, u.getId());
			ps.executeUpdate();
			ps.close();
		}
	}
	
	static public User loadUserById(Connection conn, long id) throws SQLException {
		String sql = "SELECT * FROM users where id=?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.setId(resultSet.getLong("id"));
			loadedUser.setUsername(resultSet.getString("username"));
			loadedUser.setPassword(resultSet.getString("password"));
			loadedUser.setEmail(resultSet.getString("email"));
			loadedUser.setUserGroupId(resultSet.getInt("user_group_id"));
			ps.close();
			resultSet.close();
			return loadedUser;
		}
		ps.close();
		resultSet.close();
		return null;
	}
	
	static public User[] loadAllUsers(Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<>();
		String sql = "SELECT * FROM users;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.setId(resultSet.getLong("id"));
			loadedUser.setUsername(resultSet.getString("username"));
			loadedUser.setPassword(resultSet.getString("password"));
			loadedUser.setEmail(resultSet.getString("email"));
			loadedUser.setUserGroupId(resultSet.getInt("user_group_id"));
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		ps.close();
		resultSet.close();
		return uArray;
	}
	
	public void delete(Connection conn, User u) throws SQLException {
		if (u.getId() != 0) {
			String sql = "DELETE FROM users WHERE id= ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, u.getId());
			ps.executeUpdate();
			u.setId(0);
		}
	}
	
	public static List<User> loadAllByGroupId(Connection conn, int id) throws SQLException {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM users WHERE user_group_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet resultSet = ps.executeQuery();
		while(resultSet.next()) {
			User loadedUser = new User();
			loadedUser.setId(resultSet.getLong("id"));
			loadedUser.setUsername(resultSet.getString("username"));
			loadedUser.setPassword(resultSet.getString("password"));
			loadedUser.setEmail(resultSet.getString("email"));
			loadedUser.setUserGroupId(resultSet.getInt("user_group_id"));
			users.add(loadedUser);
		}
		ps.close();
		resultSet.close();
		return users;
	}
}
