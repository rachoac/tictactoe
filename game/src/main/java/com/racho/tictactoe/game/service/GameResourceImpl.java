package com.racho.tictactoe.game.service;

import com.google.inject.Singleton;
import com.racho.tictactoe.game.logic.Game;
import com.racho.tictactoe.game.logic.impl.Match;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

/**
 * Created by aron on 5/19/15.
 */
@Singleton
public class GameResourceImpl implements GameResource {

    @Override
    public Response createMatch(String challengerPlayer, String challengedPlayer) {
        Match match = game.createMatch(challengerPlayer, challengedPlayer);
        return Response.ok( match).build();
    }

    @Override
    public Response getMatchTurnOwner(String matchID) {
        return Response.ok( game.getMatchTurnOwner(matchID) ).build();
    }

    @Override
    public Response performMove(String matchID, String player, int x, int y) {
        game.performMove(matchID, player, x, y);
        return Response.ok(game.getMatchStatus(matchID)).build();
    }

    @Override
    public Response getMatchStatus(String matchID) {
        return Response.ok(game.getMatchStatus(matchID)).build();
    }

    @Override
    public Response stopMatch(String matchID) {
        return Response.ok(game.stopMatch(matchID)).build();
    }

    private Game game;

    @Inject
    public void setGame( Game game ) {
        this.game = game;
    }
}
