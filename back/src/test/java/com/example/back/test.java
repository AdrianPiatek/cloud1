package com.example.back;

import com.example.back.entity.Sign;
import com.example.back.entity.User;
import com.example.back.exception.PositionTakenException;
import com.example.back.service.BoardService;
import com.example.back.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;

//    @Test
//    public void saveUser(){
//        userService.save(User.builder().password("test").username("test2").build());
//    }
//
//    @Test
//    public void createBoard(){
//        boardService.create(userService.getByUsername("test").get());
//    }
//
//    @Test
//    public void joinBoard(){
//        boardService.join(boardService.getById(52L).get() ,userService.getByUsername("test2").get());
//    }
//
//
//    @Test
//    public void move() throws PositionTakenException {
//        boardService.makeMove(Sign.X, 2, boardService.getById(52L).get());
//    }
//
//    @Test
//    public void getResult(){
//        System.out.println(boardService.getById(52L).get().getResults());
//    }
}
