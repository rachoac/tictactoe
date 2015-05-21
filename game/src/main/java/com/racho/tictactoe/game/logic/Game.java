package com.racho.tictactoe.game.logic;

import com.racho.tictactoe.game.logic.impl.Match;

/**
 * Created by aron on 5/18/15.
 */
public interface Game {

    public Match createMatch( String challengerPlayer, String challengedPlayer );

}
