package vn.hcmute.springboot.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.hcmute.springboot.entity.User;
import vn.hcmute.springboot.model.UserModel;
import vn.hcmute.springboot.service.IUserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final IUserService userService;

    public AdminUserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/", "/searchpaginated"})
    public String list(
            ModelMap model,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page") Optional<Integer> page,
            @RequestParam(name = "size") Optional<Integer> size) {

        int currentPage = Math.max(page.orElse(1), 1);
        int pageSize = normalizePageSize(size.orElse(5));

        Pageable pageable = PageRequest.of(
                currentPage - 1,
                pageSize,
                Sort.by("userId").descending()
        );

        Page<User> resultPage;

        if (StringUtils.hasText(keyword)) {
            keyword = keyword.trim();
            resultPage = userService.search(keyword, pageable);
        } else {
            keyword = "";
            resultPage = userService.findAll(pageable);
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("userPage", resultPage);

        addPageNumbers(model, resultPage);

        return "admin/users/list";
    }

    @GetMapping("/add")
    public String add(ModelMap model) {

        UserModel user = new UserModel();

        user.setIsEdit(false);
        user.setRole(User.ROLE_USER);

        model.addAttribute("user", user);

        return "admin/users/addOrEdit";
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(
            ModelMap model,
            @Valid @ModelAttribute("user") UserModel userModel,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        boolean isEdit = userModel.getIsEdit();

        Integer ignoreId = isEdit
                ? userModel.getUserId()
                : null;

        if (!isEdit
                && !StringUtils.hasText(userModel.getPassword())) {

            result.rejectValue(
                    "password",
                    "password.blank",
                    "Mật khẩu không được để trống khi thêm mới"
            );
        }

        if (StringUtils.hasText(userModel.getUsername())
                && userService.usernameExists(
                        userModel.getUsername().trim(),
                        ignoreId
                )) {

            result.rejectValue(
                    "username",
                    "username.exists",
                    "Username đã tồn tại"
            );
        }

        if (StringUtils.hasText(userModel.getEmail())
                && userService.emailExists(
                        userModel.getEmail().trim(),
                        ignoreId
                )) {

            result.rejectValue(
                    "email",
                    "email.exists",
                    "Email đã tồn tại"
            );
        }

        if (result.hasErrors()) {
            return new ModelAndView(
                    "admin/users/addOrEdit",
                    model
            );
        }

        String role = normalizeRole(userModel.getRole());

        userModel.setRole(role);

        User entity;

        if (isEdit) {

            entity = userService
                    .findById(userModel.getUserId())
                    .orElse(null);

            if (entity == null) {

                redirectAttributes.addFlashAttribute(
                        "message",
                        "User không tồn tại!"
                );

                return new ModelAndView(
                        "redirect:/admin/users"
                );
            }

            String oldPassword = entity.getPassword();
            String oldImages = entity.getImages();

            BeanUtils.copyProperties(
                    userModel,
                    entity,
                    "password",
                    "images",
                    "isEdit"
            );

            if (StringUtils.hasText(userModel.getPassword())) {
                entity.setPassword(userModel.getPassword());
            } else {
                entity.setPassword(oldPassword);
            }

            entity.setImages(oldImages);

        } else {

            entity = new User();

            BeanUtils.copyProperties(
                    userModel,
                    entity,
                    "isEdit"
            );
        }

        entity.setRole(role);
        entity.setUsername(entity.getUsername().trim());
        entity.setEmail(entity.getEmail().trim());

        if (entity.getFullName() != null) {
            entity.setFullName(entity.getFullName().trim());
        }

        userService.save(entity);

        redirectAttributes.addFlashAttribute(
                "message",
                isEdit
                        ? "Cập nhật User thành công!"
                        : "Thêm User thành công!"
        );

        return new ModelAndView(
                "redirect:/admin/users"
        );
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(
            ModelMap model,
            @PathVariable("id") int id,
            RedirectAttributes redirectAttributes) {

        Optional<User> optional = userService.findById(id);

        if (optional.isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "User không tồn tại!"
            );

            return new ModelAndView(
                    "redirect:/admin/users"
            );
        }

        UserModel userModel = new UserModel();

        BeanUtils.copyProperties(
                optional.get(),
                userModel,
                "password"
        );

        userModel.setPassword("");
        userModel.setIsEdit(true);

        if (!StringUtils.hasText(userModel.getRole())) {
            userModel.setRole(User.ROLE_USER);
        }

        model.addAttribute("user", userModel);

        return new ModelAndView(
                "admin/users/addOrEdit",
                model
        );
    }

    @GetMapping("/view/{id}")
    public ModelAndView view(
            ModelMap model,
            @PathVariable("id") int id,
            RedirectAttributes redirectAttributes) {

        Optional<User> optional = userService.findById(id);

        if (optional.isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "User không tồn tại!"
            );

            return new ModelAndView(
                    "redirect:/admin/users"
            );
        }

        model.addAttribute(
                "user",
                optional.get()
        );

        return new ModelAndView(
                "admin/users/view",
                model
        );
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(
            @PathVariable("id") int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Optional<User> optional = userService.findById(id);

        if (optional.isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "User không tồn tại!"
            );

            return new ModelAndView(
                    "redirect:/admin/users"
            );
        }

        Object accountObject =
                session.getAttribute("account");

        if (accountObject instanceof User account
                && account.getUserId() == id) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "Bạn không thể xóa tài khoản đang đăng nhập!"
            );

            return new ModelAndView(
                    "redirect:/admin/users"
            );
        }

        userService.deleteById(id);

        redirectAttributes.addFlashAttribute(
                "message",
                "Xóa User thành công!"
        );

        return new ModelAndView(
                "redirect:/admin/users"
        );
    }

    private String normalizeRole(String role) {

        if (User.ROLE_ADMIN.equalsIgnoreCase(role)) {
            return User.ROLE_ADMIN;
        }

        return User.ROLE_USER;
    }

    private int normalizePageSize(int size) {

        return switch (size) {
            case 3, 5, 10, 15, 20 -> size;
            default -> 5;
        };
    }

    private void addPageNumbers(
            ModelMap model,
            Page<?> resultPage) {

        int totalPages =
                resultPage.getTotalPages();

        if (totalPages <= 0) {

            model.addAttribute(
                    "pageNumbers",
                    List.of()
            );

            return;
        }

        int currentPage =
                resultPage.getNumber() + 1;

        int start =
                Math.max(
                        1,
                        currentPage - 2
                );

        int end =
                Math.min(
                        currentPage + 2,
                        totalPages
                );

        model.addAttribute(
                "pageNumbers",
                IntStream
                        .rangeClosed(start, end)
                        .boxed()
                        .collect(Collectors.toList())
        );
    }
}