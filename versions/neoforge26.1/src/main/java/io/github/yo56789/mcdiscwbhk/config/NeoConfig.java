package io.github.yo56789.mcdiscwbhk.config;

import io.github.yo56789.wbhkcommon.config.Config;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Predicate;

public class NeoConfig {
    public static final NeoConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.ConfigValue<String> webhookUri;
    public final ModConfigSpec.ConfigValue<String> serverName;
    public final ModConfigSpec.ConfigValue<String> userAvatarUrl;

    public final ModConfigSpec.ConfigValue<String> webhookMode;
    public final ModConfigSpec.ConfigValue<String> classicMessageFormat;

    public final ModConfigSpec.ConfigValue<Boolean> eventPlayerMessageEnabled;

    public final ModConfigSpec.ConfigValue<Boolean> eventServerStartingEnabled;
    public final ModConfigSpec.ConfigValue<String> eventServerStarting;

    public final ModConfigSpec.ConfigValue<Boolean> eventServerStartedEnabled;
    public final ModConfigSpec.ConfigValue<String> eventServerStarted;

    public final ModConfigSpec.ConfigValue<Boolean> eventServerStoppingEnabled;
    public final ModConfigSpec.ConfigValue<String> eventServerStopping;

    public final ModConfigSpec.ConfigValue<Boolean> eventServerStoppedEnabled;
    public final ModConfigSpec.ConfigValue<String> eventServerStopped;

    public final ModConfigSpec.ConfigValue<Boolean> eventPlayerJoinEnabled;
    public final ModConfigSpec.ConfigValue<String> eventPlayerJoin;

    public final ModConfigSpec.ConfigValue<Boolean> eventPlayerLeaveEnabled;
    public final ModConfigSpec.ConfigValue<String> eventPlayerLeave;

    public final ModConfigSpec.ConfigValue<Boolean> eventPlayerDiedEnabled;

    private NeoConfig(ModConfigSpec.Builder builder) {
        webhookUri = add(builder, "webhook-uri", "", "Discord webhook URI", "How to get a webhook: https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks");
        serverName = add(builder, "server-webhook-name", "Server", "Server name", "Can be set to anything, and shows for server-related events");
        userAvatarUrl = add(builder, "user-avatar-url", "https://mc-heads.net/avatar/%s", "User avatar provider", "Replace the part where the UUID is located with %s");

        webhookMode = add(builder, "webhook-mode", "message", NeoConfig::verifyWebhookMode, "Webhook mode", "Options: \"message\", \"embed\" or \"list\"", "\"message\" sends messages with the bot using the persons mc username. \"embed\" sends everything in embeds. \"list\" sends messages like a list.");
        classicMessageFormat = add(builder, "list-message-format", "**%s** > %s", "Format for sending messages in \"list\" mode", "First %s is username Second %s is message content", "Default: %s > %s");

        eventPlayerMessageEnabled = add(builder, "event-player-message-enabled", true, "Player Message");

        eventServerStartingEnabled = add(builder, "event-server-starting-enabled", false, "Server starting");
        eventServerStarting = add(builder, "event-server-starting-message", "Server Starting!");

        eventServerStartedEnabled = add(builder, "event-server-started-enabled", true, "Server started");
        eventServerStarted = add(builder, "event-server-started-message", "Server Started!");

        eventServerStoppingEnabled = add(builder, "event-server-stopping-enabled", false, "Server stopping");
        eventServerStopping = add(builder, "event-server-stopping-message", "Server Stopping!");

        eventServerStoppedEnabled = add(builder, "event-server-stopped-enabled", true, "Server stopped");
        eventServerStopped = add(builder, "event-server-stopped-message", "Server Stopped!");

        eventPlayerJoinEnabled = add(builder, "event-player-join-enabled", true,  "Player join", "%s = username of player");
        eventPlayerJoin = add(builder, "event-player-join-message", "%s joined!");

        eventPlayerLeaveEnabled = add(builder, "event-player-leave-enabled", true, "Player leave", "%s = username of player");
        eventPlayerLeave = add(builder, "event-player-leave-message", "%s left!");

        eventPlayerDiedEnabled = add(builder, "event-player-died-enabled", true, "Player death");
    }

    public static void init() {
        Config.init(CONFIG.webhookUri.get(), CONFIG.serverName.get(), CONFIG.userAvatarUrl.get(), CONFIG.webhookMode.get(),
                CONFIG.classicMessageFormat.get(), CONFIG.eventPlayerMessageEnabled.get(), CONFIG.eventServerStartingEnabled.get(),
                CONFIG.eventServerStarting.get(), CONFIG.eventServerStartedEnabled.get(), CONFIG.eventServerStarted.get(),
                CONFIG.eventServerStoppingEnabled.get(), CONFIG.eventServerStopping.get(), CONFIG.eventServerStoppedEnabled.get(),
                CONFIG.eventServerStopped.get(), CONFIG.eventPlayerJoinEnabled.get(), CONFIG.eventPlayerJoin.get(),
                CONFIG.eventPlayerLeaveEnabled.get(), CONFIG.eventPlayerLeave.get(), CONFIG.eventPlayerDiedEnabled.get());
    }

    private static ModConfigSpec.ConfigValue<String> add(ModConfigSpec.Builder builder, String path, String defaultValue) {
        return builder.define(path, defaultValue);
    }

    private static ModConfigSpec.ConfigValue<String> add(ModConfigSpec.Builder builder, String path, String defaultValue, String... comments) {
        builder.comment(comments);
        return builder.define(path, defaultValue);
    }

    private static ModConfigSpec.ConfigValue<String> add(ModConfigSpec.Builder builder, String path, String defaultValue, Predicate<Object> validator, String... comments) {
        builder.comment(comments);
        return builder.define(path, defaultValue, validator);
    }

    private static ModConfigSpec.ConfigValue<Boolean> add(ModConfigSpec.Builder builder, String path, boolean defaultValue, String... comments) {
        builder.comment(comments);
        return builder.define(path, defaultValue);
    }

    private static boolean verifyWebhookMode(final Object obj) {
        if (obj instanceof String mode) {
            return mode.equalsIgnoreCase("message") || mode.equalsIgnoreCase("embed") || mode.equalsIgnoreCase("list");
        }

        return false;
    }

    static {
        Pair<NeoConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(NeoConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}