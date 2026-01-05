package personalexpensetracker.dao;

import java.sql.SQLException;
import java.util.List;

import personalexpensetracker.model.Category;

public interface CategoryDAO {
	List<Category> getAllCategories() throws SQLException;
	String getCategoryName(int categoryID) throws SQLException;
}
