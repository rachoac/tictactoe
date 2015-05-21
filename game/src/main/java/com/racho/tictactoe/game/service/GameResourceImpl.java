package com.racho.tictactoe.game.service;

import com.google.inject.Singleton;
import com.racho.tictactoe.game.logic.impl.Match;

/**
 * Created by aron on 5/19/15.
 */
@Singleton
public class GameResourceImpl implements GameResource {

    @Override
    public Match createMatch(String challengerPlayer, String challengedPlayer) {
        return null;
    }
}
