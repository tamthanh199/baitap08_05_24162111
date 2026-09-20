package vn.hcmute.springboot.controller;

import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.springboot.entity.User;
import vn.hcmute.springboot.service.IUserService;
import vn.hcmute.springboot.util.EmailUtil;
import vn.hcmute.springboot.util.FormValidationUtil;
import vn.hcmute.springboot.util.OtpUtil;

@Controller
public class RegisterController {
    private static final int OTP_EXPIRE_MINUTES = 5;
    private final IUserService userService;

    public RegisterController(IUserService userService) { this.userService = userService; }

    @GetMapping("/register")
    public String page() { return "register"; }

    @PostMapping("/register")
    public String register(@RequestParam(defaultValue="") String fullName,
            @RequestParam(defaultValue="") String email,
            @RequestParam(defaultValue="") String phone,
            @RequestParam(defaultValue="") String username,
            @RequestParam(defaultValue="") String password,
            @RequestParam(defaultValue="") String confirmPassword,
            HttpSession session, Model model) {
        fullName = fullName.trim(); email = email.trim(); phone = phone.trim(); username = username.trim();
        model.addAttribute("fullName", fullName); model.addAttribute("email", email);
        model.addAttribute("phone", phone); model.addAttribute("username", username);

        String validation = FormValidationUtil.validateRegister(fullName, email, phone, username, password, confirmPassword);
        if (validation != null) { model.addAttribute("message", validation); return "register"; }
        if (userService.existsByUsername(username)) { model.addAttribute("message", "Tài khoản đã tồn tại."); return "register"; }
        if (userService.existsByEmail(email)) { model.addAttribute("message", "Email đã tồn tại."); return "register"; }
        if (userService.existsByPhone(phone)) { model.addAttribute("message", "Số điện thoại đã tồn tại."); return "register"; }

        User pendingUser = new User(username, password, fullName, email, phone);
        String otp = OtpUtil.generateOtp();
        try {
            EmailUtil.sendOtp(email, otp, "Kích hoạt tài khoản");
        } catch (MessagingException e) {
            model.addAttribute("message", "Không gửi được OTP: " + e.getMessage());
            return "register";
        }

        session.setAttribute("pendingUser", pendingUser);
        session.setAttribute("registrationOtp", otp);
        session.setAttribute("registrationOtpExpiry", LocalDateTime.now().plusMinutes(OTP_EXPIRE_MINUTES));
        return "redirect:/verify-otp";
    }

    @GetMapping("/verify-otp")
    public String verifyPage(HttpSession session, Model model) {
        User pending = (User) session.getAttribute("pendingUser");
        if (pending == null) return "redirect:/register";
        model.addAttribute("email", pending.getEmail());
        return "verify-otp";
    }

    @PostMapping("/verify-otp")
    public String verify(@RequestParam(name="action", required=false) String action,
            @RequestParam(name="otp", required=false) String inputOtp,
            HttpSession session, Model model) {
        User pending = (User) session.getAttribute("pendingUser");
        if (pending == null) return "redirect:/register";
        model.addAttribute("email", pending.getEmail());

        if ("resend".equals(action)) {
            String otp = OtpUtil.generateOtp();
            try {
                EmailUtil.sendOtp(pending.getEmail(), otp, "Kích hoạt tài khoản");
                session.setAttribute("registrationOtp", otp);
                session.setAttribute("registrationOtpExpiry", LocalDateTime.now().plusMinutes(OTP_EXPIRE_MINUTES));
                model.addAttribute("successMessage", "Đã gửi lại OTP.");
            } catch (MessagingException e) {
                model.addAttribute("message", "Không gửi lại được OTP: " + e.getMessage());
            }
            return "verify-otp";
        }

        String validation = FormValidationUtil.validateOtp(inputOtp);
        if (validation != null) { model.addAttribute("message", validation); return "verify-otp"; }
        String savedOtp = (String) session.getAttribute("registrationOtp");
        LocalDateTime expiry = (LocalDateTime) session.getAttribute("registrationOtpExpiry");
        if (savedOtp == null || expiry == null || LocalDateTime.now().isAfter(expiry)) {
            model.addAttribute("message", "Mã OTP đã hết hạn. Vui lòng gửi lại OTP."); return "verify-otp";
        }
        if (!savedOtp.equals(inputOtp.trim())) { model.addAttribute("message", "Mã OTP không đúng."); return "verify-otp"; }
        if (!userService.register(pending)) {
            clearRegistration(session);
            model.addAttribute("message", "Thông tin tài khoản đã tồn tại. Vui lòng đăng ký lại.");
            return "register";
        }
        clearRegistration(session);
        return "redirect:/login?registered=true";
    }

    private void clearRegistration(HttpSession session) {
        session.removeAttribute("pendingUser");
        session.removeAttribute("registrationOtp");
        session.removeAttribute("registrationOtpExpiry");
    }
}
