package com.racho.tictactoe.game;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.racho.tictactoe.game.service.GameResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

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

        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
