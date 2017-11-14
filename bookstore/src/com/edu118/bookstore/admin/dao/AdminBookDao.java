package com.edu118.bookstore.admin.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.edu118.bookstore.book.domain.Book;
import com.edu118.util.JdbcUtils;

public class AdminBookDao {

	protected String selectAllBook = "select * from book where state=1";
	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
	
	//查找所有的图书
	public List<Book> selectAllBook() {		
		try {
			return qr.query(selectAllBook, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteBook(String bid) {
		String deleteBook = "update book set state=0 where bid=?";
		try {
			qr.update(deleteBook, bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	//修改图书信息
	public void updateBook(Book book) {
		String udpateBook = "update book set bname=?,price=?,author=?,cid=? where bid=?";
		Object[] params = {
				book.getBname(),book.getPrice(),book.getAuthor(),
				book.getCid(),book.getBid()
				};
		try {
			qr.update(udpateBook, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void insertBook(Book book) {
		String insertBook = "insert into book values(?,?,?,?,?,?,?)";
		Object[] params = {
				book.getState(),book.getBid(),book.getBname(),book.getPrice(),
				book.getAuthor(),book.getImage(),book.getCid()
		};
		try {
			qr.update(insertBook, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
