package com.racho.tictactoe.game.logic.impl;

import com.google.common.collect.Lists;
import com.racho.tictactoe.game.logic.GameState;
import org.json.simple.JSONObject;

import java.util.List;

public class TicTacToeBoard {

    private String[][] board;
    private int moves;
    private String winner;
    private List<String> winningCells;
    private GameState state;

    public TicTacToeBoard() {
        reset();
    }

    public void reset() {
        board = new String[3][3];
        moves = 0;
        winner = null;
        state = GameState.init;
        winningCells = Lists.newArrayList();
    }

    public void performMove( String playerName, int x, int y ) throws IllegalMoveException {
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
                String key = makeKey(x, y);
                String value = board[x][y];
                if ( value != null ) {
                    object.put(key, value);
                }
            }
        }
        object.put("moves", moves);
        return object;
    }

    private String makeKey(int x, int y) {
        return x + "_" + y;
    }

    public void deserialize(JSONObject object) {
        reset();
        for ( int y = 0; y < 3; y++ ) {
            for ( int x = 0; x < 3; x++ ) {
                String key = makeKey(x, y);
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

        // check horizontal
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
                winningCells.add( makeKey(0,y) );
                winningCells.add( makeKey(1,y) );
                winningCells.add( makeKey(2,y) );
                return last;
            }
            last = null;
        }

        // check vertical
        last = null;
        current = null;
        winningCells.clear();
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
                winningCells.add( makeKey(y,0) );
                winningCells.add( makeKey(y,1) );
                winningCells.add( makeKey(y,2) );
                return last;
            }
            last = null;
        }

        // check diagonal
        last = null;
        current = null;
        winningCells.clear();
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
            winningCells.add( makeKey(0,0) );
            winningCells.add( makeKey(1,1) );
            winningCells.add( makeKey(2,2) );
            return last;
        }

        // check diagonal
        last = null;
        current = null;
        winningCells.clear();
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
            winningCells.add( makeKey(2,0) );
            winningCells.add( makeKey(1,1) );
            winningCells.add( makeKey(0,2) );
            return last;
        }

        winningCells.clear();
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

    public List<String> getWinningCells() {
        return winningCells;
    }

    public static void main(String s[]) {

        {
            TicTacToeBoard board = new TicTacToeBoard();

            board.performMove("X", 1, 0);
            System.out.println(board.computeWinner());
            board.performMove("X", 0, 0);
            System.out.println(board.computeWinner());
            board.performMove("X", 2, 0);
            System.out.println(board.computeWinner());
        }

        {
            TicTacToeBoard board = new TicTacToeBoard();

            board.performMove("X", 1, 1);
            System.out.println(board.computeWinner());
            board.performMove("X", 0, 1);
            System.out.println(board.computeWinner());
            board.performMove("X", 2, 1);
            System.out.println(board.computeWinner());
        }

        {
            TicTacToeBoard board = new TicTacToeBoard();

            board.performMove("Y", 0, 0);
            System.out.println(board.computeWinner());
            board.performMove("X", 0, 1);
            System.out.println(board.computeWinner());
            board.performMove("X", 0, 2);
            System.out.println(board.computeWinner());
        }

        System.out.println();

        {
            TicTacToeBoard board = new TicTacToeBoard();

            board.performMove("X", 1, 2);
            System.out.println(board.computeWinner());
            board.performMove("X", 1, 0);
            System.out.println(board.computeWinner());
            board.performMove("X", 1, 1);
            System.out.println(board.computeWinner());
        }

    }
}
