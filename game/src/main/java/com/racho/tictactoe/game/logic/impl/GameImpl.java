package com.racho.tictactoe.game.logic.impl;

import com.racho.tictactoe.game.logic.Game;
import com.racho.tictactoe.game.logic.GameState;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by aron on 5/18/15.
 */
public class GameImpl implements Game {

    @Override
    public Match createMatch(String challengerPlayer, String challengedPlayer) {
        String matchID = challengerPlayer + "_" + challengedPlayer;
        Match match = new Match( matchID, challengerPlayer, challengedPlayer );
        match.setMatchStartTs(new Date().getTime());

        gameDAO.createMatch( match );

        GameController controller = new GameController();
        controller.create(challengerPlayer, challengedPlayer);
        gameDAO.saveGame(match.getMatchID(), controller.getBoardData());

        return match;
    }

    @Override
    public String getMatchTurnOwner(String matchID) {
        GameController controller = getGameController(matchID);
        return controller.getTurnOwner();
    }

    @Override
    public void performMove(String matchID, String player, int x, int y) throws IllegalMoveException {
        GameController controller = getGameController(matchID);
        if ( controller.getTurnOwner().equals(player) ) {
            controller.doMove(x, y);
            gameDAO.saveGame(matchID, controller.getBoardData());
        } else {
            throw new IllegalMoveException("Not player " + player + "'s turn");
        }

        if ( controller.getState() == GameState.won || controller.getState() == GameState.stalemate ) {
            // end of the game
            // todo
            // inform lobby that the game is complete
            // ...
        }
    }

    @Override
    public GameStatus getMatchStatus(String matchID) {
        Match match = gameDAO.getMatch(matchID);

        GameController controller = getGameController(matchID);
        GameState state = controller.getState();
        String winner = controller.getWinner();
        String turnOwner = controller.getTurnOwner();
        List<String> winningCells = controller.getWinningCells();

        GameStatus status = new GameStatus();
        status.setState(state);
        status.setWinner(winner);
        status.setWinningCells(winningCells);
        status.setTurnOwner(turnOwner);
        status.setMatch(match);
        status.setBoardData((JSONObject) controller.getBoardData().get("board"));

        return status;
    }

    @Override
    public GameStatus stopMatch(String matchID) {
        Match match = gameDAO.getMatch(matchID);
        match.setState(MatchState.stopped);
        gameDAO.updateMatch(match);

        return getMatchStatus(matchID);
    }

    private GameController getGameController(String matchID) {
        JSONObject gameData = gameDAO.getGame( matchID );
        GameController controller = new GameController();
        controller.setBoardData(gameData);
        return controller;
    }

    // ------------------------------------------------------------------------------------------------------
    // dependencies

    private GameDAOImpl gameDAO;

    @Inject
    public void setGameDAO( GameDAOImpl gameDAO ) {
        this.gameDAO = gameDAO;
    }

}
