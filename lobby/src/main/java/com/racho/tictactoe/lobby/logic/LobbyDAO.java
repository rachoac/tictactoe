package com.racho.tictactoe.lobby.logic;

import java.util.List;

public interface LobbyDAO {
    public Player getPlayer(String playerName);

    public void savePlayer(Player player);

    public void removePlayer(String playerName);

    public Challenge getChallenge(String challengeID);

    public void createChallenge(Challenge challenge);

    public void removeChallenge(String challengeID);

    public List<Challenge> getChallengesFor(String challengedPlayer);

    public void saveChallenge(Challenge challenge);

    public List<String> getJoinedPlayers();

    public void setChallengeMatchID(String challengeID, String matchID);

    public String getMatchIDForChallenge(String challengeID);
}
