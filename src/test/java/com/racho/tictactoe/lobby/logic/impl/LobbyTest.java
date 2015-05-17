package com.racho.tictactoe.lobby.logic.impl;

import com.racho.tictactoe.BaseUnitTest;
import com.racho.tictactoe.lobby.logic.Challenge;
import com.racho.tictactoe.lobby.logic.Lobby;
import com.racho.tictactoe.lobby.logic.LobbyDAO;
import com.racho.tictactoe.lobby.logic.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by aron on 5/16/15.
 */
public class LobbyTest extends BaseUnitTest{
    private Lobby lobby;
    private LobbyDAO lobbyDAO;

    @Before
    public void setupLobby() {
        lobby = injector.getInstance(Lobby.class);
        lobbyDAO = injector.getInstance(LobbyDAO.class);
    }

    @Test
    public void joinPlayer() {
        String playerName = UUID.randomUUID().toString();
        Player player = lobby.joinPlayer(playerName);
        assertNotNull(player);

        boolean isJoined = lobby.isPlayerJoined(playerName);
        assertTrue(isJoined);
    }

    @Test
    public void removePlayer() {
        String playerName = UUID.randomUUID().toString();
        lobby.joinPlayer(playerName);
        assertTrue(lobby.isPlayerJoined(playerName));

        lobby.removePlayer(playerName);
        assertFalse(lobby.isPlayerJoined(playerName));
    }

    @Test
    public void createChallenge() {
        String challenger = UUID.randomUUID().toString();
        String challenged = UUID.randomUUID().toString();

        Challenge challenge = lobby.createChallenge(challenger, challenged);
        assertNotNull(challenge);

        try {
            lobby.createChallenge(challenger, challenged);
            fail("Expected challenge exists exception");
        } catch ( ChallengeExistsException e ) {
            // pass!
        } catch ( Exception e ) {
            fail("Expected challenge exists exception");
        }
    }

    @Test
    public void getChallenge() {
        String challenger = UUID.randomUUID().toString();
        String challenged = UUID.randomUUID().toString();

        Challenge challenge = lobby.createChallenge(challenger, challenged);
        Challenge resolvedChallenge = lobby.getChallengeFor(challenged);

        assertNotNull(resolvedChallenge);
        assertEquals("Expected to resolve challenge", challenge.getChallengeID(), resolvedChallenge.getChallengeID());
    }

    @Test
    public void getExpiredChallenge() {
        String challenger = UUID.randomUUID().toString();
        String challenged = UUID.randomUUID().toString();

        Challenge challenge = new Challenge(
                UUID.randomUUID().toString(),
                challenger, challenged,
                new Date("1/1/2000").getTime()
        );
        lobbyDAO.createChallenge( challenge );

        Challenge resolvedChallenge = lobby.getChallengeFor(challenged);
        if ( resolvedChallenge != null ) {
            fail("Expected challenge to be expired");
        }
    }

    @Test
    public void getFirstNonExpiredChallenge() {
        String challenger = UUID.randomUUID().toString();
        String challenged = UUID.randomUUID().toString();
        Challenge firstOne;

        {
            Challenge challenge = new Challenge(
                    UUID.randomUUID().toString(),
                    challenger, challenged,
                    new Date("1/1/2000").getTime()
            );
            lobbyDAO.createChallenge( challenge );
        }

        {
            Challenge challenge = new Challenge(
                    UUID.randomUUID().toString(),
                    challenger, challenged,
                    new Date().getTime()
            );
            lobbyDAO.createChallenge( challenge );
            firstOne = challenge;
        }

        {
            Challenge challenge = new Challenge(
                    UUID.randomUUID().toString(),
                    challenger, challenged,
                    new Date().getTime()
            );
            lobbyDAO.createChallenge( challenge );
        }

        {
            Challenge challenge = new Challenge(
                    UUID.randomUUID().toString(),
                    challenger, challenged,
                    new Date("1/1/2000").getTime()
            );
            lobbyDAO.createChallenge( challenge );
        }

        Challenge resolvedChallenge = lobby.getChallengeFor(challenged);

        assertNotNull(resolvedChallenge);
        assertEquals("Expected to resolve challenge", firstOne.getChallengeID(), resolvedChallenge.getChallengeID());
    }

    @Test
    public void acceptChallenge() {
        String challenger = UUID.randomUUID().toString();
        String challenged = UUID.randomUUID().toString();

        Challenge challenge = lobby.createChallenge(challenger, challenged);
        assertFalse(lobby.isChallengeAccepted(challenge.getChallengeID()));

        lobby.acceptChallenge(challenge.getChallengeID());

        assertTrue( lobby.isChallengeAccepted(challenge.getChallengeID()) );
    }

    @Test
    public void rejectChallenge() {
        String challenger = UUID.randomUUID().toString();
        String challenged = UUID.randomUUID().toString();

        Challenge challenge = lobby.createChallenge(challenger, challenged);
        assertFalse(lobby.isChallengeAccepted(challenge.getChallengeID()));

        lobby.rejectChallenge(challenge.getChallengeID());

        assertFalse(lobby.isChallengeAccepted(challenge.getChallengeID()));
    }

}
