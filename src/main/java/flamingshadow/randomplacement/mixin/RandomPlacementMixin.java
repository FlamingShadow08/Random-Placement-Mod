package flamingshadow.randomplacement.mixin;

import flamingshadow.randomplacement.RandomPlacementState;
import flamingshadow.randomplacement.RandomPlacementWhitelist;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Minecraft.class)
public class RandomPlacementMixin {

    @Unique
    private net.minecraft.world.item.Item randomPlacement$usedItem;

    @Inject(method = "startUseItem", at = @At("HEAD"))
    private void randomPlacement$beforeUseItem(CallbackInfo ci) {

        randomPlacement$usedItem = null;

        if (!RandomPlacementState.enabled) {
            return;
        }

        Minecraft minecraft = (Minecraft) (Object) this;

        if (minecraft.player == null) {
            return;
        }

        randomPlacement$usedItem = minecraft.player.getMainHandItem().getItem();

    }
    @Inject(method = "startUseItem", at = @At("RETURN"))
    private void randomPlacement$afterUseItem(CallbackInfo ci) {
        if (!RandomPlacementState.enabled) {
            return;
        }

        Minecraft minecraft = (Minecraft) (Object) this;

        if (minecraft.player == null || randomPlacement$usedItem == null) {
            return;
        }

        if (!RandomPlacementWhitelist.isAllowed(randomPlacement$usedItem)) {
            return;
        }

        List<Integer> allowedSlots = new ArrayList<>();

        for (int slot = 0; slot < 9; slot++) {
            var stack = minecraft.player.getInventory().getItem(slot);

            if (!stack.isEmpty()
                    && RandomPlacementWhitelist.isAllowed(stack.getItem())) {
                allowedSlots.add(slot);
            }
        }

        if (allowedSlots.isEmpty()) {
            return;
        }

        int randomIndex = minecraft.player.getRandom()
                .nextInt(allowedSlots.size());

        minecraft.player.getInventory()
                .setSelectedSlot(allowedSlots.get(randomIndex));
    }
}