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
public class ForgotPasswordController {
    private static final int OTP_EXPIRE_MINUTES = 5;
    private final IUserService userService;

    public ForgotPasswordController(IUserService userService) { this.userService = userService; }

    @GetMapping("/forgot-password")
    public String page() { return "forgot-password"; }

    @PostMapping("/forgot-password")
    public String forgot(@RequestParam(defaultValue="") String email, HttpSession session, Model model) {
        email = email.trim(); model.addAttribute("email", email);
        String validation = FormValidationUtil.validateEmail(email);
        if (validation != null) { model.addAttribute("message", validation); return "forgot-password"; }
        User user = userService.getByEmail(email);
        if (user == null) { model.addAttribute("message", "Email không tồn tại trong hệ thống."); return "forgot-password"; }
        String otp = OtpUtil.generateOtp();
        try { EmailUtil.sendOtp(email, otp, "Đặt lại mật khẩu"); }
        catch (MessagingException e) { model.addAttribute("message", "Không gửi được OTP: " + e.getMessage()); return "forgot-password"; }
        session.setAttribute("resetUserId", user.getId());
        session.setAttribute("resetEmail", email);
        session.setAttribute("resetOtp", otp);
        session.setAttribute("resetOtpExpiry", LocalDateTime.now().plusMinutes(OTP_EXPIRE_MINUTES));
        return "redirect:/verify-reset-otp";
    }

    @GetMapping("/verify-reset-otp")
    public String verifyPage(HttpSession session, Model model) {
        if (session.getAttribute("resetUserId") == null) return "redirect:/forgot-password";
        model.addAttribute("email", session.getAttribute("resetEmail"));
        return "verify-reset-otp";
    }

    @PostMapping("/verify-reset-otp")
    public String verify(@RequestParam(name="action", required=false) String action,
            @RequestParam(name="otp", required=false) String inputOtp,
            HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("resetUserId");
        String email = (String) session.getAttribute("resetEmail");
        if (userId == null || email == null) return "redirect:/forgot-password";
        model.addAttribute("email", email);

        if ("resend".equals(action)) {
            String otp = OtpUtil.generateOtp();
            try {
                EmailUtil.sendOtp(email, otp, "Đặt lại mật khẩu");
                session.setAttribute("resetOtp", otp);
                session.setAttribute("resetOtpExpiry", LocalDateTime.now().plusMinutes(OTP_EXPIRE_MINUTES));
                model.addAttribute("successMessage", "Đã gửi lại OTP.");
            } catch (MessagingException e) { model.addAttribute("message", "Không gửi lại được OTP: " + e.getMessage()); }
            return "verify-reset-otp";
        }

        String validation = FormValidationUtil.validateOtp(inputOtp);
        if (validation != null) { model.addAttribute("message", validation); return "verify-reset-otp"; }
        String savedOtp = (String) session.getAttribute("resetOtp");
        LocalDateTime expiry = (LocalDateTime) session.getAttribute("resetOtpExpiry");
        if (savedOtp == null || expiry == null || LocalDateTime.now().isAfter(expiry)) {
            model.addAttribute("message", "Mã OTP đã hết hạn. Vui lòng gửi lại OTP."); return "verify-reset-otp";
        }
        if (!savedOtp.equals(inputOtp.trim())) { model.addAttribute("message", "Mã OTP không đúng."); return "verify-reset-otp"; }
        session.setAttribute("resetVerifiedUserId", userId);
        return "redirect:/reset-password";
    }

    @GetMapping("/reset-password")
    public String resetPage(HttpSession session) {
        return session.getAttribute("resetVerifiedUserId") == null ? "redirect:/forgot-password" : "reset-password";
    }

    @PostMapping("/reset-password")
    public String reset(@RequestParam(defaultValue="") String password,
            @RequestParam(defaultValue="") String confirmPassword, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("resetVerifiedUserId");
        if (userId == null) return "redirect:/forgot-password";
        String validation = FormValidationUtil.validateResetPassword(password, confirmPassword);
        if (validation != null) { model.addAttribute("message", validation); return "reset-password"; }
        User user = userService.getById(userId);
        if (user == null) { clearReset(session); return "redirect:/forgot-password"; }
        user.setPassword(password); userService.update(user); clearReset(session);
        return "redirect:/login?reset=true";
    }

    private void clearReset(HttpSession session) {
        session.removeAttribute("resetUserId"); session.removeAttribute("resetEmail");
        session.removeAttribute("resetOtp"); session.removeAttribute("resetOtpExpiry");
        session.removeAttribute("resetVerifiedUserId");
    }
}
