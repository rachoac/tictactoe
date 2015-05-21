package com.racho.tictactoe;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.racho.tictactoe.lobby.logic.GameServiceClient;
import com.racho.tictactoe.lobby.logic.impl.GameServiceClientImpl;
import com.racho.tictactoe.lobby.logic.Lobby;
import com.racho.tictactoe.lobby.logic.LobbyDAO;
import com.racho.tictactoe.lobby.logic.impl.LobbyDAOImpl;
import com.racho.tictactoe.lobby.logic.impl.LobbyImpl;
import com.racho.tictactoe.lobby.service.LobbyResource;
import com.racho.tictactoe.lobby.service.LobbyResourceImpl;

import javax.inject.Named;

/**
 * Created by aron on 5/16/15.
 */
public class LiveModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Lobby.class).to(LobbyImpl.class);
        bind(LobbyDAO.class).to(LobbyDAOImpl.class);
        bind(LobbyResource.class).to(LobbyResourceImpl.class);
        bind(GameServiceClient.class).to(GameServiceClientImpl.class);
        bind(String.class).annotatedWith(Names.named("gameServiceURL"))
                .toInstance(System.getenv("GAME_SERVICE_URL"));
    }

}
