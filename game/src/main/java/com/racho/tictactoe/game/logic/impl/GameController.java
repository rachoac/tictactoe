package com.racho.tictactoe.game.logic.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.racho.tictactoe.game.logic.GameState;
import org.json.simple.JSONObject;

public class GameController {

    private TicTacToeBoard board;
    private String turnOwner;
    private String playerOne;
    private String playerTwo;

    public GameController() {
    }

    @JsonProperty
    public JSONObject getBoardData() {
        if ( board == null ) {
            board = new TicTacToeBoard();
        }

        JSONObject controllerData = new JSONObject();
        controllerData.put("playerOne", playerOne);
        controllerData.put("playerTwo", playerTwo);

        JSONObject serialize = board.serialize();
        controllerData.put("board", serialize);
        controllerData.put("turnOwner", turnOwner);

        return controllerData;
    }

    public void setBoardData(JSONObject data) {
        if ( board == null ) {
            board = new TicTacToeBoard();
        }
        if ( data == null ) {
            return;
        }
        if ( data.get("board") != null ) {
            board.deserialize((JSONObject) data.get("board"));
        }
        playerOne = (String) data.get("playerOne");
        playerTwo = (String) data.get("playerTwo");
        turnOwner = (String) data.get("turnOwner");
    }

    public void create(String playerOne, String playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        if ( board == null ) {
            board = new TicTacToeBoard();
        } else {
            board.reset();
        }
    }

    private void nextTurn() {
        if ( turnOwner == null ) {
            turnOwner = playerOne;
            return;
        }

        if ( turnOwner.equals(playerOne ) ) {
            turnOwner = playerTwo;
        } else {
            turnOwner = playerOne;
        }
    }

    public String getTurnOwner() {
        if ( turnOwner == null ) {
            turnOwner = playerOne;
        }
        return turnOwner;
    }

    public void doMove( int x, int y) {
        board.performMove(getTurnOwner(), x, y);
        nextTurn();
    }

    public GameState getState() {
        return board.getState();
    }

    public String getWinner() {
        return board.getWinner();
    }

}
