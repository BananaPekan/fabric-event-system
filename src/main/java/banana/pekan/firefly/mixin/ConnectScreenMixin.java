package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.ServerLoginEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public abstract class ConnectScreenMixin {

    @Shadow protected abstract void connect(MinecraftClient client, ServerAddress address, @Nullable ServerInfo info);

    @Inject(method = "connect(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/network/ServerAddress;Lnet/minecraft/client/network/ServerInfo;)V", at = @At("HEAD"), cancellable = true)
    public void connectInject(MinecraftClient client, ServerAddress address, ServerInfo info, CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            ServerLoginEvent event = new ServerLoginEvent(address.getAddress());
            EventInvoker.invokeEventWithTypes(registeredClass, event, ServerLoginEvent.class);

            if (!event.getAddress().equals(address.getAddress())) {
                info.address = event.getAddress();
                connect(client, ServerAddress.parse(event.getAddress()), info);
                ci.cancel();
            }

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

}
