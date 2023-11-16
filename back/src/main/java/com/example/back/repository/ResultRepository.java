package com.example.back.repository;

import com.example.back.entity.Result;
import com.example.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    public List<Result> findAllByUser(User user);

}
