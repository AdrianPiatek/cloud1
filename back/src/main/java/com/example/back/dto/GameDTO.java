package com.example.back.dto;

import com.example.back.entity.Board;
import com.example.back.entity.Sign;
import com.example.back.entity.State;
import com.example.back.service.BoardService;

import java.util.List;

public record GameDTO(List<Sign> grid, String playerTurn, State state, String player1, String player2, Long gameId) {
    public GameDTO(Board board){
        this(board.getGrid(),
                BoardService.getNextPlayer(board),
                board.getState(), board.getPlayer1().getUsername(),
                board.getPlayer2().getUsername(),
                board.getId()
        );
    }
}
