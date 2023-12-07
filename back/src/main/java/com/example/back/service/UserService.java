package com.example.back.service;

import com.example.back.entity.Board;
import com.example.back.entity.User;
import com.example.back.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }

    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public void addBoard(User user, Board board){
        user.getBoards().add(board);
        save(user);
    }

    public User register(String username) {
        var user = User.builder()
                .username(username)
                .build();
        save(user);
        return user;
    }
}
