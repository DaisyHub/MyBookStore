package com.edu118.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.edu118.bookstore.book.domain.Book;
import com.edu118.bookstore.book.domain.Cart;
import com.edu118.util.JdbcUtils;

public class OrderDao {
	protected String showCart = "SELECT book.bid,book.image,book.bname,book.author,book.price,usercart.count,"
			+ "usercart.subtotal FROM book INNER JOIN usercart ON book.bid=usercart.bid";
	protected String insertBook = "insert into usercart values(?,?,?,?,?)";
	protected String delBookFromCart = "delete from usercart where bid=?";
	protected String delCart = "delete from usercart";
	protected String findBookInCart = "select * from usercart where bid=?";
	protected String changeBookCount = "update usercart set count=count+?, subtotal=? where bid=?";
	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());

	public List<Cart> selectCart() {
		try {
			return qr.query(showCart, new BeanListHandler<Cart>(Cart.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 添加书籍到购物车中
	public void insertBook(Book book, Integer count, String uid) {
		String caid = UUID.randomUUID().toString().replace("-", "");
		Object[] params = {
				// uid为用户编号，这里先设置为1，等做到了订单再进行修改
				caid, count, book.getPrice() * count, book.getBid(),uid
				};
		try {
			qr.update(insertBook, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 从购物车删除书籍
	public void delBookFromCart(String bid) {
		try {
			qr.update(delBookFromCart, bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 清空购物车
	public void deleteCart() {
		try {
			qr.update(delCart);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//在购物车中查找是否有该本书
	public Cart findBookInCart(String bid) {
		try {
			
			Cart cart = qr.query(findBookInCart, new BeanHandler<Cart>(Cart.class), bid);			
			return cart;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//只更改购物车中已有书籍的数量,和价格
	public void changeBookCount(Book book, Cart cart, Integer count) {
		try {
			Object[] params = {
					count, (cart.getCount()+count)*book.getPrice(),book.getBid()
			};
			
			qr.update(changeBookCount, params);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
