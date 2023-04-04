# Fabric Event System
[![](https://jitpack.io/v/BananaPekan/fabric-event-system.svg)](https://jitpack.io/#BananaPekan/fabric-event-system)

A basic Event System for minecraft's Fabric mod loader.

*Usage:*

Put this code in the onInitialize function of the mod:
```java
// Initialize the event registry
EventRegistry.initialize();
// Create a class for listening for events,
// and register it by using the function below with an instance of that class.
EventRegistry.registry.register(eventListener);
```

To invoke a function at a certain event use functions like in this example:
```java
@EventHandler
public void onPacketEvent(PacketEvent event) {
    Packet<?> packet = event.getPacket();
    if (packet instanceof PlayerMoveC2SPacket) {
        event.cancel();
    }
}
```


