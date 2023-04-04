package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.ChatMessageEvent;
import banana.pekan.firefly.event.events.InputEvent;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {

    @Shadow protected abstract void addMessage(Text message, @Nullable MessageSignatureData signature, int ticks, @Nullable MessageIndicator indicator, boolean refresh);

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"), cancellable = true)
    public void addMessageHead(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            ChatMessageEvent.Appear event = new ChatMessageEvent.Appear(message.getString());
            EventInvoker.invokeEventWithTypes(registeredClass, event, InputEvent.class, InputEvent.KeyEvent.class);

            if (event.isCancelled()) {
                ci.cancel();
            }
            if (!event.getMessage().equals(message.getString())) {
                ci.cancel();
                addMessage(Text.of(event.getMessage()), signature, ticks, indicator, refresh);
            }

        }
    }


}
