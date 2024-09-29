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
    private static final Pattern webhookRegexPattern = Pattern.compile("http.://discord\\.com/api/webhooks/.*/.*",
            Pattern.CASE_INSENSITIVE);

    public static String assembleMessage(String message, String username, int color) {
        if (message.isBlank()) {
            return "";
        } else if (Config.WebhookMode.equalsIgnoreCase("embed")) {
            return assembleEmbed(message, username, color);
        } else if (Config.WebhookMode.equalsIgnoreCase("list")) {
            return assembleList(message, username);
        }
        return String.format("{\"content\":\"%s\", \"username\":\"%s\"}", message, username);
    }

    public static String assembleMessage(String message, String username, int color, String uuid) {
        if (message.isBlank()) {
            return "";
        } else if (Config.WebhookMode.equalsIgnoreCase("embed")) {
            return assembleEmbed(message, username, color, uuid);
        } else if (Config.WebhookMode.equalsIgnoreCase("list")) {
            return assembleList(message, username);
        }

        return String.format("{\"content\":\"%s\", \"username\":\"%s\", \"avatar_url\":\"%s\"}", message, username, String.format(Config.UserAvatarURL, uuid));
    }

    static String assembleEmbed(String message, String username, int color) {
        return String.format("{ \"content\": null, \"embeds\": [ { \"description\": \"%s\", \"color\": %s } ], \"username\": \"%s\" }", message, color, username);
    }

    static String assembleEmbed(String message, String username, int color, String uuid) {
        return String.format("{ \"content\": null, \"embeds\": [ { \"description\": \"%s\", \"color\": %s } ], \"username\": \"%s\", \"avatar_url\": \"%s\" }", message, color, username, String.format(Config.UserAvatarURL, uuid));
    }

    static String assembleList(String message, String username) {
        return String.format("{\"content\":\"%s\", \"username\":\"%s\"}", String.format(Config.ClassicMessageFormat, username, message), Config.ServerName);
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
            Main.LOGGER.error("Failed to send webhook");
        }
    }
}
