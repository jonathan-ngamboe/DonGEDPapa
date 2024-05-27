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

            // Get the content type ID by calling the API
            String contentTypes = client.getContentTypes();
            JSONArray contentTypesArray = new JSONArray(contentTypes);
            int contentTypeId = -1;
            for (int i = 0; i < contentTypesArray.length(); i++) {
                JSONObject jsonObject = contentTypesArray.getJSONObject(i);
                if (jsonObject.getString("text").equals("Note de frais")) {
                    contentTypeId = jsonObject.getInt("id");
                    break;
                }
            }

            if(contentTypeId == -1) {
                throw new Exception("Content type ID not found");
            }

            // Advanceed search request to get the notes de frais
            JSONObject query = new JSONObject();
            query.put("searchPattern", "(;NDF_etat|l01|Remboursement effectué - En attente d'exportation|list;)");
            query.put("contentTypeId", contentTypeId);
            String notes_de_frais = client.advancedSearchRequest(query).toString();

            // Validate the notes de frais
            JSONArray jsonArray = new JSONArray(notes_de_frais); // Parse the JSON data to a JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {
                // Get each JSONObject
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extract the ObjectID
                int objectId = jsonObject.getInt("ObjectID");

                // Validate
                try {
                    client.validate(objectId);
                } catch (Exception e) {
                    System.out.println("Failed to change state for object ID: " + objectId);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}