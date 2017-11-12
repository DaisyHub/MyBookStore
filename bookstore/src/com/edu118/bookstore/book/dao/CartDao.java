package com.edu118.bookstore.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.edu118.bookstore.book.domain.Book;
import com.edu118.bookstore.book.domain.Cart;
import com.edu118.util.JdbcUtils;

public class CartDao {
	//���ﳵ���
	protected String showCart = "SELECT *from usercart where uid=?";
	protected String insertBook = "insert into usercart values(?,?,?,?,?)";
	protected String delBookFromCart = "delete from usercart where caid=?";
	protected String delCart = "delete from usercart where uid=?";
	protected String findBookInCart = "select * from usercart where bid=? and uid=?";
	protected String changeBookCount = "update usercart set count=count+?, subtotal=? where bid=?";
	
	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());

	//ͨ��uid�ҵ����û����еĹ��ﳵ����ʱ���ص�cart�л�û��Book��Ϣ
	public List<Cart> selectCart(String uid) {
		try {
			return qr.query(showCart, new BeanListHandler<Cart>(Cart.class), uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//��cart�м���Book����Ϣ
	public List<Cart> selectBookCart(List<Cart> cart) {
		List<Cart> carts = new ArrayList<Cart>();
		String findBooktoCart = "select * from book where bid=?";
		for(Cart c : cart){
			Book book;
			try {
				book = qr.query(findBooktoCart, new BeanHandler<Book>(Book.class), c.getBid());
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			c.setBook(book);
			carts.add(c);
		}
		return carts;
	}
	
	// ����鼮�����ﳵ��
	public void insertBook(Book book, Integer count, String uid) {
		String caid = UUID.randomUUID().toString().replace("-", "");
		Object[] params = {
				// uidΪ�û���ţ�����������Ϊ1���������˶����ٽ����޸�
				caid, count, book.getPrice() * count, book.getBid(),uid
				};
		try {
			qr.update(insertBook, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// �ӹ��ﳵɾ���鼮
	public void delBookFromCart(String caid) {
		try {
			qr.update(delBookFromCart, caid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ��չ��ﳵ
	public void deleteCart(String uid) {
		try {
			qr.update(delCart,uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//�ڹ��ﳵ�в����Ƿ��иñ���
	public Cart findBookInCart(String bid, String uid) {
		try {
			
			Cart cart = qr.query(findBookInCart, new BeanHandler<Cart>(Cart.class), bid,uid);			
			return cart;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//ֻ���Ĺ��ﳵ�������鼮������,�ͼ۸�
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
