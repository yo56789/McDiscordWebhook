package io.github.yo56789.mcdiscwbhk.mixins;

import io.github.yo56789.mcdiscwbhk.WebhookHandler;
import io.github.yo56789.mcdiscwbhk.config.Config;
import io.github.yo56789.mcdiscwbhk.data.Colors;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value=ServerPlayNetworkHandler.class)
public class ChatMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Inject(method="handleMessage", at=@At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Ljava/util/function/Function;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"))
    public void chatMessage(TextStream.Message message, CallbackInfo ci) {
        if (Config.EVENT_PLAYER_MESSAGE_ENABLED) {
            String data = WebhookHandler.assembleMessage(message.getRaw().replaceFirst("<[^>]+>", "").trim(), player.getName().asString(), Colors.BLUE.colorCode, player.getUuidAsString());
            WebhookHandler.post(Config.WEBHOOK_URI, data);
        }
    }
}
