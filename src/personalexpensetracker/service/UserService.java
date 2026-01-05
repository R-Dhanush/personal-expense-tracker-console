package personalexpensetracker.service;

import personalexpensetracker.dao.DAOFactory;
import personalexpensetracker.dao.UserDAO;
import personalexpensetracker.model.User;

public class UserService {
	
	private UserDAO userDAO;
	
	public UserService() {
		this.userDAO = DAOFactory.getUserDAO();
	}
	
	public User register(String name, String password) throws Exception {
		String userName = name.trim();
		String userPassword = password.trim();
		
		if(!(userName.length() > 2)) {
			throw new IllegalArgumentException("User Name can not be less than 3 letters.");
		}
		
		if(!userName.matches("^[A-Za-z]+$")) {
			throw new IllegalArgumentException("Username must contain only alphabets (A-Z, a-z)");
		}
		
		if(!(userPassword.length() > 3)) {
			throw new IllegalArgumentException("Password must be greater than or equal to 4 letters.");
		}
		
		if(!userPassword.matches("^[A-Za-z0-9_]+$")) {
			throw new IllegalArgumentException("Password can contain only alphabets, numbers, and underscore (_)");
		}
		
		User user = new User(name, password);
		
		return userDAO.register(user);
		
	}
	
	public User login(String name, String password) throws Exception {
		return userDAO.login(name, password);
	}
}
