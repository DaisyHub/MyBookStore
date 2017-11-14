package com.edu118.bookstore.admin.service;

import java.util.List;

import com.edu118.bookstore.admin.dao.AdminOrderDao;
import com.edu118.bookstore.book.domain.Order;

public class AdminOrderService {

	private AdminOrderDao adminOrderDao = new AdminOrderDao();

	public List<Order> listOrders() {
		return adminOrderDao.findOrders();
	}

	public List<Order> listOrderByState(Integer state) {
		
		return adminOrderDao.findOrdersByState(state);
	}
}
