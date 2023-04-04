package banana.pekan.firefly.event.events;

import banana.pekan.firefly.event.Event;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemUseEvent extends Event {

    ItemStack itemStack;
    Item item;

    public ItemUseEvent(ItemStack itemStack, Item item) {
        this.itemStack = itemStack;
        this.item = item;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public Item getItem() {
        return this.item;
    }

}
