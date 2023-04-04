package banana.pekan.firefly.event.events;

import banana.pekan.firefly.event.Event;

public class ServerLoginEvent extends Event {

    public String address;

    public String getAddress() {
        return address;
    }

    public ServerLoginEvent(String address) {
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
