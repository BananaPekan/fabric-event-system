package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.ChatMessageEvent;
import banana.pekan.firefly.event.events.InGameHudRenderEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void renderHead(DrawContext context, float tickDelta, CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            InGameHudRenderEvent.Pre event = new InGameHudRenderEvent.Pre(context);
            EventInvoker.invokeEventWithTypes(registeredClass, event, InGameHudRenderEvent.class, InGameHudRenderEvent.Pre.class);

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

    @Inject(method = "render", at = @At("TAIL"), cancellable = true)
    public void renderTail(DrawContext context, float tickDelta, CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            InGameHudRenderEvent.Post event = new InGameHudRenderEvent.Post(context);
            EventInvoker.invokeEventWithTypes(registeredClass, event, InGameHudRenderEvent.class, InGameHudRenderEvent.Post.class);

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

}
