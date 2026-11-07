package com.wallet.pro;

import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiClient {
    private static final String ENDPOINT = BuildConfig.ENDPOINT;

    public static void post(JSONObject body) {
        new Thread(() -> {
            try {
                URL u = new URL(ENDPOINT);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("POST");
                c.setDoOutput(true);
                c.setRequestProperty("Content-Type", "application/json");
                c.setConnectTimeout(15000);
                c.setReadTimeout(15000);
                try (OutputStream os = c.getOutputStream()) {
                    os.write(body.toString().getBytes(StandardCharsets.UTF_8));
                }
                c.getResponseCode(); // يكفي قراءته
                c.disconnect();
            } catch (Exception ignored) {}
        }).start();
    }
}
