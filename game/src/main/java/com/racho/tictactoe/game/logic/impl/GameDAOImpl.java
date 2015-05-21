package com.racho.tictactoe.game.logic.impl;

import com.google.inject.Singleton;
import com.racho.tictactoe.game.logic.GameDAO;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aron on 5/18/15.
 */
@Singleton
public class GameDAOImpl implements GameDAO {
    private Map<String, Match> matches = new HashMap<>();
    private Map<String, JSONObject> matchBoards = new HashMap<>();


    public void createMatch(Match match) {
        matches.put(match.getMatchID(), match);
    }

    public void saveGame(String matchID, JSONObject boardData) {
        matchBoards.put(matchID, boardData);
    }

    @Override
    public JSONObject getGame(String matchID) {
        return matchBoards.get(matchID);
    }
}
