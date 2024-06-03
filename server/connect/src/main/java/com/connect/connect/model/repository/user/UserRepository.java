package com.connect.connect.model.repository.user;

import com.connect.connect.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByUserId(Integer userId);
    void deleteByUserId(Integer userId);
    User findByUsernameAndPassword(String username, String password);
}
