package personalexpensetracker.dao;

import java.sql.SQLException;

import personalexpensetracker.model.User;

public interface UserDAO {
	User register(User user) throws SQLException;
	User login(String name, String password) throws SQLException;
}
