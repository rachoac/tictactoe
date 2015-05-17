package com.racho.tictactoe;

import com.google.inject.AbstractModule;
import com.racho.tictactoe.lobby.logic.Lobby;
import com.racho.tictactoe.lobby.logic.LobbyDAO;
import com.racho.tictactoe.lobby.logic.impl.LobbyDAOImpl;
import com.racho.tictactoe.lobby.logic.impl.LobbyImpl;

/**
 * Created by aron on 5/16/15.
 */
public class LiveModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Lobby.class).to(LobbyImpl.class);
        bind(LobbyDAO.class).to(LobbyDAOImpl.class);
    }
}
