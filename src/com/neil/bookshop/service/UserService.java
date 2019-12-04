package com.neil.bookshop.service;

import com.neil.bookshop.dao.UserDao;
import com.neil.bookshop.domain.User;
import com.neil.bookshop.exception.UserException;
import com.neil.bookshop.util.SendEmailMail;

import java.sql.SQLException;

public class UserService {

	private UserDao ud = new UserDao();

	public void regist(User user) throws UserException {
		try {
			//用户注册
			ud.addUser(user);
			//发送激活邮件
			String content = "注册成功，请<a href='http://neil.free.idcfengye.com/bookStore/activeServlet?activeCode="
					+ user.getActiveCode() + "'>激活</a>后登录";
			SendEmailMail.sendMail(user.getEmail(),"激活用户",content,"smtp","smtp.qq.com",
					"991529187@qq.com","587","991529187","elyepukrogkmbajg");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("注册失败！");
		}
	}

	public void activeUser(String activeCode) throws UserException {
		//根据激活码查找用户
		try {
			User user = ud.findUserByActiveCode(activeCode);
			if(user!=null){
				//激活用户
				ud.activeCode(activeCode);
				return;
			}
			throw new UserException("激活失败!");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("激活失败!");
		}
	}

	public User login(String username, String password) throws UserException {
		User user=null;
		try {
			user = ud.findUserByUserNameAndPassword(username,password);
			if(user==null){
				throw new UserException("用户名或密码错误!");
			}
			if(user.getStatus()==0){
				throw new UserException("用户未激活!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("用户名或密码错误!");
		}
		return user;
	}

	public User findUserById(String id) throws UserException {
		try {
			return ud.findUserById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("用户查找失败！");
		}
	}

	public void modifyUser(User user) throws UserException {
		try {
			ud.modifyUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("修改用户信息失败");
		}
	}

}
