package banana.pekan.firefly;

import banana.pekan.firefly.event.EventHandler;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.InputEvent;
import banana.pekan.firefly.event.events.player.PlayerChatEvent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireFly implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("firefly");

	@Override
	public void onInitialize() {
		EventRegistry.initialize();
		EventRegistry.registry.register(this);
	}

	@EventHandler
	public void onChat(PlayerChatEvent event) {
		event.setMessage("TEST");
	}

}
