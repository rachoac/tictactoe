package com.racho.tictactoe.lobby.logic;

import java.util.List;

/**
 * Created by aron on 5/16/15.
 */
public interface Lobby {

    /**
     * Confirm whether or not the named player is in the lobby and is ready for a game challenge
     * @param playerName
     * @return
     */
    public boolean isPlayerJoined( String playerName );

    /**
     * Join a named player to the lobby, indicating that they are ready for a game challenge
     * @param playerName
     * @return
     */
    public Player joinPlayer( String playerName);

    /**
     * Remove a named player from the lobby. This destroys any pending game challenges
     * @param playerName
     */
    public void removePlayer( String playerName );

    /**
     * Creates a challenge between two players. If the challenge already exists, then an
     * exception is thrown
     * @param challengerPlayer
     * @param challengedPlayer
     * @return
     */
    public Challenge createChallenge( String challengerPlayer, String challengedPlayer );

    /**
     * Returns the first non expired game challenge offered to a player
     * @param challengedPlayer
     * @return
     */
    public Challenge getChallengeFor( String challengedPlayer );

    Challenge getChallenge(String challengeID);

    /**
     * Removes the named challenge if its expired
     * @param challengeID
     * @return
     */
    public boolean removeExpiredChallenge( String challengeID );

    /**
     * Confirms that a challenge has been accepted. If it doesn't exist, returns
     * false.
     * @param challengeID
     * @return
     */
    public boolean isChallengeAccepted( String challengeID );

    /**
     * Indicates that the challenge has been accepted
     * @param challengeID
     * @return String matchID if successful match is created
     */
    public String acceptChallenge(String challengeID);

    /**
     * Indicates that the challenge has been rejected
     * @param challengeID
     * @return
     */
    public boolean rejectChallenge(String challengeID);

    /**
     * Destroys the named challenge
     * @param challengeID
     */
    public void removeChallenge(String challengeID);

    /**
     * Provides a list of players currently in the lobby and accepting games
     * @return
     */
    public List<Player> getJoinedPlayers();
}
