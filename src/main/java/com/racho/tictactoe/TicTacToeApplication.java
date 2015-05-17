package com.racho.tictactoe;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.racho.tictactoe.lobby.service.LobbyResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by aron on 5/15/15.
 */
public class TicTacToeApplication extends Application<TicTacToeConfiguration> {

    private GuiceBundle<TicTacToeConfiguration> guiceBundle;


    public static void main(String[] args) throws Exception {
        new TicTacToeApplication().run(args);
    }

    @Override
    public String getName() {
        return "tic-tac-toe";
    }

    @Override
    public void initialize(Bootstrap<TicTacToeConfiguration> bootstrap) {
        // nothing to do yet
        guiceBundle = GuiceBundle.<TicTacToeConfiguration>newBuilder()
                .addModule(new LiveModule())
                .setConfigClass(TicTacToeConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(TicTacToeConfiguration configuration,
                    Environment environment) {
        environment.jersey().register(LobbyResource.class);
    }
}
