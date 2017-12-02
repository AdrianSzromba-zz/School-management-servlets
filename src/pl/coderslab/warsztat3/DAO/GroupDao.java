package pl.coderslab.warsztat3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.coderslab.warsztat3.model.Group;

public class GroupDao {

	public void saveToDB(Connection conn, Group g) throws SQLException {
		if (g.getId() == 0) {
			String sql = "INSERT INTO user_group(name) VALUES(?);";
			String[] generatedColumns = { "ID" };
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, g.getName());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				g.setId(resultSet.getInt(1));
			}
			resultSet.close();
			ps.close();
		} else {
			String sql = "UPDATE user_group SET name=? WHERE id=?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, g.getName());
			ps.setLong(2, g.getId());
			ps.executeUpdate();
			ps.close();
		}
	}

	static public Group loadGroupById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM user_group where id=?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			Group loadedGroup = new Group();
			loadedGroup.setId(resultSet.getInt("id"));
			loadedGroup.setName(resultSet.getString("name"));
			resultSet.close();
			ps.close();
			return loadedGroup;
		}
		resultSet.close();
		ps.close();
		return null;
	}

	public void deleteGroup(Connection conn, Group g) throws SQLException {
		if (g.getId() != 0) {
			String sql = "DELETE FROM user_group WHERE id= ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, g.getId());
			ps.executeUpdate();
			ps.close();
			g.setId(0);
		}
	}
}
