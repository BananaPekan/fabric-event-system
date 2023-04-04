package banana.pekan.firefly.mixin;

import banana.pekan.firefly.event.EventInvoker;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.PacketEvent;
import banana.pekan.firefly.event.events.player.PlayerChatEvent;
import banana.pekan.firefly.mixininterface.ClientPlayNetworkHandlerInterface;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.encryption.NetworkEncryptionUtils;
import net.minecraft.network.message.LastSeenMessagesCollector;
import net.minecraft.network.message.MessageBody;
import net.minecraft.network.message.MessageChain;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Instant;
import java.util.ArrayList;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements ClientPlayNetworkHandlerInterface {

    @Shadow public abstract void sendPacket(Packet<?> packet);

    @Shadow private LastSeenMessagesCollector lastSeenMessagesCollector;

    @Shadow private MessageChain.Packer messagePacker;
    @Shadow public abstract void sendChatMessage(String content);

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    public void sendPacketInject(Packet<?> packet, CallbackInfo ci) {
        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            PacketEvent.Send event = new PacketEvent.Send(packet);
            EventInvoker.invokeEventWithTypes(registeredClass, event, PacketEvent.class, PacketEvent.Send.class);

            if (event.getPacket() != packet) {
                sendPacket(event.getPacket());
                ci.cancel();
            }

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

    ArrayList<String> messagesBuffer = new ArrayList<>();

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessageI(String content, CallbackInfo ci) {

        if (messagesBuffer.contains(content)) {
            messagesBuffer.remove(content);
            return;
        }

        for (Object registeredClass : EventRegistry.registry.getRegisteredClasses()) {
            PlayerChatEvent event = new PlayerChatEvent(content);
            EventInvoker.invokeEventWithTypes(registeredClass, event, PlayerChatEvent.class);

            if (!event.getMessage().equals(content)) {
                ci.cancel();
                messagesBuffer.add(event.getMessage());
                sendChatMessage(event.getMessage());
            }

            if (event.isCancelled()) {
                ci.cancel();
            }

        }
    }

    @Override
    public Packet<?> getSignedChatMessage(String message) {
        long nextLong = NetworkEncryptionUtils.SecureRandomUtil.nextLong();
        Instant now = Instant.now();
        LastSeenMessagesCollector.LastSeenMessages lastSeenMessages = lastSeenMessagesCollector.collect();
        MessageSignatureData messageSignatureData = messagePacker.pack(new MessageBody(message, now, nextLong, lastSeenMessages.lastSeen()));
        return new ChatMessageC2SPacket(message, now, nextLong, messageSignatureData, lastSeenMessages.update());
    }

}
