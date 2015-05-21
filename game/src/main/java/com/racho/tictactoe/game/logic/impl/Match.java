package com.racho.tictactoe.game.logic.impl;

/**
 * Created by aron on 5/18/15.
 */
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

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getChallengerPlayer() {
        return challengerPlayer;
    }

    public void setChallengerPlayer(String challengerPlayer) {
        this.challengerPlayer = challengerPlayer;
    }

    public String getChallengedPlayer() {
        return challengedPlayer;
    }

    public void setChallengedPlayer(String challengedPlayer) {
        this.challengedPlayer = challengedPlayer;
    }

    public long getMatchStartTs() {
        return matchStartTs;
    }

    public void setMatchStartTs(long matchStartTs) {
        this.matchStartTs = matchStartTs;
    }
}
