package org.example;

import org.example.api.ApiClient;

public class Main {
    public static void main(String[] args) {
        ApiClient client = new ApiClient("http://157.26.83.80:2240/");
        try {
            String token = client.getToken("admin3", "admin3");
            System.out.println("Token: " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}