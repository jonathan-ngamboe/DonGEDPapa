package org.example.api;

import ch.hearc.ged.JSONUtilities;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class ApiClient {

    private String baseUrl;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getToken(String username, String password) throws Exception {
        String url = baseUrl + "/token";
        HttpURLConnection connection = null;
        String token;

        String requestBody =  "grant_type=password&username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");

        try {
            connection = JSONUtilities.write(url, JSONUtilities.RequestMethod.POST, requestBody, null);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                token = JSONUtilities.read(connection);
                return new JSONObject(token).getString("access_token");
            } else {
                throw new IOException("Failed : HTTP error code : " + connection.getResponseCode());
            }
        } catch (IOException e) {
            throw new Exception("Error while getting token", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}