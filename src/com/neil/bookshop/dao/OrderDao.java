package com.neil.bookshop.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neil.bookshop.domain.Order;
import com.neil.bookshop.domain.OrderItem;
import com.neil.bookshop.domain.Product;
import com.neil.bookshop.util.C3P0Util;
import com.neil.bookshop.util.ManagerThreadLocal;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


public class OrderDao {
	// 添加定单
	public void addOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		qr.update(ManagerThreadLocal.getConnection(),
				"INSERT INTO t_order VALUES(?,?,?,?,?,?,?,?)", order.getId(), order.getMoney(),
				order.getReceiverAddress(), order.getReceiverName(), order.getReceiverPhone(),
				order.getPayStatus(), order.getOrderTime(), order.getUser().getId());
	}

	// 根据用户id查询所有定单
	public List<Order> findOrders(int userId) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from t_order where user_id=?", new BeanListHandler<>(Order.class), userId);
	}

	// 查询指定用户的定单信息
	public Order findOrdersByOrderId(String orderId) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		// 得到一个定单
		Order order = qr.query("select * from t_order where id=?", new BeanHandler<>(Order.class), orderId);

		/** 基本方法 **/
		/*//得到当前定单中的所有定单项
		List<OrderItem> orderItems =qr.query("select * from orderItem where order_id=? ", new BeanListHandler<OrderItem>(OrderItem.class),orderid);
		//得到所有定单项中的商品信息
		List<Product> products = qr.query("select * from products where id in(select product_id from orderitem where order_id=?)", new BeanListHandler<Product>(Product.class),orderid);*/

		/** 升级方法 **/
		List<OrderItem> orderItems = qr.query("select * from t_order_item o left join t_product p on o.product_id = p.id where order_id=?",
			//new ResultSetHandler<List<OrderItem>>() {
			//替换为lambada
			rs -> {
				List<OrderItem> orderItems1 = new ArrayList<>();
				while (rs.next()) {
					//这里只包装buyNum,name,price三个字段,因为返回的页面就用这三个字段

					// 封装OrderItem对象
					OrderItem oi = new OrderItem();
					oi.setBuyNum(rs.getInt("buy_num"));
					// 封装Product对象
					Product p = new Product();
					p.setName(rs.getString("name"));
					p.setPrice(rs.getDouble("price"));
					// 把每个p对象封装到OrderItem对象中
					oi.setProduct(p);
					// 把每个OrderItem对象封装到集合中
					orderItems1.add(oi);
				}
				return orderItems1;
			}, orderId);
		// 把所有的定单项orderItems添加到主单对象Order中
		order.setOrderItems(orderItems);
		return order;
	}

	// 修改定单支付状态
	public void modifyOrderStatus(String orderId) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update t_order set pay_status=1 where id=?", orderId);
	}
}
