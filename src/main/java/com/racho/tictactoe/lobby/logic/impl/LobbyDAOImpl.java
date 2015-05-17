package com.racho.tictactoe.lobby.logic.impl;

import com.racho.tictactoe.lobby.logic.Challenge;
import com.racho.tictactoe.lobby.logic.LobbyDAO;
import com.racho.tictactoe.lobby.logic.Player;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class LobbyDAOImpl implements LobbyDAO {
    public Player getPlayer(String playerName) {
        return null;
    }

    public void savePlayer(Player player) {

    }

    @Override
    public void removePlayer(String playerName) {

    }

    @Override
    public Challenge getChallenge(String challengeID) {
        return null;
    }

    @Override
    public void createChallenge(Challenge challenge) {

    }

    @Override
    public void removeChallenge(String challengeID) {

    }

    @Override
    public List<Challenge> getChallengesFor(String challengedPlayer) {
        return null;
    }

    @Override
    public void saveChallenge(Challenge challenge) {

    }
}

