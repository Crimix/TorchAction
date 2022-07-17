package com.black_dog20.torchaction.client;

import com.black_dog20.torchaction.TorchAction;
import com.black_dog20.torchaction.client.containers.ModContainers;
import com.black_dog20.torchaction.client.keybinds.Keybinds;
import com.black_dog20.torchaction.client.screens.TorchHolderScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TorchAction.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        MenuScreens.register(ModContainers.TORCH_HOLDER_CONTAINER.get(), TorchHolderScreen::new);
    }

    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(Keybinds.PLACE);
    }

    @SubscribeEvent
    public static void setupClient(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location() == InventoryMenu.BLOCK_ATLAS) {
            event.addSprite(new ResourceLocation(TorchAction.MOD_ID, "item/empty_torchholder_slot"));
        }
    }
}
