package com.racho.tictactoe.game;

import com.google.inject.AbstractModule;
import com.racho.tictactoe.game.service.GameResource;
import com.racho.tictactoe.game.service.GameResourceImpl;

/**
 * Created by aron on 5/16/15.
 */
public class LiveModule extends AbstractModule {

    @Override
    protected void configure() {
//        bind(Lobby.class).to(LobbyImpl.class);
//        bind(LobbyDAO.class).to(LobbyDAOImpl.class);
        bind(GameResource.class).to(GameResourceImpl.class);
    }
}
