package banana.pekan.firefly.event.events.player;

import banana.pekan.firefly.event.Event;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public class EntityBedEnterEvent extends Event {

    LivingEntity entity;
    BlockPos bedPos;

    public EntityBedEnterEvent(LivingEntity entity, BlockPos bedPos) {
        this.entity = entity;
        this.bedPos = bedPos;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public BlockPos getBedPos() {
        return bedPos;
    }

}
