package com.black_dog20.torchaction.common.items;

import com.black_dog20.bml.utils.keybinds.KeybindsUtil;
import com.black_dog20.torchaction.client.containers.TorchHolderContainer;
import com.black_dog20.torchaction.client.keybinds.Keybinds;
import com.black_dog20.torchaction.common.capabilities.TorchHolderCapability;
import com.black_dog20.torchaction.common.utils.TorchHolderProperties;
import com.black_dog20.torchaction.common.utils.TorchItemHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.torchaction.common.utils.Translations.*;

public class ItemTorchHolder extends Itembase {

    public ItemTorchHolder(Properties builder) {
        super(builder);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(PLACE_TORCH_KEY.get(ChatFormatting.GRAY, KeybindsUtil.getKeyBindText(Keybinds.PLACE)));
        stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null)
                .filter(TorchItemHandler.class::isInstance)
                .map(TorchItemHandler.class::cast)
                .ifPresent(handler -> tooltip.add(AMOUNT_TORCHES.get(ChatFormatting.GRAY, handler.getCountOfItems())));
        tooltip.add(AUTO_PICKUP_TOGGLE.get(ChatFormatting.GRAY));
        tooltip.add(TorchHolderProperties.getAutoPickupModeText(stack, ChatFormatting.GRAY));
        super.appendHoverText(stack, world, tooltip, flag);
    }


    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new TorchHolderCapability(stack);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack torchHolder = player.getItemInHand(hand);
        if (!player.isCrouching()) {
            if (!world.isClientSide) {
                player.openMenu(new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return torchHolder.getHoverName();
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
                        return new TorchHolderContainer(windowId, playerInventory, player);
                    }
                });
            }
            return InteractionResultHolder.pass(torchHolder);
        } else {
            if (!world.isClientSide) {
                TorchHolderProperties.setAutoPickupMode(torchHolder, !TorchHolderProperties.getAutoPickupMode(torchHolder));
                player.displayClientMessage(TorchHolderProperties.getAutoPickupModeText(torchHolder, ChatFormatting.WHITE), true);
            }

            return InteractionResultHolder.pass(torchHolder);
        }
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }
}
