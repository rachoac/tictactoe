package com.racho.tictactoe.lobby.logic.impl;

/**
 * Created by aron on 5/16/15.
 */
public class ChallengeNotFoundException extends RuntimeException {

    public ChallengeNotFoundException(String msg) {
        super(msg);
    }
}
