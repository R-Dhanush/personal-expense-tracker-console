package personalexpensetracker.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import personalexpensetracker.model.Expense;
import personalexpensetracker.model.User;

public interface ExpenseDAO {
	
	void addExpense(Expense expense) throws SQLException;
	void updateExpense(Expense expense) throws SQLException;
	void deleteExpense(int id) throws SQLException;
	List<Expense> viewAllExpenses(User loggedInUser) throws SQLException;
	List<Expense> viewByCategory(User loggedInUser, int categoryID) throws SQLException;
	HashMap<Integer, Double> viewMonthlySummary(int month, int year, User loggedInUser) throws SQLException;
	HashMap<Integer, Double> viewYearlySummary(int year, User loggedInUser) throws SQLException;
}
