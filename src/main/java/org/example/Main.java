package org.example;

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
            query.put("searchPattern", "(;NDF_etat|l01|Remboursement effectué|list;)");
            query.put("contentTypeIDs", "101");
            String notes_de_frais = client.advancedSearchRequest(query).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}