package com.edu118.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.edu118.bookstore.book.domain.Book;
import com.edu118.bookstore.book.domain.Category;
import com.edu118.util.JdbcUtils;

public class BookDao {
	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
	protected String findCategory = "select * from category";
	protected String selectAllCats = "select * from book";
	protected String selectBookByCid = "select * from book where cid=?";
	protected String selectBookById = "select * from book where bid=?";
	
	//�ҳ����е�ͼ�����
	public List<Category> findCategory() {
		try {
			return qr.query(findCategory, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//Ҫ���صĶ���Ӧ������
	public List<Book> selectAllCats() {
		try {
			return qr.query(selectAllCats, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	//���Ҳ�ͬ������
	public List<Book> selectBookByCid(String cid) {		
		try {
			return qr.query(selectBookByCid, new BeanListHandler<Book>(Book.class), cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Book selectBookById(String bid) {
		try {
			return qr.query(selectBookById, new BeanHandler<Book>(Book.class), bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
