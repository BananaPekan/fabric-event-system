package banana.pekan.firefly.event.events;

import banana.pekan.firefly.event.Event;
import net.minecraft.network.packet.Packet;

public class PacketEvent extends Event {

    Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public static class Send extends PacketEvent {

        public Send(Packet<?> packet) {
            super(packet);
        }

    }

    public static class Receive extends PacketEvent {

        public Receive(Packet<?> packet) {
            super(packet);
        }
    }

}
