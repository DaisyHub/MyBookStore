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
		if(category == null) throw new RuntimeException("û���κ��鼮�ķ���");
		return category;
	}

	public void modCategory(String cid, String cname) {
		categoryDao.updateCate(cid, cname);
	}

	//ɾ������ʱҪ���÷����µ��鼮һ��ɾ��
	public void delCategory(String cid) {
		//����
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
			throw new RuntimeException("û��ɾ���ɹ�");
		}
	}
}
