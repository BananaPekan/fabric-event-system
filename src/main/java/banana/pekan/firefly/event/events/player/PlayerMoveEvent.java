package banana.pekan.firefly.event.events.player;

import banana.pekan.firefly.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class PlayerMoveEvent extends Event {

    PlayerEntity player;
    Vec3d movement;

    public PlayerMoveEvent(PlayerEntity player, Vec3d movement) {
        this.player = player;
        this.movement = movement;
    }

    public static class Pre extends PlayerMoveEvent {

        public Pre(PlayerEntity player, Vec3d movement) {
            super(player, movement);
        }
    }

    public static class Post extends PlayerMoveEvent {

        public Post(PlayerEntity player, Vec3d movement) {
            super(player, movement);
        }
    }


    public void setMovement(Vec3d movement) {
        this.movement = movement;
    }

    public Vec3d getMovement() {
        return movement;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

}
