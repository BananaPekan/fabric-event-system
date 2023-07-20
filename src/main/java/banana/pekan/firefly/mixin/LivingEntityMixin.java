package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.ItemUseEvent;
import banana.pekan.firefly.event.events.entity.EntityMoveEvent;
import banana.pekan.firefly.event.events.player.EntityBedEnterEvent;
import banana.pekan.firefly.event.events.player.EntityBedLeaveEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Shadow public abstract boolean isUsingItem();

    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    Vec3d movement;

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    public void tickMovementHead(CallbackInfo ci) {
        if (movement == null) movement = new Vec3d(0, 0, 0);
        if (movement.x != 0 || movement.y != 0 || movement.z != 0) {

            dismountVehicle();


            for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
                EntityMoveEvent.Pre event = new EntityMoveEvent.Pre(world.getEntityById(getId()), movement);
                EventInvoker.invokeEventWithTypes(registeredClass, event, EntityMoveEvent.class, EntityMoveEvent.Pre.class);

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
                EntityMoveEvent.Post event = new EntityMoveEvent.Post(world.getEntityById(getId()), movement);
                EventInvoker.invokeEventWithTypes(registeredClass, event, EntityMoveEvent.class, EntityMoveEvent.Post.class);

                if (movement != event.getMovement()) {
                    setPosition(getPos().add(event.getMovement().subtract(movement)));
                }

                if (event.isCancelled()) {
                    ci.cancel();
                }

            }

        }
    }

    @Inject(method = "sleep", at = @At("HEAD"), cancellable = true)
    public void sleep(BlockPos pos, CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            Entity entity = world.getEntityById(getId());
            if (!(entity instanceof LivingEntity)) continue;
            EntityBedEnterEvent event = new EntityBedEnterEvent((LivingEntity) entity, pos);
            EventInvoker.invokeEventWithTypes(registeredClass, event, EntityBedEnterEvent.class);

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

    @Inject(method = "wakeUp", at = @At("HEAD"), cancellable = true)
    public void wakeUp(CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            Entity entity = world.getEntityById(getId());
            if (!(entity instanceof LivingEntity livingEntity)) continue;
            if (livingEntity.getSleepingPosition().isEmpty()) continue;
            EntityBedLeaveEvent event = new EntityBedLeaveEvent(livingEntity, livingEntity.getSleepingPosition().get());
            EventInvoker.invokeEventWithTypes(registeredClass, event, EntityBedLeaveEvent.class);

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

    @Inject(method = "setCurrentHand", at = @At("HEAD"), cancellable = true)
    public void setCurrentHand(Hand hand, CallbackInfo ci) {
        ItemStack itemStack = getStackInHand(hand);
        if (!itemStack.isEmpty() && !isUsingItem() && !this.world.isClient) {
            for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
                ItemUseEvent event = new ItemUseEvent(itemStack, itemStack.getItem());
                EventInvoker.invokeEventWithTypes(registeredClass, event, ItemUseEvent.class);

                if (event.isCancelled()) {
                    ci.cancel();
                }

            }
        }
    }



}
