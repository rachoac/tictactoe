package com.racho.tictactoe.game.logic.impl;

import com.racho.tictactoe.game.logic.GameState;
import org.json.simple.JSONObject;

public class TicTacToeBoard {

    private String[][] board;
    private int moves;
    private String winner;
    private GameState state;

    public TicTacToeBoard() {
        reset();
    }

    public void reset() {
        board = new String[3][3];
        moves = 0;
        winner = null;
        state = GameState.init;
    }

    public void performMove( String playerName, int x, int y ) {
        validateMove(x, y);
        board[x][y] = playerName;
        moves++;
        computeState();
    }

    public GameState getState() {
        return state;
    }

    private void computeState() {
        winner = computeWinner();
        if ( winner == null && moves > 8) {
            state = GameState.stalemate;
            return;
        }

        state = winner == null ? GameState.running : GameState.won;
    }

    public JSONObject serialize() {
        JSONObject object = new JSONObject();
        for ( int y = 0; y < 3; y++ ) {
            for ( int x = 0; x < 3; x++ ) {
                String key = x + "_" + y;
                String value = board[x][y];
                if ( value != null ) {
                    object.put(key, value);
                }
            }
        }
        object.put("moves", moves);
        return object;
    }

    public void deserialize(JSONObject object) {
        reset();
        for ( int y = 0; y < 3; y++ ) {
            for ( int x = 0; x < 3; x++ ) {
                String key = x + "_" + y;
                String value = (String) object.get(key);
                if ( value != null ) {
                    board[x][y] = value;
                }
            }
        }
        Object moves = object.get("moves");
        if ( moves == null ) {
            this.moves = 0;
        } else {
            this.moves = (int) moves;
        }

        computeState();
    }

    public String getWinner() {
        return winner;
    }

    public String computeWinner() {
        String current = null;
        String last = null;

        // check vertical
        for ( int y = 0; y < 3; y++ ) {
            for ( int x = 0; x < 3; x ++ ) {
                current = board[x][y];
                if ( current == null ) {
                    // empty slot means short circuit row check
                    break ;
                }

                if ( last != null && !last.equals(current) ) {
                    break ;
                }

                last = current;
            }
            if ( last != null && current != null && last.equals(current) ) {
                return last;
            }
        }

        // check vertical
        last = null;
        current = null;
        for ( int y = 0; y < 3; y++ ) {
            for ( int x = 0; x < 3; x ++ ) {
                current = board[y][x];
                if ( current == null ) {
                    // empty slot means short circuit row check
                    break ;
                }

                if ( last != null && !last.equals(current) ) {
                    break ;
                }

                last = current;
            }
            if ( last != null && current != null && last.equals(current) ) {
                return last;
            }
        }

        // check diagonal
        last = null;
        current = null;
        for ( int i = 0; i < 3; i++ ) {
            current = board[i][i];
            if ( current == null ) {
                // empty slot means short circuit row check
                break ;
            }

            if ( last != null && !last.equals(current) ) {
                break ;
            }

            last = current;
        }
        if ( last != null && current != null && last.equals(current) ) {
            return last;
        }

        // check diagonal
        last = null;
        current = null;
        for ( int i = 2; i >= 0; i-- ) {
            int x = i;
            int y = 2 - i;
            current = board[x][y];
            if ( current == null ) {
                // empty slot means short circuit row check
                break ;
            }

            if ( last != null && !last.equals(current) ) {
                break ;
            }

            last = current;
        }
        if ( last != null && current != null && last.equals(current) ) {
            return last;
        }

        return null;
    }


    private void validateMove(int x, int y) {
        if ( x < 0 || y < 0 ) {
            throw new IllegalMoveException("Out of bounds: " + x + ", " + y);

        }

        if ( x > 3 || y > 3 ) {
            throw new IllegalMoveException("Out of bounds: " + x + ", " + y);
        }

        if ( board[x][y] != null ) {
            throw new IllegalMoveException("Already taken: " + x + ", " + y);
        }
    }

}
