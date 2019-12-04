package com.neil.bookshop.dao;

import java.sql.SQLException;

import com.neil.bookshop.domain.User;
import com.neil.bookshop.util.C3P0Util;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;


public class UserDao {

	public void addUser(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "INSERT INTO t_user (username,password,gender,email,telephone,introduce,active_code,status,register_time) "
				+ "VALUES(?,?,?,?,?,?,?,?,?)";
		qr.update(sql, user.getUsername(), user.getPassword(),user.getGender(), user.getEmail(), user.getTelephone(),
				user.getIntroduce(), user.getActiveCode(), user.getStatus(), user.getRegisterTime());

	}

	// 根据激活码查找用户
	public User findUserByActiveCode(String activeCode) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from t_user where active_code=?",
				new BeanHandler<>(User.class), activeCode);
	}

	// 修改用户激活状态
	public void activeCode(String activeCode) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update t_user set state=1 where active_code=?", activeCode);
	}

	// 用户登录
	public User findUserByUserNameAndPassword(String username, String password)
			throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from t_user where username=? and password=?",
				new BeanHandler<>(User.class), username, password);
	}

	// 根据id查找用户
	public User findUserById(String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from t_user where id=?", new BeanHandler<>(User.class), id);
	}

	// 修改用户信息
	public void modifyUser(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update t_user set password=? , gender=?, telephone=? where id=?",user.getPassword(),user.getGender(),user.getTelephone(),user.getId());
	}
}
