package com.edu118.bookstore.book.web.servlet;

import com.edu118.bookstore.book.domain.Order;
import com.edu118.bookstore.book.service.CartService;
import com.edu118.bookstore.book.service.OrderService;
import com.edu118.bookstore.user.domain.User;
import com.edu118.user.web.servlet.BaseServlet;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
      
	private OrderService orderService = new OrderService();
   
	public String showOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "还没有登录的话是不能看订单的哟~");
			return "f:/jsps/body.jsp";
		}
		List<Order> orders= orderService.showOrders(user.getUid());
		request.setAttribute("orders", orders);
		return "f:/jsps/order/list.jsp";
	}

	/*
	 * 通过uid查找用户的购物车，将购物车中的书籍生成orderitem，并添加到订单orders中去
	 * 完成添加订单都应该吧该用户的购物车清空
	 */
	public String addOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "还没有登录的话是不能看订单的哟~");
			return "f:/jsps/body.jsp";
		}
		//生成订单编号
		String oid = UUID.randomUUID().toString().replace("-", "");
		orderService.addOrder(user.getUid(), oid);
		//通过订单编号来查找订单信息
		Order order = orderService.findOrderByOid(oid);
		request.setAttribute("order", order);
		//清空购物车
		new CartService().clearCart(user.getUid());
		return "f:/jsps/order/desc.jsp";
	}

	
}
