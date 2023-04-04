package banana.pekan.firefly.event.events;

import banana.pekan.firefly.event.Event;

public class ChatMessageEvent extends Event {

    String message;

    public ChatMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static class Appear extends ChatMessageEvent {

        public Appear(String message) {
            super(message);
        }

    }

    public void setMessage(String message) {
        this.message = message;
    }

}
