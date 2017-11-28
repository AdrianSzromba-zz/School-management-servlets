package pl.coderslab.warsztat3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Excercise {

	private int id;
	private String title;
	private String description;
	
	public Excercise() {
		super();
		this.id = 0;
		this.title = "";
		this.description = "";
	}
	
	public Excercise(String title, String description) {
		super();
		this.id = 0;
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}
	
	public void saveToDB(Connection conn) throws SQLException {
		if(this.id == 0) {
			String sql = "INSERT INTO excercise(title, description)"
					+ "VALUES(?,?);";
			String[] generatedColumns ={"ID"};
			PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				this.id = resultSet.getInt(1);
			}
			resultSet.close();
			preparedStatement.close();
		} else {
			String sql = "UPDATE excercise SET title=?, description=?"
					+ "WHERE id=?;";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.setInt(3, this.id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		}
	}
	
	static public Excercise[] loadAllExcercises(Connection conn) throws SQLException {
		ArrayList<Excercise> excercises = new ArrayList<Excercise>();
		String sql = "SELECT * FROM excercise;";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Excercise loadedExcercise = new Excercise();
			loadedExcercise.id = resultSet.getInt("id");
			loadedExcercise.title = resultSet.getString("title");
			loadedExcercise.description = resultSet.getString("description");
			excercises.add(loadedExcercise);
		}
		Excercise[] uArray = new Excercise[excercises.size()];
		uArray = excercises.toArray(uArray);
		return uArray;
	}
	
	static public Excercise loadExcerciseById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM excercise where id=?;";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Excercise loadedExcercise = new Excercise();
			loadedExcercise.id = resultSet.getInt("id");
			loadedExcercise.title = resultSet.getString("title");
			loadedExcercise.description = resultSet.getString("description");
			return loadedExcercise;
		}
		return null;
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM excercise WHERE id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
}
