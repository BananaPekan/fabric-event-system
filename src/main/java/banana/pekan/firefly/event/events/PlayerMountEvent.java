package banana.pekan.firefly.event.events;

import banana.pekan.firefly.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerMountEvent extends Event {

    PlayerEntity player;
    Entity vehicle;

    public PlayerMountEvent(PlayerEntity player, Entity vehicle) {
        this.player = player;
        this.vehicle = vehicle;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public Entity getVehicle() {
        return vehicle;
    }

}
