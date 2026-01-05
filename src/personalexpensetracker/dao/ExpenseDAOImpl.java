package personalexpensetracker.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import personalexpensetracker.model.Expense;
import personalexpensetracker.model.User;
import personalexpensetracker.util.DBConnection;

public class ExpenseDAOImpl implements ExpenseDAO {

	@Override
	public void addExpense(Expense expense) throws SQLException {
		
		String query = "insert into expenses(amount, category_id, expense_date, description, user_id) values(?, ?, ?, ?, ?)";
		
		try (Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			
			ps.setDouble(1, expense.getAmount());
			ps.setInt(2, expense.getCategory());
			ps.setDate(3, java.sql.Date.valueOf(expense.getExpenseDate()));
			ps.setString(4, expense.getDescription());
			ps.setInt(5, expense.getUser());
			
			ps.executeUpdate();
			
			try (ResultSet rs = ps.getGeneratedKeys()) {
				
				if(rs.next()) {
					expense.setExpenseID(rs.getInt(1));
				}
			}
			
		}
	}
	
	@Override
	public void updateExpense(Expense expense) throws SQLException {
		
		String query = "update expenses set amount = ?, category_id = ?, expense_date = ?, description = ?, user_id = ? where expense_id = ?";
		
		try(Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query)) {
			
			ps.setDouble(1, expense.getAmount());
			ps.setInt(2, expense.getCategory());
			ps.setDate(3, java.sql.Date.valueOf(expense.getExpenseDate()));
			ps.setString(4, expense.getDescription());
			ps.setInt(5, expense.getUser());
			ps.setInt(6, expense.getExpenseID());
			
			ps.executeUpdate();
		}
	}
	
	@Override
	public void deleteExpense(int id) throws SQLException {
		String query = "delete from expenses where expense_id = ?";
		
		try(Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query)) {
			
			ps.setInt(1, id);
			
			ps.executeUpdate();
		}
	}
	
	@Override
	public List<Expense> viewAllExpenses(User loggedInUser) throws SQLException {
		List<Expense> expenses = new ArrayList<>();
		
		String query = "select * from expenses where user_id = ?";
		
		try (Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query)) {
			
			ps.setInt(1, loggedInUser.getUserID());
			
			try (ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					Expense expense = new Expense();
					expense.setExpenseID(rs.getInt("expense_id"));
					expense.setAmount(rs.getDouble("amount"));
					expense.setCategory(rs.getInt("category_id"));
					expense.setExpenseDate(rs.getDate("expense_date").toLocalDate());
					expense.setDescription(rs.getString("description"));
					expense.setUser(rs.getInt("user_id"));
					
					expenses.add(expense);					
				}
			}
		}
		
		return expenses;
	}
	
	@Override
	public List<Expense> viewByCategory(User loggedInUser, int categoryID) throws SQLException {
		List<Expense> expenses = new ArrayList<>();
		
		String query = "select * from expenses where category_id = ? and user_id = ?";
		
		try (Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query)) {
			
			ps.setInt(1, categoryID);
			ps.setInt(2, loggedInUser.getUserID());
			
			try (ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					Expense expense = new Expense();
					expense.setExpenseID(rs.getInt("expense_id"));
					expense.setAmount(rs.getDouble("amount"));
					expense.setCategory(rs.getInt("category_id"));
					expense.setExpenseDate(rs.getDate("expense_date").toLocalDate());
					expense.setDescription(rs.getString("description"));
					expense.setUser(rs.getInt("user_id"));
					
					expenses.add(expense);
				}
			}
		}
		
		return expenses;
	}
	
	@Override
	public HashMap<Integer, Double> viewMonthlySummary(int month, int year, User loggedInUser) throws SQLException {
		
		HashMap<Integer, Double> monthlySummary = new HashMap<>();
		
		String query = "select category_id, amount from expenses where month(expense_date) = ? and year(expense_date) = ? and user_id = ?";
		
		try (Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query)) {
			
			ps.setInt(1, month);
			ps.setInt(2, year);
			ps.setInt(3, loggedInUser.getUserID());
			
			try (ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					monthlySummary.put(rs.getInt("category_id"), monthlySummary.getOrDefault(rs.getInt("category_id"), 0.0) + rs.getDouble("amount"));
				}
			}
		}
		
		return monthlySummary;
	}
	
	@Override
	public HashMap<Integer, Double> viewYearlySummary(int year, User loggedInUser) throws SQLException {
		
		HashMap<Integer, Double> yearlySummary = new HashMap<>();
		
		String query = "select category_id, amount from expenses where year(expense_date) = ? and user_id = ?";
		
		try (Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, year);
			ps.setInt(2, loggedInUser.getUserID());
			
			try (ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					yearlySummary.put(rs.getInt("category_id"), yearlySummary.getOrDefault(rs.getInt("category_id"), 0.0) + rs.getDouble("amount"));
				}
			}
		}
		
		return yearlySummary;
	}
}
