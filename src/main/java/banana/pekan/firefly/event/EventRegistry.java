package banana.pekan.firefly.event;

import java.util.ArrayList;
import java.util.List;

public class EventRegistry {

    public static EventRegistry registry;

    private ArrayList<Object> registered;
    private ArrayList<Event> events;

    public EventRegistry() {
        registered = new ArrayList<>();
        events = new ArrayList<>();
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
