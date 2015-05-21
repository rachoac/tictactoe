package com.racho.tictactoe;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.racho.tictactoe.lobby.service.LobbyResource;
import de.thomaskrille.dropwizard.environment_configuration.EnvironmentConfigurationFactoryFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by aron on 5/15/15.
 */
public class LobbyApplication extends Application<LobbyConfiguration> {

    private GuiceBundle<LobbyConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        new LobbyApplication().run(args);
    }

    @Override
    public String getName() {
        return "tic-tac-toe";
    }

    @Override
    public void initialize(Bootstrap<LobbyConfiguration> bootstrap) {
        // nothing to do yet
        bootstrap.setConfigurationFactoryFactory(new EnvironmentConfigurationFactoryFactory());

        guiceBundle = GuiceBundle.<LobbyConfiguration>newBuilder()
                .addModule(new LiveModule())
                .setConfigClass(LobbyConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(LobbyConfiguration configuration,
                    Environment environment) {
        environment.jersey().register(LobbyResource.class);
    }
}
