package banana.pekan.firefly.event;

import java.util.ArrayList;
import java.util.List;

public class EventRegistry {

    public static EventRegistry registry;

    private final ArrayList<Object> registered = new ArrayList<>();
    private final ArrayList<Event> events = new ArrayList<>();

    public EventRegistry() {
        registry = this;
    }

    public static void initialize() {
        if (registry == null) registry = new EventRegistry();
    }

    public void register(Object classToRegister) {
        registered.add(classToRegister);
    }

    public void unregister(Object registeredClass) {
        registered.remove(registeredClass);
    }

    public void clearRegistry() {
        registered.clear();
    }

    public List<Object> getRegisteredClasses() {
        return registered;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public List<Event> getEvents() {
        return events;
    }

}
