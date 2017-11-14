package com.edu118.bookstore.admin.web.servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu118.bookstore.admin.service.AdminOrderService;
import com.edu118.bookstore.book.domain.Order;
import com.edu118.user.web.servlet.BaseServlet;

public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	private AdminOrderService adminOrderService = new AdminOrderService();

	public String listOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Order> orderList = adminOrderService.listOrders();
		request.setAttribute("orderList", orderList);
		return "f:/adminjsps/admin/order/list.jsp";
	}
	public String listOrdersByState(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer state = Integer.parseInt(request.getParameter("state"));
		List<Order> orderList = adminOrderService.listOrderByState(state);
		request.setAttribute("orderList", orderList);
		return "f:/adminjsps/admin/order/list.jsp";
	}
}
