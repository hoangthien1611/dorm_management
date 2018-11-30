package com.doan.dormmanagement.utility;

import com.doan.dormmanagement.common.Constant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestHandler extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String reqUri = request.getRequestURI();
        String serviceName = reqUri.substring(reqUri.lastIndexOf("/") + 1,
                reqUri.length());
        System.out.println(reqUri);
        if (!"login".equals(serviceName) && !"error".equals(serviceName) && !reqUri.contains("/admin/auth/")) {
            if (request.getSession(false) == null || request.getSession(false).getAttribute(Constant.USER_INFO) == null) {
                response.sendRedirect(request.getContextPath() + "/admin/login");
                return false;
            }
        }
        return true;
    }
}
