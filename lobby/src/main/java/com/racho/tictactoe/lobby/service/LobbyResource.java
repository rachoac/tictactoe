package com.racho.tictactoe.lobby.service;

import com.racho.tictactoe.lobby.logic.Challenge;
import com.racho.tictactoe.lobby.logic.Player;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by aron on 5/16/15.
 */
@Path("/lobby")
@Produces(MediaType.APPLICATION_JSON)
public interface LobbyResource {

    /**
     * Return list of players in the lobby
     * @return
     */
    @GET
    @Path("/roster")
    public Response getPlayers();


    /**
     * Joins a player to the lobby, making them avaiable for game challenges
     * @param playerName
     * @return
     */
    @POST
    @Path("/roster")
    public Response join(@QueryParam("playerName") String playerName);

    /**
     * Removes a player from the lobby, making them unavailable for game challenges
     * @param playerName
     */
    @DELETE
    @Path("/roster")
    public void remove(@QueryParam("playerName") String playerName);

    /**
     * Challenge a player
     * @return
     */
    @POST
    @Path("/roster/{challengedPlayerName}/challenge")
    public Response challengePlayer(
            @PathParam("challengedPlayerName") String challengedPlayerName,
            @QueryParam("challengerPlayerName") String challengerPlayerName
    );

    /**
     * Retrieves a player challenge (first nonexpired challenge)
     * @return
     */
    @GET
    @Path("/roster/{challengedPlayerName}/challenge")
    public Response getChallenge(
            @PathParam("challengedPlayerName") String challengedPlayerName
    );


    /**
     * Retrieves the status of a challenge
     * @return
     */
    @GET
    @Path("/challenge/{challengeID}/status")
    public Response challengeStatus(@PathParam("challengeID") String challengeID);


    @GET
    @Path("/challenge/{challengeID}/matchID")
    public Response challengeMatchID(@PathParam("challengeID") String challengeID);

    /**
     * Accepts or rejects player challenge
     * @return matchID
     */
    @PUT
    @Path("/challenge/{challengeID}")
    public Response acceptChallenge(
            @PathParam("challengeID") String challengeID,
            @QueryParam("response") String response
    );


}
