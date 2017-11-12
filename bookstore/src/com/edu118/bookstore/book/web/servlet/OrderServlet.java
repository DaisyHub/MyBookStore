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
			request.setAttribute("msg", "��û�е�¼�Ļ��ǲ��ܿ�������Ӵ~");
			return "f:/jsps/body.jsp";
		}
		List<Order> orders= orderService.showOrders(user.getUid());
		request.setAttribute("orders", orders);
		return "f:/jsps/order/list.jsp";
	}

	/*
	 * ͨ��uid�����û��Ĺ��ﳵ�������ﳵ�е��鼮����orderitem������ӵ�����orders��ȥ
	 * �����Ӷ�����Ӧ�ðɸ��û��Ĺ��ﳵ���
	 */
	public String addOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "��û�е�¼�Ļ��ǲ��ܿ�������Ӵ~");
			return "f:/jsps/body.jsp";
		}
		//���ɶ������
		String oid = UUID.randomUUID().toString().replace("-", "");
		orderService.addOrder(user.getUid(), oid);
		//ͨ��������������Ҷ�����Ϣ
		Order order = orderService.findOrderByOid(oid);
		request.setAttribute("order", order);
		//��չ��ﳵ
		new CartService().clearCart(user.getUid());
		return "f:/jsps/order/desc.jsp";
	}

	
}
