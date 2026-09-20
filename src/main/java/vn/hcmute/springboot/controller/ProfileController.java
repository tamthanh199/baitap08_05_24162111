package vn.hcmute.springboot.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;
import vn.hcmute.springboot.entity.User;
import vn.hcmute.springboot.service.IUserService;
import vn.hcmute.springboot.util.FileUploadUtil;
import vn.hcmute.springboot.util.FormValidationUtil;

@Controller
public class ProfileController {
    private final IUserService userService;
    public ProfileController(IUserService userService) { this.userService = userService; }

    @GetMapping("/profile")
    public String page(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        if (account == null) return "redirect:/login";
        User user = userService.getById(account.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String update(@RequestParam(defaultValue="") String fullName,
            @RequestParam(defaultValue="") String phone,
            @RequestParam(name="image", required=false) MultipartFile image,
            HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        if (account == null) return "redirect:/login";
        User user = userService.getById(account.getId());
        if (user == null) { session.invalidate(); return "redirect:/login"; }
        fullName = fullName.trim(); phone = phone.trim();
        String validation = FormValidationUtil.validateProfile(fullName, phone);
        if (validation != null) {
            user.setFullName(fullName); user.setPhone(phone);
            model.addAttribute("user", user); model.addAttribute("message", validation);
            return "profile";
        }

        String oldImage = user.getImages();
        try {
            String newImage = FileUploadUtil.saveProfileImage(image);
            user.setFullName(fullName); user.setPhone(phone);
            if (newImage != null) {
                user.setImages(newImage);
                if (oldImage != null && !oldImage.isBlank()) FileUploadUtil.deleteProfileImage(oldImage);
            }
            userService.update(user); session.setAttribute("account", user);
            return "redirect:/profile?success=true";
        } catch (IOException e) {
            model.addAttribute("user", user); model.addAttribute("message", e.getMessage());
            return "profile";
        }
    }
}
