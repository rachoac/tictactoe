package com.racho.tictactoe.lobby.logic.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {

    private String matchID;
    private String challengerPlayer;
    private String challengedPlayer;
    private long matchStartTs;

    public Match(String matchID, String challengerPlayer, String challengedPlayer) {
        this.matchID = matchID;
        this.challengerPlayer = challengerPlayer;
        this.challengedPlayer = challengedPlayer;
    }

    public Match() {
        // for deserialization
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
}
