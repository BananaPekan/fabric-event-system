package banana.pekan.firefly.mixininterface;

import net.minecraft.network.Packet;

public interface ClientPlayNetworkHandlerInterface {

    public Packet<?> getSignedChatMessage(String message);

}
