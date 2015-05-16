package com.racho.tictactoe;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by aron on 5/15/15.
 */
public class TicTacToeApplication extends Application<TicTacToeConfiguration> {
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