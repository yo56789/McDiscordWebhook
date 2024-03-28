package io.github.yo56789.mcdiscwbhk;

import io.github.yo56789.mcdiscwbhk.config.Config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.regex.Pattern;

public class WebhookHandler {
    private static boolean warnedWebhookInvalid = false;
    static Pattern webhookRegexPattern = Pattern.compile("http.:\\/\\/discord\\.com\\/api\\/webhooks\\/.*\\/.*",
            Pattern.CASE_INSENSITIVE);

    // assembleMessage creates an embed depending on the mode
    // if mode is message then String color is disregarded
    public static String assembleMessage(String message, String username, int color) {
        if(message.isBlank()) return "";

        if(Config.IS_EMBED_MODE) return assembleEmbed(message, username, color);
        return String.format("{\"content\":\"%s\", \"username\":\"%s\"}", message, username);
    }

    public static String assembleMessage(String message, String username, int color, String uuid) {
        if(message.isBlank()) return "";

        if(Config.IS_EMBED_MODE) return assembleEmbed(message, username, color, uuid);
        return String.format("{\"content\":\"%s\", \"username\":\"%s\", \"avatar_url\":\"%s\"}", message, username, String.format(Config.USER_AVATAR_URL, uuid));
    }

    static String assembleEmbed(String message, String username, int color) {
        return String.format("{ \"content\": null, \"embeds\": [ { \"description\": \"%s\", \"color\": %s } ], \"username\": \"%s\" }", message, color, username);
    }

    static String assembleEmbed(String message, String username, int color, String uuid) {
        return String.format("{ \"content\": null, \"embeds\": [ { \"description\": \"%s\", \"color\": %s } ], \"username\": \"%s\", \"avatar_url\": \"%s\" }", message, color, username, String.format(Config.USER_AVATAR_URL, uuid));
    }

    public static void post(String uri, String data) {
        if(data.isBlank()) return;

        if (Objects.equals(uri, "") || !webhookRegexPattern.matcher(uri).find()) {
            if (!warnedWebhookInvalid) {
                Main.LOGGER.error("Invalid Webhook URL");
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
