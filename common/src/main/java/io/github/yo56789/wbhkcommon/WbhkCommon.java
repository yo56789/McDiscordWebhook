package io.github.yo56789.wbhkcommon;

import io.github.yo56789.wbhkcommon.config.Config;
import io.github.yo56789.wbhkcommon.data.Colors;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;

public class WbhkCommon {

    public static void init(@Nullable Path configPath, ExecutorService threadExecutor) {
        if (configPath != null) {
            Config.init(configPath);
        }

        WebhookHandler.init(threadExecutor);
    }

    public static void chatMessageEvent(String messageContent, String senderName, String uuid) {
        String data = WebhookHandler.assembleMessage(messageContent, senderName, Colors.BLUE.colorCode, uuid);
        WebhookHandler.post(data);
    }

    public static void serverStartingEvent() {
        String data = WebhookHandler.assembleMessage(Config.EVENT_SERVER_STARTING, Config.SERVER_NAME, Colors.DARK_GREEN.colorCode);
        WebhookHandler.post(data);
    }

    public static void serverStartedEvent() {
        String data = WebhookHandler.assembleMessage(Config.EVENT_SERVER_STARTED, Config.SERVER_NAME, Colors.GREEN.colorCode);
        WebhookHandler.post(data);
    }

    public static void serverStoppingEvent() {
        String data = WebhookHandler.assembleMessage(Config.EVENT_SERVER_STOPPING, Config.SERVER_NAME, Colors.DARK_RED.colorCode);
        WebhookHandler.post(data);
    }

    public static void serverStoppedEvent() {
        String data = WebhookHandler.assembleMessage(Config.EVENT_SERVER_STOPPED, Config.SERVER_NAME, Colors.RED.colorCode);
        WebhookHandler.post(data);
    }

    public static void playerJoinedEvent(String playerName) {
        String data = WebhookHandler.assembleMessage(String.format(Config.EVENT_PLAYER_JOIN, playerName), Config.SERVER_NAME, Colors.GREEN.colorCode);
        WebhookHandler.post(data);
    }

    public static void playerLeaveEvent(String playerName) {
        String data = WebhookHandler.assembleMessage(String.format(Config.EVENT_PLAYER_LEAVE, playerName), Config.SERVER_NAME, Colors.RED.colorCode);
        WebhookHandler.post(data);
    }

    public static void playerDiedEvent(String reason) {
        String data = WebhookHandler.assembleMessage(reason, Config.SERVER_NAME, Colors.RED.colorCode);
        WebhookHandler.post(data);
    }
}
