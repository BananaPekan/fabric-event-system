package banana.pekan.firefly;

import banana.pekan.firefly.event.EventHandler;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.ClientTickEvent;
import banana.pekan.firefly.event.events.InGameHudRenderEvent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireFly implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("firefly");

	@Override
	public void onInitialize() {

	}

}
