package personalexpensetracker.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import personalexpensetracker.model.Category;
import personalexpensetracker.util.DBConnection;

public class CategoryDAOImpl implements CategoryDAO {
	@Override
	public List<Category> getAllCategories() throws SQLException{
		
		List<Category> categories = new ArrayList<>();
		
		String query = "select category_id, category_name from categories";
		
		try (Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery()){
			
			while(rs.next()) {
				Category c = new Category(rs.getInt(1), rs.getString(2));
				categories.add(c);
			}
			
		}
		
		return categories;
	}
	
	@Override
	public String getCategoryName(int categoryID) throws SQLException {
		
		String query = "select category_name from categories where category_id = ?";
		
		try (Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query)){
			
			ps.setInt(1, categoryID);
			
			try (ResultSet rs = ps.executeQuery()) {
				
				if(rs.next()) {
					return rs.getString(1);
				}
			}
			
		}
		
		return null;
	}
}
