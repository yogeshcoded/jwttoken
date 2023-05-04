package com.nt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.model.UserInfo;

public interface IUserRepository extends JpaRepository<UserInfo, Integer> {
           public Optional<UserInfo> findByUsername(String username);
}
