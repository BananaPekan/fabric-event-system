package banana.pekan.firefly.event;

public class Event {

    boolean cancelled;

    public Event() {

    }

    public void onInvoked() {}

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }


}
