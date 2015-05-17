package com.racho.tictactoe.lobby.service;

import com.racho.tictactoe.lobby.logic.Challenge;
import com.racho.tictactoe.lobby.logic.Lobby;
import com.racho.tictactoe.lobby.logic.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.xml.ws.Response;

/**
 * Created by aron on 5/16/15.
 */
@Singleton
public class LobbyResourceImpl implements LobbyResource {

    public Player join(String playerName) {
        return lobby.joinPlayer(playerName);
    }

    /**
     * Retrieves the status of a challenge
     * @return
     */

    public Challenge challengePlayer(
            @PathParam("challengedPlayerName") String challengedPlayerName,
            @QueryParam("challengerPlayerName") String challengerPlayerName
    ) {
        return lobby.createChallenge(challengerPlayerName, challengedPlayerName);
    }

    public Response challengeStatus() {
        return null;
    }

    /**
     * Retrieves an offered challenge
     * @return
     */
    public Response getChallenge() {
        return null;
    }

    // ------------------------------------------------------------------------------------------------------
    // dependencies

    private Lobby lobby;

    @Inject
    public void setLobby( Lobby lobby ) {
        this.lobby = lobby;
    }
}
