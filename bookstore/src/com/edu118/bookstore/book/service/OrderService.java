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
		 * 1.ͨ��uid�ҵ����û������ж���
		 * 2.ͨ��oid�ҵ����е�orderitem
		 */
		return orderDao.findOrderItem(orders);
	}

	public void addOrder(String uid, String oid) {
		/*
		 * 1.ͨ��uid�ҵ�����˵Ĺ��ﳵ
		 * 2.���ɶ�����ţ�����ʱ��,�ܼۣ�uid����ַҪ��descҳ����غ������
		 * 3.�����ﳵ�����Ϣ���ϣ���ӵ�orderitem����ȥ
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
