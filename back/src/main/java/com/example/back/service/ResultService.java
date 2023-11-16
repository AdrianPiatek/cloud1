package com.example.back.service;

import com.example.back.entity.*;
import com.example.back.exception.WrongSignException;
import com.example.back.repository.ResultRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResultService {
    private ResultRepository resultRepository;

    public List<Result> getUserResults(User user){
        return resultRepository.findAllByUser(user);
    }

    private void save(Result result){
        resultRepository.save(result);
    }

    private Result addResult(User player, GameResult gameResult, Board board){
        var result = Result.builder().user(player).gameResult(gameResult).board(board).build();
        save(result);
        return result;
    }

    private List<Result> addResultWon(User winner, User loser, Board board){
        var results = new ArrayList<Result>();
        results.add(addResult(winner, GameResult.WON, board));
        results.add(addResult(loser, GameResult.LOST, board));
        return results;
    }

    public List<Result> setResultDraw(Board board){
        var results = new ArrayList<Result>();
        addResult(board.getPlayer1(), GameResult.DRAW, board);
        addResult(board.getPlayer2(), GameResult.DRAW, board);
        return results;
    }

    @SneakyThrows
    public List<Result> setResultWon(Sign winnerSign, Board board) {
        switch (winnerSign){
            case X -> {
                return addResultWon(board.getPlayer1(), board.getPlayer2(), board);
            }
            case O -> {
                return addResultWon(board.getPlayer2(), board.getPlayer1(), board);
            }
            default -> throw new WrongSignException("Empty Sign is not for playerTurn");
        }
    }

    public Map<GameResult, Long> getUserResultsCount(User user){
        return getUserResults(user).stream().collect(Collectors.groupingBy(Result::getGameResult, Collectors.counting()));
    }
}
