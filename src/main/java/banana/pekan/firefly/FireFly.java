package banana.pekan.firefly;

import banana.pekan.firefly.event.EventHandler;
import banana.pekan.firefly.event.EventRegistry;
import banana.pekan.firefly.event.events.player.EntityBedEnterEvent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
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
	public void onPlayerSleepEvent(EntityBedEnterEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
//			World world = event.getEntity().world;
//			TntEntity tnt = new TntEntity(EntityType.TNT, world);
//			tnt.setPosition(event.getEntity().getPos());
//			world.spawnEntity(tnt);
//			event.cancel();
		}
	}
}
