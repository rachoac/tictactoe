package com.racho.tictactoe.lobby.logic;

/**
 * Created by aron on 5/16/15.
 */
public interface Lobby {

    public boolean isPlayerJoined( String playerName );

    public Player joinPlayer( String playerName);

    public void removePlayer( String playerName );

    public Challenge createChallenge( String challengerPlayer, String challengedPlayer );

    public Challenge getChallengeFor( String challengedPlayer );

    public boolean removeExpiredChallenge( String challengeID );

    public boolean isChallengeAccepted( String challengeID );

    boolean acceptChallenge(String challengeID);

    boolean rejectChallenge(String challengeID);

    void removeChallenge(String challengeID);
}