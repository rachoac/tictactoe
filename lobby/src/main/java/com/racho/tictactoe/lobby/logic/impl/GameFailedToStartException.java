package com.racho.tictactoe.lobby.logic.impl;

/**
 * Created by aron on 5/20/15.
 */
public class GameFailedToStartException extends RuntimeException {

    public GameFailedToStartException(String s) {
        super(s);
    }
}
