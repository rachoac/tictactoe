package com.racho.tictactoe.game.logic.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.racho.tictactoe.game.logic.GameState;
import org.json.simple.JSONObject;

/**
 * Created by aron on 5/21/15.
 */
public class GameStatus {

    private GameState state;
    private String winner;
    private String turnOwner;
    private Match match;
    private JSONObject boardData;

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

    @JsonProperty
    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @JsonProperty
    public JSONObject getBoardData() {
        return boardData;
    }

    public void setBoardData(JSONObject boardData) {
        this.boardData = boardData;
    }
}
