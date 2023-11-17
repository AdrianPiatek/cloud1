package com.example.back.controller;

import com.example.back.dto.GameDTO;
import com.example.back.dto.MessageDTO;
import com.example.back.dto.MoveDTO;
import com.example.back.dto.ResultDTO;
import com.example.back.entity.MessageType;
import com.example.back.entity.State;
import com.example.back.service.BoardService;
import com.example.back.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketGameController {
    private ObjectMapper objectMapper;
    private BoardService boardService;
    private UserService userService;

    @SneakyThrows
    @SendTo("/topic/{id}")
    @MessageMapping("/game/{id}")
    public String getBoard(@DestinationVariable Long id){
        var board = boardService.getById(id);
        if (board.isEmpty())
            return "Game with this ID dose not exist";
        return objectMapper.writeValueAsString(new MessageDTO<>(MessageType.GAME_STATE, new GameDTO(board.get())));
    }

    @SneakyThrows
    @SendTo("/topic/{id}")
    @MessageMapping("/game/{id}/move")
    public String move(@DestinationVariable Long id, MoveDTO move){
        var board = boardService.getById(id);
        if (board.isEmpty())
            return "Game with this ID dose not exist";

        boardService.playerMove(move, board.get());
        if (board.get().getState() == State.ENDED){
            return objectMapper.writeValueAsString(new MessageDTO<>(MessageType.RESULT, new ResultDTO(board.get())));
        }
        return objectMapper.writeValueAsString(new MessageDTO<>(MessageType.GAME_STATE, new GameDTO(board.get())));
    }

    @SneakyThrows
    @SendTo("/topic/{id}")
    @MessageMapping("/game/{id}/surrender")
    public String surrender(@DestinationVariable Long id, String username){
        var board = boardService.getById(id);
        if (board.isEmpty())
            return "Game with this ID dose not exist";

        var user = userService.getByUsername(username);
        if (user.isEmpty())
            return "User " + username + " dose not exist";

        boardService.playerSurrender(user.get(), board.get());
        return objectMapper.writeValueAsString(new MessageDTO<>(MessageType.RESULT, new ResultDTO(board.get())));
    }

    @SneakyThrows
    @SendTo("/topic/{id}")
    @MessageMapping("/game/{id}/ping")
    public String ping(){
        return "pong";
    }

}
