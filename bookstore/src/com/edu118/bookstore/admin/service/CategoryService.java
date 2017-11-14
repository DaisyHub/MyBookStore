package com.edu118.bookstore.admin.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.edu118.bookstore.admin.dao.CategoryDao;
import com.edu118.bookstore.book.domain.Category;
import com.edu118.util.JdbcUtils;

public class CategoryService {

	private CategoryDao categoryDao = new CategoryDao();

	public List<Category> showCategory() {
		
		return categoryDao.selectCategory();
	}

	public void addCategory(String cname, String cid) {
		categoryDao.insertCategory(cname, cid);
	}

	public Category findCategory(String cid) {
		
		Category category = categoryDao.findCategory(cid);
		if(category == null) throw new RuntimeException("没有任何书籍的分类");
		return category;
	}

	public void modCategory(String cid, String cname) {
		categoryDao.updateCate(cid, cname);
	}

	//删除分类时要将该分类下的书籍一并删除
	public void delCategory(String cid) {
		//事务
		Connection conn  = null;
		try{
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			categoryDao.delBookByCid(conn, cid);
			categoryDao.delCate(conn, cid);
			conn.commit();
		}catch(Exception e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
			throw new RuntimeException("没能删除成功");
		}
	}
}
