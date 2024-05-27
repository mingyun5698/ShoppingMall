package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.SignUpDAO;
import com.example.shoppingmall.entity.User;
import com.example.shoppingmall.repository.UserRepository;
import com.example.shoppingmall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    /*회원가입 뷰*/
    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    /*회원가입 API*/
    @PostMapping("/api/signup")
    public String signUp(SignUpDAO signUpDAO, RedirectAttributes redirectAttributes) {
        try {
            userService.signUp(signUpDAO);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
            return "signup"; // 회원가입 실패 시 회원가입 페이지로 리다이렉트
        }
    }

    // 관리자 전용 DB 뷰
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    /* 회원 추가 뷰 */
    @GetMapping("/adduser")
    public String addemployeeView() {
        return "adduser";
    }

    /* 회원 추가 API */
    @PostMapping("/api/users")
    public String addEmployee(SignUpDAO signUpDAO, RedirectAttributes redirectAttributes) {
        try {
            userService.signUp(signUpDAO);
            return "redirect:/users"; // 직원 추가 성공 시 직원 목록 페이지로 리다이렉트
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "직원 추가 중 오류가 발생했습니다. 다시 시도해주세요.");
            return "adduser"; // 직원 추가 실패 시 직원 추가 페이지로 리다이렉트
        }
    }

    //    회원 삭제 API
    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    // 회원 수정 뷰
    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        // 회원 정보를 데이터베이스에서 가져옴
        User user = userService.findById(id);

        // 가져온 회원 정보를 모델에 추가하여 Thymeleaf로 전달
        model.addAttribute("user", user);

        return "edituser"; // 수정할 회원 정보를 담은 페이지로 이동
    }

    // 회원 수정 저장 API
    @PostMapping("/user/edit")
    public String saveEditedUser(@ModelAttribute User user) {
        // 수정된 회원 정보를 데이터베이스에 저장
        userService.edit(user);

        // 수정 후에는 보통 상세 페이지로 리다이렉트하거나, 수정된 내용을 보여주는 페이지로 이동합니다.
        return "redirect:/users"; // 수정된 회원 정보를 보여주는 페이지로 리다이렉트
    }






}
