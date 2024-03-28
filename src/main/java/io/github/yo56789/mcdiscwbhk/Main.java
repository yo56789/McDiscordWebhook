package io.github.yo56789.mcdiscwbhk;

import io.github.yo56789.mcdiscwbhk.config.Config;
import io.github.yo56789.mcdiscwbhk.data.Colors;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements DedicatedServerModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "mcdiscwbhk";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitializeServer() {
		Config.init();

		if (Config.EVENT_SERVER_STARTING_ENABLED) {
			ServerLifecycleEvents.SERVER_STARTING.register((MinecraftServer server) -> {
				String data = WebhookHandler.assembleMessage(Config.EVENT_SERVER_STARTING, Config.SERVER_NAME, Colors.DARK_GREEN.colorCode);
				WebhookHandler.post(Config.WEBHOOK_URI, data);
			});
		}

		if (Config.EVENT_SERVER_STARTED_ENABLED) {
			ServerLifecycleEvents.SERVER_STARTED.register((MinecraftServer server) -> {
				String data = WebhookHandler.assembleMessage(Config.EVENT_SERVER_STARTED, Config.SERVER_NAME, Colors.GREEN.colorCode);
				WebhookHandler.post(Config.WEBHOOK_URI, data);
			});
		}

		if (Config.EVENT_SERVER_STOPPING_ENABLED) {
			ServerLifecycleEvents.SERVER_STOPPING.register((MinecraftServer server) -> {
				String data = WebhookHandler.assembleMessage(Config.EVENT_SERVER_STOPPING, Config.SERVER_NAME, Colors.DARK_RED.colorCode);
				WebhookHandler.post(Config.WEBHOOK_URI, data);
			});
		}

		if (Config.EVENT_SERVER_STOPPED_ENABLED) {
			ServerLifecycleEvents.SERVER_STOPPED.register((MinecraftServer server) -> {
				String data = WebhookHandler.assembleMessage(Config.EVENT_SERVER_STOPPED, Config.SERVER_NAME, Colors.RED.colorCode);
				WebhookHandler.post(Config.WEBHOOK_URI, data);
			});
		}

		if (Config.EVENT_PLAYER_JOIN_ENABLED) {
			ServerPlayConnectionEvents.JOIN.register((ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) -> {
				String data = WebhookHandler.assembleMessage(String.format(Config.EVENT_PLAYER_JOIN, handler.getPlayer().getName().asString()), Config.SERVER_NAME, Colors.GREEN.colorCode);
				WebhookHandler.post(Config.WEBHOOK_URI, data);
			});
		}

		if (Config.EVENT_PLAYER_LEAVE_ENABLED) {
			ServerPlayConnectionEvents.DISCONNECT.register((ServerPlayNetworkHandler handler, MinecraftServer server) -> {
				String data = WebhookHandler.assembleMessage(String.format(Config.EVENT_PLAYER_LEAVE, handler.getPlayer().getName().asString()), Config.SERVER_NAME, Colors.RED.colorCode);
				WebhookHandler.post(Config.WEBHOOK_URI, data);
			});
		}
	}
}
