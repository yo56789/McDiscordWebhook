package io.github.yo56789.mcdiscwbhk.config;

import io.github.yo56789.mcdiscwbhk.Main;

public class Config {
    static SimpleConfig config = SimpleConfig.of("mcdiscwbhk").provider((String name) -> "# Url for discord webhook\nwebhook-uri=\n# The name the server uses for the webhook\n# Only shows on player join, leave, server start and stop\nserver-webhook-name=Server\n# Changes where avatar for user sent message comes from\n# must contain url and %s where the player uuid goes\nuser-avatar-url=https://mc-heads.net/avatar/%s").request();

    public static final String WEBHOOK_URI = config.getOrDefault("webhook-uri", "");
    public static final String SERVER_NAME = config.getOrDefault("server-webhook-name", "Server");
    public static final String USER_AVATAR_URL = config.getOrDefault("user-avatar-url", "https://mc-heads.net/avatar/%s");

    public static void init() {
        Main.LOGGER.info("Webhook Url: " + WEBHOOK_URI);
    }
}
