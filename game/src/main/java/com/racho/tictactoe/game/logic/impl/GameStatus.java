package com.racho.tictactoe.game.logic.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.racho.tictactoe.game.logic.GameState;

/**
 * Created by aron on 5/21/15.
 */
public class GameStatus {

    private GameState state;
    private String winner;
    private String turnOwner;

    @JsonProperty
    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    @JsonProperty
    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getTurnOwner() {
        return turnOwner;
    }

    public void setTurnOwner(String turnOwner) {
        this.turnOwner = turnOwner;
    }
}
