package com.neil.bookshop.domain;

/**
 * 订单详细
 */
public class OrderItem {
	private Order order;// 订单
	private Product product; // 商品
	private int buyNum; // 购物数量

	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

}
