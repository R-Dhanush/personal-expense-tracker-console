package personalexpensetracker.dao;

public class DAOFactory {

	public static CategoryDAO getCategoryDAO() {
		return new CategoryDAOImpl();
	}
	
	public static ExpenseDAO getExpenseDAO() {
		return new ExpenseDAOImpl();
	}
	
	public static UserDAO getUserDAO() {
		return new UserDAOImpl();
	}
}
