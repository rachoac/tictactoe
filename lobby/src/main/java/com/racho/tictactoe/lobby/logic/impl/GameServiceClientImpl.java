package com.racho.tictactoe.lobby.logic.impl;

import com.racho.tictactoe.LobbyConfiguration;
import com.racho.tictactoe.lobby.logic.GameServiceClient;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aron on 5/18/15.
 */
public class GameServiceClientImpl implements GameServiceClient {


    @Override
    public String createMatch(String challengerPlayer, String challengedPlayer) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(gameServiceURL + "/game/match/create" );

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("challengerPlayer", challengerPlayer));
        nvps.add(new BasicNameValuePair("challengedPlayer", challengedPlayer));
        try {
            request.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {

            CloseableHttpResponse response = client.execute(request);

            try {
                System.out.println(response.getStatusLine());
                HttpEntity entity2 = response.getEntity();



                EntityUtils.consume(entity2);
            } finally {
                response.close();
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return challengerPlayer;
    }

    private String gameServiceURL;

    @Inject
    public void setGameServiceURL( @Named("gameServiceURL") String gameServiceURL) {
        this.gameServiceURL = gameServiceURL;
    }
}
