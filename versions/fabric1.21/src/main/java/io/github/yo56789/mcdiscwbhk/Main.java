package io.github.yo56789.mcdiscwbhk;

import io.github.yo56789.wbhkcommon.WbhkCommon;
import io.github.yo56789.wbhkcommon.config.Config;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
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
			ServerMessageEvents.CHAT_MESSAGE.register((SignedMessage message, ServerPlayerEntity sender, MessageType.Parameters params) -> {
				WbhkCommon.chatMessageEvent(message.getContent().getString(), sender.getName().getString(), sender.getUuidAsString());
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
			ServerPlayConnectionEvents.JOIN.register((ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) -> {
				WbhkCommon.playerJoinedEvent(handler.getPlayer().getName().getString());
			});
		}

		if (Config.EVENT_PLAYER_LEAVE_ENABLED) {
			ServerPlayConnectionEvents.DISCONNECT.register((ServerPlayNetworkHandler handler, MinecraftServer server) -> {
				WbhkCommon.playerLeaveEvent(handler.getPlayer().getName().getString());
			});
		}

		if (Config.EVENT_PLAYER_DIED_ENABLED) {
			ServerLivingEntityEvents.AFTER_DEATH.register((LivingEntity entity, DamageSource source) -> {
				if (entity instanceof PlayerEntity) {
					WbhkCommon.playerDiedEvent(source.getDeathMessage(entity).getString());
				}
			});
		}
	}
}
