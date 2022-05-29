package com.accenture.transactionapplication.service.user;

import com.accenture.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
