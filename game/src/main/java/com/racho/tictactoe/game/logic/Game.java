package com.racho.tictactoe.game.logic;

import com.racho.tictactoe.game.logic.impl.GameStatus;
import com.racho.tictactoe.game.logic.impl.Match;

/**
 * Created by aron on 5/18/15.
 */
public interface Game {

    public Match createMatch( String challengerPlayer, String challengedPlayer );

    String getMatchTurnOwner(String matchID);

    void performMove(String matchID, String player, int x, int y);

    GameStatus getMatchStatus(String matchID);

    GameStatus stopMatch(String matchID);
}
