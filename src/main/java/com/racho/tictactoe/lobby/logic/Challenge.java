package com.racho.tictactoe.lobby.logic;

/**
 * Created by aron on 5/16/15.
 */
public class Challenge {
    public static long CHALLENGE_TIMEOUT_MS = 10 * 1000; // 10 seconds

    private String challengeID;
    private String challengerPlayer;
    private String challengedPlayer;
    private long challengeTs;
    private ChallengeStatus challengeStatus;

    public Challenge(String challengeID, String challengerPlayer, String challengedPlayer, long challengeTs) {
        this.challengeID = challengeID;
        this.challengerPlayer = challengerPlayer;
        this.challengedPlayer = challengedPlayer;
        this.challengeTs = challengeTs;
    }

    public long getChallengeTs() {
        return challengeTs;
    }

    public void setChallengeTs(long challengeTs) {
        this.challengeTs = challengeTs;
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

    public ChallengeStatus getChallengeStatus() {
        return this.challengeStatus;
    }

    public ChallengeStatus getChallengeStatusthis() {
        return this.challengeStatus;
    }

    public void setChallengeStatusthis(ChallengeStatus challengeStatus) {
        this.challengeStatus = challengeStatus;
    }

    public boolean isChallengeExpired() {
        return Util.elapsed(getChallengeTs(), CHALLENGE_TIMEOUT_MS);
    }

    public String getChallengeID() {
        return challengeID;
    }

    public void setChallengeID(String challengeID) {
        this.challengeID = challengeID;
    }
}
