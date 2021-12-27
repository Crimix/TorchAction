package com.black_dog20.torchaction.common.utils;

import com.black_dog20.torchaction.common.compat.Curios;
import com.black_dog20.torchaction.common.items.ItemTorchHolder;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.util.stream.Stream;

public class ModUtils {

    public static ItemStack findTorchHolder(Player player) {
        return Stream.of(player.getInventory().items, player.getInventory().offhand, getCuriosTorchHolder(player))
                .flatMap(NonNullList::stream)
                .filter(s -> !s.isEmpty())
                .filter(s -> s.getItem() instanceof ItemTorchHolder)
                .findFirst()
                .orElse(ItemStack.EMPTY);
    }

    private static NonNullList<ItemStack> getCuriosTorchHolder(Player player) {
        if (ModList.get().isLoaded("curios")) {
            return Curios.getTorchHolders(player);
        } else {
            return NonNullList.withSize(1, ItemStack.EMPTY);
        }
    }
}
