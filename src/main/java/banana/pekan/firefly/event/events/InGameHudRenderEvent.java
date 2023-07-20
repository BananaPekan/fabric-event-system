package banana.pekan.firefly.event.events;

import banana.pekan.firefly.event.Event;
import banana.pekan.firefly.mixin.InGameHudMixin;
import net.minecraft.client.gui.DrawContext;

public class InGameHudRenderEvent extends Event {

    DrawContext context;

    public InGameHudRenderEvent(DrawContext context) {
        this.context = context;
    }

    public DrawContext getContext() {
        return context;
    }

    public static class Pre extends InGameHudRenderEvent {
        public Pre(DrawContext context) {
            super(context);
        }
    }

    public static class Post extends InGameHudRenderEvent {
        public Post(DrawContext context) {
            super(context);
        }
    }

}
