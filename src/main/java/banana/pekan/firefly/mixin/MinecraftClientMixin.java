package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.ClientTickEvent;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow
    public static MinecraftClient getInstance() {
        return null;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tickHead(CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            ClientTickEvent.Pre event = new ClientTickEvent.Pre(getInstance());
            EventInvoker.invokeEventWithTypes(registeredClass, event, ClientTickEvent.class, ClientTickEvent.Pre.class);

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

    @Inject(method = "tick", at = @At("TAIL"), cancellable = true)
    public void tickTail(CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            ClientTickEvent.Post event = new ClientTickEvent.Post(getInstance());
            EventInvoker.invokeEventWithTypes(registeredClass, event, ClientTickEvent.Post.class);

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

}
