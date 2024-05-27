package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.LoginDAO;
import com.example.shoppingmall.dto.SignUpDAO;
import com.example.shoppingmall.entity.User;
import com.example.shoppingmall.jwt.JwtUtil;
import com.example.shoppingmall.repository.UserRepository;
import com.example.shoppingmall.security.UserRoleEnum;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입
    public void signUp(SignUpDAO signUpDAO) {
        Long id = null;
        UserRoleEnum usertype = UserRoleEnum.valueOf(signUpDAO.getUsertype());
        String userId = signUpDAO.getUserId();;
        String username = signUpDAO.getUsername();
        String password = signUpDAO.getPassword();
        String birthdate = signUpDAO.getBirthdate();
        String gender = signUpDAO.getGender();
        String email = signUpDAO.getEmail();
        String contact = signUpDAO.getContact();
        String address = signUpDAO.getAddress();

        if (userRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원 이름입니다: " + userId);
        }

        User user = new User(id, usertype, userId, username, password, birthdate, gender, email, contact, address);
        userRepository.save(user);

    }

    // 유저 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 유저 수정
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // 수정 완료
    public User edit(User user) {
        return userRepository.save(user);
    }

    /*로그인*/
    @Transactional
    public void login(LoginDAO loginDAO, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByUserId(loginDAO.getUserId());

        if (optionalUser.isEmpty()) {

            throw new IllegalArgumentException("회원이 존재하지 않음");
        }

        User user = optionalUser.get();

//        if (!passwordEncoder.matches(loginDAO.getPassword(), user.getPassword())) {
//
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserId(), user.getUsertype()));
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setSecure(false);

        response.addCookie(cookie);
    }

}
