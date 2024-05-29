//package com.example.shoppingmall.controller;
//
//import com.example.shoppingmall.dto.LoginDAO;
//import com.example.shoppingmall.dto.SignUpDAO;
//import com.example.shoppingmall.entity.User;
//import com.example.shoppingmall.repository.UserRepository;
//import com.example.shoppingmall.security.UserDetailsImpl;
//import com.example.shoppingmall.service.UserService;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Slf4j
//@Controller
//
//public class UserController {
//    private final UserService userService;
//    private final UserRepository userRepository;
//
//    public UserController(UserService userService, UserRepository userRepository) {
//        this.userService = userService;
//        this.userRepository = userRepository;
//    }
//
//
//    /*회원가입 뷰*/
//    @GetMapping("/signup")
//    public String signUp() {
//        return "signup";
//    }
//
//    /*회원가입 API*/
//    @PostMapping("/api/signup")
//    public String signUp(SignUpDAO signUpDAO, RedirectAttributes redirectAttributes) {
//        try {
//            userService.signUp(signUpDAO);
//            return "redirect:/login";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
//            return "signup"; // 회원가입 실패 시 회원가입 페이지로 리다이렉트
//        }
//    }
//
//    // 관리자 전용 DB 뷰
//    @GetMapping("/users")
//    public String getAllUsers(Model model) {
//        Iterable<User> users = userRepository.findAll();
//        model.addAttribute("users", users);
//        return "users";
//    }
//
//    /* 관리자 전용 회원 추가 뷰 */
//    @GetMapping("/adduser")
//    public String addemployeeView() {
//        return "adduser";
//    }
//
//    /* 관리자 전용 회원 추가 API */
//    @PostMapping("/api/users")
//    public String addEmployee(SignUpDAO signUpDAO, RedirectAttributes redirectAttributes) {
//        try {
//            userService.signUp(signUpDAO);
//            return "redirect:/users"; // 직원 추가 성공 시 직원 목록 페이지로 리다이렉트
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "직원 추가 중 오류가 발생했습니다. 다시 시도해주세요.");
//            return "adduser"; // 직원 추가 실패 시 직원 추가 페이지로 리다이렉트
//        }
//    }
//
//    // 관리자 전용 회원 삭제 API
//    @PostMapping("/users/delete")
//    public String deleteUser(@RequestParam Long id) {
//        userService.deleteUser(id);
//        return "redirect:/users";
//    }
//
//    // 관리자 전용 회원 수정 뷰
//    @GetMapping("/user/edit/{id}")
//    public String editUser(@PathVariable("id") Long id, Model model) {
//        // 회원 정보를 데이터베이스에서 가져옴
//        User user = userService.findById(id);
//
//        // 가져온 회원 정보를 모델에 추가하여 Thymeleaf로 전달
//        model.addAttribute("user", user);
//
//        return "edituser"; // 수정할 회원 정보를 담은 페이지로 이동
//    }
//
//    // 관리자 전용 회원 수정 저장 API
//    @PostMapping("/user/edit")
//    public String saveEditedUser(@ModelAttribute User user) {
//        // 수정된 회원 정보를 데이터베이스에 저장
//        userService.edit(user);
//
//        // 수정 후에는 보통 상세 페이지로 리다이렉트하거나, 수정된 내용을 보여주는 페이지로 이동합니다.
//        return "redirect:/users"; // 수정된 회원 정보를 보여주는 페이지로 리다이렉트
//    }
//
//    /* mypage 뷰*/
//    @GetMapping("/mypage")
//    public String system(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userDetails.getUser(); // UserDetailsImpl 객체에서 User 정보 추출
//        model.addAttribute("user", user); // 추출된 User 정보를 모델에 추가
//        return "mypage";
//    }
//
//    // mypage 회원탈퇴 API 및 header 쿠키삭제
//    @PostMapping("/users/mydelete")
//    public String deleteMyUser(@RequestParam Long id, @CookieValue(value = "Authorization", defaultValue = "", required = false) Cookie jwtCookie,
//                               HttpServletResponse response) {
//        userService.deleteUser(id);
//        jwtCookie.setValue(null);
//        jwtCookie.setMaxAge(0);
//        jwtCookie.setPath("/");
//        response.addCookie(jwtCookie);
//        return "redirect:/";
//    }
//
////    // mypage 회원 수정 뷰
////    @GetMapping("/user/myedit/{id}")
////    public String editMyUser(@PathVariable("id") Long id, Model model ) {
////        // 회원 정보를 데이터베이스에서 가져옴
////        User user = userService.findById(id);
////
////        // 가져온 회원 정보를 모델에 추가하여 Thymeleaf로 전달
////        model.addAttribute("user", user);
////
////        return "editmypage"; // 수정할 회원 정보를 담은 페이지로 이동
////    }
//
//    // mypage 회원 수정 뷰
//    @GetMapping("/editmypage")
//    public String Ssystem(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userDetails.getUser(); // UserDetailsImpl 객체에서 User 정보 추출
//        model.addAttribute("user", user); // 추출된 User 정보를 모델에 추가
//        return "editmypage";
//    }
//
//    // mypage 회원 수정 저장 API 및 로그아웃
//    @PostMapping("/user/myedit")
//    public String saveMyEditedUser(@ModelAttribute User user, @CookieValue(value = "Authorization", defaultValue = "", required = false) Cookie jwtCookie,
//                                   HttpServletResponse response) {
//        // 수정된 회원 정보를 데이터베이스에 저장
//        userService.edit(user);
//        jwtCookie.setValue(null);
//        jwtCookie.setMaxAge(0);
//        jwtCookie.setPath("/");
//        response.addCookie(jwtCookie);
//
//        // 수정 후에는 보통 상세 페이지로 리다이렉트하거나, 수정된 내용을 보여주는 페이지로 이동합니다.
//        return "redirect:/login"; // 수정된 회원 정보를 보여주는 페이지로 리다이렉트
//    }
//
//    /*로그인 뷰*/
//    @GetMapping("/login")
//    public String login(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        /*이미 로그인된 사용자일 경우 인덱스 페이지로 강제이동.*/
//        if (userDetails != null) {
//            return "redirect:/";
//        }
//
//        return "login";
//    }
//
//    /*로그인 API*/
//    @PostMapping("/api/login")
//    public String login(LoginDAO loginDAO, HttpServletResponse response) {
//        userService.login(loginDAO, response);
//        return "redirect:/";
//    }
//
//    /*로그아웃 API*/
//    @GetMapping("/api/logout")
//    public String logout(@CookieValue(value = "Authorization", defaultValue = "", required = false) Cookie jwtCookie,
//                         HttpServletResponse response) {
//        /*jwt 쿠키를 가지고와서 제거한다.*/
//        jwtCookie.setValue(null);
//        jwtCookie.setMaxAge(0);
//        jwtCookie.setPath("/");
//        response.addCookie(jwtCookie);
//
//        return "redirect:/";
//    }
//
//
//
//
//
//
//
//}
