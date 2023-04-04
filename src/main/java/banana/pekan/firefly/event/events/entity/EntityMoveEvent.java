package banana.pekan.firefly.event.events.entity;

import banana.pekan.firefly.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;

public class EntityMoveEvent extends Event {

    Entity entity;

    EntityType<?> entityType;

    Vec3d movement;

    public EntityMoveEvent(Entity entity, Vec3d movement) {
        this.entity = entity;
        this.movement = movement;
        this.entityType = entity == null ? null : entity.getType();
    }

    public static class Pre extends EntityMoveEvent {

        public Pre(Entity entity, Vec3d movement) {
            super(entity, movement);
        }
    }

    public static class Post extends EntityMoveEvent {

        public Post(Entity entity, Vec3d movement) {
            super(entity, movement);
        }
    }


    public void setMovement(Vec3d movement) {
        this.movement = movement;
    }

    public Vec3d getMovement() {
        return movement;
    }

    public Entity getEntity() {
        return entity;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

}
