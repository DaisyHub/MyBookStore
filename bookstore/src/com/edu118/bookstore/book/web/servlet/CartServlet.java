package com.edu118.bookstore.book.web.servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.edu118.bookstore.book.domain.Cart;
import com.edu118.bookstore.book.service.CartService;
import com.edu118.bookstore.user.domain.User;
import com.edu118.user.web.servlet.BaseServlet;


public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CartService cartService = new CartService();
   
	//��ʾ���ﳵ����Ϣ
	public String showCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "����빺�ﳵ����ô�ȵ�¼��");
			return "f:/jsps/body.jsp";
		}
		List<Cart> carts = cartService.showCart(user.getUid());
		
		Double allMoney = 0.0d;
		for (Cart cart : carts) {
			allMoney += cart.getSubtotal();
		}
		if(carts.isEmpty()){
			request.setAttribute("msg", "���ﳵ��տ���Ҳ,��ȥ��㶫����");
		}
		request.setAttribute("allMoney", allMoney);
		request.setAttribute("carts", carts);
		return "f:/jsps/cart/list.jsp";
	}

	//���鼮��ӽ����ﳵ��Ҫ�����Ϣ��usercart��
	public String addToCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//�õ����鼮��id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "����빺�ﳵ����ô�ȵ�¼��");
			return "f:/jsps/body.jsp";
		}
		String bid = request.getParameter("bid");//book id
		Integer count = Integer.parseInt(request.getParameter("count"));//����
		cartService.addToCart(bid, count, user.getUid());		
		return showCart(request, response);
	}
	
	//�ӹ��ﳵ��ɾ���鼮
	public String delBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String caid = request.getParameter("caid");
		cartService.delBook(caid);
		return showCart(request, response);
	}
	
	//��չ��ﳵ,ͨ��uidɾ��
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		cartService.clearCart(user.getUid());
		request.setAttribute("msg", "���ﳵ��տ���Ҳ,��ȥ��㶫����");
		return "f:/jsps/cart/list.jsp";
	}
}
