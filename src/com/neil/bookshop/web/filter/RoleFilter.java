package com.neil.bookshop.web.filter;

import com.neil.bookshop.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RoleFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
						 FilterChain chain) throws IOException, ServletException {
		//强转
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		//处理业务
		//从session中把用户对象得到
		User user = (User) request.getSession().getAttribute("user");
		//判断当前用户权限
		if(user!=null){
			if(!"admin".equals(user.getRole())){
				response.getWriter().write("权限不足，你无法访问！");
				response.setHeader("refresh", "2;url="+request.getContextPath()+"/index.jsp");
				return;
			}
			//放行
			chain.doFilter(request, response);
		}
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
