package banana.pekan.firefly.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class EventInvoker {

    @SafeVarargs
    public static Method[] getEventMethods(Class<?> classIn, Class<? extends Event>... event) {

        if (classIn != null) {
            Method[] methods = classIn.getDeclaredMethods();
            return Arrays.stream(methods).filter(method -> {
                Annotation methodAnnotation = method.getAnnotation(EventHandler.class);
                if (method.getParameterCount() != 1) return false;
                if (Arrays.stream(event).noneMatch(eventType -> method.getParameterTypes()[0] == eventType)) {
                    return false;
                }
                return methodAnnotation != null;
            }).toArray(Method[]::new);
        }
        return new Method[0];

    }

    @SafeVarargs
    public static Event invokeEventWithTypes(Object classIn, Event event, Class<? extends Event>... types) {

        Method[] methods = getEventMethods(classIn.getClass(), types);

        for (Method method : methods) {
            try {
                method.invoke(classIn, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return event;

    }


    public static Event invokeEvent(Object classIn, Event event) {
        Method[] methods = getEventMethods(classIn.getClass(), event.getClass());

        for (Method method : methods) {
            try {
                method.invoke(classIn, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return event;

    }

}
