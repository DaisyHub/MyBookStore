package com.edu118.bookstore.book.service;

import com.edu118.bookstore.book.dao.OrderDao;

public class OrderService {
	private OrderDao orderDao = new OrderDao();

	public void showCart() {
		orderDao.selectCart();
	}
}
