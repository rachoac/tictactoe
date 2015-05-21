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

    public Response join(String playerName) {
        Player player = lobby.joinPlayer(playerName);
        return Response.ok(player).build();
    }

    public void remove(String playerName) {
        lobby.removePlayer(playerName);
    }

    /**
     * Retrieves the status of a challenge
     * @return
     */

    public Response challengePlayer(
            String challengedPlayerName,
            String challengerPlayerName
    ) {
        return Response.ok(
            lobby.createChallenge(challengerPlayerName, challengedPlayerName)
        ).build();
    }

    @Override
    public Response challengeStatus(String challengeID) {
        Challenge challenge = lobby.getChallenge(challengeID);
        if ( challenge == null ) {
            throw new ChallengeNotFoundException("ChallengeID " + challengeID + " not found");
        }
        return Response.ok(
                challenge.getChallengeStatus() != null ? challenge.getChallengeStatus().toString() : "pending"
        ).build();
    }

    @Override
    public Response challengeMatchID(String challengeID) {
        return Response.ok(
            lobby.getMatchIDForChallenge(challengeID)
        ).build();
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

    public Response acceptChallenge(
            String challengeID,
            String response
    ) {
        if (accepted.toString().equalsIgnoreCase(response) ) {
            return Response.ok(
                lobby.acceptChallenge(challengeID)
            ).build();
        } else if (rejected.toString().equalsIgnoreCase(response) ) {
            lobby.rejectChallenge(challengeID);
            return null;
        } else {
            throw new IllegalArgumentException("Invalid challenge response");
        }
    }

    public Response getPlayers() {
        return Response.ok(
            lobby.getJoinedPlayers()
        ).build();
    }

    // ------------------------------------------------------------------------------------------------------
    // dependencies

    private Lobby lobby;

    @Inject
    public void setLobby( Lobby lobby ) {
        this.lobby = lobby;
    }
}
