package com.example.back.controller;

import com.example.back.entity.State;
import com.example.back.service.BoardService;
import com.example.back.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private BoardService boardService;
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> joinGame(@RequestBody String username){
        var user = userService.getByUsername(username);
        if (user.isEmpty())
            return ResponseEntity.badRequest().body("User dose not exists");

        var board = boardService.getByState(State.WAITING_FOR_PLAYERS);
        if (board.isEmpty())
            return ResponseEntity.ok(boardService.create(user.get()).getId());

        boardService.join(board.get(), user.get());
        return ResponseEntity.ok(board.get().getId());
    }
}
