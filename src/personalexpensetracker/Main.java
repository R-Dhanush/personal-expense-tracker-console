package personalexpensetracker;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import personalexpensetracker.model.Category;
import personalexpensetracker.model.Expense;
import personalexpensetracker.model.User;
import personalexpensetracker.service.CategoryService;
import personalexpensetracker.service.ExpenseService;
import personalexpensetracker.service.UserService;

public class Main {
	public static void main(String[] args) {
		Scanner sca = new Scanner(System.in);
		CategoryService categoryService = new CategoryService();
		ExpenseService expenseService = new ExpenseService();
		UserService userService = new UserService();
		
		while(true) {
			try {
				System.out.println("1.Register");
				System.out.println("2.Login");
				System.out.println("3.Exit");
				
				System.out.println("Enter your choice : ");
				
				int ch = sca.nextInt();
				sca.nextLine();
				
				switch(ch) {
					case 1 : {
						try {
							User registeredUser = handleRegister(sca, userService);
							System.out.println(registeredUser.getUsername() + " your registration successful!");
						} catch (SQLIntegrityConstraintViolationException e) {
							System.out.println("Username already exists.");
						} catch (SQLException e) {
							System.out.println("Database error during registration.");
						} catch (IllegalArgumentException e) {
				            System.out.println("Error: " + e.getMessage());
				        }
						
						break;
					}
					case 2: {
						User loggedInUser = null;
						
						try {
							loggedInUser = handleLogin(sca, userService);
						} catch (SQLException e) {
							System.out.println("Database error during login.");
						}
		
						if (loggedInUser == null) {
		                    System.out.println("Invalid username or password.");
		                } else {
		                    System.out.println("Welcome " + loggedInUser.getUsername());
		                    loggedInMenu(sca, loggedInUser, categoryService, expenseService);
		                }
						
						break;
					}
					case 3: {
						System.out.println("Goodbye!");
						sca.close();
						return;
					}
					default: {
						System.out.println("Invalid choice " + ch + " . Please try again.");
					}
				}
			}catch (Exception e) {
				System.out.println("Something went wrong. Please try again.");
	        }
		}
		
	}
	
	private static User handleRegister(Scanner sca, UserService userService) throws Exception {
		System.out.println("Enter User Name : ");
		String name = sca.nextLine();
		
		System.out.println("Enter User Password : ");
		String password = sca.next();
		
		User registeredUser = userService.register(name, password);
		
		return registeredUser;
	}
	
	private static User handleLogin(Scanner sca, UserService userService) throws Exception {
		System.out.println("Enter User Name : ");
		String name = sca.nextLine();
		
		System.out.println("Enter User Password : ");
		String password = sca.next();
		
		User loggedInUser = userService.login(name, password);
		
		return loggedInUser;
	}
	
	private static void loggedInMenu(Scanner sca, User loggedInUser, CategoryService categoryService, ExpenseService expenseService) {
		while(true) {
			try {
				System.out.println("1. Add Expense");
				System.out.println("2. Update Expense");
				System.out.println("3. Delete Expense");
				System.out.println("4. View All Expenses");
				System.out.println("5. View By Category");
				System.out.println("6. View Monthly Summary");
				System.out.println("7. View Yearly Summary");
				System.out.println("8. Exit");
				
				System.out.println("Enter your choice : ");
				
				int choice = sca.nextInt();
				sca.nextLine();
				
				switch (choice) {
					case 1: {
						try {
							addExpense(sca, categoryService, expenseService, loggedInUser);
							System.out.println("Expense added successfuly!");
						} catch (SQLException e) {
							System.out.println("Database error during adding the expense.");
						} catch (IllegalArgumentException e) {
				            System.out.println("Error: " + e.getMessage());
				        }
						
						break;
						
					}
					case 2: {
						try {
							updateExpense(sca, loggedInUser, expenseService, categoryService);
							System.out.println("Expense updated successfuly!");
						} catch (SQLException e) {
							System.out.println("Database error during updating the expense.");
						} catch (IllegalArgumentException e) {
				            System.out.println("Error: " + e.getMessage());
				        }
						
						break;
					}
					case 3: {
						try {
							deleteExpense(sca, loggedInUser, expenseService, categoryService);
							System.out.println("Expense deleted successfuly!");
						} catch (SQLException e) {
							System.out.println("Database error during deleting the expense.");
						} catch (IllegalArgumentException e) {
				            System.out.println("Error: " + e.getMessage());
				        }
						
						break;
					}
					case 4: {
						try {
							viewAllExpenses(loggedInUser, expenseService, categoryService);
						} catch (SQLException e) {
							System.out.println("Database error during retriving the expenses.");
						} catch (IllegalArgumentException e) {
				            System.out.println("Error: " + e.getMessage());
				        }
						break;
					}
					case 5: {
						try {
							viewByCategory(sca, loggedInUser, expenseService, categoryService);
						} catch (SQLException e) {
							System.out.println("Database error during retriving the expenses.");
						} catch (IllegalArgumentException e) {
				            System.out.println("Error: " + e.getMessage());
				        }
						break;
					}
					case 6: {
						try {
							viewMonthlySummary(sca, loggedInUser, expenseService, categoryService);
						} catch (SQLException e) {
							System.out.println("Database error during retriving the expenses.");
						} catch (IllegalArgumentException e) {
				            System.out.println("Error: " + e.getMessage());
				        }
						break;
					}
					case 7: {
						try {
							viewyearlySummary(sca, loggedInUser, expenseService, categoryService);
						} catch (SQLException e) {
							System.out.println("Database error during retriving the expenses.");
						} catch (IllegalArgumentException e) {
				            System.out.println("Error: " + e.getMessage());
				        }
						break;
					}
					case 8: {
						System.out.println("Logged out.");
						return;
					}
					default: {
						System.out.println("Invalid choice " + choice +" . Please try again.");
					}
				}
			} catch (Exception e) {
				System.out.println("Something went wrong. Please try again.");
			}
		}
	}
	
