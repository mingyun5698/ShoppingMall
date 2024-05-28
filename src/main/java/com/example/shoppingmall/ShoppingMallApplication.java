package com.example.shoppingmall;

import com.example.shoppingmall.entity.User;
import com.example.shoppingmall.repository.UserRepository;
import com.example.shoppingmall.security.UserRoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoppingMallApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public ShoppingMallApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShoppingMallApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User(1L, UserRoleEnum.ADMIN, "ADMIN", "aaa", "ADMIN", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw"));
        userRepository.save(new User(2L, UserRoleEnum.USER, "USER", "bbb", "USER", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw"));
        userRepository.save(new User(3L, UserRoleEnum.USER, "ccc", "ccc", "ccc", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw"));
        userRepository.save(new User(4L, UserRoleEnum.USER, "ddd", "ddd", "ddd", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw"));
        userRepository.save(new User(5L, UserRoleEnum.USER, "eee", "eee", "eee", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw", "dqwqdw"));


    }
}
