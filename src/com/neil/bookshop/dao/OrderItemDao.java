package com.neil.bookshop.dao;

import java.sql.SQLException;
import java.util.List;

import com.neil.bookshop.domain.Order;
import com.neil.bookshop.domain.OrderItem;
import com.neil.bookshop.util.ManagerThreadLocal;
import org.apache.commons.dbutils.QueryRunner;

public class OrderItemDao {
	//添加定单项
	public void addOrderItem(Order order) throws SQLException{
		//得到所有定单项的集合
		List<OrderItem> orderItems = order.getOrderItems();
		QueryRunner qr = new QueryRunner();
		Object[][] params = new Object[orderItems.size()][];
		for (int i = 0; i < params.length; i++) {
			//数组中的第一个参数代表主单id， 第二个参数：商品id 第三个参数 ：商品的购买数量
			params[i] = new Object[]{order.getId(),orderItems.get(i).getProduct().getId(),orderItems.get(i).getBuyNum()};
		}
		//批量插入 order_item
		qr.batch(ManagerThreadLocal.getConnection(),"INSERT INTO t_order_item VALUES(?,?,?)", params);
	}
}
