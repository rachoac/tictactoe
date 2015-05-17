package com.racho.tictactoe;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.racho.tictactoe.lobby.logic.Lobby;
import com.racho.tictactoe.lobby.logic.LobbyDAO;
import com.racho.tictactoe.lobby.logic.impl.LobbyImpl;
import com.racho.tictactoe.lobby.logic.impl.TestLobbyDAOImpl;
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
            }
        });
    }
}
