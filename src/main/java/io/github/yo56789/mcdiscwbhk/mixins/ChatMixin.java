package io.github.yo56789.mcdiscwbhk.mixins;

import io.github.yo56789.mcdiscwbhk.Main;
import io.github.yo56789.mcdiscwbhk.WebhookHandler;
import io.github.yo56789.mcdiscwbhk.config.Config;
import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;
import java.util.function.Function;

@Mixin(value=ServerPlayNetworkHandler.class)
public class ChatMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Redirect(method="handleMessage", at=@At(value = "INVOKE", target="Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Ljava/util/function/Function;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"))
    public void chatMessage(PlayerManager instance, Text message, Function<ServerPlayerEntity, Text> playerMessageFactory, MessageType type, UUID sender) {
        String data = WebhookHandler.assembleString(message.getString().replaceFirst("<[^>]+>", "").trim(), player.getDisplayName().asString());
        WebhookHandler.post(Config.WEBHOOK_URI, data);
        instance.broadcast(message, type, sender);
    }


}
