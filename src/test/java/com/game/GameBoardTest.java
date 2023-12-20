package com.game;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.*;

public class GameBoardTest {
    private GameBoard gameBoard;

    @Before
    public void setUp() {
        gameBoard = new GameBoard();
    }

    @Test
    public void testInitialEmptyBoard() {
        char[][] board = gameBoard.getBoard();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals('-', board[i][j]);
            }
        }
    }

    @Test
    public void testMakeMove() {
        assertTrue(gameBoard.makeMove(0, 0, 'x'));
        assertEquals('x', gameBoard.getBoard()[0][0]);
    }

    @Test
    public void testInvalidMove() {
        // Make a move outside the board
        assertFalse(gameBoard.makeMove(-1, 0, 'x'));
        assertFalse(gameBoard.makeMove(0, -1, 'x'));
        assertFalse(gameBoard.makeMove(10, 0, 'x'));
        assertFalse(gameBoard.makeMove(0, 10, 'x'));

        // Make a move on a non-empty cell
        gameBoard.makeMove(0, 0, 'x');
        assertFalse(gameBoard.makeMove(0, 0, 'o'));
    }

    @Test
    public void testCheckWinHorizontal() {
        // Test for a horizontal win
        assertFalse(gameBoard.checkWin('x'));
        for (int i = 0; i < 5; i++) {
            gameBoard.makeMove(i, 0);
            gameBoard.makeMove(i, 1);
        }
        assertTrue(gameBoard.checkWin('x'));
    }

    @Test
    public void testCheckWinVertical() {
        // Test for a vertical win
        assertFalse(gameBoard.checkWin('o'));
        for (int i = 0; i < 5; i++) {
            gameBoard.makeMove(0, i);
            gameBoard.makeMove(1, i);
        }
        assertTrue(gameBoard.checkWin('o'));
    }

    @Test
    public void testCheckWinDiagonal() {
        // Test for a diagonal win
        assertFalse(gameBoard.checkWin('x'));
        for (int i = 0; i < 5; i++) {
            gameBoard.makeMove(i, i);
            gameBoard.makeMove(9 - i,9 - i);
        }
        assertTrue(gameBoard.checkWin('x'));
    }

    @Test
    public void testCheckFull() {
        assertFalse(gameBoard.checkFull());
        // Fill the board
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameBoard.makeMove(i, j);
            }
        }
        assertTrue(gameBoard.checkFull());
    }
}
