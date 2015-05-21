package com.racho.tictactoe.game;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.racho.tictactoe.game.service.GameResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by aron on 5/15/15.
 */
public class GameApplication extends Application<GameConfiguration> {

    private GuiceBundle<GameConfiguration> guiceBundle;


    public static void main(String[] args) throws Exception {
        new GameApplication().run(args);
    }

    @Override
    public String getName() {
        return "tic-tac-toe-game";
    }

    @Override
    public void initialize(Bootstrap<GameConfiguration> bootstrap) {
        // nothing to do yet
        guiceBundle = GuiceBundle.<GameConfiguration>newBuilder()
                .addModule(new LiveModule())
                .setConfigClass(GameConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(GameConfiguration configuration,
                    Environment environment) {
        environment.jersey().register(GameResource.class);
    }
}
