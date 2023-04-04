package banana.pekan.firefly.mixininterface;

import net.minecraft.network.packet.Packet;

public interface ClientPlayNetworkHandlerInterface {

    public Packet<?> getSignedChatMessage(String message);

}
