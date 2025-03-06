package com.gocile.shikesystem.filter;

import com.gocile.shikesystem.model.User;
import com.gocile.shikesystem.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

//这里不全部拦截的原因是方便swagger的请求，可以在调试完成之后改成"/*"
@WebFilter(urlPatterns = {"/login/*","/stu/*","/admin/*","/sadmin/*"}/*"/*"*/)
public class Filter implements jakarta.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jakarta.servlet.Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String url =  ((HttpServletRequest)servletRequest).getRequestURI();
        if("/login".equals(url)){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            String token = null;
            Cookie[] cookies = ((HttpServletRequest)servletRequest).getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                    }
                }
            }
            String id = null;
            String permission;
            try {
                User user = JwtUtil.verify(token,new User());
                id = user.getId();
                permission = user.getPermission();
            } catch (Exception e) {
                permission = "无权限";
            }

            if( !(permission == null) && ((url.startsWith("/admin")&&permission.equals("管理员"))||
                    (url.startsWith("/stu")&&permission.equals("学生"))||url.startsWith("/sadmin")
                    &&permission.equals("超级管理员")/*||true*/)){
                //如果请求与token携带的permission匹配，则放行，并向request里注入id
                servletRequest.setAttribute("id", id);
                filterChain.doFilter(servletRequest, servletResponse);
            }else {
                Map<String,String> map = new HashMap<>();
                map.put("errorMsg","未登录或登录过期");
                JSONObject jsonObject = new JSONObject(map);
                servletResponse.setContentType("application/json");
                servletResponse.setCharacterEncoding("utf-8");
                PrintWriter pw=servletResponse.getWriter();
                pw.write(jsonObject.toString());
                pw.flush();
                pw.close();
            }
        }
    }

    @Override
    public void destroy() {
        jakarta.servlet.Filter.super.destroy();
    }
}