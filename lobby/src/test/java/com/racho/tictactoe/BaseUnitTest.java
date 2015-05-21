package com.racho.tictactoe;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.racho.tictactoe.lobby.logic.GameServiceClient;
import com.racho.tictactoe.lobby.logic.Lobby;
import com.racho.tictactoe.lobby.logic.LobbyDAO;
import com.racho.tictactoe.lobby.logic.impl.GameServiceClientImpl;
import com.racho.tictactoe.lobby.logic.impl.LobbyDAOImpl;
import com.racho.tictactoe.lobby.logic.impl.LobbyImpl;
import com.racho.tictactoe.lobby.logic.impl.TestLobbyDAOImpl;
import com.racho.tictactoe.lobby.service.LobbyResource;
import com.racho.tictactoe.lobby.service.LobbyResourceImpl;
import org.junit.Before;

/**
 * Created by aron on 5/16/15.
 */
public class BaseUnitTest {

    protected Injector injector;

    @Before
    public void setup() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Lobby.class).to(LobbyImpl.class);
                bind(LobbyDAO.class).to(TestLobbyDAOImpl.class);
                bind(LobbyResource.class).to(LobbyResourceImpl.class);
                bind(GameServiceClient.class).to(GameServiceClientImpl.class);
                bind(String.class).annotatedWith(Names.named("gameServiceURL"))
                        .toInstance("http://localhost");
            }
        });
    }
}
