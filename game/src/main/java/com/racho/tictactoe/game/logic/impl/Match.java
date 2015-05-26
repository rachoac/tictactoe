package com.racho.tictactoe.game.logic.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aron on 5/18/15.
 */
public class Match {

    private String matchID;
    private String challengerPlayer;
    private String challengedPlayer;
    private long matchStartTs;
    private MatchState state;

    public Match(String matchID, String challengerPlayer, String challengedPlayer) {
        this.matchID = matchID;
        this.challengerPlayer = challengerPlayer;
        this.challengedPlayer = challengedPlayer;
        this.state = MatchState.active;
    }

    public Match() {
    }

    @JsonProperty
    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    @JsonProperty
    public String getChallengerPlayer() {
        return challengerPlayer;
    }

    public void setChallengerPlayer(String challengerPlayer) {
        this.challengerPlayer = challengerPlayer;
    }

    @JsonProperty
    public String getChallengedPlayer() {
        return challengedPlayer;
    }

    public void setChallengedPlayer(String challengedPlayer) {
        this.challengedPlayer = challengedPlayer;
    }

    @JsonProperty
    public long getMatchStartTs() {
        return matchStartTs;
    }

    public void setMatchStartTs(long matchStartTs) {
        this.matchStartTs = matchStartTs;
    }

    @JsonProperty
    public MatchState getState() {
        return state;
    }

    public void setState(MatchState state) {
        this.state = state;
    }
}
