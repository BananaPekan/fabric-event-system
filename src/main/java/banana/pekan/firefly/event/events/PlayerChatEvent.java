package banana.pekan.firefly.event.events;

import banana.pekan.firefly.event.Event;

public class PlayerChatEvent extends Event {

    String message;

    public PlayerChatEvent(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
