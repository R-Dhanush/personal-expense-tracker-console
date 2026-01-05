package personalexpensetracker.model;

import java.time.LocalDate;

public class Expense {
	private int expenseID;
	private double amount;
	private int category;
	private LocalDate expenseDate;
	private String description;
	private int user;
	
	public Expense() {
	}
	
	public Expense(double amount, int category, LocalDate expenseDate, String description, int user) {
		this.amount = amount;
		this.category = category;
		this.expenseDate = expenseDate;
		this.description = description;
		this.user = user;
	}

	public int getExpenseID() {
		return expenseID;
	}
	
	public void setExpenseID(int expenseID) {
		this.expenseID = expenseID;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public int getCategory() {
		return category;
	}
	
	public void setCategory(int category) {
		this.category = category;
	}
	
	public LocalDate getExpenseDate() {
		return expenseDate;
	}
	
	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getUser() {
		return user;
	}
	
	public void setUser(int user) {
		this.user = user;
	}
	
}
