package com.racho.tictactoe.game.logic.impl;

import com.racho.tictactoe.game.logic.Game;

import javax.inject.Inject;
import java.util.Date;

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

        return match;
    }

    // ------------------------------------------------------------------------------------------------------
    // dependencies

    private GameDAOImpl gameDAO;

    @Inject
    public void setGameDAO( GameDAOImpl gameDAO ) {
        this.gameDAO = gameDAO;
    }

}
