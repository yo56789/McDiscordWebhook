package io.github.yo56789.mcdiscwbhk.config;

import io.github.yo56789.mcdiscwbhk.Main;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<String> WEBHOOK_URI = BUILDER
            .comment("Discord webhook URI", "How to get a webhook: https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks")
            .define("webhook-uri", "");
    private static final ModConfigSpec.ConfigValue<String> SERVER_NAME = BUILDER
            .comment("Server name", "Can be set to anything, and shows for server-related events")
            .define("server-webhook-name", "Server");
    private static final ModConfigSpec.ConfigValue<String> USER_AVATAR_URL = BUILDER
            .comment("User avatar provider", "Replace the part where the UUID is located with %s")
            .define("user-avatar-url", "https://mc-heads.net/avatar/%s");

    private static final ModConfigSpec.ConfigValue<String> WEBHOOK_MODE = BUILDER
            .comment("Webhook mode", "Options: \"message\", \"embed\" or \"list\"", "\"message\" sends messages with the bot using the persons mc username. \"embed\" sends everything in embeds. \"list\" sends messages like a list.")
            .define("webhook-mode", "message", Config::verifyWebhookMode);
    private static final ModConfigSpec.ConfigValue<String> CLASSIC_MESSAGE_FORMAT = BUILDER
            .comment("Format for sending messages in \"list\" mode", "First %s is username Second %s is message content", "Default: %s > %s")
            .define("list-message-format", "**%s** > %s");

    private static final ModConfigSpec.BooleanValue EVENT_PLAYER_MESSAGE_ENABLED = BUILDER
            .comment("Player Message")
            .define("event-player-message-enabled", true);

    private static final ModConfigSpec.BooleanValue EVENT_SERVER_STARTING_ENABLED = BUILDER
            .comment("Server starting")
            .define("event-server-starting-enabled", false);
    private static final ModConfigSpec.ConfigValue<String> EVENT_SERVER_STARTING = BUILDER
            .define("event-server-starting-message", "Server Starting!");

    private static final ModConfigSpec.BooleanValue EVENT_SERVER_STARTED_ENABLED = BUILDER
            .comment("Server started")
            .define("event-server-started-enabled", true);
    private static final ModConfigSpec.ConfigValue<String> EVENT_SERVER_STARTED = BUILDER
            .define("event-server-started-message", "Server Started!");

    private static final ModConfigSpec.BooleanValue EVENT_SERVER_STOPPING_ENABLED = BUILDER
            .comment("Server stopping")
            .define("event-server-stopping-enabled", false);
    private static final ModConfigSpec.ConfigValue<String> EVENT_SERVER_STOPPING = BUILDER
            .define("event-server-stopping-message", "Server Stopping!");

    private static final ModConfigSpec.BooleanValue EVENT_SERVER_STOPPED_ENABLED = BUILDER
            .comment("Server stopped")
            .define("event-server-stopped-enabled", true);
    private static final ModConfigSpec.ConfigValue<String> EVENT_SERVER_STOPPED = BUILDER
            .define("event-server-stopped-message", "Server Stopped!");

    private static final ModConfigSpec.BooleanValue EVENT_PLAYER_JOIN_ENABLED = BUILDER
            .comment("Player join", "%s = username of player")
            .define("event-player-join-enabled", true);
    private static final ModConfigSpec.ConfigValue<String> EVENT_PLAYER_JOIN = BUILDER
            .define("event-player-join-message", "%s joined!");

    private static final ModConfigSpec.BooleanValue EVENT_PLAYER_LEAVE_ENABLED = BUILDER
            .comment("Player leave", "%s = username of player")
            .define("event-player-leave-enabled", true);
    private static final ModConfigSpec.ConfigValue<String> EVENT_PLAYER_LEAVE = BUILDER
            .define("event-player-leave-message", "%s left!");

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static String WebhookURI;
    public static String ServerName;
    public static String UserAvatarURL;

    public static String WebhookMode;
    public static String ClassicMessageFormat;

    public static boolean EventPlayerMessageEnabled;

    public static boolean EventServerStartingEnabled;
    public static String EventServerStarting;

    public static boolean EventServerStartedEnabled;
    public static String EventServerStarted;

    public static boolean EventServerStoppingEnabled;
    public static String EventServerStopping;

    public static boolean EventServerStoppedEnabled;
    public static String EventServerStopped;

    public static boolean EventPlayerJoinEnabled;
    public static String EventPlayerJoin;

    public static boolean EventPlayerLeaveEnabled;
    public static String EventPlayerLeave;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        WebhookURI = WEBHOOK_URI.get();
        Main.LOGGER.info("Webhook URL: {}", !WebhookURI.isEmpty() ? WebhookURI.substring(0, 35).concat("***************************************************") : "");
        ServerName = SERVER_NAME.get();
        UserAvatarURL = USER_AVATAR_URL.get();
        WebhookMode = WEBHOOK_MODE.get();
        Main.LOGGER.info("Launched in {} mode", WebhookMode);
        ClassicMessageFormat = CLASSIC_MESSAGE_FORMAT.get();

        EventPlayerMessageEnabled = EVENT_PLAYER_MESSAGE_ENABLED.get();
        EventServerStartingEnabled = EVENT_SERVER_STARTING_ENABLED.get();
        EventServerStarting = EVENT_SERVER_STARTING.get();
        EventServerStartedEnabled = EVENT_SERVER_STARTED_ENABLED.get();
        EventServerStarted = EVENT_SERVER_STARTED.get();
        EventServerStoppingEnabled = EVENT_SERVER_STOPPING_ENABLED.get();
        EventServerStopping = EVENT_SERVER_STOPPING.get();
        EventServerStoppedEnabled = EVENT_SERVER_STOPPED_ENABLED.get();
        EventServerStopped = EVENT_SERVER_STOPPED.get();
        EventPlayerJoinEnabled = EVENT_PLAYER_JOIN_ENABLED.get();
        EventPlayerJoin = EVENT_PLAYER_JOIN.get();
        EventPlayerLeaveEnabled = EVENT_PLAYER_LEAVE_ENABLED.get();
        EventPlayerLeave = EVENT_PLAYER_LEAVE.get();
    }

    private static boolean verifyWebhookMode(final Object obj) {
        if (obj instanceof String mode) {
            return mode.equalsIgnoreCase("message") || mode.equalsIgnoreCase("embed") || mode.equalsIgnoreCase("list");
        }

        return false;
    }
}
