package com.racho.tictactoe.lobby.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;

/**
 * Created by aron on 5/16/15.
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class LobbyResource {

    @POST
    @Path("/join")
    public Response join() {
        return null;
    }

    /**
     * Retrieves the status of a challenge
     * @return
     */
    @GET
    @Path("/challenge/{challengeID}/status")
    public Response challengeStatus() {
        return null;
    }

    /**
     * Retrieves an offered challenge
     * @return
     */
    @GET
    @Path("/challenge")
    public Response getChallenge() {
        return null;
    }

    @PUT
    public Response xx() {
        return null;
    }

}
