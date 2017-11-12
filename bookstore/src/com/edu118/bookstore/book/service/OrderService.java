package com.edu118.bookstore.book.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.edu118.bookstore.book.dao.CartDao;
import com.edu118.bookstore.book.dao.OrderDao;
import com.edu118.bookstore.book.domain.Cart;
import com.edu118.bookstore.book.domain.Order;

public class OrderService {
	private OrderDao orderDao = new OrderDao();

	public List<Order> showOrders(String uid) {
		List<Order> orders = orderDao.selectOrders(uid);
		/*
		 * 1.通过uid找到该用户的所有订单
		 * 2.通过oid找到所有的orderitem
		 */
		return orderDao.findOrderItem(orders);
	}

	public void addOrder(String uid, String oid) {
		/*
		 * 1.通过uid找到这个人的购物车
		 * 2.生成订单编号，订单时间,总价，uid，地址要在desc页面加载后再添加
		 * 3.将购物车里的信息整合，添加到orderitem表中去
		 */
		List<Cart> cartInfo = new CartService().showCart(uid);
		int total = 0;
		for(Cart c : cartInfo){
			total += c.getSubtotal();
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ordertime = format.format(new Date());
		orderDao.insertOrder(oid, ordertime, total, uid);
		
		//orderitem:iid,count,subtotal,oid,bid		
		orderDao.insertOrderItem(cartInfo, oid);
	}

	public Order findOrderByOid(String oid) {
		return orderDao.selectOrderByOid(oid);
	}
}
