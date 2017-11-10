package com.edu118.bookstore.user.service;

import com.edu118.bookstore.user.dao.UserDao;
import com.edu118.bookstore.user.domain.User;

public class UserService {

	private UserDao userDao = new UserDao();

	public void registerUser(User userForm) {
		//�����ݿ��в���username �� Email,���Ƿ���ڣ������ڣ����޷�ע��
		User user1 = userDao.findUserByUName(userForm.getUsername());
		User user2 = userDao.findUserByEmail(userForm.getEmail());
		
		if(user1 != null) throw new RuntimeException("���û����Ѿ�����,��������ע���");
		if(user2 != null) throw new RuntimeException("�������ѱ�ע�ᣬ��������");
		
		//���ݿⲻ���ڸ�username��email������ע��		
		userDao.insertUser(userForm);
		
	}

	public User activeUser(String code) {
		User user = userDao.findUserByCode(code);
		
		//����û��ע��
		if(user == null) throw new RuntimeException("�������������ûע�ᣬ��������㣡");
		//�Ѽ���
		if(user.getState() == 1) throw new RuntimeException("�Ѿ�������ˣ���Ҫ�ٸ�������");
		//��δ�������״̬��
		userDao.updateStateByCode(code);
		return user;
	}

	public User checkUser(String username, String password) {
		User haveUser = userDao.haveUser(username, password);
		if(haveUser == null) throw new RuntimeException("û���û���Ϣ�������û����������Ƿ����");
		if(haveUser.getState() != 1) throw new RuntimeException("�˻���û�м�����ȵ����伤��");
		return haveUser;
	}
}
