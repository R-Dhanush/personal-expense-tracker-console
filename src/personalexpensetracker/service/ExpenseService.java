package personalexpensetracker.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import personalexpensetracker.dao.DAOFactory;
import personalexpensetracker.dao.ExpenseDAO;
import personalexpensetracker.model.Category;
import personalexpensetracker.model.Expense;
import personalexpensetracker.model.User;

public class ExpenseService {
	
	private ExpenseDAO expenseDAO;
	private CategoryService categoryService;
	
	public ExpenseService() {
		this.expenseDAO = DAOFactory.getExpenseDAO();
		this.categoryService = new CategoryService(); 
	}
	
	public void addExpense(double amount, int category, String date, String description, int user) throws Exception {
		
		//amount
		if(amount <= 0) {
			throw new IllegalArgumentException("Expense must be greater than 0.");
		}
		
		//category
		boolean flag = true;
		List<Category> categories = categoryService.getAllCategories();
		for(Category c : categories) {
			if(c.getCategoryID() == category) {
				flag = false;
				break;
			}
		}
		
		if(flag) {
			
			throw new IllegalArgumentException("Enter the correct Category.");
		}
		
		
		//expense_date
		LocalDate expense_date;
		try {
			
			expense_date = LocalDate.parse(date);
			
			if(expense_date.getYear() > LocalDate.now().getYear()) {
				throw new IllegalArgumentException("Enter a valid year.");
			}
			
		} catch (java.time.format.DateTimeParseException e) {
			
			throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
			
		}
		
		Expense expense = new Expense(amount, category, expense_date, description, user);	
		
		expenseDAO.addExpense(expense);
		
	}
	
	public void updateExpense(int id, double amount, int category, String date, String description, User loggedInUser) throws Exception {
		//id
		boolean checkID = true;
		List<Expense> expenses = viewAllExpenses(loggedInUser);
		for(Expense expense : expenses) {
			if(expense.getExpenseID() == id) {
				checkID = false;
				break;
			}
		}
		
		if(checkID) {
			throw new IllegalArgumentException("Enter the correct ID.");
		}
		
		//amount
		if(amount <= 0) {
			throw new IllegalArgumentException("Expense must be greater than 0.");
		}
		
		//category
		boolean flag = true;
		List<Category> categories = categoryService.getAllCategories();
		for(Category c : categories) {
			if(c.getCategoryID() == category) {
				flag = false;
				break;
			}
		}
		
		if(flag) {
			
			throw new IllegalArgumentException("Enter the correct Category.");
		}
		
		
		//expense_date
		LocalDate expense_date;
		try {
			
			expense_date = LocalDate.parse(date);
			
			if(expense_date.getYear() > LocalDate.now().getYear()) {
				throw new IllegalArgumentException("Enter a valid year.");
			}
			
		} catch (java.time.format.DateTimeParseException e) {
			
			throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
			
		}
		
		Expense expense = new Expense(amount, category, expense_date, description, loggedInUser.getUserID());
		expense.setExpenseID(id);
		
		expenseDAO.updateExpense(expense);
	}
	
	public void deleteExpense(int id, User loggedInUser) throws Exception {
		//id
		boolean checkID = true;
		List<Expense> expenses = viewAllExpenses(loggedInUser);
		for(Expense expense : expenses) {
			if(expense.getExpenseID() == id) {
				checkID = false;
				break;
			}
		}
		
		if(checkID) {
			throw new IllegalArgumentException("Enter the correct ID.");
		}
		
		expenseDAO.deleteExpense(id);
	}
	
	public List<Expense> viewAllExpenses(User loggedInUser) throws Exception {
		if(loggedInUser == null) {
			throw new IllegalArgumentException("User must be logged in");
		}
		
		return expenseDAO.viewAllExpenses(loggedInUser);
	}
	
	public List<Expense> viewByCategory(User loggedInUser, int categoryID) throws Exception {
		if(loggedInUser == null) {
			throw new IllegalArgumentException("User must be logged in");
		}
		
		//category
		boolean flag = true;
		List<Category> categories = categoryService.getAllCategories();
		for(Category c : categories) {
			if(c.getCategoryID() == categoryID) {
				flag = false;
				break;
			}
		}
		
		if(flag) {
			
			throw new IllegalArgumentException("Enter the correct Category.");
		}
		
		return expenseDAO.viewByCategory(loggedInUser, categoryID);
	}
	
	public HashMap<Integer, Double> viewMonthlySummary(int month, int year, User loggedInUser) throws Exception {
		if(loggedInUser == null) {
			throw new IllegalArgumentException("User must be logged in");
		}
		
		if(month <= 0 || month > 12) {
			throw new IllegalArgumentException("Enter the valid month.");
		}
		
		return expenseDAO.viewMonthlySummary(month, year, loggedInUser);
	}
	
	public HashMap<Integer, Double> viewYearlySummary(int year, User loggedInUser) throws Exception {
		if(loggedInUser == null) {
			throw new IllegalArgumentException("User must be logged in");
		}
		
		return expenseDAO.viewYearlySummary(year, loggedInUser);
	}
}
