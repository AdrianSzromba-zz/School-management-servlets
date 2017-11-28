package pl.coderslab.warsztat3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {

	private int id;
	private String created;
	private String updated;
	private String description;
	private int excerciseId;
	private long usersId;
	
	public Solution() {
		super();
		this.id = 0;
		this.created = "";
		this.updated = "";
		this.description = "";
		this.excerciseId = 0;
		this.usersId = 0;
	}
	
	public Solution(String created, String updated, String description, int excercise_id, long users_id) {
		super();
		this.id = 0;
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.excerciseId = excercise_id;
		this.usersId = users_id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getExcerciseId() {
		return excerciseId;
	}

	public void setExcerciseId(int excercise_id) {
		this.excerciseId = excercise_id;
	}

	public long getUsersId() {
		return usersId;
	}

	public void setUsers_id(long usersId) {
		this.usersId = usersId;
	}

	public int getId() {
		return id;
	}
	
	public void saveToDB(Connection conn) throws SQLException {
		if(this.id == 0) {
			String sql = "INSERT INTO solution(created, updated, description, excercise_id, users_id)"
					+ "VALUES(?,?,?,?,?);";
			String[] generatedColumns ={"ID"};
			PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.created);
			preparedStatement.setString(2, this.updated);
			preparedStatement.setString(3, this.description);
			preparedStatement.setInt(4, this.excerciseId);
			preparedStatement.setLong(5, this.usersId);
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				this.id = resultSet.getInt(1);
			}
			resultSet.close();
			preparedStatement.close();
		} else {
			String sql = "UPDATE users SET created=?, updated=?, description=?, excercise_id=?, users_id=? "
					+ "WHERE id=?;";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.created);
			preparedStatement.setString(2, this.updated);
			preparedStatement.setString(3, this.description);
			preparedStatement.setInt(4, this.excerciseId);
			preparedStatement.setLong(5, this.usersId);
			preparedStatement.setInt(6, this.id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		}
	}
	
	static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM solution where id=?;";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getString("created");
			loadedSolution.updated = resultSet.getString("updated");
			loadedSolution.excerciseId = resultSet.getInt("excercise_id");
			loadedSolution.usersId = resultSet.getLong("users_id");
			return loadedSolution;
		}
		return null;
	}
	
	static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution;";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getString("created");
			loadedSolution.updated = resultSet.getString("updated");
			loadedSolution.excerciseId = resultSet.getInt("excercise_id");
			loadedSolution.usersId = resultSet.getLong("users_id");
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM solution WHERE id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
	
	static public Solution[] loadAllByUserId (Connection conn, long usersId) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution WHERE users_id=?;";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setLong(1, usersId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getString("created");
			loadedSolution.updated = resultSet.getString("updated");
			loadedSolution.excerciseId = resultSet.getInt("excercise_id");
			loadedSolution.usersId = resultSet.getLong("users_id");
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}
	
	static public Solution[] loadAllByExerciseId (Connection conn, int excerciseId) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution WHERE excercise_id=? ORDER BY created ASC;";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, excerciseId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getString("created");
			loadedSolution.updated = resultSet.getString("updated");
			loadedSolution.excerciseId = resultSet.getInt("excercise_id");
			loadedSolution.usersId = resultSet.getLong("users_id");
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}
}
