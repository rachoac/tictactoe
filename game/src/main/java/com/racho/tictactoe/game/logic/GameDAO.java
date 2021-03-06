package com.racho.tictactoe.game.logic;

import com.racho.tictactoe.game.logic.impl.Match;
import org.json.simple.JSONObject;

/**
 * Created by aron on 5/18/15.
 */
public interface GameDAO {

    public Match getMatch(String matchID);

    public void createMatch(Match match);

    void updateMatch(Match match);

    public void saveGame(String matchID, JSONObject boardData);

    public JSONObject getGame(String matchID);
}
