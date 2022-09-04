package com.sbnz.gleficu.repository.user;

import com.sbnz.gleficu.model.user.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserDb, Integer> {
    Optional<UserDb> findByUsername(String username);
}
