package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.InputEvent;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            InputEvent.KeyEvent event = new InputEvent.KeyEvent(key, scancode, action);
            EventInvoker.invokeEventWithTypes(registeredClass, event, InputEvent.class, InputEvent.KeyEvent.class);

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

}
