package com.accenture.transactionapplication.controller.user;

import com.accenture.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    List<Transaction> transaction = new ArrayList<>();

    @PostConstruct
    public void encode() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("User password: " + bCryptPasswordEncoder.encode("password12345"));
        System.out.println("Admin password: " + bCryptPasswordEncoder.encode("admin12345"));
    }
}
