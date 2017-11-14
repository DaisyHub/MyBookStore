package com.edu118.bookstore.admin.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.edu118.bookstore.book.domain.Book;
import com.edu118.bookstore.book.domain.Order;
import com.edu118.bookstore.book.domain.OrderItem;
import com.edu118.util.JdbcUtils;

public class AdminOrderDao {

	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
	//找到所有的订单
	public List<Order> findOrders() {
		String findOrders = "select * from orders";
		List<Order> orderList;
		try {
			orderList = qr.query(findOrders, new BeanListHandler<Order>(Order.class));
			return listOrders(orderList);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//通过状态找到订单
	public List<Order> findOrdersByState(Integer state) {
		String findOrders = "select * from orders where state=?";
		List<Order> orderList;
		try {
			orderList = qr.query(findOrders, new BeanListHandler<Order>(Order.class), state);
			return listOrders(orderList);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//通过找到的订单返回订单条目
	private List<Order> listOrders(List<Order> orderList) {
		String findOrderItem = "select * from orderitem where oid=?";
		String findBook = "select * from book where bid=?";
		try{
		for(Order order : orderList){
			List<OrderItem> orderItemList = qr.query(findOrderItem, new BeanListHandler<OrderItem>(OrderItem.class), order.getOid());
			for(OrderItem orderItem : orderItemList){
				List<Book> bookList = qr.query(findBook, new BeanListHandler<Book>(Book.class), orderItem.getBid());
				orderItem.setBooklist(bookList);
			}
			order.setOrderitem(orderItemList);
		}
		return orderList;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
}
