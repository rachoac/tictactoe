package com.racho.tictactoe;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.racho.tictactoe.lobby.logic.Lobby;
import com.racho.tictactoe.lobby.logic.impl.LobbyImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by aron on 5/15/15.
 */
public class TicTacToeApplication extends Application<TicTacToeConfiguration> {
    public static void main(String[] args) throws Exception {


        Injector injector = Guice.createInjector(new AppInjector());
        Lobby x = injector.getInstance( Lobby.class );

        new TicTacToeApplication().run(args);
    }

    @Override
    public String getName() {
        return "tic-tac-toe";
    }

    @Override
    public void initialize(Bootstrap<TicTacToeConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(TicTacToeConfiguration configuration,
                    Environment environment) {

        final TicTacToeResource resource = new TicTacToeResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(resource);
    }
}
