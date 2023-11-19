package com.example.back.dto;

import com.example.back.entity.Board;
import com.example.back.entity.GameResult;
import com.example.back.entity.Result;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ResultDTO(GameDTO gameDTO, Map<String, GameResult> results) {
    public ResultDTO(Board board) {
        this(new GameDTO(board), board.getResults().stream().collect(Collectors.toMap(result -> result.getUser().getUsername(), Result::getGameResult)));
    }
}
