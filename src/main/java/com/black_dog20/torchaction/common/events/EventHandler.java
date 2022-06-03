package com.black_dog20.torchaction.common.events;

import com.black_dog20.torchaction.TorchAction;
import com.black_dog20.torchaction.common.utils.ModUtils;
import com.black_dog20.torchaction.common.utils.TorchHolderProperties;
import com.black_dog20.torchaction.common.utils.TorchItemHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = TorchAction.MOD_ID)
public class EventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onItemPickup(EntityItemPickupEvent event) {
        ItemEntity itemEntity = event.getItem();
        AtomicReference<ItemStack> remainingStackSimulated = new AtomicReference<>(itemEntity.getItem().copy());
        Player player = event.getPlayer();

        if (remainingStackSimulated.get().getItem() == Items.TORCH) {
            ItemStack torchHolder = ModUtils.findTorchHolder(event.getPlayer());
            if (torchHolder.isEmpty())
                return;
            if (!TorchHolderProperties.getAutoPickupMode(torchHolder))
                return;

            boolean hasRoom = torchHolder.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(TorchItemHandler.class::isInstance)
                    .map(TorchItemHandler.class::cast)
                    .map(TorchItemHandler::hasRoom)
                    .orElse(false);

            if (!hasRoom)
                return;

            torchHolder.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(TorchItemHandler.class::isInstance)
                    .map(TorchItemHandler.class::cast)
                    .map(handler -> {
                        remainingStackSimulated.set(handler.insertItemStack(remainingStackSimulated.get(), false));
                        return remainingStackSimulated.get().isEmpty();
                    })
                    .orElse(false);

            if (!itemEntity.isSilent()) {
                Random rand = itemEntity.level.random;
                itemEntity.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, (getRandomFloatMinusRandomFloat(rand) * 0.7F + 1.0F) * 2.0F);
            }
            if (remainingStackSimulated.get().isEmpty()) {
                itemEntity.setItem(ItemStack.EMPTY);
                event.setResult(Event.Result.ALLOW);
            } else {
                if (player.getInventory().add(remainingStackSimulated.get())) {
                    itemEntity.setItem(ItemStack.EMPTY);
                    event.setResult(Event.Result.ALLOW);
                } else {
                    itemEntity.setItem(remainingStackSimulated.get());
                    event.setCanceled(true);
                }
            }
        }

    }

    private static float getRandomFloatMinusRandomFloat(Random rand) {
        return rand.nextFloat() - rand.nextFloat();
    }
}
