package com.racho.tictactoe.lobby.logic.impl;

import com.racho.tictactoe.lobby.logic.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import static com.racho.tictactoe.lobby.logic.ChallengeStatus.accepted;
import static com.racho.tictactoe.lobby.logic.ChallengeStatus.rejected;

@Singleton
public class LobbyImpl implements Lobby {

    public static Logger log = Logger.getLogger(LobbyImpl.class.getName());

    /**
     * Constructor
     */
    public LobbyImpl() {
    }

    @Override
    public Player joinPlayer( String playerName ) {
        Player player = lobbyDAO.getPlayer( playerName );
        if ( player == null ) {
            player = new Player( playerName );
            player.setJoinedTs(new Date().getTime() );
            lobbyDAO.savePlayer( player );
        }

        return player;
    }

    @Override
    public boolean isPlayerJoined( String playerName ) {
        Player player = lobbyDAO.getPlayer( playerName );
        if ( player == null ) {
           return false;
        }

        return true;
    }

    @Override
    public void removePlayer( String playerName ) {
        lobbyDAO.removePlayer( playerName );
    }

    @Override
    public Challenge createChallenge( String challengerPlayer, String challengedPlayer ) {
        String challengeID = challengedPlayer + "_" + challengedPlayer;
        Challenge challenge = lobbyDAO.getChallenge( challengeID );
        if ( challenge != null ) {
            // challenge already exists
            throw new ChallengeExistsException("Challenge already exists: " + challengeID);
        }

        challenge = new Challenge(
            challengeID,
            challengerPlayer, challengedPlayer,
            new Date().getTime()
        );

        lobbyDAO.createChallenge( challenge );

        return challenge;
    }

    /**
     * Returns the first non-expired challenge
     * Removes any expired challenges
     * @param challengedPlayer
     * @return the first non-expired challenge
     */
    @Override
    public Challenge getChallengeFor( String challengedPlayer ) {
        List<Challenge> challenges = lobbyDAO.getChallengesFor( challengedPlayer );
        Optional<Challenge> first =
                challenges
                .stream()
                .filter(challengeID -> {
                    if (challengeID.isChallengeExpired()) {
                        removeExpiredChallenge(challengeID.getChallengeID());
                        return false;
                    } else {
                        return true;
                    }
                })
                .findFirst();
        if ( first.isPresent() ) {
            return first.get();
        }

        return null;
    }

    /**
     * @param challengeID
     * @return true if expired challenge was removed
     */
    @Override
    public boolean removeExpiredChallenge( String challengeID ) {
        Challenge challenge = lobbyDAO.getChallenge( challengeID );
        if ( challenge == null ) {
            return false;
        }

        if ( challenge.isChallengeExpired() ) {
            log.info("Challenge " + challengeID + " expired, removing");
            lobbyDAO.removeChallenge( challengeID );
            return true;
        }

        return true;
    }

    @Override
    public boolean isChallengeAccepted( String challengeID ) {
        Challenge challenge = lobbyDAO.getChallenge( challengeID );
        if ( challenge == null ) {
            return false;
        }

        return challenge.getChallengeStatus() == accepted;
    }

    @Override
    public boolean acceptChallenge(String challengeID) {
        Challenge challenge = lobbyDAO.getChallenge( challengeID );
        if ( challenge == null ) {
            return false;
        }

        challenge.setChallengeStatus(accepted);
        lobbyDAO.saveChallenge(challenge);
        return true;
    }

    @Override
    public boolean rejectChallenge(String challengeID) {
        Challenge challenge = lobbyDAO.getChallenge( challengeID );
        if ( challenge == null ) {
            return false;
        }

        challenge.setChallengeStatus(rejected);
        lobbyDAO.saveChallenge(challenge);
        return true;
    }

    @Override
    public void removeChallenge(String challengeID) {
        lobbyDAO.removeChallenge(challengeID);
    }

    // ------------------------------------------------------------------------------------------------------
    // dependencies

    private LobbyDAO lobbyDAO;

    @Inject
    public void setLobbyDAO( LobbyDAO lobbyDAO ) {
        this.lobbyDAO = lobbyDAO;
    }

}