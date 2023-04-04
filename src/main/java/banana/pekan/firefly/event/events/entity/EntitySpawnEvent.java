package banana.pekan.firefly.event.events.entity;

import banana.pekan.firefly.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

public class EntitySpawnEvent extends Event {

    public Entity entity;
    public EntitySpawnS2CPacket packet;

    public EntitySpawnEvent(EntitySpawnS2CPacket packet) {
        this.packet = packet;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public EntitySpawnS2CPacket getPacket() {
        return this.packet;
    }

    public static class Pre extends EntitySpawnEvent {

        public Pre(EntitySpawnS2CPacket packet) {
            super(packet);
        }
    }

    public static class Post extends EntitySpawnEvent {

        public Post(EntitySpawnS2CPacket packet) {
            super(packet);
        }

    }

}
