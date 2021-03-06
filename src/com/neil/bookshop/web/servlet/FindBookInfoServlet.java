package com.neil.bookshop.web.servlet;

import com.neil.bookshop.domain.Product;
import com.neil.bookshop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/findBookInfoServlet")
public class FindBookInfoServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		ProductService bs = new ProductService();
		Product book = bs.findBookById(id);
		request.setAttribute("b", book);
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
