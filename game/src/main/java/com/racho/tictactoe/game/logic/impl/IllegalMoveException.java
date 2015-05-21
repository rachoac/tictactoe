package com.racho.tictactoe.game.logic.impl;

/**
 * Created by aron on 5/20/15.
 */
public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(String s) {
        super(s);
    }
}
