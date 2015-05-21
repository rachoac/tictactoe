package com.racho.tictactoe.lobby.logic.impl;

import com.racho.tictactoe.lobby.logic.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        return player != null;
    }

    @Override
    public void removePlayer( String playerName ) {
        lobbyDAO.removePlayer( playerName );
    }

    @Override
    public Challenge createChallenge( String challengerPlayer, String challengedPlayer ) {
        // assert that the challenger and challenged players both exist in the lobby
        validatePlayer(challengerPlayer);
        validatePlayer(challengedPlayer);

        String challengeID = challengerPlayer + "_" + challengedPlayer;
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
        challenge.setChallengeStatus(ChallengeStatus.pending);

        lobbyDAO.createChallenge( challenge );

        return challenge;
    }

    private void validatePlayer(String playerName) {
        Player player = lobbyDAO.getPlayer(playerName);
        if (player == null) {
            String msg = "Challenged " + playerName + " not found";
            throw new PlayerNotFoundException(msg);
        }
    }

    /**
     * Returns the first non-expired challenge
     * Removes any expired challenges
     * @param challengedPlayer
     * @return the first non-expired challenge
     */
    @Override
    public Challenge getChallengeFor( String challengedPlayer ) {
        validatePlayer(challengedPlayer);

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

    @Override
    public Challenge getChallenge(String challengeID) {
        return lobbyDAO.getChallenge(challengeID);
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
    public String acceptChallenge(String challengeID) {
        Challenge challenge = lobbyDAO.getChallenge( challengeID );
        if ( challenge == null ) {
            throw new ChallengeNotFoundException(challengeID);
        }

        challenge.setChallengeStatus(accepted);
        lobbyDAO.saveChallenge(challenge);

        // call game service and prepare a match
        // todo
        String matchID = "XXX"; // todo

        // todo - what if this doesn't succeed (error, timeout, etc)
        matchID = gameServiceClient.createMatch(
                challenge.getChallengerPlayer(),
                challenge.getChallengedPlayer()
        );

        lobbyDAO.setChallengeMatchID( challengeID, matchID );

        return matchID;
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

    @Override
    public List<Player> getJoinedPlayers() {
        List<String> joinedPlayerNames = lobbyDAO.getJoinedPlayers();
        return joinedPlayerNames
                .stream()
                .map(lobbyDAO::getPlayer)
                .collect(Collectors.<Player>toList());
    }

    @Override
    public String getMatchIDForChallenge(String challengeID) {
        return lobbyDAO.getMatchIDForChallenge(challengeID);
    }

    // ------------------------------------------------------------------------------------------------------
    // dependencies

    private LobbyDAO lobbyDAO;
    private GameServiceClientImpl gameServiceClient;

    @Inject
    public void setLobbyDAO( LobbyDAO lobbyDAO ) {
        this.lobbyDAO = lobbyDAO;
    }

    @Inject
    public void setGameServiceClient(GameServiceClientImpl gameServiceClient) {
        this.gameServiceClient = gameServiceClient;
    }
}
