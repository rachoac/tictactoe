package com.racho.tictactoe.game.service;

import com.racho.tictactoe.game.logic.impl.Match;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by aron on 5/19/15.
 */
@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
public interface GameResource {

    @POST
    @Path("/match/create")
    public Response createMatch( @FormParam("challengerPlayer") String challengerPlayer,
                                 @FormParam("challengedPlayer") String challengedPlayer );

    @GET
    @Path("/match/{matchID}/turnOwner")
    Response getMatchTurnOwner( @PathParam("matchID") String matchID);

    @PUT
    @Path("/match/{matchID}/move")
    Response performMove( @PathParam("matchID") String matchID, @QueryParam("player") String player, @QueryParam("x") int x, @QueryParam("y") int y);

    @GET
    @Path("/match/{matchID}/status")
    Response getMatchStatus( @PathParam("matchID") String matchID);
}
