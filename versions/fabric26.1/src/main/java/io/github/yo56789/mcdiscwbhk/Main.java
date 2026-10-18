package io.github.yo56789.mcdiscwbhk;

import io.github.yo56789.wbhkcommon.WbhkCommon;
import io.github.yo56789.wbhkcommon.config.Config;
import net.fabricmc.api.DedicatedServerModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class Main implements DedicatedServerModInitializer {
	public static final String MODID = "mcdiscwbhk";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitializeServer() {

		WbhkCommon.init(FabricLoader.getInstance().getConfigDir(), Executors.newVirtualThreadPerTaskExecutor());

		if (Config.EVENT_PLAYER_MESSAGE_ENABLED) {
			ServerMessageEvents.CHAT_MESSAGE.register((PlayerChatMessage message, ServerPlayer sender, ChatType.Bound params) -> {
				WbhkCommon.chatMessageEvent(message.signedContent(), sender.getName().getString(), sender.getStringUUID());
			});
		}

		if (Config.EVENT_SERVER_STARTING_ENABLED) {
			ServerLifecycleEvents.SERVER_STARTING.register((MinecraftServer server) -> {
				WbhkCommon.serverStartingEvent();
			});
		}

		if (Config.EVENT_SERVER_STARTED_ENABLED) {
			ServerLifecycleEvents.SERVER_STARTED.register((MinecraftServer server) -> {
				WbhkCommon.serverStartedEvent();
			});
		}

		if (Config.EVENT_SERVER_STOPPING_ENABLED) {
			ServerLifecycleEvents.SERVER_STOPPING.register((MinecraftServer server) -> {
				WbhkCommon.serverStoppingEvent();
			});
		}

		if (Config.EVENT_SERVER_STOPPED_ENABLED) {
			ServerLifecycleEvents.SERVER_STOPPED.register((MinecraftServer server) -> {
				WbhkCommon.serverStoppedEvent();
			});
		}

		if (Config.EVENT_PLAYER_JOIN_ENABLED) {
			ServerPlayConnectionEvents.JOIN.register((ServerGamePacketListenerImpl listener, PacketSender sender, MinecraftServer server) -> {
				WbhkCommon.playerJoinedEvent(listener.getPlayer().getPlainTextName());
			});
		}

		if (Config.EVENT_PLAYER_LEAVE_ENABLED) {
			ServerPlayConnectionEvents.DISCONNECT.register((ServerGamePacketListenerImpl handler, MinecraftServer server) -> {
				WbhkCommon.playerLeaveEvent(handler.getPlayer().getPlainTextName());
			});
		}
	}
}
