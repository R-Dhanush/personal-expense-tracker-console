package personalexpensetracker.dao;

import java.sql.*;

import personalexpensetracker.model.User;
import personalexpensetracker.util.DBConnection;

public class UserDAOImpl implements UserDAO{

	@Override
	public User register(User user) throws SQLException {
		String query = "insert into users(username, password) values(?,?)";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			
			ps.executeUpdate();
			
			try (ResultSet rs = ps.getGeneratedKeys()) {
				
				if(rs.next()) {
					user.setUserID(rs.getInt(1));
				}
			}
			
		}
		
		return user;
	}
	
	@Override
	public User login(String name, String password) throws SQLException {
		String query = "select user_id, username, password from users where username = ? and password = ?";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(query)){
			
			ps.setString(1, name);
			ps.setString(2, password);
			
			try (ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					User user = new User(rs.getString("username"), rs.getString("password"));
					user.setUserID(rs.getInt("user_id"));
					return user;
				}
			}
			
		}
		
		return null;
	}
	
}
