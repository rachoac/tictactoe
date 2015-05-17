package com.racho.tictactoe.lobby.logic.impl;

/**
 * Created by aron on 5/16/15.
 */
public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String msg) {
        super(msg);
    }
}
