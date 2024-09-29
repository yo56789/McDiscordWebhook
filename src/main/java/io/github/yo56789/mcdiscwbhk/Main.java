package io.github.yo56789.mcdiscwbhk;

import io.github.yo56789.mcdiscwbhk.config.Config;
import io.github.yo56789.mcdiscwbhk.data.Colors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
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

@Mod(value = Main.MODID, dist = Dist.DEDICATED_SERVER)
public class Main {

    public static final String MODID = "mcdiscwbhk";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Main(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onCommonSetup(final FMLCommonSetupEvent event) {
        if (Config.EventPlayerMessageEnabled) {
            NeoForge.EVENT_BUS.addListener(Main::onPlayerMessage);
        }

        if (Config.EventServerStartingEnabled) {
            NeoForge.EVENT_BUS.addListener(Main::onServerStarting);
        }

        if (Config.EventServerStartedEnabled) {
            NeoForge.EVENT_BUS.addListener(Main::onServerStarted);
        }

        if (Config.EventServerStoppingEnabled) {
            NeoForge.EVENT_BUS.addListener(Main::onServerStopping);
        }

        if (Config.EventServerStoppedEnabled) {
            NeoForge.EVENT_BUS.addListener(Main::onServerStopped);
        }

        if (Config.EventPlayerJoinEnabled) {
            NeoForge.EVENT_BUS.addListener(Main::onPlayerJoin);
        }

        if (Config.EventPlayerLeaveEnabled) {
            NeoForge.EVENT_BUS.addListener(Main::onPlayerLeave);
        }
    }

    private static void onPlayerMessage(ServerChatEvent event) {
        String data = WebhookHandler.assembleMessage(event.getMessage().getString(), event.getUsername(), Colors.BLUE.colorCode, event.getPlayer().getUUID().toString());
        WebhookHandler.post(Config.WebhookURI, data);

    }

    private static void onServerStarting(ServerStartingEvent event) {
        String data = WebhookHandler.assembleMessage(Config.EventServerStarting, Config.ServerName, Colors.DARK_GREEN.colorCode);
        WebhookHandler.post(Config.WebhookURI, data);
    }

    private static void onServerStarted(ServerStartedEvent event) {
        String data = WebhookHandler.assembleMessage(Config.EventServerStarted, Config.ServerName, Colors.GREEN.colorCode);
        WebhookHandler.post(Config.WebhookURI, data);
    }

    private static void onServerStopping(ServerStoppingEvent event) {
        String data = WebhookHandler.assembleMessage(Config.EventServerStopping, Config.ServerName, Colors.DARK_RED.colorCode);
        WebhookHandler.post(Config.WebhookURI, data);
    }

    private static void onServerStopped(ServerStoppedEvent event) {
        String data = WebhookHandler.assembleMessage(Config.EventServerStopped, Config.ServerName, Colors.RED.colorCode);
        WebhookHandler.post(Config.WebhookURI, data);
    }

    private static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        String data = WebhookHandler.assembleMessage(String.format(Config.EventPlayerJoin, event.getEntity().getName().getString()), Config.ServerName, Colors.GREEN.colorCode);
        WebhookHandler.post(Config.WebhookURI, data);
    }

    private static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        String data = WebhookHandler.assembleMessage(String.format(Config.EventPlayerLeave, event.getEntity().getName().getString()), Config.ServerName, Colors.RED.colorCode);
        WebhookHandler.post(Config.WebhookURI, data);
    }
}
