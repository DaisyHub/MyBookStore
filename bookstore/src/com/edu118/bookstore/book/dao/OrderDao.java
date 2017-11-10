package com.edu118.bookstore.book.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.edu118.bookstore.book.domain.Cart;
import com.edu118.util.JdbcUtils;

public class OrderDao {
	protected String showCart = "SELECT book.image,book.bname,book.author,book.price,orderitem.count,"
			+ "orderitem.subtotal FROM book INNER JOIN orderitem ON book.bid=orderitem.bid";
	
	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());

	public void selectCart() {
		try {
			qr.query(showCart, new BeanHandler<Cart>(Cart.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
