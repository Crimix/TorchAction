package com.black_dog20.torchaction.common.utils;

import com.black_dog20.bml.utils.item.NBTUtil;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

import static com.black_dog20.torchaction.common.utils.NBTTags.TAG_AUTO_PICKUP_MODE;
import static com.black_dog20.torchaction.common.utils.Translations.*;

public final class TorchHolderProperties {

    private TorchHolderProperties() {
    }

    public static void setAutoPickupMode(ItemStack torchHolder, boolean state) {
        if (!torchHolder.isEmpty()) {
            NBTUtil.putBoolean(torchHolder, TAG_AUTO_PICKUP_MODE, state);
        }
    }

    public static boolean getAutoPickupMode(ItemStack torchHolder) {
        return NBTUtil.getBoolean(torchHolder, TAG_AUTO_PICKUP_MODE, true);
    }

    public static Component getAutoPickupModeText(ItemStack torchHolder, ChatFormatting color) {
        if (!torchHolder.isEmpty()) {
            Supplier<Boolean> isOn = () -> TorchHolderProperties.getAutoPickupMode(torchHolder);

            return TextComponentBuilder.of(AUTO_PICKUP.get())
                    .format(color)
                    .with(":")
                    .format(color)
                    .space()
                    .with(ON.get(), OFF.get(), isOn)
                    .build();
        }
        return new TextComponent("");
    }
}
