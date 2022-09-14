package com.treeleaf.assistment.service;

import com.treeleaf.assistment.entity.User;
import com.treeleaf.assistment.enums.UserRoleEnum;
import com.treeleaf.assistment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
@Transactional
public class DatabaseInitialization {

    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    @PostConstruct
    public void init() {
        System.out.println("INITIALIZING DATABASE................");
        String password = "admin123";
        if (userRepository.count() == 0) {
            User user = new User("admin", bCryptPasswordEncoder.encode(password.subSequence(0, password.length())), UserRoleEnum.TEACHER.name());
           userRepository.save(user);
            System.out.println("Admin user is created.");
        }
    }
}
