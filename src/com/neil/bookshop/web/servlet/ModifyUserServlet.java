package com.neil.bookshop.web.servlet;

import com.neil.bookshop.domain.User;
import com.neil.bookshop.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/modifyUserServlet")
public class ModifyUserServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//封装表单数据
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			UserService us = new UserService();
			us.modifyUser(user);
			request.getSession().invalidate();//相当于注消用户
			response.sendRedirect(request.getContextPath()+"/modifyUserInfoSuccess.jsp");
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
