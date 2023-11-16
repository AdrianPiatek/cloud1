package com.example.back.repository;

import com.example.back.entity.Board;
import com.example.back.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByState(State state);
}
