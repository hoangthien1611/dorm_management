package com.doan.dormmanagement.utility;

import com.doan.dormmanagement.common.Constant;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SessionInfo {
    private String username;

    public String getCurrentUser(HttpServletRequest request) {
        if (username == null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                username = session.getAttribute(Constant.USER_INFO) != null ? (String) session.getAttribute(Constant.USER_INFO) : null;
            }
        }
        return username;
    }
}
