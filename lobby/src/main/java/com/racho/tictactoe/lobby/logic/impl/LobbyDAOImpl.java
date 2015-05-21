package com.racho.tictactoe.lobby.logic.impl;

import com.google.common.collect.Lists;
import com.racho.tictactoe.lobby.logic.Challenge;
import com.racho.tictactoe.lobby.logic.LobbyDAO;
import com.racho.tictactoe.lobby.logic.Player;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
/**
 * xxx - todo - backed by crude in memory db
 * xxx - todo - this implementation could be used by the test dao
 */
public class LobbyDAOImpl implements LobbyDAO {

    Map<String, Player> joinedPlayers = new HashMap<>();
    Map<String, Challenge> challenges = new HashMap<>();
    Map<String, List<String>> playerChallenges = new HashMap<>();

    public Player getPlayer(String playerName) {
        return joinedPlayers.get(playerName);
    }

    public void savePlayer(Player player) {
        joinedPlayers.put( player.getPlayerName(), player );
        playerChallenges.put( player.getPlayerName(), new ArrayList<>() );
    }

    public void removePlayer(String playerName) {
        joinedPlayers.remove( playerName );
        playerChallenges.remove( playerName );
    }

    public Challenge getChallenge(String challengeID) {
        return challenges.get( challengeID );
    }

    public void createChallenge(Challenge challenge) {
        challenges.put( challenge.getChallengeID(), challenge );
        List<String> challengeIDs = playerChallenges.get(challenge.getChallengedPlayer());
        if ( challengeIDs == null ) {
            challengeIDs = new ArrayList<>();
            playerChallenges.put( challenge.getChallengedPlayer(), challengeIDs );
        }
        challengeIDs.add( challenge.getChallengeID() );
    }

    public void removeChallenge(String challengeID) {
        challenges.remove(challengeID);
        playerChallenges
                .keySet()
                .stream()
                .filter(s -> s.equals(challengeID));
    }

    // lambda
    private Function<String, Challenge> idToChallenge = id -> challenges.get(id);

    public List<Challenge> getChallengesFor(String challengedPlayer) {
        List<Challenge> challenges = Lists.newArrayList();
        List<String> challengeIDs = playerChallenges.get(challengedPlayer);
        if ( challengeIDs == null ) {
            return challenges;
        }

        challenges = challengeIDs.stream()
                .map(idToChallenge)
                .collect(Collectors.<Challenge> toList());

        return challenges;
    }

    @Override
    public void saveChallenge(Challenge challenge) {
        challenges.put(challenge.getChallengeID(), challenge);
    }

    @Override
    public List<String> getJoinedPlayers() {
        return joinedPlayers.keySet()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public void setChallengeMatchID(String challengeID, String matchID) {
        Challenge challenge = getChallenge(challengeID);
        if ( challenge != null ) {
            challenge.setMatchID(matchID);
        }
    }

    @Override
    public String getMatchIDForChallenge(String challengeID) {
        Challenge challenge = getChallenge(challengeID);
        if ( challenge == null ) {
            return null;
        }
        return challenge.getMatchID();
    }
}

