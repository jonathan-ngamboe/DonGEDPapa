package org.example.api;

import ch.hearc.ged.JSONUtilities;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class ApiClient {

    private String baseUrl;
    private String token;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void authenticate(String username, String password) throws Exception {
        String url = baseUrl + "/token";
        String response = null;
        HttpURLConnection connection = null;

        String requestBody =  "grant_type=password&username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");

        try {
            connection = JSONUtilities.write(url, JSONUtilities.RequestMethod.POST, requestBody, null);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                response = JSONUtilities.read(connection);
                this.token = new JSONObject(response).getString("access_token");
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

    public String advancedSearchRequest(JSONObject query) throws Exception {
        String url = baseUrl + "api/search/advanced";
        HttpURLConnection connection = null;

        try {
            connection = JSONUtilities.write(url, JSONUtilities.RequestMethod.POST, query.toString(), token);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String response = JSONUtilities.read(connection);
                return response;
            } else {
                throw new IOException("Failed to make advanced search request : HTTP error code : " + connection.getResponseCode());
            }
        } catch (IOException | JSONException e) {
            throw new Exception("Error while making advanced search request", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public void validate(int id) throws Exception {
        String url = baseUrl + "api/flow/validate/" + id;
        HttpURLConnection connection = null;

        System.out.println("URL : " + url);

        try {
            connection = JSONUtilities.write(url, JSONUtilities.RequestMethod.POST, null, token);
            System.out.println(connection.getResponseMessage());

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Failed to validate : HTTP error code : " + connection.getResponseCode());
            }
        } catch (IOException e) {
            throw new Exception("Error while validating", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}