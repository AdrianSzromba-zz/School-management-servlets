package pl.coderslab.warsztat3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.coderslab.warsztat3.model.Solution;
import pl.coderslab.warsztat3.model.User;

public class SolutionDao {
	
	public void saveToDB(Connection conn, Solution s) throws SQLException {
		if(s.getId() == 0) {
			String sql = "INSERT INTO solution(created, updated, description, excercise_id, users_id)"
					+ "VALUES(NOW(),NOW(),?,?,?);";
			String[] generatedColumns ={"ID"};
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, s.getDescription());
			ps.setInt(2, s.getExcerciseId());
			ps.setLong(3, s.getUsersId());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			
			if (resultSet.next()) {
				s.setId(resultSet.getInt(1));
			}
			resultSet.close();
			ps.close();
		} else {
			String sql = "UPDATE users SET updated=NOW(), description=?, excercise_id=?, users_id=? "
					+ "WHERE id=?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, s.getDescription());
			ps.setInt(2, s.getExcerciseId());
			ps.setLong(3, s.getUsersId());
			ps.setInt(4, s.getId());
			ps.executeUpdate();
			ps.close();
		}
	}
	
	static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM solution where id=?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.setId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getDate("created"));
			loadedSolution.setUpdated(rs.getDate("updated"));
			loadedSolution.setExcerciseId(rs.getInt("excercise_id"));
			loadedSolution.setUsersId(rs.getLong("users_id"));
			ps.close();
			rs.close();
			return loadedSolution;
		}
		ps.close();
		return null;
	}
	
	static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<>();
		String sql = "SELECT * FROM solution;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.setId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getDate("created"));
			loadedSolution.setUpdated(rs.getDate("updated"));
			loadedSolution.setExcerciseId(rs.getInt("excercise_id"));
			loadedSolution.setUsersId(rs.getLong("users_id"));
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		ps.close();
		rs.close();
		return uArray;
	}
	
	public void deleteSolution(Connection conn, Solution s) throws SQLException {
		if (s.getId() != 0) {
			String sql = "DELETE FROM solution WHERE id= ?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, s.getId());
			ps.executeUpdate();
			ps.close();
			s.setId(0);
		}
	}
	
	static public Solution[] loadAllByUserId (Connection conn, long usersId) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<>();
		String sql = "SELECT * FROM solution WHERE users_id=?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, usersId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.setId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getDate("created"));
			loadedSolution.setUpdated(rs.getDate("updated"));
			loadedSolution.setExcerciseId(rs.getInt("excercise_id"));
			loadedSolution.setUsersId(rs.getLong("users_id"));
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		ps.close();
		rs.close();
		return uArray;
	}
	
	static public Solution[] loadAllByExerciseId (Connection conn, int excerciseId) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<>();
		String sql = "SELECT * FROM solution WHERE excercise_id=? ORDER BY created ASC;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, excerciseId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.setId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getDate("created"));
			loadedSolution.setUpdated(rs.getDate("updated"));
			loadedSolution.setExcerciseId(rs.getInt("excercise_id"));
			loadedSolution.setUsersId(rs.getLong("users_id"));
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		ps.close();
		rs.close();
		return uArray;
	}
	
	public static List<Solution> loadAll(Connection conn, int count) throws SQLException {
		List<Solution> list = new ArrayList<>();
		String sql = "SELECT solution.id, solution.updated, solution.description, solution.users_id, users.username "
				+ "FROM solution JOIN users ON solution.users_id=users.id ORDER BY solution.updated DESC LIMIT ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, count);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.setId(rs.getInt("id"));
			loadedSolution.setCreated(rs.getDate("created"));
			loadedSolution.setUpdated(rs.getDate("updated"));
			loadedSolution.setExcerciseId(rs.getInt("excercise_id"));
			loadedSolution.setUsersId(rs.getLong("users_id"));
			list.add(loadedSolution);
		}
		ps.close();
		rs.close();
		return list;
	}
}
