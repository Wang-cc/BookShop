package com.neil.bookshop.web.filter;

import com.neil.bookshop.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/*")
//Servlet3.0后使用注解则按照类名的自然顺序，即类名的字母顺序来排~因为容器加载时按此顺序加载~
//Servlet3.0之前使用web.xml配置按照mapping的顺序即先映射的先过滤
//这里使用前者
/**
 * @Author: Neil
 * @Date: 2019/11/4
 */
public class LoginFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath();
        System.out.println("请求路径为：" + servletPath);
        if (servletPath.contains("login") || servletPath.contains("Login") || servletPath.contains("user") ||
                servletPath.contains("register") || servletPath.contains("Register") || servletPath.contains("/imageCode")) {
            chain.doFilter(req, resp);
            return;
        }
        if (servletPath.endsWith(".js") || servletPath.endsWith(".jpg") || servletPath.endsWith(".png") || servletPath.endsWith(".gif") ||
                servletPath.endsWith(".css") || servletPath.endsWith(".ttf") || "".equals(servletPath) || "/".equals(servletPath)) {
            chain.doFilter(req, resp);
            return;
        }
        User loginUser = (User) request.getSession().getAttribute("user");
        if (loginUser == null) {
            System.out.println("用户未登录============================");
            request.setAttribute("user_msg", "您好，请先登录！");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        } else {
            if (loginUser.getStatus() != 1) {
                System.out.println("用户未激活============================");
                request.setAttribute("user_msg", "您好，请激活账号并登录！");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
