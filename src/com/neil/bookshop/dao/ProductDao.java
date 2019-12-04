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
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


public class ProductDao {
	//修改商品数量
	public void updateProductNum(Order order) throws SQLException{
		List<OrderItem> orderItems = order.getOrderItems();
		QueryRunner qr = new QueryRunner();
		Object[][] params = new Object[orderItems.size()][];
		for (int i = 0; i < params.length; i++) {
			params[i] = new Object[]{orderItems.get(i).getBuyNum(),orderItems.get(i).getProduct().getId()};
		}
		//根据订单 更新商品数量
		qr.batch(ManagerThreadLocal.getConnection(),"update t_product set product_num=product_num-? where id=?", params);
	}

	/**
	 * 查找所有图书
	 * @return
	 * @throws SQLException
	 */
	public List<Product> findAllBooks() throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from t_product", new BeanListHandler<>(Product.class));
	}

	/**
	 * 添加图书信息
	 * @param product
	 * @throws SQLException
	 */
	public void addBook(Product product) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("INSERT INTO t_product VALUES(?,?,?,?,?,?,?)",product.getId(),product.getName(),product.getPrice(),product.getProductNum(),product.getCategory(),product.getDescription(),product.getImgUrl());
	}

	/**
	 *
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Product findBookById(String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from t_product where id=?", new BeanHandler<>(Product.class),id);
	}

	/**
	 * 修改图书信息
	 * @param product
	 * @throws SQLException
	 */
	public void updateBook(Product product) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update t_product set name=?,price=?,product_num=?,category=?,description=? where id=?",
				product.getName(),product.getPrice(),product.getProductNum(),product.getCategory(),product.getDescription(),product.getId());


	}
	/**
	 * 根据id删除图书
	 * @param id
	 * @throws SQLException
	 */
	public void delBook(String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("delete from t_product where id=?",id);
	}

	/**
	 * 批量删除
	 * @param ids
	 * @throws SQLException
	 */
	public void deleAllBooks(String[] ids) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		Object[][] params = new Object[ids.length][];
		for (int i = 0; i < params.length; i++) {
			params[i] = new Object[]{ids[i]};//循环给每个一维数组中的元素赋值，值是id
		}
		//批量删除商品
		qr.batch("delete from t_product where id=?", params);
	}

	/**
	 * 多条件查询图书
	 * @param id
	 * @param category
	 * @param name
	 * @param minPrice
	 * @param maxPrice
	 * @return
	 * @throws SQLException
	 */
	public List<Product> searchBooks(String id, String category, String name, String minPrice, String maxPrice) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select * from t_product where 1=1";
		List<String> list = new ArrayList<>();
		if(!"".equals(id.trim())){
			sql+=" and id like ?"; //  不能在这写%   %'1002'%
			list.add("%"+id.trim()+"%");// '%1002%'
		}

		if(!"".equals(category.trim())){
			sql+=" and category=?";
			list.add(category.trim());
		}

		if(!"".equals(name.trim())){
			sql+=" and name like ?";
			list.add("%"+name.trim()+"%");
		}

		if(!"".equals(minPrice.trim())){
			sql+=" and price>?";
			list.add(minPrice.trim());
		}
		if(!"".equals(maxPrice.trim())){
			sql+=" and price<?";
			list.add(maxPrice.trim());
		}
		return qr.query(sql, new BeanListHandler<>(Product.class),list.toArray());
	}

	/**
	 * 得到总记录数
	 * @return
	 * @throws SQLException
	 */
	public int count(String category) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql ="select count(*) from t_product";
		//如果category不是空，就把条件加上
		if(!"".equals(category)){
			sql+=" where category='"+category+"'";
		}
		long l =  (Long)qr.query(sql, new ScalarHandler(1));
		return (int)l;
	}

	/**
	 * 查找分页数据
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public List<Product> findBooks(int currentPage, int pageSize, String category) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select * from t_product where 1=1";
		List list = new ArrayList();
		if(!"".equals(category)){
			sql+=" and category=?";
			list.add(category);
		}
		sql+=" limit ?,?";
		// select * from products where 1=1 and category=? limit ?,?;
		list.add((currentPage-1)*pageSize);
		list.add(pageSize);
		return qr.query(sql, new BeanListHandler<>(Product.class),list.toArray());
	}

	/**
	 * 根据书名查找图书 模糊查询
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public List<Object> searchBookByName(String name) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select name from t_product where name like ?", new ColumnListHandler(),"%"+name+"%");
	}
}
