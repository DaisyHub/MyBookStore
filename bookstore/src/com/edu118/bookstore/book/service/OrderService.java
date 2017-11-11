package com.edu118.bookstore.book.service;

import java.util.List;

import com.edu118.bookstore.book.dao.BookDao;
import com.edu118.bookstore.book.dao.OrderDao;
import com.edu118.bookstore.book.domain.Book;
import com.edu118.bookstore.book.domain.Cart;

public class OrderService {
	private OrderDao orderDao = new OrderDao();

	public List<Cart> showCart() {
		return orderDao.selectCart();
	}

	public void addToCart(String bid, Integer count, String uid) {
		/*
		 * 1.ͨ��bid���ҵ��ñ����������Ϣ
		 * 2.����Ϣ���ϣ���ӽ�orderItem��
		 */
		//BookDao�������������������OrderDao������д��
		Book book = new BookDao().selectBookById(bid);	
		//�����book�Ѿ������ڹ��ﳵ�У���ôֻ��Ҫ�ı������ͼ۸�
		Cart cart = orderDao.findBookInCart(bid);
		if(cart != null){
			orderDao.changeBookCount(book, cart, count);
		}else{
			orderDao.insertBook(book, count, uid);			
		}
	}

	public void delBook(String bid) {
		orderDao.delBookFromCart(bid);
	}
	//��չ��ﳵ
	public void clearCart() {
		orderDao.deleteCart();
		
	}
}
