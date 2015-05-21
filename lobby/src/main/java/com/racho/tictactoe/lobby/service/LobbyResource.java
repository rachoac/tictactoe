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

    @POST
    @Path("/join")
    public Player join(@QueryParam("playerName") String playerName);

    @DELETE
    @Path("/join")
    public void remove(@QueryParam("playerName") String playerName);

    /**
     * Challenge a player
     * @return
     */
    @POST
    @Path("/players/{challengedPlayerName}/challenge")
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
    public String challengeStatus(@PathParam("challengeID") String challengeID);

    /**
     * Retrieves a player challenge (first nonexpired challenge)
     * @return
     */
    @GET
    @Path("/players/{challengedPlayerName}/challenge")
    public Response getChallenge(
            @PathParam("challengedPlayerName") String challengedPlayerName
    );

    /**
     * Return list of players in the lobby
     * @return
     */
    @GET
    @Path("/players")
    public List<Player> getPlayers();

    /**
     * Accepts or rejects player challenge
     * @return
     */
    @PUT
    @Path("/challenge/{challengeID}")
    public void acceptChallenge(
            @PathParam("challengeID") String challengeID,
            @QueryParam("response") String response
    );

}
