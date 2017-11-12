package com.edu118.bookstore.book.service;

import java.util.List;

import com.edu118.bookstore.book.dao.BookDao;
import com.edu118.bookstore.book.dao.CartDao;
import com.edu118.bookstore.book.domain.Book;
import com.edu118.bookstore.book.domain.Cart;

public class CartService {
	private CartDao cartDao = new CartDao();
	
	//�鿴���ﳵ
	public List<Cart> showCart(String uid) {
		List<Cart> cart = cartDao.selectCart(uid);
		return cartDao.selectBookCart(cart);
	}

	public void addToCart(String bid, Integer count, String uid) {
		/*
		 * 1.ͨ��bid���ҵ��ñ����������Ϣ
		 * 2.����Ϣ���ϣ���ӽ�orderItem��
		 */
		//BookDao�������������������OrderDao������д��
		Book book = new BookDao().selectBookById(bid);	
		//�����book�Ѿ������ڹ��ﳵ�У���ôֻ��Ҫ�ı������ͼ۸�
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
	//��չ��ﳵ
	public void clearCart(String uid) {
		cartDao.deleteCart(uid);
		
	}
}
