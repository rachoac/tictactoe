package com.racho.tictactoe.lobby.service;

import com.racho.tictactoe.lobby.logic.Challenge;
import com.racho.tictactoe.lobby.logic.Lobby;
import com.racho.tictactoe.lobby.logic.Player;
import com.racho.tictactoe.lobby.logic.impl.ChallengeNotFoundException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.function.Function;

import static com.racho.tictactoe.lobby.logic.ChallengeStatus.accepted;
import static com.racho.tictactoe.lobby.logic.ChallengeStatus.rejected;

/**
 * Created by aron on 5/16/15.
 */
@Singleton
public class LobbyResourceImpl implements LobbyResource {

    public Player join(String playerName) {
        return lobby.joinPlayer(playerName);
    }

    public void remove(String playerName) {
        lobby.removePlayer(playerName);
    }

    /**
     * Retrieves the status of a challenge
     * @return
     */

    public Challenge challengePlayer(
            String challengedPlayerName,
            String challengerPlayerName
    ) {
        return lobby.createChallenge(challengerPlayerName, challengedPlayerName);
    }

    @Override
    public String challengeStatus(String challengeID) {
        Challenge challenge = lobby.getChallenge(challengeID);
        if ( challenge == null ) {
            throw new ChallengeNotFoundException("ChallengeID " + challengeID + " not found");
        }
        return challenge.getChallengeStatus() != null ? challenge.getChallengeStatus().toString() : "pending";
    }

    public Response getChallenge(
            String challengedPlayerName
    ) {
        Challenge challenge = lobby.getChallengeFor(challengedPlayerName);
        if ( challenge == null ) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Challenge not found for player " + challengedPlayerName).build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity(challenge).build();
        }
    }

    public void acceptChallenge(
            String challengeID,
            String response
    ) {
        if (accepted.toString().equalsIgnoreCase(response) ) {
            lobby.acceptChallenge(challengeID);
        } else if (rejected.toString().equalsIgnoreCase(response) ) {
            lobby.rejectChallenge(challengeID);
        } else {
            throw new IllegalArgumentException("Invalid challenge response");
        }
    }

    public List<Player> getPlayers() {
        return lobby.getJoinedPlayers();
    }

    // ------------------------------------------------------------------------------------------------------
    // dependencies

    private Lobby lobby;

    @Inject
    public void setLobby( Lobby lobby ) {
        this.lobby = lobby;
    }
}
