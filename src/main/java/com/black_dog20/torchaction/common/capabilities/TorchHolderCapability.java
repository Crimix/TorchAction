package com.black_dog20.torchaction.common.capabilities;

import com.black_dog20.torchaction.common.utils.TorchItemHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class TorchHolderCapability implements ICapabilityProvider {

    private ItemStack stack;
    private LazyOptional<IItemHandler> optional = LazyOptional.of(() -> new TorchItemHandler(stack));

    public TorchHolderCapability(ItemStack stack) {
        this.stack = stack;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null)
            return LazyOptional.empty();

        if (cap == ForgeCapabilities.ITEM_HANDLER)
            return optional.cast();
        else
            return LazyOptional.empty();
    }
}
