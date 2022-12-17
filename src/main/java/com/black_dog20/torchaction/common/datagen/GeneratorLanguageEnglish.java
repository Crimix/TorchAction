package com.black_dog20.torchaction.common.datagen;

import com.black_dog20.bml.datagen.BaseLanguageProvider;
import com.black_dog20.torchaction.TorchAction;
import com.black_dog20.torchaction.common.items.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import static com.black_dog20.torchaction.common.utils.Translations.*;

public class GeneratorLanguageEnglish extends BaseLanguageProvider {

    public GeneratorLanguageEnglish(PackOutput packOutput) {
        super(packOutput, TorchAction.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addPrefixed(ITEM_CATEGORY, "Torch Action");
        add(ModItems.TORCH_HOLDER.get(), "Torch Holder");
        add("curios.identifier.torchholder", "Torch Holder");

        addPrefixed(CATEGORY, "Torch Action");
        addPrefixed(KEY_PLACE_TORCH, "Place torch");

        addPrefixed(AMOUNT_TORCHES, "Holds §6%s torches§r");
        addPrefixed(PLACE_TORCH_KEY, "Press %s to place torch");
        addPrefixed(AUTO_PICKUP_TOGGLE, "Sneak + Right click to toggle pickup");
        addPrefixed(AUTO_PICKUP, "Auto pickup");
        addPrefixed(ON, "On", ChatFormatting.GREEN);
        addPrefixed(OFF, "Off", ChatFormatting.RED);
    }

    @Override
    public @NotNull String getName() {
        return String.format("Torch Action: %s", super.getName());
    }
}
