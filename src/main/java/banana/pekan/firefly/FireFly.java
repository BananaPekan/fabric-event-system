package banana.pekan.firefly;

import banana.pekan.firefly.event.EventHandler;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.InputEvent;
import net.fabricmc.api.ModInitializer;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireFly implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("firefly");

	@Override
	public void onInitialize() {
		EventRegistry.initialize();
		EventRegistry.registry.register(this);
	}


}
