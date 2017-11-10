package com.edu118.bookstore.book.web.servlet;


import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu118.bookstore.book.domain.OrderItem;
import com.edu118.bookstore.book.service.OrderService;
import com.edu118.user.web.servlet.BaseServlet;
import com.edu118.utils.CommonUtils;


public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = new OrderService();
   
	public void showCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Map<String, String[]> map = request.getParameterMap();
		String bid = request.getParameter("bid");
		Integer count = Integer.parseInt(request.getParameter("count"));
		Double price = Double.parseDouble(request.getParameter("price"));
		
		OrderItem orderItem = CommonUtils.getBean(map, OrderItem.class);
		//设置订单主键
		orderItem.setIid(UUID.randomUUID().toString().replace("-", ""));
		orderItem.setSubtotal(count*price);
		orderItem.setBid(bid);
		//订单编号，先这样设置，有误稍后再改
		orderItem.setOid(UUID.randomUUID().toString().replace("-", ""));
		
		orderService.showCart();
	}

}
