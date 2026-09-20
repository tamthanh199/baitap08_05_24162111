package vn.hcmute.springboot.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.hcmute.springboot.entity.User;
import vn.hcmute.springboot.service.IUserService;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final String COOKIE_USERNAME = "cookie_username";

    private final IUserService userService;

    public LoginInterceptor(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        User currentUser = null;

        HttpSession session = request.getSession(false);

        if (session != null) {

            Object accountObject =
                    session.getAttribute("account");

            if (accountObject instanceof User account) {

                currentUser =
                        userService.getById(
                                account.getUserId()
                        );

                if (currentUser != null) {
                    session.setAttribute(
                            "account",
                            currentUser
                    );
                }
            }
        }

        if (currentUser == null) {

            String username =
                    getUsernameFromCookie(request);

            if (username != null) {

                User user =
                        userService.getByUsername(
                                username
                        );

                if (user != null) {

                    HttpSession newSession =
                            request.getSession(true);

                    newSession.setAttribute(
                            "account",
                            user
                    );

                    newSession.setMaxInactiveInterval(
                            30 * 60
                    );

                    currentUser = user;
                }
            }
        }

        if (currentUser == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login"
            );

            return false;
        }

        if (isAdminRequest(request)
                && !currentUser.isAdmin()) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Bạn không có quyền ADMIN để truy cập trang này."
            );

            return false;
        }

        return true;
    }

    private boolean isAdminRequest(
            HttpServletRequest request) {

        String contextPath =
                request.getContextPath();

        String requestURI =
                request.getRequestURI();

        String path =
                requestURI.substring(
                        contextPath.length()
                );

        return path.equals("/admin")
                || path.startsWith("/admin/");
    }

    private String getUsernameFromCookie(
            HttpServletRequest request) {

        Cookie[] cookies =
                request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {

            if (COOKIE_USERNAME.equals(
                    cookie.getName())) {

                return cookie.getValue();
            }
        }

        return null;
    }
}