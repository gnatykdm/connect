package com.connect.connect.model.repository.user;

import com.connect.connect.model.entity.User;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByUserId(Integer userId);

    void deleteByUserId(Integer userId);

    @Query("SELECT u.userId FROM User u WHERE u.username = :username")
    Optional<Integer> getUserIdByUsername(String username);
}
