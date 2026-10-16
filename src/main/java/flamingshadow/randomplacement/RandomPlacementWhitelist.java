package flamingshadow.randomplacement;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class RandomPlacementWhitelist {

    private static final List<Class<?>> ALLOWED_CLASSES =
            new ArrayList<>();

    private static final List<Item> ALLOWED_ITEMS =
            new ArrayList<>();

    static {
        // Allow all items that extend BlockItem
        ALLOWED_CLASSES.add(BlockItem.class);

        // Add specific items here in the future
        // ALLOWED_ITEMS.add(...);
    }

    public static boolean isAllowed(Item item) {

        for (Class<?> allowedClass : ALLOWED_CLASSES) {
            if (allowedClass.isInstance(item)) {
                return true;
            }
        }

        return ALLOWED_ITEMS.contains(item);
    }
}