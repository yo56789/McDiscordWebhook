package io.github.yo56789.mcdiscwbhk;

import io.github.yo56789.mcdiscwbhk.config.Config;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.world.ServerWorld;
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

		ServerLifecycleEvents.SERVER_STARTED.register((MinecraftServer server) -> {
			String data = WebhookHandler.assembleString("Server Started!", Config.SERVER_NAME);
			WebhookHandler.post(Config.WEBHOOK_URI, data);
		});

		ServerLifecycleEvents.SERVER_STOPPED.register((MinecraftServer server) -> {
			String data = WebhookHandler.assembleString("Server Stopped!", Config.SERVER_NAME);
			WebhookHandler.post(Config.WEBHOOK_URI, data);
		});

		ServerPlayConnectionEvents.JOIN.register((ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) -> {
			String data = WebhookHandler.assembleString(handler.getPlayer().getDisplayName().asString() + " has joined!", Config.SERVER_NAME);
			WebhookHandler.post(Config.WEBHOOK_URI, data);
		});

		ServerPlayConnectionEvents.DISCONNECT.register((ServerPlayNetworkHandler handler, MinecraftServer server) -> {
			String data = WebhookHandler.assembleString(handler.getPlayer().getDisplayName().asString() + " has left!", Config.SERVER_NAME);
			WebhookHandler.post(Config.WEBHOOK_URI, data);
		});
	}
}