	private static void addExpense(Scanner sca,CategoryService categoryService, ExpenseService expenseService, User loggedInUser) throws Exception {
		System.out.println("Enter the Expense : ");
		double expense = sca.nextDouble();
		
		List<Category> categories = categoryService.getAllCategories();
		for(Category c : categories) {
			System.out.println(c.getCategoryID() + ". " + c.getCategoryName());
		}
		System.out.println("Enter the Category : ");
		int category = sca.nextInt();
		
		System.out.println("Enter the Date[yyyy-MM-dd] : ");
		String date = sca.next();
		sca.nextLine();
		
		System.out.println("Enter the Description : ");
		String description = sca.nextLine();
		
		int user = loggedInUser.getUserID();
		
		expenseService.addExpense(expense, category, date, description, user);
	}
	
	private static void updateExpense(Scanner sca, User loggedInUser, ExpenseService expenseService, CategoryService categoryService) throws Exception {
		viewAllExpenses(loggedInUser, expenseService, categoryService);
		
		System.out.println("Enter the expense ID you need to modify : ");
		int id = sca.nextInt();
		
		System.out.println("Enter the Expense : ");
		double expense = sca.nextDouble();
		
		List<Category> categories = categoryService.getAllCategories();
		for(Category c : categories) {
			System.out.println(c.getCategoryID() + ". " + c.getCategoryName());
		}
		System.out.println("Enter the Category : ");
		int category = sca.nextInt();
		
		System.out.println("Enter the Date[yyyy-MM-dd] : ");
		String date = sca.next();
		sca.nextLine();
		
		System.out.println("Enter the Description : ");
		String description = sca.nextLine();
		
		expenseService.updateExpense(id, expense, category, date, description, loggedInUser);
	}
	
	private static void deleteExpense(Scanner sca, User loggedInUser, ExpenseService expenseService, CategoryService categoryService) throws Exception {
		viewAllExpenses(loggedInUser, expenseService, categoryService);
		
		System.out.println("Enter the expense ID you need to delete : ");
		int id = sca.nextInt();
		
		expenseService.deleteExpense(id, loggedInUser);
	}
	
	private static void viewAllExpenses(User loggedInUser, ExpenseService expenseService, CategoryService categoryService) throws Exception {
		List<Expense> expenses = expenseService.viewAllExpenses(loggedInUser);
		
		if(expenses.isEmpty()) {
			System.out.println("You don't have any expense data.");
			return;
		}
		
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Expenses");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		for(Expense e : expenses) {
			System.out.println("ExpenseID : " + e.getExpenseID() + " | Amount : " + e.getAmount() + " | Category : " + categoryService.getCategoryName(e.getCategory()) + " | Date : " + e.getExpenseDate() + " | Description : " + e.getDescription() + " | User : " + loggedInUser.getUsername());
		}
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	private static void viewByCategory(Scanner sca, User loggedInUser, ExpenseService expenseService, CategoryService categoryService) throws Exception {
		List<Category> categories = categoryService.getAllCategories();
		for(Category c : categories) {
			System.out.println(c.getCategoryID() + ". " + c.getCategoryName());
		}
		System.out.println("Enter the Category ID : ");
		int categoryID = sca.nextInt();
		
		List<Expense> expenses = expenseService.viewByCategory(loggedInUser, categoryID);
		
		if(expenses.isEmpty()) {
			System.out.println("You don't have any expense data under " + categoryService.getCategoryName(categoryID) + " category.");
			return;
		}
		
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(categoryService.getCategoryName(categoryID) + " CATEGORY :");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		for(Expense e : expenses) {
			System.out.println("ExpenseID : " + e.getExpenseID() + " | Amount : " + e.getAmount() + " | Category : " + categoryService.getCategoryName(e.getCategory()) + " | Date : " + e.getExpenseDate() + " | Description : " + e.getDescription() + " | User : " + loggedInUser.getUsername());
		}
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	private static void viewMonthlySummary(Scanner sca, User loggedInUser, ExpenseService expenseService, CategoryService categoryService) throws Exception {
		System.out.println("Enter the Month[MM] : ");
		int month = sca.nextInt();
		
		System.out.println("Enter the Year[yyyy] : ");
		int year = sca.nextInt();
		
		HashMap<Integer, Double> monthlySummary = expenseService.viewMonthlySummary(month, year, loggedInUser);
		
		if(monthlySummary.isEmpty()) {
			System.out.println("You don't have any expense data in this month.");
			return;
		}
		
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Monthly Summary");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		for(Map.Entry<Integer, Double> entry : monthlySummary.entrySet()) {
			System.out.println("Category : " + categoryService.getCategoryName(entry.getKey()) + " | Amount : " + entry.getValue());
		}
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	private static void viewyearlySummary(Scanner sca, User loggedInUser, ExpenseService expenseService, CategoryService categoryService) throws Exception {
		
		System.out.println("Enter the Year[yyyy] : ");
		int year = sca.nextInt();
		
		HashMap<Integer, Double> yearlySummary = expenseService.viewYearlySummary(year, loggedInUser);
		
		if(yearlySummary.isEmpty()) {
			System.out.println("You don't have any expense data in this year.");
			return;
		}
		
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Yearly Summary");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		for(Map.Entry<Integer, Double> entry : yearlySummary.entrySet()) {
			System.out.println("Category : " + categoryService.getCategoryName(entry.getKey()) + " | Amount : " + entry.getValue());
		}
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
}
