package com.racho.tictactoe.game.service;

import com.racho.tictactoe.game.logic.impl.Match;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by aron on 5/19/15.
 */
@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
public interface GameResource {

    @POST
    @Path("/match/create")
    public Match createMatch( @FormParam("challengerPlayer") String challengerPlayer,
                              @FormParam("challengedPlayer") String challengedPlayer );

}
