package vn.hcmute.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.hcmute.springboot.entity.User;
import vn.hcmute.springboot.service.IUserService;

@Controller
public class LoginController {

    private static final String COOKIE_USERNAME =
            "cookie_username";

    private static final int COOKIE_MAX_AGE =
            30 * 60;

    private final IUserService userService;

    public LoginController(
            IUserService userService) {

        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(

            @RequestParam(
                    name = "registered",
                    required = false
            )
            String registered,

            HttpServletRequest request,

            HttpServletResponse response,

            Model model) {

        HttpSession session =
                request.getSession(false);

        if (session != null) {

            Object accountObject =
                    session.getAttribute(
                            "account"
                    );

            if (accountObject instanceof User user) {
                return redirectAfterLogin(user);
            }
        }

        String username =
                getUsernameFromCookie(request);

        if (username != null) {

            User user =
                    userService.getByUsername(
                            username
                    );

            if (user != null) {

                session =
                        request.getSession(true);

                session.setAttribute(
                        "account",
                        user
                );

                session.setMaxInactiveInterval(
                        30 * 60
                );

                return redirectAfterLogin(user);
            }

            deleteRememberCookie(
                    request,
                    response
            );
        }

        if ("true".equals(registered)) {

            model.addAttribute(
                    "successMessage",
                    "Đăng ký thành công. Vui lòng đăng nhập."
            );
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(

            @RequestParam(
                    name = "username",
                    defaultValue = ""
            )
            String username,

            @RequestParam(
                    name = "password",
                    defaultValue = ""
            )
            String password,

            @RequestParam(
                    name = "remember",
                    required = false
            )
            String remember,

            HttpServletRequest request,

            HttpServletResponse response,

            Model model) {

        username = username.trim();

        model.addAttribute(
                "username",
                username
        );

        if (username.isEmpty()
                || password.isEmpty()) {

            model.addAttribute(
                    "message",
                    "Vui lòng nhập tài khoản và mật khẩu."
            );

            return "login";
        }

        User user =
                userService.login(
                        username,
                        password
                );

        if (user == null) {

            model.addAttribute(
                    "message",
                    "Tài khoản hoặc mật khẩu không đúng."
            );

            return "login";
        }

        HttpSession session =
                request.getSession(true);

        session.setAttribute(
                "account",
                user
        );

        session.setMaxInactiveInterval(
                30 * 60
        );

        if ("on".equals(remember)) {

            saveRememberCookie(
                    request,
                    response,
                    user.getUsername()
            );

        } else {

            deleteRememberCookie(
                    request,
                    response
            );
        }

        return redirectAfterLogin(user);
    }

    @GetMapping("/logout")
    public String logout(

            HttpServletRequest request,

            HttpServletResponse response) {

        HttpSession session =
                request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        deleteRememberCookie(
                request,
                response
        );

        return "redirect:/login";
    }

    private String redirectAfterLogin(
            User user) {

        if (user != null
                && user.isAdmin()) {

            return "redirect:/admin/categories";
        }

        return "redirect:/home";
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

    private void saveRememberCookie(

            HttpServletRequest request,

            HttpServletResponse response,

            String username) {

        Cookie cookie =
                new Cookie(
                        COOKIE_USERNAME,
                        username
                );

        cookie.setMaxAge(
                COOKIE_MAX_AGE
        );

        cookie.setPath(
                getCookiePath(request)
        );

        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }

    private void deleteRememberCookie(

            HttpServletRequest request,

            HttpServletResponse response) {

        Cookie cookie =
                new Cookie(
                        COOKIE_USERNAME,
                        ""
                );

        cookie.setMaxAge(0);

        cookie.setPath(
                getCookiePath(request)
        );

        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }

    private String getCookiePath(
            HttpServletRequest request) {

        String contextPath =
                request.getContextPath();

        return contextPath == null
                || contextPath.isEmpty()
                ? "/"
                : contextPath;
    }
}