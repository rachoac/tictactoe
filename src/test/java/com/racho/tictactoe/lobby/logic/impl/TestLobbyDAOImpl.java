package com.racho.tictactoe.lobby.logic.impl;

import com.racho.tictactoe.lobby.logic.Challenge;
import com.racho.tictactoe.lobby.logic.LobbyDAO;
import com.racho.tictactoe.lobby.logic.Player;

import java.util.List;

/**
 * Created by aron on 5/16/15.
 */
public class TestLobbyDAOImpl implements LobbyDAO {


    public Player getPlayer(String playerName) {
        return null;
    }

    public void savePlayer(Player player) {

    }

    public void removePlayer(String playerName) {

    }

    public Challenge getChallenge(String challengeID) {
        return null;
    }

    public void createChallenge(Challenge challenge) {

    }

    public void removeChallenge(String challengeID) {

    }

    public List<Challenge> getChallengesFor(String challengedPlayer) {
        return null;
    }
}
