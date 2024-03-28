package io.github.yo56789.mcdiscwbhk.config;

import io.github.yo56789.mcdiscwbhk.Main;

public class Config {
    static SimpleConfig config = SimpleConfig.of("mcdiscwbhk").provider((String name) -> DefaultConfig.DEFAULTCONFIG).request();

    public static final String WEBHOOK_URI = config.getOrDefault("webhook-uri", "");
    public static final String SERVER_NAME = config.getOrDefault("server-webhook-name", "Server");
    public static final String USER_AVATAR_URL = config.getOrDefault("user-avatar-url", "https://mc-heads.net/avatar/%s");
    // This should just get the "webhook-mode" key and check if it equals "embed", this bool will be true if passed
    // Example: if(IS_EMBED_MODE) /*embed stuff*/ else /*message stuff*/
    public static final boolean IS_EMBED_MODE = config.getOrDefault("webhook-mode", "message").equalsIgnoreCase("embed");

    // Events - Server Lifecycle
    public static final String EVENT_SERVER_STARTING = config.getOrDefault("event-server-starting", "Server Starting!");
    public static final String EVENT_SERVER_STARTED = config.getOrDefault("event-server-started", "Server Started!");
    public static final String EVENT_SERVER_STOPPING = config.getOrDefault("event-server-stopping", "Server Stopping!");
    public static final String EVENT_SERVER_STOPPED = config.getOrDefault("event-server-stopped", "Server Stopped!");

    // Events - Player interaction
    public static final String EVENT_PLAYER_JOIN = config.getOrDefault("event-player-join", "%s joined!");
    public static final String EVENT_PLAYER_LEAVE = config.getOrDefault("event-player-leave", "%s left!");

    public static void init() {
        // Protection in-case logs are shared.
        // Many log-sharing websites don't recognise links as something that should be filtered.
        Main.LOGGER.info("Webhook URL: " + (!WEBHOOK_URI.isEmpty() ? WEBHOOK_URI.substring(0, 60).concat("*************************************") : ""));
        Main.LOGGER.info("Launched in " + (IS_EMBED_MODE ? "embed" : "message") + " mode");
    }
}
