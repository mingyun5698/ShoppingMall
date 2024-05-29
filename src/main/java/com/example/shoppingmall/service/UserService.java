package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.LoginDao;
import com.example.shoppingmall.dto.UserDao;
import com.example.shoppingmall.entity.User;
import com.example.shoppingmall.jwt.JwtUtil;
import com.example.shoppingmall.repository.UserRepository;
import com.example.shoppingmall.security.UserRoleEnum;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final PasswordEncoder passwordEncoder;



    // 회원가입
    public User signUp(UserDao userDao) {
        Long id = null;
        UserRoleEnum usertype = UserRoleEnum.valueOf(userDao.getUsertype());
        String userId = userDao.getUserId();;
        String username = userDao.getUsername();
        String password = (userDao.getPassword());
        String birthdate = userDao.getBirthdate();
        String gender = userDao.getGender();
        String email = userDao.getEmail();
        String contact = userDao.getContact();
        String address = userDao.getAddress();

        if (userRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원 이름입니다: " + userId);
        }

        User user = new User(id, usertype, userId, username, password, birthdate, gender, email, contact, address);
        userRepository.save(user);

        return user;
    }

    // 유저 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 유저 수정
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // mypage 수정 완료
    public User edit(UserDao userDao) {
        // UserDTO를 User 객체로 변환
        User user = new User();
        user.setId(userDao.getId());
        user.setUserId(userDao.getUserId());
        user.setPassword(userDao.getPassword());
        user.setUsertype(UserRoleEnum.valueOf(userDao.getUsertype()));
        user.setUsername(userDao.getUsername());
        user.setAddress(userDao.getAddress());
        user.setContact(userDao.getContact());
        user.setEmail(userDao.getEmail());
        user.setBirthdate(userDao.getBirthdate());
        return user;

        // 여기서 해당 사용자를 수정하는 로직을 구현
        // 예를 들어, JPA를 사용한다면 해당 사용자를 조회하고 값을 업데이트할 것입니다.
    }

    // UserDTO를 User 객체로 변환하는 메서드
    private User convertToUser(UserDao userDao) {
        User user = new User();
        user.setId(userDao.getId());
        user.setUserId(userDao.getUserId());
        user.setPassword(userDao.getPassword());
        user.setUsertype(UserRoleEnum.valueOf(userDao.getUsertype()));
        user.setUsername(userDao.getUsername());
        user.setAddress(userDao.getAddress());
        user.setContact(userDao.getContact());
        user.setEmail(userDao.getEmail());
        user.setBirthdate(userDao.getBirthdate());

        return user;
    }


    // 관리자 수정 완료
    public User updateUser(Long id, UserDao userDao) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUserId(userDao.getUserId());
            user.setUsername(userDao.getUsername());
            user.setPassword(userDao.getPassword());
            user.setAddress(userDao.getAddress());
            user.setBirthdate(userDao.getBirthdate());
            user.setContact(userDao.getContact());
            user.setUsertype(UserRoleEnum.valueOf(userDao.getUsertype()));
            user.setGender(userDao.getGender());

            userRepository.save(user);
            return user;
        } else {
            return null;
        }
    }


    /*로그인*/
    @Transactional
    public void login(LoginDao loginDao, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByUserId(loginDao.getUserId());

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
