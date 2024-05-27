package org.example;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.example.api.ApiClient;


public class Main {
    public static void main(String[] args) {
        ApiClient client = new ApiClient("http://157.26.83.80:2240/");
        try {
            // Authenticate
            client.authenticate("admin2", "admin2");

            // Advanceed search request
            JSONObject query = new JSONObject();
            query.put("searchPattern", "(;NDF_etat|l01|Remboursement effectué - En attente d'exportation|list;)");
            query.put("contentTypeIDs", "140");
            String notes_de_frais = client.advancedSearchRequest(query).toString();

            // Change all the states to "Remboursement effectué - Exporté"
            JSONArray jsonArray = new JSONArray(notes_de_frais); // Parse the JSON data to a JSONArray
            // Iterate through the array
            for (int i = 0; i < jsonArray.length(); i++) {
                // Get each JSONObject
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extract the ObjectID
                int objectId = jsonObject.getInt("ObjectID");

                // Validate
                client.validate(objectId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}