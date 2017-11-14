package com.edu118.bookstore.book.dao;

import java.sql.Connection;
import java.sql.SQLException;
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
	protected String findOrders = "SELECT * FROM orders where uid=? ORDER BY ordertime DESC";
	protected String findOrder = "select * from orders where oid=? ORDER BY ordertime DESC";
	protected String findOrderItem = "select * from orderitem where oid=?";
	protected String findBookByBid = "select * from book where bid=?";
	protected String insertOrder = "insert into orders(oid,ordertime,total,state,uid) values(?,?,?,?,?)";
	protected String insertOrderItem = "insert into orderitem values(?,?,?,?,?)";
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

		try {
			for (Order order : orders) {
				// 得到该订单的订单条目
				List<OrderItem> items = qr.query(findOrderItem, new BeanListHandler<OrderItem>(OrderItem.class),
						order.getOid());
				for (OrderItem item : items) {
					// 得到该条目下的所有书籍
					List<Book> books = qr.query(findBookByBid, new BeanListHandler<Book>(Book.class), item.getBid());
					item.setBooklist(books);
				}
				order.setOrderitem(items);
			}
			return orders;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void insertOrder(Connection conn, String oid, String ordertime, int total, String uid) {
		Integer state = 1;
	
		Object[] params = { oid, ordertime, total, state, uid };
		try {
			qr.update(conn, insertOrder, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void insertOrderItem(Connection conn, List<Cart> cartInfo, String oid) {
		
		for (Cart cart : cartInfo) {
			String iid = UUID.randomUUID().toString().replace("-", "");
			Object[] params = { iid, cart.getCount(), cart.getSubtotal(), oid, cart.getBid() };
			try {
				qr.update(conn, insertOrderItem, params);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public Order selectOrderByOid(String oid) {
		try {
			Order order = qr.query(findOrder, new BeanHandler<Order>(Order.class), oid);
			// 通过oid找orderitem
			List<OrderItem> items = qr.query(findOrderItem, new BeanListHandler<OrderItem>(OrderItem.class), oid);
			for (OrderItem item : items) {
				// 得到该条目下的所有书籍
				List<Book> books = qr.query(findBookByBid, new BeanListHandler<Book>(Book.class), item.getBid());
				item.setBooklist(books);
			}
			order.setOrderitem(items);
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	//state = 2;等待发货
	public void payOrder(String oid, String address) {
		String payOrder = "update orders set address=?,state=? where oid=?";
		Object[] params = {address, 2, oid};
		try {
			qr.update(payOrder, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	//确认收货
	public void confirmReceipt(String oid) {
		String confirmReceipt = "update orders set state=4 where oid=?";
		try {
			qr.update(confirmReceipt,oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
