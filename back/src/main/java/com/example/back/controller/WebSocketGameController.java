package com.example.back.controller;

import com.example.back.dto.*;
import com.example.back.entity.Board;
import com.example.back.entity.State;
import com.example.back.service.BoardService;
import com.example.back.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketGameController {
    private BoardService boardService;
    private UserService userService;
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/game/{id}")
    public void getBoard(@DestinationVariable Long id) {
        var board = boardService.getById(id);
        if (board.isEmpty()) {
            sendError(id, "Game with this ID dose not exist");
            return;
        }
        sendGameState(id, board.get());
    }

    @MessageMapping("/game/{id}/move")
    public void move(@DestinationVariable Long id, MoveDTO move) {
        var board = boardService.getById(id);
        if (board.isEmpty()) {
            sendError(id, "Game with this ID does not exist");
            return;
        }


        boardService.playerMove(move, board.get(), this::sendError);
        if (board.get().getState() == State.ENDED)
            sendResult(id, board.get());

        sendGameState(id, board.get());
    }


    @MessageMapping("/game/{id}/surrender")
    public void surrender(@DestinationVariable Long id, String username) {
        var board = boardService.getById(id);
        if (board.isEmpty()) {
            sendError(id, "Game with this ID dose not exist");
            return;
        }
        var user = userService.getByUsername(username);
        if (user.isEmpty()) {
            sendError(id, "User " + username + " dose not exist");
            return;
        }

        boardService.playerSurrender(user.get(), board.get());
        sendGameState(id, board.get());
        sendResult(id, board.get());
    }

    private void sendGameState(Long gameId, Board board) {
        var dest = "/topic/" + gameId + "/game-state";
        var gameState = new GameDTO(board);
        messagingTemplate.convertAndSend(dest, gameState);
    }

    private void sendError(Long gameId, String errorMsg) {
        var dest = "/topic/" + gameId + "/error";
        messagingTemplate.convertAndSend(dest, errorMsg);
    }

    private void sendResult(Long gameId, Board board) {
        var dest = "/topic/" + gameId + "/result";
        var result = new ResultDTO(board);
        messagingTemplate.convertAndSend(dest, result);
    }

}
