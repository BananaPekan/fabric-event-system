package banana.pekan.firefly.event.events.entity;

import banana.pekan.firefly.event.Event;
import net.minecraft.entity.Entity;

public class EntityKilledEvent extends Event {

    public Entity entity;

    public EntityKilledEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }

}
