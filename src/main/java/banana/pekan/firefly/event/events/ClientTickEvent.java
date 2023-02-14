package banana.pekan.firefly.event.events;

import banana.pekan.firefly.event.Event;
import net.minecraft.client.MinecraftClient;

public class ClientTickEvent extends Event {

    MinecraftClient client;

    public ClientTickEvent(MinecraftClient client) {
        this.client = client;
    }

    public MinecraftClient getClient() {
        return client;
    }

    public static class Pre extends ClientTickEvent {

        public Pre(MinecraftClient client) {
            super(client);
        }

    }

    public static class Post extends ClientTickEvent {

        public Post(MinecraftClient client) {
            super(client);
        }

    }




}
