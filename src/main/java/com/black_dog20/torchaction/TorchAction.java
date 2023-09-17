package com.black_dog20.torchaction;

import com.black_dog20.torchaction.client.containers.ModContainers;
import com.black_dog20.torchaction.common.items.ModItems;
import com.black_dog20.torchaction.common.network.PacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod(TorchAction.MOD_ID)
public class TorchAction {

    public static final String MOD_ID = "torchaction";
    private static final Logger LOGGER = LogManager.getLogger();

    public TorchAction() {
        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(event);
        ModItems.CREATIVE_MODE_TABS.register(event);
        ModContainers.CONTAINERS.register(event);

        event.addListener(this::setup);
        event.addListener(this::imc);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.register();
    }

    private void imc(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("torchholder").icon(new ResourceLocation(MOD_ID, "item/empty_torchholder_slot")).build());
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
