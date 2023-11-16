package com.example.back;

import com.example.back.entity.Board;
import com.example.back.entity.Sign;
import com.example.back.service.BoardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class BoardServiceTest {

    private final BoardService boardService = new BoardService(null, null, null);

    @Test
    public void givenEmptyBoard() {
        Assertions.assertEquals(Optional.empty(), boardService.checkForWinner(new Board()));
    }

    @Test
    public void givenXRowWinBoard() {
        var list = new ArrayList<Sign>();
        list.add(Sign.X);
        list.add(Sign.X);
        list.add(Sign.X);
        list.add(Sign.O);
        list.add(Sign.O);
        list.add(null);
        list.add(Sign.O);
        list.add(null);
        list.add(null);
        Assertions.assertEquals(Optional.of(Sign.X), boardService.checkForWinner(Board.builder().grid(list).build()));
    }

    @Test
    public void givenODiagonalWinBoard() {
        var list = new ArrayList<Sign>();
        list.add(Sign.X);
        list.add(Sign.X);
        list.add(Sign.O);
        list.add(Sign.X);
        list.add(Sign.O);
        list.add(null);
        list.add(Sign.O);
        list.add(null);
        list.add(null);
        Assertions.assertEquals(Optional.of(Sign.O), boardService.checkForWinner(Board.builder().grid(list).build()));
    }

    @Test
    public void givenOColWinBoard() {
        var list = new ArrayList<Sign>();
        list.add(Sign.X);
        list.add(Sign.X);
        list.add(Sign.O);
        list.add(Sign.X);
        list.add(null);
        list.add(Sign.O);
        list.add(null);
        list.add(null);
        list.add(Sign.O);
        Assertions.assertEquals(Optional.of(Sign.O), boardService.checkForWinner(Board.builder().grid(list).build()));
    }

    @Test
    public void isGridFullWhenEmptyTest() {
        Assertions.assertFalse(boardService.isGridFull(new Board()));
    }

    @Test
    public void isGridFullWhenFullTest() {
        Assertions.assertTrue(boardService.isGridFull(Board.builder().grid(new ArrayList<>(Collections.nCopies(9, Sign.X))).build()));
    }
}
