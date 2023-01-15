package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.PacketEvent;
import banana.pekan.firefly.event.events.PlayerMoveEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin<T> {

    @Shadow
    private static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener) {

    }

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void handlePacketInject(Packet<T> packet, PacketListener listener, CallbackInfo ci) {

        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            PacketEvent.Receive event = new PacketEvent.Receive(packet);
            EventInvoker.invokeEventWithTypes(registeredClass, event, PacketEvent.class, PacketEvent.Receive.class);

            if (event.getPacket() != packet) {
                handlePacket(event.getPacket(), listener);
                ci.cancel();
            }

            if (event.isCancelled()) {
                ci.cancel();
            }

        }

    }

}
