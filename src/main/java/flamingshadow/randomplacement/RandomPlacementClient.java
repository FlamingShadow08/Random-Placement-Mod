
package flamingshadow.randomplacement;

import net.minecraft.ChatFormatting;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class RandomPlacementClient implements ClientModInitializer {

    private static final KeyMapping TOGGLE_KEY =
            KeyMappingHelper.registerKeyMapping(
                    new KeyMapping(
                            "key.randomplacement.toggle",
                            InputConstants.Type.KEYBOARD,
                            InputConstants.KEY_R,
                            KeyMapping.Category.INVENTORY
                    )
            );

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (TOGGLE_KEY.consumeClick()) {
                RandomPlacementState.enabled = !RandomPlacementState.enabled;

                if (client.player != null) {
                    MutableComponent message = Component.literal("Random placement ")
                            .withStyle(ChatFormatting.GOLD)
                            .append(Component.literal(": ")
                                    .withStyle(ChatFormatting.WHITE));

                    if (RandomPlacementState.enabled) {
                        message.append(
                                Component.literal("On")
                                        .withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD)
                        );
                        client.gui.hud.setOverlayMessage(message, false);
                    } else {
                        message.append(
                                Component.literal("Off")
                                        .withStyle(ChatFormatting.RED, ChatFormatting.BOLD)
                        );
                        client.gui.hud.setOverlayMessage(message, false);
                    }
                }
            }
        });
    }
}
