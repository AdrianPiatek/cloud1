package com.example.back.service;

import com.example.back.dto.MoveDTO;
import com.example.back.entity.Board;
import com.example.back.entity.Sign;
import com.example.back.entity.State;
import com.example.back.entity.User;
import com.example.back.exception.PositionTakenException;
import com.example.back.repository.BoardRepository;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;
    private ResultService resultService;
    private UserService userService;

    public static String getNextPlayer(Board board) {
        return getNextSign(board.getGrid()) == Sign.X ? board.getPlayer1().getUsername() : board.getPlayer2().getUsername();
    }

    private static Sign getNextSign(List<Sign> grid) {
        return grid.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .distinct()
                .count() == 1 ? Sign.X : Sign.O;
    }

    public void save(Board board) {
        boardRepository.save(board);
    }

    public Optional<Board> getById(Long id) {
        return boardRepository.findById(id);
    }

    private final List<Integer> possibleWiningIndexes = List.of(
            0, 1, 2,
            3, 4, 5,
            6, 7, 8,
            0, 3, 7,
            1, 4, 7,
            2, 5, 8,
            0, 4, 8,
            2, 4, 6
    );

    public Optional<Sign> checkForWinner(Board board) {
        return check(board.getGrid());
    }

    private Optional<Sign> check(List<Sign> grid) {
        return Lists.partition(possibleWiningIndexes.stream().map(grid::get).toList(), 3)
                .stream()
                .map(list -> list.stream().distinct().toList())
                .filter(list -> list.size() == 1)
                .flatMap(Collection::stream)
                .filter(e -> e != Sign.EMPTY)
                .findFirst();
    }

    private void makeMove(Sign sign, int position, Board board, BiConsumer<Long, String> handleError) {
        if (!checkIfMoveIsPossible(board.getGrid(), position, sign)) {
            handleError.accept(board.getId(),"move not possible");
            return;
        }
        board.getGrid().set(position, sign);
        var optionalWinner = check(board.getGrid());
        if (optionalWinner.isPresent()) {
            board.setState(State.ENDED);
            var results = resultService.setResultWon(optionalWinner.get(), board);
            board.setResults(results);
        }
        if (isGridFull(board)) {
            board.setState(State.ENDED);
            var results = resultService.setResultDraw(board);
            board.setResults(results);
        }
        save(board);
    }

    public void playerMove(MoveDTO move, Board board, BiConsumer<Long, String> handleError) {
        makeMove(getUserSign(move.username(), board), move.position(), board, handleError);
    }

    public Sign getUserSign(String username, Board board) {
        return board.getPlayer1().getUsername().equals(username) ? Sign.X : Sign.O;
    }

    public boolean isGridFull(Board board) {
        return board.getGrid().stream().noneMatch(e -> e == Sign.EMPTY);
    }

    public Board create(User player) {
        var board = Board.builder()
                .player1(player)
                .state(State.WAITING_FOR_PLAYERS)
                .build();
        save(board);
        userService.addBoard(player, board);
        return board;
    }

    public Board create(Board oldBoard) {
        var board = Board.builder()
                .player1(oldBoard.getPlayer2())
                .player2(oldBoard.getPlayer1())
                .state(State.IN_PROGRESS)
                .build();
        save(board);
        userService.addBoard(board.getPlayer1(), board);
        userService.addBoard(board.getPlayer2(), board);
        return board;
    }

    public void join(Board board, User player) {
        board.setPlayer2(player);
        board.setState(State.IN_PROGRESS);
        save(board);
        userService.addBoard(player, board);
    }

    private boolean checkIfMoveIsPossible(List<Sign> grid, int position, Sign sign) {
        var isEmpty = grid.get(position) == Sign.EMPTY;
        var isPlayerTurn = getNextSign(grid) == sign;

        return isEmpty && isPlayerTurn;
    }

    public Optional<Board> getByState(State state) {
        return boardRepository.findByState(state);
    }

    public void playerSurrender(User user, Board board) {
        var sign = getUserSign(user.getUsername(), board) == Sign.X ? Sign.O : Sign.X;
        board.setState(State.ENDED);
        var results = resultService.setResultWon(sign, board);
        board.setResults(results);
        save(board);
    }
}
