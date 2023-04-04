package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.entity.EntityKilledEvent;
import banana.pekan.firefly.event.events.entity.EntitySpawnEvent;
import banana.pekan.firefly.event.events.player.PlayerMountEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract EntityType<?> getType();

    @Shadow public World world;

    @Shadow private int id;

    @Shadow public abstract @Nullable Entity getVehicle();

    @Inject(method = "startRiding(Lnet/minecraft/entity/Entity;Z)Z", at = @At("HEAD"), cancellable = true)
    public void startRiding(Entity entity, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (getType() != EntityType.PLAYER) return;
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            PlayerMountEvent event = new PlayerMountEvent((PlayerEntity) world.getEntityById(id), entity);
            EventInvoker.invokeEventWithTypes(registeredClass, event, PlayerMountEvent.class);

            if (event.isCancelled()) {
                cir.setReturnValue(false);
            }

        }
    }

    @Inject(method = "kill*", at = @At("HEAD"), cancellable = true)
    public void kill(CallbackInfo ci) {

        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            EntityKilledEvent event = new EntityKilledEvent(world.getEntityById(id));
            EventInvoker.invokeEventWithTypes(registeredClass, event, EntityKilledEvent.class);

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

    @Inject(method = "onSpawnPacket*", at = @At("HEAD"), cancellable = true)
    public void onSpawnPacketHead(EntitySpawnS2CPacket packet, CallbackInfo ci) {

        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            EntitySpawnEvent event = new EntitySpawnEvent(packet);
            EventInvoker.invokeEventWithTypes(registeredClass, event, EntitySpawnEvent.class, EntitySpawnEvent.Pre.class);

            if (event.isCancelled()) {
                ci.cancel();
            }

        }

    }

    @Inject(method = "onSpawnPacket*", at = @At("TAIL"), cancellable = true)
    public void onSpawnPacketTail(EntitySpawnS2CPacket packet, CallbackInfo ci) {

        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            EntitySpawnEvent event = new EntitySpawnEvent(packet);
            EventInvoker.invokeEventWithTypes(registeredClass, event, EntitySpawnEvent.Post.class);

            if (event.isCancelled()) {
                ci.cancel();
            }

        }

    }


}
