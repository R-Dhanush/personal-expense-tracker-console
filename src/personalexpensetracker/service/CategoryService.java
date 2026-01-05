package personalexpensetracker.service;

import java.util.List;

import personalexpensetracker.dao.CategoryDAO;
import personalexpensetracker.dao.DAOFactory;
import personalexpensetracker.model.Category;

public class CategoryService {
	
	private CategoryDAO categoryDAO;
	
	public CategoryService() {
		this.categoryDAO = DAOFactory.getCategoryDAO();
	}
	
	public List<Category> getAllCategories() throws Exception {
		return categoryDAO.getAllCategories();
	}
	
	public String getCategoryName(int categoryID) throws Exception {
		return categoryDAO.getCategoryName(categoryID);
	}
}
