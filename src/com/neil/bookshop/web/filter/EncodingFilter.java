package com.neil.bookshop.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/*")
//Servlet3.0后使用注解则按照类名的自然顺序，即类名的字母顺序来排~因为容器加载时按此顺序加载~
//Servlet3.0之前使用web.xml配置按照mapping的顺序即先映射的先过滤
//这里使用前者
/**
 * 通用解决 get 和 post乱码过滤器
 * @Author: neil
 * @Date: 2019-10-28
 */
public class EncodingFilter implements Filter {

	private String encoding = "utf-8";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 处理请求乱码
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		httpServletRequest.setCharacterEncoding(encoding);
		// 处理响应乱码
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setContentType("text/html;charset=utf-8");
		chain.doFilter(httpServletRequest, httpServletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String initParameter = filterConfig.getInitParameter("encoding");
		if (initParameter != null) {
			encoding = initParameter;
		}
	}

}
