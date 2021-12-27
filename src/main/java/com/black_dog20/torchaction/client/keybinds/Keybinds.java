package com.black_dog20.torchaction.client.keybinds;

import com.black_dog20.torchaction.TorchAction;
import com.black_dog20.torchaction.common.network.PacketHandler;
import com.black_dog20.torchaction.common.network.packets.PacketPlaceTorch;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static com.black_dog20.torchaction.common.utils.Translations.CATEGORY;
import static com.black_dog20.torchaction.common.utils.Translations.KEY_PLACE_TORCH;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TorchAction.MOD_ID, value = Dist.CLIENT)
public class Keybinds {
    public static final KeyMapping PLACE = new KeyMapping(KEY_PLACE_TORCH.getDescription(),  KeyConflictContext.IN_GAME, KeyModifier.NONE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, CATEGORY.getDescription());

    @SubscribeEvent
    public static void onEvent(InputEvent event) {
            if (PLACE.consumeClick()) {
                HitResult hitResult = Minecraft.getInstance().hitResult;
                if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    PacketHandler.sendToServer(new PacketPlaceTorch(blockHitResult));
                }

            }
    }
}
