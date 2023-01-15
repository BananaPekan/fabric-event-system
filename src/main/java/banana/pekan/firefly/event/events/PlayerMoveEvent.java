package banana.pekan.firefly.event.events;

import banana.pekan.firefly.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

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
