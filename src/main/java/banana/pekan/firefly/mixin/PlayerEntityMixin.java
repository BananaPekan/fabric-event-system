package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.player.PlayerMoveEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract float getMovementSpeed();

    @Shadow public abstract void sendMessage(Text message, boolean overlay);

    @Shadow public abstract void dismountVehicle();

    Vec3d movement;

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    public void tickMovementHead(CallbackInfo ci) {
        if (movement == null) movement = new Vec3d(0, 0, 0);
        if (movement.x != 0 || movement.y != 0 || movement.z != 0) {
            for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
                PlayerMoveEvent.Pre event = new PlayerMoveEvent.Pre(getWorld().getPlayerByUuid(getUuid()), movement);
                EventInvoker.invokeEventWithTypes(registeredClass, event, PlayerMoveEvent.class, PlayerMoveEvent.Pre.class);

                if (movement != event.getMovement()) {
                    setPosition(getPos().add(event.getMovement()));
                }

                if (event.isCancelled()) {
                    ci.cancel();
                }

            }

        }
        movement = getPos();
    }

    @Inject(method = "tickMovement", at = @At("TAIL"), cancellable = true)
    public void tickMovementTail(CallbackInfo ci) {
        movement = getPos().add(movement.negate());
        if (movement.x != 0 || movement.y != 0 || movement.z != 0) {

            for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
                PlayerMoveEvent.Post event = new PlayerMoveEvent.Post(getWorld().getPlayerByUuid(getUuid()), movement);
                EventInvoker.invokeEventWithTypes(registeredClass, event, PlayerMoveEvent.class, PlayerMoveEvent.Post.class);

                if (movement != event.getMovement()) {
                    setPosition(getPos().add(event.getMovement().subtract(movement)));
                }

                if (event.isCancelled()) {
                    ci.cancel();
                }

            }

        }
    }


}
