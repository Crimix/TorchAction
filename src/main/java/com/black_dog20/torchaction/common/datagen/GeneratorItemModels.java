package com.black_dog20.torchaction.common.datagen;

import com.black_dog20.bml.datagen.BaseItemModelProvider;
import com.black_dog20.torchaction.TorchAction;
import com.black_dog20.torchaction.common.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class GeneratorItemModels extends BaseItemModelProvider {

    public GeneratorItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TorchAction.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModItems.ITEMS.getEntries().forEach(item -> {
            String path = ForgeRegistries.ITEMS.getKey(item.get()).getPath();
            singleTexture(path, mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
        });
    }

    @Override
    public @NotNull String getName() {
        return String.format("Torch Action: %s", "Item Models");
    }
}
