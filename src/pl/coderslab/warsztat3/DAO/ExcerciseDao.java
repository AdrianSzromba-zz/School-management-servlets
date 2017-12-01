package pl.coderslab.warsztat3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.warsztat3.model.Excercise;

public class ExcerciseDao {

	public void saveToDB(Connection conn, Excercise e) throws SQLException {
		if (e.getId() == 0) {
			String sql = "INSERT INTO excercise(title, description)" + "VALUES(?,?);";
			String[] generatedColumns = { "ID" };
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, e.getTitle());
			ps.setString(2, e.getDescription());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				e.setId(resultSet.getInt(1));
			}
			resultSet.close();
			ps.close();
		} else {
			String sql = "UPDATE excercise SET title=?, description=?" + "WHERE id=?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, e.getTitle());
			ps.setString(2, e.getDescription());
			ps.setInt(3, e.getId());
			ps.executeUpdate();
			ps.close();
		}
	}

	static public Excercise[] loadAllExcercises(Connection conn) throws SQLException {
		ArrayList<Excercise> excercises = new ArrayList<>();
		String sql = "SELECT * FROM excercise;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Excercise loadedExcercise = new Excercise();
			loadedExcercise.setId(resultSet.getInt("id"));
			loadedExcercise.setTitle(resultSet.getString("title"));
			loadedExcercise.setDescription(resultSet.getString("description"));
			excercises.add(loadedExcercise);
		}
		Excercise[] uArray = new Excercise[excercises.size()];
		uArray = excercises.toArray(uArray);
		ps.close();
		resultSet.close();
		return uArray;
	}

	static public Excercise loadExcerciseById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM excercise where id=?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			Excercise loadedExcercise = new Excercise();
			loadedExcercise.setId(resultSet.getInt("id"));
			loadedExcercise.setTitle(resultSet.getString("title"));
			loadedExcercise.setDescription(resultSet.getString("description"));
			ps.close();
			resultSet.close();
			return loadedExcercise;
		}
		ps.close();
		resultSet.close();
		return null;
	}

	public void deleteExcercise(Connection conn, Excercise e) throws SQLException {
		if (e.getId() != 0) {
			String sql = "DELETE FROM excercise WHERE id= ?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, e.getId());
			ps.executeUpdate();
			e.setId(0);
		}
	}
}
