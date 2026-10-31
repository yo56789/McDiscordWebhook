package io.github.yo56789.mcdiscwbhk;

import io.github.yo56789.mcdiscwbhk.config.NeoConfig;
import io.github.yo56789.wbhkcommon.WbhkCommon;
import io.github.yo56789.wbhkcommon.config.Config;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.concurrent.Executors;

@Mod(value = Main.MODID, dist = Dist.DEDICATED_SERVER)
public class Main {

    public static final String MODID = "mcdiscwbhk";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Main(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, NeoConfig.SPEC);
    }

    @SubscribeEvent
    public void onCommonSetup(final FMLCommonSetupEvent event) {
        WbhkCommon.init(null, Executors.newVirtualThreadPerTaskExecutor());

        if (Config.EVENT_PLAYER_MESSAGE_ENABLED) {
            NeoForge.EVENT_BUS.addListener(Main::onPlayerMessage);
        }

        if (Config.EVENT_SERVER_STARTING_ENABLED) {
            NeoForge.EVENT_BUS.addListener(Main::onServerStarting);
        }

        if (Config.EVENT_SERVER_STARTED_ENABLED) {
            NeoForge.EVENT_BUS.addListener(Main::onServerStarted);
        }

        if (Config.EVENT_SERVER_STOPPING_ENABLED) {
            NeoForge.EVENT_BUS.addListener(Main::onServerStopping);
        }

        if (Config.EVENT_SERVER_STOPPED_ENABLED) {
            NeoForge.EVENT_BUS.addListener(Main::onServerStopped);
        }

        if (Config.EVENT_PLAYER_JOIN_ENABLED) {
            NeoForge.EVENT_BUS.addListener(Main::onPlayerJoin);
        }

        if (Config.EVENT_PLAYER_LEAVE_ENABLED) {
            NeoForge.EVENT_BUS.addListener(Main::onPlayerLeave);
        }

        if (Config.EVENT_PLAYER_DIED_ENABLED) {
            NeoForge.EVENT_BUS.addListener(Main::onPlayerDied);
        }
    }

    private static void onPlayerMessage(ServerChatEvent event) {
        WbhkCommon.chatMessageEvent(event.getMessage().getString(), event.getUsername(), event.getPlayer().getUUID().toString());
    }

    private static void onServerStarting(ServerStartingEvent event) {
        WbhkCommon.serverStartingEvent();
    }

    private static void onServerStarted(ServerStartedEvent event) {
        WbhkCommon.serverStartedEvent();
    }

    private static void onServerStopping(ServerStoppingEvent event) {
        WbhkCommon.serverStoppingEvent();
    }

    private static void onServerStopped(ServerStoppedEvent event) {
        WbhkCommon.serverStoppedEvent();
    }

    private static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        WbhkCommon.playerJoinedEvent(event.getEntity().getName().getString());
    }

    private static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        WbhkCommon.playerLeaveEvent(event.getEntity().getName().getString());
    }

    private static void onPlayerDied(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            WbhkCommon.playerDiedEvent(event.getSource().getLocalizedDeathMessage(player).getString());
        }
    }
}
