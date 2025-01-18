package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        Optional<User> user = iUserService.findByUsername(username);

        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("messageType", "error");
            redirectAttributes.addFlashAttribute("message", "Tài khoản không tồn tại!");
            redirectAttributes.addFlashAttribute("username", username);
            return "redirect:/login";
        }

        if (!user.get().getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("messageType", "error");
            redirectAttributes.addFlashAttribute("message", "Mật khẩu không chính xác!");
            redirectAttributes.addFlashAttribute("username", username);
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("messageType", "success");
        redirectAttributes.addFlashAttribute("message", "Đăng nhập thành công!");
        return "redirect:/dashboard";
    }
}
