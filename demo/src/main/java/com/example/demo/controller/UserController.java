package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/users")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/list")
    public String list(@RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "keyword", required = false) String keyword,
                       @RequestParam(name = "role", required = false) Role role,
                       Model model) {
        Pageable pageable = PageRequest.of(page, 2);
        Page<Users> users;

        if ((keyword != null && !keyword.trim().isEmpty()) || role != null) {
            users = iUserService.searchUsers(keyword, role, pageable);
            if (users.isEmpty()) {
                model.addAttribute("messageType", "error");
                model.addAttribute("message", "Không tìm thấy người dùng nào phù hợp!");
            }
        } else {
            users = iUserService.findAll(pageable);
            if (users.isEmpty()) {
                model.addAttribute("messageType", "error");
                model.addAttribute("message", "Hiện tại không có người dùng nào trong hệ thống!");
            }
        }

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        model.addAttribute("role", role);
        model.addAttribute("roles", Role.values());
        return "dashboard/users/list";
    }


    @GetMapping("/create")
    public ModelAndView createUser(Model model) {
        ModelAndView modelAndView = new ModelAndView("dashboard/users/create");
        modelAndView.addObject("users", new UserDTO());
        modelAndView.addObject("roles", Role.values());
        return modelAndView;
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("users") UserDTO userDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        if (iUserService.existsByUsername(userDTO.getUsername())) {
            model.addAttribute("users", userDTO);
            model.addAttribute("roles", Role.values());
            bindingResult.rejectValue("username", "","Tài khoản đã tồn tại!");
        }

        if (iUserService.existsByEmail(userDTO.getEmail())) {
            model.addAttribute("users", userDTO);
            model.addAttribute("roles", Role.values());
            bindingResult.rejectValue("email", "", "Email đã tồn tại!");
        }

        new UserDTO().validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userDTO);
            model.addAttribute("roles", Role.values());
            return "dashboard/users/create";
        }

        Users user = new Users();
        BeanUtils.copyProperties(userDTO, user);
        iUserService.save(user);

        redirectAttributes.addFlashAttribute("messageType", "success");
        redirectAttributes.addFlashAttribute("message", "Thêm người dùng mới thành công!");
        return "redirect:/dashboard/users/list";
    }

    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable Long id,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        Users user = iUserService.getById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("messageType", "error");
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy người dùng!");
            return "redirect:/dashboard/users/list";
        }
        model.addAttribute("user", user);
        return "dashboard/users/view";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        Users user = iUserService.getById(id);

        if (user == null) {
            redirectAttributes.addFlashAttribute("messageType", "error");
            redirectAttributes.addFlashAttribute("message", "Người dùng không tồn tại");
            return "redirect:/dashboard/users/list";
        }

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);

        model.addAttribute("user", userDTO);
        model.addAttribute("roles", Role.values());
        return "dashboard/users/update";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") UserDTO userDTO,
                             BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes) {
        Users userToUpdate = iUserService.getById(userDTO.getId());

        if (!userToUpdate.getEmail().equals(userDTO.getEmail()) && iUserService.existsByEmail(userDTO.getEmail())) {
            model.addAttribute("user", userDTO);
            model.addAttribute("roles", Role.values());
            bindingResult.rejectValue("email", "", "Email này đã tồn tại!");
            return "dashboard/users/update";
        }

        userDTO.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            model.addAttribute("roles", Role.values());
            return "dashboard/users/update";
        }

        userToUpdate.setPassword(userDTO.getPassword());
        userToUpdate.setEmail(userDTO.getEmail());
        userToUpdate.setPhone(userDTO.getPhone());
        userToUpdate.setFullName(userDTO.getFullName());
        userToUpdate.setGender(userDTO.getGender());
        userToUpdate.setAddress(userDTO.getAddress());
        userToUpdate.setRole(userDTO.getRole());
        userToUpdate.setStatus(userDTO.getStatus());

        iUserService.save(userToUpdate);

        redirectAttributes.addFlashAttribute("messageType", "success");
        redirectAttributes.addFlashAttribute("message", "Cập nhật người dùng thành công");
        return "redirect:/dashboard/users/list";
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Long id,
                             RedirectAttributes redirectAttributes) {
        try {
            iUserService.deleteById(id);
            redirectAttributes.addFlashAttribute("messageType", "success");
            redirectAttributes.addFlashAttribute("message", "Xoá thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("messageType", "error");
            redirectAttributes.addFlashAttribute("message", "Có lỗi khi xoá người dùng này!");
        }
        return "redirect:/dashboard/users/list";
    }
}
