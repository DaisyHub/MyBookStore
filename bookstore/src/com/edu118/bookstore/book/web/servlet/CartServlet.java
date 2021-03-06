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
   
	//显示购物车的信息
	public String showCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "想加入购物车吗，那么先登录吧");
			return "f:/jsps/body.jsp";
		}
		List<Cart> carts = cartService.showCart(user.getUid());
		
		Double allMoney = 0.0d;
		for (Cart cart : carts) {
			allMoney += cart.getSubtotal();
		}
		if(carts.isEmpty()){
			request.setAttribute("msg", "购物车里空空如也,快去买点东西吧");
		}
		request.setAttribute("allMoney", allMoney);
		request.setAttribute("carts", carts);
		return "f:/jsps/cart/list.jsp";
	}

	//将书籍添加进购物车，要添加信息进usercart表
	public String addToCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//得到该书籍的id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "想加入购物车吗，那么先登录吧");
			return "f:/jsps/body.jsp";
		}
		String bid = request.getParameter("bid");//book id
		Integer count = Integer.parseInt(request.getParameter("count"));//数量
		cartService.addToCart(bid, count, user.getUid());		
		return showCart(request, response);
	}
	
	//从购物车里删除书籍
	public String delBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String caid = request.getParameter("caid");
		cartService.delBook(caid);
		return showCart(request, response);
	}
	
	//清空购物车,通过uid删除
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		cartService.clearCart(user.getUid());
		request.setAttribute("msg", "购物车里空空如也,快去买点东西吧");
		return "f:/jsps/cart/list.jsp";
	}
}
