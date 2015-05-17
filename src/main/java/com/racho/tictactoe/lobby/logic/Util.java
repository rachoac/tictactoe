package com.racho.tictactoe.lobby.logic;

import java.util.Date;

/**
 * Created by aron on 5/16/15.
 */
public class Util {

    public static boolean elapsed( long start, long duration ) {
        return new Date().getTime() -  new Date(start).getTime() > duration;
    }
}
