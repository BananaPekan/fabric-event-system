package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.InputEvent;
import banana.pekan.firefly.event.events.PacketEvent;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {

    @Shadow protected abstract void onMouseButton(long window, int button, int action, int mods);

    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    public void onMouseButtonInject(long window, int button, int action, int mods, CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            InputEvent.MouseEvent event = new InputEvent.MouseEvent(button, action);
            EventInvoker.invokeEventWithTypes(registeredClass, event, InputEvent.class, InputEvent.MouseEvent.class);

            if (event.getAction().action != action || event.getButton() != button) {
                onMouseButton(window, event.getButton(), event.getAction().action, mods);
                event.cancel();
            }

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

}
