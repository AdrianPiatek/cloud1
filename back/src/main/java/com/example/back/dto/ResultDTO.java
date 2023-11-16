package com.example.back.dto;

import com.example.back.entity.Board;
import com.example.back.entity.Result;

import java.util.List;

public record ResultDTO(GameDTO gameDTO, List<Result> results) {
    public ResultDTO(Board board) {
        this(new GameDTO(board), board.getResults());
    }
}
