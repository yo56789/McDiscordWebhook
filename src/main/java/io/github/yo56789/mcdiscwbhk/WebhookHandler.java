package io.github.yo56789.mcdiscwbhk;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class WebhookHandler {
    private static boolean warnedWebhookInvalid = false;

    public static String assembleString(String message, String username) {
        return String.format("{\"content\":\"%s\", \"username\":\"%s\"}", message, username);
    }

    public static void post(String uri, String data) {
        if (Objects.equals(uri, "") || !uri.startsWith("https://discord.com/api/webhooks/")) {
            if (!warnedWebhookInvalid) {
                Main.LOGGER.warn("Invalid Webhook uri");
                warnedWebhookInvalid = true;
            }
            return;
        }
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            Main.LOGGER.warn("Failed to send webhook");
        }
    }
}
