package io.github.yo56789.wbhkcommon.config;

import io.github.yo56789.mcdiscwbhk.Main;

import java.nio.file.Path;

public class Config {
    public static String WEBHOOK_URI;
    public static String SERVER_NAME;
    public static String USER_AVATAR_URL;

    public static String WEBHOOK_MODE;
    public static String CLASSIC_MESSAGE_FORMAT;

    // Events - Server Lifecycle
    public static boolean EVENT_PLAYER_MESSAGE_ENABLED;

    public static boolean EVENT_SERVER_STARTING_ENABLED;
    public static String EVENT_SERVER_STARTING;

    public static boolean EVENT_SERVER_STARTED_ENABLED;
    public static String EVENT_SERVER_STARTED;

    public static boolean EVENT_SERVER_STOPPING_ENABLED;
    public static String EVENT_SERVER_STOPPING;

    public static boolean EVENT_SERVER_STOPPED_ENABLED;
    public static String EVENT_SERVER_STOPPED;

    // Events - Player interaction
    public static boolean EVENT_PLAYER_JOIN_ENABLED;
    public static String EVENT_PLAYER_JOIN;

    public static boolean EVENT_PLAYER_LEAVE_ENABLED;
    public static String EVENT_PLAYER_LEAVE;

    public static boolean EVENT_PLAYER_DIED_ENABLED;

    public static void init(Path path) {
        SimpleConfig config = SimpleConfig.of("mcdiscwbhk", path).provider((String name) -> DefaultConfig.DEFAULTCONFIG).request();

        init(config.getOrDefault("webhook-uri", ""), config.getOrDefault("server-webhook-name", "Server"), config.getOrDefault("user-avatar-url", "https://mc-heads.net/avatar/%s"),
                verifyWebhookMode(config.getOrDefault("webhook-mode", "message")), config.getOrDefault("list-message-format", "%s > %s"), config.getOrDefault("event-player-message-enabled", true),
                config.getOrDefault("event-server-starting-enabled",  false),config.getOrDefault("event-server-starting-message", "Server Starting!"), config.getOrDefault("event-server-started-enabled", true),
                config.getOrDefault("event-server-started-message", "Server Started!"), config.getOrDefault("event-server-stopping-enabled", false), config.getOrDefault("event-server-stopping-message", "Server Stopping!"),
                config.getOrDefault("event-server-stopped-enabled", true), config.getOrDefault("event-server-stopped-message", "Server Stopped!"), config.getOrDefault("event-player-join-enabled", true),
                config.getOrDefault("event-player-join-message", "%s joined!"), config.getOrDefault("event-player-leave-enabled", true), config.getOrDefault("event-player-leave-message", "%s left!"),
                config.getOrDefault("event-player-died-enabled", true));
    }

    // 19 inputs :)
    public static void init(String webhookURI, String serverName, String userAvatarUrl, String webhookMode, String classicMessageFormat, boolean eventPlayerMessageEnabled, boolean eventServerStartingEnabled, String eventServerStarting, boolean eventServerStartedEnabled, String eventServerStarted, boolean eventServerStoppingEnabled, String eventServerStopping, boolean eventServerStoppedEnabled, String eventServerStopped, boolean eventServerJoinEnabled, String eventServerJoin, boolean eventServerLeaveEnabled, String eventServerLeave, boolean eventPlayerDied) {
        WEBHOOK_URI = webhookURI;
        SERVER_NAME = serverName;
        USER_AVATAR_URL = userAvatarUrl;

        WEBHOOK_MODE = webhookMode;
        CLASSIC_MESSAGE_FORMAT = classicMessageFormat;

        EVENT_PLAYER_MESSAGE_ENABLED = eventPlayerMessageEnabled;

        EVENT_SERVER_STARTING_ENABLED = eventServerStartingEnabled;
        EVENT_SERVER_STARTING = eventServerStarting;

        EVENT_SERVER_STARTED_ENABLED = eventServerStartedEnabled;
        EVENT_SERVER_STARTED = eventServerStarted;

        EVENT_SERVER_STOPPING_ENABLED = eventServerStoppingEnabled;
        EVENT_SERVER_STOPPING = eventServerStopping;

        EVENT_SERVER_STOPPED_ENABLED = eventServerStoppedEnabled;
        EVENT_SERVER_STOPPED = eventServerStopped;

        EVENT_PLAYER_JOIN_ENABLED = eventServerJoinEnabled;
        EVENT_PLAYER_JOIN = eventServerJoin;

        EVENT_PLAYER_LEAVE_ENABLED = eventServerLeaveEnabled;
        EVENT_PLAYER_LEAVE = eventServerLeave;

        EVENT_PLAYER_DIED_ENABLED = eventPlayerDied;

        // Protection in-case logs are shared.
        // Many log-sharing websites don't recognise links as something that should be filtered.
        Main.LOGGER.info("Webhook URL: " + (!WEBHOOK_URI.isEmpty() ? WEBHOOK_URI.substring(0, 35).concat("***************************************************") : ""));
        Main.LOGGER.info("Launched in " + WEBHOOK_MODE + " mode");
    }

    private static String verifyWebhookMode(String mode) {
        if (mode.equalsIgnoreCase("message") || mode.equalsIgnoreCase("embed") || mode.equalsIgnoreCase("list")) {
            return mode;
        }

        return "message";
    }
}