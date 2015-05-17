package com.racho.tictactoe.lobby.logic;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aron on 5/16/15.
 */
public class Player {
    private String playerName;
    private long joinedTs;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    @JsonProperty
    public String getPlayerName() {
        return this.playerName;
    }

    @JsonProperty
    public long getJoinedTs() {
        return joinedTs;
    }

    public void setJoinedTs(long joinedTs) {
        this.joinedTs = joinedTs;
    }
}
