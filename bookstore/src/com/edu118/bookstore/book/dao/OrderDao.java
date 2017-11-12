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
import com.edu118.bookstore.book.domain.Order;
import com.edu118.bookstore.book.domain.OrderItem;
import com.edu118.util.JdbcUtils;

public class OrderDao {
	protected String findOrders = "SELECT * FROM orders where uid=?";
	protected String findOrder = "select * from orders where oid=?";
	protected String findOrderItem = "select * from orderitem where oid=?";
	protected String findBookByBid = "select * from book where bid=?";
	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());

	public List<Order> selectOrders(String uid) {
		try {
			return qr.query(findOrders, new BeanListHandler<Order>(Order.class), uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 通过Order的oid找到orderitem
	public List<Order> findOrderItem(List<Order> orders) {
		List<Order> orderList = new ArrayList<Order>();

		try {
			for (Order order : orders) {
				List<OrderItem> orderItem = new ArrayList<OrderItem>();
				// 得到该订单的订单条目
				List<OrderItem> items = qr.query(findOrderItem, new BeanListHandler<OrderItem>(OrderItem.class),
						order.getOid());
				for (OrderItem item : items) {
					List<Book> book = new ArrayList<Book>();
					// 得到该条目下的所有书籍
					List<Book> books = qr.query(findBookByBid, new BeanListHandler<Book>(Book.class), item.getBid());
					for (Book b : books) {
						book.add(b);
						// item.setBook(b);
					}
					item.setBooklist(book);
					orderItem.add(item);
				}
				order.setOrderitem(orderItem);
				orderList.add(order);

				// System.out.println(order);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void insertOrder(String oid, String ordertime, int total, String uid) {
		Integer state = 1;
		String insertOrder = "insert into orders(oid,ordertime,total,state,uid) values(?,?,?,?,?)";
		Object[] params = { oid, ordertime, total, state, uid };
		try {
			qr.update(insertOrder, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void insertOrderItem(List<Cart> cartInfo, String oid) {
		String insertOrderItem = "insert into orderitem values(?,?,?,?,?)";
		for (Cart cart : cartInfo) {
			String iid = UUID.randomUUID().toString().replace("-", "");
			Object[] params = { iid, cart.getCount(), cart.getSubtotal(), oid, cart.getBid() };
			try {
				qr.update(insertOrderItem, params);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public Order selectOrderByOid(String oid) {
		try {
			Order order = qr.query(findOrder, new BeanHandler<Order>(Order.class), oid);
			List<OrderItem> orderItem = new ArrayList<OrderItem>();
			// 通过oid找orderitem
			List<OrderItem> items = qr.query(findOrderItem, new BeanListHandler<OrderItem>(OrderItem.class), oid);
			for (OrderItem item : items) {
				List<Book> book = new ArrayList<Book>();
				// 得到该条目下的所有书籍
				List<Book> books = qr.query(findBookByBid, new BeanListHandler<Book>(Book.class), item.getBid());
				for (Book b : books) {
					book.add(b);
				}
				item.setBooklist(book);
				orderItem.add(item);
			}
			order.setOrderitem(orderItem);
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
