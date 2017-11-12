package com.edu118.bookstore.book.service;

import java.util.List;

import com.edu118.bookstore.book.dao.BookDao;
import com.edu118.bookstore.book.dao.CartDao;
import com.edu118.bookstore.book.domain.Book;
import com.edu118.bookstore.book.domain.Cart;

public class CartService {
	private CartDao cartDao = new CartDao();
	
	//查看购物车
	public List<Cart> showCart(String uid) {
		List<Cart> cart = cartDao.selectCart(uid);
		return cartDao.selectBookCart(cart);
	}

	public void addToCart(String bid, Integer count, String uid) {
		/*
		 * 1.通过bid查找到该本书的所有信息
		 * 2.将信息整合，添加进orderItem表
		 */
		//BookDao中有这个方法，不再在OrderDao中重新写了
		Book book = new BookDao().selectBookById(bid);	
		//如果该book已经存在在购物车中，那么只需要改变数量和价格
		Cart cart = cartDao.findBookInCart(bid, uid);
		if(cart != null){
			cartDao.changeBookCount(book, cart, count);
		}else{
			cartDao.insertBook(book, count, uid);			
		}
	}

	public void delBook(String caid) {
		cartDao.delBookFromCart(caid);
	}
	//清空购物车
	public void clearCart(String uid) {
		cartDao.deleteCart(uid);
		
	}
}
