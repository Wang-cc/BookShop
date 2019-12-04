package com.neil.bookshop.service;

import com.neil.bookshop.dao.OrderDao;
import com.neil.bookshop.dao.OrderItemDao;
import com.neil.bookshop.dao.ProductDao;
import com.neil.bookshop.domain.Order;
import com.neil.bookshop.exception.OrderException;
import com.neil.bookshop.util.ManagerThreadLocal;

import java.sql.SQLException;
import java.util.List;

public class OrderService {

	private OrderDao orderDao = new OrderDao();
	private OrderItemDao orderItemDao = new OrderItemDao();
	private ProductDao productDao = new ProductDao();

	public void addOrder(Order order){
		try {
			ManagerThreadLocal.startTransaction();
			orderDao.addOrder(order);
			orderItemDao.addOrderItem(order);
			productDao.updateProductNum(order);
			ManagerThreadLocal.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			ManagerThreadLocal.rollback();
		}
	}

	public List<Order> findOrdersByUserId(int id) {
		try {
			return orderDao.findOrders(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Order findOrdersByOrderId(String orderId) {
		try {
			return orderDao.findOrdersByOrderId(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public void modifyOrderState(String orderId) throws OrderException {
		try {
			orderDao.modifyOrderStatus(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OrderException("修改订单状态失败");
		}
	}
}
