package com.racho.tictactoe.lobby.service;

import com.racho.tictactoe.lobby.logic.Challenge;
import com.racho.tictactoe.lobby.logic.Player;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;

/**
 * Created by aron on 5/16/15.
 */
@Path("/lobby")
@Produces(MediaType.APPLICATION_JSON)
public interface LobbyResource {

    @POST
    @Path("/join")
    public Player join(@QueryParam("playerName") String playerName);

    /**
     * Challenge a player
     * @return
     */
    @POST
    @Path("/player/{challengedPlayerName}/challenge")
    public Challenge challengePlayer(
            @PathParam("challengedPlayerName") String challengedPlayerName,
            @QueryParam("challengerPlayerName") String challengerPlayerName
    );

    /**
     * Retrieves the status of a challenge
     * @return
     */
    @GET
    @Path("/challenge/{challengeID}/status")
    public Response challengeStatus();

    /**
     * Retrieves an offered challenge
     * @return
     */
    @GET
    @Path("/challenge")
    public Response getChallenge();

}
