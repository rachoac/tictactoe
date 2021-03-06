package com.racho.tictactoe;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.inject.Named;

/**
 * Created by aron on 5/15/15.
 */
public class LobbyConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String gameServiceURL;

    @NotEmpty
    private String defaultName = "Stranger";

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty
    public String getGameServiceURL() {
        return gameServiceURL;
    }

    public void setGameServiceURL(String gameServiceURL) {
        this.gameServiceURL = gameServiceURL;
    }
}
