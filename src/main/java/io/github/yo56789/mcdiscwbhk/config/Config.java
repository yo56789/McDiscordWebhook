package io.github.yo56789.mcdiscwbhk.config;

import io.github.yo56789.mcdiscwbhk.Main;

public class Config {
    static SimpleConfig config = SimpleConfig.of("mcdiscwbhk").provider((String name) -> "# Url for discord webhook\nwebhook-uri=\n# The name the server uses for the webhook\n# Only shows on player join, leave, server start and stop\nserver-webhook-name=Server").request();

    public static final String WEBHOOK_URI = config.getOrDefault("webhook-uri", "");
    public static final String SERVER_NAME = config.getOrDefault("server-webhook-name", "Server");

    public static void init() {
        Main.LOGGER.info("Webhook Url: " + WEBHOOK_URI);
        Main.LOGGER.info("Server Webhook Name: " + SERVER_NAME);
    }
}
